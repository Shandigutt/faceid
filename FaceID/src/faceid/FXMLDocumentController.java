/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package faceid;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;

/**
 *
 * @author Tharaka
 */
public class FXMLDocumentController implements Initializable {
    
    private String location;
    private static int fileCount = 0;
    private ObservableList filesCollectin;
    @FXML
    private Button open;
    @FXML
    private Label locationPath;
    @FXML
    private ListView<String> fileList;
    @FXML
    private ImageView imageView;
    @FXML
    private AnchorPane workSpace;
    
    
    
    @FXML
    private void openDirectory(ActionEvent event) { // when the open directory button is pressed
        
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Please open the location that contains the scanned images");
        File selectedDirectory = directoryChooser.showDialog(open.getScene().getWindow());
        if(selectedDirectory == null){
            location = "No location selected";
            locationPath.setText(location);
        }else{
            fileCount = 0;
            filesCollectin = FXCollections.observableArrayList();
            for(File x : selectedDirectory.listFiles()){
                if(x.isFile()){
                    filesCollectin.add(x.getName());
                    fileCount++;
                }
            }
            location = selectedDirectory.getAbsolutePath();
            locationPath.setText(location);
            fileList.setItems(filesCollectin);
        }
        
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        imageView.fitHeightProperty().bind(workSpace.heightProperty());
        //imageView.
        
        fileList.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<String>() {
                public void changed(ObservableValue<? extends String> ov, 
                    String old_val, String new_val) {
                        //label.setText(new_val);
                        //label.setTextFill(Color.web(new_val));
                    //System.out.println(location + "\\" + new_val);
                    imageView.setImage(new Image("file:" + location + "\\" + new_val));
                    
            }
        });
    }    

    
}
