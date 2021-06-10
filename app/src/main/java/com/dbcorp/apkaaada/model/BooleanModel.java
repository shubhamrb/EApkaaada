package com.dbcorp.apkaaada.model;

import java.io.Serializable;

/**
 * Created by Bhupesh Sen on 23-03-2021.
 */
public class BooleanModel implements Serializable {

    boolean check=false;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
