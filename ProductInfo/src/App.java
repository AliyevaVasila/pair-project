import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {
    TableView<Customer> tableView;
    Button butCreate, butUpdate, butDelete;

    private List<Customer> customers = new ArrayList<>();
    private int selectedIndex = -1;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Customer Info List");

        butCreate = new Button("Create");
        butUpdate = new Button("Update");
        butDelete = new Button("Delete");

        butCreate.setOnAction(this::handleCreateButtonClick);
        butUpdate.setOnAction(this::handleUpdateButtonClick);
        butDelete.setOnAction(this::handleDeleteButtonClick);

        tableView = new TableView<>();

        TableColumn<Customer, Integer> idColumn = new TableColumn<>("ID");
        TableColumn<Customer, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Customer, String> sNameColumn = new TableColumn<>("Surname");
        TableColumn<Customer, Character> genColumn = new TableColumn<>("Gender");
        TableColumn<Customer, Integer> disColumn = new TableColumn<>("Discount");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        sNameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        genColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        disColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));

        tableView.getColumns().add(idColumn);
        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(sNameColumn);
        tableView.getColumns().add(genColumn);
        tableView.getColumns().add(disColumn);

        tableView.setItems(FXCollections.observableArrayList(customers));

        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Customer>() {
            @Override
            public void changed(ObservableValue<? extends Customer> observable, Customer oldValue, Customer newValue) {
                selectedIndex = tableView.getSelectionModel().getSelectedIndex();
                butUpdate.setDisable(selectedIndex < 0);
                butDelete.setDisable(selectedIndex < 0);
            }
        });

        HBox hbox1 = new HBox(10);
        hbox1.setPadding(new Insets(20));
        hbox1.setAlignment(Pos.CENTER);
        hbox1.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
        hbox1.setStyle("-fx-background-color: #FBFAF0");
        hbox1.getChildren().addAll(butCreate, butUpdate, butDelete);

        BorderPane rootPane = new BorderPane();
        rootPane.setPadding(new Insets(20));
        rootPane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        rootPane.setTop(hbox1);
        rootPane.setCenter(tableView);

        Scene scene = new Scene(rootPane);
        stage.setScene(scene);

        stage.show();
    }

    private void handleCreateButtonClick(ActionEvent event) {
        openCustomerDialog("Create Customer", -1);
    }

    private void handleUpdateButtonClick(ActionEvent event) {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            openCustomerDialog("Update Customer", selectedIndex);
        }
    }

    private void handleDeleteButtonClick(ActionEvent event) {
        if (selectedIndex >= 0) {
            deleteCustomer(selectedIndex);
            tableView.setItems(FXCollections.observableArrayList(customers));
            saveToCSV(); // Save the updated data to CSV
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
        saveToCSV();
    }
    private void saveToCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data.csv", false))) {
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
    

    private void deleteCustomer(int index) {
        customers.remove(index);
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

        Button butSave = new Button("Save");

        VBox vbox2 = new VBox(10);
        vbox2.setAlignment(Pos.CENTER);

        vbox2.getChildren().add(butSave);

        gridPane.add(vbox2, 1, 4);

        Scene scene = new Scene(gridPane, 360, 180);
        newStage.setScene(scene);

        // Handle the Save button click event
        butSave.setOnAction(e -> {
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

            if (selectedIndex == -1) {
                Customer newCustomer = new Customer(name, surname, gender, discount);
                // Create a new customer
                addCustomer(newCustomer);
                saveToCSV();
            } else {
                // Update an existing customer
                updateCustomer(selectedIndex, name, surname, gender, discount);

            }

            // Update the TableView
            tableView.setItems(FXCollections.observableArrayList(customers));

            newStage.close();
        });

        newStage.show();
    }
}