package com.lhh.base;

import java.io.Serializable;
import java.util.List;

public class LhhPage<T> implements Cloneable, Serializable {
	private static final long serialVersionUID = 1L;

	private int totalProperty;
    
    private List<T> datas;

    public LhhPage(int totalProperty, List<T> datas) {
        this.totalProperty = totalProperty;
        if( this.totalProperty <0)totalProperty = 0 ;
        this.datas = datas;
    }

    public int getTotalProperty() {
        return totalProperty;
    }

    public void setTotalProperty(int totalProperty) {
        if( this.totalProperty <0)totalProperty = 0 ;
        this.totalProperty = totalProperty;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }
    
    
}
