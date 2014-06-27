package com.mrprez.gencross.drawer.utils;

public class VariableNameUtil {
	
	public static String findCommonBegins(String str1, String str2){
		for(int i=0; i<str1.length() && i<str2.length(); i++){
			if(str1.charAt(i)!=str2.charAt(i)){
				return str1.substring(0, i);
			}
		}
		if(str1.length()!=str2.length()){
			return str1.substring(0, Math.min(str1.length(), str2.length()));
		}
		return str1;
	}
	
	public static String findCommonEnds(String str1, String str2){
		StringBuilder sb1 = new StringBuilder(str1);
		StringBuilder sb2 = new StringBuilder(str2);
		sb1.reverse();
		sb2.reverse();
		String str = findCommonBegins(sb1.toString(), sb2.toString());
		StringBuilder sb = new StringBuilder(str);
		return sb.reverse().toString();
	}

}
