package com.example.mlmmusic.beans;

import com.example.mlmmusic.base.BaseBean;

import java.util.List;

public class CeshiTextBean extends BaseBean {

    /**
     * tabName : 便民生活
     * functionItems : [{"name":"充值中心","isSelect":false,"imageUrl":"icon_home_selected","background":"#86c751"},{"name":"信用卡还款","isSelect":false,"imageUrl":"icon_home_selected","background":"#86c751"},{"name":"生活缴费","isSelect":false,"imageUrl":"icon_home_selected","background":"#86c751"},{"name":"城市服务","isSelect":false,"imageUrl":"icon_home_selected","background":"#86c751"},{"name":"生活号","isSelect":false,"imageUrl":"icon_home_selected","background":"#86c751"},{"name":"我的客服","isSelect":false,"imageUrl":"icon_home_selected","background":"#86c751"},{"name":"我的快递","isSelect":false,"imageUrl":"icon_home_selected","background":"#86c751"},{"name":"医疗健康","isSelect":false,"imageUrl":"icon_home_selected","background":"#86c751"},{"name":"记账本","isSelect":false,"imageUrl":"icon_home_selected","background":"#86c751"},{"name":"城市一卡通","isSelect":false,"imageUrl":"icon_home_selected","background":"#86c751"},{"name":"发票管家","isSelect":false,"imageUrl":"icon_home_selected","background":"#86c751"},{"name":"蚂蚁宝卡","isSelect":false,"imageUrl":"icon_home_selected","background":"#86c751"},{"name":"车主服务","isSelect":false,"imageUrl":"icon_home_selected","background":"#86c751"},{"name":"天天有料","isSelect":false,"imageUrl":"icon_home_selected","background":"#86c751"}]
     */

    private String tabName;
    private List<FunctionItemsBean> functionItems;

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public List<FunctionItemsBean> getFunctionItems() {
        return functionItems;
    }

    public void setFunctionItems(List<FunctionItemsBean> functionItems) {
        this.functionItems = functionItems;
    }


}
