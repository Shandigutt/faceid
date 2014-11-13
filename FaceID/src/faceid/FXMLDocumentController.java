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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import static javafx.scene.paint.Color.rgb;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
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
    private double x1 =0 ,x2 = 0,y1 = 0,y2 = 0,thickness;
    
    @FXML
    private Button open;
    @FXML
    private Label locationPath;
    @FXML
    private ListView<String> fileList;
    @FXML
    private AnchorPane workSpace;
    @FXML
    private RadioButton point1;
    @FXML
    private RadioButton point2;
    @FXML
    private Button calculate;
    @FXML
    private TextField thickness_textField;
    @FXML
    private TextField x1_textField;
    @FXML
    private TextField x2_textField;
    @FXML
    private TextField y1_textField;
    @FXML
    private TextField y2_textField;
    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView2;
    @FXML
    private ToggleGroup point;
    @FXML
    private Button clear;
    @FXML
    private ScrollPane workSpaceScrollPane;
    @FXML
    private SplitPane workSpaceSplitPane;
    @FXML
    private AnchorPane workSpace1;
    
    
    
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
    @FXML
    private void calculate(ActionEvent event) { //calculate tissue thickness 
        
        thickness = (double)Math.sqrt((((x2 - x1)*(x2 - x1)) + ((y1-y2)*(y1-y2))));
        thickness_textField.setText(""+thickness);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        imageView1.fitHeightProperty().bind(workSpace.heightProperty());
        imageView1.setCursor(Cursor.CROSSHAIR); //Change cursor to crosshair
        imageView2.setCursor(Cursor.CROSSHAIR); //Change cursor to crosshair
        
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
        

        
        imageView2.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                
                if(point.getSelectedToggle().equals(point1)){
                    x1 = (int) event.getX();
                    y1 = (int) event.getY();
                    x1_textField.setText(""+x1);
                    y1_textField.setText(""+y1);
                     
                    Circle circle = new Circle();
                    
                    circle.setCenterX(x1);
                    circle.setCenterY(y1);
                    circle.setFill(rgb(255,0,0));
                    //circle.setStroke(rgb(255,0,0));
                    //circle.setStrokeWidth(6);
                    circle.setRadius(2.0f);
                    workSpace.getChildren().add(circle);
                    workSpace1.getChildren().add(circle);
                    
                
                }else if(point.getSelectedToggle().equals(point2)){
                    x2 = (int) event.getX();
                    y2 = (int) event.getY();
                    x2_textField.setText(""+x2);
                    y2_textField.setText(""+y2);
                    
                    //Drawing cricle for point 2 goes here
                    Circle circle1 = new Circle();
                    circle1.setCenterX(x2);
                    circle1.setCenterY(y2);
                    circle1.setFill(rgb(0,255,0));
                    //circle1.setStroke(rgb(0,255,0));
                    //circle1.setStrokeWidth(6);
                    circle1.setRadius(2.0f);
                    workSpace.getChildren().add(circle1);
                    workSpace1.getChildren().add(circle1);
                    
                    //Drawing line between two points goes here
                    Line line = new Line();
                    line.setStartX(x1);
                    line.setStartY(y1);
                    line.setEndX(x2);
                    line.setEndY(y2);
                    line.setFill(rgb(0,0,255));
                    line.setStroke(rgb(0,0,255));
                    line.setStrokeWidth(1);
                    workSpace1.getChildren().add(line);
                }

            }
        });
        
        clear.setOnAction(new EventHandler<ActionEvent>() {//clearing all the components goes here
            @Override
            public void handle(ActionEvent e) {
                x1_textField.clear();
                y1_textField.clear();
                x2_textField.clear();
                y2_textField.clear();
                thickness_textField.clear();
                ObservableList tempList = workSpace1.getChildren();
                int size = tempList.size();
                for (int i = 1; i <= (size-1); i++) {
                    tempList.remove(1);
                }
            }
        });
    }    
    

    
}
