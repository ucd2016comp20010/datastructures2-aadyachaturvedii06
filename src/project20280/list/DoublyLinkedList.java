package project20280.list;

import project20280.interfaces.List;

import java.util.Iterator;

public class DoublyLinkedList<E> implements List<E> {

    private static class Node<E> {
        private final E data;
        private Node<E> next;
        private Node<E> prev;

        public Node(E e, Node<E> p, Node<E> n) {
            data = e;
            prev = p;
            next = n;
        }

        public E getData() {
            return data;
        }

        public Node<E> getNext() {
            return next;
        }

        public Node<E> getPrev() {
            return prev;
        }

    }

    private final Node<E> head;
    private final Node<E> tail;
    private int size = 0;

    public DoublyLinkedList() {
        head = new Node<E>(null, null, null);
        tail = new Node<E>(null, head, null);
        head.next = tail;
    }

    private void addBetween(E e, Node<E> pred, Node<E> succ) {
        Node<E> newest = new Node<>(e, pred, succ); // new node between
        pred.next = newest; // left node points next; forward
        succ.prev = newest; // right node points prev; back
        size++; // link added
    }

    @Override
    public int size() {
        return size; // return stored size
    }

    @Override
    public boolean isEmpty() {
        return size == 0; // true if no real nodes
    }

    @Override
    public E get(int i) {
        if (i < 0 || i >= size) return null; // bad index

        Node<E> curr = head.next; // start at first real node

        for (int j = 0; j < i; j++) {
            curr = curr.next; // go to index
        }

        return curr.data; //rqd data
    }

    @Override
    public void add(int i, E e) {
        if (i < 0 || i > size) return; // bad index

        Node<E> curr = head.next; // start at head

        for (int j = 0; j < i; j++) {
            curr = curr.next; // find after
        }

        addBetween(e, curr.prev, curr); // add before curr
    }

    @Override
    public E remove(int i) {
        if (i < 0 || i >= size) return null; // bad index

        Node<E> curr = head.next; // start at first r

        for (int j = 0; j < i; j++) {
            curr = curr.next; // walk to tbd
        }

        return remove(curr); // unlink
    }

    private class DoublyLinkedListIterator<E> implements Iterator<E> {
        Node<E> curr = (Node<E>) head.next;

        @Override
        public boolean hasNext() {
            return curr != tail;
        }

        @Override
        public E next() {
            E res = curr.data;
            curr = curr.next;
            return res;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new DoublyLinkedListIterator<E>();
    }

    private E remove(Node<E> n) {
        Node<E> pred = n.prev; // node before tbd
        Node<E> succ = n.next; // node after tbd

        pred.next = succ; // skip tbd forward
        succ.prev = pred; // skip tbd backward

        size--; // list size reduce
        return n.data; // return removed value
    }

    public E first() {
        if (isEmpty()) {
            return null;
        }
        return head.next.getData();
    }

    public E last() {
        if (isEmpty()) {
            return null;
        }
        return tail.prev.getData(); // last real node
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) return null; // nothing to remove
        return remove(head.next); // first real node
    }

    @Override
    public E removeLast() {
        if (isEmpty()) return null; // nothing to remove
        return remove(tail.prev); // last real node
    }

    @Override
    public void addLast(E e) {
        addBetween(e, tail.prev, tail); // insert before tail
    }

    @Override
    public void addFirst(E e) {
        addBetween(e, head, head.next); // insert after head
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = head.next;
        while (curr != tail) {
            sb.append(curr.data);
            curr = curr.next;
            if (curr != tail) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        DoublyLinkedList<Integer> ll = new DoublyLinkedList<Integer>();
        ll.addFirst(0);
        ll.addFirst(1);
        ll.addFirst(2);
        ll.addLast(-1);
        System.out.println(ll);

        ll.removeFirst();
        System.out.println(ll);

        ll.removeLast();
        System.out.println(ll);

        for (Integer e : ll) {
            System.out.println("value: " + e);
        }
    }
}