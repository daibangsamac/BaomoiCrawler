package src.main;

import java.io.IOException;
import java.util.LinkedList;
import src.main.model.Crawler;
import src.main.controller.CrawlerController;
import src.main.util.DBConnectionPool;



/**
 * Main class
 * <p>This is the main class that runs the crawlers.</p>
 * <p>It creates multiple Crawler instances for the each topic and starts the crawling process.</p>
 * @version 1.0
 * @author Nguyen Huu Quang
 */
public class Main {
	private static LinkedList<Object[]> pages = new LinkedList<>();
	/** 
	 * Main method
	 * This run the application by default.
	 * @param args Command line arguments
	 * @throws IOException
	*/
    public static void main(String[] args) {
		// TODO: Handle command line arguments
		// TODO: Create a cli-based interface
		// Initialize database connection pool
		DBConnectionPool.getInstance();
		
		prepare();
		CrawlerController crawlerController = new CrawlerController();
		
		for (Object[] page : pages) {
			String url = (String) page[0];
			String topic = (String) page[1];
			Crawler crawler = crawlerController.handleCreateCrawler(url, topic); 
			try {
				crawlerController.handleCrawl(crawler);
				crawlerController.handleStoreArticlesToDB(crawler);
			} catch (Exception e) {
				System.err.println("Error while crawling topic: " + topic);
				e.printStackTrace();
			}
		}
	}

	/**
	 * Preparation 
	 */
	private static void prepare() {
		//pages.add(new Object[] {"https://baomoi.com/the-gioi","the-gioi"});
		pages.add(new Object[] {"https://baomoi.com/xa-hoi","xa-hoi"});
		pages.add(new Object[] {"https://baomoi.com/van-hoa","van-hoa"});
		pages.add(new Object[] {"https://baomoi.com/kinh-te","kinh-te"});
		//pages.add(new Object[] {"https://baomoi.com/giao-duc","giao-duc"});
	}
}
