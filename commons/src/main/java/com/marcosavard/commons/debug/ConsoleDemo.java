package com.marcosavard.commons.debug;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConsoleDemo {
	private static int x = 42;
	private static int[] array = new int[] {1, 1, 2, 3, 5, 8, 13, 21};
	private static int[][] multiDArray = { {2,3}, {5,9} };
	private static List<Integer> list; 
	private static Map<String, Integer> map; 
	private static Point pt = Point.of(2, 3); 
	
	public static void main(String[] args) {	
		list = Arrays.stream(array).boxed().collect(Collectors.toList());
		map = new HashMap<>(); 
		map.put("alpha", 5); 
		map.put("beta", 4); 
		map.put("gamma", 8); 
		
		printSystemOut();
		printConsole(); 
	}

	private static void printSystemOut() {
				
		System.out.println("Print using System.out");
		System.out.println("x = " + x);
		System.out.println(Math.PI);
		System.out.println(list);
		System.out.println(map);
		System.out.println(array);
		System.out.println(multiDArray);
		System.out.println(MessageFormat.format("Dear {0} {1}", "John", "Smith"));
		System.out.println(pt);
		System.out.println();
	}

	private static void printConsole() {
		Console.println("Print using Console");
		Console.println("x = " + x);
		Console.println(Math.PI);
		Console.println(list);
		Console.println(map);
		Console.println(array);
		Console.println(multiDArray);
		
		Console.println("Dear {0} {1}", "John", "Smith");
		Console.println(pt);
		Console.println();
	}
	
	//custom class w/o toString() method
	private static class Point {
		private double x, y;

		public static Point of(int x, int y) {
			return new Point(x, y);
		}
		
		public Point(int x, int y) {
			this.x = x;
			this.y = y; 
		}
		
		public Double getX() { return x; } 
		public Double getY() { return y; } 
	}

}
