import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application implements EventHandler<ActionEvent> {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(ActionEvent event) {
        Stage newStage = new Stage();
        newStage.setTitle("Adding Customer");

        Label label = new Label("ID");
        Label label2 = new Label("Name");
        Label label3 = new Label("Surname");
        Label label4 = new Label("Gender");
        Label label5 = new Label("Discount");

        TextField field = new TextField();
        TextField field2 = new TextField();
        TextField field3 = new TextField();
        TextField field4 = new TextField();
        TextField field5 = new TextField();

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(label, label2, label3, label4, label5);

        Button button4 = new Button("Save");

        VBox vbox2 = new VBox(10);
        vbox2.setPadding(new Insets(20));
        vbox2.getChildren().addAll(field, field2, field3, field4, field5, button4);

        HBox hBox2 = new HBox(10);
        hBox2.getChildren().addAll(vbox, vbox2);

        Scene scene = new Scene(hBox2, 620, 360);
        newStage.setScene(scene);

        newStage.show();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Customer Info");

        Button button1 = new Button("Save");
        Button button2 = new Button("Update");
        Button button3 = new Button("Delete");

        button1.setOnAction(this);
        button2.setOnAction(this);
        button3.setOnAction(this);

        TableView<Customer> tableView = new TableView<>();

        TableColumn<Customer, String> idColumn = new TableColumn<>("ID");
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

        // Create an ObservableList to hold the data for the TableView
        ObservableList<Customer> customers = FXCollections.observableArrayList();

        // Add some sample data to the ObservableList
        // customers.add(new Customer("1", "John", "Doe", 'M', 10));
        // customers.add(new Customer("2", "Jane", "Smith", 'F', 5));

        // Set the items of the TableView to the ObservableList
        tableView.setItems(customers);

        HBox hbox1 = new HBox(10);
        hbox1.setPadding(new Insets(20));
        hbox1.setMinWidth(300);
        hbox1.setStyle("-fx-background-color: #FBFAF0");
        hbox1.getChildren().addAll(button1, button2, button3);

        VBox vBox1 = new VBox(10);
        vBox1.getChildren().addAll(hbox1, tableView);

        Scene scene = new Scene(vBox1, 620, 360);
        stage.setScene(scene);

        stage.show();
    }
}
