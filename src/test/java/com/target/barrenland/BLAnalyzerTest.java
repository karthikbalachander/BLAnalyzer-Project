package com.target.barrenland;


import static org.junit.Assert.assertEquals;

import org.junit.Test;
/**
 * 
 * @author Karthik Balachander
 *
 */
public class BLAnalyzerTest {
	
	BLAnalyzer blTest;
	@Test
	public void barrenLandTestEmptyInput() {
		
		blTest = new BLAnalyzer();
		String output = "240000";
		
		String input = new String("{\"\"}");
		
		String programOutput = blTest.getFertileLand(input);
		assertEquals(output,programOutput);
	}
	
	@Test
	public void barrenLandTestSampleInput1() {
		blTest = new BLAnalyzer();
		String output = ("116800 116800");		
		String input = new String("{“0 292 399 307”}");
		
		String programOutput = blTest.getFertileLand(input);
		assertEquals(output,programOutput);
	}
	
	@Test
	public void barrenLandTestSampleInput2() {
		blTest = new BLAnalyzer();
		String output = ("22816 192608");		
		String input = new String("{\"48 192 351 207\",\"48 392 351 407\",\"120 52 135 547\",\"260 52 275 547\"}");
		
		String programOutput = blTest.getFertileLand(input);
		assertEquals(output,programOutput);
	}
	
	@Test
	public void barrenLandTestInvalidInput() {
		blTest = new BLAnalyzer();
		String input = "";
		String output = "240000";
		String programOutput = blTest.getFertileLand(input);
		assertEquals(output, programOutput);
	}

}
