package com.bobocode.cs;


import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * {@link LinkedList} is a list implementation that is based on singly linked generic nodes. A node is implemented as
 * inner static class {@link Node<T>}.
 * <p><p>
 * <strong>TODO: to get the most out of your learning, <a href="https://www.bobocode.com/learn">visit our website</a></strong>
 * <p>
 *
 * @param <T> generic type parameter
 * @author Taras Boychuk
 * @author Serhii Hryhus
 */
public class LinkedList<T> implements List<T> {

    private static class Node<T> {
        T value;

        Node<T> next;

        public Node(T value) {
            this.value = value;
        }
    }

    int size;

    Node<T> first;

    Node<T> last;

    /**
     * This method creates a list of provided elements
     *
     * @param elements elements to add
     * @param <T>      generic type
     * @return a new list of elements the were passed as method parameters
     */
    public static <T> LinkedList<T> of(T... elements) {
        LinkedList<T> linkedList = new LinkedList<>();
        Arrays.stream(elements).forEach(linkedList::add);
        return linkedList;
    }

    /**
     * Adds an element to the end of the list.
     *
     * @param element element to add
     */
    @Override
    public void add(T element) {
        Node<T> node = new Node<>(element);
        if (first == null) {
            first = node;
        } else {
            last.next = node;
        }
        last = node;
        size++;
    }

    /**
     * Adds a new element to the specific position in the list. In case provided index in out of the list bounds it
     * throws {@link IndexOutOfBoundsException}
     *
     * @param index   an index of new element
     * @param element element to add
     */
    @Override
    public void add(int index, T element) {
        Node<T> newNode = new Node<>(element);
        if (index == 0) {
            addAsFirst(newNode);
        } else if (index == size) {
            addAsLast(newNode);
        } else {
            add(index, newNode);
        }
        size++;
    }

    private void addAsFirst(Node<T> newNode) {
        newNode.next = first;
        first = newNode;
        if (newNode.next == null) {
            last = newNode;
        }
    }

    private void addAsLast(Node<T> newNode) {
        last.next = newNode;
        last = newNode;
    }

    private void add(int index, Node<T> newNode) {
        Node<T> nodeBeforeIndex = getNodeByIndex(index - 1);
        newNode.next = nodeBeforeIndex.next;
        nodeBeforeIndex.next = newNode;
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndexBounds(index);
        if (index == size - 1) {
            return last;
        }

        Node<T> currentNode = first;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private void checkIndexBounds(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Changes the value of an list element at specific position. In case provided index in out of the list bounds it
     * throws {@link IndexOutOfBoundsException}
     *
     * @param index   an position of element to change
     * @param element a new element value
     */
    @Override
    public void set(int index, T element) {
        getNodeByIndex(index).value = element;
    }

    /**
     * Retrieves an elements by its position index. In case provided index in out of the list bounds it
     * throws {@link IndexOutOfBoundsException}
     *
     * @param index element index
     * @return an element value
     */
    @Override
    public T get(int index) {
        return getNodeByIndex(index).value;
    }

    /**
     * Returns the first element of the list. Operation is performed in constant time O(1)
     *
     * @return the first element of the list
     * @throws java.util.NoSuchElementException if list is empty
     */
    @Override
    public T getFirst() {
        if (first == null) {
            throw new NoSuchElementException();
        }
        return first.value;
    }

    /**
     * Returns the last element of the list. Operation is performed in constant time O(1)
     *
     * @return the last element of the list
     * @throws java.util.NoSuchElementException if list is empty
     */
    @Override
    public T getLast() {
        if (last == null) {
            throw new NoSuchElementException();
        }
        return last.value;
    }

    /**
     * Removes an elements by its position index. In case provided index in out of the list bounds it
     * throws {@link IndexOutOfBoundsException}
     *
     * @param index element index
     * @return deleted element
     */
    @Override
    public T remove(int index) {
        T elementToDelete;
        if (index == 0 && first != null) {
            elementToDelete = first.value;
            first = first.next;
            if (first == null) {
                last = null;
            }
        } else {
            Node<T> nodeBeforeNodeToDelete = getNodeByIndex(index - 1);
            Node<T> nodeToDelete = nodeBeforeNodeToDelete.next;
            elementToDelete = nodeToDelete.value;
            nodeBeforeNodeToDelete.next = nodeToDelete.next;
            if (index == size - 1) {
                last = nodeBeforeNodeToDelete;
            }
        }
        size--;
        return elementToDelete;
    }


    /**
     * Checks if a specific exists in he list
     *
     * @return {@code true} if element exist, {@code false} otherwise
     */
    @Override
    public boolean contains(T element) {
        Node<T> currentNode = first;
        for (int i = 0; i < size; i++) {
            if (element.equals(currentNode.value)) {
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    /**
     * Checks if a list is empty
     *
     * @return {@code true} if list is empty, {@code false} otherwise
     */
    @Override
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Returns the number of elements in the list
     *
     * @return number of elements
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes all list elements
     */
    @Override
    public void clear() {
        first = null;
        last = null;
        size = 0;
    }
}
