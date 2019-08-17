package com.alro.zoo.shared;

import org.springframework.stereotype.Component;

@Component("GeneralMethods")
public class GeneralMethods {

	public int generateAnId(String prefixe) {
		int aNumber = (int)((Math.random() * 9000000)+1000000);
		return aNumber;
	}
}
