package com.example.demo.entity;

public class pass {
	
	private String code;
	private String encryptedData;
	private String iv;
	private int invite_id;

	public int getInvite_id(){return  invite_id;}

	public void setInvite_id(int invite_id) {this.invite_id=invite_id; }

	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the encryptedData
	 */
	public String getEncryptedData() {
		return encryptedData;
	}
	/**
	 * @param encryptedData the encryptedData to set
	 */
	public void setEncryptedData(String encryptedData) {
		this.encryptedData = encryptedData;
	}
	/**
	 * @return the iv
	 */
	public String getIv() {
		return iv;
	}
	/**
	 * @param iv the iv to set
	 */
	public void setIv(String iv) {
		this.iv = iv;
	}


}
