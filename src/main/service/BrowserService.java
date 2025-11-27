package src.main.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import src.main.model.Browser;
import src.main.repository.PostgreSQLURLRepository;

/**
 * Service class for handling browser operations
 */
public class BrowserService {
	PostgreSQLURLRepository repo;
    
	public BrowserService(PostgreSQLURLRepository repo) {
		this.repo = repo;
	}
	/**
     * Create a Browser object with the given URL
     * @param url The URL to be browsed
     * @return Browser object
     */
    public Browser createBrowser(String url) {
        Browser browser = new Browser(url);
        return browser;
    }
    
    /**
	 * Fetch the content of the URL for HTML Source
	 * @throws IOException
	 */
	public boolean request(Browser browser) throws InterruptedException{
		String url = browser.getUrl();
		int attempt = 1;
		final int MAX_ATTEMPT = 5;
		try {
				URL urlInURL = new URL(url);
				URLConnection urlCon = urlInURL.openConnection();	
				BufferedReader bufferRead = new BufferedReader(new InputStreamReader(urlCon.getInputStream(), StandardCharsets.UTF_8));
				StringBuilder htmlBuilder = new StringBuilder();
				String line;
				while ((line = bufferRead.readLine()) != null) {
					htmlBuilder.append(line);
				}
				bufferRead.close();
				String HTMLSource = htmlBuilder.toString();
				if (HTMLSource == null || HTMLSource.equals("")) {
					try {
						TimeUnit.MILLISECONDS.sleep(200);
					} catch (InterruptedException e) {
						throw new InterruptedException("Sleep interrupted while retrying to fetch URL: " + browser.getUrl());
					}
					throw new IOException();
				}
				repo.insert(url, HTMLSource);
			} catch (IOException e) {
				System.out.println("Failed to request "+ url + ", attempt " + attempt + " out of " + MAX_ATTEMPT);
				attempt = attempt + 1;
			}
		if (repo.exists(url)) return true;
		while (attempt <= MAX_ATTEMPT) {
			try {
				URL urlInURL = new URL(url);
				URLConnection urlCon = urlInURL.openConnection();	
				BufferedReader bufferRead = new BufferedReader(new InputStreamReader(urlCon.getInputStream(), StandardCharsets.UTF_8));
				StringBuilder htmlBuilder = new StringBuilder();
				String line;
				while ((line = bufferRead.readLine()) != null) {
					htmlBuilder.append(line);
				}
				bufferRead.close();
				String HTMLSource = htmlBuilder.toString();
				if (HTMLSource == null || HTMLSource.equals("")) {
					try {
						TimeUnit.MILLISECONDS.sleep(200);
					} catch (InterruptedException e) {
						throw new InterruptedException("Sleep interrupted while retrying to fetch URL: " + browser.getUrl());
					}
					throw new IOException();
				}
				repo.insert(url, HTMLSource);
			} catch (IOException e) {
				System.out.println("Failed to request "+ url + ", attempt " + attempt + " out of " + MAX_ATTEMPT);
				attempt = attempt + 1;
			}
		}
		return repo.exists(url);
	}
}
