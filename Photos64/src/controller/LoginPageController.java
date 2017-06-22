/**
 * 
 * 	Controller for Login Page
 * 
 * @author Justin Chong
 * @author Matthew Reyes
 * 
 */


package controller;

import model.Backend;
import model.User;
import model.Album;
import model.Photo;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.scene.image.*;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import java.awt.image.BufferedImage;
import java.io.File;

public class LoginPageController {

    @FXML
    private TextField usernameText;

    @FXML
    private Button loginButton;

    @FXML
    private Label inputMessage;
    
   
    /** initializes Backend user list
     * 
     * @param e
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void login(ActionEvent e) throws IOException, ClassNotFoundException{
    	
    	/*
    	
    	Backend list = new Backend();
    	User stock = new User("Stock");
    	
    	BufferedImage img1 = null;
    	try {
    	    img1 = ImageIO.read(new File("Stock/Cat1.jpg"));
    	} catch (IOException exception) {
    	}
    	Image image1 = SwingFXUtils.toFXImage(img1, null);
    	Photo photo1 = new Photo(image1);
    	
    	BufferedImage img2 = null;
    	try {
    	    img2 = ImageIO.read(new File("Stock/Cat2.jpg"));
    	} catch (IOException exception) {
    	}
    	Image image2 = SwingFXUtils.toFXImage(img2, null);
    	Photo photo2 = new Photo(image2);
    	
    	BufferedImage img3 = null;
    	try {
    	    img3 = ImageIO.read(new File("Stock/Cat3.jpg"));
    	} catch (IOException exception) {
    	}
    	Image image3 = SwingFXUtils.toFXImage(img3, null);
    	Photo photo3 = new Photo(image3);
    	
    	BufferedImage img4 = null;
    	try {
    	    img4 = ImageIO.read(new File("Stock/Dog1.jpg"));
    	} catch (IOException exception) {
    	}
    	Image image4 = SwingFXUtils.toFXImage(img4, null);
    	Photo photo4 = new Photo(image4);
    	
    	BufferedImage img5 = null;
    	try {
    	    img5 = ImageIO.read(new File("Stock/Dog2.jpg"));
    	} catch (IOException exception) {
    	}
    	Image image5 = SwingFXUtils.toFXImage(img5, null);
    	Photo photo5 = new Photo(image5);
    	
    	list.addUser(stock);
    	
    	Album cats = new Album("Cats");
    	cats.addPhoto(photo1);
    	cats.addPhoto(photo2);
    	cats.addPhoto(photo3);
    	
    	Album dogs = new Album("Dogs");
    	dogs.addPhoto(photo4);
    	dogs.addPhoto(photo5);
    
    	
    	stock.addExisting(cats);
    	stock.addExisting(dogs);
  
    	 */
    	
    	Backend list = Backend.loadData();
    	Backend.saveSession(list);
 
		if(usernameText.getText() == null || usernameText.getText().isEmpty()){
    		inputMessage.setText("Username field is empty try again");
    	}
    	
    	String username = usernameText.getText();
    	
    	try {
    		if(username.toLowerCase().equals("admin")){
    			
    			FXMLLoader loader = new FXMLLoader();
    			loader.setLocation(getClass().getResource("/view/AdminPage.fxml"));
				Parent root = loader.load();
				        
				Scene scene = new Scene(root);		
				Stage adminStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
				
			             
			    adminStage.setScene(scene);
			    adminStage.show(); 
    		}
    		else if(list.userExists(username)){
    		
    			FXMLLoader loader = new FXMLLoader();
    			loader.setLocation(getClass().getResource("/view/UserView.fxml"));
    			Parent root = loader.load();    
				Scene scene = new Scene(root);		
				Stage appStage = (Stage) ((Node) e.getSource()).getScene().getWindow();	
				
				UserViewController controller = loader.getController();
				controller.setUser(list.getUser(username));

				controller.setBackend(list);

				
				controller.start(appStage);
			    
			    appStage.setScene(scene);
			    appStage.show(); 
    		}
    		else{
    			inputMessage.setText("Username does not exist");
    		}
    	}catch (IOException exception) {
    		exception.printStackTrace();
		}
    	
    	
    }

}
