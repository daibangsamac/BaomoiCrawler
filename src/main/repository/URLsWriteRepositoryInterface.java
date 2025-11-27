package src.main.repository;

import src.main.model.Article;

public interface URLsWriteRepositoryInterface {
    
    /**
     * Insert if not exist else update
     * @param article 
     */
    public void upsert(Article article);

    /**
     * Delete bt url
     * @param url
     */
    public void delete(String url);
    
}
