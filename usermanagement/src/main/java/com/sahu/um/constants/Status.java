package com.sahu.um.constants;

import lombok.Getter;

@Getter
public enum Status {
	ACTIVE("active"), INACTIVE("inactive");
	
	private final String value;
	
	Status(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return this.value;
	}
	
}
