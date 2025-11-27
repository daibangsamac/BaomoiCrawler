package src.main.model;

import java.time.Instant;

/**
 * Class Article
 * <p>This class represents an article for <b>common news</b> with its attributes such as URL, time, title, topic, and content.</p>
 * @version 2.0
 * @author Nguyen Huu Quang	
 */
public class Article {
	private String url;
	private int id;
	private String og_url;
	private String author;
	private String title;
	private Instant publish_date;
	private String topic;
	private String body;
	private String keywords; //json

	/**
	 * Constructor of Article class
	 */
	public Article() {
		this.url = null;
		this.id = 0;
		this.og_url = null;
		this.author = null;
		this.title = null;
		this.publish_date = null;
		this.topic = null;
		this.body = null;
		this.keywords = null;
	}

	/**
	 * Constructor of Article class with parameters
	 * @param url URL of the article
	 * @param time Time of the article
	 * @param title Title of the article
	 * @param topic Topic of the article
	 * @param content Content of the article
	 */
	public Article(String url,int id,String og_url,String author,String title,Instant publish_date,String topic,String body,String keywords) {
		this.url = url;
		this.id = id;
		this.og_url = og_url;
		this.author = author;
		this.title = title;
		this.publish_date = publish_date;
		this.topic = topic;
		this.body = body;
		this.keywords = keywords;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOg_url() {
		return og_url;
	}

	public void setOg_url(String og_url) {
		this.og_url = og_url;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Instant getPublish_date() {
		return publish_date;
	}

	public void setPublish_date(Instant publish_date) {
		this.publish_date = publish_date;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
}
