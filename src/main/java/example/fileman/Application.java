package example.fileman;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		
		//This allows a user to run the server from a basic Windows UI without special knowledge
		JFrame exitGUI = new JFrame("Server");
		exitGUI.add(new JLabel("Close this window to end the server."));
		exitGUI.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		exitGUI.pack();
		exitGUI.setVisible(true);
		
		SpringApplication.run(Application.class, args);
		
	}
}
