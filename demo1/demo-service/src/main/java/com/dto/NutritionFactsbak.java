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
public class NutritionFactsbak {

    private final int servingsize;
    private final int servings;
    private final int calories;
    private final int fal;
    private final int sodium;
    private final int carbohydrate;

    public NutritionFactsbak(int servingsize, int servings){
        this(servingsize,servings,0);
    }

    public NutritionFactsbak(int servingsize, int servings, int calories){
        this(servingsize,servings,calories,0);
    }

    public NutritionFactsbak(int servingsize, int servings, int calories, int fal){
        this(servingsize,servings,calories,fal,0);
    }

    public NutritionFactsbak(int servingsize, int servings, int calories, int fal, int sodium){
        this(servingsize,servings,calories,fal,sodium,0);
    }

    public NutritionFactsbak(int servingsize, int servings, int calories, int fal, int sodium, int carbohydrate) {
        this.servingsize = servingsize;
        this.servings = servings;
        this.calories = calories;
        this.fal = fal;
        this.sodium = sodium;
        this.carbohydrate = carbohydrate;
    }


    public static void main(String[] args) {

        NutritionFactsbak nutritionFacts = new NutritionFactsbak(1,1);
        nutritionFacts.getCalories();
    }

}

