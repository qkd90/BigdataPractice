/*
 * frxs Inc.  湖南兴盛优选电子商务有限公司.
 * Copyright (c) 2017-2019. All Rights Reserved.
 */
package com.dto;

import java.util.Objects;

import static com.dto.Pizza.Topping.*;

/**
 * @author lst
 * @version : MyPizza.java,v 0.1 2020年01月14日 14:07
 */
public class MyPizza extends Pizza {

    public enum Size{AMALL,MEDIUM,LARGE}
    private final Size size;

    public static class Builder extends Pizza.Builder<Builder>{
        private final Size size;

        public Builder(Size size){
            this.size = Objects.requireNonNull(size);
        }

        @Override
        public MyPizza build() {
            return new MyPizza(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    MyPizza(Builder builder) {
        super(builder);
        size = builder.size;
    }

    public static void main(String[] args) {
        // MyPizza pizza = (MyPizza)new MyPizza.Builder(Size.AMALL).addTopping().build();
        // Calzone calzone = new Calzone.Builder().addTopping(HAM).sauceInside().build();



    }
}