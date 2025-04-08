package com.smart.setting_controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smart.config.MyConfig;
import com.smart.entity.ChangePassword;
import com.smart.entity.User;
import com.smart.helper.Message;
import com.smart.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Configuration
@Controller
@RequestMapping("/setting")
public class settingController {

//	@Autowired
//	private userSettingRepository repository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	public MyConfig config;

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
	public String userSettingForm(@ModelAttribute User user, ChangePassword password , Model model, HttpSession session) {
	  
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
		            session.setAttribute("message", new Message("Your password has been successfully updated!", "alert-success"));
		            return "redirect:/user/user_setting"; 
		        } else {
		            // Error message if passwords don't match
		            session.setAttribute("message", new Message("Error: The passwords do not match. Please ensure both passwords are identical and try again.", "alert-danger"));
		            return "redirect:/user/user_setting"; 
		        }
		        
		    } else {
		        // Error message if current password is incorrect
		        session.setAttribute("message", new Message("The current password you entered is incorrect. Please try again.", "alert-danger"));
		        return "redirect:/user/user_setting";
		    }
		    
		} catch (Exception e) {
		    e.printStackTrace();
		    // In case of an exception, redirect to the user settings page
		    session.setAttribute("message", new Message("An error occurred. Please try again later.", "alert-danger"));
		    return "redirect:/user/user_setting";
		}
	}

}
