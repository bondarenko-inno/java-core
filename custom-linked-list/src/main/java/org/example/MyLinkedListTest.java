package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class MyLinkedListTest {
    private MyLinkedList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new MyLinkedList<>();
    }

    @Test
    void sizeInitiallyZero() {
        assertEquals(0, list.size());
    }

    @Test
    void addFirstAndGetFirst() {
        list.addFirst(10);
        assertEquals(1, list.size());
        assertEquals(10, list.getFirst());
    }

    @Test
    void addLastAndGetLast() {
        list.addLast(20);
        assertEquals(1, list.size());
        assertEquals(20, list.getLast());
    }

    @Test
    void addByIndex() {
        list.addLast(1);
        list.addLast(3);
        list.add(1, 2);
        assertEquals(3, list.size());
        assertEquals(2, list.get(1));
    }

    @Test
    void getThrowsOnEmpty() {
        assertThrows(NoSuchElementException.class, list::getFirst);
        assertThrows(NoSuchElementException.class, list::getLast);
    }

    @Test
    void getByIndex() {
        list.addLast(5);
        list.addLast(6);
        list.addLast(7);
        assertEquals(6, list.get(1));
    }

    @Test
    void removeFirst() {
        list.addLast(1);
        list.addLast(2);
        assertEquals(1, list.removeFirst());
        assertEquals(1, list.size());
        assertEquals(2, list.getFirst());
    }

    @Test
    void removeLast() {
        list.addLast(3);
        list.addLast(4);
        assertEquals(4, list.removeLast());
        assertEquals(1, list.size());
        assertEquals(3, list.getLast());
    }

    @Test
    void removeByIndex() {
        list.addLast(8);
        list.addLast(9);
        list.addLast(10);
        assertEquals(9, list.remove(1));
        assertEquals(2, list.size());
        assertEquals(10, list.get(1));
    }

    @Test
    void removeThrowsOnEmpty() {
        assertThrows(NoSuchElementException.class, list::removeFirst);
        assertThrows(NoSuchElementException.class, list::removeLast);
    }

    @Test
    void indexOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
        list.addLast(1);
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(2, 2));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(5));
    }


}
