package com.htao.programmer.page;

import org.springframework.stereotype.Component;

/**
 * @Author: kedaya55
 * @Date: 2019-12-16 18:09
 */
@Component
public class Page {
    /**
     * 当前页面
     */
    private int page;
    /**
     * 页面显示数量
     */
    private int rows;
    private int offset;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getOffset() {
        this.offset = (page-1)*rows;
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = (page-1)*rows;
    }

    @Override
    public String toString() {
        return "Page{" +
                "page=" + page +
                ", row=" + rows +
                ", offset=" + offset +
                '}';
    }
}
