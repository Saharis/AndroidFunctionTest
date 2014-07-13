package com.virgil.androidfunctiontest.datamodel.pay;

import java.util.List;

/**
 * Created by Virgil on 2014/6/26.
 */
public class Pay_TitleInfoModel {
    public String title_main = "";//主Title
    public String title_vice = "";//副Title
    public List<Pay_TitleInfoModel> childTitleList = null;//诸多子标题

    public Pay_TitleInfoModel() {

    }
    public Pay_TitleInfoModel(String title_main,String title_vice) {
        this.title_main=title_main;
        this.title_vice=title_vice;
    }
}
