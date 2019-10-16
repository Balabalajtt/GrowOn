package com.utte.growon.kaiyan.category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 江婷婷 on 2017/11/26.
 */

public class CategoryItem {
    public String title;
    public String subTitle;
    public List<CategoryVideoItem> itemList;

    public CategoryItem(String title, String subTitle) {
        this.title = title;
        this.subTitle = subTitle;
        this.itemList = new ArrayList<>();
    }
}
