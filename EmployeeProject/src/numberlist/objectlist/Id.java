package numberlist.objectlist;

import java.io.Serializable;
import java.util.Random;

/**
 * This class is in charge of generating a unique 6 digit ID number given a
 * String and providing a equals/hashCode, compareTo, and a getter for id.
 * 
 * @author Ali Ibrahim
 * @author Gabriel Schepman 
 * @author Junhao Lin
 * @version 1.0
 */
public class Id implements Comparable<Id>, Serializable, Copiable{
    
    private int id;

    /**
     * calls the createId method to generate the ID and assigns the generated
     * ID to the integer field id.
     * 
     * @param source the string value to which will be used to create the ID
     */
    public Id(String source) {
	id = createId(source);
    }

    /**
     * constructor for the case to which an Id is to be copied, and so a past
     * id is sent as a parameter.
     * 
     * @param id the presumably int value of an id
     */
    public Id(int id) {
	this.id = id;
    }
    
    /**
     * provides access to the value of id
     * 
     * @return the integer value of id
     */
    public int getId() {
	return id;
    }

    /**
     * provides the hash code of the id
     * 
     * @return the hash code of the id
     */
    @Override
    public int hashCode() {
	int hash = 3;
	hash = 59 * hash + this.id;
	return hash;
    }

    /**
     * compares two presumably instances of Id with each other to see if they are
     * equal.
     * 
     * @param obj the presumably instance of Id object that is being compared
     * @return the hash code of the id
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final Id other = (Id) obj;
	if (this.id != other.id) {
	    return false;
	}
	return true;
    }

    /**
     * provides a string version of the ID
     * 
     * @return A string representation of the ID
     */
    @Override
    public String toString() {
	return Integer.toString(id);
    }

    /**
     * provides a integer representation of two Id objects comparison
     * 
     * @return the integer representation of said comparison
     */
    @Override
    public int compareTo(Id o) {
	return Integer.compare(this.id, o.getId());
    }
    
    /**
     * generates a 6 digit id number by alternating between adding hashCode
     * numbers based on the letters in the string and random numbers.
     *
     * @param source the string value to which will be used to create the ID 
     * @return the generated id number
     */
    public int createId(String source) {
	Random rand = new Random();
	String idStr = "";
	//generating value of 
	for (int i = 0; i < source.length(); i++) {
	    if (source.charAt(i) != ' ') {
		if (i % 2 == 0) {
		    idStr += Character.hashCode(source.charAt(id));
		} else {
		    idStr += rand.nextInt(10);
		}
	    }
	    if (idStr.length() >= 6) {
		    break;
		}
	}
	//cleaning up id
	//if id has less than 6 numbers
	while (idStr.length() < 6) {
	    idStr += rand.nextInt(10);
	}
	//if id has more than 6 numbers
	while (idStr.length() > 6) {
	    idStr = idStr.substring(0, idStr.length() - 1);
	}
	return Integer.valueOf(idStr);
    }

    /**
     * provides a copied version of an instance of Id
     * 
     * @return the new copy of an instance of id
     */
    @Override
    public Copiable copy() {
	return new Id(this.id);
    }
    
}
