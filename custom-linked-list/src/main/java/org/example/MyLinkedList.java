package org.example;

import java.util.NoSuchElementException;

public class MyLinkedList<T> {
    private static class Node<T> {
        T value;
        Node<T> next;
        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public int size() {
        return size;
    }

    public void addFirst(T el) {
        Node<T> node = new Node<>(el);
        node.next = head;
        head = node;
        if (size++ == 0) {
            tail = head;
        }
    }

    public void addLast(T el) {
        Node<T> node = new Node<>(el);
        if (size == 0) {
            head = node;
        }
        else {
            tail.next = node;
        }
        tail = node;
        size++;
    }

        public void add(int index, T el) {
            if (index < 0 || index > size){
                throw new IndexOutOfBoundsException();
            }

            if (index == 0) {
                addFirst(el);
                return;
            }

            if (index == size) {
                addLast(el);
                return;

            }
            Node<T> node = new Node<>(el);

            Node<T> prev = nodeAt(index - 1);
            node.next = prev.next;
            prev.next = node;
            size++;
        }

    public T getFirst() {
        checkEmpty();
        return head.value;
    }

    public T getLast() {
        checkEmpty();
        return tail.value;
    }

    public T get(int index) {
        return nodeAt(index).value;
    }

    public T removeFirst() {
        checkEmpty();
        T val = head.value;
        head = head.next;
        if (--size == 0){
            tail = null;
        }
        return val;
    }

    public T removeLast() {
        checkEmpty();
        if (size == 1){
            return removeFirst();
        }
        Node<T> prev = nodeAt(size - 2);
        T val = tail.value;
        tail = prev;
        tail.next = null;
        size--;
        return val;
    }

    public T remove(int index) {
        if (index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            return removeFirst();
        }

        if (index == size - 1){
            return removeLast();
        }

        Node<T> prev = nodeAt(index - 1);
        T val = prev.next.value;
        prev.next = prev.next.next;
        size--;
        return val;
    }

    private Node<T> nodeAt(int index) {
        if (index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }
        Node<T> curr = head;
        for (int i = 0; i < index; i++){
            curr = curr.next;
        }
        return curr;
    }

    private void checkEmpty() {
        if (size == 0) throw new NoSuchElementException();
    }


}
