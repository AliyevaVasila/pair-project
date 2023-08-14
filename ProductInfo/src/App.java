import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    TableView<Customer> tableView;
    Button button1, button2, button3;

    private List<Customer> customers = new ArrayList<>();
    private int selectedIndex = -1;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Customer Info List");

        button1 = new Button("Create");
        button2 = new Button("Update");
        button3 = new Button("Delete");

        button1.setOnAction(this::handleCreateButtonClick);
        button2.setOnAction(this::handleUpdateButtonClick);
        button3.setOnAction(this::handleDeleteButtonClick);

        tableView = new TableView<>();

        // Set up TableColumns (ID, Name, Surname, Gender, Discount)
        TableColumn<Customer, String> idColumn = new TableColumn<>("ID");
        TableColumn<Customer, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Customer, String> sNameColumn = new TableColumn<>("Surname");
        TableColumn<Customer, Character> genColumn = new TableColumn<>("Gender");
        TableColumn<Customer, Integer> disColumn = new TableColumn<>("Discount");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        sNameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        genColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        disColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));

        tableView.getColumns().addAll(idColumn, nameColumn, sNameColumn, genColumn, disColumn);

        // Load existing data from CSV
        loadFromCSV();

        // Set the items in the TableView
        tableView.setItems(FXCollections.observableArrayList(customers));

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            button2.setDisable(selectedIndex < 0);
            button3.setDisable(selectedIndex < 0);
        });

        HBox hbox1 = new HBox(10);
        hbox1.setPadding(new Insets(20));
        hbox1.setAlignment(Pos.CENTER);
        hbox1.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
        hbox1.setStyle("-fx-background-color: #FBFAF0");
        hbox1.getChildren().addAll(button1, button2, button3);

        BorderPane rootPane = new BorderPane();
        rootPane.setPadding(new Insets(20));
        rootPane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        rootPane.setTop(hbox1);
        rootPane.setCenter(tableView);

        Scene scene = new Scene(rootPane);
        stage.setScene(scene);

        // Save data to CSV when application is closed
        stage.setOnCloseRequest(event -> {
            saveToCSV();
        });

        stage.show();
    }

    private void handleCreateButtonClick(ActionEvent event) {
        openCustomerDialog("Create Customer", -1);
    }

    private void handleUpdateButtonClick(ActionEvent event) {
        if (selectedIndex >= 0) {
            Customer selectedCustomer = customers.get(selectedIndex);
            openCustomerDialog("Update Customer", selectedIndex, selectedCustomer);
        }
    }
    

    private void handleDeleteButtonClick(ActionEvent event) {
        if (selectedIndex >= 0) {
            customers.remove(selectedIndex);
            tableView.setItems(FXCollections.observableArrayList(customers));
            saveToCSV();
        }
    }

    private void addCustomer(Customer customer) {
        customers.add(customer);
    }

    private void updateCustomer(int index, String name, String surname, Character gender, Integer discount) {
        Customer customer = customers.get(index);
        customer.setName(name);
        customer.setSurname(surname);
        customer.setGender(gender);
        customer.setDiscount(discount);
        
    }

    private void saveToCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data.csv"))) {
            for (Customer customer : customers) {
                String data = customer.getID() + "," + customer.getName() + "," + customer.getSurname() + ","
                        + customer.getGender() + "," + customer.getDiscount();
                writer.write(data);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader("data.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 5) {
                    Customer customer = new Customer(data[1], data[2], data[3].charAt(0), Integer.parseInt(data[4]));
                    customers.add(customer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void openCustomerDialog(String title, int selectedIndex, Customer selectedCustomer) {
        Stage newStage = new Stage();
        newStage.setTitle(title);
    
        Label label2 = new Label("Name");
        Label label3 = new Label("Surname");
        Label label4 = new Label("Gender");
        Label label5 = new Label("Discount");
    
        TextField field2 = new TextField(selectedCustomer.getName());
        TextField field3 = new TextField(selectedCustomer.getSurname());
        TextField field4 = new TextField(String.valueOf(selectedCustomer.getGender()));
        TextField field5 = new TextField(String.valueOf(selectedCustomer.getDiscount()));
    
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));
        gridPane.add(label2, 0, 0);
        gridPane.add(label3, 0, 1);
        gridPane.add(label4, 0, 2);
        gridPane.add(label5, 0, 3);
        gridPane.add(field2, 1, 0);
        gridPane.add(field3, 1, 1);
        gridPane.add(field4, 1, 2);
        gridPane.add(field5, 1, 3);
    
        Button button4 = new Button("Save");
    
        VBox vbox2 = new VBox(10);
        vbox2.setAlignment(Pos.CENTER);
    
        vbox2.getChildren().add(button4);
    
        gridPane.add(vbox2, 1, 4);
    
        Scene scene = new Scene(gridPane, 360, 180);
        newStage.setScene(scene);
    
        // Handle the Save button click event
        button4.setOnAction(e -> {
            // Get the input from the text fields
            String name = field2.getText();
            String surname = field3.getText();
            char gender = field4.getText().charAt(0);
            int discount;
            try {
                discount = Integer.parseInt(field5.getText());
            } catch (NumberFormatException ex) {
                discount = 0; // Default value or handle the error case
            }
    
            // Update the selected customer with the new information
            updateCustomer(selectedIndex, name, surname, gender, discount);
    
            // Update the TableView and save changes
            tableView.setItems(FXCollections.observableArrayList(customers));
            saveToCSV();
    
            newStage.close();
        });
    
        newStage.show();
    }
    
    private void openCustomerDialog(String title, int selectedIndex) {
    Stage newStage = new Stage();
    newStage.setTitle(title);

    Label label2 = new Label("Name");
    Label label3 = new Label("Surname");
    Label label4 = new Label("Gender");
    Label label5 = new Label("Discount");

    TextField field2 = new TextField();
    TextField field3 = new TextField();
    TextField field4 = new TextField();
    TextField field5 = new TextField();

    GridPane gridPane = new GridPane();
    gridPane.setAlignment(Pos.CENTER);
    gridPane.setHgap(10);
    gridPane.setVgap(10);
    gridPane.setPadding(new Insets(20));
    gridPane.add(label2, 0, 0);
    gridPane.add(label3, 0, 1);
    gridPane.add(label4, 0, 2);
    gridPane.add(label5, 0, 3);
    gridPane.add(field2, 1, 0);
    gridPane.add(field3, 1, 1);
    gridPane.add(field4, 1, 2);
    gridPane.add(field5, 1, 3);

    Button button4 = new Button("Save");

    VBox vbox2 = new VBox(10);
    vbox2.setAlignment(Pos.CENTER);

    vbox2.getChildren().add(button4);

    gridPane.add(vbox2, 1, 4);

    Scene scene = new Scene(gridPane, 360, 180);
    newStage.setScene(scene);

    // Handle the Save button click event
    button4.setOnAction(e -> {
        // Get the input from the text fields
        String name = field2.getText();
        String surname = field3.getText();
        char gender = field4.getText().charAt(0);
        int discount;
        try {
            discount = Integer.parseInt(field5.getText());
        } catch (NumberFormatException ex) {
            discount = 0; // Default value or handle the error case
        }
        Customer newCustomer = new Customer(name, surname, gender, discount);
        if (selectedIndex == -1) {
            // Create a new customer
            addCustomer(newCustomer);
        } else {
            // Update an existing customer
            updateCustomer(selectedIndex, name, surname, gender, discount);
        }

        // Update the TableView
        tableView.setItems(FXCollections.observableArrayList(customers));
        saveToCSV();

        newStage.close();
    });

    newStage.show();
}


}
