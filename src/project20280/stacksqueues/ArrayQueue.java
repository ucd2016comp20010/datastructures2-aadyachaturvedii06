package project20280.stacksqueues;

import project20280.interfaces.Queue;

public class ArrayQueue<E> implements Queue<E> {

    private static final int CAPACITY = 1000;
    private E[] data;
    private int front = 0;
    private int size = 0;

    public ArrayQueue(int capacity) {
        data = (E[]) new Object[capacity]; // creates the array that will store queue elements
    }

    public ArrayQueue() {
        this(CAPACITY);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void enqueue(E e) {
        if (size == data.length) { // checks if the queue is full before adding
            throw new IllegalStateException("Queue is full");
        }

        int rear = (front + size) % data.length; // finds the next available position using circular indexing
        data[rear] = e; // stores the new element at the rear
        size++; // increases the number of elements in the queue
    }

    @Override
    public E first() {
        return isEmpty() ? null : data[front]; // return the element itself, not Optional
    }

    @Override
    public E dequeue() {
        if (isEmpty()) { // nothing to remove
            return null;
        }

        E answer = data[front]; // save the front value
        data[front] = null; // clear old spot
        front = (front + 1) % data.length; // move front around the circular array
        size--; // one fewer element
        return answer; // return the element itself, not Optional
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; ++i) {
            E res = data[(front + i) % data.length];
            sb.append(res);
            if (i != size - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        Queue<Integer> qq = new ArrayQueue<>();
        System.out.println(qq);

        int N = 10;
        for (int i = 0; i < N; ++i) {
            qq.enqueue(i);
        }
        System.out.println(qq);

        for (int i = 0; i < N / 2; ++i) qq.dequeue();
        System.out.println(qq);
    }
}