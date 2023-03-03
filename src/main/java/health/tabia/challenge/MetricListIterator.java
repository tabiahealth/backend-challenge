package health.tabia.challenge;

import java.util.List;

public class MetricListIterator implements MetricIterator {

    private int currentIndex;
    private List<Metric> list;

    public MetricListIterator(List<Metric> list) {
        this.currentIndex = 0;
        this.list = list;
    }

    @Override
    public void close() throws Exception {
        System.out.println("Closing...");
    }

    @Override
    public boolean moveNext() {
        currentIndex++;
        return currentIndex < list.size();
    }

    @Override
    public Metric current() {
        return list.get(currentIndex);
    }

    @Override
    public void remove() {
        list.remove(currentIndex);
    }

}
