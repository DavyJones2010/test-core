package edu.xmu.test.spring.batch.model;

import java.math.BigDecimal;

public class Trade {
	private String isin;
	private int quantity;
	private BigDecimal price;
	private String customer;

	public Trade() {
		super();
	}

	public Trade(String isin, int quantity, BigDecimal price, String customer) {
		super();
		this.isin = isin;
		this.quantity = quantity;
		this.price = price;
		this.customer = customer;
	}

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "Trade [isin=" + isin + ", quantity=" + quantity + ", price=" + price + ", customer=" + customer + "]";
	}

}
