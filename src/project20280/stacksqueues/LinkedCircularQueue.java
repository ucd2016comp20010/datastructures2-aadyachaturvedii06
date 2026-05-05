package project20280.stacksqueues;

import project20280.interfaces.Queue;
import project20280.list.CircularlyLinkedList;

/**
 * Realization of a circular FIFO queue as an adaptation of a
 * CircularlyLinkedList. This provides one additional method not part of the
 * general Queue interface. A call to rotate() is a more efficient simulation of
 * the combination enqueue(dequeue()). All operations are performed in constant
 * time.
 */

public class LinkedCircularQueue<E> implements Queue<E> {

    private CircularlyLinkedList<E> list = new CircularlyLinkedList<>();

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        LinkedCircularQueue<Integer> q = new LinkedCircularQueue<>();

        q.enqueue(10);
        q.enqueue(20);
        q.enqueue(30);

        System.out.println("First: " + q.first());
        System.out.println("Dequeue: " + q.dequeue());
        System.out.println("First after dequeue: " + q.first());

        q.enqueue(40);
        q.enqueue(50);

        System.out.println("Size: " + q.size());

        q.rotate();
        System.out.println("First after rotate: " + q.first());

        while (!q.isEmpty()) {
            System.out.println("Removing: " + q.dequeue());
        }
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return list.isEmpty();
    }

    @Override
    public void enqueue(E e) {
        // TODO Auto-generated method stub
        list.addLast(e);
    }

    @Override
    public E first() {
        // TODO Auto-generated method stub
        return list.get(0);
    }

    @Override
    public E dequeue() {
        // TODO Auto-generated method stub
        return list.removeFirst();
    }

    public void rotate() {
        list.rotate();
    }

}