package backend.cmd;

import java.io.IOException;
import java.util.LinkedList;

import backend.internal.source.Crawler;

/**
 * Main class
 * <p>This is the main class that runs the crawlers.</p>
 * <p>It creates multiple Crawler instances for the each topic and starts the crawling process.</p>
 * @version 1.0
 * @author Nguyen Huu Quang
 */
public class Main {
	private static LinkedList<Object[]> pages = new LinkedList<>();
	/** 
	 * Main method
	 * This run the application by default.
	 * @param args Command line arguments
	 * @throws IOException
	*/
    public static void main(String[] args) {
		// TODO: Handle command line arguments
		// TODO: Create a cli-based interface

		// Preparation
		prepare();
		// For each topic, create a crawler and start crawling
		for (Object[] page :pages) {
			String url = (String) page[0];
			String topic = (String) page[1];
			Crawler Crawler = new Crawler(url, topic);
			try {
				Crawler.crawl();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			System.out.println("Crawler " + topic + ": DONE!");
			Crawler.createJson();
		}
	}

	/**
	 * Preparation 
	 */
	private static void prepare() {
		//pages.add(new Object[] {"https://baomoi.com/the-gioi","the-gioi"});
		pages.add(new Object[] {"https://baomoi.com/xa-hoi","xa-hoi"});
		pages.add(new Object[] {"https://baomoi.com/van-hoa","van-hoa"});
		//pages.add(new Object[] {"https://baomoi.com/kinh-te","kinh-te"});
		//pages.add(new Object[] {"https://baomoi.com/giao-duc","giao-duc"});
	}
}
