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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {
    TableView<Customer> tableView;
    Button button1, button2, button3;

    private List<Customer> customers = new ArrayList<>();

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

        TableColumn<Customer, Integer> idColumn = new TableColumn<>("ID");
        TableColumn<Customer, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Customer, String> sNameColumn = new TableColumn<>("Surname");
        TableColumn<Customer, Character> genColumn = new TableColumn<>("Gender");
        TableColumn<Customer, Integer> disColumn = new TableColumn<>("Discount");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
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
                int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
                button2.setDisable(selectedIndex < 0);
                button3.setDisable(selectedIndex < 0);
            }
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

        Scene scene = new Scene(rootPane, 620, 360);
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
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            deleteCustomer(selectedIndex);
        }
    }

    private void addCustomer(String name, String surname, char gender, int discount) {
        int id = customers.size() + 1;
        Customer newCustomer = new Customer(name, surname, gender, discount);
        customers.add(newCustomer);
    }

    private void updateCustomer(int index, String name, String surname, char gender, int discount) {
        Customer customer = customers.get(index);
        customer.setName(name);
        customer.setSurname(surname);
        customer.setGender(gender);
        customer.setDiscount(discount);
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

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(label2, label3, label4, label5);

        Button button4 = new Button("Save");

        VBox vbox2 = new VBox(10);
        vbox2.setAlignment(Pos.CENTER);
        vbox2.setPadding(new Insets(20));

        vbox2.getChildren().addAll(field2, field3, field4, field5, button4);

        HBox hBox2 = new HBox(10);
        hBox2.setAlignment(Pos.CENTER);
        hBox2.getChildren().addAll(vbox, vbox2);

        Scene scene = new Scene(hBox2, 360, 180);
        newStage.setScene(scene);

        // Handle the Save button click event
        button4.setOnAction(e -> {
            // Get the input from the text fields
            String name = field2.getText();
            String surname = field3.getText();
            char gender = field4.getText().charAt(0);
            int discount = Integer.parseInt(field5.getText());

            if (selectedIndex == -1) {
                // Create a new customer
                addCustomer(name, surname, gender, discount);
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

