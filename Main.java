package Application;

import javafx.scene.control.Button;
import java.beans.Statement;
import java.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import java.sql.*;

public class Main extends Application{
    private Label lblStatus = new Label("");

    private Label lblID = new Label("ID");
    private java.awt.TextField txtID = new java.awt.TextField();
    private Label lblLastName = new Label("Last name");
    private java.awt.TextField txtLastName = new java.awt.TextField();
    private Label lblFirstName = new Label("First name");
    private java.awt.TextField txtFirstName = new java.awt.TextField();
    private Label lblMI = new Label("Middle Initile");
    private java.awt.TextField txtMI = new java.awt.TextField();
    private Label lblAddress = new Label("Address");
    private java.awt.TextField txtAddress = new java.awt.TextField();
    private Label lblCity = new Label("City");
    private java.awt.TextField txtCity = new java.awt.TextField();
    private Label lblState = new Label("State");
    private java.awt.TextField txtState = new java.awt.TextField();
    private Label lblPhone = new Label("Telephone");
    private java.awt.TextField txtPhone = new java.awt.TextField();
    private Label lblEmail = new Label("Email");
    private java.awt.TextField txtEmail = new java.awt.TextField();
    

    private Button btnView = new Button("View");
    private Button btnInsert = new Button("Insert");
    private Button btnUpdate = new Button("Update");
    private Button btnClear = new Button("Clear");

    private Statement stat;

    @Override
    public void start(Stage primaryStage) throws Exception{
        HBox hbox1 = new HBox(5);
        hbox1.getChild().addAll(lblID, txtID);
        HBox hbox2 = new HBox(5);
        hbox2.getChild().addAll(lblLastName,txtLastName,lblFirstName,txtFirstName);
        HBox hbox3 = new HBox(5);
        hbox3.getChild().addAll(lblAddress,txtAddress);
        HBox hbox4 = new HBox(5);
        hbox4.getChild().addAll(lblCity,txtCity,lblState,txtState);
        HBox hbox5 = new HBox(5);
        hbox5.getChild().addAll(lblPhone,txtPhone,lblEmail,txtEmail);
       
        VBox vbox = new VBox(5);
        vbox.getChild().addAll(hbox1,hbox2,hbox3,hbox4,hbox5);

        HBox btnBox = new HBox(5);
        btnBox.getChild().addAll(btnView,btnInsert,btnUpdate,btnClear);
        btnBox.setAlignment(Pox.CENTER);

        BorderPane pane = new BorderPane();
        pane.setTop(LblStatus);
        pane.setCenter(vbox);
        pane.setBottem(btnBox);

        Scene scene = new Scene(pane, 500, 200);
        primaryStage.setTitle("Exercise 34.1 - Staff Table");
        primaryStage.setScene(scene);
        primaryStage.show();

        initializeDB();

        btnView.setOnAction(e -> view());
        btnInsert.setOnAction(e -> insert());
        btnClear.setOnAction(e -> clear());
        btnUpdate.setOnAction(e -> update());
    }

    private void view(){
       String query = "SELECT * FROM Staff WHERE id = '" + txtID.getText().trim() +"'";
       try {
        ResultSet RS = stat.executeQuery(query);
        LoadFields(RS);

       } catch (SQLException Ex) {
        lblStatus.setText("Query Failed");
        System.err.println("There is a problem with the query" + Ex);
       }
    }

    private void LoadFields(ResultSet RS) throws SQLException {
        if (RS.next()) {
            txtLastName.setText(RS.getString(2));
            txtFirstName.setText(RS.getString(3));
            txtMI.setText(RS.getString(4));
            txtAddress.setText(RS.getString(5));
            txtCity.setText(RS.getString(6));
            txtState.setText(RS.getString(7));
            txtPhone.setText(RS.getString(8));
            txtEmail.setText(RS.getString(9));
            lblStatus.setText("Record Found");
        }
        else{
            txtLastName.setText("");
            txtFirstName.setText("");
            txtMI.setText("");
            txtAddress.setText("");
            txtCity.setText("");
            txtState.setText("");
            txtPhone.setText("");
            txtEmail.setText("");
            lblStatus.setText("Record Not Found");
        }
    }

    private void insert(){
        String insertQuery = 
        "INSERT INTO `staff`(id, lastName, firstName, mi, address," +  
        "city, state, telephone, email) VALUES (" + txtID.getText().trim() +
        "','" + txtLastName.getText().trim() + 
        "','" + txtFirstName.getText().trim() +
        "','" + txtMI.getText().trim() + 
        "','" + txtAddress.getText().trim() + 
        "','" + txtCity.getText().trim() +
        "','" + txtState.getText().trim() + 
        "','" + txtPhone.getText().trim() + 
        "','" + txtEmail.getText().trim() + "');";
try {
    Statement.executeUpdate(insertQuery);
    lblStatus.setText("Insert Succeeded");
} catch (SQLException Ex) {
    lblStatus.setText("Insert Failed: " + Ex);
    System.out.println("Insert Failed: " + Ex);
}
    }

    private void clear(){
        txtLastName.setText(null);
        txtFirstName.setText(null);
        txtMI.setText(null);
        txtAddress.setText(null);
        txtCity.setText(null);
        txtState.setText(null);
        txtPhone.setText(null);
        txtEmail.setText(null);
        lblStatus.setText("Record Cleard");
    }

    private void update(){
        String updateQuery =
        "UPDATE staff SET " + 
        "lastName = '" + txtLastName.getText().trim() +
        "' , firstName = '" + txtFirstName.getText().trim() +
        "' , MI = '" + txtMI.getText().trim() +
        "' , address = '" + txtAddress.getText().trim() +
        "' , city = '" + txtCity.getText().trim() +
        "' , state = '" + txtState.getText().trim() +
        "' , telephone = '" + txtPhone.getText().trim() +
        "' , email = '" + txtEmail.getText().trim() +
        "' , WHERE id = '" + txtID.getText().trim() + "';" ;
    }


    private void initializeDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver Loaded"); 
            Connection connection = DriverManager.getConnection
                ("jdbc:mysql: //apollo.gtc.edu/staff","aaaaa","password");
            System.out.println("Database Conected");
            Statement = connection.createStatement();

        } catch (Exception Ex) {
            System.out.println("There has been a problem with connecting" + Ex);
            lblStatus.setText("Database Connection Failed. Is your VPN active?");
        }
    }
    public static void main(String[] args) {
        
        launch(args);
    }
}