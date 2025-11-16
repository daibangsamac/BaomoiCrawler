package src.main.model;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;



/**
 * Class Browser
 * <p>This class is used to browse a given URL and read its content.</p>
 * @version 1.0
 * @author Nguyen Huu Quang	
 */
public class Browser {
	
	// URL to be browsed
	private URL url;

	// HTML content of the browsed URL
	private String htmlSource;
	private String type;
	private String topic;

	/**
	 * Default constructor of Browser class
	 */
	public Browser() {
		this.url = null;
		this.htmlSource = null;
		this.type = null;
		this.topic = null;
	}

	/**
	 * Constructor of Browser class, check if URL is valid
	 * @param url The URL to be browsed
	 * @throws IOException If an I/O error occurs
	 * @throws InterruptedException If the sleep is interrupted
	 */
	public Browser(String url, String type,String topic) throws MalformedURLException {
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			System.err.println("Invalid URL: " + url);
			this.type = null;
			this.topic = null;
			throw e;
		}
		this.type = type;
		this.topic = topic;
	}

	/**
	 * Get the URL as a string
	 * @return String representation of the URL
	 */
	public URL getUrl() {
		return url;
	}

	/**
	 * Set the URL to be browsed
	 * @param url The URL as a string
	 * @throws MalformedURLException If the URL is invalid
	 */
	public void setUrl(String url) throws MalformedURLException {
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			System.err.println("Invalid URL: " + url);
			this.type = null;
			this.topic = null;
			throw e;
		}
	}

	/**
	 * Get the topic of the browser
	 * @return topic as String
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * Set the topic of the browser
	 * @param topic The topic as a string
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}

	/**
	 * Get the HTML content of the browsed URL
	 * @return HTML content as String
	 */
	public String getHtmlSource() {
		if (htmlSource == null)
			return null;
		return htmlSource.isEmpty() ? null : htmlSource;
	}

	/**
	 * Set the HTML content of the browsed URL
	 * @param htmlSource The HTML content as a string
	 */
	public void setHtmlSource(String htmlSource) {
		this.htmlSource = htmlSource;
	}

	/**
	 * Get the type of the browser (summary or article)
	 * @return type as String
	 */
	public String getType() {
		return type;
	}

	/**
	 * Set the type of the browser (summary or article)
	 * @param type The type as a string
	 */
	public void setType(String type) {
		this.type = type;
	}
}



