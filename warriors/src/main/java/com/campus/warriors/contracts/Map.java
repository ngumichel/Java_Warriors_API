package com.campus.warriors.contracts;

import com.campus.warriors.model.BaseCase;

public interface Map {

	String getName();
	
	int getNumberOfCase();

    BaseCase getCase(int index);
}
