package com.skorobahatko;

import com.skorobahatko.ArrayList;
import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

import static org.junit.Assert.*;

public class ArrayListTest {

    @Test
    public void testSizeWhenSizeIs0() throws Exception {
        final Collection<Integer> testInstance = new ArrayList<>();
        assertEquals(0, testInstance.size());
    }

    @Test
    public void testIsEmptyWhenEmpty() throws Exception {
        final Collection<Integer> testInstance = new ArrayList<>();
        assertTrue(testInstance.isEmpty());
    }

    @Test
    public void testToArrayWhenInputArrayHaveSizeOne() throws Exception {
        final Collection<Integer> testInstance = new ArrayList<>();
        testInstance.add(1);
        testInstance.add(2);
        testInstance.add(3);

        final Integer[] input = new Integer[1];

        final Integer[] result = testInstance.toArray(input);
        assertNotEquals(input, result);
        assertEquals((Integer)1, result[0]);
        assertEquals((Integer)2, result[1]);
        assertEquals((Integer)3, result[2]);
        assertEquals(3, result.length);
    }

    @Test
    public void testToArrayWhenInputArrayHaveCorrectSize() throws Exception {
        final Collection<Integer> testInstance = new ArrayList<>();
        testInstance.add(1);
        testInstance.add(2);
        testInstance.add(3);

        final Integer[] input = new Integer[3];

        final Integer[] result = testInstance.toArray(input);
        assertEquals(input, result);
    }

    @Test
    public void testContains() throws Exception {
        final Collection<Integer> testInstance = new ArrayList<>();
        testInstance.add(1);
        testInstance.add(2);

        assertTrue(testInstance.contains(1));
        assertFalse(testInstance.contains(0));
    }

    @Test
    public void testAdd() throws Exception {
        final Collection<Integer> testInstance = new ArrayList<>();
        testInstance.add(1);
        testInstance.add(1);

        assertEquals(2, testInstance.size());
        assertFalse(testInstance.isEmpty());
    }

    @Test
    public void testRemoveFirstElement() throws Exception {
        final Collection<Integer> testInstance = new ArrayList<>();
        testInstance.add(1);
        testInstance.add(2);
        testInstance.remove(1);

        assertEquals(1, testInstance.size());
        assertFalse(testInstance.isEmpty());
    }

    @Test
    public void testRemoveLastElement() throws Exception {
        final Collection<Integer> testInstance = new ArrayList<>();
        testInstance.add(1);
        testInstance.add(2);
        testInstance.remove(2);

        assertEquals(1, testInstance.size());
        assertFalse(testInstance.isEmpty());
    }

    @Test
    public void testContainsAll() throws Exception {
        final Collection<Integer> testInstance = new ArrayList<>();
        final Collection<Integer> testInstance2 = new ArrayList<>();
        testInstance.add(1);
        testInstance.add(2);

        testInstance2.add(2);
        testInstance2.add(1);

        assertTrue(testInstance.containsAll(testInstance2));
    }

    @Test
    public void testAddAll() throws Exception {
        final Collection<Integer> testInstance = new ArrayList<>();
        testInstance.add(1);
        testInstance.add(2);

        testInstance.add(3);
        testInstance.add(4);

        assertTrue(testInstance.contains(3));
        assertTrue(testInstance.contains(4));
    }

    @Test
    public void testRemoveAll() throws Exception {
        final Collection<Integer> testInstance = new ArrayList<>();
        final Collection<Integer> testInstance2 = new ArrayList<>();
        testInstance.add(1);
        testInstance.add(2);

        testInstance2.add(2);
        testInstance2.add(3);

        testInstance.removeAll(testInstance2);

        assertEquals(1, testInstance.size());
        assertTrue(testInstance.contains(1));
    }

    @Test
    public void testRetainAll() throws Exception {
        final Collection<Integer> testInstance = new ArrayList<>();
        final Collection<Integer> testInstance2 = new ArrayList<>();
        testInstance.add(1);
        testInstance.add(2);

        testInstance2.add(2);
        testInstance2.add(3);

        testInstance.retainAll(testInstance2);

        assertEquals(1, testInstance.size());
        assertTrue(testInstance.contains(2));
    }

    @Test
    public void testClear() throws Exception {
        final Collection<Integer> testInstance = new ArrayList<>();
        testInstance.add(1);
        testInstance.add(1);

        testInstance.clear();

        assertTrue(testInstance.isEmpty());
        assertEquals(0, testInstance.size());
    }

    @Test
    public void testRemoveBeforeNext() throws Exception {
        final Collection<Integer> testInstance = new ArrayList<>();
        testInstance.add(2);

        final Iterator<Integer> iter = testInstance.iterator();
        try {
            iter.remove();
            fail("remove do not throw the Exception when called before next");
        } catch (final IllegalStateException e) {}
    }

