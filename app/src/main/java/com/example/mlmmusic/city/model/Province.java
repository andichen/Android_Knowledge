package com.example.mlmmusic.city.model;

import java.io.Serializable;

public class Province extends City implements Serializable {
    public String getP_pinyin() {
        return p_pinyin;
    }

    public void setP_pinyin(String p_pinyin) {
        this.p_pinyin = p_pinyin;
    }

    private String p_pinyin;
    public Province(String name, String code, String p_pinyin) {
        super(name, name, "province", code);
        this.p_pinyin=p_pinyin;
    }
}
