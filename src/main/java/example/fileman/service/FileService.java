package example.fileman.service;

import java.io.File;
import java.io.IOException;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



@Service
@Component
@EnableScheduling
public class FileService {
	
	public void saveFile(MultipartFile file, String owner) throws IllegalStateException, IOException{
		
		//basic measure to keep people from messing with the file structure
		String modifiedFilename = file.getOriginalFilename().replaceAll("'\'", "");
		String modifiedPath = System.getenv("FILEPATH").replaceAll("'\'", "\\");
		
		System.out.println(modifiedFilename);
		File folder = new File(modifiedPath+modifiedFilename.substring(0, modifiedFilename.indexOf('.')));
		File toAdd = new File(modifiedPath+modifiedFilename.substring(0, modifiedFilename.indexOf('.'))+"\\"+modifiedFilename);
		if(folder.mkdir()){
			file.transferTo(toAdd);
		}else{
			System.err.println("failure to make directory. canWrite is "+toAdd.canWrite());
			throw new IOException();
		}
		
		//File metaData = new File(D:\\misc games\\savedata\\"+modifiedFilename+"\\"+modifiedFilename)
		
	}
	
}