    @Test
    public void testNextOnEmptyCollection() throws Exception {
        final Collection<Integer> testInstance = new ArrayList<>();
        testInstance.add(1);
        testInstance.add(2);

        final Iterator<Integer> iter = testInstance.iterator();
        iter.next();
        iter.remove();
        iter.next();
        iter.remove();
        try {
            iter.next();
            fail("next do not throw the Exception when no more ellements");
        } catch (final java.util.NoSuchElementException e) {}
    }

    @Test
    public void testHasPreviouseWhenIteratorAtTheEndOfTheCollection() {
        final ArrayList<Integer> testInstance = new ArrayList<>();
        testInstance.add(1);
        testInstance.add(2);

        final ListIterator<Integer> listIterator = testInstance.listIterator();
        listIterator.next();

        assertTrue(listIterator.hasPrevious());
    }

    @Test
    public void testPreviouseIndexWhenItEqualsTo1() {
        final ArrayList<Integer> testInstance = new ArrayList<>();
        testInstance.add(1);
        testInstance.add(1);

        final ListIterator<Integer> listIterator = testInstance.listIterator();
        listIterator.next();
        listIterator.next();

        assertEquals(1, listIterator.previousIndex());
    }

    @Test
    public void testSetWhenNeitherNextNorPreviousHaveBeenCalled() {
        final ArrayList<Integer> testInstance = new ArrayList<>();
        testInstance.add(1);

        final ListIterator<Integer> listIterator = testInstance.listIterator();

        try {
            listIterator.set(null);
            fail("set method do not throw IllegalStateException the if neither next nor previous have been called");
        } catch (final IllegalStateException e){}
    }

    @Test
    public void testSet() {
        final ArrayList<Integer> testInstance = new ArrayList<>();
        testInstance.add(1);

        final ListIterator<Integer> listIterator = testInstance.listIterator();
        listIterator.next();
        listIterator.set(2);
        assertEquals((Integer)2, testInstance.get(0));
    }

    @Test
    public void testPreviouseOnCollectionWithOneElement() {
        final ArrayList<Integer> testInstance = new ArrayList<>();
        testInstance.add(1);

        final ListIterator<Integer> listIterator = testInstance.listIterator();
        final Integer next = listIterator.next();
        final Integer previous = listIterator.previous();

        assertEquals(next, previous);
    }

    @Test
    public void testPreviouseIndex() {
        final ArrayList<Integer> testInstance = new ArrayList<>();
        testInstance.add(1);

        final ListIterator<Integer> listIterator = testInstance.listIterator();
        listIterator.next();

        assertEquals(0, listIterator.previousIndex());
    }

    @Test
    public void testPreviouseIndexWhenEmptyCollection() {
        final ArrayList<Integer> testInstance = new ArrayList<>();

        final ListIterator<Integer> listIterator = testInstance.listIterator();

        assertEquals(-1, listIterator.previousIndex());
    }

    @Test
    public void testPreviouseWhenEmptyCollection() {
        final ArrayList<Integer> testInstance = new ArrayList<>();

        final ListIterator<Integer> listIterator = testInstance.listIterator();

        try {
            listIterator.previous();
            fail("list iterator do not throw the Exception when called previous method on empty collection");
        } catch (final java.util.NoSuchElementException e) {}
    }

    @Test
    public void testHasPreviouseWhenEmptyCollection() {
        final ArrayList<Integer> testInstance = new ArrayList<>();

        final ListIterator<Integer> listIterator = testInstance.listIterator();

        assertFalse(listIterator.hasPrevious());
    }

    @Test
    public void testRemoveTwoTimeInTheRow() throws Exception {
        final Collection<Integer> testInstance = new ArrayList<>();
        testInstance.add(1);
        testInstance.add(2);

        final Iterator<Integer> iter = testInstance.iterator();
        iter.next();
        iter.remove();
        assertEquals("Expected collection size is 1, however actual is not", 1, testInstance.size());
        try {
            iter.remove();
            fail("remove do not throw the Exception when called twice");
        } catch (final IllegalStateException e) {}
    }



    @Test
    public void testSubList() throws Exception {
        final ArrayList<Integer> testInstance = new ArrayList<>();
        testInstance.add(1);
        testInstance.add(2);

        Collection<Integer> subList = testInstance.subList(0,1);
        assertEquals("Expected collection size is 1, however actual is not", 1, subList.size());
        assertEquals("No 2 expected,", false, subList.contains(2));
        try {
            testInstance.subList(-1,2);
            fail("subList do not throw the Exception when called with out bounds indexes");
        } catch (final IndexOutOfBoundsException e) {}
        try {
            testInstance.subList(2,1);
            fail("subList do not throw the Exception when called with wrong order indexes");
        } catch (final IllegalArgumentException e) {}

    }

