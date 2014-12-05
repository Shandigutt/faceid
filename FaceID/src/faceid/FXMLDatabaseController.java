/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package faceid;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;

public class FXMLDatabaseController implements Initializable {
 
    private static Connection con;
    private static Statement stat;
    private PreparedStatement prep;
    public ObservableList<UserData> data;
   
    @FXML
    private TableView measurement_tabel;
    @FXML
    private TableColumn column1;
    @FXML
    private TableColumn column2;
    @FXML
    private TableColumn column3;
    @FXML
    private TableColumn column4;
    @FXML
    private TableColumn column5;
//  @FXML
//  private Button btn_db_save;

    @Override

    public void initialize(URL url, ResourceBundle rb) {
        // connectToDatabase()
        con=JavaConnect.ConnecrDb();
            data = FXCollections.observableArrayList();
            ResultSet rs = null;
        try {
            rs = con.createStatement().executeQuery("select * from TissueThicknessData" );
            while (rs.next()) {
//                UserData userData = new UserData(rs.getString("AgeGroup"), rs.getString("Gender"), rs.getString("FeaturePoint"), rs.getString("CTScanLayerNumber"), rs.getString("Distance"));
//                data.add(userData);
                data.add(new UserData(""+rs.getString("AgeGroup"), ""+rs.getString("Gender"), ""+rs.getString("FeaturePoint"), ""+rs.getString("CTScanLayerNumber"), ""+rs.getString("Distance")));
//                System.out.println(userData);
                //System.out.println(rs.getString("AgeGroup"));       
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
            column1.setCellValueFactory(new PropertyValueFactory("ageGroup"));
//            column1.setCellValueFactory(new PropertyValueFactory<UserData, String>("AgeGroup"));
            column2.setCellValueFactory(new PropertyValueFactory("gender"));
            column3.setCellValueFactory(new PropertyValueFactory("featurePoint"));
            column4.setCellValueFactory(new PropertyValueFactory("cTScanLayerNumber"));
            column5.setCellValueFactory(new PropertyValueFactory("distance"));
//          measurement_tabel.setItems(null);
            measurement_tabel.setItems(data);
    }
}



