package example.fileman.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



@Service
@Component
@EnableScheduling
public class FileService {
	
	private final static String DELIMITER = ":=";
	
	public void saveFile(MultipartFile file, String owner) throws IllegalStateException, IOException{
		
		//basic measure to keep people from messing with the file structure
		String modifiedFilename = file.getOriginalFilename().replaceAll("'\'", "");
		
		//if the filename will cause a collision with the metadata, stop now
		if(modifiedFilename.equals("metadata.txt")){
			throw new IOException();
		}
		
		String modifiedPath = getModifiedPath();
		int periodIndex = modifiedFilename.indexOf('.')==-1?modifiedFilename.length():modifiedFilename.indexOf('.');
		
		System.out.println(modifiedFilename);
		File folder = new File(modifiedPath+modifiedFilename.substring(0, periodIndex));
		File toAdd = new File(modifiedPath+modifiedFilename.substring(0, periodIndex)+"\\"+modifiedFilename);
		if(folder.mkdir()){
			file.transferTo(toAdd);
		}else{
			System.err.println("failure to make directory. canWrite is "+toAdd.canWrite());
			throw new IOException();
		}
		
		//populate automatically calculated metadata
		SimpleDateFormat ISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		String uploadDate = ISO.format(new Date());
		
		
		BufferedWriter metaData = new BufferedWriter(new FileWriter(new File(modifiedPath+modifiedFilename.substring(0, periodIndex)+"\\metadata.txt")));
		metaData.write("Owner "+DELIMITER+" "+owner);
		metaData.newLine();
		metaData.write("Upload_Date "+DELIMITER+" "+uploadDate);
		metaData.close();
	}
	
	public String getMetaData(String file, String name) throws FileNotFoundException{
		String modifiedFilename = file.replaceAll("'\'", "");
		String modifiedPath = getModifiedPath();
		int periodIndex = modifiedFilename.indexOf('.')==-1?modifiedFilename.length():modifiedFilename.indexOf('.');

		Scanner reader = new Scanner(new File(modifiedPath+modifiedFilename.substring(0, periodIndex)+"\\metadata.txt"));
		while(reader.hasNextLine()){
			String current = reader.nextLine();
			System.out.println(current);
			String type = current.substring(0, current.indexOf(DELIMITER));
			if(type.toLowerCase().trim().equals(name.toLowerCase().trim())){
				reader.close();
				return current.substring(current.indexOf(DELIMITER)+2);
			}
		}
		
		reader.close();
		return null;
	}
	
	private String getModifiedPath(){
		return System.getenv("FILEPATH").replaceAll("'\'", "\\");
	}
	
}
