package src.main.infrastructure.cache;

import java.util.HashMap;

/**
 * This is used to store local cache
 */
public class LocalCache {
    
    private HashMap<String,Boolean> HTMLSource;
    private HashMap<String,Boolean> Url;

    public LocalCache() {
         HTMLSource = new HashMap<>();
        Url = new HashMap<>();
    }

    public Boolean getUrl(String url) {
        return Url.get(url);
    }

    public Boolean getHTMLSource(String hashedSource) {
        return HTMLSource.get(hashedSource);
    }

    public void putUrl(String url) {
        Url.put(url,true);
    }

    public void putHTMLSource(String hashedSource) {
        HTMLSource.put(hashedSource, true);
    }
}
