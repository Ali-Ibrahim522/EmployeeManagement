package group5;

import javafx.application.Application;

/**
 * This class is in charge of running the JavaFxUI class to which starts the
 * employee management application.
 *
 * @author Ali Ibrahim
 * @author Gabriel Schepman
 * @author Junhao Lin
 * @version 1.0
 */
public class EmployeeProject {

    /**
     * this method is in charge of launching the presentation class (JavaFxUI)
     * to which starts and presents the employee management program.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	Application.launch(JavaFxUI.class);
    }

}
