/**
 * 
 * Model for Backend
 * 
 * @author Justin Chong
 * @author Matthew Reyes
 * 
 */


package model;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class Backend implements Serializable{
	
	private List<User> users;
	
	/**
	 * Constructor for User
	 */
	public Backend() {
		users = new ArrayList<User>();
	}
	
	/** loads serialized backed data from album.dat
	 * 
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Backend loadData() throws IOException, ClassNotFoundException{
		
		try {
	         FileInputStream fileIn = new FileInputStream("data" + File.separator + "album.dat");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         Backend list = (Backend) in.readObject();
	         in.close();
	         fileIn.close();
	         return list;
	      }catch(IOException i) {
	         i.printStackTrace();
	      }catch(ClassNotFoundException c) {
	         
	         c.printStackTrace();
	         
	      }
		
		return null;
	}
	
	/** saves session to album.dat
	 * 
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void saveSession(Backend list) throws IOException{
		
		try {
	         FileOutputStream fileOut = new FileOutputStream("data" + File.separator + "album.dat");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(list);
	         out.close();
	         fileOut.close();
	         
	      }catch(IOException i) {
	         i.printStackTrace();
	      }
	}
	
	
	/**
	 * getter method for users
	 * @return
	 */
	public List<User> getUsers(){
		return users;
	}
	
	/**
	 * adds user to backend user list
	 *
	 * @param newUser
	 */
	public void addUser(User newUser){
		users.add(newUser);
	}
	
	/**
	 * deletes user from backend user list
	 * @param oldUser
	 */
	public void deleteUser(User oldUser){
		users.remove(oldUser);
	}
	
	/**
	 * gets user with specified username
	 * @param username
	 * @return
	 */
	public User getUser(String username){
		for(User u: users){
			if(u.getUsername().toLowerCase().equals(username.toLowerCase())){
				return u;
			}
		}
		return null;
	}
	
	/**
	 * checks if user with username exists in data
	 * @param username
	 * @return
	 */
	public boolean userExists(String username){
		for(User u: users){
			if(u.getUsername().toLowerCase().equals(username.toLowerCase())){
				return true;
			}
		}
		
		return false;
	}
	
		
	
	
	
	
	
	
}
