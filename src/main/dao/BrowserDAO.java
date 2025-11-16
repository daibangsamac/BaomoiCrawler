package src.main.dao;


/**
 * Interface BrowserDAO
 * <p>This interface defines the methods for accessing and manipulating browser data in the database.</p>
 * @version 1.0
 * @author Nguyen Huu Quang	
 */
@SuppressWarnings("unused")
public interface BrowserDAO {
    public void insertHTMLSourceToDB(String url, String htmlSource);
    public void getHTMLSourceByURLFromDB(String url);
    public void getHTMLSourceHashedByURLFromDB(String url); 
    public void updateHTMLSourceByURLFromDB(String url, String htmlSource);
    public void deleteHTMLSourceByURLFromDB(String url);
    public boolean isHTMLSourceExistInDB(String htmlSource);
}
