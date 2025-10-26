package backend.internal.source;
import java.io.IOException;

import java.util.LinkedList;


/**
 * Abstract class Crawler
 * <p>This class is used to define the structure of a crawler.</p>
 * <p>It contains methods to set and get the URL and topic of the crawler,
 * as well as an abstract method crawl() that must be implemented by subclasses.</p>
 * @version 1.0
 * @author Nguyen Huu Quang
 */
public class Crawler {
	
	private LinkedList<Article> articles = new LinkedList<Article>();
	private String url;
	private String topic;

	public Crawler(String url, String topic) {
		this.url = url;
		this.topic = topic;
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
	 * @throws IOException
	 */
	public void crawl() throws IOException {
		// Check URL validity
		try {
			urlCheck();
		}
		catch (IOException e) {
			throw new IOException("Invalid URL: " + this.url);
		}

		int page = 1;
		LinkedList<String> articleLinks = new LinkedList<String>();
		// Browsing the URL of summary page for all articles of the topic in range of pages  
		final int MAX_PAGE = 166;
		while (page <= MAX_PAGE) {
			String browsingUrl = this.url + "/trang" + page + ".epi";
			Browser summaryPage = new Browser(browsingUrl,"summary",topic);
			page = page + 4;
			System.out.println("Crawling: " + browsingUrl);
			articleLinks.addAll(summaryPage.getLinksOfArticles());
		}
		
		// Browsing all articles and parsing needed information
		for (String link : articleLinks) {
			Browser articlePage = new Browser(link,"article",topic);
			Article article = articlePage.parseArticle();
			this.articles.add(article);
			System.out.println("Parsed article: " + article.getTitle());
		}

	}

	/**
	 * Create JSON files for all crawled articles
	 */
	public void createJson() {
		for (Article article : this.articles) {
			article.createJson();
		}
	}

	/**
	 * Create YAML files for all crawled articles
	 */
	@SuppressWarnings("unused")
	public void createYaml() {
		// TODO Auto-generated method stub
	}

	/**
	 * Check if URL is valid or not
	 * @throws IOException
	 */
	public void urlCheck() throws IOException {
		// Check if URL is null/empty or not 
		if (this.url == null || this.url.isEmpty()) {
			throw new IOException("URL is null or empty");
		}
	}

	/**
	 * Set URL of the crawler
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}	
	
	/**
	 * Get URL of the crawler
	 * @return string URL of the crawler
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * Set topic of the crawler
	 * @param topic
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	};

	/**
	 * Get topic of the crawler
	 * @return string topic of the crawler
	 */
	public String getTopic() {
		return this.topic;
	}
	
	 
}
