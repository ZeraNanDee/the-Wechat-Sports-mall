package com.example.demo.entity;


public class wxProduct {
		private int productId;
		private String productName;
		private String picture;
		private double price;
		private int pCount;


	public wxProduct(){

	}
	public wxProduct(int productId, int pCount) {
		this.productId = productId;
		this.pCount = pCount;
	}

	/**
		 * @return the productId
		 */
		public int getProductId() {
			return productId;
		}
		/**
		 * @param productId the productId to set
		 */
		public void setProductId(int productId) {
			this.productId = productId;
		}
		/**
		 * @return the productName
		 */
		public String getProductName() {
			return productName;
		}
		/**
		 * @param productName the productName to set
		 */
		public void setProductName(String productName) {
			this.productName = productName;
		}
		/**
		 * @return the picture
		 */
		public String getPicture() {
			return "http://localhost:8080/image"+picture;
		}
		/**
		 * @param picture the picture to set
		 */
		public void setPicture(String picture) {
			this.picture = picture;
		}
		/**
		 * @return the price
		 */
		public double getPrice() {
			return price;
		}
		/**
		 * @param price the price to set
		 */
		public void setPrice(double price) {
			this.price = price;
		}
		/**
		 * @return the pCount
		 */
		public int getpCount() {
			return pCount;
		}
		/**
		 * @param pCount the pCount to set
		 */
		public void setpCount(int pCount) {
			this.pCount = pCount;
		}
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "wxProduct [productId=" + productId + ", productName=" + productName + ", picture=" + picture
					+ ", price=" + price + ", pCount=" + pCount + "]";
		}
		
		
		
}
