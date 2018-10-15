package edu.xmu.test.guava.basic;

public class State {
	String stateName;
	String stateAddress;

	public State(String stateName, String stateAddress) {
		super();
		this.stateName = stateName;
		this.stateAddress = stateAddress;
	}

	public String getStateName() {
		return stateName;
	}

	public String getStateAddress() {
		return stateAddress;
	}

	@Override
	public String toString() {
		return "State [stateName=" + stateName + ", stateAddress=" + stateAddress + "]";
	}
}
