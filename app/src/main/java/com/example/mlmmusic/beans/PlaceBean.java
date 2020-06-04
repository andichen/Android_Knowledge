package com.example.mlmmusic.beans;

import com.example.mlmmusic.base.BaseBean;

public class PlaceBean extends BaseBean {
    /**
     * id : 3226
     * name : 北京
     * isdisplay : 1
     * labeldomainid :
     * anchor_id :
     */

    private String id;
    private String name;
    private String isdisplay;
    private String labeldomainid;
    private String anchor_id;

    private boolean selected; //添加一个标记

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsdisplay() {
        return isdisplay;
    }

    public void setIsdisplay(String isdisplay) {
        this.isdisplay = isdisplay;
    }

    public String getLabeldomainid() {
        return labeldomainid;
    }

    public void setLabeldomainid(String labeldomainid) {
        this.labeldomainid = labeldomainid;
    }

    public String getAnchor_id() {
        return anchor_id;
    }

    public void setAnchor_id(String anchor_id) {
        this.anchor_id = anchor_id;
    }
}
