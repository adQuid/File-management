package example.fileman.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

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
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("owner") String owner){			
		try {
			fileService.saveFile(file, owner);
			return new ResponseEntity<String>("successful file upload",HttpStatus.OK);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("failure in saving file",HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@CrossOrigin
	@RequestMapping(value="/metaData", method=RequestMethod.GET)
	public ResponseEntity<String> uploadFile(@RequestParam("file") String file, @RequestParam("name") String name) throws FileNotFoundException{			
		try{
			String retval = fileService.getMetaData(file, name);	
			if(retval == null){
				return new ResponseEntity<String>(retval,HttpStatus.NOT_FOUND);
			}else{
				return new ResponseEntity<String>(retval,HttpStatus.OK);
			}
		}catch(FileNotFoundException e){
			return new ResponseEntity<String>("File not found on server",HttpStatus.NOT_FOUND);
		}catch(Exception e){
			e.printStackTrace();
			return new ResponseEntity<String>("",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@CrossOrigin
	@RequestMapping(value="", method=RequestMethod.GET)
	public ResponseEntity<String> getFileByName(@RequestParam("file") String file, HttpServletResponse response){
		
		byte[] fetched = fileService.searchByName(file);
		
		if(fetched==null){
			return new ResponseEntity<String>("",HttpStatus.NOT_FOUND);
		}else{
			try {
				response.setHeader("Content-Disposition", "attachment; filename="+file);	
				response.setContentLength(fetched.length);
				response.getOutputStream().write(fetched);
				return new ResponseEntity<String>("",HttpStatus.OK);
			} catch (IOException e) {
				return new ResponseEntity<String>("",HttpStatus.INTERNAL_SERVER_ERROR);
			}		
		}		
	}
}
