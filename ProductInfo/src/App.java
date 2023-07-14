import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application implements EventHandler<ActionEvent> {

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void handle(ActionEvent arg0) {

    }

    @Override
    public void start(Stage stage) throws Exception {
        Button button1 = new Button("Save");
        Button button2 = new Button("Update");
        Button button3 = new Button("Delete");

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

        HBox hbox1 = new HBox(10);
        hbox1.setPadding(new Insets(20));
        hbox1.setMinWidth(300);
        hbox1.setStyle("-fx-background-color: #FBFAF0");

        hbox1.getChildren().addAll(button1, button2, button3);

        VBox vBox= new VBox(10);
        vBox.getChildren().addAll(hbox1, tableView);

        
        Scene scene = new Scene(vBox, 620, 360);
        stage.setScene(scene);

        stage.show();

    }
}
