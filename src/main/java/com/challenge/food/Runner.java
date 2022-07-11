package com.challenge.food;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Runner {

	public static void main(String...args) {
//		MyArray a = new MyArray(3);
//		a.add(1);
//		a.add(890);
//		a.add(31);
//		a.add(1000);
//		a.removeAt(0);
//		a.removeAt(0);
//		a.removeAt(0);
//		a.print();
//		System.out.println();
//		System.out.print(a.indexOf(8));
		teste("minusplusminus");
	
	}
	
	public static void teste(String text) {
			String[] split = text.split("s");
		StringBuilder result = new StringBuilder();
		
		 String collect = Arrays.stream(split).map(s ->{
			StringBuilder r = new StringBuilder();
			if (s.equals("plu")) {
				r.append("+");
			}else {
				r.append("-");
			}
			
			return r;
		}).collect(Collectors.joining());
//
//		for (String s : split) {
//			if (s.equals("plu")) {
//				result.append("+");
//			}else {
//				result.append("-");
//			}
//		}
		
		System.out.print(collect);
	}
}
