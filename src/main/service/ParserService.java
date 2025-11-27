package src.main.service;

import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import src.main.model.Article;
import src.main.model.MyParser;
import src.main.repository.MySQLRepository;
import src.main.repository.PostgreSQLURLRepository;

/**
 * Service class for handling parsing operations.
 */
public class ParserService {

	public MyParser createParser(String url) {
		return new MyParser(url);
	}

	private String getHTMLSource(MyParser parser) {
		PostgreSQLURLRepository repo = new PostgreSQLURLRepository();
		return repo.getHTMLSource(parser.getUrl());
	}

	private Instant getLastFetch(MyParser parser) {
		MySQLRepository repo = new MySQLRepository();
		return repo.getLastFetch(parser.getUrl());
	}

	public void parseAndStore(MyParser parser) {
		String source = getHTMLSource(parser);
		String url = parser.getUrl();
		int id = -1;
		String og_url = new String();
		String author = new String();
		String title = new String();
		Instant publish_date = Instant.now();
		String topic = new String();
		String body = new String();
		String keywords = new String();

		Instant lastFetched = getLastFetch(parser);
		Instant now = Instant.now();

		if (lastFetched != null) {
			Duration duration = Duration.between(lastFetched, now);
			double hours = duration.toMinutes() / 60.0;
			if (hours <= 24) {
				return;
			}
		}

		Document doc = Jsoup.parse(source);
		Element script = doc.getElementById("__NEXT_DATA__");
		String json = script.data();
		Gson gson = new Gson();
		JsonObject root = gson.fromJson(json, JsonObject.class);
		JsonObject data = root
			.getAsJsonObject("props")
			.getAsJsonObject("pageProps")
			.getAsJsonObject("resp")
			.getAsJsonObject("data");
		JsonObject meta = data
			.getAsJsonObject("head")
			.getAsJsonObject("meta");
		JsonObject content = data
			.getAsJsonObject("content");

		// id
		id = content.get("id").getAsInt();

		// og_url
		og_url = meta.get("ogUrl").getAsString();

		// author
		JsonArray jsonLd= data
			.getAsJsonObject("head")
			.getAsJsonArray("jsonLd");
		for (JsonElement element : jsonLd) {
            JsonObject obj = element.getAsJsonObject();
			try {
				author = obj
					.getAsJsonObject("author")
					.get("name").getAsString();
			} catch (Exception e) {
				continue;
			}
		}

		//	title
		title = meta.get("ogTitle").getAsString();

		// publish date
		String temp = meta.get("articlePublishedTime").getAsString();
		OffsetDateTime odt = OffsetDateTime.parse(temp);
		publish_date = odt.toInstant();

		// topic
		topic = data.get("categoryShortUrl").getAsString();

		// keywords
		keywords = meta.get("keywords").getAsString();
		String[] keys = keywords.split(",");
		keywords = gson.toJson(keys);

		// Body
		JsonArray bodys = content.getAsJsonArray("bodys");
		StringBuilder bodyBuilder = new StringBuilder();
		for (JsonElement e : bodys) {
			JsonObject obj = e.getAsJsonObject();
			String type = obj.get("type").getAsString();
			if ("text".equals(type)) {
				bodyBuilder.append(obj.get("content").getAsString()).append("\n\n");
			}
		}
		body = bodyBuilder.toString();
		
		Article article = new Article(url, id, og_url, author, title, publish_date, topic, body, keywords);
		MySQLRepository repo = new MySQLRepository();
		repo.upsert(article); 
	}

	public LinkedList<String> parseAndGetURLs(MyParser parser) {
		String source = getHTMLSource(parser);
		String post_url = "https://baomoi.com";
		Document doc;
		LinkedList<String> links = new LinkedList<String>();

		doc = Jsoup.parse(source);
		Elements allElements = doc.select("div.bm-card-header");
		for (Element div : allElements) {
			// Get the links of articles
			Elements articleLink = div.select("a");
			if (articleLink != null) {
				String href = articleLink.attr("href");
				links.add(post_url + href);
			}
		}
		return links;
	}
}
