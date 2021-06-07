/*
 * frxs Inc.  湖南兴盛优选电子商务有限公司.
 * Copyright (c) 2017-2019. All Rights Reserved.
 */
package com.dto;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author lst
 * @version : Pizza.java,v 0.1 2020年01月13日 17:22
 */
public abstract class Pizza {

    //定义一个枚举类
    public enum  Topping{HAM,MUSHROOM,ONION,PEPPER,SAUSAGE}

    final Set<Topping> toppings;

    abstract static class Builder<T extends Builder<T>>{
        //创建一个空枚举类
        EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);

        //添加配料
        public T addTopping(Topping topping){
            toppings.add(Objects.requireNonNull(topping));
            return self();
        }

        abstract  Pizza build();

        //传递类型作用
        protected  abstract T self();
    }

    Pizza(Builder<?> builder){
        toppings = builder.toppings.clone();
    }


    public static void main(String[] args) throws CloneNotSupportedException {
       UserInfo userInfo = new UserInfo();
       userInfo.setAddr("梅溪湖");
       userInfo.setAge(12);
       userInfo.setName("哈莉");
       userInfo.setPhone("111111111");

       UserInfo u1 = userInfo;

       // UserInfo u2 = (UserInfo) userInfo.clone();

       System.out.println(u1 == userInfo);
       System.out.println(userInfo);
       System.out.println(u1);
       System.out.println(userInfo.clone());
    }


}