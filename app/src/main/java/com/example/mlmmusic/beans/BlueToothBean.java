package com.example.mlmmusic.beans;

import com.example.mlmmusic.base.BaseBean;

public class BlueToothBean extends BaseBean {
    /**
     * id : 3226
     * name : 北京
     * isdisplay : 1
     * labeldomainid :
     * anchor_id :
     */

    private String address;
    private String name;
    private String bondState;
    private String type;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBondState() {
        return bondState;
    }

    public void setBondState(String bondState) {
        this.bondState = bondState;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
