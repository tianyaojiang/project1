package com.langlang.health.common.entity;

import java.util.List;

public class PageBO {

	/**
     * 总记录数量
     */
    private Integer totals;
    /**
     * 当前页数据列表
     */
  	private List<?> list;
  	
  	public PageBO(){
  	}
  	
  	public PageBO(Integer totals, List<?> list){
  		this.totals = totals;
  		this.list = list;
  	}
  	
	public Integer getTotals() {
		return totals;
	}
	public void setTotals(Integer totals) {
		this.totals = totals;
	}
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}
  	
}
