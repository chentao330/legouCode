package com.legou.common.pojo;


import java.io.Serializable;
import java.util.List;

public class EasyUIDataGridResult implements Serializable {
	
	private long total;
	private List rows;
	public EasyUIDataGridResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	
}
