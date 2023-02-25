package com.bobocode.tdd;

import com.bobocode.util.ExerciseNotCompletedException;

public class LinkedStack<T> implements Stack<T> {
    private class Node<T> {
        T value;
        Node<T> next;

        public Node(final T value) {
            this.value = value;
        }

        public Node(final T value, final Node<T> next) {
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> head;
    private int size;

    @Override
    public void push(T element) {
        final Node<T> newNode = new Node<>(element);
        if(head != null) {
            newNode.next = head;
        }
        head = newNode;
        size++;
    }
    
    @Override
    public T pop() {
        final Node<T> nodeToReturn = head;
        if(head != null) {
            head = head.next;
            size--;
        }
        return nodeToReturn == null ? null : nodeToReturn.value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }
}
