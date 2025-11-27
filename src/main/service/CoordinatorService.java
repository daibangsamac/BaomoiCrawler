package src.main.service;

import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import src.main.model.Browser;
import src.main.model.Coordinator;
import src.main.model.MyParser;
import src.main.repository.PostgreSQLURLRepository;
import src.main.infrastructure.queue.FrontierQueue;
import src.main.infrastructure.queue.DLQ;
import src.main.infrastructure.queue.ParsingQueue;

/**
 * Class CoordinatorService 
 * <p> Service provider for coordinator model </p>
 * @version 1.0
 * @author Nguyen Huu Quang
 */
public class CoordinatorService {
    public CoordinatorService() {
    }

    /**
     * Public starting function of default protocol
     * Step 1: Create DBConnectionPool
     * Step 1: Create queue threads
     * Step 2: Add default URLs to Frontier queue
     * Step 3: Creating new thread handle each queue-objects
     * Step 4: Track the time, url, article parsed  
     */
    public void start() {
        // TODO: Update to priority queue for scheduler
		// TODO: Make a class to handle thread command
		
		// Initialize queues
		FrontierQueue frontierQueue = FrontierQueue.getInstance();
		ParsingQueue parsingQueue = ParsingQueue.getInstance();
		DLQ dlq = DLQ.getInstance();
		
		// Initialize the frontier queue
		addDefaultURLs(frontierQueue);

		// Get the logical cores available for multi-threading
		int availableCores = Runtime.getRuntime().availableProcessors();
		availableCores = 10; // Version 2
		int availableThread = availableCores;

		// Available worker to do logical stuffs
		ExecutorService dlqExecutor = Executors.newFixedThreadPool(availableThread);
        ExecutorService executor = Executors.newFixedThreadPool(availableThread);
		
        BlockingQueue<Runnable> dlqTaskQueue = new LinkedBlockingQueue<>();
		BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
		
		// Frontier queue thread
		Thread frontierQueueThread = new Thread(() -> {
            while (true) {
                Coordinator coordinator;
				synchronized (frontierQueue) {
					while (frontierQueue.isEmpty()) {
						try {
							frontierQueue.wait(); 
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
							return; 
						}
					}
					coordinator = frontierQueue.poll();
				}
                Runnable task = () -> {
                    processCoordinator(coordinator);
                };
       			taskQueue.offer(task);
			}
        });
        frontierQueueThread.start();
        System.out.println("Starting frontier queue thread");
		
		// Parsing queue thread
		Thread parsingQueueThread = new Thread(() -> {
            while (true) {
                Coordinator coordinator;
				synchronized (parsingQueue) {
					while (parsingQueue.isEmpty()) {
						try {
							parsingQueue.wait(); 
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
							return; 
						}
					}
					coordinator = parsingQueue.poll();
				}
                Runnable task = () -> {
                    processCoordinator(coordinator);
                };
                taskQueue.offer(task); 
			}
        });
        parsingQueueThread.start();
		System.out.println("Starting parsing queue thread");

		// DLQ thread
		Thread dlqThread = new Thread(() -> {
            while (true) {
                Coordinator coordinator;
				synchronized (dlq) {
					while (dlq.isEmpty()) {
						try {
							dlq.wait(); 
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
							return; 
						}
					}
					coordinator = dlq.poll();     
				}
                Runnable task = () -> {
                    processCoordinator(coordinator);
                };
                dlqTaskQueue.offer(task);
			}
        });
        dlqThread.start();
        System.out.println("Starting dlq thread");

		// Worker threads for parsing and fetching
		for (int i = 0; i < availableThread; i++) {
			new Thread(() -> {
				while (true) {
					try {
						Runnable task = taskQueue.take(); 
						executor.submit(task);         
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						break;
					}
				}
			}).start();
            System.out.println("Starting worker" + (i+1) + " thread");
		}

        // Worker threads for dlq
        for (int i = 0; i < availableThread; i++) {
			new Thread(() -> {
				while (true) {
					try {
						Runnable task = dlqTaskQueue.take(); 
						dlqExecutor.submit(task);         
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						break;
					}
				}
			}).start();
            System.out.println("Starting dlq worker" + (i+1) + " thread");
		}

        // While 1 of 3 queue is still on, app continue to run
        while (!frontierQueue.isEmpty() || !parsingQueue.isEmpty() || dlq.isEmpty()) {
            // do sth
        }

        // Statistic
        // Update read db

        // Shutdown all threads
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (t != Thread.currentThread()) {
                t.interrupt(); 
            }
        }
    }

    /**
     * Handle of coordinator
     * @param coordinator Coordinator need to be handle
     */
    public void processCoordinator(Coordinator coordinator) {
        Coordinator.Stat status = Coordinator.Stat.valueOf(coordinator.getStatus().toUpperCase());
        switch (status) {
            case FETCHING:
                try {
                    System.out.println("FETCHING " + coordinator.getURL());
                    processFETCHING(coordinator);
                } catch (InterruptedException e) {
                    // Log sth here
                }
                break;
            case PARSING:
                System.out.println("PARSING " + coordinator.getURL());
                processPARSING(coordinator);
                break;
            case DLQ:
                System.out.println("DLQ-ing " + coordinator.getURL());
                processDLQ(coordinator);
                break;
            default:
                break;
        }
    }

    /**
     * Handle fetching status of the coordinator
     * Step 1: Check if there is url and HTMLSource in database; if there is, go to step 3, else go to step 2
     * Step 2: Call browser to try to fetch the url for HTMLSource 
     * Step 2.1: If request is accomplished store the url and HTMLSource in database
     * Step 2.2: If request is failed increase the attemp of coordinator and return 
     * Step 3: Put the url and topic into parsing queue
     * @param coordinator Coordinator need to be handle
     */
    private void processFETCHING(Coordinator coordinator) throws InterruptedException{
        // TODO: Create a method in CoordinatorService to check for URL in DB

        // Check if url is attemptable
        if (!coordinator.attemptable()) {
            System.out.println("Unable to fetch " + coordinator.getURL() + " out of attempts.");
            return;
        }

        String url = coordinator.getURL();

        // Create a Browser to fetch the url for HTMLSource and store the url and HTMLSource in database
        PostgreSQLURLRepository repo = new PostgreSQLURLRepository();
        BrowserService browserService = new BrowserService(repo);
        Browser browser = browserService.createBrowser(url);

        // Check if there is url and HTMLSource in database
        boolean isURLInDB = browserService.request(browser);
        
        // If Browser is failed to request or URL is not in DB then add URL to DLQ
        if (!isURLInDB) {
            coordinator.increaseAttempt();
            DLQ dlq = DLQ.getInstance();
            coordinator.setStatus("DLQ");
            synchronized (dlq) {
                dlq.add(coordinator);
                dlq.notify();
            }
            return;
        }

        // Put the url and topic into parsing queue
        ParsingQueue parsingQueue = ParsingQueue.getInstance();
        coordinator.setStatus("PARSING");
        synchronized (parsingQueue) {
            parsingQueue.add(coordinator);
            parsingQueue.notify();
        }
    }

    /**
     * Handle parsing status of the coordinator
     * Create a parser to parse HTMLSource and store in database
     * @param coordinator Coordinator need to be handle
     */
    private void processPARSING(Coordinator coordinator) {
        ParserService parserService = new ParserService();
        String url = coordinator.getURL();
        MyParser parser = parserService.createParser(url);
        if (coordinator.getType() == "summary-page") {
            LinkedList<String> urls = new LinkedList<>();
            FrontierQueue frontierQueue = FrontierQueue.getInstance();
            urls = parserService.parseAndGetURLs(parser);
            synchronized (frontierQueue) {
                for (String t:urls) {
                    frontierQueue.add(new Coordinator(t, "FETCHING", "article-page"));
                }
                frontierQueue.notify();
            }
        }

        if (coordinator.getType() == "article-page") {            
            parserService.parseAndStore(parser);
        }
    }

    /**
     * Handle DLQ status of the coordinator
     * Step 1: Check if URL is attemptable
     * Step 2: If URL is attemptable push it to frontier queue
     * @param coordinator Coordinator need to be handle
     */
    private void processDLQ(Coordinator coordinator) {
        if (!coordinator.attemptable()) {
            System.out.println("Unable to fetch " + coordinator.getURL() + " out of attempts.");
            return;
        }
        int timer = 2;
        try {
            TimeUnit.SECONDS.sleep(timer);
        } catch (Exception e) {
            // TODO: Log sth about sleep interruption
            return;
        }
        FrontierQueue frontierQueue = FrontierQueue.getInstance();
        coordinator.setStatus("FETCHING");
        synchronized (frontierQueue) {
            frontierQueue.add(coordinator);
            frontierQueue.notify();
        }
    }
    
    /**
	 * Preparation 
	 */
	private static void addDefaultURLs(FrontierQueue queue) {
        LinkedList<String> commonNews = new LinkedList<>();
		commonNews.add("https://baomoi.com/the-gioi");
        commonNews.add("https://baomoi.com/xa-hoi");
        commonNews.add("https://baomoi.com/van-hoa");
        commonNews.add("https://baomoi.com/kinh-te");
        commonNews.add("https://baomoi.com/giao-duc");
        commonNews.add("https://baomoi.com/giai-tri");
        commonNews.add("https://baomoi.com/phap-luat");
        commonNews.add("https://baomoi.com/khoa-hoc-cong-nghe");
        commonNews.add("https://baomoi.com/khoa-hoc");
        commonNews.add("https://baomoi.com/doi-song");
        commonNews.add("https://baomoi.com/xe-co");
        commonNews.add("https://baomoi.com/nha-dat");
        commonNews.add("https://baomoi.com/the-thao");
        for (String post_url:commonNews){
            for (int i = 1;i<=166;i=i+4) {
                String url = post_url + "/trang" + Integer.toString(i) + ".epi";
                queue.add(new Coordinator(url,"FETCHING","summary-page"));
            }
        }
        //queue.add(new Coordinator("https://baomoi.com/bong-da", "FETCHING", "summary-page"));	// Special news
        //queue.add(new Coordinator("https://baomoi.com/tien-ich", "FETCHING", "summary-page"));  // Special news
	}
}

