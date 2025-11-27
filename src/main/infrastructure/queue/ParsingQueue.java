package src.main.infrastructure.queue;

import java.util.LinkedList;
import java.util.Queue;

import src.main.model.Coordinator;

/**
 * Singleton class for parsing queue
 * Used to store URL need to be parse 
 * @version 1.0
 * @author Nguyen Huu Quang
 */
public class ParsingQueue {
    private final Queue<Coordinator> parsingQueue = new LinkedList<>();
    private static ParsingQueue parsingQueueInstance;

    /**
     * Private constructor of the parsing queue create a new queue
     */
    private ParsingQueue() {
        
    }

    /**
     * Get the singleton instance of Parsing queue
     * @return The parsingQueue instance
     */
    public static synchronized ParsingQueue getInstance() {
        if (parsingQueueInstance == null) {
            parsingQueueInstance = new ParsingQueue();
        }
        return parsingQueueInstance;
    }

    /**
     * Add url
     * @param url Object[] = {url, topic}
     */
    public synchronized void add(Coordinator coordinator) {
        parsingQueue.add(coordinator);
    }

    /**
     * Poll url from queue
     * @return Object[] = {url, topic}
     */
    public synchronized Coordinator poll() {
        return parsingQueue.poll();
    }

    /**
     * Return true if this collection have no elements
     * @return true if this collection have no elements
     */
    public boolean isEmpty() {
        return parsingQueue.isEmpty();
    }

    /**
     * Clear the queue
     */
    public synchronized void shutdown() {
        parsingQueue.clear();        
    }
}
