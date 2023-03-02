package health.tabia.challenge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Test;

public class MetricStoreImplTest {

    private MetricList list = new MetricList(new ArrayList<>());

    @Test
    public void testDemo() {
        assertTrue(true);
    }

    /*
     * In this test we insert a metric and verify if it is ok.
     */
    @Test
    public void testInsert() {
        list.insert(new Metric("test", 1677713468));
        assertEquals(1, list.getSize());
    }

    /*
     * In this test we remove all registers of an specific metric
     */
    @Test
    public void testRemoveAll() {
        list.insert(new Metric("test", 1677713468));
        list.removeAll("test");
        assertEquals(0, list.getSize());
    }

    /*
     * In this test we remove all registers of an specific metric and verify if
     * another metric still there
     */
    @Test
    public void testRemoveAllNameRest() {
        list.insert(new Metric("test", 1677713468));
        list.insert(new Metric("test_2", 1677713468));
        list.removeAll("test");
        assertEquals(1, list.getSize());
    }

    /*
     * In this test differents metrics are inserted and a query is made for two
     * metrics according interval.
     * after that we get the current object for each one.
     */
    @Test
    public void testQuery() {
        list.insert(new Metric("test", 1677713468));
        list.insert(new Metric("test", 1677713469));
        list.insert(new Metric("test", 1677713470));
        list.insert(new Metric("test", 1677713471));
        list.insert(new Metric("test", 1677713472));
        list.insert(new Metric("test_2", 1677713472));
        list.insert(new Metric("test", 1677713473));
        list.insert(new Metric("test_2", 1677713473));
        MetricIterator it = list.query("test", 1677713471, 1677713473);
        MetricIterator it2 = list.query("test_2", 1677713471, 1677713473);
        assertEquals(1677713471, it.current().getTimestamp());
        assertEquals(1677713472, it2.current().getTimestamp());
    }

    /*
     * In this test we validate if index is moving to next
     */
    @Test
    public void testMoveNext() {
        list.insert(new Metric("test", 1677713468));
        list.insert(new Metric("test", 1677713469));

        MetricIterator it = list.query("test", 0, 1677713473);
        it.moveNext();
        assertEquals(1677713469, it.current().getTimestamp());
        assertFalse(it.moveNext());
    }

    /*
     * In this test we remove current metric from iterator
     */
    @Test
    public void testRemove() {
        list.insert(new Metric("test", 1677713468));
        list.insert(new Metric("test", 1677713469));
        list.insert(new Metric("test", 1677713470));
        list.insert(new Metric("test", 1677713471));
        list.insert(new Metric("test", 1677713472));
        MetricIterator it = list.query("test", 1677713468, 1677713473);
        it.remove();
        assertNotEquals(1677713468, it.current().getTimestamp());
        assertEquals(1677713469, it.current().getTimestamp());
    }

    /*
     * In this test we validate current object of iterator
     */
    @Test
    public void testCurrent() {
        list.insert(new Metric("test", 1677713468));
        list.insert(new Metric("test", 1677713469));
        list.insert(new Metric("test", 1677713470));
        MetricIterator it = list.query("test", 1677713468, 1677713473);
        assertEquals(1677713468, it.current().getTimestamp());
        assertNotEquals(1677713471, it.current().getTimestamp());
    }

    // After each test, we remove all metrics
    @After
    public void tearDown() {
        list.removeAll("test");
        list.removeAll("test_2");
    }
}
