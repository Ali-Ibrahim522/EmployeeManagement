package group5;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import numberlist.IndexException;
import numberlist.objectlist.Money;

/**
 * This class is in charge of displaying the GUI interface for the user to be
 * able to manage their employees. Showing them their list of employees,
 * allowing them to enter data to make more employees, sort their list of
 * employees by any of their attributes, and displaying the various stats of all
 * the employees.
 *
 * @author Ali Ibrahim
 * @author Gabriel Schepman
 * @author Junhao Lin
 * @version 1.0
 */
public class JavaFxUI extends Application {

    //fields
    private Stage popupWindow;
    private Text txtStats;
    private ScatterChart employeeChart;
    private Label lblName;
    private Label lblSalary;
    private TextField txtName;
    private TextField txtSalary;
    private ImageView titlePage;
    private ComboBox cmboSort;
    private Button btnSubmit;
    private Button btnBack;
    private Button btnAdd;
    private Button btnStats;
    private BorderPane pane;
    private HBox topPane;
    private ScrollPane leftPane;
    private GridPane centerPane;
    private FlowPane homePane;
    private GridPane paneStats;
    private VBox employeeList;
    private Employees list;

    /**
     * Creates the Employee management window while setting up and initializing
     * all the panes and button/combo sort action handlers.
     *
     * @param stage the stage to which all of our nodes are added to and
     * presented to the user
     */
    @Override
    public void start(Stage stage) {
	//setting up border pane, employee list, and pop-up window
	list = new Employees();
	pane = new BorderPane();
	//setting up left pane and adding serialized data
	setupLeftPane();
	//setting up top pane
	setupTopPane();
	//setting up center pane
	setupCenterPane();
	//setting up stats pane
	setupStatsPane();
	//adding non-changing panes to border pane
	pane.setLeft(leftPane);
	pane.setTop(topPane);
	//handlers
	btnAdd.setOnAction(e -> btnAddHandler());
	btnBack.setOnAction(e -> btnBackHandler());
	btnSubmit.setOnAction(e -> btnSubmitHandler(stage));
	btnStats.setOnAction(e -> {
	    try {
		btnStatsHandler(stage);
	    } catch (IndexException ex) {
		System.out.println(ex.getMessage());
	    }
	});
	cmboSort.setOnAction(e -> cmboSortHandler(stage));
	//setting up scene and stage
	stage.setTitle("Employee Management");
	stage.setMinHeight(600);
	stage.setMinWidth(900);
	stage.setMaxHeight(600);
	stage.setMaxWidth(900);
	stage.setResizable(false);
	stage.setX(200);
	stage.setY(200);
	Scene scene = new Scene(pane);
	stage.setScene(scene);
	stage.show();
    }

    /**
     * Returns the GUI screen back to the original screen that is shown at
     * application startup
     */
    private void homePage() {
	if (pane.getCenter().equals(paneStats)) {
	    pane.getChildren().remove(btnBack);
	    centerPane.add(btnBack, 0, 2);
	}
	pane.setCenter(homePane);
	txtName.setText("");
	txtSalary.setText("");
    }

    /**
     * updates the list of employees in the left pane by checking to see what
     * sort is selected from cmboSort
     */
    private void updateEmployees() {
	if (cmboSort.getSelectionModel().getSelectedIndex() == 0) {
	    list.sortById();
	} else if (cmboSort.getSelectionModel().getSelectedIndex() == 1) {
	    list.sortByName();
	} else if (cmboSort.getSelectionModel().getSelectedIndex() == 2) {
	    list.sortBySalary();
	}
	employeeList = new VBox();
	employeeList.setSpacing(5);
	for (int i = 0; i < list.getSize(); i++) {
	    try {
		Label lblEmployee = new Label(list.toString(i));
		lblEmployee.setFont(Font.font("Helvetica", 16.0));
		employeeList.getChildren().add(lblEmployee);
	    } catch (IndexException ex) {
		System.out.println("Exception should not be thrown");
	    }
	}
	leftPane.setContent(employeeList);
    }

