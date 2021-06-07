/*
 * frxs Inc.  湖南兴盛优选电子商务有限公司.
 * Copyright (c) 2017-2019. All Rights Reserved.
 */
package com.dto;

/**
 * @author lst
 * @version : Calzone.java,v 0.1 2020年01月14日 14:14
 */
public class Calzone extends Pizza{

    private final boolean sauceInside;

    public static class Builder extends Pizza.Builder<Builder>{
        private boolean sauceInside = false;

        public Builder sauceInside(){
            sauceInside = true;
            return this;
        }
        @Override
        Calzone build() {
            return new Calzone(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    private Calzone(Builder builder){
        super(builder);
        sauceInside = builder.sauceInside;
    }



}