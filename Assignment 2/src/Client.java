
//Title     : FT MUR T122 ICT373 B – Assignment 2 (Client class)
//Author    : Tee Yee Kang
//Date      : 27/Mar/2022
//File Name : FTB-34315323-Assignment 02
//Purpose  	: This is the client class (GUI program). This is the program that users
//            execute and used to interact with the GUI.
//            This program is to allowed the users (customers) to create
//            a Magazine with different supplement services and a list of different
//            customer that subscribe to the magazine. The program also provide some
//            functions to allow the user to manipulate/make changes such as 
//            add customer, remove customer, display information, etc.

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.Optional;
import java.util.Scanner;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * @author TeeYeeKang yeekang88@gmail.com
 * @version 1.1
 */
public class Client extends Application {

	static DecimalFormat df = new DecimalFormat("0.00");

	// member variables
	private static ArrayList<Customer> customerList = new ArrayList<Customer>();
	private static Magazine magazine = new Magazine();
	private Scene viewScene, createScene, editScene;

	// for supplement view list
	private ChoiceDialog<String> supplementDialog;
	private Text viewOutputInfo;
	private String[] arrayData;
	private List<String> dialogData;

	// for customer view list
	private ChoiceDialog<String> customerDialog;
	private String[] customerArrayData;
	private List<String> customerDialogData;

	// create mode supplement
	private static ArrayList<Supplement> newSupList;
	private Text numOfSupplements;
	private int supplementCount = 0;
	private Button createSupplementBtn;

	// create new customer mode
	private Text numOfCustomers;
	private int customerCount = 0;
	private static ArrayList<Customer> tempCusList;
	private static ArrayList<AssociateCustomer> tempAssoCusList;
	private Text numOfAssoCusAdded;
	private int assoCusCount;

	// edit mode
	private Text info;
	private int counter;
	private Button addBtn;

	// modify customer
	private String[] dropdown;
	private List<String> customerDropdownDialogData;
	private Text numOfCustomer;

	// edit customer info
	private ChoiceBox<String> allCustomerChoiceBox;
	int position;

	// Multithreading
	private static String billingHistory;

	private class MultiThread implements Runnable {
		String name;

		public MultiThread(String customerName) {
			name = customerName;
		}

		public void run() {
			for (int i = 0; i < customerList.size(); i++) {
				if (customerList.get(i).GetCustomerName().equals(name)) {
					if (customerList.get(i) instanceof PayingCustomer) {
						billingHistory = "Bank A/C: " + ((PayingCustomer)customerList.get(i)).GetPayment().GetBankAcc()
								+"\nA/C Type: " + ((PayingCustomer)customerList.get(i)).GetPayment().GetBankAccType()
								+"\nBilling History (Monthly)\n-------------------------\nPayment : $"
								+ df.format((4 * (magazine.GetWeeklyCost() + customerList.get(i).GetWeeklyPrice())));
					}
				}
			}
		}
	}

	public static void main(String[] args) {

		newSupList = new ArrayList<Supplement>();
		tempCusList = new ArrayList<Customer>();
		tempAssoCusList = new ArrayList<AssociateCustomer>();
		magazine = ReadMagazineSerialise();
		customerList = ReadCustomerSerialise();
		
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {

		// store supplement name for use of view mode ChoiceDialog
		arrayData = new String[magazine.GetMazSupplement().size()];
		for (int i = 0; i < magazine.GetMazSupplement().size(); i++) {
			arrayData[i] = magazine.GetMazSupplement().get(i).GetName();
		}

		// store customer name for use of view mode ChoiceDialog
		customerArrayData = new String[customerList.size()];
		for (int idx = 0; idx < customerList.size(); idx++) {
			customerArrayData[idx] = customerList.get(idx).GetCustomerName();
		}

		VBox viewBoxLeft = new VBox();
		VBox viewBoxRight = new VBox();

		try {

			// ---------------------------------------------------------------------------------------------
			// View Scene

			// Top label
			Label viewLabel = new Label("Magazine Service");
			viewLabel.setTextFill(Color.DARKBLUE);
			viewLabel.setFont(Font.font("Calibri", FontWeight.BOLD, 36));
			HBox viewLabelHb = new HBox();
			viewLabelHb.setAlignment(Pos.CENTER);
			viewLabelHb.getChildren().add(viewLabel);

			// 3 function buttons to switch scene
			Button viewButton = new Button("View");
			Button createButton = new Button("Create");
			Button editButton = new Button("Edit");
			// add Event to button
			viewButton.setOnAction(e -> stage.setScene(viewScene));
			createButton.setOnAction(e -> stage.setScene(createScene));
			editButton.setOnAction(e -> stage.setScene(editScene));
			// Set size of button
			viewButton.setPrefWidth(60.0);
			createButton.setPrefWidth(60.0);
			editButton.setPrefWidth(60.0);
			HBox buttonHb = new HBox(10);
			buttonHb.setAlignment(Pos.CENTER);
			buttonHb.setSpacing(100);
			buttonHb.getChildren().addAll(viewButton, createButton, editButton);

			// Display view scene output
			viewOutputInfo = new Text();
			viewOutputInfo.setText("Information Panel:\n");
			viewOutputInfo.setFont(Font.font("Calibri", FontWeight.NORMAL, 20));
			viewOutputInfo.setFill(Color.FIREBRICK);

			// --------------------------------------------------------------------
			// supplement view list

			Label supplementLabel = new Label("Supplement Information");
			supplementLabel.setTextFill(Color.DARKBLUE);
			supplementLabel.setFont(Font.font("Calibri", FontWeight.BOLD, 20));

			// Button
			Button supplementButton = new Button("Select Supplement");
			supplementButton.setPrefWidth(115);
			supplementButton.setOnAction(new ChoiceButtonListener());

			VBox supplementViewVBox = new VBox();
			supplementViewVBox.setSpacing(20);
			supplementViewVBox.getChildren().addAll(supplementLabel, supplementButton);

			// --------------------------------------------------------------------
			// customer view list

			// Button
			Label customerLabel = new Label("Customer Information");
			customerLabel.setTextFill(Color.DARKBLUE);
			customerLabel.setFont(Font.font("Calibri", FontWeight.BOLD, 20));

			Button customerButton = new Button("Select Customer");
			customerButton.setPrefWidth(115);
			customerButton.setOnAction(new ChoiceButtonListener_Customer());

			VBox customerViewVBox = new VBox();
			customerViewVBox.setSpacing(20);
			customerViewVBox.getChildren().addAll(customerLabel, customerButton);

			Button clearInfoButton = new Button("Clear");
			clearInfoButton.setOnAction(e -> viewOutputInfo.setText("Information Panel:\n"));
			clearInfoButton.setAlignment(Pos.BOTTOM_LEFT);
			clearInfoButton.setStyle("-fx-background-color: FIREBRICK; -fx-text-fill: white;");

			// Store all elements to viewBoxLest and viewBoxRight
			viewBoxLeft.getChildren().addAll(supplementViewVBox, customerViewVBox, clearInfoButton);
			viewBoxLeft.setAlignment(Pos.CENTER_LEFT);
			viewBoxLeft.setPadding(new Insets(0, 200, 10, 20));
			viewBoxLeft.setSpacing(30);
			viewBoxRight.getChildren().add(viewOutputInfo);
			viewBoxRight.setAlignment(Pos.TOP_RIGHT);
			HBox assemble = new HBox();
			assemble.getChildren().addAll(viewBoxLeft, viewBoxRight);
			assemble.setAlignment(Pos.CENTER);

			Separator separator1 = new Separator(Orientation.HORIZONTAL);
			separator1.setPrefWidth(1500);

			// Add to GridPane
			GridPane gridPane = new GridPane();
			gridPane.setMinSize(400, 200);
			gridPane.setPadding(new Insets(10, 10, 10, 10));
			gridPane.setVgap(25);
			gridPane.setHgap(15);
			gridPane.setAlignment(Pos.TOP_CENTER);

			gridPane.add(viewLabelHb, 0, 0); // column=0 row=0
			gridPane.add(buttonHb, 0, 2);
			gridPane.add(separator1, 0, 3);
			gridPane.add(assemble, 0, 5);

			viewScene = new Scene(gridPane, 800, 700);

			// ---------------------------------------------------------------------------------------------
			// Create Scene

			// Top label
			Label createLabel = new Label("Create Mode");
			createLabel.setTextFill(Color.DARKBLUE);
			createLabel.setFont(Font.font("Calibri", FontWeight.BOLD, 36));
			HBox createLabelHb = new HBox();
			createLabelHb.setAlignment(Pos.CENTER);
			createLabelHb.getChildren().add(createLabel);

			// 3 functions button to switch scene
			Button viewButton2 = new Button("View");
			viewButton2.setOnAction(e -> stage.setScene(viewScene));
			Button createButton2 = new Button("Create");
			createButton2.setOnAction(e -> stage.setScene(createScene));
			Button editButton2 = new Button("Edit");
			editButton2.setOnAction(e -> stage.setScene(editScene));
			// Set size of button
			viewButton2.setPrefWidth(60.0);
			createButton2.setPrefWidth(60.0);
			editButton2.setPrefWidth(60.0);
			HBox createButtonHb = new HBox(10);
			createButtonHb.setAlignment(Pos.CENTER);
			createButtonHb.setSpacing(100);
			createButtonHb.getChildren().addAll(viewButton2, createButton2, editButton2);

			Button magazineCreateBtn = new Button("Magazine");
			magazineCreateBtn.setOnAction(new DisplayNewCreateWindow_Magazine());
			Button customerCreateBtn = new Button("Customer");
			customerCreateBtn.setOnAction(new DisplayNewCreateWindow_Customer());
			VBox customerCreateBtnVb = new VBox(10);
			customerCreateBtnVb.setAlignment(Pos.CENTER);
			customerCreateBtnVb.setSpacing(50);
			customerCreateBtnVb.getChildren().addAll(magazineCreateBtn, customerCreateBtn);

			Separator separator2 = new Separator(Orientation.HORIZONTAL);
			separator2.setPrefWidth(1500);

			GridPane createGridPane = new GridPane();
			createGridPane.setMinSize(400, 200);
			createGridPane.setPadding(new Insets(10, 10, 10, 10));
			createGridPane.setVgap(25);
			createGridPane.setHgap(5);
			createGridPane.setAlignment(Pos.TOP_CENTER);

			createGridPane.add(createLabelHb, 0, 0);
			createGridPane.add(createButtonHb, 0, 2);
			createGridPane.add(separator2, 0, 3);
			createGridPane.add(customerCreateBtnVb, 0, 5);

			createScene = new Scene(createGridPane, 800, 700);

			// ---------------------------------------------------------------------------------------------
			// Edit Scene

			// Top label
			Label editLabel = new Label("Edit Mode");
			editLabel.setTextFill(Color.DARKBLUE);
			editLabel.setFont(Font.font("Calibri", FontWeight.BOLD, 36));
			HBox editLabelHb = new HBox();
			editLabelHb.setAlignment(Pos.CENTER);
			editLabelHb.getChildren().add(editLabel);

			// 3 functions button to switch scene
			Button viewButton3 = new Button("View");
			viewButton3.setOnAction(e -> stage.setScene(viewScene));
			Button createButton3 = new Button("Create");
			createButton3.setOnAction(e -> stage.setScene(createScene));
			Button editButton3 = new Button("Edit");
			editButton3.setOnAction(e -> stage.setScene(editScene));
			// Set size of button
			viewButton3.setPrefWidth(60.0);
			createButton3.setPrefWidth(60.0);
			editButton3.setPrefWidth(60.0);
			HBox editButtonHb = new HBox(10);
			editButtonHb.setAlignment(Pos.CENTER);
			editButtonHb.setSpacing(100);
			editButtonHb.getChildren().addAll(viewButton3, createButton3, editButton3);

			Button magazineEditBtn = new Button("Magazine");
			magazineEditBtn.setPrefWidth(80);
			magazineEditBtn.setOnAction(new DisplayEditMagazineWindow());
			Button supplementEditBtn = new Button("Supplement");
			supplementEditBtn.setOnAction(new DisplayEditSupplementWindow());
			Button customerEditBtn = new Button("Customer");
			customerEditBtn.setPrefWidth(80);
			customerEditBtn.setOnAction(new DisplayEditCustomerWindow());
			VBox editModeVbox = new VBox(10);
			editModeVbox.setAlignment(Pos.CENTER);
			editModeVbox.setSpacing(50);
			editModeVbox.getChildren().addAll(magazineEditBtn, supplementEditBtn, customerEditBtn);

			Separator separator3 = new Separator(Orientation.HORIZONTAL);
			separator3.setPrefWidth(1500);

			GridPane editGridPane = new GridPane();
			editGridPane.setMinSize(400, 200);
			editGridPane.setPadding(new Insets(10, 10, 10, 10));
			editGridPane.setVgap(25);
			editGridPane.setHgap(5);
			editGridPane.setAlignment(Pos.TOP_CENTER);

			editGridPane.add(editLabelHb, 0, 0);
			editGridPane.add(editButtonHb, 0, 2);
			editGridPane.add(separator3, 0, 3);
			editGridPane.add(editModeVbox, 0, 5);

			editScene = new Scene(editGridPane, 800, 700);

			stage.setTitle("34315323-Assignment 2");
			stage.setScene(viewScene);
			stage.show();

			dialogData = Arrays.asList(arrayData); // supplement info view
			UpdateCustomerDropdownList(1);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}// End of start()

	private class ChoiceButtonListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			viewOutputInfo.setText("");
			DisplaySupplementDialog();
		}
	}

