package com.lhh.base;

import java.io.Serializable;

/**
 * 分页模型
 * @author hwaggLee
 * @createDate 2016年12月16日
 */
public class LhhPageParam implements Cloneable, Serializable {
	private static final long serialVersionUID = 1L;
	public static final String[] DIR = {"ASC","DESC"};
    public static final int NUM_PER_PAGE = 20;
    
    /**查询起始位置*/
    private int start;
    
    /**查询个数*/
    private int limit;
    
    /**排序字段名*/
    private String sort;
    
    /**排序顺序*/
    private String dir;
    
    public LhhPageParam() {
    }

    /**
     * 提供默认的查询条件对象，start=0，limit=NUM_PER_PAGE=20
     * @return PageParam
     */
    public static LhhPageParam getDefault() {
        return new LhhPageParam(0, NUM_PER_PAGE, null, null);
    }

    public LhhPageParam(int start, int limit, String sort, String dir) {
        this.start = start;
        if( this.start <0)start = 0 ;
        this.limit = limit;
        if( this.limit <0)limit = 0 ;
        this.sort = sort;
        this.dir = dir;
    }

    public int getEnd() {
        return start + limit;
    }

    public String getDir() {
        return dir == null ? null : dir.toUpperCase();
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        if( this.start <0)start = 0 ;
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if( this.limit <0)limit = 0 ;
        this.limit = limit;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }
    
}
