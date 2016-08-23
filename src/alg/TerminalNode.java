package alg;

import java.util.ArrayList;

import data.DATA;

public class TerminalNode {
	private int terminal_node;
	private LabelList label_list;

	public TerminalNode(int termNode) {
		this.terminal_node = termNode;
		this.label_list = new LabelList();
	}

	public void addLabel(Label label) {
		this.label_list.addLabel(label);
	}

	public void addNewLabels(ArrayList<Label> old_labels) {
		this.label_list.addNewLabels(old_labels, this.terminal_node);
	}

	public void checkPostFeasibility(State state, int iter) {
		for (int i = 0; i < this.label_list.getLabelList().size(); i++) {
			Label label = this.label_list.getLabelList().get(i);
			if (!criteria6(state, iter, label)) {

				this.label_list.getLabelList().remove(i);
			}

			else if (!criteria8(state, label)) {

				this.label_list.getLabelList().remove(i);

			} else if (!criteria7(state, label)) {

				this.label_list.getLabelList().remove(i);
			}
		}
	}

	public boolean criteria6(State state, int k, Label label) {
		int maxIter = Math.min(k + 4, DATA.getNumberOfLocations() - 1);
		int minIter = Math.max(k - 2, 0);
		for (int iter = minIter; iter <= maxIter; iter++) {
			int new_loc = DATA.getSortedLocation(iter).getId();
			if (!state.isVisited(new_loc) && label.term_node != new_loc) {
				if (label.time + DATA.getServiceTime(label.term_node) + DATA.getTime(label.term_node, new_loc) > DATA
						.getLocation(new_loc).getLatestTimeInterval()) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean criteria7(State state, Label label) {
		// Get destination pair
		int dest_p1 = -1, dest_p2 = -1, dest;
		boolean found1 = false, found2 = false;
		int index = 0;
		while ((!found1 && !found2) && index < DATA.getNumberOfRequests()) {
			if (!found1) {
				dest = DATA.getSortedDelivery(index).getId();
				if (!state.isVisited(dest) && state.isVisited(dest - DATA.getNumberOfRequests())) {
					dest_p1 = dest;
					found1 = true;
					index++;
				}
			}
			if (found1 && index < DATA.getNumberOfRequests()) {
				dest = DATA.getSortedDelivery(index).getId();
				if (!state.isVisited(dest) && state.isVisited(dest - DATA.getNumberOfRequests())) {
					dest_p2 = dest;
					found2 = true;
				}
			}

			index++;
		}
		if (found1 && found2) {
			double time1, time2;
			time1 = Math.max(DATA.getLocation(dest_p1).getEarliestTimeInterval(),
					label.time + DATA.getServiceTime(label.term_node) + DATA.getTime(label.term_node, dest_p1));

			time1 = time1 + DATA.getServiceTime(dest_p1) + DATA.getTime(dest_p1, dest_p2);

			time2 = Math.max(DATA.getLocation(dest_p2).getEarliestTimeInterval(),
					label.time + DATA.getServiceTime(label.term_node) + DATA.getTime(label.term_node, dest_p2));

			time2 = time2 + DATA.getServiceTime(dest_p2) + DATA.getTime(dest_p2, dest_p1);

			return time1 <= DATA.getLocation(dest_p2).getLatestTimeInterval()
					|| time2 <= DATA.getLocation(dest_p1).getLatestTimeInterval();
		} else {
			return true;
		}
	}

	public boolean criteria8(State state, Label label) {
		// Get destination pair
		int pick_p1 = -1, pick_p2 = -1, pick;
		boolean found1 = false, found2 = false;
		int index = 0;
		while ((!found1 && !found2) && index < DATA.getNumberOfRequests()) {
			if (!found1) {
				pick = DATA.getSortedPickup(index).getId();
				if (!state.isVisited(pick)) {
					pick_p1 = pick;
					found1 = true;
					index++;
				}
			}
			if (found1 && index < DATA.getNumberOfRequests()) {
				pick = DATA.getSortedPickup(index).getId();
				if (!state.isVisited(pick)) {
					pick_p2 = pick;
					found2 = true;
				}
			}

			index++;
		}
		if (found1 && found2) {
			double time1, time2;
			time1 = Math.max(DATA.getLocation(pick_p1).getEarliestTimeInterval(),
					label.time + DATA.getServiceTime(label.term_node) + DATA.getTime(label.term_node, pick_p1));

			time1 = time1 + DATA.getServiceTime(pick_p1) + DATA.getTime(pick_p1, pick_p2);

			time2 = Math.max(DATA.getLocation(pick_p2).getEarliestTimeInterval(),
					label.time + DATA.getServiceTime(label.term_node) + DATA.getTime(label.term_node, pick_p2));

			time2 = time2 + DATA.getServiceTime(pick_p2) + DATA.getTime(pick_p2, pick_p1);

			return time1 <= DATA.getLocation(pick_p2).getLatestTimeInterval()
					|| time2 <= DATA.getLocation(pick_p1).getLatestTimeInterval();
		} else {
			return true;
		}
	}

	public void eliminateLabels() {
		this.label_list.eliminateLabels();

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TerminalNode other = (TerminalNode) obj;
		if (this.terminal_node != other.terminal_node) {
			return false;
		}
		return true;
	}

	public ArrayList<Label> getLabelList() {
		return this.label_list.getLabelList();
	}

	public int getTerminalNode() {
		return this.terminal_node;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.terminal_node;
		return result;
	}

	@Override
	public String toString() {
		return this.label_list.toString();
	}

}
