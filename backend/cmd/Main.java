package backend.cmd;

import java.io.IOException;

import backend.internal.source.Crawler;

public class Main {
    public static void main(String[] args) {
		
		Crawler theGioiCrawler;
		theGioiCrawler = new Crawler("https://baomoi.com/the-gioi","the-gioi");
		
		try {
			theGioiCrawler.crawl();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("The-gioi Articles crawling: DONE!");
		theGioiCrawler.createJson();

		
	}
}
