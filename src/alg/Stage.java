package alg;

import java.util.HashMap;

import data.DATA;

public class Stage {

	public static void main(String[] args) {
	}

	private int num_req;

	private HashMap<String, State> states;

	public Stage() {
		this.num_req = DATA.getNumberOfRequests();
		this.states = new HashMap<String, State>();
	}

	public Stage(int num_req) {
		this.num_req = num_req;
		this.states = new HashMap<String, State>();
	}

	public void addNewState(State old_state, int new_term_node) {
		State new_state = new State(this.num_req, old_state.getVisited(), old_state.canVisit(), old_state.getLoad());
		new_state.visitNode(new_term_node);
		// CRITERIA #3: CAPCITY CONSTRAINT MUST BE RESPECTED
		if (new_state.getLoad() <= DATA.getVehicleCapacity()) {
			if (this.states.get(new_state.getVisited().toString()) == null) {
				new_state.addTerminalNode(new_term_node, old_state.getLabelList());
				if (!new_state.getLabelList().isEmpty()) {
					this.states.put(new_state.getVisited().toString(), new_state);
				}
			} else {
				this.states.get(new_state.getVisited().toString()).addTerminalNode(new_term_node,
						old_state.getLabelList());
			}
		}
	}

	public void createInitalStage() {
		State s;
		for (int i = 1; i <= this.num_req; i++) {
			s = new State(i);
			this.states.put(s.getVisited().toString(), s);
		}
	}

	public boolean criteria4(State state, int loc, int k) {
		int earliestTime = DATA.getLocation(loc).getEarliestTimeInterval();
		int maxIter = Math.min(k + 4, DATA.getNumberOfLocations() - 1);
		int minIter = Math.max(k - 2, 0);
		for (int iter = minIter; iter <= maxIter; iter++) {
			int new_loc = DATA.getSortedLocation(iter).getId();
			if (!state.isVisited(new_loc) && loc != new_loc) {
				if (earliestTime + DATA.getServiceTime(loc) + DATA.getTime(loc, new_loc) > DATA.getLocation(new_loc)
						.getLatestTimeInterval()) {
					return false;
				}
			}
		}
		return true;

	}

	public HashMap<String, State> getStates() {
		return this.states;
	}

	public String printStage() {
		String str = "";
		for (State state : this.states.values()) {
			str += state.printPath();
		}
		return str;
	}

	public void reduceStates() {
		for (State state : this.states.values()) {
			state.eliminateLabels();
			if (state.getLabelList().isEmpty()) {
				this.states.remove(state);
			}
		}
	}

	public void reduceStates(int iter) {
		for (State state : this.states.values()) {
			state.eliminateLabels();
			state.checkPostFeasibility(iter);
		}
	}

}
