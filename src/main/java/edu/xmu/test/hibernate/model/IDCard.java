package edu.xmu.test.hibernate.model;

public class IDCard {
	private int id;
	private String cardNo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	@Override
	public String toString() {
		return "IDCard [id=" + id + ", cardNo=" + cardNo + "]";
	}

}
