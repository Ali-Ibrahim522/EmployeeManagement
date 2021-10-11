package group5;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import numberlist.IndexException;
import numberlist.objectlist.Id;
import numberlist.objectlist.Money;
import numberlist.objectlist.NumericArrayList;

/**
 * This class is in charge of holding a collection of employees through parallel
 * arrays of their attributes, those of which being their name, salary, and ID.
 * The class provides adding of more employees, getting of specific attributes
 * of employees, sorting, serializing, and avg, combined, lowest, and highest
 * salary.
 *
 * @author Ali Ibrahim
 * @author Gabriel Schepman
 * @author Junhao Lin
 * @version 1.0
 */
public class Employees {

    //fields
    private File ser;
    private ArrayList<String> names = new ArrayList<>();
    private NumericArrayList salaries = new NumericArrayList();
    private NumericArrayList ids = new NumericArrayList();

    /**
     * initializes a new list of employees while reading for saved employee
     * data.
     */
    public Employees() {
	ser = new File("employees.ser");
	names = new ArrayList<>();
	salaries = new NumericArrayList();
	ids = new NumericArrayList();
	readLists();
    }

    /**
     * Allows a new employee to be added given their name and salary and saves
     * the data.
     *
     * @param newName the name of the new employee
     * @param newSalary the salary of the new employee
     */
    public void addEmployee(String newName, Money newSalary) {
	names.add(newName);
	salaries.add(newSalary);
	ids.add(new Id(newName));
	writeLists();
    }

    /**
     * provides access to the name of an employee at a specific index.
     *
     * @param index the index of the employee's name that is being requested
     * @return the name of the employee at index
     */
    public String getName(int index) {
	return names.get(index);
    }

    /**
     * provides access to the salary of an employee at a specific index.
     *
     * @param index the index of the employee's salary that is being requested
     * @return the salary of the employee at index
     * @throws IndexException
     */
    public Money getSalary(int index) throws IndexException {
	return ((Money) salaries.getValue(index));
    }

    /**
     * provides access to the ID of an employee at a specific index.
     *
     * @param index the index of the employee's ID that is being requested
     * @return the ID of the employee at index
     * @throws IndexException
     */
    public Id getId(int index) throws IndexException {
	return ((Id) ids.getValue(index));
    }

    /**
     * provides access to the number of employees in the list.
     *
     * @return the number of employees in the list
     */
    public int getSize() {
	return names.size();
    }

    /**
     * provides access to the average salary of all the employees.
     *
     * @return the average salary of all employees as a Money object
     * @throws IndexException
     */
    public Money avgSalary() throws IndexException {
	String combined = combinedSalaries().toString();
	String str = combined.substring(1, combined.length());
	Double total = Double.parseDouble(str);
	Double average = total / salaries.getCount();
	Double wholeAverage = average * 100;
	Money avg = new Money((int) (wholeAverage / 100),
		(byte) (wholeAverage % 100));
	return avg;
    }

    /**
     * provides access to the combined salary of all the employees.
     *
     * @return the combined salaries all employees as a money object
     * @throws IndexException
     */
    public Money combinedSalaries() throws IndexException {
	Money mTotal = new Money();
	for (int i = 0; i < salaries.getCount(); i++) {
	    mTotal = mTotal.add((Money) salaries.getValue(i));
	}
	return mTotal;
    }

    /**
     * provides access to the index of the highest earning employee.
     *
     * @return the index of the highest earning employee in the list
     * @throws IndexException
     */
    public int highestSalary() throws IndexException {
	String strMoney = ((Money) salaries.getValue(0)).toString();
	Double highest = Double.parseDouble(strMoney.substring(
		1, strMoney.length()));
	int highIndex = 0;
	for (int i = 1; i < salaries.getCount(); i++) {
	    Double iMoney = Double.parseDouble(((Money) salaries.getValue(i)).
		    toString().substring(1, strMoney.length()));
	    if (Double.compare(highest, iMoney) < 0) {
		highest = iMoney;
		highIndex = i;
	    }
	}
	return highIndex;
    }

    /**
     * provides access to the index of the lowest earning employee.
     *
     * @return the index of the lowest earning employee in the list
     * @throws IndexException
     */
    public int lowestSalary() throws IndexException {
	String strMoney = ((Money) salaries.getValue(0)).toString();
	Double lowest = Double.parseDouble(strMoney.substring(
		1, strMoney.length()));
	int lowIndex = 0;
	for (int i = 1; i < salaries.getCount(); i++) {
	    Double iMoney = Double.parseDouble(((Money) salaries.getValue(i))
		    .toString().substring(1, strMoney.length()));
	    if (Double.compare(lowest, iMoney) > 0) {
		lowest = iMoney;
		lowIndex = i;
	    }
	}
	return lowIndex;
    }

