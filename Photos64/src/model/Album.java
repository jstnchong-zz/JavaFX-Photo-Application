/**
 * 
 * Model for a Album
 * 
 * @author Justin Chong
 * @author Matthew Reyes
 * 
 */

package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javafx.scene.image.Image;

public class Album implements Serializable{
	
	private String name;
	private ArrayList<Photo> photos;

	
	/**
	 * Constructor for album
	 * @param name
	 */
	public Album(String name) {
		this.name = name;
		this.photos = new ArrayList<Photo>();
	
	}
	
	
	/**
	 * Getter method for album name
	 * @return album name
	 */
	public String getName() {
		return this.name;
	}
	
	
	/**
	 * Setter Method for album name
	 * @param name		
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * adds photo to album
	 * @param photo
	 */
	public void addPhoto(Photo photo) {
			photos.add(photo);
	}
	
	/** removes photo from album
	 * 
	 * @param index
	 */
	public void removePhoto(int index) {
			photos.remove(index);
	}
	
	/**
	 * retrieves photo given album name
	 * @param photoName
	 * @return
	 */
	public Photo getPhotoByName(String photoName) {
		for(Photo p: photos){
			if(p.getName().equals(photoName)){
				return p;
			}
		}
		
		return null;
	}
	
	/**
	 * gets photo by index
	 * @param index
	 * @return
	 */
	public Photo getPhotoByIndex(int index) {
		return photos.get(index);
	}
	
	/**
	 * gets list of all photos in album
	 * @return
	 */
	public List<Photo> getPhotos() {
		return photos;
	}
	
	/** returns string of size of album
	 * 
	 * @return
	 */
	public String getSize(){
		return Integer.toString(photos.size());
	}
	
	
	/**
	 * gets oldest photo date string
	 * @return
	 */
	public String oldestPhotoDate(){
	
		if(photos.size()==0){
			return "";
		}
		if(photos.size()==1){
			return photos.get(0).formatDate();
		}
		
		Photo oldest = photos.get(0);
		for(Photo p: photos){
			if (p.getCalendar().compareTo(oldest.getCalendar()) < 0){
				oldest = p;
				
			}
		}
		
		return oldest.formatDate();
		
	}
	
	/**
	 * gets newest photo date string
	 * @return
	 */
	public String newestPhotoDate(){
		if(photos.size()==0){
			return "";
		}
		if(photos.size()==1){
			return photos.get(0).formatDate();
		}
		
		Photo newest = photos.get(0);
		for(Photo p: photos){
			if (p.getCalendar().compareTo(newest.getCalendar()) > 0){
				newest = p;
				
			}
		}
		
		return newest.formatDate();
	}

	@Override
	public String toString(){
		return this.name;
	}
	
		

		
}