    @Test
    public void testAddToEnd() {
        final ArrayList<Integer> testInstance = new ArrayList<>();
        testInstance.add(1);
        testInstance.add(2);
        testInstance.add(3);
        testInstance.add(3, 4);
        final Iterator<Integer> iter = testInstance.listIterator(3);
        assertEquals("Expected iterator to have next after adding new element", true, iter.hasNext());
        assertEquals("Expected 4 but it's not", 4, (int)iter.next());
        try {
            testInstance.add(-1,4);
            fail("add do not throw the Exception when called with out bounds indexes");
        } catch (final IndexOutOfBoundsException e) {}
        try {
            testInstance.add(5,4);
            fail("add do not throw the Exception when called with out bounds indexes");
        } catch (final IndexOutOfBoundsException e) {}
    }

    @Test
    public void testAddToMiddle() {
        final ArrayList<Integer> testInstance = new ArrayList<>();
        testInstance.add(1);
        testInstance.add(2);
        testInstance.add(4);
        testInstance.add(2, 3);
        final Iterator<Integer> iter = testInstance.listIterator(2);
        assertEquals("Expected iterator to have next after adding new element", true, iter.hasNext());
        assertEquals("Expected 4 but it's not", 3, (int)iter.next());
    }

    @Test
    public void testAddAllToEnd() {
        final ArrayList<Integer> testInstance = new ArrayList<>();
        testInstance.add(1);
        testInstance.add(2);
        testInstance.add(3);
        testInstance.add(4);
        final ArrayList<Integer> testInstance2 = new ArrayList<>();
        testInstance2.add(1);
        testInstance2.add(2);
        testInstance2.add(3);
        testInstance2.add(4);

        testInstance.addAll(4, testInstance2);

        assertEquals("Expected new testInstance size to be 8", 8, testInstance.size());
        assertEquals("Expected 5-th element of new List to be 1 but it's not", 1, (int)testInstance.get(4));
        final ArrayList<Integer> testInstance3 = new ArrayList<>();
        try {
            testInstance.addAll(1,testInstance3);
            fail("addAll do not throw the Exception when called with null List");
        } catch (final NullPointerException e) {}
    }

    @Test
    public void testAddAllToEndEnoughSize() {
        final ArrayList<Integer> testInstance = new ArrayList<>();
        testInstance.add(1);
        testInstance.add(2);
        testInstance.add(3);
        testInstance.add(4);
        testInstance.add(5);
        final ArrayList<Integer> testInstance2 = new ArrayList<>();
        testInstance2.add(1);
        testInstance2.add(2);
        testInstance.addAll(5, testInstance2);
        assertEquals("Expected new testInstance size to be 7", 7, testInstance.size());
        assertEquals("Expected 6-th element of new List to be 1 but it's not", 1, (int)testInstance.get(5));
    }

    @Test
    public void testAddAllToMiddle() {
        final ArrayList<Integer> testInstance = new ArrayList<>();
        testInstance.add(1);
        testInstance.add(2);
        testInstance.add(3);
        testInstance.add(4);
        final ArrayList<Integer> testInstance2 = new ArrayList<>();
        testInstance2.add(1);
        testInstance2.add(2);
        testInstance2.add(3);
        testInstance2.add(4);
        testInstance.addAll(2, testInstance2);
        assertEquals("Expected new testInstance size to be 8", 8, testInstance.size());
        assertEquals("Expected 3-th element of new List to be 1 but it's not", 1, (int)testInstance.get(2));
    }

    @Test
    public void testAddAllToMiddleEnoughSize() {
        final ArrayList<Integer> testInstance = new ArrayList<>();
        testInstance.add(1);
        testInstance.add(2);
        testInstance.add(3);
        testInstance.add(4);
        testInstance.add(5);
        final ArrayList<Integer> testInstance2 = new ArrayList<>();
        testInstance2.add(1);
        testInstance2.add(2);
        testInstance.addAll(2, testInstance2);
        assertEquals("Expected new testInstance size to be 7", 7, testInstance.size());
        assertEquals("Expected 3-th element of new List to be 1 but it's not", 1, (int)testInstance.get(2));
    }


    @Test
    public void testLastIndexOf() {
        final ArrayList<Integer> testInstance = new ArrayList<>();
        testInstance.add(1);
        testInstance.add(2);
        testInstance.add(3);
        testInstance.add(3);
        testInstance.add(4);
        assertEquals("Expected index to be 3", 3, testInstance.lastIndexOf(3));
        assertEquals("Expected to see -1, but it's not", -1, testInstance.lastIndexOf(0));
    }

    @Test
    public void testIndexOf() {
        final ArrayList<Integer> testInstance = new ArrayList<>();
        testInstance.add(1);
        testInstance.add(2);
        testInstance.add(3);
        testInstance.add(3);
        testInstance.add(4);
        assertEquals("Expected index to be 2", 2, testInstance.indexOf(3));
        assertEquals("Expected to see -1, but it's not", -1, testInstance.lastIndexOf(0));
    }


}
