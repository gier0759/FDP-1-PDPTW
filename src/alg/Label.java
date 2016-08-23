package alg;

import java.util.Locale;

public class Label implements Comparable<Object> {
	public double time;
	public double distance;
	public boolean eliminated = false;
	public int term_node;
	public Label previous_label = null;

	public Label(int term_node, double time, double distance, Label previous_label) {
		Locale.setDefault(Locale.ENGLISH);
		this.time = time;
		this.distance = distance;
		this.term_node = term_node;
		this.previous_label = previous_label;
	}

	@Override
	public int compareTo(Object o) {
		Label label = (Label) o;
		if (this.time > label.time) {
			return 1;
		} else {
			if (this.time < label.time) {
				return -1;
			} else {
				if (this.distance < label.distance) {
					return -1;
				} else if (this.distance == label.distance) {
					return 0;
				} else {
					return 1;
				}
			}
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
		Label other = (Label) obj;
		if (Double.doubleToLongBits(this.distance) != Double.doubleToLongBits(other.distance)) {
			return false;
		}
		if (this.eliminated != other.eliminated) {
			return false;
		}
		if (this.term_node != other.term_node) {
			return false;
		}
		if (Double.doubleToLongBits(this.time) != Double.doubleToLongBits(other.time)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(this.distance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (this.eliminated ? 1231 : 1237);
		result = prime * result + this.term_node;
		temp = Double.doubleToLongBits(this.time);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public String toString() {
		return String.format("(%.2f, %.2f)", this.time, this.distance);
	}

}
