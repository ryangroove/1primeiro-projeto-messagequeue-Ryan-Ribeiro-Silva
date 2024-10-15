package br.com.mangarosa.datastructures.interfaces.impl;

import br.com.mangarosa.datastructures.interfaces.Queue;
import br.com.mangarosa.datastructures.abstractClasses.QueueNode;

public class LinkedQueue<T> implements Queue<T> {
    private QueueNode<T> head;
    private QueueNode<T> tail;
    private int size;

    public LinkedQueue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public void enqueue(T element) {
        QueueNode<T> newNode = new QueueNode<>(element);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            return null;
        }
        T data = head.getValue();
        head = head.getNext();
        size--;
        return data;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }
}
