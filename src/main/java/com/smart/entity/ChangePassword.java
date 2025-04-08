package com.smart.entity;

public class ChangePassword {

	private String current_password;
	private String new_password;
	private String confirm_new_password;

	public ChangePassword() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ChangePassword(String current_password, String new_password, String confirm_new_password) {
		super();
		this.current_password = current_password;
		this.new_password = new_password;
		this.confirm_new_password = confirm_new_password;
	}

	public String getCurrent_password() {
		return current_password;
	}

	public void setCurrent_password(String current_password) {
		this.current_password = current_password;
	}

	public String getNew_password() {
		return new_password;
	}

	public void setNew_password(String new_password) {
		this.new_password = new_password;
	}

	public String getConfirm_new_password() {
		return confirm_new_password;
	}

	public void setConfirm_new_password(String confirm_new_password) {
		this.confirm_new_password = confirm_new_password;
	}

	@Override
	public String toString() {
		return "ChangePassword [current_password=" + current_password + ", new_password=" + new_password
				+ ", confirm_new_password=" + confirm_new_password + "]";
	}

}
