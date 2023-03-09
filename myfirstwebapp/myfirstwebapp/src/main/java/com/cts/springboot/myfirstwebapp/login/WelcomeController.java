package com.cts.springboot.myfirstwebapp.login;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("name")
public class WelcomeController {

	// /login url should redirect to login.jspt

	// RequestParam - we pass a query in the url
	// http://localhost:8080/login?name=Vishal
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String gotoWelcomePage(ModelMap model) {
		model.put("name", getLoggedinUsername());
		return "welcome";
	}
	
	private String getLoggedinUsername() {
		Authentication authentication = 
				SecurityContextHolder.getContext().getAuthentication();
		//here we get user from authentication
		return authentication.getName();
	}

}


//Model - To pass the info from controller to jsp.
//to print logger.debug() - in application.properties the logging level should be set to info
//logger.inf0() - prints info level logging.
//before spring we used to create using authentication = new authentication();
//but we don't do that in spring, instead spring should take care.
//Here we wire the authenticationservice using constructor injection.

//@RequestMapping(value="login", method = RequestMethod.POST)
//public String gotoWelcomePage(@RequestParam String name, @RequestParam String password, 
//		ModelMap model)
//{
//	if(authenticationService.authenticate(name, password)) {
//	model.put("name", name);
//	return "welcome";
//	}
//	
//	model.put("errorMessage", "Invalid Credentials!, Please check again!!");
//	return "login";
//	
//}

//getAuthentication() = we can get the user who is authenticated.