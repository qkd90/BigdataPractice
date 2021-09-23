package com.gson.bean;

import java.io.Serializable;

/**
 * Created by caiys on 2016/11/8.
 */
public class TransInfo implements Serializable {
    private String KfAccount;

    public String getKfAccount() {
        return KfAccount;
    }

    public void setKfAccount(String kfAccount) {
        KfAccount = kfAccount;
    }
}
