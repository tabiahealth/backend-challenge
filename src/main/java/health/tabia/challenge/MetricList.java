package health.tabia.challenge;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MetricList implements MetricStore {

    // It was used list to handle indexes and iterations needed to remove an
    // object
    private LinkedList<Metric> store;
    private int size;

    public MetricList(LinkedList<Metric> newStore) {
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
            this.store = new LinkedList<Metric>();
        }

        this.store.add(metric);
        this.size++;
    }

    @Override
    synchronized public void removeAll(String name) {
        if (this.store != null) {
            this.store.removeIf(metric -> metric.getName().equalsIgnoreCase(name));
            this.size = this.store.size();
        }
    }

    @Override
    synchronized public MetricIterator query(String name, long from, long to) {
        MetricList filteredStore = new MetricList(new LinkedList<>());

        // when name is null or empty and intervals are 0, return original iterator
        if ((name == null || name.equalsIgnoreCase("") && (from == 0 && to == 0)) || (from > to)) {
            return this.iterator();
        }

        filteredStore.store = new LinkedList<>(this.store.stream()
                .filter(metric -> metric.getName().equalsIgnoreCase(name) && metric.getTimestamp() >= from
                        && metric.getTimestamp() <= to)
                .collect(Collectors.toList()));

        return filteredStore.iterator();
    }
}
