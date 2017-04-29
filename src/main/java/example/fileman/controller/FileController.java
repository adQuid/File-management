package example.fileman.controller;

import java.io.File;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {
		
	@CrossOrigin
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public String uploadFile(@RequestParam("file") MultipartFile file){		
		return "File uploaded of length "+file.getSize()+" bytes";
	}
		
}
