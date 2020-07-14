package com.demo.jpa.utils;

public class ClzMethodUtil {
	
	public static String getCurClzMethodName(Class<?> clz) {
		return clz.getEnclosingClass().getSimpleName() + "." + clz.getEnclosingMethod().getName();
	}

}
