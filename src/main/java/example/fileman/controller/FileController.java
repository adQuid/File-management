package example.fileman.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import example.fileman.service.FileService;

@RestController
@RequestMapping("/file")
public class FileController {
		
	@Autowired
	FileService fileService;
	
	@CrossOrigin
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file){		
		
		
		try {
			fileService.saveFile(file, "test owner");
			return new ResponseEntity<String>("successful file upload",HttpStatus.OK);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("failure in saving file",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
		
}
