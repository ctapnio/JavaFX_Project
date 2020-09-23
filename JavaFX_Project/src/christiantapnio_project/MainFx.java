/*
 * Christian Tapnio
 * 991-359-879
 * Project
 */
package christiantapnio_project;

import christiantapnio_project.content.Order;
import christiantapnio_project.content.OrderFile;
import christiantapnio_project.content.OrderSearch;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author taipan
 */
public class MainFx extends Application {

    private ArrayList<Order> orderList = new ArrayList<>();
    private TextField txtOrderID = new TextField();
    private TextField txtCustomerID = new TextField();
    private TextField txtProduct = new TextField();
    private TextField txtShipping = new TextField();

    private Label lblOrderID = new Label("Order ID: ");
    private Label lblCustomerID = new Label("Customer ID: ");
    private Label lblProduct = new Label("Product: ");
    private Label lblShipping = new Label("Shipping Method: ");

    private Button btnNext = new Button("Next >");
    private Button btnPrevious = new Button("< Previous");
    private Button btnFirst = new Button("<< First");
    private Button btnLast = new Button("Last >>");

    private Button btnUpdate = new Button("Update");
    private Button btnDelete = new Button("Delete");
    private Button btnAdd = new Button("Add");

    private TextField txtSearch = new TextField();
    private Button btnSearch = new Button("Search");

    private HBox hBoxSearch = new HBox(txtSearch, btnSearch);

    private VBox hBoxLabels = new VBox(lblOrderID, lblCustomerID,
            lblProduct, lblShipping);

    private VBox boxTxtFields = new VBox(txtOrderID,
            txtCustomerID, txtProduct, txtShipping);
    private HBox boxButtonsNav = new HBox(btnFirst, btnPrevious,
            btnNext, btnLast);
    private VBox boxButtonsEditor = new VBox(btnUpdate,
            btnDelete, btnAdd);

    private int count = orderList.size();

