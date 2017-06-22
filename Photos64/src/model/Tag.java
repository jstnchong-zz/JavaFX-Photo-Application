/**
 * 
 * Model for a tag
 * 
 * @author Justin Chong
 * @author Matthew Reyes
 * 
 * 
 */

package model;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Tag implements Serializable{
	
	private String type;
	private String value;
	
/** 
 * Creates Tag for photo
 * 
 * @param type
 * @param value
 */
	public Tag(String type, String value) {
		this.type = type;
		this.value = value;
	}
	/** 
	 * Getter for Tag type
	 * @return
	 */
	public String getType() {
		return type;
	}
	/** 
	 * Setter for Tag type
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * Getter for Tag value
	 * @return
	 */
	public String getValue() {
		return value;
	}
	/**
	 * Setter for Tag Value
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null || !(obj instanceof Tag)){
			   return false;
		}

		Tag o = (Tag )obj;
		
        if(o.getType().equals(this.type) && o.getValue().equals(this.value)){
        	return true;
        }
        
        return false;
	}
	
	public String toString(){
		return this.type+"="+this.value;
	}

}
