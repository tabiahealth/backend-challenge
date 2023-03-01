package health.tabia.challenge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class MetricStoreImplTest {

    private MetricList list = new MetricList(new ArrayList<>());

    @Test
    public void testDemo() {
        assertTrue(true);
    }

    @Test
    public void testInsert() {
        assertEquals(0, list.getSize());
        list.insert(new Metric("teste", 1677713468));
        assertEquals(1, list.getSize());
    }
}
