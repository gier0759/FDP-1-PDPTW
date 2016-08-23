package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import alg.Solver;

public class Main {

	public static void main(String[] args) {

		try {
			File dataFolder = new File("data1");
			File[] sizeFolders = dataFolder.listFiles();
			FileWriter fileWriter;
			Solver s = new Solver();
			BufferedWriter bufferedWriter;
			fileWriter = new FileWriter("log.txt");
			bufferedWriter = new BufferedWriter(fileWriter);
			for (File sizeFolder : sizeFolders) {
				if ((Integer.parseInt(sizeFolder.getName()) >= 19) && (Integer.parseInt(sizeFolder.getName()) <= 39)) {
					for (File instance : sizeFolder.listFiles()) {
						bufferedWriter.write(String.format("%s\t%s", instance.getName(), s.solve(instance, 30)));
						System.out.println("Done with " + instance.getName());
						bufferedWriter.newLine();
					}
				}
			}
			bufferedWriter.close();

		} catch (IOException e) {
			e.printStackTrace();

		}
	}

}