    /**
     * presents a popup that disables use of the employee management till it is
     * closed and shows a message alerting the user of a mistake they have made.
     *
     * @param message the custom message to be shown in the popup
     * @param stage the stage to which x and y position will be used to position
     * the popup in the center of stage
     */
    private void popup(String message, Stage stage) {
	Label warning = new Label(message);
	warning.setAlignment(Pos.CENTER);
	popupWindow = new Stage();
	FlowPane popupPane = new FlowPane(warning);
	warning.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18.0));
	popupPane.setAlignment(Pos.CENTER);
	popupWindow.setAlwaysOnTop(true);
	popupWindow.setTitle("An Issue has occured");
	popupWindow.setScene(new Scene(popupPane));
	popupWindow.setWidth(500);
	popupWindow.setHeight(100);
	popupWindow.setX(stage.getX() + 200);
	popupWindow.setY(stage.getY() + 260);
	popupWindow.initModality(Modality.APPLICATION_MODAL);
	popupWindow.show();
    }

    /**
     * Setups up the window to allow the user to input the name and salary of
     * their desired employee.
     */
    private void btnAddHandler() {
	if (pane.getCenter().equals(paneStats)) {
	    pane.getChildren().remove(btnBack);
	    centerPane.add(btnBack, 0, 2);
	}
	pane.setCenter(centerPane);
    }

    /**
     * returns the program back to the home page upon btnBack being pressed
     */
    private void btnBackHandler() {
	homePage();
    }

    /**
     * reviews the inputted name and salary of the users desired employee,
     * rejecting and showing a popup when a mistake in the input is found and
     * creating the new employee and adding it to the scroll pane if the input
     * is correct.
     *
     * @param stage used for presenting the popups for when user makes a mistake
     */
    private void btnSubmitHandler(Stage stage) {
	if (!(txtName.getText().equals(""))
		&& !(txtSalary.getText().equals(""))) {
	    String name = txtName.getText();
	    String money = txtSalary.getText();
	    if (money.contains("$")) {
		popup("You do not need to use \"$\" when entering the salary",
			stage);
		return;
	    }
	    if (money.contains(",")) {
		popup("You do not need to use commas when entering the salary",
			stage);
		return;
	    }
	    try {
		if (!money.contains(".")) {
		    list.addEmployee(name, new Money(Long.parseLong(money),
			    (byte) 0));
		    try {
			Label lblEmployee = new Label(
				list.toString(list.getSize() - 1));
			lblEmployee.setFont(Font.font("Helvetica", 16.0));
			employeeList.getChildren().add(lblEmployee);
		    } catch (IndexException ex) {
			System.out.println("Exception should not be thrown");
		    }
		} else if (money.contains(".")) {
		    if (money.indexOf(".") == 0) {
			byte cents;
			String strCents = money.substring(
				money.indexOf(".") + 1, money.length());
			if (!strCents.startsWith("0")) {
			    cents = Byte.parseByte(strCents);
			    cents *= 10;
			} else {
			    cents = Byte.parseByte(strCents);
			}
			
			list.addEmployee(name, new Money(0, cents));
			try {
			    Label lblEmployee = new Label(list.toString(
				    list.getSize() - 1));
			    lblEmployee.setFont(Font.font("Helvetica", 16.0));
			    employeeList.getChildren().add(lblEmployee);
			} catch (IndexException ex) {
			    System.out.println("Exception should not be "
				    + "thrown");
			}
		    } else {
			long dollars = Long.parseLong(money.substring(
				0, money.indexOf(".")));
			byte cents;
			String strCents = money.substring(
				money.indexOf(".") + 1, money.length());
			if (!strCents.startsWith("0")) {
			    cents = Byte.parseByte(strCents);
			    cents *= 10;
			} else {
			    cents = Byte.parseByte(strCents);
			}
			list.addEmployee(name, new Money(dollars, cents));
			try {
			    Label lblEmployee = new Label(list.toString(
				    list.getSize() - 1));
			    lblEmployee.setFont(Font.font("Helvetica", 16.0));
			    employeeList.getChildren().add(lblEmployee);
			} catch (IndexException ex) {
			    System.out.println("Exception should not be"
				    + " thrown");
			}
		    }
		}
		updateEmployees();
	    } catch (NumberFormatException nfe) {
		popup("Make sure salary is only numbers and is written "
			+ "correctly", stage);
		return;
	    }

	} else {
	    popup("Make sure to fill in all values of your employee", stage);
	    return;
	}
	//-------------------------------------------------------------------
	txtName.setText("");
	txtSalary.setText("");
    }

    /**
     * shows in the center of the window the average, combined, lowest, and
     * highest earning salaries and employees and showing a graph of all the
     * employees salaries.
     *
     * @param stage used for presenting the popups for when user requests stats
     * and there are no employees
     */
    private void btnStatsHandler(Stage stage) throws IndexException {
	if (list.getSize() == 0) {
	    popup("There is nothing to show stats of, try adding an employee"
		    + " first!", stage);
	    return;
	}
	centerPane.getChildren().remove(btnBack);
	paneStats.getChildren().clear();
	employeeChart.getData().clear();
	txtStats.setText("");
	//graph setup
	XYChart.Series dataSeries1 = new XYChart.Series();
	dataSeries1.setName("Employees");
	list.sortBySalary();
	int counter = 0;
	for (int i = 0; i < list.getSize() - 1; i++) {
	    long salary = list.getSalary(i).getDollars();
	    dataSeries1.getData().add(new XYChart.Data(salary, counter));
	    counter++;
	    if (list.getSalary(i + 1).getDollars() != salary) {
		counter = 0;
	    }
	}
	employeeChart.getData().add(dataSeries1);
	employeeChart.setLegendVisible(false);
	//setup text stats gridpane for display of stats
	GridPane paneTxtStats = new GridPane();
	int rowCount = 4;
	int columnCount = 2;
	RowConstraints rc = new RowConstraints();
	rc.setPercentHeight(100d / rowCount);
	for (int i = 0; i < rowCount; i++) {
	    paneTxtStats.getRowConstraints().add(rc);
	}
	ColumnConstraints cc = new ColumnConstraints();
	cc.setPercentWidth(100d / columnCount);
	cc.setHalignment(HPos.LEFT);
	for (int i = 0; i < columnCount; i++) {
	    paneTxtStats.getColumnConstraints().add(cc);
	}
	Text txtCombined = new Text("Total combined Salaries:");
	txtCombined.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
	GridPane.setHalignment(txtCombined, HPos.RIGHT);
	Text txtCombinedValue = new Text(list.combinedSalaries().toString());
	txtCombinedValue.setFont(Font.font("Times New Roman", 20));
	Text txtAverage = new Text("Average Salary:");
	GridPane.setHalignment(txtAverage, HPos.RIGHT);
	txtAverage.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
	Text txtAverageValue = new Text(list.avgSalary().toString());
	txtAverageValue.setFont(Font.font("Times New Roman", 20));
	Text txtHighest = new Text(list.getName(list.highestSalary())
		+ " has highest salary of:");
	GridPane.setHalignment(txtHighest, HPos.RIGHT);
	txtHighest.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
	Text txtHighestValue = new Text(String.valueOf(list.getSalary(
		list.highestSalary())));
	txtHighestValue.setFont(Font.font("Times New Roman", 20));
	Text txtLowest = new Text(list.getName(list.lowestSalary())
		+ " has lowest salary of:");
	GridPane.setHalignment(txtLowest, HPos.RIGHT);
	txtLowest.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
	Text txtLowestValue = new Text(String.valueOf(list.getSalary(
		list.lowestSalary())));
	txtLowestValue.setFont(Font.font("Times New Roman", 20));
	paneTxtStats.setTranslateX(50);
	paneTxtStats.setHgap(25);
	paneTxtStats.setVgap(5);
	paneTxtStats.add(txtCombined, 0, 0);
	paneTxtStats.add(txtCombinedValue, 1, 0);
	paneTxtStats.add(txtAverage, 0, 1);
	paneTxtStats.add(txtAverageValue, 1, 1);
	paneTxtStats.add(txtHighest, 0, 2);
	paneTxtStats.add(txtHighestValue, 1, 2);
	paneTxtStats.add(txtLowest, 0, 3);
	paneTxtStats.add(txtLowestValue, 1, 3);
	//add gridpane and graph to main stats gridpane
	paneStats.add(paneTxtStats, 0, 0);
	paneStats.add(employeeChart, 0, 1);
	GridPane.setHgrow(employeeChart, Priority.ALWAYS);
	GridPane.setVgrow(employeeChart, Priority.ALWAYS);
	pane.setCenter(paneStats);
	pane.setBottom(btnBack);
	btnBack.setVisible(true);
	txtStats.setVisible(true);
    }

    /**
     * sorts the displayed employees in the scroll pane by the chosen attribute
     * in cmboSort.
     *
     * @param stage used for presenting the popups for when user requests
     * sorting and there are no employees
     */
    private void cmboSortHandler(Stage stage) {
	if (list.getSize() == 0) {
//	    popup("There is nothing to sort, try adding an employee first!",
//		    stage);
	    return;
	}
	updateEmployees();
    }

    /**
     * sets up the scroll pane and the Vbox of employees that is within it.
     */
    private void setupLeftPane() {
	leftPane = new ScrollPane();
	leftPane.setStyle("-fx-background: #800020");
	leftPane.setPrefViewportWidth(300);
	employeeList = new VBox();
	employeeList.setSpacing(5);
	for (int i = 0; i < list.getSize(); i++) {
	    try {
		Label lblEmployee = new Label(list.toString(i));
		lblEmployee.setFont(Font.font("Helvetica", 16.0));
		employeeList.getChildren().add(lblEmployee);

	    } catch (IndexException ex) {
		System.out.println("Exception should not be thrown");
	    }
	}
	leftPane.setContent(employeeList);
    }

    /**
     * sets up the top pane to which holds the add, stats, and sort
     * buttons/combo box
     */
    private void setupTopPane() {
	topPane = new HBox(25);
	topPane.setPadding(new Insets(10, 15, 10, 15));
	topPane.setStyle("-fx-background-color: grey");
	topPane.setAlignment(Pos.CENTER);
	cmboSort = new ComboBox(
		FXCollections.observableArrayList("Sort by IDs",
			"Sort by Names", "Sort by Salaries (low to high)"));
	cmboSort.setValue("Sort by...");
	btnAdd = new Button("ADD");
	btnStats = new Button("STATS");
	topPane.getChildren().addAll(btnStats, btnAdd, cmboSort);
    }

    /**
     * sets up the home pane and the add pane window, both to which appear in
     * the center area of the border pane. Note that statsPane is not setup here
     */
    private void setupCenterPane() {
	titlePage = new ImageView("images/homePage.png");
	titlePage.setPreserveRatio(true);
	titlePage.setSmooth(true);
	homePane = new FlowPane(titlePage);
	homePane.setAlignment(Pos.CENTER);
	pane.setCenter(homePane);
	centerPane = new GridPane();
	centerPane.setAlignment(Pos.CENTER);
	centerPane.setHgap(15.0);
	centerPane.setVgap(15.0);
	lblName = new Label("Name: ");
	lblSalary = new Label("Salary: ");
	lblName.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30.0));
	lblSalary.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30.0));
	txtName = new TextField();
	txtSalary = new TextField();
	txtName.setPrefSize(175.0, 40.0);
	txtSalary.setPrefSize(175.0, 40.0);
	btnSubmit = new Button("SUBMIT");
	btnBack = new Button("BACK");
	btnSubmit.setPrefWidth(120.0);
	btnSubmit.setPrefHeight(40.0);
	btnBack.setPrefWidth(120.0);
	btnBack.setPrefHeight(40.0);
	centerPane.add(lblName, 0, 0);
	centerPane.add(txtName, 1, 0);
	centerPane.add(lblSalary, 0, 1);
	centerPane.add(txtSalary, 1, 1);
	centerPane.add(btnBack, 0, 2);
	centerPane.add(btnSubmit, 1, 2);
	GridPane.setHalignment(btnSubmit, HPos.RIGHT);
    }

    /**
     * sets up the stats pane holding the text and Scatter chart for their stats
     */
    private void setupStatsPane() {
	paneStats = new GridPane();
	paneStats.setHgap(10);
	paneStats.setVgap(10);
	paneStats.setPadding(new Insets(30, 0, 0, 0));
	txtStats = new Text();
	txtStats.setFont(Font.font("Times New Roman", 20.0));
	GridPane.setHalignment(txtStats, HPos.CENTER);
	NumberAxis xAxis = new NumberAxis();
	xAxis.setLabel("Salary");
	NumberAxis yAxis = new NumberAxis();
	yAxis.setUpperBound(list.getSize());
	yAxis.setLabel("No. of Employees");
	employeeChart = new ScatterChart(xAxis, yAxis);
	employeeChart.setTranslateX(-15);
	employeeChart.setTranslateY(25);
    }
}