    @Override
    public void start(Stage primaryStage) throws IOException {

        Alert dlgErr = new Alert(Alert.AlertType.ERROR);
        dlgErr.setTitle("Error");
        dlgErr.setHeaderText("Unable to edit");
        dlgErr.setContentText("Product and shipping "
                + "method on available for changes");

        btnNext.setOnAction(e -> {
            count++;
            if (count > orderList.size()) {
                count = orderList.size();
            }
            setTextArea();

        });
        btnPrevious.setOnAction(e -> {
            count--;
            if (count < 0) {
                count = 0;
            }
            setTextArea();
        });
        btnFirst.setOnAction(e -> {
            count = 0;

            setTextArea();
            txtProduct.requestFocus();
        });
        btnLast.setOnAction(e -> {
            count = orderList.size() - 1;

            setTextArea();
        });

        btnUpdate.setOnAction(e -> {
            Alert dlgConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
            Optional<ButtonType> result = dlgConfirmation.showAndWait();

            String message = new String();

            if (result.get() == ButtonType.OK) {
                message = "Updated";
                try {
                    setProductFromTextF();
                    setShippingFromTextF();
                    orderList.get(count).setCustomerID(txtCustomerID.getText());

                    OrderFile.setRecords(orderList);
                } catch (IOException ex) {
                    System.err.println(ex);
                }
            } else {
                message = "Cancelled";
                count--;
                setTextArea();
            }

            Alert dlgMessage = new Alert(Alert.AlertType.INFORMATION);

            dlgMessage.setContentText(message);
            dlgMessage.show();
            txtCustomerID.setDisable(true);

        });

        btnDelete.setOnAction(e -> {
            Alert dlgConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
            Optional<ButtonType> result = dlgConfirmation.showAndWait();

            String message = new String();

            if (result.get() == ButtonType.OK) {
                message = "Deleted";
                try {
                    deleteOrder();
                    count--;
                    setTextArea();
                } catch (IOException ex) {
                    System.err.println(ex);
                }
            } else {
                message = "Cancelled";
                count--;
                setTextArea();
            }

            Alert dlgMessage = new Alert(Alert.AlertType.INFORMATION);

            dlgMessage.setContentText(message);
            dlgMessage.show();

        });
        btnAdd.setOnAction(e -> {

            try {

                addOrder();
                txtCustomerID.setDisable(false);
                txtCustomerID.requestFocus();

            } catch (IOException ex) {
                System.err.println(ex);
            }

        });
        btnSearch.setOnAction(e -> {
            OrderSearch ds;
            try {
                ds = new OrderSearch(txtSearch.getText(), orderList);
                ds.show();
            } catch (IOException ex) {
                System.err.println(ex);
            }

        });
        txtCustomerID.setOnKeyReleased(e -> {
            if (isNumeric(e.getCode())) {
                txtCustomerID.selectBackward();
                Alert dlgError = new Alert(Alert.AlertType.ERROR);
                dlgError.setContentText("Must enter a number");
                dlgError.show();
            }
        });
        txtOrderID.setDisable(true);
        txtCustomerID.setDisable(true);
        hBoxLabels.setSpacing(20);
        boxTxtFields.setSpacing(8);
        txtSearch.setPrefWidth(375);
        boxButtonsEditor.setAlignment(Pos.TOP_RIGHT);
        hBoxSearch.setAlignment(Pos.CENTER);
        txtSearch.setPromptText("Empty search bar displays all orders");
        
        GridPane gPane = new GridPane();
        gPane.add(hBoxLabels, 0, 0);
        gPane.add(boxTxtFields, 2, 0);
        gPane.add(boxButtonsNav, 2, 2);
        
        BorderPane bPane = new BorderPane();
        bPane.setCenter(gPane);
        bPane.setBottom(hBoxSearch);
        bPane.setRight(boxButtonsEditor);
        
        setTextArea();
        Scene scene = new Scene(bPane, 500, 225);
        primaryStage.setTitle("Order Record");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        scene.getStylesheets().add("christiantapnio_project/style/OrderStyle.css");
        btnUpdate.getStyleClass().add("Update");
        btnDelete.getStyleClass().add("Delete");
        btnAdd.getStyleClass().add("Add");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void setTextArea() {
        try {
            orderList = OrderFile.getRecords();
            txtOrderID.setText(orderList.get(count).getOrderID());
            txtCustomerID.setText(orderList.get(count).getCustomerID());
            txtProduct.setText(orderList.get(count).getProduct());
            txtShipping.setText(orderList.get(count).getShippingMethod());
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private void setProductFromTextF() throws IOException {

        orderList.get(count).setProduct(txtProduct.getText());
        OrderFile.setRecords(orderList);

    }

    private void setShippingFromTextF() throws IOException {
        orderList.get(count).setShippingMethod(txtShipping.getText());
        OrderFile.setRecords(orderList);

    }

    private void deleteOrder() throws IOException {
        orderList.remove(count);
        OrderFile.setRecords(orderList);
    }

    private void addOrder() throws IOException {

        Order addOne = new Order("O0" + (orderList.size() + 1));
        orderList.add(addOne);
        count = orderList.size() - 1;

        txtOrderID.setText(orderList.get(count).getOrderID());
        txtCustomerID.setText(orderList.get(count).getCustomerID());
        txtCustomerID.setText("C10" + orderList.size());
        txtCustomerID.requestFocus();
        txtProduct.setText("");
        txtShipping.setText("");

    }

    private boolean isNumeric(KeyCode code) {
        switch (code) {
            case LEFT:
            case RIGHT:
            case BACK_SPACE:
            case DELETE:
            case SHIFT:
            case C:
            case DIGIT0:
            case DIGIT1:
            case DIGIT2:
            case DIGIT3:
            case DIGIT4:
            case DIGIT5:
            case DIGIT6:
            case DIGIT7:
            case DIGIT8:
            case DIGIT9:
                return false;
        }
        return true;
    }

}