	private void DisplaySupplementDialog() {

		supplementDialog = new ChoiceDialog<String>(dialogData.get(0), dialogData);
		supplementDialog.setTitle("Supplement Selection");
		supplementDialog.setHeaderText("Select a supplement");

		Optional<String> result = supplementDialog.showAndWait();
		String selected = "NIL";

		if (result.isPresent()) {
			selected = result.get();
		}

		for (int i = 0; i < magazine.GetMazSupplement().size(); i++) {
			if (selected.equals(magazine.GetMazSupplement().get(i).GetName())) {
				selected = GetSupplementInfo(magazine.GetMazSupplement().get(i).GetName());
				viewOutputInfo.setText(
						"Magazine: " + magazine.GetName() + "\nWeekly Cost: $" + df.format(magazine.GetWeeklyCost())
								+ "\nSupplement Info: \n-----------------------\n" + selected);
			} else {
				viewOutputInfo.setText(
						"Magazine: " + magazine.GetName() + "\nWeekly Cost: $" + df.format(magazine.GetWeeklyCost())
								+ "\nSupplement Info: \n-----------------------\n" + selected);
			}
		}
	}

	private String GetSupplementInfo(String supplementName) {

		double cost = 0;
		String finalOutput;
		String name = "";
		Customer customer = null;
		HashSet<String> customerNameList = new HashSet<String>();

		for (int i = 0; i < customerList.size(); i++) {
			customer = customerList.get(i);
			if (customer instanceof AssociateCustomer) {
				ArrayList<Supplement> supList = customer.GetSuppList();
				for (int j = 0; j < supList.size(); j++) {
					if (supList.get(j).GetName().equals(supplementName)) {
						customerNameList.add(customer.GetCustomerName());
					}
				}
			} else {
				ArrayList<Supplement> supList = customer.GetSuppList();
				for (int j = 0; j < supList.size(); j++) {
					if (supList.get(j).GetName().equals(supplementName)) {
						customerNameList.add(customer.GetCustomerName());
					}
				}

				// retrieve associate customer list
				ArrayList<AssociateCustomer> assoCusList = ((PayingCustomer) customer).GetAssoCusList();
				for (int idx = 0; idx < assoCusList.size(); idx++) {
					AssociateCustomer assoCus = assoCusList.get(idx);
					ArrayList<Supplement> supList2 = assoCus.GetSuppList();
					for (int j = 0; j < supList2.size(); j++) {
						if (supList2.get(j).GetName().equals(supplementName)) {
							customerNameList.add(assoCus.GetCustomerName());
						}
					}
				}
			}
		}

		for (int i = 0; i < magazine.GetMazSupplement().size(); i++) {
			if (magazine.GetMazSupplement().get(i).GetName().equals(supplementName)) {
				cost = magazine.GetMazSupplement().get(i).GetWeeklyCost();
			}
		}

		finalOutput = "Supplement name: " + supplementName + "\n" + "Weekly Cost: $" + cost + "\n" + "Date: "
				+ java.time.LocalDate.now() + "\nSubscribed Customer:" + "\n-----------------------\n";

		Iterator<String> itr = customerNameList.iterator();
		while (itr.hasNext()) {
			String temp = itr.next();
			name = name.concat(temp) + "\n";
		}

		finalOutput = finalOutput + name;
		return finalOutput;
	}

