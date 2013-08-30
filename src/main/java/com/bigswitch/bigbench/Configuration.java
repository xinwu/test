package com.bigswitch.bigbench;

public class Configuration {

	private String _comment;

	public Configuration() {
		setComment("no comment");
	}
	
	public String getComment() { 
		return _comment; 
	}
	
	public void setComment(String comment) {
		_comment = comment; 
	}
}
