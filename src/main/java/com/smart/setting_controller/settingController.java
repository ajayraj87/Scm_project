package com.smart.setting_controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smart.config.MyConfig;
import com.smart.entity.Setting;
import com.smart.entity.User;
import com.smart.helper.Message;
import com.smart.repository.UserRepository;
import com.smart.repository.userSettingRepository;

import jakarta.servlet.http.HttpSession;


@Configuration
@Controller
@RequestMapping("/setting")
public class settingController {
	
	@Autowired
	private userSettingRepository repository;
	
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
  public String userSettingForm(@ModelAttribute Setting s , Model model, HttpSession session) {
  	try {
  		
  		System.out.println("USER SETTING PAGE CLICK");
  	  	model.addAttribute("title","Setting Page");
  	  	String userPassword = s.getUserPassword();
  	  	String passwordEncoder = config.passwordEncoder().encode(userPassword);
  	  	s.setUserPassword(passwordEncoder);
  	  	Setting save = this.repository.save(s);
  	  	System.err.println(save);
  		
		session.setAttribute("message", new Message("Successfull add contact !! Added more !!", "alert-success"));
		return "normal_user/Add_Details.html";

	} catch (Exception e) {
		session.setAttribute("massage", new Message("Some thing went wrong !! try again !!", "alert-success"));
		return "redirect:/user/user_setting";
	}
  	
  }

}
