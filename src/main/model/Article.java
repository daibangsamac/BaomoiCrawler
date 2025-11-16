package src.main.model;

/**
 * Class Article
 * <p>This class represents an article for <b>common news</b> with its attributes such as URL, time, title, topic, and content.</p>
 * @version 1.0
 * @author Nguyen Huu Quang	
 */
public class Article {
	
	private String url;
	private String time;
	private String title;
	private String topic;
	private String content;

	// TODO: Use StringBuilder for htmlSource, Make a constructor to set htmlSource
	private String htmlSource;
	/**
	 * Constructor of Article class
	 */
	public Article() {
		this.url = null;
		this.time = null;
		this.title = null;
		this.topic = null;
		this.content = null;
		this.htmlSource = null;
	}

	/**
	 * Constructor of Article class with parameters
	 * @param url URL of the article
	 * @param time Time of the article
	 * @param title Title of the article
	 * @param topic Topic of the article
	 * @param content Content of the article
	 */
	public Article(String url, String time, String title, String topic, String content) {
		this.url = url;
		this.time = time;
		this.title = title;
		this.topic = topic;
		this.content = content;
	}

	/**
	 * Get url of article
	 * @param _url
	 */
	public void setUrl(String _url) {
		this.url = _url;
	}
	
	/**
	 * Set time of article
	 * @param _time
	 */
	public void setTime(String _time) {
		this.time = _time;
	}
	
	/**
	 * Set title of article
	 * @param _title
	 */
	public void setTitle(String _title) {
		this.title = _title;
	}
	
	/**
	 * Set category of article
	 * @param _topic
	 */
	public void setTopic(String _topic) {
		this.topic = _topic;
	}
	
	/**
	 * Set content of article
	 * @param content
	 */
	public void setContent(String _content) {
		this.content = _content;
	}
	
	/**
	 * Get url of article
	 * @return string url of article
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * Get time of article
	 * @return string time of article
	 */

	public String getTime() {
		if (this.time == null || this.time.isEmpty()) {
			return "0000-01-01 00:00:00";
		}
		if (this.time.length() != 19) {
			return "0000-01-01 00:00:00";
		}
		return this.time;
	}

	/**
	 * Get title of article
	 * @return string title of article
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Get category of article
	 * @return string category of article
	 */
	public String getTopic() {
		return this.topic;
	}

	/**
	 * Get content of article
	 * @return string content of article
	 */
	public String getContent() {
		return this.content;
	}
	
	/**
	 * Get HTML source of article
	 * @return string HTML source of article
	 */
	public String getHtmlSource() {
		return this.htmlSource;
	}

	/**
	 * Set HTML source of article
	 * @param htmlSource
	 */
	public void setHtmlSource(String htmlSource) {
		this.htmlSource = htmlSource;
	}
}
