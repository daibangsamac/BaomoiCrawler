package src.main.service;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedList;

import src.main.model.Article;
import src.main.model.Browser;
import src.main.model.Crawler;

/**
 * Service class for Crawler
 * <p>This class provides services related to the Crawler such as URL validation,
 * crawling process, and file creation for crawled articles.</p>
 * @version 1.0
 * @author Nguyen Huu Quang
 */
public class CrawlerService {
    
    private LinkedList<String> articleLinks = new LinkedList<String>();
   
    /**
     * Start crawling process
     * @throws MalformedURLException
     * @throws InterruptedException
     * @throws IOException
     */
    public void crawl(Crawler crawler) throws MalformedURLException, InterruptedException, IOException {
         // Check if URL is valid or not
        try {
            urlCheck(crawler);
        } catch (MalformedURLException e) {
            throw new MalformedURLException("Invalid URL: " + crawler.getUrl());
        }
        // Browse all summary pages to get article links
        browseAllSummaryPages(crawler);
        // Browse all articles to get article details
        browseAllArticles(crawler);
    }

    /**
     * Check if URL of crawler is valid or not
     * @throws MalformedURLException
     */
	private void urlCheck(Crawler crawler) throws MalformedURLException {
		// Check if URL is null/empty or not 
		if (crawler.getUrl() == null || crawler.getUrl().isEmpty()) {
			throw new MalformedURLException("URL is null or empty");
		}
        if (!crawler.getUrl().startsWith("http:/baomoi.com") && !crawler.getUrl().startsWith("https://baomoi.com")) {
            throw new MalformedURLException("URL is not from baomoi.com");
        }
	}

    /**
     * Browse summary page to get article links
     * @throws MalformedURLException
     * @throws InterruptedException
     */
    private void browseAllSummaryPages(Crawler crawler) throws MalformedURLException, InterruptedException, IOException {
        int page = 1;
		
		System.out.println("Crawling: " + crawler.getUrl());
		// Browsing the URL of summary page for all articles of the topic in range of pages  
		final int MAX_PAGE = 166;
		while (page <= MAX_PAGE) {
			String browsingUrl = crawler.getUrl() + "/trang" + page + ".epi";;
			try {
                BrowserService browserService = new BrowserService();
				Browser summaryPageBrowser = browserService.createBrowser(browsingUrl,"summary",crawler.getTopic());
				page = page + 4;
				
                LinkedList<String> links = browserService.fetchSummaryPageForLinks(summaryPageBrowser);
                if (links == null) {
                    System.out.println("Failed to fetch summary page: " + browsingUrl);
                    break;
                }
                if (links.isEmpty()) {
                    System.out.println("No more articles found on summary page: " + browsingUrl);
                    break;
                }
				articleLinks.addAll(links);
			} catch (InterruptedException e) {
				throw new InterruptedException("Interrupted while fetching summary page: " + browsingUrl);
			} catch (MalformedURLException e) {
				throw new MalformedURLException("Malformed URL: " + browsingUrl);
			} catch (IOException e) {
                throw new IOException(e + "IOException while fetching summary page: " + browsingUrl);
            }
			
		}
    }

    /**
     * Factory method to create a Crawler instance
     * @param url The URL to be crawled
     * @param topic The topic of the crawler
     * @return Crawler instance
     */
    public Crawler createCrawler(String url, String topic) {
        return new Crawler(url, topic);
    }

    /**
	 * Start crawling process
	 * 
	 * <p>Process of function:</p>
	 * <ol>
	 *   <li>Check if url is valid or not — if not, throw IOException.</li>
	 *   <li>Getting every article summary.</li>
	 *   <li>Getting every article description.</li>
	 * </ol>
	 * @throws MalformedURLException
	 * @throws InterruptedException
	 */
	private void browseAllArticles(Crawler crawler) throws MalformedURLException, InterruptedException,IOException {
        System.out.println("Total articles found: " + articleLinks.size());
		BrowserService browserService = new BrowserService();
		// Browsing all articles and parsing needed information
		for (String link : articleLinks) {
			Browser articlePage = browserService.createBrowser(link, "article", crawler.getTopic());
			try {
                Article article = browserService.fetchArticlePage(articlePage);
                if (article == null) {
                    System.out.println("Failed to fetch article page: " + link);
                    continue;
                }
                crawler.addArticle(article);
                //System.out.println("Parsed article: " + article.getTitle());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}

    /**
     * Store all crawled articles to the database
     */
    public void storeArticlesToDB(Crawler crawler) {
        ArticleService articleService = new ArticleService();
        for (Article article : crawler.getArticles()) {
            articleService.addArticleToDB(article);
        }
    }

    /**
	 * Create JSON files for all crawled articles
	 */
	public void createJson(Crawler crawler) throws IOException {
        ArticleService articleService = new ArticleService();
        for (Article article : crawler.getArticles()) {
            try {
                articleService.createJson(article);
            } catch (IOException e) {
                throw new IOException("IOException while creating JSON file for article: " + article.getTitle());
            }
        }
		// TODO Auto-generated method stub
	}

	/**
	 * Create YAML files for all crawled articles
	 */
	@SuppressWarnings("unused")
	public void createYaml(Crawler crawler) {
		// TODO Auto-generated method stub
	}
}