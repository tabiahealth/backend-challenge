package health.tabia.challenge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class MetricList implements MetricStore {

    private HashMap<String, List<Metric>> store;
    private boolean isWriting = false;

    public MetricList(HashMap<String, List<Metric>> newStore) {
        this.store = newStore;
    }

    public HashMap<String, List<Metric>> getStore() {
        return this.store;
    }

    @Override
    public void insert(Metric metric) {
        if (isWriting) {
            return;
        }
        isWriting = true;
        if (this.store == null) {
            this.store = new HashMap<String, List<Metric>>();
        }

        if (this.store.containsKey(metric.getName())) {
            this.store.get(metric.getName()).add(metric);
        } else {
            List<Metric> list = new ArrayList<>();
            list.add(metric);
            this.store.put(metric.getName(), list);
        }

        isWriting = false;
    }

    @Override
    public void removeAll(String name) {
        if (isWriting) {
            return;
        }
        isWriting = true;
        if (this.store != null) {
            this.store.remove(name);
        }
        isWriting = false;
    }

    @Override
    public MetricIterator query(String name, long from, long to) {
        List<Metric> list = this.store.get(name).stream()
                .filter(metric -> metric.getName().equalsIgnoreCase(name) && metric.getTimestamp() >= from
                        && metric.getTimestamp() <= to)
                .collect(Collectors.toList());

        return new MetricListIterator(list);

    }
}
