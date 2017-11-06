package com.target.barrenland;

import java.io.IOException;

public class RunBLAnalyzer {

	public static void main(String[] args) {
		
		BLAnalyzer blAnalyzer = new BLAnalyzer();
		String result = blAnalyzer.getFertileLand(args[0]);
		System.out.println("Area of Fertile land in sorted order is: " + result);
		System.out.println("Press any key to exit.");
		try {
			System.in.read();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}

}
