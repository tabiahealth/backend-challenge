package health.tabia.challenge;

import java.util.ArrayList;
import java.util.List;

public class MetricList implements MetricStore {

    // It was used list to handle indexes and iterations needed to remove an
    // object
    private List<Metric> store;
    private int size;

    public MetricList(List<Metric> newStore) {
        this.store = newStore;
        this.size = newStore.size();
    }

    public int getSize() {
        return this.size;
    }

    public MetricIterator iterator() {
        MetricIterator metricIterator = new MetricIterator() {

            private int currentIndex = 0;

            @Override
            public void close() throws Exception {
                System.out.println("Closing metric iterator...");
            }

            @Override
            public boolean moveNext() {
                currentIndex += 1;
                return (currentIndex < size && store.get(currentIndex) != null);
            }

            @Override
            public void remove() {
                store.remove(currentIndex);
                size--;
            }

            @Override
            public Metric current() {
                return store.get(currentIndex);
            }

        };

        return metricIterator;
    }

    @Override
    synchronized public void insert(Metric metric) {
        if (this.store == null) {
            this.store = new ArrayList<Metric>();
        }

        this.store.add(metric);
        this.size++;
    }

    @Override
    synchronized public void removeAll(String name) {
        List<Metric> cleanedList = new ArrayList<>();
        if (this.store != null) {
            for (Metric metric : this.store) {
                if (!metric.getName().equalsIgnoreCase(name)) {
                    cleanedList.add(metric);
                }
            }
            this.store = cleanedList;
            this.size = cleanedList.size();
        }
    }

    @Override
    synchronized public MetricIterator query(String name, long from, long to) {
        MetricList filteredStore = new MetricList(new ArrayList<>());

        if ((name == null || name.equalsIgnoreCase("") && (from == 0 && to == 0)) || (from > to)) {
            return this.iterator();
        }

        for (Metric metric : this.store) {
            boolean isIncluded = false;
            if (name != null && !name.equalsIgnoreCase("") && metric.getName().equalsIgnoreCase(name)) {
                isIncluded = true;

                if (metric.getTimestamp() >= from && metric.getTimestamp() <= to) {
                    isIncluded = true;
                } else {
                    isIncluded = false;
                }
            }

            if (name.equalsIgnoreCase("")) {
                if (metric.getTimestamp() >= from && metric.getTimestamp() <= to) {
                    isIncluded = true;
                }
            }

            // If I have a signal to insert, I do it.
            if (isIncluded) {
                filteredStore.insert(metric);
            }
        }

        return filteredStore.iterator();
    }
}
