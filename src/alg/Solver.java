package alg;

import java.io.File;
import java.util.ArrayList;

import data.DATA;

public class Solver {

	public Solver() {
		// TODO Auto-generated constructor stub
	}

	public String solve(File file) {
		DATA.createData(file.getPath());
		ArrayList<Stage> stages = new ArrayList<>();
		Stage stage = new Stage();
		stage.createInitalStage();
		stages.add(stage);
		Stage final_stage = new Stage();
		for (int i = 2; i <= DATA.getNumberOfLocations(); i++) {
			Stage previous_stage = stages.get(i - 2);
			Stage new_stage = new Stage();
			if (i == DATA.getNumberOfLocations()) {
				for (State state : previous_stage.getStates().values()) {
					final_stage.addNewState(state, 0);
				}
				final_stage.reduceStates();
				return String.format(final_stage.printStage());

			} else {
				for (State state : previous_stage.getStates().values()) {
					for (int loc = state.canVisit().nextSetBit(0); loc >= 0; loc = state.canVisit()
							.nextSetBit(loc + 1)) {
						// CRITERIA #4
						if (new_stage.criteria4(state, loc, i)) {
							new_stage.addNewState(state, loc);
						}
					}
				}
				new_stage.reduceStates(i);

			}
			stages.add(new_stage);
		}
		return "Infeasible";
	}

	public String solve(File file, long time_limit) {
		DATA.createData(file.getPath());
		ArrayList<Stage> stages = new ArrayList<>();
		Stage stage = new Stage();
		stage.createInitalStage();
		stages.add(stage);
		double start = System.currentTimeMillis();
		double end = start + (time_limit * 1000);
		for (int i = 2; i <= DATA.getNumberOfLocations(); i++) {
			Stage previous_stage = stages.get(i - 2);
			Stage new_stage = new Stage();
			Stage final_stage = new Stage();
			if (i == DATA.getNumberOfLocations()) {
				for (State state : previous_stage.getStates().values()) {
					final_stage.addNewState(state, 0);
				}
				final_stage.reduceStates();
				return String.format("YES\t%.2f\t%d\t%s", ((System.currentTimeMillis() - start) / 1000), i,
						new_stage.printStage());

			} else {
				for (State state : previous_stage.getStates().values()) {
					if (System.currentTimeMillis() > end) {
						return String.format("NO\t>10\t%d\t-", i);
					}
					for (int loc = state.canVisit().nextSetBit(0); loc >= 0; loc = state.canVisit()
							.nextSetBit(loc + 1)) {
						if (System.currentTimeMillis() > end) {
							return String.format("NO\t>10\t%d\t-", i);
						}
						// CRITERIA #4
						if (new_stage.criteria4(state, loc, i)) {
							new_stage.addNewState(state, loc);
						}
					}
				}
				new_stage.reduceStates(i);

			}
			stages.add(new_stage);
		}
		return "";
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
