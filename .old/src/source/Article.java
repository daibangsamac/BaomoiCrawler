package source;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.JsonObject;


public class Article {
	
	private String url;
	private String time;
	private String title;
	private String category;
	private String description;
	private String content;
	private String[] keyWords;
	private ArrayList<JsonObject> trends;
	
	public Article() {
		this.url = null;
		this.time = null;
		this.title = null;
		this.category = null;
		this.description = null;
		this.content = null;
		this.trends = new ArrayList<JsonObject>();
	}
	
	/**
	 * Lấy url của bài báo
	 * @param link
	 */
	public void setUrl(String link) {
		this.url = link;
	}
	
	/**
	 * Lấy thời gian bài báo
	 * @param html
	 * @return Xâu biểu diễn thời gian
	 */
	public void setTime(String html) {
		
		if (html == null) return;
		
		String time;
		time = html.substring(html.indexOf("class=\"time\"") + "class=\"time\"".length() + 1);
		time = time.substring(0, time.indexOf("</span>"));
		this.time = time;
	}
	
	/**
	 * Lấy title bài báo
	 * @param html
	 * @return Xâu title
	 */
	public void setTitle(String html) {
		
		if (html == null) return;
		
		String title;
		Document doc;
		doc = Jsoup.parse(html);
		title = doc.text();
		title = title.replaceAll("\"", "'");
		this.title = title;
	}
	
	/**
	 * Lấy category bài báo
	 * @param html
	 * @return Xâu biểu diễn categoty
	 */
	public void setCategory(String html) {
		
		if (html == null) return;
		
		String category;
		Document doc;
		int st = html.indexOf("<span>");
		int fn = html.indexOf("</span") + 7;
		category = html.substring(st,  fn);
		doc = Jsoup.parse(category);
		category = doc.text();
		this.category = category;
	}
	
	/**
	 * Lấy mô tả bài báo
	 * @param html
	 * @return Xâu nội dung mô tả
	 */
	public void setDescription(String html) {
		
		if (html == null) return;
		
		String description;
		Document doc;
		doc = Jsoup.parse(html);
		description = doc.text();
		description = description.replaceAll("\"", "'");
		this.description = description;
	}
	
	/**
	 * Lấy nội dung bài báo
	 * @param html
	 * @return Xâu nội dung bài báo
	 */
	public void setContent(String html) {
		
		if (html == null) return;
		
		String content;
		content = html.replaceAll("\"", "'");
		this.content = content;
	}
	
	/**
	 * Lấy các key word của bài báo
	 * @param html
	 * @return màng String chưa các KeyWord
	 */
	public void setKeyWord(String html) {
		
		if (html == null) return;
		
		Document doc;
		String keyWord = "";
		String[] keyWords;
		
		doc = Jsoup.parse(html);
		Elements keys = doc.select("a[href]");
		
		for (Element key : keys)
			keyWord = keyWord + key.text() + ":";
			
		keyWords = keyWord.split(":");
		
		this.keyWords = keyWords;
	}
	
	/**
	 * Lấy các từ biểu thị xu hướng đọc
	 * @param html
	 * @param source
	 * @return mảng String các từ biểu hiện xu hướng và weith của chúng
	 * @throws IOException
	 */
	public void setTrends(String html, Web web) throws IOException {
		
		if (html == null) return;
		
		Document doc;
		String word = "", weith;
		
		while (html.indexOf("tagCloud") != -1)
		{
			weith = "";
			doc = Jsoup.parse(html);
			
			word = doc.text();
			weith = weith + html.charAt(html.indexOf("tagCloud") + "tagCloud_".length());
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("word", word);
			jsonObject.addProperty("weith", weith);
			trends.add(jsonObject);
		
			html = web.getReader().readLine();
		}
	}
	
	public String getUrl() {
		return this.url;
	}
	public String getTime() {
		return this.time;
	}
	public String getTitle() {
		return this.title;
	}
	public String getCategory() {
		return this.category;
	}
	public String getDescription() {
		return this.description;
	}
	public String getContent() {
		return this.content;
	}
	public String[] getKeyWords() {
		return this.keyWords;
	}
	public ArrayList<JsonObject> getTrends() {
		return this.trends;
	}
	
	public void printArticle() {
		System.out.println("url         : " + this.url);
		System.out.println("time        : " + this.time);
		System.out.println("title       : " + this.title);
		System.out.println("category    : " + this.category);
		System.out.println("description : " + this.description);
		System.out.println("content     : " + this.content);
		System.out.println("key word    : ");
		for (String keyWord : this.keyWords)
			System.out.println("\t" + keyWord);
		System.out.println("trends      :");
		for (JsonObject word : trends)
			System.out.println("\t" + word.toString());
	}
}
