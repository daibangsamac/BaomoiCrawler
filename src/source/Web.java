package source;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class Web {
	
	private URL url;
	private BufferedReader bufferRead;
	
	public Web(String url) throws IOException {
		
		this.url = new URL(url);
		URLConnection urlCon = this.url.openConnection();
		this.bufferRead = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
	}
	
	public String getUrl() {
		return url.toString();
	}
	public BufferedReader getReader() {
		return this.bufferRead;
	}
	
	public String readLine() throws IOException {
		return this.bufferRead.readLine();
	}
	
public LinkedList<String> getLinksOfArticles() throws IOException {
		
		String htmlLine;
		Document doc;
		LinkedList<String> links = new LinkedList<String>();
        
        while (true)
        {
        	htmlLine = this.bufferRead.readLine();
        	if (htmlLine == null) break;
        	if (htmlLine.indexOf("sprite clickable comment") != -1)
        	{
        		doc = Jsoup.parse(htmlLine);
        		Elements element = doc.select("a[href]");
        		links.add(element.attr("abs:href"));
        	}
        }
        
        return links;
	}

	public String checkLineOfTime(String lineHtml, String time) throws IOException{

		if ((lineHtml.indexOf("class=\"time\"") != -1) && (time == null))
			return lineHtml;
		return null;
	}
	public String checkLineOfTitle(String lineHtml, String title) throws IOException{

		if ((lineHtml.indexOf("class=\"title\"") != -1) && (title == null))
			return lineHtml;
		return null;
	}
	public String checkLineOfCategory(String lineHtml, String category) throws IOException{

		if ((lineHtml.indexOf("page-header article-header") != -1) && (category == null))
			return lineHtml;
		return null;
	}
	public String checkLineOfDescription(String lineHtml, String description) throws IOException{

		if ((lineHtml.indexOf("class=\"summary\"") != -1) && (description == null))
			return lineHtml;
		return null;
	}
	public String checkLineOfContent(String lineHtml, String content) throws IOException{

		if ((lineHtml.indexOf("itemprop=\"articleBody\"") != -1) && (content == null))
		{
			lineHtml = this.bufferRead.readLine();
			lineHtml = this.bufferRead.readLine();
			return lineHtml;
		}
		return null;
	}
	public String checkLineOfKeyWords(String lineHtml) throws IOException{
		
		if (lineHtml.indexOf("class=\"keywords\"") != -1)
		{
			lineHtml = this.bufferRead.readLine();
			lineHtml = this.bufferRead.readLine();
			return lineHtml;
		}
		return null;
	}
	public String checkLineOfTrends(String lineHtml) throws IOException{

		if (lineHtml.indexOf("<h4>Xu hướng đọc</h4>") != -1)
		{
			lineHtml = this.bufferRead.readLine();
			return lineHtml;
		}
		return null;
	}
}



