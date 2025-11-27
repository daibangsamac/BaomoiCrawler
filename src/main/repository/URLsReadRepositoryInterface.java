package src.main.repository;

/**
 * Interface of URL Source repository
 * <p>This interface defines the methods for accessing and manipulating browser data in the database.</p>
 * @version 1.0
 * @author Nguyen Huu Quang	
 */
public interface URLsReadRepositoryInterface {

    /**
     * Check if url exist and HTMLSource not null
     * @param url
     * @return
     */
    public boolean exists(String url);

    /**
     * Get HTMLSource of url
     * @param url
     * @return
     */
    public String getHTMLSource(String url);

}
