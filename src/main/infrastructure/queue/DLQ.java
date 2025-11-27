package src.main.infrastructure.queue;

import java.util.LinkedList;
import java.util.Queue;

import src.main.model.Coordinator;

/**
 * Singleton class for DLQ 
 * Used to store URL need to be fetch again
 * @version 1.0
 * @author Nguyen Huu Quang
 */
public class DLQ {
    private final Queue<Coordinator> DLQ = new LinkedList<>();
    private static DLQ DLQInstance;

    /**
     * Private constructor of the DLQ create a new queue
     */
    private DLQ() {
        
    }

    /**
     * Get the singleton instance of DLQ
     * @return The DLQ instance
     */
    public static synchronized DLQ getInstance() {
        if (DLQInstance == null) {
            DLQInstance = new DLQ();
        }
        return DLQInstance;
    }

    /**
     * Add url
     * @param url Object[] = {url, topic}
     */
    public synchronized void add(Coordinator coordinator) {
        DLQ.add(coordinator);
    }

    /**
     * Poll url from queue
     * @return Object[] = {url, topic}
     */
    public synchronized Coordinator poll() {
        return DLQ.poll();
    }

    /**
     * Return true if this collection have no elements
     * @return true if this collection have no elements
     */
    public boolean isEmpty() {
        return DLQ.isEmpty();
    }

    /**
     * Clear the queue
     */
    public synchronized void shutdown() {
        DLQ.clear();        
    }
}
