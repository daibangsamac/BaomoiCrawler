package src.main.controller;

import src.main.model.Crawler;
import src.main.service.CrawlerService;

/**
 * Controller class for Crawler
 * <p>This class is used to control the crawling process.</p>
 * @version 1.0
 * @author Nguyen Huu Quang
 */
public class CrawlerController {
    
    /**
     * Default constructor
     */
    public CrawlerController() {
    }

    /**
     * Handle create crawler request
     * @param url URL of the crawler
     * @param topic Topic of the crawler
     * @return Crawler object
     */
    public Crawler handleCreateCrawler(String url, String topic) {
        CrawlerService crawlerService = new CrawlerService();
        return crawlerService.createCrawler(url, topic);
    }

    /**
     * Handle crawl request
     * @param crawler Crawler object
     */
    public void handleCrawl(Crawler crawler) {
        CrawlerService crawlerService = new CrawlerService();
        try { 
            crawlerService.crawl(crawler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle store articles to database request
     * @param crawler Crawler object
     */
    public void handleStoreArticlesToDB(Crawler crawler) {
        CrawlerService crawlerService = new CrawlerService();
        crawlerService.storeArticlesToDB(crawler);
    }

    /**
     * Handle create JSON file request
     * @param crawler Crawler object
     */
    public void handleCreateJson(Crawler crawler) {
        CrawlerService crawlerService = new CrawlerService();
        try {
            crawlerService.createJson(crawler);
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }
}
