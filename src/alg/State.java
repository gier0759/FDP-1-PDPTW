package alg;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;

import data.DATA;

/* A state represents a unique set S in P union D. At state has the following
 * * A set S of visited nodes denoted as a bitSet
 * * A collection of terminal nodes
 * * A load for the set
 */

public class State implements Cloneable {

	public static void main(String args[]) {
		State state = new State(5, 2);
		System.out.println(state);
		System.out.println(state.isVisited(2));
	}

	private BitSet visited;
	private BitSet can_visit;

	private int load = 0;

	private HashMap<Integer, TerminalNode> terminal_nodes;
	private int num_req = DATA.getNumberOfRequests();

	public State(BitSet visited, BitSet can_visit, int load) {
		this.visited = (BitSet) visited.clone();
		this.can_visit = (BitSet) can_visit.clone();
		this.load = load;
		this.terminal_nodes = new HashMap<Integer, TerminalNode>();
	}

	public State(int j_pickup) {
		this.visited = new BitSet(2 * this.num_req);
		this.can_visit = new BitSet(2 * this.num_req);
		this.can_visit.flip(1, this.num_req + 1);
		this.visited.flip(j_pickup);
		this.can_visit.flip(j_pickup);
		this.can_visit.flip(j_pickup + this.num_req);
		this.terminal_nodes = new HashMap<Integer, TerminalNode>();
		this.terminal_nodes.put(j_pickup, new TerminalNode(j_pickup));
		this.terminal_nodes.get(j_pickup)
				.addLabel(new Label(j_pickup,
						Math.max(DATA.getTime(0, j_pickup), DATA.getLocation(j_pickup).getEarliestTimeInterval()),
						DATA.getDist(0, j_pickup), null));
	}

	public State(int n, BitSet visited, BitSet can_visit, int load) {
		this.num_req = n;
		this.visited = (BitSet) visited.clone();
		this.can_visit = (BitSet) can_visit.clone();
		this.load = load;
		this.terminal_nodes = new HashMap<Integer, TerminalNode>();
	}

	public State(int n, int j_pickup) {
		this.num_req = n;
		this.visited = new BitSet(2 * this.num_req);
		this.can_visit = new BitSet(2 * n);
		this.can_visit.flip(1, n + 1);
		this.visited.flip(j_pickup);
		this.can_visit.flip(j_pickup);
		this.can_visit.flip(j_pickup + n);
		this.terminal_nodes = new HashMap<Integer, TerminalNode>();
		this.terminal_nodes.put(j_pickup, new TerminalNode(j_pickup));
	}

	public void addTerminalNode(int new_term_node, ArrayList<Label> old_labels) {
		if (this.terminal_nodes.get(new_term_node) == null) {
			this.terminal_nodes.put(new_term_node, new TerminalNode(new_term_node));
		}
		this.terminal_nodes.get(new_term_node).addNewLabels(old_labels);
		if (this.terminal_nodes.get(new_term_node).getLabelList().isEmpty()) {
			this.terminal_nodes.remove(new_term_node);
		}
	}

	public BitSet canVisit() {
		return this.can_visit;
	}

	public boolean canVisit(int i) {
		return this.can_visit.get(i) & (1 << i) == (1 << i);
	}

	public void checkPostFeasibility(int iter) {
		for (TerminalNode term_node : this.terminal_nodes.values()) {
			term_node.checkPostFeasibility(this, iter);
		}

	}

	public void eliminateLabels() {
		for (TerminalNode term_node : this.terminal_nodes.values()) {
			term_node.eliminateLabels();
		}

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
		State other = (State) obj;
		if (this.can_visit == null) {
			if (other.can_visit != null) {
				return false;
			}
		} else if (!this.can_visit.equals(other.can_visit)) {
			return false;
		}
		if (this.num_req != other.num_req) {
			return false;
		}
		if (this.visited == null) {
			if (other.visited != null) {
				return false;
			}
		}
		return true;
	}

	public ArrayList<Label> getLabelList() {
		LabelList label_list = new LabelList();
		for (TerminalNode term_node : this.terminal_nodes.values()) {
			label_list.addLabels(term_node.getLabelList());
		}
		return label_list.getLabelList();
	}

	public int getLoad() {
		return this.load;
	}

	public TerminalNode getTerminalNode(int term_node) {
		return this.terminal_nodes.get(term_node);
	}

	public HashMap<Integer, TerminalNode> getTerminalNodes() {
		return this.terminal_nodes;
	}

	public BitSet getVisited() {
		return this.visited;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.can_visit == null) ? 0 : this.can_visit.hashCode());
		result = prime * result + this.num_req;
		result = prime * result + ((this.visited == null) ? 0 : this.visited.hashCode());
		return result;
	}

	public boolean isVisited(int i) {
		return this.visited.get(i);
	}

	public String printPath() {
		String str = "";
		for (TerminalNode termNode : this.terminal_nodes.values()) {
			for (Label label : termNode.getLabelList()) {
				str += label;
			}
		}
		return str;
	}

	public void setLoad(int load) {
		this.load = load;
	}

	@Override
	public String toString() {

		return "(" + this.visited + "," + this.can_visit + ")" + this.terminal_nodes.toString();
	}

	public void visitNode(int j) {
		// CRITERIA #1 and #2
		if (j == 0) {
			this.visited.flip(0);
		} else {
			// Delivery Location
			if (j > this.num_req) {
				this.can_visit.flip(j);

			}
			// Pickup Location
			else {
				this.can_visit.flip(this.num_req + j);
				this.can_visit.flip(j);
			}
			this.visited.flip(j);
		}

		this.load += DATA.getLocation(j).getDemand();
	}
}
