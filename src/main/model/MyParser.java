package src.main.model;

/**
 * Parser class for handling parsing operations.
 */
public class MyParser {
    private String url;
    
    /**
     * Public constructor
     * @param url string presentation of url
     */
    public MyParser(String url) {
        this.url = url; 
    }

    /**
     * Getter of URL
     * @return String presentation of url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Setter of URL
     * @param url String presentation of URL
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
