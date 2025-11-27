package src.main.model;

/**
 * Class Browser
 * <p> This class is used to fetch a given URL and store it contents to DB.</p>
 * @version 2.0
 * @author Nguyen Huu Quang	
 */
public class Browser {
	
	private String url;

	/**
	 * Constructor for class Browser
	 * @param url url need to be fetched
	 */
	public Browser (String url) {
		this.url = url;
	}

	/**
	 * Get the URL as a string
	 * @return String representation of the URL
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Set the URL to be browsed
	 * @param url The URL as a string
	 */
	public void setUrl(String url) {
		this.url = url;
	}

}



