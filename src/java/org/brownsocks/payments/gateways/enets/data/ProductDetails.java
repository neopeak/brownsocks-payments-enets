package org.brownsocks.payments.gateways.enets.data;

import java.io.Serializable;

public class ProductDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private String sku;
	private String price;
	private int quantity;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
