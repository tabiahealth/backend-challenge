package health.tabia.challenge;

/**
 * Basic implementation of the Metric abstraction
 */
public class Metric {
    private final String name;
    private final long timestamp;

    public Metric(String name, long timestamp) {
        this.name = name;
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
