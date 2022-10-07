package health.tabia.challenge;

/**
 * An iterator over a metric collection
 */
public interface MetricIterator extends AutoCloseable {
    /**
     * Move the iterator to the next metric, if any.
     * @return false if iterator has reached the end, true otherwise
     */
    boolean moveNext();

    /**
     * Get the current metric
     * @return the metric instance
     * @throws IllegalStateException if {@link #moveNext} was never called
     *                               or its last result was {@code false}.
     */
    Metric current();

    /**
     * Remove the current metric from the store
     */
    void remove();
}
