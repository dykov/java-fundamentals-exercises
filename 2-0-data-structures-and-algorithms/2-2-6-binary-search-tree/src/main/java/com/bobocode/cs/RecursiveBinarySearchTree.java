package com.bobocode.cs;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * {@link RecursiveBinarySearchTree} is an implementation of a {@link BinarySearchTree} that is based on a linked nodes
 * and recursion. A tree node is represented as a nested class {@link Node}. It holds an element (a value) and
 * two references to the left and right child nodes.
 * <p><p>
 * <strong>TODO: to get the most out of your learning, <a href="https://www.bobocode.com/learn">visit our website</a></strong>
 * <p>
 *
 * @param <T> a type of elements that are stored in the tree
 * @author Taras Boychuk
 * @author Maksym Stasiuk
 */
public class RecursiveBinarySearchTree<T extends Comparable<T>> implements BinarySearchTree<T> {

    private static class Node<T> {
        T value;
        Node<T> left;
        Node<T> right;

        private Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root;
    private int size;

    public static <T extends Comparable<T>> RecursiveBinarySearchTree<T> of(T... elements) {
        final RecursiveBinarySearchTree<T> tree = new RecursiveBinarySearchTree<>();
        Arrays.stream(elements).forEach(tree::insert);
        return tree;
    }

    @Override
    public boolean insert(T element) {
        if(root == null) {
            root = new Node<>(element);
            size++;
            return true;
        }
        return insert(root, element);
    }

    private boolean insert(final Node<T> currentNode, final T element) {
        final int difference = element.compareTo(currentNode.value);
        if(difference < 0) {
            if(currentNode.left == null) {
                currentNode.left = new Node<>(element);
                size++;
                return true;
            } else {
                return insert(currentNode.left, element);
            }
        } else if(difference > 0) {
            if(currentNode.right == null) {
                currentNode.right = new Node<>(element);
                size++;
                return true;
            } else {
                return insert(currentNode.right, element);
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean contains(T element) {
        Objects.requireNonNull(element);
        return contains(root, element);
    }

    private boolean contains(final Node<T> currentNode, final T element) {
        if(currentNode == null) {
            return false;
        }

        final int difference = element.compareTo(currentNode.value);
        if(difference < 0) {
            return contains(currentNode.left, element);
        } else if(difference > 0) {
            return contains(currentNode.right, element);
        } else {
            return true;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int depth() {
        return root == null ? 0 : depth(root) - 1;
    }

    private int depth(final Node<T> node) {
        if(node == null) {
            return 0;
        } else {
            return 1 + Math.max(depth(node.left), depth(node.right));
        }
    }

    @Override
    public void inOrderTraversal(Consumer<T> consumer) {
        inOrderTraversal(root, consumer);
    }

    private void inOrderTraversal(final Node<T> currentNode, final Consumer<T> consumer) {
        if(currentNode != null) {
            inOrderTraversal(currentNode.left, consumer);
            consumer.accept(currentNode.value);
            inOrderTraversal(currentNode.right, consumer);
        }
    }

}
