package example.fileman.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MiscController {
		
	@CrossOrigin
	@RequestMapping("/test")
	public String getHealth(){		
		return "OK";
	}
		
}
