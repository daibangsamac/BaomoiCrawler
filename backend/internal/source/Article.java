package backend.internal.source;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
	
	/**
	 * Constructor of Article class
	 */
	public Article() {
		this.url = null;
		this.time = null;
		this.title = null;
		this.topic = null;
		this.content = null;
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
	 * Print all information of the article
	 * For debugging purpose
	 */
	public void printArticle() {
		System.out.println("url         : " + this.url);
		System.out.println("time        : " + this.time);
		System.out.println("title       : " + this.title);
		System.out.println("topic    	: " + this.topic);
		System.out.println("content     : " + this.content);
	}

	/**
	 * Create JSON file for the article
	 */
	public void createJson() {
		String pathFolder = "./output/" + this.topic;
		File dir = new File(pathFolder);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		String path = "./output/" + this.topic + "/";
		Map<String, Object> data = new HashMap<>();
        data.put("url", this.url);
        data.put("time", this.time);
        data.put("title", this.title);
		data.put("topic", this.topic);
		data.put("content", this.content);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(path + this.title.replaceAll("[\\\\/:*?\"<>|]", "_") + ".json")) {
            gson.toJson(data, writer); 
            System.out.println("Created JSON for article: " + this.title);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
	
	
}
