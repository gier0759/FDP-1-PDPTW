package data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import data.Location;

public class Reader {

	private String fileLoc;
	private int NUMBER_OF_LOCATIONS;
	private int VEHICLE_CAPACITY;
	private Location locations[];

	public Reader(String fileLoc) {
		this.fileLoc = fileLoc;
		readFile();
	}

	public void createDATA() {
		DATA.createDATA(this.locations, this.NUMBER_OF_LOCATIONS, this.VEHICLE_CAPACITY);
	}

	private void readFile() {
		String line = "";
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(this.fileLoc);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			line = bufferedReader.readLine();
			String[] loc_cap = line.split(" ");
			this.NUMBER_OF_LOCATIONS = Integer.parseInt(loc_cap[0]);
			this.locations = new Location[this.NUMBER_OF_LOCATIONS];
			this.VEHICLE_CAPACITY = Integer.parseInt(loc_cap[1]);
			for (int i = 0; i < this.NUMBER_OF_LOCATIONS; i++) {
				line = bufferedReader.readLine();
				this.locations[i] = new Location(line);
			}
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			System.err.println("File not found");
		} catch (IOException e) {
			System.err.println("IOException:");
			e.printStackTrace();
		}

	}
}
