package project20280.list;

import project20280.interfaces.List;

import java.util.Iterator;

public class CircularlyLinkedList<E> implements List<E> {

    private class Node<T> {
        private final T data;
        private Node<T> next;

        public Node(T e, Node<T> n) {
            data = e;
            next = n;
        }

        public T getData() {
            return data;
        }

        public void setNext(Node<T> n) {
            next = n;
        }

        public Node<T> getNext() {
            return next;
        }
    }

    private Node<E> tail = null;
    private int size = 0;

    public CircularlyLinkedList() {

    }

    @Override
    public boolean isEmpty() {
        return size == 0; // true if list has no nodes
    }

    @Override
    public E get(int i) {
        if (i < 0 || i >= size) return null; // bad index

        Node<E> curr = tail.next; // start at head

        for (int j = 0; j < i; j++) {
            curr = curr.next; // walk to index
        }

        return curr.data; // return found data
    }

    /**
     * Inserts the given element at the specified index of the list, shifting all
     * subsequent elements in the list one position further to make room.
     *
     * @param i the index at which the new element should be stored
     * @param e the new element to be stored
     */
    @Override
    public void add(int i, E e) {
        if (i < 0 || i > size) return; // bad index

        if (i == 0) {
            addFirst(e); // add at front
            return;
        }

        if (i == size) {
            addLast(e); // add at back
            return;
        }

        Node<E> curr = tail.next; // start at head

        for (int j = 0; j < i - 1; j++) {
            curr = curr.next; // find node before spot
        }

        curr.next = new Node<>(e, curr.next); // link new node in
        size++; // list grew
    }

    @Override
    public E remove(int i) {
        if (i < 0 || i >= size) return null;

        if (i == 0) {
            return removeFirst(); // remove head
        }

        Node<E> curr = tail.next; // start at head

        for (int j = 0; j < i - 1; j++) {
            curr = curr.next; // find node before toDelete
        }

        Node<E> toDelete = curr.next; // node to remove
        curr.next = toDelete.next; // skip toDelete

        if (toDelete == tail) {
            tail = curr; // update tail if needed
        }

        size--; // list shrank
        return toDelete.data; // return removed data
    }

    public void rotate() {
        if (!isEmpty()) {
            tail = tail.next; // old head -> new tail
        }
    }

    private class CircularlyLinkedListIterator<E> implements Iterator<E> {
        private Node<E> curr = (Node<E>) (isEmpty() ? null : tail.next);
        private int count = 0;

        @Override
        public boolean hasNext() {
            return count < size; // stop after one full loop
        }

        @Override
        public E next() {
            E res = curr.data; // save current data
            curr = curr.next; // move forward
            count++; // count visited node
            return res;
        }

    }

    @Override
    public Iterator<E> iterator() {
        return new CircularlyLinkedListIterator<E>();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) return null;

        Node<E> head = tail.next; // first node

        if (head == tail) {
            tail = null; // list becomes empty
        } else {
            tail.next = head.next; // tail skips old head
        }

        size--;
        return head.data; // return removed data
    }

    @Override
    public E removeLast() {
        if (isEmpty()) return null;

        if (size == 1) {
            E data = tail.data; // save only value
            tail = null; // list empty
            size--;
            return data;
        }

        Node<E> curr = tail.next; // start at head

        while (curr.next != tail) {
            curr = curr.next; // find node before tail
        }

        E data = tail.data; // save old tail
        curr.next = tail.next; // new tail points to head
        tail = curr; // update tail
        size--;

        return data;
    }

    @Override
    public void addFirst(E e) {
        if (isEmpty()) {
            tail = new Node<>(e, null); // create only node
            tail.next = tail; // points to itself
        } else {
            Node<E> newest = new Node<>(e, tail.next); // new node before head
            tail.next = newest; // tail to new head
        }

        size++;
    }

    @Override
    public void addLast(E e) {
        addFirst(e); // add new head first
        tail = tail.next; // rotate it to tail
    }

    public String toString() {
        if (isEmpty()) {
            return "[]"; // empty list
        }

        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = tail;

        do {
            curr = curr.next; // move from tail to head
            sb.append(curr.data);
            if (curr != tail) {
                sb.append(", ");
            }
        } while (curr != tail);

        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        CircularlyLinkedList<Integer> ll = new CircularlyLinkedList<Integer>();
        for (int i = 10; i < 20; ++i) {
            ll.addLast(i);
        }

        System.out.println(ll);

        ll.removeFirst();
        System.out.println(ll);

        ll.removeLast();
        System.out.println(ll);

        ll.rotate();
        System.out.println(ll);

        ll.removeFirst();
        ll.rotate();
        System.out.println(ll);

        ll.removeLast();
        ll.rotate();
        System.out.println(ll);

        for (Integer e : ll) {
            System.out.println("value: " + e);
        }

    }
}