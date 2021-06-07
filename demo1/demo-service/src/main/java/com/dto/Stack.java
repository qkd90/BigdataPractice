/*
 * frxs Inc.  湖南兴盛优选电子商务有限公司.
 * Copyright (c) 2017-2019. All Rights Reserved.
 */
package com.dto;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * @author lst
 * @version : Stack.java,v 0.1 2020年01月16日 16:32
 */
public class Stack {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public Stack(){
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(Object e){
        ensureCapacity();
        elements[size++] = e;

    }

    public Object pop(){
        if(size == 0){
            throw new EmptyStackException();
        }
        return  elements[--size];
    }

    private void ensureCapacity(){
        if(elements.length == size){
            elements = Arrays.copyOf(elements,2*size+1);
        }
    }

    public static void main(String[] args) {
        Stack stack = new Stack();

        stack.push("dd");

        System.out.println(stack.pop());
    }
}