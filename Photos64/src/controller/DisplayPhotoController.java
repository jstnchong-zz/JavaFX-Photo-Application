/**
 * 
 * Controller for DisplayPhoto
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
import model.Tag;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.embed.swing.SwingFXUtils;
import javafx.stage.FileChooser;
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
import javafx.scene.control.ListCell;
import javafx.beans.value.ChangeListener;
import javafx.scene.shape.Rectangle;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;

import controller.AlbumViewController.ImageViewCell;

import java.io.File;
import java.awt.image.BufferedImage;
import javafx.util.Callback;


import java.io.IOException;
import java.util.Optional;

public class DisplayPhotoController {
	
	private Backend list;
	private User user;
	private Album album;
	private List<Photo> photos;
	private Photo photo;
	private List<Tag> tags;
	ObservableList<Tag> items;
	private int index;

    @FXML
    private Button addButton;

    @FXML
    private Button prevButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button deleteButton;

    @FXML
    private ImageView image;

    @FXML
    private ListView<Tag> tagList;

    @FXML
    private Button logoutButton;

    @FXML
    private TextField tagType;
    
    @FXML
    private TextField tagValue;

    @FXML
    private Label captionLabel;

    @FXML
    private Label dateLabel;
    
    /** initializes data for photo, album and tags
     * 
     * @param primaryStage
     */
    public void start(Stage primaryStage){
    	
    	tags = photo.getAllTags();
    	photos = album.getPhotos();
    	
		items =FXCollections.observableArrayList(tags);
		
		image.setImage(photo.getImage());
		image.fitWidthProperty().bind(primaryStage.widthProperty());
		
		
		tagList.setItems(items);
		captionLabel.setText(photo.getCaption());
		dateLabel.setText(photo.formatDate());
	
		Image im = photo.getImage();
		for (int i = 0; i < photos.size(); i++){
			Photo a = photos.get(i);
	        if (im.equals(a.getImage()))
	        {
	            index = i;
	        }
		}
		
		System.out.println(index);
		
    }
    
    
    
    public void setUser(User u){
    	this.user = u;
    }

    public void setBackend(Backend l){
    	this.list = l;
    	
    }
    
    public void setAlbum(Album a){
    	this.album = a;
    }
    
    public void setPhoto(Photo p){
    	this.photo = p;
    }
   
    /**
     * logout
     * @param event
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @FXML
    void logout(ActionEvent event) throws IOException, ClassNotFoundException{
    	
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
    
    /**
     * deletes tag
     * @param event
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @FXML
    void deleteTag(ActionEvent event) throws IOException, ClassNotFoundException{
    	Tag delete = tagList.getSelectionModel().getSelectedItem();
    	
    	if(delete!= null) {
    	
    		Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Delete Album");
			alert.setHeaderText("Confirm deleted Tag");
		
			Optional<ButtonType> result = alert.showAndWait();
			if(result.get() == ButtonType.OK){	
				tags.remove(delete);
				items =FXCollections.observableArrayList(tags);
		    	tagList.setItems(items);
			}
    	}
    	
    	Backend.saveSession(list);
    }
    
    /**adds tag
     * 
     * @param event
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @FXML
    public void addTag(ActionEvent event) throws IOException, ClassNotFoundException {
        
    	if ((tagType.getText()==null) || tagValue.getText()==null || tagType.getText().isEmpty() ||  tagValue.getText().isEmpty()) {
    		Alert fail= new Alert(AlertType.INFORMATION);
            fail.setHeaderText("Warning");
            fail.setContentText("Please enter both value and type");
            fail.showAndWait();
            return;
    	}
    	
    	String newType = tagType.getText().trim();
    	String newValue = tagValue.getText().trim();
  
    	
    	Tag newTag = new Tag(newType, newValue);
    	
    	boolean tagExists = false;
    	for(Tag t: tags){
    		if(newTag.equals(t)){
    			tagExists = true;
    		}
    	}
    	
    	
    	if (tagExists==true){
        	Alert fail= new Alert(AlertType.INFORMATION);
            fail.setHeaderText("Warning");
            fail.setContentText("Tag Already Exists");
            fail.showAndWait();
            return;
        }
    		

    	Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Add Tag");
		alert.setHeaderText("Confirm added Tag");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK){	
			tags.add(newTag);
			items =FXCollections.observableArrayList(tags);
	    	tagList.setItems(items);
		}
		
		Backend.saveSession(list);
    }
    
    /** changes photo display
     * 
     */
    public void changePhoto(){
    	Photo change = photos.get(index);
    	tags = change.getAllTags();
    	
		items =FXCollections.observableArrayList(tags);
		
		image.setImage(change.getImage());
		//image.setFitWidth(630);
		//image.setFitHeight(350);
    	tagList.setItems(items);
		captionLabel.setText(change.getCaption());
		dateLabel.setText(change.formatDate());
    }

    /** gets next photo index
     * 
     * @param event
     */
    @FXML
    void nextPhoto(ActionEvent event) {
    	if(photos.size()<=1){
    		return;
    	}
    	if(index==photos.size()-1){
    		return;
    	}
    	
    	index++;
    	changePhoto();
    	

    }
    
    /**
     * gets index of previous photo
     * @param event
     */
    @FXML
    void previousPhoto(ActionEvent event) {
    	if(photos.size()<=1){
    		return;
    	}
    	if(index==0){
    		return;
    	}
    	
    	index--;
    	changePhoto();	

    }



}
