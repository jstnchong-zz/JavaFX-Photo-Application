package application;

import model.Backend;
import model.User;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javafx.scene.layout.AnchorPane;



public class Photos extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/LoginPage.fxml"));
		
		AnchorPane root = loader.load();
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Photo Album");
		primaryStage.setResizable(false);  
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}