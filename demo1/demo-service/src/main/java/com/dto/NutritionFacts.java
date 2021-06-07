/*
 * frxs Inc.  湖南兴盛优选电子商务有限公司.
 * Copyright (c) 2017-2019. All Rights Reserved.
 */
package com.dto;

import lombok.Data;

/**
 * @author lst
 * @version : NutritionFacts.java,v 0.1 2020年01月13日 15:46
 */
@Data
public class NutritionFacts {

    private final int servingsize;
    private final int servings;
    private final int calories;
    private final int fal;
    private final int sodium;
    private final int carbohydrate;

    public static class Builder{
        private final int servingsize;
        private final int servings;

        public Builder(int servingsize, int servings) {
            this.servingsize = servingsize;
            this.servings = servings;
        }

        private  int calories = 0;
        private  int fal = 0;
        private  int sodium = 0;
        private  int carbohydrate = 0;

        public Builder calories(int val){
            calories = val;
            return this;
        }

        public Builder fal(int val){
            fal = val;
            return this;
        }

        public Builder sodium(int val){
            sodium = val;
            return this;
        }

        public Builder carbohydrate(int val){
            carbohydrate = val;
            return this;
        }

        public NutritionFacts build(){
            return new NutritionFacts(this);
        }

    }

    public NutritionFacts(Builder builder){
        servings = builder.servings;
        servingsize = builder.servingsize;
        calories = builder.calories;
        carbohydrate = builder.carbohydrate;
        sodium = builder.sodium;
        fal = builder.fal;
    }


    public static void main(String[] args) {
        NutritionFacts nutritionFacts = new Builder(220,2).calories(111).build();

    }

}

