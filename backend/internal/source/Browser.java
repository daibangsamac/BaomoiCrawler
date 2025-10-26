package backend.internal.source;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

/**
 * Class Browser
 * <p>This class is used to browse a given URL and read its content.</p>
 * @version 1.0
 * @author Nguyen Huu Quang	
 */
public class Browser {
	
	private URL url;
	private String htmlPage;
	private String type;
	private String topic;
	//private HTMLParser htmlParser;

	/**
	 * Constructor of Browser class, check if URL is valid
	 * @param url The URL to be browsed
	 * @throws IOException If an I/O error occurs
	 */
	public Browser(String url, String type,String topic) throws IOException {
		this.url = new URL(url);
		this.type = type;
		this.topic = topic;
		try { 
			URLConnection urlCon = this.url.openConnection();	
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(urlCon.getInputStream(), StandardCharsets.UTF_8));
			StringBuilder htmlBuilder = new StringBuilder();
        	String line;
			while ((line = bufferRead.readLine()) != null) {
				 htmlBuilder.append(line);
				 htmlBuilder.append("\n");
			}
			bufferRead.close();
			this.htmlPage = htmlBuilder.toString();
		} catch (IOException e) {
			this.type = null;
			throw new IOException("Cannot connect to URL: " + url);
		}
	}
	
	/**
	 * Get the URL as a string
	 * @return String representation of the URL
	 */
	public String getUrl() {
		return url.toString();
	}

	/**
	 * Get the topic of the browser
	 * @return topic as String
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * Get the HTML content of the browsed URL
	 * @return HTML content as String
	 */
	public String getHtmlPage() {
		return htmlPage.toString();
	}

	/**
	 * Get the type of the browser (summary or article)
	 * @return type as String
	 */
	public String getType() {
		return type;
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
	public LinkedList<String> getLinksOfArticles() throws IOException {
		if (!this.type.equals("summary"))
			throw new IOException("This method is only for summary page.");

		Document doc;
		LinkedList<String> links = new LinkedList<String>();


		doc = Jsoup.parse(htmlPage);

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
	public Article parseArticle() throws IOException {
		if (!this.type.equals("article"))
			throw new IOException("This method is only for article page.");

		
		Document doc = Jsoup.parse(htmlPage);
		String url = this.getUrl();
		String time = "";
		String title = "";
		String topic = this.getTopic();
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



