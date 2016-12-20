package com.lhh.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class TreeNodeModel implements Cloneable, Serializable{
	private static final long serialVersionUID = 1L;
	public TreeNodeModel() {
		
	}
	public TreeNodeModel(String text) {
		this.text = text;
	}
	private String text;
	public Map<String, String> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	private Map<String, String> attributes = null;
	private List<TreeNodeModel> children;
	public List<TreeNodeModel> getChildren() {
		return children;
	}
	public void setChildren(List<TreeNodeModel> children) {
		this.children = children;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