    /**
     * sorts the parallel lists of employee attributes by their names sorts the
     * lists via the bubble sort algorithm with a time complexity of O(n) to
     * O(n2).
     */
    public void sortByName() {
	boolean swapped = true;
	for (int i = 0; i < names.size() && swapped; i++) {
	    swapped = false;
	    for (int j = 1; j < names.size() - i; j++) {
		if (names.get(j).compareToIgnoreCase(names.get(j - 1)) < 0) {
		    swap(j, j - 1);
		    swapped = true;
		}
	    }
	}
    }

    /**
     * sorts the parallel lists of employee attributes by their salaries sorts
     * the lists via the bubble sort algorithm with a time complexity of O(n) to
     * O(n2).
     */
    public void sortBySalary() {
	boolean swapped = true;
	for (int i = 0; i < salaries.getCount() && swapped; i++) {
	    swapped = false;
	    for (int j = 1; j < salaries.getCount() - i; j++) {
		try {
		    String strMoney1 = ((Money) salaries.getValue(j)).
			    toString();
		    String strMoney2 = ((Money) salaries.getValue(j - 1)).
			    toString();
		    Double money1 = Double.parseDouble(
			    strMoney1.substring(1, strMoney1.length()));
		    Double money2 = Double.parseDouble(
			    strMoney2.substring(1, strMoney2.length()));
		    if (Double.compare(money1, money2) < 0) {
			swap(j, j - 1);
			swapped = true;
		    }
		} catch (IndexException ex) {
		    System.out.println("Should not cause an IndexException");
		}
	    }
	}
    }

    /**
     * sorts the parallel lists of employee attributes by their IDs sorts the
     * lists via the bubble sort algorithm with a time complexity of O(n) to
     * O(n2).
     */
    public void sortById() {
	boolean swapped = true;
	for (int i = 0; i < salaries.getCount() && swapped; i++) {
	    swapped = false;
	    for (int j = 1; j < salaries.getCount() - i; j++) {
		try {
		    if (((Id) ids.getValue(j)).compareTo(
			    ((Id) ids.getValue(j - 1))) < 0) {
			swap(j, j - 1);
			swapped = true;
		    }
		} catch (IndexException ex) {
		    System.out.println("Should not cause an IndexException");
		}
	    }
	}
    }

    /**
     * swaps the values found at two indexes through all parallel lists holding
     * the employee attributes.
     *
     * @param fromIndex the index to which is having its values swapped
     * @param toIndex the other index to which is having its values swapped
     */
    private void swap(int fromIndex, int toIndex) {
	try {
	    names.set(toIndex, names.set(fromIndex, names.get(toIndex)));
	    salaries.set(toIndex, salaries.set(
		    fromIndex, salaries.getValue(toIndex)));
	    ids.set(toIndex, ids.set(fromIndex, ids.getValue(toIndex)));
	} catch (IndexException ie) {
	    System.out.println("Should not cause an IndexException");
	}
    }

    /**
     * updates the contents of the save file for employees data.
     */
    private void writeLists() {
	try (ObjectOutputStream output = new ObjectOutputStream(
		new FileOutputStream(ser))) {
	    output.writeObject(names);
	    output.writeObject(salaries);
	    output.writeObject(ids);
	} catch (IOException ioe) {
	    System.out.println("Cannote write to file.");
	}
    }

    /**
     * reads from save file for saved employees.
     */
    private void readLists() {
	try (ObjectInputStream input = new ObjectInputStream(
		new FileInputStream(ser))) {
	    names = (ArrayList<String>) input.readObject();
	    salaries = (NumericArrayList) input.readObject();
	    ids = (NumericArrayList) input.readObject();
	} catch (IOException ex) {
	    System.out.println("Cannot write to file");
	} catch (ClassNotFoundException ex) {
	    System.out.println("Wrong type of data in file");
	}
    }
    
    /**
     * provides a string concatenation of all of an employees attributes found
     * at a specific index in the parallel lists.
     *
     * @param index the location of the employees values in the lists
     * @return the String representation of an employees attributes
     * @throws IndexException
     */
    public String toString(int index) throws IndexException {
	return "ID: " + ((Id) ids.getValue(index)).getId()
		+ ", " + names.get(index) + ", "
		+ ((Money) salaries.getValue(index)).toString();
    }

}
