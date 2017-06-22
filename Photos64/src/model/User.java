/**
 * 
 * Model for a User
 * 
 * @author Justin Chong
 * @author Matthew Reyes
 * 
 */

package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.*;


public class User implements Serializable {

	private String username;
	private List<Album> albums;
	private HashMap<String, Photo> photos;


	public User(String username) {
		this.username = username;
		this.albums = new ArrayList<Album>();
		this.photos = new HashMap<String, Photo>();
	}
	
	
	
	/**
	 * Getter for username
	 * @return username
	 */
	public String getUsername() {
		return username;
	}
	
	
	/**
	 * Setter method for username
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	/**
	 * Getter method for list of albums
	 * @return albums
	 */
	public List<Album> getAlbums() {
		return albums;
	}
	
	/**
	 * method to add album
	 * @param name
	 */
	public void addAlbum(String name) {
		Album newAlbum = new Album(name);
		albums.add(newAlbum);
	}
	
	/**
	 * method to add existing album;
	 * @param name
	 */
	public void addExisting(Album a) {
		albums.add(a);
	}
	
	
	/**
	 * method to remove album
	 * @param name
	 * 
	 */
	
	public void removeAlbum(Album a) {
		
		albums.remove(a);
	}
	
	/**
	 * gets Album by name
	 * Album names must be unique
	 * @param albumName		albumName to be checked before adding
	 * @return true if the album name already exists, else false
	 */
	public Album getAlbum(String albumName) {
		for(Album album: albums){
			String existing = album.getName();
			if(existing.toLowerCase().equals(albumName.toLowerCase())){
				return album;
			}
		}
		
		return null;
	}
	
	/**
	 * Boolean to see if an album exist
	 * Album names must be unique
	 * @param albumName		albumName to be checked before adding
	 * @return true if the album name already exists, else false
	 */
	public boolean albumExists(String albumName) {
		for(Album album: albums){
			String existing = album.getName();
			if(existing.toLowerCase().equals(albumName.toLowerCase())){
				return true;
			}
		}
		
		return false;
	}
	
	public String toString() {
		return this.username;
	}
	

	
}
