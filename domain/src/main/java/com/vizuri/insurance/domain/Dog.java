package com.vizuri.insurance.domain;

public class Dog {

	private int count;
	private String type;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "Dog [count=" + count + ", type=" + type + "]";
	}
	
}
