/**
 * 
 * Controller for User View page
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


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.concurrent.Task;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.*;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.io.IOException;
import java.util.Optional;

public class UserViewController {
	
	
    @FXML
    private Button logoutButton;

    @FXML
    private Button openButton;
    
    @FXML
    private Label userAlbum;

    @FXML
    private ListView<Album> albumList;

    @FXML
    private Label photoCount;

    @FXML
    private Label oldestPhoto;

    @FXML
    private Label newestPhoto;

    @FXML
    private TextField createText;

    @FXML
    private Button deleteButton;

    @FXML
    private Button addButton;

    @FXML
    private Button renameButton;

    @FXML
    private TextField renameText;

   
    private Backend list;
    private User user;
    private List<Album> albums;
    private ObservableList<Album> items;
    Album selectedAlbum;
    
   
    /** loads data and initializes page
     * 
     * @param mainStage
     */
    public void start(Stage mainStage){
    	userAlbum.setText(user.getUsername()+"'s Albums");
    			
    	albums = user.getAlbums();
    	items =FXCollections.observableArrayList(albums);
    	
    	albumList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Album>(){	
		@Override
			public void changed(ObservableValue<? extends Album> observable, Album oldVal, Album newVal) {	
				albumSelection(newVal);
			}
    	});
    	
    	albumList.setItems(items);
    }
    
    public void setUser(User u){
    	user = u;
    }

    public void setBackend(Backend l){
    	this.list = l;
    	
    }
    
    /**
     * selection and listener for selected element
     * @param album
     */
    @FXML
    public void albumSelection(Album album) {
		if(album != null) {
			selectedAlbum = album;
			photoCount.setText(selectedAlbum.getSize());
			oldestPhoto.setText(selectedAlbum.oldestPhotoDate());
			newestPhoto.setText(selectedAlbum.newestPhotoDate());
		
		}
    	
    }
    
    
    /** opens album goes to albumview
     * 
     * @param event
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @FXML
    public void openAlbum(ActionEvent event) throws IOException, ClassNotFoundException {
    	
    	Album open = albumList.getSelectionModel().getSelectedItem();
    	
    	if(open != null) {
    		Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Open Album");
			alert.setHeaderText("Confirm opening Album");
		
			Optional<ButtonType> result = alert.showAndWait();
			if(result.get() == ButtonType.OK){	
				FXMLLoader loader = new FXMLLoader();
    			loader.setLocation(getClass().getResource("/view/AlbumView.fxml"));
    			Parent root = loader.load();    
				Scene scene = new Scene(root);		
				Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();	
				
				AlbumViewController controller = loader.getController();
				controller.setUser(user);
				controller.setBackend(list);
				controller.setAlbum(open);

				controller.start(appStage);
			    
			    appStage.setScene(scene);
			    appStage.show();
			}
    	}
    	
    	
    }
    
    /**
     * deletes selected album
     * @param event
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @FXML
    public void deleteAlbumButton(ActionEvent event) throws IOException, ClassNotFoundException {
 
    	
    	Album delete = albumList.getSelectionModel().getSelectedItem();
    	
    	if(delete!= null) {
    	
    		Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Delete Album");
			alert.setHeaderText("Confirm deleted Album");
		
			Optional<ButtonType> result = alert.showAndWait();
			if(result.get() == ButtonType.OK){	
				albums.remove(delete);
				items =FXCollections.observableArrayList(albums);
		    	albumList.setItems(items);
			}
    	}
    	
    	Backend.saveSession(list);
    	
		
    }
    
    /** adds album
     * 
     * @param event
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @FXML
    public void addAlbumButton(ActionEvent event) throws IOException, ClassNotFoundException {
        
    	if ((createText.getText()==null) || createText.getText().equals("")) {
    		Alert fail= new Alert(AlertType.INFORMATION);
            fail.setHeaderText("Warning");
            fail.setContentText("Please enter a valid album name");
            fail.showAndWait();
            return;
    	}
    	
    	String newName = createText.getText().trim();
    	
    	Album newAlbum = new Album(newName);
    	
    	
    	if (user.albumExists(newName)){
        	Alert fail= new Alert(AlertType.INFORMATION);
            fail.setHeaderText("Warning");
            fail.setContentText("Album name Already Exists");
            fail.showAndWait();
            return;
        }
    		

    	Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Add album");
		alert.setHeaderText("Confirm added album");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK){	
			albums.add(newAlbum);
			items =FXCollections.observableArrayList(albums);
	    	albumList.setItems(items);
		}
		
		Backend.saveSession(list);
    }
    
    /**
     * renames selected album
     * @param event
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @FXML
    public void renameAlbumButton(ActionEvent event) throws IOException, ClassNotFoundException {
        
    	Album rename = albumList.getSelectionModel().getSelectedItem();
    	
    	if(rename!= null) {
    		
    	
    	if ((renameText.getText()==null) || renameText.getText().equals("")) {
    		Alert fail= new Alert(AlertType.INFORMATION);
            fail.setHeaderText("Warning");
            fail.setContentText("Please enter a valid album name");
            fail.showAndWait();
            return;
    	}
    	
    	String newName = renameText.getText().trim();
    	
    	
    	if (user.albumExists(newName)){
        	Alert fail= new Alert(AlertType.INFORMATION);
            fail.setHeaderText("Warning");
            fail.setContentText("Album name Already Exists");
            fail.showAndWait();
            return;
        }
    		

    	Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Rename album");
		alert.setHeaderText("Confirm edit");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK){	
			rename.setName(newName);
			albums.remove(rename);
			albums.add(rename);
			items = FXCollections.observableArrayList(albums);
			albumList.setItems(items);	
			
		}
		
    	}
		
		Backend.saveSession(list);
	
    }
    
    
    /**
     * logout
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

