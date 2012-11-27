package com.above.smlite.util;

public class UniqueIDGenerator {
	
	private static long uniqueId = -1;
	
	public static String getID(){
		if(uniqueId == -1){
			uniqueId = System.currentTimeMillis();
		}
		return ++uniqueId + ""; 
	}
}
