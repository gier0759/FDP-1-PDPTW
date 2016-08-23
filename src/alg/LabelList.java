package alg;

import java.util.ArrayList;
import java.util.Collections;

import data.DATA;

public class LabelList {

	public static void main(String[] args) {
	}

	public ArrayList<Label> labelList;

	public LabelList() {
		this.labelList = new ArrayList<>();
	}

	public void addLabel(Label label) {
		this.labelList.add(label);
	}

	public void addLabels(ArrayList<Label> labels) {
		for (int i = 0; i < labels.size(); i++) {
			this.labelList.add(labels.get(i));
		}
	}

	public void addNewLabels(ArrayList<Label> labels, int new_node) {
		for (int i = 0; i < labels.size(); i++) {
			int old_node = labels.get(i).term_node;
			Label label = new Label(labels.get(i).term_node, labels.get(i).time, labels.get(i).distance, labels.get(i));
			label.distance += DATA.getDist(old_node, new_node);
			label.time = Math.max(DATA.getTime(old_node, new_node) + DATA.getServiceTime(old_node) + label.time,
					DATA.getLocation(new_node).getEarliestTimeInterval());
			label.term_node = new_node;
			// CRITERIA #5: TIME CONSTRAINT MUST BE RESPECTED
			if (label.time <= DATA.getLocation(new_node).getLatestTimeInterval()) {
				this.labelList.add(label);
			}
		}
	}

	public void eliminateLabels() {
		Collections.sort(this.labelList);
		Label label_check = this.labelList.get(0);
		ArrayList<Label> buffer = new ArrayList<>();
		buffer.add(label_check);
		for (int i = 1; i < this.labelList.size(); i++) {
			Label label_current = this.labelList.get(i);
			if (!label_current.eliminated) {
				if (label_check.distance > label_current.distance) {
					label_check = label_current;
					buffer.add(label_check);
				}
			}
		}
		this.labelList = buffer;

	}

	public ArrayList<Label> getLabelList() {
		return this.labelList;
	}

	@Override
	public String toString() {
		return this.labelList.toString();
	}

}
