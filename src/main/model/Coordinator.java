package src.main.model;

/**
 * Class Coordinator
 * <p>This class is used to browse a given URL and read its content.</p>
 * @version 1.0
 * @author Nguyen Huu Quang	
 */
public class Coordinator {
    
    private String url;
    private Stat status;
    private final int MAX_ATTEMPT = 4; 
    private int attempt = 0;
    private String type;
    /**
     * Enum of status 
     */
    public static enum Stat{
        FETCHING,
        PARSING,
        DLQ,
        CANCEL
    }

    /**
     * Constructor for coordinator class
     * Require 3 params to make the coordinator class
     * @param url URL to website need to be fetch
     * @param topic topic of the URL
     * @param status status of the url
     */
    public Coordinator(String url,String status, String type) {
        this.url = url;
        try {
            this.status = Stat.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status invalid (FETCHING, PARSING, DLQ, CANCEL");
        }
        this.type = type;
    }

    /**
     * URL getter
     * @return String of URL
     */
    public String getURL() {
        return this.url;
    };

    /**
     * URL setter
     * @param url String of URL
     */
    public void setURL(String url) {
        this.url = url;
    }

    /**
     * Status getter
     * @return String of status
     */
    public String getStatus() {
        return this.status.toString();
    }

    /**
     * Status setter
     * @param status String of status
     */
    public void setStatus(String status) {
        try {
            this.status = Stat.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status invalid (FETCHING, PARSING, DLQ, CANCEL");
        }
    }

    /**
     * Type getter
     * @return String presentation of type of an url
     */
    public String getType() {
        return type;
    }

    /**
     * Type setter
     * @param type String presentation of type of an url
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Boolean function to see if coordinator can fetch HTML or not
     * @return true if coordinator can attempt to request HTML again, else return false
     */
    public boolean attemptable() {
        if (attempt <= MAX_ATTEMPT)
            return true;
        return false;
    }

    /**
     * Increase attempt fetching HTML of the coordinator by one
     */
    public void increaseAttempt() {
        this.attempt = this.attempt + 1;
    }
}
