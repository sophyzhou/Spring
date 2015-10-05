package Spring_framework_example;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.ModelMap;

@Controller
public class HelloWorld_Controller{
 
   //@RequestMapping(method = RequestMethod.GET)
	@RequestMapping("/HelloWorld")

   public ModelAndView helloWorld() {
	   
       String message = "Hello World, Spring 3.0!";
       return new ModelAndView("hello", "message", message);
   }

}
