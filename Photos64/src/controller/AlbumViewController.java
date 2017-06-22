/**
 * 
 * Controller for Album View
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
import java.io.File;
import java.awt.image.BufferedImage;
import javafx.util.Callback;


import java.io.IOException;
import java.util.Optional;


public class AlbumViewController {
	
	private Backend list;
	private User user;
	private Album album;
	private List<Photo> photos;
	ObservableList<Photo> items;
	
    @FXML
    private ListView<Photo> photoList;

    @FXML
    private Button logoutButton;
    
    @FXML
    private Button captionButton;

    @FXML
    private Button addButton;

    @FXML
    private Button viewButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button moveButton;
    
    @FXML
    private Button copyButton;
    
    @FXML
    private Label albumLabel;
    
    @FXML
    private TextField albumTextField;
    

    @FXML
    private TextField captionTextField;

 
    
    /**
     * initializes user album and photo data
     * @param primaryStage
     */
    public void start(Stage primaryStage){
    	albumLabel.setText("Album: "+album.getName());
    	
    	//System.out.println(list);
    	//System.out.println(user);
    	//System.out.println(album.getName());
    	photos = album.getPhotos();
    	
		items =FXCollections.observableArrayList(photos);
    	
		
    	photoList.setCellFactory(new Callback<ListView<Photo>, ListCell<Photo>>(){
			@Override
			public ListCell<Photo> call(ListView<Photo> p) {
				return new ImageViewCell();}});
				
		
		
		photoList.setItems(items);
    }
    
    
    /**
     * copies photo from one album to another
     * @param event
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @FXML
    public void copyPhoto(ActionEvent event) throws IOException, ClassNotFoundException {
    	
    	Photo selected = photoList.getSelectionModel().getSelectedItem();
    	
    	if(selected!= null) {
    	
    	if ((albumTextField.getText()==null)) {
    		Alert fail= new Alert(AlertType.INFORMATION);
            fail.setHeaderText("Warning");
            fail.setContentText("Please enter a valid album name");
            fail.showAndWait();
            return;
    	}
    	
    	String nameAlbum = albumTextField.getText().trim();
    	
    	
    	if(!user.albumExists(nameAlbum) || nameAlbum.equals(album.getName())){
    		Alert newfail= new Alert(AlertType.INFORMATION);
            newfail.setHeaderText("Warning");
            newfail.setContentText("Album name does not exist");
            newfail.showAndWait();
            return;
    	}
    	
    	if(user.albumExists(nameAlbum) && !nameAlbum.equals(album.getName())){
    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle("Copy Photo");
    		alert.setHeaderText("Copy photo?");
    		Optional<ButtonType> result = alert.showAndWait();
    		if(result.get() == ButtonType.OK){	
    			user.getAlbum(nameAlbum).addPhoto(selected);
    			items =FXCollections.observableArrayList(photos);
    	    	photoList.setItems(items);
    		}
    	}
    	
    	
    	}
		
		Backend.saveSession(list);
    
    }
    
    /**
     * moves photo to album
     * @param event
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @FXML
    public void movePhoto(ActionEvent event) throws IOException, ClassNotFoundException {
        
    	Photo selected = photoList.getSelectionModel().getSelectedItem();
    	
    	if(selected!= null) {
    	
    	if ((albumTextField.getText()==null)) {
    		Alert fail= new Alert(AlertType.INFORMATION);
            fail.setHeaderText("Warning");
            fail.setContentText("Please enter a valid album name");
            fail.showAndWait();
            return;
    	}
    	
    	String nameAlbum = albumTextField.getText().trim();
    	
    	
    	if(!user.albumExists(nameAlbum) || nameAlbum.equals(album.getName())){
    		Alert newfail= new Alert(AlertType.INFORMATION);
            newfail.setHeaderText("Warning");
            newfail.setContentText("Album name does not exist");
            newfail.showAndWait();
            return;
    	}
    	
    	if(user.albumExists(nameAlbum) && !nameAlbum.equals(album.getName())){
    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle("Move Photo");
    		alert.setHeaderText("Move photo?");
    		Optional<ButtonType> result = alert.showAndWait();
    		if(result.get() == ButtonType.OK){	
    			photos.remove(selected);
    			user.getAlbum(nameAlbum).addPhoto(selected);
    			items =FXCollections.observableArrayList(photos);
    	    	photoList.setItems(items);
    		}
    	}
    	
    	}
		
		Backend.saveSession(list);
    }
    
    /**
     * captions photo
     * @param event
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @FXML
    public void captionPhotoButton(ActionEvent event) throws IOException, ClassNotFoundException {
        
    	Photo selected = photoList.getSelectionModel().getSelectedItem();
    	
    	if(selected!= null) {
    	
    	if ((captionTextField.getText()==null)) {
    		Alert fail= new Alert(AlertType.INFORMATION);
            fail.setHeaderText("Warning");
            fail.setContentText("Please enter a valid album name");
            fail.showAndWait();
            return;
    	}
    	
    	String newCaption = captionTextField.getText().trim();
    	

    	Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Add/Rename");
		alert.setHeaderText("Add/Rename Caption?");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK){	
			selected.setCaption(newCaption);
			photos.remove(selected);
			photos.add(selected);
			items =FXCollections.observableArrayList(photos);
	    	photoList.setItems(items);
		}
    	}
		
		Backend.saveSession(list);
    }
    

    /**
     * views selected photo to photo display
     * @param event
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @FXML
    public void viewPhotoButton(ActionEvent event) throws IOException, ClassNotFoundException {
    	
    	Photo view = photoList.getSelectionModel().getSelectedItem();
    	
    	if(view != null) {
    	
    	Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Display Photo");
		alert.setHeaderText("Are you sure you want to view this photo?");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK){	
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/DisplayPhoto.fxml"));
			Parent root = loader.load();
			        
			Scene scene = new Scene(root);		
			Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		             
			DisplayPhotoController controller = loader.getController();
			controller.setUser(user);
			controller.setBackend(list);
			controller.setAlbum(album);
			controller.setPhoto(view);
			

			controller.start(appStage);
			
		    appStage.setScene(scene);
		    appStage.show(); 
		} 	
    	}
		
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
    
    /**
     * new cell factory to hold image and caption in list view
     * @author justinchong
     *
     */
    static class ImageViewCell extends ListCell<Photo> {
    	
    	AnchorPane anchor = new AnchorPane();
    	ImageView imageView = new ImageView();
    	Label captionText = new Label();
    	
    	public ImageViewCell(){
    		super();
		
    		imageView.setFitWidth(100.0);
    		imageView.setFitHeight(100.0);
    		imageView.setPreserveRatio(false);
    		
    
    			
    		AnchorPane.setLeftAnchor(imageView, 0.0);
    		AnchorPane.setLeftAnchor(captionText, 120.0);
    		AnchorPane.setTopAnchor(captionText, 40.0);
    		anchor.getChildren().addAll(imageView, captionText);
    		captionText.setMaxWidth(500.0);
    		setGraphic(anchor);
    	}
    	
        @Override
        public void updateItem(Photo p, boolean empty) {
            super.updateItem(p, empty);
            
            if (p != null) {
               imageView.setImage(p.getImage());
               captionText.setText("Caption: "+ p.getCaption());
            }
            else{
               imageView.setImage(null);
               captionText.setText("");
            }
            
        }
        
    }
    
    
    /**
     * deletes photo
     * @param event
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @FXML
    public void deletePhotoButton(ActionEvent event) throws IOException, ClassNotFoundException {
 
    	
    	Photo delete = photoList.getSelectionModel().getSelectedItem();
    	
    	if(delete!= null) {
    	
    		Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Delete photo");
			alert.setHeaderText("Confirm deleted photo");
		
			Optional<ButtonType> result = alert.showAndWait();
			if(result.get() == ButtonType.OK){	
				photos.remove(delete);
				items =FXCollections.observableArrayList(photos);
		    	photoList.setItems(items);
			}
    	}
    	
    	Backend.saveSession(list);	
		
    }
    
    /**adds photo with filechooser
     * 
     * @param event
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @FXML
    public void addPhotoButton(ActionEvent event) throws IOException, ClassNotFoundException{
    	
    	 
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sample File Chooser");
        
        String filePath;
        
     // Set jpg filter
        FileChooser.ExtensionFilter extFilter3 = 
                new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        fileChooser.getExtensionFilters().add(extFilter3);
        
        // Set png filter
        FileChooser.ExtensionFilter extFilter1 = 
                new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().add(extFilter1);
        
        // Set bmp filter
        FileChooser.ExtensionFilter extFilter2 = 
                new FileChooser.ExtensionFilter("BMP files (*.bmp)", "*.BMP");
        fileChooser.getExtensionFilters().add(extFilter2);
        
        
        // Set gif filter
        FileChooser.ExtensionFilter extFilter4 = 
                new FileChooser.ExtensionFilter("GIF files (*.gif)", "*.GIF");
        fileChooser.getExtensionFilters().add(extFilter4);
        
        // Set tiff filter
        FileChooser.ExtensionFilter extFilter5 = 
                new FileChooser.ExtensionFilter("TIFF files (*.tiff)", "*.TIFF");
        fileChooser.getExtensionFilters().add(extFilter5);
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            filePath = file.getPath();
        }
        
        BufferedImage img = ImageIO.read(file);
        Image image = SwingFXUtils.toFXImage(img, null);
        
        Photo newPhoto = new Photo(image);
        photos.add(newPhoto);
        items =FXCollections.observableArrayList(photos);
        Backend.saveSession(list);
        
        photoList.setItems(items);
        
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

}