	// for customer info view
	private class ChoiceButtonListener_Customer implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			viewOutputInfo.setText("");
			DisplayCustomerDialog();
		}
	}

	private void DisplayCustomerDialog() {

		customerDialog = new ChoiceDialog<String>(customerDialogData.get(0), customerDialogData);
		customerDialog.setTitle("Customer Selection");
		customerDialog.setHeaderText("Select a customer");

		Optional<String> result = customerDialog.showAndWait();
		String selected = "No customer selected";

		if (result.isPresent()) {
			selected = result.get();
		}

		for (int i = 0; i < customerList.size(); i++) {
			if (selected.equals(customerList.get(i).GetCustomerName())) {
				selected = GetCustomerInfo(customerList.get(i).GetCustomerName());
				viewOutputInfo.setText("Customer Info: \n" + selected);
			} else {
				viewOutputInfo.setText("Customer Info: \n" + selected);
			}

			if (customerList.get(i) instanceof PayingCustomer) {
				ArrayList<AssociateCustomer> list = ((PayingCustomer) customerList.get(i)).GetAssoCusList();
				for (int inner = 0; inner < list.size(); inner++) {
					if (list.get(inner).GetCustomerName().equals(selected)) {
						selected = GetCustomerInfo(list.get(inner).GetCustomerName());
						viewOutputInfo.setText("Customer Info: \n" + selected);
					}
				}
			}
		}
	}

	private String GetCustomerInfo(String customerName) {

		boolean payingCustomer = false;
		String associateCustomer = "";
		String supplement = "";
		String finalOutput;
		String status = "";
		Customer customer = null;
		Customer exactCustomer = null;
		HashSet<String> supplementList = new HashSet<String>();
		HashSet<String> associateCusList = new HashSet<String>();

		for (int i = 0; i < customerList.size(); i++) {
			customer = customerList.get(i);

			if (customer.GetCustomerName().equals(customerName)) {

				if (customer instanceof AssociateCustomer) {
					status = "Associate Customer";
					exactCustomer = customerList.get(i);
					ArrayList<Supplement> supList = exactCustomer.GetSuppList();
					for (int j = 0; j < supList.size(); j++) {
						supplementList.add(supList.get(j).GetName());
					}
				} else {
					payingCustomer = true;
					status = "Paying Customer";
					exactCustomer = customerList.get(i);

					// This is the part that address the multithreading issue
					// mentioned in the project limitation
					billingHistory = "Bank A/C: " + ((PayingCustomer)exactCustomer).GetPayment().GetBankAcc()
							+"\nA/C Type: " + ((PayingCustomer)exactCustomer).GetPayment().GetBankAccType()
							+"\nBilling History (Monthly)\n-------------------------\nPayment : $"
							+ df.format((4 * (magazine.GetWeeklyCost() + exactCustomer.GetWeeklyPrice())));

					// for billing information
					// Client c = new Client();
					// Client.MultiThread m = c.new
					// MultiThread(customerList.get(i).GetCustomerName());
					// new Thread(m).start();

					// retrieve supplement list
					ArrayList<Supplement> supList = customer.GetSuppList();
					for (int j = 0; j < supList.size(); j++) {
						supplementList.add(supList.get(j).GetName());
					}

					// retrieve associate customer list
					ArrayList<AssociateCustomer> tempList = ((PayingCustomer) customer).GetAssoCusList();
					for (int idx = 0; idx < tempList.size(); idx++) {
						associateCusList.add(tempList.get(idx).GetCustomerName());
					}
				}
			} else {
				// cannot found the same name from outer customer list
				// look for paying customer's associate customer list
				if (customer instanceof PayingCustomer) {
					ArrayList<AssociateCustomer> list = ((PayingCustomer) customer).GetAssoCusList();
					for (int inner = 0; inner < list.size(); inner++) {
						if (list.get(inner).GetCustomerName().equals(customerName)) {
							status = "Associate Customer";
							exactCustomer = list.get(inner);
							ArrayList<Supplement> supList = exactCustomer.GetSuppList();
							for (int j = 0; j < supList.size(); j++) {
								supplementList.add(supList.get(j).GetName());
							}
						}
					}
				}
			}
		}

		finalOutput = "Customer name: " + exactCustomer.GetCustomerName() + "\n" + "Address: "
				+ exactCustomer.GetAddress() + "\n" + "Status: " + status + "\n" + "Supplements: "
				+ "\n-------------------------\n";

		Iterator<String> itr = supplementList.iterator();
		while (itr.hasNext()) {
			String temp = itr.next();
			supplement = supplement.concat(temp) + "\n";
		}
		finalOutput = finalOutput + supplement + "\nAssociate Customers:" + "\n-------------------------\n";

		if (payingCustomer) {
			Iterator<String> it = associateCusList.iterator();
			while (it.hasNext()) {
				String temp = it.next();
				associateCustomer = associateCustomer.concat(temp) + "\n";
			}
			associateCustomer = associateCustomer + "\n" + billingHistory;
		} else {
			associateCustomer = "NIL";
		}

		finalOutput = finalOutput + associateCustomer;

		return finalOutput;
	}

	private class DisplayNewCreateWindow_Magazine implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			supplementCount = 0;
			newSupList.clear();
			CreateNewMagazine();
		}
	}

	private void CreateNewMagazine() {

		ArrayList<Supplement> tempList = new ArrayList<Supplement>();
		Dialog<Magazine> dialog = new Dialog<>();
		dialog.setTitle("Create Mode Window");
		dialog.setHeaderText("Enter the magazine's information to create a new Magazine");
		dialog.setResizable(true);

		// Magazine name
		Text magazineName = new Text("Name");
		TextField magazineNameText = new TextField();

		// Magazine weekly cost
		Text magazineCost = new Text("Weekly Cost");
		TextField magazineCostText = new TextField();

		Text supplementText = new Text("Create Supplement");

		// create supplement button
		createSupplementBtn = new Button("Supplement");
		createSupplementBtn.setOnAction(new CreateNewSup());

		numOfSupplements = new Text();
		numOfSupplements.setText("Number of supplements: " + supplementCount);
		numOfSupplements.setFont(Font.font("Calibri", FontWeight.NORMAL, 15));
		numOfSupplements.setFill(Color.FIREBRICK);

		// file chooser
		Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();
		Button selectFile = new Button("Read From File");
		selectFile.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
		selectFile.setOnAction(e -> {
			File selectedFile = fileChooser.showOpenDialog(stage);

			Magazine tempMagazine = new Magazine();
			tempMagazine = ReadMagazineFileChooser(selectedFile);
			magazine = new Magazine(tempMagazine); // copy constructor

			// update supplement drop-down list
			UpdateSupplementDropDownList(magazine.GetMazSupplement());

			// save to serialize file
			SaveToSerialiseFile_Magazine();

			dialog.close(); // close the create magazine window
		});

		GridPane root = new GridPane();
		root.setMinSize(300, 200);
		root.setPadding(new Insets(10, 10, 10, 10));
		root.setVgap(5);
		root.setHgap(30);
		root.setAlignment(Pos.TOP_CENTER);
		root.setStyle("-fx-background-color: BEIGE;");

		root.add(magazineName, 0, 0);
		root.add(magazineNameText, 1, 0);
		root.add(magazineCost, 0, 1);
		root.add(magazineCostText, 1, 1);
		root.add(supplementText, 0, 2);
		root.add(createSupplementBtn, 1, 2);
		root.add(numOfSupplements, 0, 4);
		root.add(selectFile, 0, 6);

		magazineName.setStyle("-fx-font: normal bold 15px 'serif' ");
		magazineCost.setStyle("-fx-font: normal bold 15px 'serif' ");
		supplementText.setStyle("-fx-font: normal bold 15px 'serif' ");

		dialog.getDialogPane().setContent(root);

		// Add button to dialog
		ButtonType buttonTypeOk = new ButtonType("Okay", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

		dialog.setResultConverter(new Callback<ButtonType, Magazine>() {
			@Override
			public Magazine call(ButtonType b) {

				if (b == buttonTypeOk) {

					UpdateSupplementDropDownList(newSupList); // update supplement drop-down

					for (int i = 0; i < newSupList.size(); i++) {
						tempList.add(newSupList.get(i));
					}

					return new Magazine(magazineNameText.getText(), Double.parseDouble(magazineCostText.getText()),
							tempList);
				}

				return null;
			}
		});

		Optional<Magazine> result = dialog.showAndWait();
		if (result.isPresent()) {
			magazine.SetName(result.get().GetName());
			magazine.SetWeeklyCost(result.get().GetWeeklyCost());
			magazine.SetSupplimentList(tempList);

			// save the updated magazine to serialize file
			SaveToSerialiseFile_Magazine();
		}
	}

	private class CreateNewSup implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			CreateNewSupplements(0);
		}
	}

	private void CreateNewSupplements(int opt) {

		Dialog<Supplement> dialog = new Dialog<>();
		dialog.setTitle("Create Supplement Window");
		dialog.setHeaderText("Enter the supplement's information to create a new Magazine");
		dialog.setResizable(true);

		// Magazine name
		Text supplementName = new Text("Name");
		TextField supplementNameText = new TextField();

		// Magazine weekly cost
		Text supplementCost = new Text("Weekly Cost");
		TextField supplementCostText = new TextField();

		GridPane root = new GridPane();
		root.setMinSize(200, 100);
		root.setPadding(new Insets(10, 10, 10, 10));
		root.setVgap(5);
		root.setHgap(30);
		root.setAlignment(Pos.TOP_CENTER);
		root.setStyle("-fx-background-color: BEIGE;");

		root.add(supplementName, 0, 0);
		root.add(supplementNameText, 1, 0);
		root.add(supplementCost, 0, 1);
		root.add(supplementCostText, 1, 1);

		supplementName.setStyle("-fx-font: normal bold 15px 'serif' ");
		supplementCost.setStyle("-fx-font: normal bold 15px 'serif' ");

		dialog.getDialogPane().setContent(root);

		// Add button to dialog
		ButtonType buttonTypeOk = new ButtonType("Okay", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

		dialog.setResultConverter(new Callback<ButtonType, Supplement>() {
			@Override
			public Supplement call(ButtonType b) {
				if (b == buttonTypeOk) {

					return new Supplement(supplementNameText.getText(),
							Double.parseDouble(supplementCostText.getText()));
				}
				return null;
			}
		});

		Optional<Supplement> results = dialog.showAndWait();
		if (results.isPresent()) {

			// if opt is 0 - perform the task under create mode
			// if opt is 1 - perform the task under edit mode
			if (opt == 0) {
				newSupList.add(results.get());
				supplementCount++;
				numOfSupplements.setText("Number of supplements: " + supplementCount);

				// maximum only 4 supplements
				if (supplementCount > 3) {
					createSupplementBtn.setDisable(true);
				}
			}

			if (opt == 1) {
				magazine.GetMazSupplement().add(results.get());
				counter++;
				info.setText("Current number of supplements: " + counter);

				if (counter > 3) {
					addBtn.setDisable(true);
				}
			}
		}
	}

	private class DisplayNewCreateWindow_Customer implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			customerCount = 0;
			tempCusList.clear(); // clear temporary list
			tempAssoCusList.clear();
			CreateNewCustomer();
		}
	}

	private void CreateNewCustomer() {

		Dialog<Customer> dialog = new Dialog<>();
		dialog.setTitle("Create Customer Window");
		dialog.setHeaderText("Select the customer type and enter all information");
		dialog.setResizable(true);

		Button associateCusBtn = new Button("Associate Customer");
		associateCusBtn.setPrefWidth(125);
		associateCusBtn.setOnAction(new CreateNewAssociateCus());
		Button payingCusBtn = new Button("Paying Customer");
		payingCusBtn.setPrefWidth(125);
		payingCusBtn.setOnAction(new CreateNewPayingCus());

		numOfCustomers = new Text();
		numOfCustomers.setText("No. of customers added: " + customerCount);
		numOfCustomers.setFont(Font.font("Calibri", FontWeight.BOLD, 12));
		numOfCustomers.setFill(Color.FIREBRICK);

		// file chooser
		Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();
		Button selectFile = new Button("Read From File");
		selectFile.setPrefWidth(125);
		selectFile.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
		selectFile.setOnAction(e -> {
			File selectedFile = fileChooser.showOpenDialog(stage);

			customerList.clear(); // clear existing customer list first
			ReadCustomerFileChooser(customerList, magazine, selectedFile); // read by filechooser

			// update drop-down list & ChoiceDialog data
			UpdateCustomerDropdownList(0);
			UpdateCustomerDropdownList(1);
			
			//save new info to serialize file
			SaveToSerialiseFile_Customer();

			dialog.close(); // close the create magazine window
		});

		GridPane root = new GridPane();
		root.setMinSize(300, 200);
		root.setPadding(new Insets(10, 10, 10, 10));
		root.setVgap(5);
		root.setHgap(30);
		root.setAlignment(Pos.TOP_CENTER);
		root.setStyle("-fx-background-color: BEIGE;");

		root.add(associateCusBtn, 0, 0);
		root.add(payingCusBtn, 0, 1);
		root.add(selectFile, 0, 2);
		root.add(numOfCustomers, 0, 10);

		dialog.getDialogPane().setContent(root);

		ButtonType buttonTypeOk = new ButtonType("Okay", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

		dialog.setResultConverter(new Callback<ButtonType, Customer>() {
			@Override
			public Customer call(ButtonType b) {

				if (b == buttonTypeOk) {

					customerList.clear();

					customerArrayData = new String[tempCusList.size()];

					for (int i = 0; i < tempCusList.size(); i++) {
						customerList.add(tempCusList.get(i));

						customerArrayData[i] = tempCusList.get(i).GetCustomerName();
					}
					customerDialogData = Arrays.asList(customerArrayData);

					// update drop-down list & ChoiceDialog data
					UpdateCustomerDropdownList(0);
					UpdateCustomerDropdownList(1);
					
					//save new info to serialize file
					SaveToSerialiseFile_Customer();
				}

				return null;
			}
		});

		Optional<Customer> result = dialog.showAndWait();
		if (result.isPresent()) {
			
		}
	}

	private class CreateNewAssociateCus implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			CreateNewAssociateCustomer(0);
		}
	}

	private void CreateNewAssociateCustomer(int code) {

		ArrayList<Supplement> list = new ArrayList<Supplement>();
		Dialog<AssociateCustomer> dialog = new Dialog<>();
		dialog.setTitle("Create Associate Customer Window");
		dialog.setHeaderText("Enter the customer's information to create a new associate customer");
		dialog.setResizable(true);

		// Customer info
		Text customertName = new Text("Name");
		TextField customerNameText = new TextField();
		Text email = new Text("Email");
		TextField emailText = new TextField();
		Text stNumber = new Text("Street Number");
		TextField stNumberText = new TextField();
		Text stName = new Text("Street Name");
		TextField stNameText = new TextField();
		Text suburb = new Text("Suburb");
		TextField suburbText = new TextField();
		Text postcode = new Text("Postcode");
		TextField postcodeText = new TextField();
		Text supplementSelect = new Text("Subscribed Supplement");
		CheckBox[] arr = new CheckBox[magazine.GetMazSupplement().size()];
		HBox supHBox = new HBox(10);
		for (int i = 0; i < magazine.GetMazSupplement().size(); i++) {
			arr[i] = new CheckBox(magazine.GetMazSupplement().get(i).GetName());
			supHBox.getChildren().add(arr[i]);
		}

		Text choiceBoxText = new Text("Paying Customer");
		ChoiceBox<String> payingCustomerChoiceBox = new ChoiceBox<String>();
		StorePayCusToChoiceBox(payingCustomerChoiceBox);

		GridPane root5 = new GridPane();
		root5.setMinSize(400, 300);
		root5.setPadding(new Insets(10, 10, 10, 10));
		root5.setVgap(10);
		root5.setHgap(30);
		root5.setAlignment(Pos.TOP_CENTER);
		root5.setStyle("-fx-background-color: BEIGE;");

		root5.add(customertName, 0, 0); // column, row
		root5.add(customerNameText, 1, 0);
		root5.add(email, 0, 1);
		root5.add(emailText, 1, 1);
		root5.add(stNumber, 0, 2);
		root5.add(stNumberText, 1, 2);
		root5.add(stName, 0, 3);
		root5.add(stNameText, 1, 3);
		root5.add(suburb, 0, 4);
		root5.add(suburbText, 1, 4);
		root5.add(postcode, 0, 5);
		root5.add(postcodeText, 1, 5);
		root5.add(supplementSelect, 0, 6);
		root5.add(supHBox, 1, 6);
		if (code == 2) {
			root5.add(choiceBoxText, 0, 7);
			root5.add(payingCustomerChoiceBox, 1, 7);
		}

		// Styling
		customertName.setStyle("-fx-font: normal bold 15px 'serif' ");
		email.setStyle("-fx-font: normal bold 15px 'serif' ");
		stNumber.setStyle("-fx-font: normal bold 15px 'serif' ");
		stName.setStyle("-fx-font: normal bold 15px 'serif' ");
		suburb.setStyle("-fx-font: normal bold 15px 'serif' ");
		postcode.setStyle("-fx-font: normal bold 15px 'serif' ");
		supplementSelect.setStyle("-fx-font: normal bold 15px 'serif' ");
		choiceBoxText.setStyle("-fx-font: normal bold 15px 'serif' ");

		dialog.getDialogPane().setContent(root5);

		ButtonType buttonTypeOk = new ButtonType("Okay", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

		dialog.setResultConverter(new Callback<ButtonType, AssociateCustomer>() {
			@Override
			public AssociateCustomer call(ButtonType b) {

				if (b == buttonTypeOk) {

					// create Address object
					long streetNo = Long.parseLong(stNumberText.getText());
					long post = Long.parseLong(postcodeText.getText());
					Address address = new Address(streetNo, stNameText.getText(), suburbText.getText(), post);

					// create supplement list
					for (int idx = 0; idx < arr.length; idx++) {
						if (arr[idx].isSelected()) {
							list.add(magazine.GetMazSupplement().get(idx));
						}
					}

					customerCount++;// update the number of customer added
					assoCusCount++;

					return new AssociateCustomer(customerNameText.getText(), emailText.getText(), list, address);
				}

				return null;
			}
		});

		Optional<AssociateCustomer> result = dialog.showAndWait();
		if (result.isPresent()) {
			if (code == 0) {
				tempCusList.add(result.get()); // add to outer customer list
				numOfCustomers.setText("No. of customers added: " + customerCount);

			} else if (code == 1) {
				tempAssoCusList.add(result.get()); // add to paying customer's associate customer list
				numOfAssoCusAdded.setText("No. of associate customer added: " + assoCusCount);
			} else {

				// is add associate customer to existing customer list
				customerList.add(result.get());
				String payCus = payingCustomerChoiceBox.getValue();

				// Assign the paying customer
				for (int i = 0; i < customerList.size(); i++) {
					if (customerList.get(i).GetCustomerName().equals(payCus)) {
						((PayingCustomer) customerList.get(i)).GetAssoCusList().add(result.get());
					}
				}
				// update number of customer and display text
				numOfCustomer.setText("Number of customers: " + GetNumberOfCustomers());
				assoCusCount = 0; // set back to zero
			}
		}
	}

	private class CreateNewPayingCus implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			assoCusCount = 0;
			CreateNewPayingCustomer(0);
		}
	}

	private void CreateNewPayingCustomer(int option) {

		String title = "", header = "";

		if (option == 0) {
			title = "Create Paying Customer Window";
			header = "Enter the customer's information to create a new paying customer";
		} else {
			title = "Adding Paying Customer Window";
			header = "Enter the customer's information to add a new paying customer";
		}

		Payment pay = new Payment();
		ArrayList<Supplement> list = new ArrayList<Supplement>();
		Dialog<PayingCustomer> dialog = new Dialog<>();
		dialog.setTitle(title);
		dialog.setHeaderText(header);
		dialog.setResizable(true);

		// Customer info
		Text customertName = new Text("Name");
		TextField customerNameText = new TextField();
		Text email = new Text("Email");
		TextField emailText = new TextField();
		Text stNumber = new Text("Street Number");
		TextField stNumberText = new TextField();
		Text stName = new Text("Street Name");
		TextField stNameText = new TextField();
		Text suburb = new Text("Suburb");
		TextField suburbText = new TextField();
		Text postcode = new Text("Postcode");
		TextField postcodeText = new TextField();
		Text bankNo = new Text("Bank A/C No");
		TextField bankNoText = new TextField();
		Text payment = new Text("Payment Method");
		RadioButton bankType1 = new RadioButton("Credit Card");
		RadioButton bankType2 = new RadioButton("Direct Debit");
		ToggleGroup bankTypegroup = new ToggleGroup();
		bankType1.setToggleGroup(bankTypegroup);
		bankType2.setToggleGroup(bankTypegroup);
		HBox bankHBox = new HBox(10);
		bankHBox.getChildren().addAll(bankType1, bankType2);
		Text supplementSelect = new Text("Subscribed Supplement");
		CheckBox[] arr = new CheckBox[magazine.GetMazSupplement().size()];
		HBox supHBox = new HBox(10);
		for (int i = 0; i < magazine.GetMazSupplement().size(); i++) {
			arr[i] = new CheckBox(magazine.GetMazSupplement().get(i).GetName());
			supHBox.getChildren().add(arr[i]);
		}

		Text associateCusSelect = new Text("Associate Customer");
		Button assoCusBtn = new Button("Associate Customer");
		assoCusBtn.setOnAction(new AddAssociateCustomer());

		numOfAssoCusAdded = new Text();
		numOfAssoCusAdded.setText("No. of associate customer added: " + assoCusCount);
		numOfAssoCusAdded.setFont(Font.font("Calibri", FontWeight.BOLD, 12));
		numOfAssoCusAdded.setFill(Color.BLUE);

		GridPane root6 = new GridPane();
		root6.setMinSize(400, 300);
		root6.setPadding(new Insets(10, 10, 10, 10));
		root6.setVgap(10);
		root6.setHgap(30);
		root6.setAlignment(Pos.TOP_CENTER);
		root6.setStyle("-fx-background-color: BEIGE;");

		root6.add(customertName, 0, 0); // col, row
		root6.add(customerNameText, 1, 0);
		root6.add(email, 0, 1);
		root6.add(emailText, 1, 1);
		root6.add(stNumber, 0, 2);
		root6.add(stNumberText, 1, 2);
		root6.add(stName, 0, 3);
		root6.add(stNameText, 1, 3);
		root6.add(suburb, 0, 4);
		root6.add(suburbText, 1, 4);
		root6.add(postcode, 0, 5);
		root6.add(postcodeText, 1, 5);
		root6.add(bankNo, 0, 6);
		root6.add(bankNoText, 1, 6);
		root6.add(payment, 0, 7);
		root6.add(bankHBox, 1, 7);
		root6.add(supplementSelect, 0, 8);
		root6.add(supHBox, 1, 8);
		root6.add(associateCusSelect, 0, 9);
		root6.add(assoCusBtn, 1, 9);
		root6.add(numOfAssoCusAdded, 0, 10);

		// Styling
		customertName.setStyle("-fx-font: normal bold 15px 'serif' ");
		email.setStyle("-fx-font: normal bold 15px 'serif' ");
		stNumber.setStyle("-fx-font: normal bold 15px 'serif' ");
		stName.setStyle("-fx-font: normal bold 15px 'serif' ");
		suburb.setStyle("-fx-font: normal bold 15px 'serif' ");
		postcode.setStyle("-fx-font: normal bold 15px 'serif' ");
		supplementSelect.setStyle("-fx-font: normal bold 15px 'serif' ");
		bankNo.setStyle("-fx-font: normal bold 15px 'serif' ");
		payment.setStyle("-fx-font: normal bold 15px 'serif' ");
		associateCusSelect.setStyle("-fx-font: normal bold 15px 'serif' ");

		dialog.getDialogPane().setContent(root6);

		ButtonType buttonTypeOk = new ButtonType("Okay", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

		dialog.setResultConverter(new Callback<ButtonType, PayingCustomer>() {
			@Override
			public PayingCustomer call(ButtonType b) {

				if (b == buttonTypeOk) {

					// create Payment object
					long ACNo = Long.parseLong(bankNoText.getText());
					pay.SetBankAcc(ACNo);

					if (bankType1.isSelected()) {
						pay.SetBankType(1);
					} else if (bankType2.isSelected()) {
						pay.SetBankType(0);
					}

					// create Address object
					long streetNo = Long.parseLong(stNumberText.getText());
					long post = Long.parseLong(postcodeText.getText());
					Address address = new Address(streetNo, stNameText.getText(), suburbText.getText(), post);

					// create supplement list
					for (int idx = 0; idx < arr.length; idx++) {
						if (arr[idx].isSelected()) {
							list.add(magazine.GetMazSupplement().get(idx));
						}
					}

					customerCount++; // update the number of customer added
					
					//Store the paying customer's associate customer list to another temporary list
					ArrayList<AssociateCustomer> assoCusListOfPayCus =  new ArrayList<AssociateCustomer>();
					for(int idx=0; idx<tempAssoCusList.size(); idx++) {
						assoCusListOfPayCus.add(tempAssoCusList.get(idx));
					}
	
					return new PayingCustomer(customerNameText.getText(), emailText.getText(), list, pay,
							assoCusListOfPayCus, address);

				}
				return null;
			}
		});

		Optional<PayingCustomer> result = dialog.showAndWait();
		if (result.isPresent()) {

			// option == 0 is creating new paying customer
			// option == 1 is adding paying customer to existing customer list
			if (option == 0) {
				tempCusList.add(result.get());
				numOfCustomers.setText("No. of customers added: " + customerCount); // update the number of customer added
				tempAssoCusList.clear(); //clear the tempAssoCusList for next paying customer to use
			}

			if (option == 1) {
				customerList.add(result.get());
				// update drop-down list & ChoiceDialog data
				UpdateCustomerDropdownList(0);
				UpdateCustomerDropdownList(1);
				numOfCustomer.setText("Number of customers: " + GetNumberOfCustomers());
			}
		}
	}

	private class AddAssociateCustomer implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			CreateNewAssociateCustomer(1);
		}
	}

	private class DisplayEditMagazineWindow implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			ModifyMagazine();
		}
	}

	private void ModifyMagazine() {

		Dialog<Magazine> dialog = new Dialog<>();
		dialog.setTitle("Magazine Modification Window");
		dialog.setHeaderText("Enter the information of Magazine");
		dialog.setResizable(true);

		// Magazine name
		Text magazineName = new Text("Name");
		TextField magazineNameText = new TextField();
		magazineName.setStyle("-fx-font: normal bold 15px 'serif'");

		// Magazine weekly cost
		Text magazineCost = new Text("Weekly Cost");
		TextField magazineCostText = new TextField();
		magazineCost.setStyle("-fx-font: normal bold 15px 'serif' ");

		GridPane root = new GridPane();
		root.setMinSize(200, 100);
		root.setPadding(new Insets(10, 10, 10, 10));
		root.setVgap(5);
		root.setHgap(30);
		root.setAlignment(Pos.TOP_CENTER);
		root.setStyle("-fx-background-color: BEIGE;");

		root.add(magazineName, 0, 0);
		root.add(magazineNameText, 1, 0);
		root.add(magazineCost, 0, 1);
		root.add(magazineCostText, 1, 1);

		dialog.getDialogPane().setContent(root);

		// Add button to dialog
		ButtonType buttonTypeOk = new ButtonType("Okay", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

		dialog.setResultConverter(new Callback<ButtonType, Magazine>() {
			@Override
			public Magazine call(ButtonType b) {

				if (b == buttonTypeOk) {

					if (!magazineNameText.getText().isEmpty())
						magazine.SetName(magazineNameText.getText());

					if (!magazineCostText.getText().isEmpty())
						magazine.SetWeeklyCost(Double.parseDouble(magazineCostText.getText()));
				}
				return null;
			}
		});

		Optional<Magazine> result = dialog.showAndWait();
		if (result.isPresent()) {
			SaveToSerialiseFile_Magazine(); // save to serialize file
		}
	}

	private class DisplayEditSupplementWindow implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			ModifySupplement();
		}
	}

	private void ModifySupplement() {

		Dialog<Supplement> dialog = new Dialog<>();
		dialog.setTitle("Supplements Modification Window");
		dialog.setHeaderText("Select the task you want to perform.\nRemember to click Okay to save your changes");
		dialog.setResizable(true);

		Button editBtn = new Button("Edit Supplement");
		editBtn.setPrefWidth(115);
		editBtn.setOnAction(new EditSupplement());
		addBtn = new Button("Add Supplement");
		addBtn.setPrefWidth(115);
		addBtn.setOnAction(new AddSupplement());
		Button removeBtn = new Button("Delete Supplement");
		removeBtn.setOnAction(new RemoveSupplement());

		counter = magazine.GetMazSupplement().size();
		info = new Text();
		info.setText("Current number of supplements: " + counter);
		info.setFont(Font.font("Calibri", FontWeight.NORMAL, 20));
		info.setFill(Color.FIREBRICK);

		VBox root = new VBox();
		root.setMinSize(400, 200);
		root.setSpacing(20);
		root.setAlignment(Pos.TOP_CENTER);
		root.setStyle("-fx-background-color: BEIGE;");

		root.getChildren().addAll(editBtn, addBtn, removeBtn, info);

		if (counter >= 4) {
			addBtn.setDisable(true);
		}

		dialog.getDialogPane().setContent(root);

		// Add button to dialog
		ButtonType buttonTypeOk = new ButtonType("Okay", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

		dialog.setResultConverter(new Callback<ButtonType, Supplement>() {
			@Override
			public Supplement call(ButtonType b) {

				if (b == buttonTypeOk) {

					// update the drop down list
					UpdateSupplementDropDownList(magazine.GetMazSupplement());

					// save to serialize file
					SaveToSerialiseFile_Magazine();
				}
				return null;
			}
		});

		Optional<Supplement> result = dialog.showAndWait();
		if (result.isPresent()) {

		}
	}

	private class RemoveSupplement implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			RemoveSupplementInnerMethod();
		}
	}

	//remove supplement from magazine service and customer's supplement list
	private void RemoveSupplementInnerMethod() {

		ChoiceDialog<String> dialog = new ChoiceDialog<String>(dialogData.get(0), dialogData);
		dialog.setTitle("Supplement Selection");
		dialog.setHeaderText("Select the supplement to remove");

		Optional<String> result = dialog.showAndWait();

		if (result.isPresent()) {

			for (int i = 0; i < magazine.GetMazSupplement().size(); i++) {
				if (magazine.GetMazSupplement().get(i).GetName().equals(result.get())) {
					magazine.GetMazSupplement().remove(i);
					counter--;
					info.setText("Current number of supplements: " + counter);

					// enable the button
					if (counter < 4) {
						addBtn.setDisable(false);
					}

					// update the drop down list
					UpdateSupplementDropDownList(magazine.GetMazSupplement());

					// remove the supplement from all customer's supplement list
					for (int idx = 0; idx < customerList.size(); idx++) {
						Customer customer = customerList.get(idx);
						if (customer instanceof AssociateCustomer) {
							for (int j = 0; j < customer.GetSuppList().size(); j++) {
								if (customer.GetSuppList().get(j).GetName().equals(result.get())) {
									customer.GetSuppList().remove(j);
								}
							}
						} else {
							for (int n = 0; n < customer.GetSuppList().size(); n++) {
								if (customer.GetSuppList().get(n).GetName().equals(result.get())) {
									customer.GetSuppList().remove(n);
								}
							}

							for (int k = 0; k < ((PayingCustomer) customer).GetAssoCusList().size(); k++) {
								AssociateCustomer cus = ((PayingCustomer) customer).GetAssoCusList().get(k);
								for (int m = 0; m < cus.GetSuppList().size(); m++) {
									if (cus.GetSuppList().get(m).GetName().equals(result.get())) {
										((PayingCustomer) customer).GetAssoCusList().get(k).GetSuppList().remove(m);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private class AddSupplement implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			// AddTheSupplement();
			CreateNewSupplements(1);

			// update the drop down list
			UpdateSupplementDropDownList(magazine.GetMazSupplement());
		}
	}

	private class EditSupplement implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			EditSupplementInfo();
		}
	}

	private void EditSupplementInfo() {

		Dialog<Supplement> dialog = new Dialog<>();
		dialog.setTitle("Edit Supplement Information");
		dialog.setHeaderText("Select the supplement you want to edit.\nClick Edit to save changes.");
		dialog.setResizable(true);

		VBox root = new VBox();
		root.setMinSize(400, 200);
		root.setSpacing(20);
		root.setAlignment(Pos.TOP_CENTER);
		root.setStyle("-fx-background-color: BEIGE;");

		Button[] btnArray = new Button[magazine.GetMazSupplement().size()];
		for (int i = 0; i < btnArray.length; i++) {
			btnArray[i] = new Button(magazine.GetMazSupplement().get(i).GetName());
			btnArray[i].setPrefWidth(75);
			btnArray[i].setOnAction(new EnterSupplementContent());
			root.getChildren().add(btnArray[i]);
		}

		dialog.getDialogPane().setContent(root);

		// Add button to dialog
		ButtonType buttonTypeOk = new ButtonType("Edit", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

		dialog.setResultConverter(new Callback<ButtonType, Supplement>() {
			@Override
			public Supplement call(ButtonType b) {
				if (b == buttonTypeOk) {
					return null;
				}
				return null;
			}
		});

		Optional<Supplement> result = dialog.showAndWait();
		if (result.isPresent()) {
		}
	}

	private class EnterSupplementContent implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			String btn = ((Button) e.getSource()).getText();
			EnterSupplementContent(btn);
		}
	}

	private void EnterSupplementContent(String btnName) {

		Dialog<Supplement> dialog = new Dialog<>();
		dialog.setTitle("Modify Existing Supplements Window");
		dialog.setHeaderText("Enter the supplement's new information");
		dialog.setResizable(true);

		// Magazine name
		Text supplementName = new Text("Name");
		TextField supplementNameText = new TextField();

		// Magazine weekly cost
		Text supplementCost = new Text("Weekly Cost");
		TextField supplementCostText = new TextField();

		GridPane root = new GridPane();
		root.setMinSize(200, 100);
		root.setPadding(new Insets(10, 10, 10, 10));
		root.setVgap(5);
		root.setHgap(30);
		root.setAlignment(Pos.TOP_CENTER);
		root.setStyle("-fx-background-color: BEIGE;");

		root.add(supplementName, 0, 0);
		root.add(supplementNameText, 1, 0);
		root.add(supplementCost, 0, 1);
		root.add(supplementCostText, 1, 1);

		supplementName.setStyle("-fx-font: normal bold 15px 'serif' ");
		supplementCost.setStyle("-fx-font: normal bold 15px 'serif' ");

		dialog.getDialogPane().setContent(root);

		// Add button to dialog
		ButtonType buttonTypeOk = new ButtonType("Save", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

		dialog.setResultConverter(new Callback<ButtonType, Supplement>() {
			@Override
			public Supplement call(ButtonType b) {
				if (b == buttonTypeOk) {

					Supplement s = new Supplement();

					if (!supplementNameText.getText().isEmpty()) {
						s.SetName(supplementNameText.getText());
					}else {
						s.SetName("empty");
					}
						
					if (!supplementCostText.getText().isEmpty()) {
						s.SetWeeklyCost(Double.parseDouble(supplementCostText.getText()));
					}else {
						s.SetWeeklyCost(0);
					}
						
					return s;
				}
				return null;
			}
		});

		Optional<Supplement> results = dialog.showAndWait();
		if (results.isPresent()) {
			
			String oldName=""; //used to retrieve old supplement name
			
			for (int i = 0; i < magazine.GetMazSupplement().size(); i++) {
				if (magazine.GetMazSupplement().get(i).GetName().equals(btnName)) {
					
					// Get the old name for later update first
					oldName = magazine.GetMazSupplement().get(i).GetName();
					
					if (!results.get().GetName().equals("empty")) {
						// Set the new name for magazine
						magazine.GetMazSupplement().get(i).SetName(results.get().GetName());
					}
										
					//update customer supplement info
					UpdateCustomerSupplementInfo(oldName, results.get().GetName(), results.get().GetWeeklyCost());
					
					//update supplement cost for magazine
					if (results.get().GetWeeklyCost() != 0) {
						magazine.GetMazSupplement().get(i).SetWeeklyCost(results.get().GetWeeklyCost());
					}
				}
			}
			// update the drop down list
			UpdateSupplementDropDownList(magazine.GetMazSupplement());
			
			//save new info to serialize file
			SaveToSerialiseFile_Customer();
		}
	}

	private void UpdateCustomerSupplementInfo(String oldName, String newName, double newCost) {

		for (int i = 0; i < customerList.size(); i++) {
			Customer exactCustomer = customerList.get(i);
			ArrayList<Supplement> supList = exactCustomer.GetSuppList();
			for (int idx = 0; idx < supList.size(); idx++) {
				if (supList.get(idx).GetName().equals(oldName)) {
								
					//set new name is newName not null
					if(!newName.equals("empty")) {
						customerList.get(i).GetSuppList().get(idx).SetName(newName);
					}
		
					//set new cost is newCost not 0
					if (newCost > 0) {
						customerList.get(i).GetSuppList().get(idx).SetWeeklyCost(newCost);
					}
				}
			}

			if (exactCustomer instanceof PayingCustomer) {
				ArrayList<AssociateCustomer> assoCusList = ((PayingCustomer) exactCustomer).GetAssoCusList();
				for (int outer = 0; outer < assoCusList.size(); outer++) {
					ArrayList<Supplement> assoCusSupList = assoCusList.get(outer).GetSuppList();
					for (int inner = 0; inner < assoCusSupList.size(); inner++) {
						if (assoCusSupList.get(inner).GetName().equals(oldName)) {
							
							if(!newName.equals("empty")) {
								((PayingCustomer) customerList.get(i)).GetAssoCusList().get(outer).GetSuppList().get(inner)
								.SetName(newName);
							}

							if (newCost > 0) {
								((PayingCustomer) customerList.get(i)).GetAssoCusList().get(outer).GetSuppList()
										.get(inner).SetWeeklyCost(newCost);
							}
						}
					}
				}
			}
		}
	}

	private class DisplayEditCustomerWindow implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			ModifyCustomer();
		}
	}

	private void ModifyCustomer() {

		UpdateCustomerDropdownList(0);
		Dialog<Customer> dialog = new Dialog<>();
		dialog.setTitle("Customer Modification Window");
		dialog.setHeaderText("Select the task you want to perform");
		dialog.setResizable(true);

		Button editBtn = new Button("Edit Customer");
		editBtn.setPrefWidth(145);
		editBtn.setOnAction(new EditCustomerInfo());
		Button removeBtn = new Button("Remove Customer");
		removeBtn.setPrefWidth(145);
		removeBtn.setOnAction(new RemoveCustomerWindow());
		Button addAssoCusBtn = new Button("Add Associate Customer");
		addAssoCusBtn.setPrefWidth(145);
		addAssoCusBtn.setOnAction(new AddAssociateCustomerToExist());
		Button addPayingCustomerBtn = new Button("Add Paying Customer");
		addPayingCustomerBtn.setPrefWidth(145);
		addPayingCustomerBtn.setOnAction(new AddPayingCustomer());

		numOfCustomer = new Text();
		numOfCustomer.setText("Number of customers: " + GetNumberOfCustomers());
		numOfCustomer.setFont(Font.font("Calibri", FontWeight.NORMAL, 20));
		numOfCustomer.setFill(Color.FIREBRICK);

		VBox root = new VBox();
		root.setMinSize(400, 200);
		root.setSpacing(20);
		root.setAlignment(Pos.TOP_CENTER);
		root.setStyle("-fx-background-color: BEIGE;");

		root.getChildren().addAll(editBtn, removeBtn, addAssoCusBtn, addPayingCustomerBtn, numOfCustomer);

		dialog.getDialogPane().setContent(root);

		// Add button to dialog
		ButtonType buttonTypeOk = new ButtonType("Okay", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

		dialog.setResultConverter(new Callback<ButtonType, Customer>() {
			@Override
			public Customer call(ButtonType b) {
				if (b == buttonTypeOk) {
				
					//save new info to serialize file
					SaveToSerialiseFile_Customer();
				}
				return null;
			}
		});

		Optional<Customer> result = dialog.showAndWait();
		if (result.isPresent()) {
		}
	}

	private class RemoveCustomerWindow implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			RemoveCustomer();
		}
	}

	private void RemoveCustomer() {

		UpdateCustomerDropdownList(0);

		ChoiceDialog<String> customerDropdown = new ChoiceDialog<String>(customerDropdownDialogData.get(0),
				customerDropdownDialogData);
		customerDropdown.setTitle("Customer Removal Selection");
		customerDropdown.setHeaderText("Select a customer to remove");

		Optional<String> result = customerDropdown.showAndWait();
		String selected = "NIL";

		if (result.isPresent()) {
			selected = result.get();
		}

		for (int i = 0; i < customerList.size(); i++) {
			Customer customer = customerList.get(i);
			if (customer instanceof PayingCustomer) {
				for (int j = 0; j < ((PayingCustomer) customer).GetAssoCusList().size(); j++) {
					AssociateCustomer cus = ((PayingCustomer) customer).GetAssoCusList().get(j);
					if (cus.GetCustomerName().equals(selected)) {
						((PayingCustomer) customer).GetAssoCusList().remove(j);
					}
				}
			}

			if (customer.GetCustomerName().equals(selected)) {
				customerList.remove(i);
			}
		}

		// update drop-down list & ChoiceDialog data
		UpdateCustomerDropdownList(0);
		UpdateCustomerDropdownList(1);
		numOfCustomer.setText("Number of customers: " + GetNumberOfCustomers());
	}

	private class AddPayingCustomer implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			assoCusCount = 0;
			CreateNewPayingCustomer(1);
		}
	}

	private class AddAssociateCustomerToExist implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			CreateNewAssociateCustomer(2);

			// update drop-down list & ChoiceDialog data
			UpdateCustomerDropdownList(0);
			UpdateCustomerDropdownList(1);
		}
	}

	private class EditCustomerInfo implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			EditCustomerInformation();
		}
	}

	private void EditCustomerInformation() {

		Dialog<Customer> dialog = new Dialog<>();
		dialog.setTitle("Eit Customer Info Window");
		dialog.setHeaderText("Select the customer you wish to edit.\nClick Done to save changes.");
		dialog.setResizable(true);

		allCustomerChoiceBox = new ChoiceBox<String>();
		allCustomerChoiceBox.setValue(customerList.get(0).GetCustomerName()); // default value

		// store customer name to choice box
		UpdateCustomerChoiceBox(allCustomerChoiceBox);

		Button editButton = new Button("Edit");
		editButton.setOnAction(new EditCustomer());

		HBox root = new HBox();
		root.setMinSize(300, 100);
		root.setSpacing(20);
		root.setAlignment(Pos.TOP_CENTER);
		root.setStyle("-fx-background-color: BEIGE;");

		root.getChildren().addAll(allCustomerChoiceBox, editButton);

		dialog.getDialogPane().setContent(root);

		// Add button to dialog
		ButtonType buttonTypeOk = new ButtonType("Done", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

		dialog.setResultConverter(new Callback<ButtonType, Customer>() {
			@Override
			public Customer call(ButtonType b) {

				if (b == buttonTypeOk) {
					// update drop-down list & ChoiceDialog data
					UpdateCustomerDropdownList(0);
					UpdateCustomerDropdownList(1);
				}

				return null;
			}
		});

		Optional<Customer> result = dialog.showAndWait();
		if (result.isPresent()) {

		}
	}

	private boolean isPayingCustomer(String customerName) {
		boolean isPayingCus = false;
		for (int i = 0; i < customerList.size(); i++) {
			if (customerList.get(i).GetCustomerName().equals(allCustomerChoiceBox.getValue())) {
				position = i;
				if (customerList.get(i) instanceof PayingCustomer) {
					isPayingCus = true;
				}
			}
		}
		return isPayingCus;
	}

	private class EditCustomer implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {

			// new window
			Stage secondStage = new Stage();
			boolean isPayingCus = isPayingCustomer(allCustomerChoiceBox.getValue());
			ArrayList<Supplement> list = new ArrayList<Supplement>();

			// Customer info
			Text customertName = new Text("Name");
			TextField customerNameText = new TextField();
			Text email = new Text("Email");
			TextField emailText = new TextField();
			Text stNumber = new Text("Street Number");
			TextField stNumberText = new TextField();
			Text stName = new Text("Street Name");
			TextField stNameText = new TextField();
			Text suburb = new Text("Suburb");
			TextField suburbText = new TextField();
			Text postcode = new Text("Postcode");
			TextField postcodeText = new TextField();

			Text bankNo = new Text("Bank A/C No");
			TextField bankNoText = new TextField();
			Text payment = new Text("Payment Method");
			RadioButton bankType1 = new RadioButton("Credit Card");
			RadioButton bankType2 = new RadioButton("Direct Debit");
			ToggleGroup bankTypegroup = new ToggleGroup();
			bankType1.setToggleGroup(bankTypegroup);
			bankType2.setToggleGroup(bankTypegroup);
			HBox bankHBox = new HBox(10);
			bankHBox.getChildren().addAll(bankType1, bankType2);

			Text supplementSelect = new Text("Subscribed Supplement");
			CheckBox[] arr = new CheckBox[magazine.GetMazSupplement().size()];
			HBox supHBox = new HBox(10);
			for (int i = 0; i < magazine.GetMazSupplement().size(); i++) {
				arr[i] = new CheckBox(magazine.GetMazSupplement().get(i).GetName());
				supHBox.getChildren().add(arr[i]);
			}

			Button buttonUpdate = new Button("Update");
			buttonUpdate.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {

					boolean isUpdateSupplement = false;

					// Set new value if textfield is not empty
					if (!customerNameText.getText().isEmpty())
						customerList.get(position).SetCustomerName(customerNameText.getText());
					if (!emailText.getText().isEmpty())
						customerList.get(position).SetEmail(emailText.getText());
					if (!stNumberText.getText().isEmpty())
						customerList.get(position).GetAddress().SetStreetNumber(Long.parseLong(stNumberText.getText()));
					if (!stNameText.getText().isEmpty())
						customerList.get(position).GetAddress().SetStreetName(stNameText.getText());
					if (!suburbText.getText().isEmpty())
						customerList.get(position).GetAddress().SetSuburb(suburbText.getText());
					if (!postcodeText.getText().isEmpty())
						customerList.get(position).GetAddress().SetPostcode(Long.parseLong(postcodeText.getText()));

					for (int idx = 0; idx < arr.length; idx++) {
						if (arr[idx].isSelected()) {
							list.add(magazine.GetMazSupplement().get(idx));
							isUpdateSupplement = true;
						}
					}

					if (isUpdateSupplement)
						customerList.get(position).SetSuppList(list);

					if (isPayingCus) {
						if (!bankNoText.getText().isEmpty())
							((PayingCustomer) customerList.get(position)).GetPayment()
									.SetBankAcc(Long.parseLong(bankNoText.getText()));

						if (bankType1.isSelected()) {
							((PayingCustomer) customerList.get(position)).GetPayment().SetBankType(1);
						} else if (bankType2.isSelected()) {
							((PayingCustomer) customerList.get(position)).GetPayment().SetBankType(0);
						}
					}

					for (int outer = 0; outer < customerList.size(); outer++) {
						if (customerList.get(outer) instanceof PayingCustomer) {
							ArrayList<AssociateCustomer> associateCusList = ((PayingCustomer) customerList.get(outer))
									.GetAssoCusList();
							for (int inner = 0; inner < associateCusList.size(); inner++) {
								if (associateCusList.get(inner).GetCustomerName()
										.equals(allCustomerChoiceBox.getValue())) {
									if (!customerNameText.getText().isEmpty())
										associateCusList.get(inner).SetCustomerName(customerNameText.getText());
									if (!emailText.getText().isEmpty())
										associateCusList.get(inner).SetEmail(emailText.getText());
									if (!stNumberText.getText().isEmpty())
										associateCusList.get(inner).GetAddress()
												.SetStreetNumber(Long.parseLong(stNumberText.getText()));
									if (!stNameText.getText().isEmpty())
										associateCusList.get(inner).GetAddress().SetStreetName(stNameText.getText());
									if (!suburbText.getText().isEmpty())
										associateCusList.get(inner).GetAddress().SetSuburb(suburbText.getText());
									if (!postcodeText.getText().isEmpty())
										associateCusList.get(inner).GetAddress()
												.SetPostcode(Long.parseLong(postcodeText.getText()));

									for (int idx = 0; idx < arr.length; idx++) {
										if (arr[idx].isSelected()) {
											list.add(magazine.GetMazSupplement().get(idx));
										}
									}
									// set new supplement list
									associateCusList.get(inner).SetSuppList(list);
								}
							}
						}
					}
					secondStage.close();// close the new window
				}
			});

			GridPane gridPane = new GridPane();
			gridPane.setMinSize(500, 500);
			gridPane.setPadding(new Insets(10, 10, 10, 10));
			gridPane.setVgap(5);
			gridPane.setHgap(5);
			gridPane.setAlignment(Pos.TOP_CENTER);
			gridPane.setStyle("-fx-background-color: BEIGE;");

			gridPane.add(customertName, 0, 0); // column, row
			gridPane.add(customerNameText, 1, 0);
			gridPane.add(email, 0, 1);
			gridPane.add(emailText, 1, 1);
			gridPane.add(stNumber, 0, 2);
			gridPane.add(stNumberText, 1, 2);
			gridPane.add(stName, 0, 3);
			gridPane.add(stNameText, 1, 3);
			gridPane.add(suburb, 0, 4);
			gridPane.add(suburbText, 1, 4);
			gridPane.add(postcode, 0, 5);
			gridPane.add(postcodeText, 1, 5);
			if (isPayingCus) {
				gridPane.add(bankNo, 0, 6);
				gridPane.add(bankNoText, 1, 6);
				gridPane.add(payment, 0, 7);
				gridPane.add(bankHBox, 1, 7);
			}
			gridPane.add(supplementSelect, 0, 8);
			gridPane.add(supHBox, 1, 8);
			gridPane.add(buttonUpdate, 2, 10);

			// Styling
			customertName.setStyle("-fx-font: normal bold 15px 'serif'");
			email.setStyle("-fx-font: normal bold 15px 'serif'");
			stNumber.setStyle("-fx-font: normal bold 15px 'serif'");
			stName.setStyle("-fx-font: normal bold 15px 'serif'");
			suburb.setStyle("-fx-font: normal bold 15px 'serif'");
			postcode.setStyle("-fx-font: normal bold 15px 'serif'");
			supplementSelect.setStyle("-fx-font: normal bold 15px 'serif'");
			bankNo.setStyle("-fx-font: normal bold 15px 'serif'");
			payment.setStyle("-fx-font: normal bold 15px 'serif'");
			buttonUpdate.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");

			Scene secondScene = new Scene(gridPane, 500, 500);
			secondStage.setScene(secondScene); // set the scene
			secondStage.setTitle("Edit customer " + allCustomerChoiceBox.getValue() + " window");
			secondStage.show();
		}
	}

	private int GetNumberOfCustomers() {

		HashSet<String> nameList = new HashSet<String>();
		for (int outer = 0; outer < customerList.size(); outer++) {
			Customer customer = customerList.get(outer);
			nameList.add(customer.GetCustomerName());

			if (customer instanceof PayingCustomer) {
				ArrayList<AssociateCustomer> assoList = ((PayingCustomer) customer).GetAssoCusList();
				for (int inner = 0; inner < assoList.size(); inner++) {
					nameList.add(assoList.get(inner).GetCustomerName());
				}
			}
		}

		return nameList.size();
	}

	private void StorePayCusToChoiceBox(ChoiceBox<String> cBox) {
		for (int i = 0; i < customerList.size(); i++) {
			Customer customer = customerList.get(i);
			if (customer instanceof PayingCustomer) {
				cBox.getItems().add(customer.GetCustomerName());
			}
		}
	}

	private void UpdateSupplementDropDownList(ArrayList<Supplement> list) {
		// update the drop down list
		String[] newArray = new String[list.size()];
		for (int n = 0; n < list.size(); n++) {
			newArray[n] = list.get(n).GetName();
		}
		arrayData = newArray;
		dialogData = Arrays.asList(arrayData);
	}

	private void UpdateCustomerDropdownList(int code) {

		HashSet<String> set = new HashSet<String>();
		int counter = 0;

		for (int i = 0; i < customerList.size(); i++) {
			Customer customer = customerList.get(i);
			if (customer instanceof AssociateCustomer) {
				set.add(customer.GetCustomerName());
			} else {
				set.add(customer.GetCustomerName());
				for (int idx = 0; idx < ((PayingCustomer) customer).GetAssoCusList().size(); idx++) {
					set.add(((PayingCustomer) customer).GetAssoCusList().get(idx).GetCustomerName());
				}
			}
		}

		if (code == 0) {
			dropdown = new String[set.size()];
			for (String drop : set) {
				dropdown[counter] = drop;
				counter++;
			}
			customerDropdownDialogData = Arrays.asList(dropdown);
		} else {
			customerArrayData = new String[set.size()];
			for (String customerName : set) {
				customerArrayData[counter] = customerName;
				counter++;
			}
			customerDialogData = Arrays.asList(customerArrayData);
		}
	}

	private void UpdateCustomerChoiceBox(ChoiceBox<String> box) {

		HashSet<String> set = new HashSet<String>();
		for (int i = 0; i < customerList.size(); i++) {
			Customer customer = customerList.get(i);
			set.add(customer.GetCustomerName());

			if (customer instanceof PayingCustomer) {
				ArrayList<AssociateCustomer> list = ((PayingCustomer) customer).GetAssoCusList();
				for (int idx = 0; idx < list.size(); idx++) {
					set.add(list.get(idx).GetCustomerName());
				}
			}
		}

		Iterator<String> itr = set.iterator();
		while (itr.hasNext()) {
			box.getItems().add(itr.next());
		}
	}

	public static Magazine ReadMagazineSerialise() {

		Magazine temp = null;

		try {
			FileInputStream fileIn = new FileInputStream("MagazineSerialize.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			temp = (Magazine) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
			return null;
		} catch (ClassNotFoundException c) {
			System.out.println("Magazine not found");
			c.printStackTrace();
			return null;
		}
		return temp;
	}

	private void SaveToSerialiseFile_Magazine() {

		try {
			FileOutputStream fileOut = new FileOutputStream("MagazineSerialize.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(magazine);
			out.close();
			fileOut.close();
			System.out.println("Serialized data is saved in MagazineSerialize.ser");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}
	
	public static ArrayList<Customer> ReadCustomerSerialise() {
		
		ArrayList<Customer> list = new ArrayList<Customer>();
		
		try {
			FileInputStream fileIn = new FileInputStream("CustomerSerialize.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);

			list = (ArrayList<Customer>) in.readObject();
			
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException c) {
			System.out.println("Customer not found");
			c.printStackTrace();
		}
		
		return list;
	}
	
	private void SaveToSerialiseFile_Customer() {
		try {
			FileOutputStream fileOut = new FileOutputStream("CustomerSerialize.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(customerList);
			out.close();
			fileOut.close();
			System.out.println("Serialized data is saved in CustomerSerialize.ser");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	private Magazine ReadMagazineFileChooser(File filepath) {

		ArrayList<Supplement> suppList = new ArrayList<Supplement>();
		String magazineName = "";
		double magazineWeeklyCost = 0.0;

		try {
			Scanner file = new Scanner(filepath);

			String theLine = file.nextLine();
			String[] magazineData = theLine.split(",");
			magazineName = magazineData[0];
			String magazineCost = magazineData[1];
			magazineWeeklyCost = Double.parseDouble(magazineCost);

			while (file.hasNextLine()) {

				String line = file.nextLine();
				String[] data = line.split(",");
				String supplementName = data[0];
				String weeklyCost = data[1];
				double cost = Double.parseDouble(weeklyCost);

				Supplement supplement = new Supplement(supplementName, cost);
				suppList.add(supplement);
			}

			file.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			System.exit(0);
		}

		Magazine magazine = new Magazine(magazineName, magazineWeeklyCost, suppList);

		return magazine;
	}

	private void ReadCustomerFileChooser(ArrayList<Customer> cusList, Magazine magazine, File filepath) {

		try {
			Scanner file = new Scanner(filepath);

			while (file.hasNextLine()) {
				String line = file.nextLine();
				String[] data = line.split(",");
				String code = data[0];

				// first data is 1 - associate customer
				if (code.equals("1")) {

					String[] array = new String[2];
					String streetNo = data[1];
					String streetName = data[2];
					String suburb = data[3];
					String postCode = data[4];
					String cusName = data[5];
					String cusEmail = data[6];
					array[0] = data[7];
					array[1] = data[8];

					long strNumber = Long.parseLong(streetNo);
					long postcode = Long.parseLong(postCode);

					Address address = new Address(strNumber, streetName, suburb, postcode);
					ArrayList<Supplement> supList = new ArrayList<Supplement>();

					StoreSupplement(array, supList, magazine);

					AssociateCustomer cus = new AssociateCustomer(cusName, cusEmail, supList, address);
					cusList.add(cus);
				} else {
					//first data is 2 - paying customer
					ArrayList<AssociateCustomer> assoCusList = new ArrayList<AssociateCustomer>();

					String[] array = new String[2];
					String streetNo = data[1];
					String streetName = data[2];
					String suburb = data[3];
					String postCode = data[4];
					String cusName = data[5];
					String cusEmail = data[6];
					array[0] = data[7];
					array[1] = data[8];
					String bankNo = data[9];
					String bankType = data[10];

					long strNumber = Long.parseLong(streetNo);
					long postcode = Long.parseLong(postCode);
					long bankNumber = Long.parseLong(bankNo);
					int paymentMethod = Integer.parseInt(bankType);

					Payment payment = new Payment(bankNumber, paymentMethod);
					Address address = new Address(strNumber, streetName, suburb, postcode);
					ArrayList<Supplement> supList = new ArrayList<Supplement>();
					StoreSupplement(array, supList, magazine);

					// Associate customer 1 info
					String[] array2 = new String[2];
					String streetNo2 = data[11];
					String streetName2 = data[12];
					String suburb2 = data[13];
					String postCode2 = data[14];
					String cusName2 = data[15];
					String cusEmail2 = data[16];
					array2[0] = data[17];
					array2[1] = data[18];

					long strNumber2 = Long.parseLong(streetNo2);
					long postcode2 = Long.parseLong(postCode2);
					Address address2 = new Address(strNumber2, streetName2, suburb2, postcode2);
					ArrayList<Supplement> supList2 = new ArrayList<Supplement>();
					StoreSupplement(array2, supList2, magazine);
					AssociateCustomer cus2 = new AssociateCustomer(cusName2, cusEmail2, supList2, address2);

					// Associate customer 2 info
					String[] array3 = new String[2];
					String streetNo3 = data[19];
					String streetName3 = data[20];
					String suburb3 = data[21];
					String postCode3 = data[22];
					String cusName3 = data[23];
					String cusEmail3 = data[24];
					array3[0] = data[25];
					array3[1] = data[26];

					long strNumber3 = Long.parseLong(streetNo3);
					long postcode3 = Long.parseLong(postCode3);
					Address address3 = new Address(strNumber3, streetName3, suburb3, postcode3);
					ArrayList<Supplement> supList3 = new ArrayList<Supplement>();
					StoreSupplement(array3, supList3, magazine);
					AssociateCustomer cus3 = new AssociateCustomer(cusName3, cusEmail3, supList3, address3);

					assoCusList.add(cus2);
					assoCusList.add(cus3);
					PayingCustomer cus = new PayingCustomer(cusName, cusEmail, supList, payment, assoCusList, address);
					cusList.add(cus);
				}
			}

			file.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			System.exit(0);
		}
	}

	private void StoreSupplement(String[] strArray, ArrayList<Supplement> supList, Magazine magazine) {

		// supplements
		ArrayList<Supplement> list = magazine.GetMazSupplement();
		Supplement s1 = list.get(0);
		Supplement s2 = list.get(1);  
		Supplement s3 = list.get(2);
		Supplement s4 = list.get(3);

		for (int i = 0; i < strArray.length; i++) {
			if (strArray[i].equals(s1.GetName())) {
				supList.add(s1);
			} else if (strArray[i].equals(s2.GetName())) {
				supList.add(s2);
			} else if (strArray[i].equals(s3.GetName())) {
				supList.add(s3);
			} else {
				supList.add(s4);
			}
		}
	}
}
