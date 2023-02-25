package com.bobocode.tdd;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StackTest {

    private static final Integer INT_VALUE = 123;
    private Stack<Integer> stack;

    @BeforeEach
    public void beforeEach() {
        stack = new LinkedStack<>();
    }

    @Test
    public void pushAndPopElement() {
        stack.push(INT_VALUE);

        final int actual = stack.pop();
        final int expected = INT_VALUE;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void popOnEmptyStack() {
        Assertions.assertNull(stack.pop());
    }

    @Test
    public void popOnEmptyStackAfterRemoveElements() {
        stack.push(1);
        stack.pop();
        Assertions.assertNull(stack.pop());
    }

    @Test
    public void sizeIsZeroOnEmptyElement() {
        Assertions.assertTrue(stack.isEmpty());
        Assertions.assertEquals(0, stack.size());
    }

    @Test
    public void size() {
        stack.push(1);
        stack.push(2);
        stack.push(3);

        stack.pop();
        stack.pop();

        Assertions.assertEquals(1, stack.size());
        Assertions.assertFalse(stack.isEmpty());
    }

}
