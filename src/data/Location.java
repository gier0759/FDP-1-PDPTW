package data;

public class Location implements Comparable {

	private int id = -1;
	private int ref = -1;
	private int cordX = -1;
	private int cordY = -1;
	private int demand = -1;
	private int earliestTimeInterval = -1;
	private int latestTimeInterval = -1;
	private int serviceTime = -1;
	private int pickupSibling = -1;
	private int deliverySibling = -1;

	public Location(String line) {
		parseLine(line);
	}

	@Override
	public int compareTo(Object obj) {
		Location j = (Location) obj;
		return (this.latestTimeInterval < j.getLatestTimeInterval()) ? -1
				: (this.latestTimeInterval > j.getLatestTimeInterval()) ? 1 : 0;
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
		Location other = (Location) obj;
		if (this.cordX != other.cordX) {
			return false;
		}
		if (this.cordY != other.cordY) {
			return false;
		}
		if (this.deliverySibling != other.deliverySibling) {
			return false;
		}
		if (this.demand != other.demand) {
			return false;
		}
		if (this.earliestTimeInterval != other.earliestTimeInterval) {
			return false;
		}
		if (this.id != other.id) {
			return false;
		}
		if (this.latestTimeInterval != other.latestTimeInterval) {
			return false;
		}
		if (this.pickupSibling != other.pickupSibling) {
			return false;
		}
		if (this.ref != other.ref) {
			return false;
		}
		if (this.serviceTime != other.serviceTime) {
			return false;
		}
		return true;
	}

	public int getCordX() {
		return this.cordX;
	}

	public int getCordY() {
		return this.cordY;
	}

	public int getDeliverySibling() {
		return this.deliverySibling;
	}

	public int getDemand() {
		return this.demand;
	}

	public int getEarliestTimeInterval() {
		return this.earliestTimeInterval;
	}

	public int getId() {
		return this.id;
	}

	public int getLatestTimeInterval() {
		return this.latestTimeInterval;
	}

	public int getPickupSibling() {
		return this.pickupSibling;
	}

	public Integer getRef() {
		return this.ref;
	}

	public int getServiceTime() {
		return this.serviceTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.cordX;
		result = prime * result + this.cordY;
		result = prime * result + this.deliverySibling;
		result = prime * result + this.demand;
		result = prime * result + this.earliestTimeInterval;
		result = prime * result + this.id;
		result = prime * result + this.latestTimeInterval;
		result = prime * result + this.pickupSibling;
		result = prime * result + this.ref;
		result = prime * result + this.serviceTime;
		return result;
	}

	public boolean isDelivery() {
		return (this.pickupSibling != 0 && this.deliverySibling == 0);
	}

	public boolean isPickup() {
		return (this.pickupSibling == 0 && this.deliverySibling != 0);
	}

	private void parseLine(String line) {
		String[] lineseg = line.split("\\s");
		if (lineseg.length == 10) {
			this.id = Integer.parseInt(lineseg[0]);
			this.ref = Integer.parseInt(lineseg[1]);
			this.cordX = Integer.parseInt(lineseg[2]);
			this.cordY = Integer.parseInt(lineseg[3]);
			this.demand = Integer.parseInt(lineseg[4]);
			this.earliestTimeInterval = Integer.parseInt(lineseg[5]);
			this.latestTimeInterval = Integer.parseInt(lineseg[6]);
			this.serviceTime = Integer.parseInt(lineseg[7]);
			this.pickupSibling = Integer.parseInt(lineseg[8]);
			this.deliverySibling = Integer.parseInt(lineseg[9]);
		} else {
			this.id = Integer.parseInt(lineseg[0]);
			this.cordX = Integer.parseInt(lineseg[1]);
			this.cordY = Integer.parseInt(lineseg[2]);
			this.demand = Integer.parseInt(lineseg[3]);
			this.earliestTimeInterval = Integer.parseInt(lineseg[4]);
			this.latestTimeInterval = Integer.parseInt(lineseg[5]);
			this.serviceTime = Integer.parseInt(lineseg[6]);
			this.pickupSibling = Integer.parseInt(lineseg[7]);
			this.deliverySibling = Integer.parseInt(lineseg[8]);
		}
	}

	@Override
	public String toString() {
		return String.format("%d(%d) [%d,%d] %d %d", this.id, this.ref, this.earliestTimeInterval,
				this.latestTimeInterval, this.serviceTime, this.demand);
	}
}
