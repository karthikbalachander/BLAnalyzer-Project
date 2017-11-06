package com.target.barrenland;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import com.target.utils.BLConstants;

/**
 * 
 * @author Karthik Balachander
 *
 */
public class BLAnalyzer {

	/**
	 * 
	 * Object to store the co ordinates of all barren lands.
	 */
	private LinkedList<Integer[]> barrenLandCO;
	/**
	 * Object to store the co ordinates/ areas to inspect.
	 */
	private LinkedList<Integer[]> areaToInspectQueue;
	/**
	 * Object to store the size of various fertile lands.
	 */
	private Map<Integer, Integer> farmLandMap;
	/**
	 * Array to mark the land.
	 */
	private String landMarker[][];

	final static int landMaxLength = 600;
	final static int landMaxBreadth = 400;

	BLAnalyzer() {
		barrenLandCO = new LinkedList<Integer[]>();
		areaToInspectQueue = new LinkedList<Integer[]>();
		farmLandMap = new HashMap<Integer, Integer>();
		landMarker = new String[landMaxBreadth][landMaxLength];
	}

	/**
	 * Method accepts co ordinates of barren land and computes the fertile land
	 * info and provides fertile are in square meters sorted from smallest area
	 * to greatest.
	 * 
	 * @param barrenLandInfo
	 * @return String
	 */
	public String getFertileLand(String barrenLandInfo) {

		String result = "";

		try {
			
			// Marks all co ordinates of farm land to variable F.
			markAllFarmLand();

			// parse the input from STDIN into the necessary objects.
			parseInputBarrenLand(barrenLandInfo);

			// mark lands which are barren in the farm.
			markBarrenArea();

			// algorithm to identify and mark fertile land.
			identifyFertileLand();

			// method to compute area for fertile land.
			result = computeFertileArea();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * Method to mark all the farm land with Char "F". 
	 */
	private void markAllFarmLand() {

		for (int x = 0; x < landMaxBreadth; x++) {
			for (int y = 0; y < landMaxLength; y++) {
				landMarker[x][y] = BLConstants.BL_LAND_FARM_MARKER;
			}
		}

	}

	/**
	 * Method to compute the fertile area and return sorted result.
	 * 
	 * @return String
	 */
	private String computeFertileArea() {
		int[] area = new int[farmLandMap.values().size()];
		int count = 0;
		for (Map.Entry<Integer, Integer> currentEntry : farmLandMap.entrySet()) {
			area[count] = currentEntry.getValue();
			count++;
		}
		Arrays.sort(area);

		return (Arrays.toString(area).replaceAll("\\[|\\]|,", ""));
	}

	/**
	 * 
	 * Method to parse through the input received from STDIN.
	 * 
	 * @param barrenLandInfo
	 * @return Void
	 */
	private void parseInputBarrenLand(String barrenLandInfo) {

		String[] barrenLands = barrenLandInfo.split(BLConstants.BL_INPUT_SEPARATOR);
		for (String barrenLand : barrenLands) {
			barrenLand = barrenLand.replace(BLConstants.BL_REGEX_DOUBLE_QUOTE, BLConstants.BL_EMPTY_STRING);
			barrenLand = barrenLand.replaceAll(BLConstants.BL_REGEX_CURLY_BRACKETS, BLConstants.BL_EMPTY_STRING);
			barrenLand = barrenLand.replaceAll(BLConstants.BL_REGEX_DOUBLE_QUOTE_2, BLConstants.BL_EMPTY_STRING);
			barrenLand = barrenLand.replaceAll(BLConstants.BL_REGEX_CONTROL_CHAR, BLConstants.BL_EMPTY_STRING);

			if (!barrenLand.isEmpty()) {
				String[] blCoOrdinatesStr = barrenLand.split(BLConstants.BL_SPACE_STRING);

				Integer[] blCoOrdinates = { Integer.parseInt(blCoOrdinatesStr[0]),
						Integer.parseInt(blCoOrdinatesStr[1]), Integer.parseInt(blCoOrdinatesStr[2]),
						Integer.parseInt(blCoOrdinatesStr[3]) };

				barrenLandCO.add(blCoOrdinates);
			}
		}

	}

	/**
	 * 
	 * Method to mark all barren land co ordinates with Barren land marker "B"
	 */
	private void markBarrenArea() {

		Iterator<Integer[]> iter = barrenLandCO.iterator();

		while (iter.hasNext()) {
			Integer[] currentBarrenLand = iter.next();

			for (int x = currentBarrenLand[0]; x <= currentBarrenLand[2]; x++) {
				for (int y = currentBarrenLand[1]; y <= currentBarrenLand[3]; y++) {
					landMarker[x][y] = BLConstants.BL_LAND_BARREN_MARKER;
				}
			}
		}
	}

	/**
	 * Method to add the given co ordinates (x,y) to the queue which needs to be inspected. The co ordinates are added only if the land is not barren for the given co ordinates.
	 * 
	 * @param x
	 * @param y
	 */
	private void addCOToInspectionQueue(int x, int y) {
		if (landMarker[x][y].equalsIgnoreCase(BLConstants.BL_LAND_FARM_MARKER)) {
			Integer[] currLandCO = { x, y };
			areaToInspectQueue.add(currLandCO);
		}
	}

	/**
	 * This is the method having the algorithm. The algorithm does a breadth first traversal for a graph.
	 * We will traverse through the farm land giving the fertileCounter to all connected farm areas which are not covered by barren land. 
	 */
	private void identifyFertileLand() {
		int fertileLandCounter = 1;
		int x = 0;
		int y = 0;

		while (x < landMaxBreadth && y < landMaxLength) {

			// if Queue is empty, pick existing co ordinates (x,y)
			if (areaToInspectQueue.isEmpty()) {

				Integer[] coOrdinates = { x, y };

				// If land is not barren and has not been visited yet.
				if (landMarker[x][y].equalsIgnoreCase(BLConstants.BL_LAND_FARM_MARKER)) {

					fertileLandCounter++;

					farmLandMap.put(fertileLandCounter, 0);
					areaToInspectQueue.add(coOrdinates);
				}

				// Increment X or Y co -ordinates manually to traverse through
				// the farm land
				if (x == (landMaxBreadth - 1)) {
					x = 0;
					y++;
				} else {
					x++;
				}
			}

			// If queue is not empty, then traverse the farm co ordinates in the
			// queue.
			if(!areaToInspectQueue.isEmpty()) {

				Integer[] coOrdinates = areaToInspectQueue.pop();

				int queueX = coOrdinates[0];
				int queueY = coOrdinates[1];

				if (landMarker[queueX][queueY].equalsIgnoreCase(BLConstants.BL_LAND_FARM_MARKER)) {
					if (queueX > 0) {
						addCOToInspectionQueue(queueX - 1, queueY);
					}
					if (queueX < (landMaxBreadth - 1)) {
						addCOToInspectionQueue(queueX + 1, queueY);
					}
					if (queueY > 0) {
						addCOToInspectionQueue(queueX, queueY - 1);
					}
					if (queueY < (landMaxLength - 1)) {
						addCOToInspectionQueue(queueX, queueY + 1);
					}
					
					landMarker[queueX][queueY] = Integer.toString(fertileLandCounter);
					farmLandMap.put(fertileLandCounter, (farmLandMap.get(fertileLandCounter) + 1));
				}
			}

		}
	}

}
