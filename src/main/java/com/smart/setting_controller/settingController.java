package com.smart.setting_controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.config.MyConfig;
import com.smart.emailService.EmailService;
import com.smart.entity.ChangePassword;
import com.smart.entity.Email;
import com.smart.entity.User;
import com.smart.helper.Message;
import com.smart.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Configuration
@Controller
@RequestMapping("/setting")
public class settingController {

	public static long sendOtp = 0;

//	@Autowired
//	private userSettingRepository repository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	public MyConfig config;

	@Autowired
	private EmailService service;

	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String name = principal.getName();

//		System.out.println("user name => "+name);

//		Get the user by using username(String name)	

		User user = this.userRepository.findByName(name).orElse(null);
//		System.out.println("this is all vlaue => "+user);
		model.addAttribute("user", user);
	}

	@PostMapping("/user_setting_form")
	public String userSettingForm(@ModelAttribute User user, ChangePassword password, Model model,
			HttpSession session) {

//		System.err.println(user);

		// get old password And new password

		String hashed = user.getPassword();
		String plainText = password.getCurrent_password();

		try {
			if (BCrypt.checkpw(plainText, hashed)) {

				// Check if the new password and confirm password match
				if (password.getNew_password().equals(password.getConfirm_new_password())) {

					// Encode the new password
					String encodedPassword = config.passwordEncoder().encode(password.getConfirm_new_password());

					// Save the encoded password
					user.setPassword(encodedPassword);

					// Save the user with the updated password
					this.userRepository.save(user);

					// Success message
					session.setAttribute("message",
							new Message("Your password has been successfully updated!", "alert-success"));
					return "redirect:/user/user_setting";
				} else {
					// Error message if passwords don't match
					session.setAttribute("message", new Message(
							"Error: The passwords do not match. Please ensure both passwords are identical and try again.",
							"alert-danger"));
					return "redirect:/user/user_setting";
				}

			} else {
				// Error message if current password is incorrect
				session.setAttribute("message", new Message(
						"The current password you entered is incorrect. Please try again.", "alert-danger"));
				return "redirect:/user/user_setting";
			}

		} catch (Exception e) {
			e.printStackTrace();
			// In case of an exception, redirect to the user settings page
			session.setAttribute("message", new Message("An error occurred. Please try again later.", "alert-danger"));
			return "redirect:/user/user_setting";
		}
	}

//  This is code for the send OTP 

	/*
	 * @GetMapping("/email_form") public String sendEmailForm(Model model) {
	 * model.addAttribute("title", "Email Form"); return "normal_user/email_form"; }
	 */

//	this is send - email - process

	@GetMapping("/send-email")
	public String sendEmail(@ModelAttribute User user, Email email, Model model, HttpSession session) {

		String subject = "Your OTP for Smart Contact Manager Login";

//	  This is final OTP all the page 
		long otp = (long) ((Math.random() * 900000) + 100000);
		sendOtp = otp;

		String sendMaggase = "Hello, [" + user.getName() + "] ,\r\n" + "\r\n"
				+ "We’ve sent a One-Time Password (OTP) to your email. Use the OTP [" + otp
				+ "] to reset your password. The OTP is valid for 10 minutes.\r\n" + "\r\n"
				+ "If you didn’t request this, please ignore this message.\r\n" + "\r\n" + "Thank you,  \r\n"
				+ "[Your Company Name] Team\r\n" + "";

		String toEmail = "ajayraj325729@gmail.com";

		try {
			Boolean is = this.service.sendEmail(toEmail, subject, sendMaggase);
			if (is) {
				return "redirect:/setting/otp_form";
			} else {
				session.setAttribute("message",
						new Message("Something went wrong while sending OTP. Please try again later.", "alert-danger"));
				return "redirect:/user/user_setting";
			}
		} catch (Exception e) {
			// If sending OTP fails
			session.setAttribute("message",
					new Message("Your email does not exist. Please check and try again later.", "alert-danger"));
			return "redirect:/user/user_setting";
		}

	}

//  This is code for the OTP form 

	@GetMapping("/otp_form")
	public String sendOtpForm(Model model) {
		model.addAttribute("title", "Sent Otp Page");
		return "normal_user/otp_form";
	}

//	This is code for the change password with OTP 

	@GetMapping("/send-changePasswordWithOtp")
	public String sendChangePasswordWithPassword() {
		return "normal_user/changePasswordWithOtp.html";
	}

//  This is code use for the click verify OTP

	@PostMapping("/verify-otp")
	public String sendPasswordChangeWithOtp(@ModelAttribute User user, @RequestParam("otp") String getOtp, Model model,
			HttpSession session) {
		model.addAttribute("title", "Password Change Page");

		try {
			if (String.valueOf(sendOtp).equals(getOtp)) {
				System.err.println("This is equals value");
				return "redirect:/setting/send-changePasswordWithOtp";
			} else {
				System.err.println("This is not equals value");
				session.setAttribute("message",
						new Message("The OTP you entered is incorrect. Please try again!", "alert-danger"));
				return "redirect:/setting/otp_form";
			}
		} catch (Exception e) {
			session.setAttribute("message",
					new Message("Something went wrong while sending OTP. Please try again later.", "alert-danger"));
			return "normal_user/changePasswordWithOtp.html";
		}

	}

//	This is code for the change new password and confirm password 

	@PostMapping("/send-pass_confirm_pass")
	public String changeNewPasswordAndConfirmPassword(@ModelAttribute User user, ChangePassword password, Model model,
			HttpSession session) {

		String new_password = password.getNew_password();
		String confirm_new_password = password.getConfirm_new_password();

		try {

			if (new_password.equals(confirm_new_password)) {

				String encode = config.passwordEncoder().encode(confirm_new_password);
				String image = user.getImage();

				user.setImage(image);
				user.setPassword(encode);
//				USER SAVE ALL DATA 
				this.userRepository.save(user);

				// Success message
				session.setAttribute("message",
						new Message("Your password has been successfully updated!", "alert-success"));
				return "redirect:/setting/send-changePasswordWithOtp";

			} else {
				// Error message if passwords don't match
				session.setAttribute("message", new Message(
						"Error: The passwords do not match. Please ensure both passwords are identical and try again.",
						"alert-danger"));
				return "redirect:/setting/send-changePasswordWithOtp";
			}

		} catch (Exception e) {
			// Error message if passwords don't match
			session.setAttribute("message", new Message(
					"Error: The passwords do not match. Please ensure both passwords are identical and try again.",
					"alert-danger"));
			return "redirect:/setting/send-changePasswordWithOtp";
		}

	}

}
