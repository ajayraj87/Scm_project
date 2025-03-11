package com.smart.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "SETTING")
public class Setting {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userSettingId;
	private String UserName;
	private String UserEmail;
	private String UserPassword;
	private String UserTheme;
	private String UserEnableNotification;
	private String twoFactorAuthentication;
	private String UserDateBackup;

	public Setting() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Setting(int userSettingId, String userName, String userEmail, String userPassword, String userTheme,
			String userEnableNotification, String twoFactorAuthentication, String userDateBackup) {
		super();
		this.userSettingId = userSettingId;
		UserName = userName;
		UserEmail = userEmail;
		UserPassword = userPassword;
		UserTheme = userTheme;
		UserEnableNotification = userEnableNotification;
		this.twoFactorAuthentication = twoFactorAuthentication;
		UserDateBackup = userDateBackup;
	}

	public int getUserSettingId() {
		return userSettingId;
	}

	public void setUserSettingId(int userSettingId) {
		this.userSettingId = userSettingId;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getUserEmail() {
		return UserEmail;
	}

	public void setUserEmail(String userEmail) {
		UserEmail = userEmail;
	}

	public String getUserPassword() {
		return UserPassword;
	}

	public void setUserPassword(String userPassword) {
		UserPassword = userPassword;
	}

	public String getUserTheme() {
		return UserTheme;
	}

	public void setUserTheme(String userTheme) {
		UserTheme = userTheme;
	}

	public String getUserEnableNotification() {
		return UserEnableNotification;
	}

	public void setUserEnableNotification(String userEnableNotification) {
		UserEnableNotification = userEnableNotification;
	}

	public String getTwoFactorAuthentication() {
		return twoFactorAuthentication;
	}

	public void setTwoFactorAuthentication(String twoFactorAuthentication) {
		this.twoFactorAuthentication = twoFactorAuthentication;
	}

	public String getUserDateBackup() {
		return UserDateBackup;
	}

	public void setUserDateBackup(String userDateBackup) {
		UserDateBackup = userDateBackup;
	}

	@Override
	public String toString() {
		return "Setting [userSettingId=" + userSettingId + ", UserName=" + UserName + ", UserEmail=" + UserEmail
				+ ", UserPassword=" + UserPassword + ", UserTheme=" + UserTheme + ", UserEnableNotification="
				+ UserEnableNotification + ", twoFactorAuthentication=" + twoFactorAuthentication + ", UserDateBackup="
				+ UserDateBackup + "]";
	}

}
