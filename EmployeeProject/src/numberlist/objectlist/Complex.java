package numberlist.objectlist;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;

/**
 * This class represents a single complex number of the form x + yi.
 * 
 * @author Ali Ibrahim
 * @version 3.0
 */
public final class Complex implements Copiable, Comparable<Complex>, Serializable{
    
    //fields
    private double real;   
    private double imaginary;   

    /**
     * Default constructor. Creates a new Complex object.
     * Sets both real and imaginary to zero.
     */
    public Complex() {
        real = 0;
        imaginary = 0;
    }

    /**
     * Full constructor. Creates a new Complex object.
     * 
     * @param real the value of the real portion of the complex number
     * @param imaginary the value of the imaginary portion of the complex number
     */
    public Complex(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }
    
    /**
     * Provides access to the real portion of the complex number
     * 
     * @return the value of the real portion of the complex number
     */
    public double getReal() {
        return real;
    }
    
    /**
     * Provides access to the imaginary portion of the complex number
     * 
     * @return the value of the imaginary portion of the complex number
     */
    public double getImaginary() {
        return imaginary;
    }
 
    /**
     * Adds the current and the given complex numbers together, and stores
     * the sum in a new Complex object. The current and given complex numbers
     * are not altered in the process.
     * 
     * @param other the other complex number to add to this one
     * @return the new Complex object that holds the result of the addition
     */
    public Complex add(Complex other) {
        Complex newClass = new Complex(real + other.real, imaginary + other.imaginary);
        return newClass;
    }

    /**
     * Subtracts the other complex number from this one, and stores
     * the result in a new Complex object. The current and given complex numbers
     * are not altered in the process.
     * 
     * @param other the other complex number to subtract from this one
     * @return the new Complex object that holds the result of the subtraction
     */
    public Complex subtract(Complex other) {
        Complex newClass = new Complex(real - other.real, imaginary - other.imaginary);
        return newClass; 
    }
    
    /**
     * Provides the hash code of the complex number
     * 
     * @return the integer hash code
     */
    @Override
    public int hashCode() {
	int hash = 7;
	hash = 83 * hash + (int) (Double.doubleToLongBits(this.real) ^ (Double.doubleToLongBits(this.real) >>> 32));
	hash = 83 * hash + (int) (Double.doubleToLongBits(this.imaginary) ^ (Double.doubleToLongBits(this.imaginary) >>> 32));
	return hash;
    }
    
    /**
     * finds wether or not a given complex number is equal to another.
     * 
     * @param obj the other presumably complex number we are comparing
     * @return true if equal, false if not
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
	final Complex other = (Complex) obj;
	if (Double.doubleToLongBits(this.real) != Double.doubleToLongBits(other.real)) {
	    return false;
	}
	if (Double.doubleToLongBits(this.imaginary) != Double.doubleToLongBits(other.imaginary)) {
	    return false;
	}
	return true;
    }

    /**
     * Provides a string representation of the current complex number,
     * in the form x + yi.
     * 
     * @return the string representation of the current complex number 
     */
    @Override
    public String toString() {
        BigDecimal bd = new BigDecimal(real);
        bd = bd.round(new MathContext(2));
        real = bd.doubleValue();
        bd = new BigDecimal(imaginary);
        bd = bd.round(new MathContext(2));
        imaginary = bd.doubleValue();
        if (imaginary == 0) return real + "";
        if (real == 0) return imaginary + "i";
        if (imaginary <  0) return real + " - " + (-imaginary) + "i";
        return real + " + " + imaginary + "i";
    }

    /**
     * copies the real and imaginary fields and returns a duplicated instance of
     * Complex with those copied values.
     *
     * @return the duplicated Complex
     */
    @Override
    public Complex copy() {
	return new Complex(this.getReal(), this.getImaginary());
    }

    /**
     * compares two instances of complex to each other.
     * 
     * @param o the other presumably instance of complex we are comparing
     * @return the int value representation of the two money's comparison
     */
    @Override
    public int compareTo(Complex o) {
	int compared = Double.compare(this.getReal(), o.getReal());
	if (compared == 0) {
	    return Double.compare(this.getImaginary(), o.getImaginary());
	}
	return compared;
    }

}
