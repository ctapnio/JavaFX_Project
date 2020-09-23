package christiantapnio_project.content;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class OrderSearch extends Stage {

    public OrderSearch(String search, ArrayList<Order> orderList) throws IOException {
        this.searchText = search;
        this.orderList = orderList;
        setScene(addScene());
    }
    private String searchText = new String();
    private ArrayList<Order> orderList = new ArrayList<>();
    private TextArea txtDisplayBox = new TextArea();

    private TextField txtSearchBox = new TextField();
    Button btnSearch = new Button("Search");

    private Scene addScene() throws FileNotFoundException, IOException {

        searchRecordList();
        Pane pane = new Pane(txtDisplayBox);
        Scene scene = new Scene(pane, 500, 300);
        return scene;
    }
    private void searchRecordList(){
        String recordList = new String();
        for (int i = 0; i < orderList.size(); i++) {
            Order one = orderList.get(i);
            if (one.getCustomerID().contains(searchText)
                    || one.getProduct().contains(searchText)) {
                recordList += one.getOrderID() + ","
                        + one.getCustomerID() + "," + one.getProduct()
                        + "," + one.getShippingMethod() + "\n";
            } 
        }
        txtDisplayBox.setText(recordList);
        
    }

}
