package com.xiaohe.ysjspt.entity;

import java.util.List;
/**
 * @author
 * Administrator
 */
public class PageBean<T> {
    private int itemCounts;
    private  int curPage;
    private int pageSize;
    private List<T> list;

    public int getItemCounts() {
        return itemCounts;
    }

    public void setItemCounts(int itemCounts) {
        this.itemCounts = itemCounts;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
