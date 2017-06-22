/**
 * 
 * Model for a Photo
 * 
 * @author Justin Chong
 * @author Matthew Reyes
 * 
 */



package model;

import java.io.Serializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.embed.swing.SwingFXUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.*;


public class Photo implements Serializable{

	private String name;
	private String caption;
	private List<Tag> tags;
	private Calendar dateAndTime;
	private SimpleDateFormat format;
	private transient Image image;
	
	public Photo() {
		
	}	
	
	
	/**
	 * Constructor
	 */
	public Photo(Image i) {
		this.name = "";
		this.caption = "";
		this.tags = new ArrayList<Tag>();
		this.dateAndTime  = Calendar.getInstance();
		this.dateAndTime.set(Calendar.MILLISECOND, 0);
		this.format = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss");
		this.image = i;
	}	
	
	
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        image = SwingFXUtils.toFXImage(ImageIO.read(s), null);
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", s);
    }
    
    
    
    
	/**
	 * Getter for Name
	 * @return name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Setter for Name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Getter for Caption
	 * @return caption
	 */
	public String getCaption() {
		return this.caption;
	}
	
	
	/**
	 * Setter for Caption
	 * @param caption
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	public List<Tag> getAllTags(){
		return tags;
	}
	/**
	 * Add for Tag
	 * @param type
	 * @param value
	 */
	public void addTag(String type, String value) {
		Tag newTag = new Tag(type,value);
		tags.add(newTag);
	}
	/**
	 * Remove for Tag
	 * @param i
	 */
	public void removeTag(int i){
		tags.remove(i);
	}
	/** 
	 * Edit for Tag
	 * 
	 * @param index
	 * @param type
	 * @param value
	 */
	public void editTag(int index, String type, String value) {
		tags.get(index).setType(type);
		tags.get(index).setValue(value);
	}
	

	public Tag findTag(int i) {
		return tags.get(i);
	}
	
	/**
	 * Getter for Calendar
	 * @return dateAndTime;
	 */
	public Calendar getCalendar() {
		return dateAndTime;
		
	}
	
	/**
	 * Setter for Calendar
	 * @param date;
	 */
	public void setCalendar(Calendar date) {
		 this.dateAndTime = date;
		 this.dateAndTime.set(Calendar.MILLISECOND, 0);		 
	}
	
	/**
	 * getDate
	 * @return string 		form of the date using Calendar
	 */
	public String formatDate() {
		return this.format.format(dateAndTime.getTime());
	}
	/**
	 * Getter for Image
	 * @return
	 */
	public Image getImage(){
		return image;
	}
	
	
	
	
	
}
