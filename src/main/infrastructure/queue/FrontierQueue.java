package src.main.infrastructure.queue;

import java.util.LinkedList;
import java.util.Queue;

import src.main.model.Coordinator;

/**
 * Singleton class for frontier queue
 * Used to store URL need to be fetch for HTML
 * @version 1.0
 * @author Nguyen Huu Quang
 */
public class FrontierQueue {
    private final Queue<Coordinator> frontierQueue = new LinkedList<>();
    private static FrontierQueue frontierQueueInstance;

    /**
     * Private constructor of the frontier queue create a new queue
     */
    private FrontierQueue() {
        
    }

    /**
     * Get the singleton instance of Frontier queue
     * @return The frontierQueue instance
     */
    public static synchronized FrontierQueue getInstance() {
        if (frontierQueueInstance == null) {
            frontierQueueInstance = new FrontierQueue();
        }
        return frontierQueueInstance;
    }

    /**
     * Add url
     * @param url Object[] = {url, topic}
     */
    public synchronized void add(Coordinator coordinator) {
        frontierQueue.add(coordinator);
    }

    /**
     * Poll url from queue
     * @return Object[] = {url, topic}
     */
    public synchronized Coordinator poll() {
        return frontierQueue.poll();
    }

    /**
     * Return true if this collection have no elements
     * @return true if this collection have no elements
     */
    public boolean isEmpty() {
        return frontierQueue.isEmpty();
    }

    /**
     * Clear the queue
     */
    public synchronized void shutdown() {
        frontierQueue.clear();        
    }
}
