package src.main.model;
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

	/**
	 * Constructor of Crawler class
	 * @param url The URL to be crawled
	 * @param topic The topic of the crawler
	 */
	public Crawler(String url, String topic) {
		this.url = url;
		this.topic = topic;
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
	
	/**
	 * Get list of crawled articles
	 * @return LinkedList<Article> list of crawled articles
	 */
	public LinkedList<Article> getArticles() {
		return articles;
	}
	
	/**
	 * Set list of crawled articles
	 * @param articles
	 */
	public void addArticle(Article article) {
		this.articles.add(article);
	}
}
