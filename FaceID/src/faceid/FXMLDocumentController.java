/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package faceid;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javax.imageio.ImageIO;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

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
    private AnchorPane workSpace;
    @FXML
    private CheckBox edges;
    @FXML
    private CheckBox point1;
    @FXML
    private CheckBox point2;
    @FXML
    private Button calculate;
    @FXML
    private Label thickness;
    @FXML
    private Label x1;
    @FXML
    private Label x2;
    @FXML
    private Label y1;
    @FXML
    private Label y2;
    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView2;
    
    
    
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
        
        imageView1.fitHeightProperty().bind(workSpace.heightProperty());
        //imageView.
        
        fileList.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<String>() {
                Mat fillImage;
                Mat cannyImage;
                MatOfByte matOfByte;
                byte[] byteArray;
                BufferedImage bufferedImage = null;
                
                public void changed(ObservableValue<? extends String> ov, 
                    String old_val, String new_val) {
                    
                    imageView1.setImage(new Image("file:" + location + "\\" + new_val));
                    fillImage  = Highgui.imread(location + "\\" + new_val);
                    cannyImage = new Mat();
                    matOfByte = new MatOfByte();
                    Imgproc.Canny(fillImage, cannyImage, 10, 100, 3, true);//Applying canny algorithm 
                    Highgui.imencode(".jpg", cannyImage, matOfByte);//encoding Mat image to MatOfByte
                    byteArray = matOfByte.toArray();
                    
                    try {

                        InputStream in = new ByteArrayInputStream(byteArray);
                        bufferedImage = ImageIO.read(in);
                        in.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    
                    imageView2.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                    
            }
        });
    }    

    
}
