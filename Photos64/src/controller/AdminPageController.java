/**
 * 
 * 	Controller for Admin Page
 * 
 * @author Justin Chong
 * @author Matthew Reyes
 * 
 */

package controller;

import model.Backend;
import model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import javafx.fxml.FXMLLoader;


import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.*;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import java.util.Optional;


public class AdminPageController {

	@FXML
    private Button deleteButton;
   
   
    @FXML
    private ListView<User> userlist;
    
    
    @FXML
    private Button addButton;
    
    @FXML
    private Button logoutButton;

    @FXML
    private TextField usernameText;
    
    @FXML
    private Label inputMessage;
    
    private Backend list;
	private List<User> users = new ArrayList<User>();
	ObservableList<User> items;

	/**
	 * 
	 * Initializes data od Backend User List
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
    public void initialize() throws IOException, ClassNotFoundException{
    	
    	inputMessage.setVisible(false);
    	
    	list = Backend.loadData();
    	users = list.getUsers();
    	items =FXCollections.observableArrayList(users);
    	userlist.setItems(items);
    	
    }
    
    /**
     * Adds user to Backend user list
     * @param event
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @FXML
    public void addUserButton(ActionEvent event) throws IOException, ClassNotFoundException {
    
    	if ((usernameText.getText()==null) || usernameText.getText().equals("")) {
    		Alert fail= new Alert(AlertType.INFORMATION);
            fail.setHeaderText("Warning");
            fail.setContentText("Please enter a valid username");
            fail.showAndWait();
            return;
    	}
    	
    	String newName = usernameText.getText().trim();
    	
    	User newUser = new User(newName);
    	
    	
    	if (list.userExists(newName)){
        	Alert fail= new Alert(AlertType.INFORMATION);
            fail.setHeaderText("Warning");
            fail.setContentText("Username Already Exists");
            fail.showAndWait();
            return;
        }
    		

    	Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Add user");
		alert.setHeaderText("Confirm added User");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK){	
			users.add(newUser);
			items =FXCollections.observableArrayList(users);
	    	userlist.setItems(items);
		}
		
		Backend.saveSession(list);
    	
    }
    
    /**
     * deletes user from backend user list
     * @param event
     * @throws IOException
     * @throws ClassNotFoundException
     */
    
    @FXML
    public void deleteUserButton(ActionEvent event) throws IOException, ClassNotFoundException {
 
    	
    	User delete = userlist.getSelectionModel().getSelectedItem();
    	
    	if(delete!= null) {
    	
    		Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Delete User");
			alert.setHeaderText("Confirm deleted User");
		
			Optional<ButtonType> result = alert.showAndWait();
			if(result.get() == ButtonType.OK){	
				users.remove(delete);
				items =FXCollections.observableArrayList(users);
		    	userlist.setItems(items);
			}
    	}
    	
    	Backend.saveSession(list);
    	
		
    }
    
    /**Logout
     * 
     * @param event
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @FXML
    public void logout(ActionEvent event) throws IOException, ClassNotFoundException {
    	Backend.saveSession(list);
    	Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Logout");
		alert.setHeaderText("Are you sure you want to logout?");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK){	
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/LoginPage.fxml"));
			Parent root = loader.load();
			        
			Scene scene = new Scene(root);		
			Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		             
		    appStage.setScene(scene);
		    appStage.show(); 
		} 	
    	
    	
		
    }
    
}
