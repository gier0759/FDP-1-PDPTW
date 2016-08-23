package data;

import java.util.Arrays;
import java.util.HashMap;

import data.Location;

public class DATA {

	private static Location[] locations;
	private static int NUMBER_OF_LOCATIONS;
	private static int NUMBER_OF_REQUESTS;
	private static int VEHICLE_CAPACITY;
	private static HashMap<Integer, Integer> ref_locations = new HashMap<Integer, Integer>();
	private static double[][] dist, time;
	private static Location[] sortedLocations;
	private static Location[] sortedPickup;
	private static Location[] sortedDelivery;
	private static String fileLoc;

	private static void calcDistTIme() {
		DATA.dist = new double[DATA.NUMBER_OF_LOCATIONS][DATA.NUMBER_OF_LOCATIONS];
		for (int i = 0; i < DATA.NUMBER_OF_LOCATIONS; i++) {
			for (int j = 0; j < DATA.NUMBER_OF_LOCATIONS; j++) {
				if (i == j) {
					DATA.dist[i][j] = 0;
				} else {
					DATA.dist[i][j] = findDistance(DATA.locations[i].getCordX(), DATA.locations[i].getCordY(),
							DATA.locations[j].getCordX(), DATA.locations[j].getCordY());
				}
			}
		}
		DATA.time = DATA.dist;

	}

	public static void createData(String fileLoc) {
		setFileLoc(fileLoc);
		new Reader(fileLoc).createDATA();
	}

	public static void createDATA(Location[] locations, int loc, int cap) {
		DATA.locations = locations;
		DATA.NUMBER_OF_LOCATIONS = loc;
		DATA.NUMBER_OF_REQUESTS = loc / 2;
		DATA.VEHICLE_CAPACITY = cap;
		DATA.dist = new double[DATA.NUMBER_OF_LOCATIONS][DATA.NUMBER_OF_LOCATIONS];
		DATA.time = new double[DATA.NUMBER_OF_LOCATIONS][DATA.NUMBER_OF_LOCATIONS];
		for (Location location : locations) {
			DATA.ref_locations.put(location.getRef(), location.getId());
		}
		calcDistTIme();
		createSortedArrays();
	}

	private static void createSortedArrays() {
		sortedLocations = new Location[NUMBER_OF_LOCATIONS];
		sortedDelivery = new Location[NUMBER_OF_REQUESTS];
		sortedPickup = new Location[NUMBER_OF_REQUESTS];
		int countPick = 0;
		int countDel = 0;
		for (int i = 0; i < NUMBER_OF_LOCATIONS; i++) {
			sortedLocations[i] = locations[i];
			if (locations[i].isPickup()) {
				sortedPickup[countPick] = locations[i];
				countPick++;
			} else if (locations[i].isDelivery()) {
				sortedDelivery[countDel] = locations[i];
				countDel++;
			}
		}
		Arrays.sort(sortedLocations);
		Arrays.sort(sortedDelivery);
		Arrays.sort(sortedPickup);

	}

	private static double findDistance(int x1, int y1, int x2, int y2) {
		double x_delta = Math.abs(x1 - x2);
		double y_delta = Math.abs(y1 - y2);
		x_delta *= x_delta;
		y_delta *= y_delta;
		return Math.sqrt(x_delta + y_delta);
	}

	public static double getAvgDistanceCost() {
		double total_dist = 0.0;
		int count = 0;
		for (int i = 0; i < NUMBER_OF_LOCATIONS; i++) {
			for (int j = 0; j < NUMBER_OF_LOCATIONS; j++) {
				if ((i != j) && ((i != j - NUMBER_OF_REQUESTS) && (j - NUMBER_OF_REQUESTS > 0))) {
					total_dist += dist[i][j];
					count++;
				}
			}
		}
		return (total_dist / count);
	}

	public static double getAvgTimeCost() {
		double total_time = 0.0;
		int count = 0;
		for (int i = 0; i < NUMBER_OF_LOCATIONS; i++) {
			for (int j = 0; j < NUMBER_OF_LOCATIONS; j++) {
				if ((i != j) && ((i != j - NUMBER_OF_REQUESTS) && (j - NUMBER_OF_REQUESTS > 0))) {
					total_time += time[i][j];
					count++;
				}
			}
		}
		return (total_time / count);
	}

	public static double getAvgWindowSize() {
		double total_window_size = 0;
		for (int i = 1; i <= 2 * NUMBER_OF_REQUESTS; i++) {

			total_window_size += locations[i].getLatestTimeInterval() - locations[i].getEarliestTimeInterval();
		}
		return total_window_size / (2 * NUMBER_OF_REQUESTS);
	}

	public static double getDist(int i, int j) {
		return DATA.dist[i][j];
	}

	public static String getFileLoc() {
		return fileLoc;
	}

	public static Location getLocation(int i) {
		return DATA.locations[i];
	}

	/* Getters */
	public static int getNumberOfLocations() {
		return DATA.NUMBER_OF_LOCATIONS;
	}

	public static int getNumberOfRequests() {
		return DATA.NUMBER_OF_REQUESTS;
	}

	public static double getServiceTime(int i) {
		return DATA.locations[i].getServiceTime();
	}

	public static Location[] getSortedDelivery() {
		return sortedDelivery;
	}

	public static Location getSortedDelivery(int i) {
		return sortedDelivery[i];
	}

	public static Location[] getSortedLocation() {
		return sortedLocations;
	}

	public static Location getSortedLocation(int i) {
		return sortedLocations[i];
	}

	public static Location[] getSortedPickup() {
		return sortedPickup;
	}

	public static Location getSortedPickup(int i) {
		return sortedPickup[i];
	}

	public static double getTime(int i, int j) {
		return DATA.time[i][j];
	}

	public static int getVehicleCapacity() {
		return DATA.VEHICLE_CAPACITY;
	}

	public static void setFileLoc(String fileLoc) {
		DATA.fileLoc = fileLoc;
	}

	public Location getByRef(int ref) {
		return DATA.locations[DATA.ref_locations.get(ref)];
	}

}
