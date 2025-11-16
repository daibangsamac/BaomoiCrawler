package src.main.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import src.main.model.Article;
import src.main.model.Browser;

/**
 * Service class for handling browser operations
 */
public class BrowserService {

    /**
     * Create a Browser object with the given URL, type, and topic
     * @param url The URL to be browsed
     * @param type The type of the page (summary or article)
     * @param topic The topic of the page
     * @return Browser object
     * @throws MalformedURLException If the URL is invalid
     * @throws InterruptedException 
     */
    public Browser createBrowser(String url, String type, String topic) throws MalformedURLException, InterruptedException {
        Browser browser = new Browser(url, type, topic);
		browse(browser);
        return browser;
    }
    
    /**
	 * Fetch the content of the URL in HTML
	 * @throws IOException
	 */
	private boolean request(Browser browser) {
		try { 
			URLConnection urlCon = browser.getUrl().openConnection();	
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(urlCon.getInputStream(), StandardCharsets.UTF_8));
			StringBuilder htmlBuilder = new StringBuilder();
        	String line;
			while ((line = bufferRead.readLine()) != null) {
				 htmlBuilder.append(line);
				 htmlBuilder.append("\n");
			}
			bufferRead.close();
			browser.setHtmlSource(htmlBuilder.toString());
		} catch (IOException e) {
			return false;
		}
        return true;
	}

    /**
     * Browse the given URL with retry mechanism
     * @param browser The Browser object containing the URL to be browsed
     */
    private void browse(Browser browser) throws InterruptedException {
        int attemp = 1;
		int MAX_ATTEMPT = 5;
		while (!request(browser) && attemp <= MAX_ATTEMPT) {
			System.out.println("Failed to fetch URL: " + browser.getUrl());
			try {
				TimeUnit.SECONDS.sleep(4);
			} catch (InterruptedException e) {
				throw new InterruptedException("Sleep interrupted while retrying to fetch URL: " + browser.getUrl());
			}
			System.out.println("Retrying to fetch URL: " + browser.getUrl() + " (Attempt " + attemp + " of 3)");
			attemp = attemp + 1;
		}
        if (browser.getHtmlSource() == null) 
            browser.setType(null);
    }

    /**
	 * Get links of articles from the browsed URL
	 * <p> This method reads the HTML content line by line and extracts links of articles
	 * based on specific HTML patterns. <br>
	 * Only for summary page, page containing multiple articles.
	 * </p>
	 * @return LinkedList<String> of article links
	 * @throws IOException
	 */
	public LinkedList<String> fetchSummaryPageForLinks(Browser browser) throws IOException {
		if (browser.getType() == null) {
			System.out.println("Unable to fetchh summary page of topic: " + browser.getTopic());
			return null;
		}
		if (!browser.getType().equals("summary"))
			throw new IOException("This method is only for summary page.");

		Document doc;
		LinkedList<String> links = new LinkedList<String>();


		doc = Jsoup.parse(browser.getHtmlSource());

		Elements allElements = doc.select("div.bm-card-header");

		for (Element div : allElements) {
		// Get the links of articles
		Elements articleLink = div.select("a");
		if (articleLink != null) {
			String href = articleLink.attr("href");
			links.add("https://baomoi.com" + href);
		}
		}
        return links;
	}

    /**
	 * Parse an article from the browsed URL
	 * <p> This method reads the HTML content line by line and extracts needed information
	 * based on specific HTML patterns. <br>
	 * Only for article page, page containing a single article.
	 * @return Article object containing parsed information
	 * @throws IOException
	 */
	public Article fetchArticlePage(Browser browser) throws IOException {
		if (browser.getType() == null) {
			System.out.println("Unable to fetchh article page: " + browser.getUrl());
			return null;
		}
		if (!browser.getType().equals("article"))
			throw new IOException("This method is only for article page.");
		Document doc = Jsoup.parse(browser.getHtmlSource());
		String url = browser.getUrl().toString();
		String time = "";
		String title = "";
		String topic = browser.getTopic();
		String content = "";
		// Extract time
		Elements timeElement = doc.select("time[datetime]");
		if (timeElement != null) {
			time = timeElement.attr("datetime").toString();
		}
		// Extract title
		Elements titleElement = doc.select("div.content-main.relative > h1");
		if (titleElement != null) {
			title = titleElement.text();
		}
		// Extract content
		Elements contentElement = doc.select("div.content-main");
		if (contentElement != null) {
			content = contentElement.toString();
		}

		Article article = new Article(url, time, title, topic, content);
		return article;
	}
}
