package com.cts.springboot.myfirstwebapp.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class SayHelloController {
	
	//'say-hello' => "Hello! what are you learning today?"
	
	//http://localhost:8080/say-hello
	@RequestMapping("/say-hello")
	@ResponseBody //This sends as it is return value to the mapping
	public String sayHello()
	{
		return "Hello! what are you learning today?";
	}
	
	@RequestMapping("/say-hello-html")
	@ResponseBody //This sends as it is return value to the mapping
	public String sayHelloHtml()
	{
		//here we are returning in HTML format 
		//We cannot directly append the string, so we use StringBuffer.
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<title> My first title page </title>");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("My first html page with body");
		sb.append("</body>");
		sb.append("</html>");
		
		return sb.toString();
		
	}
		//say-hello-jsp => sayHello.jsp
		//src/main/resources/META-INF/resources/WEB-INF/jsp/sayHello.jsp
		@RequestMapping("/say-hello-jsp")
		//@ResponseBody  //prints the content in return
		//instead we want to return the sayhello.jsp file
		public String sayHelloJSP()
		{
			return "sayHello";
		}
		
	}
