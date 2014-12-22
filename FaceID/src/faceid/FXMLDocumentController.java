/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package faceid;

import com.sun.javafx.Utils;
import java.awt.Color;
import static java.awt.Color.black;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FillTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.R;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.RED;
import static javafx.scene.paint.Color.color;
import static javafx.scene.paint.Color.rgb;
import static javafx.scene.paint.Color.rgb;
import static javafx.scene.paint.Color.rgb;
import static javafx.scene.paint.Color.rgb;
import static javafx.scene.paint.Color.rgb;
import static javafx.scene.paint.Color.rgb;
import static javafx.scene.paint.Color.rgb;
import static javafx.scene.paint.Color.rgb;
import static javafx.scene.paint.Color.rgb;
import static javafx.scene.paint.Color.rgb;
import static javafx.scene.paint.Color.rgb;
import static javafx.scene.paint.Color.rgb;
import javafx.scene.shape.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import static org.opencv.core.Core.FILLED;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.CHAIN_APPROX_SIMPLE;
import static org.opencv.imgproc.Imgproc.CV_CONTOURS_MATCH_I2;
import static org.opencv.imgproc.Imgproc.THRESH_BINARY;
import static org.opencv.imgproc.Imgproc.matchShapes;
import static org.opencv.imgproc.Imgproc.threshold;
import sun.rmi.runtime.Log;

/**
 *
 * @author Tharaka
 */
public class FXMLDocumentController extends JFrame implements Initializable {
    
    //..........................................................................
    @FXML
    private Button open;
    @FXML
    private Button nextWindow;
    @FXML
    private Button databaseWindow;
    @FXML
    private Label locationPath;
    @FXML
    private ListView<String> fileList;
    @FXML
    private AnchorPane workSpace;
    @FXML
    private ScrollPane scrollPaneImageView1;
    @FXML
    private ImageView imageView1;
    @FXML
    private Slider workSpaceSlider;
    @FXML
    private AnchorPane workSpace1;
    @FXML
    private ScrollPane scrollPaneImageView2;
    @FXML
    private Slider workSpace1Slider;
    @FXML
    private ImageView imageView2;
    @FXML
    private ComboBox<String> comboBox_AgeGroup;
    @FXML
    private ComboBox<String> comboBox_gender;
    @FXML
    private ComboBox<String> comboBox_featurePoint;
    @FXML
    public TextField txt_field_LayerNum;
    @FXML
    private TextField thickness_textField;
    @FXML
    private ToggleGroup point;
    @FXML
    private RadioButton point1;
    @FXML
    private RadioButton point2;
    @FXML
    private TextField x1_textField;
    @FXML
    private TextField x2_textField;
    @FXML
    private TextField y1_textField;
    @FXML
    private TextField y2_textField;
    @FXML
    private Button calculate;
    @FXML
    private Button clear;
    @FXML
    private Button btn_save;
    @FXML
    private Slider zoomSlider; 
    @FXML
    private Slider zoomSlider1; 
    private TextField txt_field_featurePoint;
    
    //Define Variables..........................................................
    
    private String location;
    private static int fileCount = 0;
    private ObservableList filesCollectin;
    private int x1 =0 ,x2 = 0,y1 = 0,y2 = 0;
    double thickness;
    private int radius;   
    private Line line;
    Boolean autoFix = true;
    public String featurePoint;
    public ObservableList<UserData> data; 
    ScrollPane scrollPane;
    public double thresh;
    public double thresh1;
    private double image1Scale = 1;
    private double image1Height = 512;
    private double image2Scale = 1;
    private double image2Height = 512;
    private boolean first = true;
    
    Mat fillImage;
    Mat cannyImage;
    Mat cannyImage2;
    MatOfByte matOfByte;
    byte[] byteArray;
    BufferedImage bufferedImage = null;
    
    Connection conn=null;
    ResultSet rs=null;
    PreparedStatement pst=null;
    
    //..........................................................................
   
    
    @FXML
    private void openDirectory(ActionEvent event) { // when the open directory button is pressed
       
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Please open the location that contains the scanned images");
        File selectedDirectory = directoryChooser.showDialog(open.getScene().getWindow());
        if(selectedDirectory == null){
            location = "No location selected";
            //locationPath.setText(location);
        }else{
            fileCount = 0;
            filesCollectin = FXCollections.observableArrayList();
            for(File x : selectedDirectory.listFiles()){
                if(x.isFile() & accept(x)){
                    filesCollectin.add(x.getName());
                    fileCount++;
                }
            }
            location = selectedDirectory.getAbsolutePath();
            locationPath.setText(location);
            fileList.setItems(filesCollectin);
        } 
    }
    //file flitter
    public String extension(File x) {
        String filename = x.getName();
            int IndexFile = filename.lastIndexOf(".");
            if(IndexFile > 0 && IndexFile<filename.length()-1){
                return filename.substring(IndexFile+1);
            }else {
            return "";
            }
  }
    public boolean accept(File x){
        if(x.isDirectory()){
            return true;
        }
        if(extension(x).equalsIgnoreCase("jpg") | extension(x).equalsIgnoreCase("png")){
            return true;
        }else
            return false;
    } 
    
    //..........................................................................
    
    //calculate distance
    @FXML
    private void calculate(ActionEvent event) {
        
        thickness = ((double)Math.sqrt((((x2 - x1)*(x2 - x1)) + ((y1-y2)*(y1-y2))))) / image2Scale;
        thickness_textField.setEditable(false);
        thickness_textField.setText(""+thickness);
    }
    
    //..........................................................................
    
    //Initialize 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // connectToDatabase()
        conn=JavaConnect.ConnecrDb();
        imageView1.setCursor(Cursor.CROSSHAIR); //Change cursor to crosshair
        imageView2.setCursor(Cursor.CROSSHAIR); //Change cursor to crosshair
        txt_field_LayerNum.setEditable(false);  //Set Layer number textField editable false
        
        workSpaceSlider.setMin(35);
        workSpaceSlider.setMax(100);
        workSpaceSlider.setValue(50);
        workSpaceSlider.setShowTickLabels(true);
        workSpaceSlider.setShowTickMarks(true);
        workSpaceSlider.setMajorTickUnit(50);
        workSpaceSlider.setMinorTickCount(5);
        workSpaceSlider.setBlockIncrement(10);
        
        workSpace1Slider.setMin(35);
        workSpace1Slider.setMax(100);
        workSpace1Slider.setValue(50);
        workSpace1Slider.setShowTickLabels(true);
        workSpace1Slider.setShowTickMarks(true);
        workSpace1Slider.setMajorTickUnit(50);
        workSpace1Slider.setMinorTickCount(5);
        workSpace1Slider.setBlockIncrement(10);
        
        zoomSlider.setMin(0);
        zoomSlider.setMax(1100);
        zoomSlider.setValue(50);
        zoomSlider.setShowTickLabels(true);
        zoomSlider.setShowTickMarks(true);
        zoomSlider.setMajorTickUnit(50);
        zoomSlider.setMinorTickCount(5);
        zoomSlider.setBlockIncrement(10);
        
        zoomSlider1.setMin(0);
        zoomSlider1.setMax(1100);
        zoomSlider1.setValue(50);
        zoomSlider1.setShowTickLabels(true);
        zoomSlider1.setShowTickMarks(true);
        zoomSlider1.setMajorTickUnit(50);
        zoomSlider1.setMinorTickCount(5);
        zoomSlider1.setBlockIncrement(10);

        zoomSlider.valueProperty().addListener(new ChangeListener<Number>() {
          @Override
          public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
              applyCanny((double) zoomSlider.getValue());
              String.valueOf((double) zoomSlider.getValue());
          }
          public void applyCanny(double tr) {
                thresh = tr;
                System.out.println("thresh is" + thresh);
                applyCannyToImage();
          }
      });
        zoomSlider1.valueProperty().addListener(new ChangeListener<Number>() {
          @Override
          public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
              applyCanny((double) zoomSlider1.getValue());
              String.valueOf((double) zoomSlider1.getValue());
          }
          public void applyCanny(double tr1) {
                thresh1 = tr1;
                System.out.println("thresh1 is" + thresh1);
                applyCannyToImage();
          }
      });
        
        workSpaceSlider.valueProperty().addListener(new ChangeListener<Number>() {
          @Override
          public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
              image1Scale = 1 + (((double) workSpaceSlider.getValue() - 50) / 25);
              imageView1.setFitHeight(image1Height * image1Scale);
//              System.out.println(""+(1 + (((double) workSpaceSlider.getValue() - 50) / 25)));
//              System.out.println(workSpace.getHeight() + " : " + workSpace.getWidth());
//              System.out.println(""+imageView1.getBoundsInLocal());
//              System.out.println(""+imageView1.getBoundsInParent());
          }
        });
        
        workSpace1Slider.valueProperty().addListener(new ChangeListener<Number>() {
          @Override
          public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
              image2Scale = 1 + (((double) workSpace1Slider.getValue() - 50) / 25);
              imageView2.setFitHeight(image2Height * image2Scale);
          }
        });
        
        imageView1.setCursor(Cursor.CROSSHAIR); //Change cursor to crosshair
        imageView2.setCursor(Cursor.CROSSHAIR); //Change cursor to crosshair
        
        txt_field_LayerNum.setEditable(false);  //Set Layer number textField editable false
        //txt_field_featurePoint.setEditable(false);  //Set featurePoint textField editable false
                
        comboBox_AgeGroup.setItems(ageGroupList);
        comboBox_gender.setItems(genderList);
        comboBox_featurePoint.setItems(featurePointList);
        
        //imageView
        
        fileList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
             
                public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) {
                    
                    applyCannyToImage();
            }
                
        });
        
         //workSpace1
        workSpace1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {                
                if(point.getSelectedToggle().equals(point1)){                   
                    x1 = (int) event.getX();
                    y1 = (int) event.getY();
                    x1_textField.setText(""+x1);
                    y1_textField.setText(""+y1);
                     
                    Circle circle = new Circle();   //create circle object
                    
                    circle.setCenterX(x1);
                    circle.setCenterY(y1);
                    circle.setFill(rgb(255,0,0));
                    circle.setStroke(rgb(255,0,0));
                    circle.setRadius(2.0f);
                    circle.setId("p1");
                    workSpace1.getChildren().add(circle);   //add circle to workspace
                    
                }else if(point.getSelectedToggle().equals(point2)){
                    x2 = (int) event.getX();
                    y2 = (int) event.getY();
                    x2_textField.setText(""+x2);
                    y2_textField.setText(""+y2);
                    
                    Circle circle1 = new Circle();
                    
                    circle1.setCenterX(x2);
                    circle1.setCenterY(y2);
                    circle1.setFill(rgb(0,255,0));
                    circle1.setRadius(2.0f);
                    workSpace1.getChildren().add(circle1);
                    
                    Line line = new Line();
                    line.setStartX(x1);
                    line.setStartY(y1);
                    line.setEndX(x2);
                    line.setEndY(y2);
                    
                    line.setFill(rgb(0,0,255));
                    line.setStroke(rgb(0,255,255));
                    line.setStrokeWidth(1);
                    workSpace1.getChildren().add(line);
                }          
            }
        });
        
        //clear button on action
        clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                x1_textField.clear();
                y1_textField.clear();
                x2_textField.clear();
                y2_textField.clear();
                thickness_textField.clear();
                txt_field_LayerNum.clear();
                //workSpace1.getChildren().remove(1, 4);
                ObservableList tempList = workSpace1.getChildren();
                int size = tempList.size();
                for (int i = 1; i <= size-1; i++) {
                    tempList.remove(1);
                }  
            }
        });
        
        //Setting an action for the Submit button
        btn_save.setOnAction(new EventHandler<ActionEvent>() {
        @Override
            public void handle(ActionEvent e) {
                try{
//                    if(comboBox_AgeGroup.getValue() != null && comboBox_gender.getValue() != null && comboBox_featurePoint.getValue() != null && txt_field_LayerNum.getText() != null && thickness_textField.getText() != null){
                    String sql="Insert into TissueThicknessData(AgeGroup, Gender, FeaturePoint, CTScanLayerNumber, Distance) values (?, ?, ?, ?, ?)";
                    pst=conn.prepareStatement(sql);
                    
                    pst.setString(1,comboBox_AgeGroup.getValue());
                    pst.setString(2,comboBox_gender.getValue());
                    pst.setString(3,comboBox_featurePoint.getValue());
                    pst.setString(4,txt_field_LayerNum.getText());
                    pst.setString(5,thickness_textField.getText());
                    pst.execute();
                    //data.add(new UserData(txt_field_LayerNum.getText(), thickness_textField.getText()));
                    JOptionPane.showMessageDialog(null, "saved"); 
//                    }
//                    else{
//                        String message = "\"An Error\"\n" + "Fill all the fields";
//                        JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
////                    }
                }
                catch(Exception e1){
                    JOptionPane.showMessageDialog(null,e1);
                }
             }
         });
 
        //open next Window
        nextWindow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e1) {
                
                //open new window
                try {
                    Parent root1 = FXMLLoader.load(getClass().getResource("FXMLSecondPage.fxml"));
                    Scene scene = new Scene(root1);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle("Feature Point Selector");
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
        });
        
        //open database window
        databaseWindow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e2) {
                try {
                    Parent root2 = FXMLLoader.load(getClass().getResource("FXMLDatabase.fxml"));
                    Scene scene = new Scene(root2);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle("Tissue Thickness Database");
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }  
            }
        });   
    }
   
    //..........................................................................
  
    public void applyCannyToImage(){
        String new_val = fileList.getSelectionModel().getSelectedItem();
        System.out.println(new_val);
        
        fillImage  = Highgui.imread(location + "\\" + new_val);
        System.out.println("fillImage" + fillImage.channels()+"channels"+fillImage.cols()+"columns and "+fillImage.rows()+"rows");
        System.out.println(location + "\\" + new_val);
                    
        imageView1.setImage(new Image("file:" + location + "\\" + new_val));
        
        System.out.println(new_val);
        
                
        //canny convertor Image
        cannyImage = new Mat();
        cannyImage2 = new Mat();
        matOfByte = new MatOfByte();
        Imgproc.Canny(fillImage, cannyImage, thresh1, thresh, 3, true);//Applying canny algorithm 
        System.out.println("thresh for canny is " +thresh);
        Highgui.imencode(".jpg", cannyImage, matOfByte);//encoding Mat image to MatOfByte
        byteArray = matOfByte.toArray();
                    
            try {
                InputStream in = new ByteArrayInputStream(byteArray);
                bufferedImage = ImageIO.read(in);
                //setFix();
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        
        //......................................................................  
        
//        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();  
//        threshold(fillImage, fillImage, 100, 255, THRESH_BINARY);
//        //Improc.findContours(cannyImage, storage, &contours, sizeof(CvContour), CV_RETR_EXTERNAL);
//        //drawContours(cannyImage, List<MatOfPoint> contours, int contourIdx, Scalar color, int thickness, int lineType, Mat hierarchy, int maxLevel, Point offset);
//        Imgproc.findContours(fillImage, contours, new Mat(),Imgproc.RETR_LIST,Imgproc.CHAIN_APPROX_SIMPLE);
//        for (int x = 0; x < contours.size(); x++) {  
//            double d = Imgproc.contourArea (contours.get(x));  
//            contours.get(x).convertTo(fillImage, CvType.CV_32FC2);  
//            double iContourAreaMin;
//                  
//            if (d > iContourAreaMin) {  
//  
//            Imgproc.approxPolyDP(fillImage, mMOP2f2, 2, true);  
//                      
//            // convert back to MatOfPoint and put it back in the list  
//            mMOP2f2.convertTo(contours.get(x), CvType.CV_32S);  
//                      
//            // draw the contour itself  
//            Imgproc.drawContours(fillImage, contours, x, colorblack, 100);  
//            }  
//        }  
//        //Imgproc.drawContours(fillImage, contours, idx, black, CV_FILLED, 8, hierarchy ); 
//  
//       
//        Double compare = matchShapes(cannyImage, cannyImage2, CV_CONTOURS_MATCH_I2, 0);
//        System.out.println("difference is:" + compare);

        //......................................................................
                
            
        imageView2.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        txt_field_LayerNum.setText(new_val);
        if(first){
            image1Height = imageView1.getBoundsInLocal().getHeight();
            image2Height = imageView2.getBoundsInLocal().getHeight();
            first = false;
        }
    }
    
    //Age group list
    ObservableList<String> ageGroupList = FXCollections.observableArrayList(
            "0-5",
            "5-10",
            "10-15",
            "15-20",
            "20-25",
            "25-30",
            "30-35",
            "35-40",
            "40-45",
            "45-50",
            "50-55",
            "55-60",
            "60-65",
            "65-70",
            "70-75",
            "75-80",
            "80-85",
            "85-90",
            "90-95",
            "95-100"
    );
    //Male / Female list
    ObservableList<String> genderList = FXCollections.observableArrayList(
            "Male",
            "Female"   
    );
    //FeaturePointList 
    ObservableList<String> featurePointList = FXCollections.observableArrayList(
            "M1: Bregma (BR)", 
            "M2: Glabella (GL)", 
            "M3: Nasion (NA)", 
            "M4: Nsdodpinslr (NAS)", 
            "M5: Prosthion (PR)", 
            "M6: Infradentale (ID)", 
            "M7: Pogonion (PG)",
            "M8: Gnathion (GN)",
            "M9: Orale (OR)",
            "M10: Staphylion (STA)",
            "M11: Basion (BA)",
            "M12: Opistion (OPS)",
            "M13: Opisthocranium (OPC)",
            "B1 : Staphanion (ST)", 
            "B2 : Euryon (EU)" ,
            "B3 : Frontotemporale (FT)",
            "B4 : Supraorbitale (SOR)" ,
            "B5 : Maxillo-frontale (MF)" ,
            "B6 : Ectoconchion (EC)" ,
            "B7 : Orbitale (ORB)" ,
            "B8 : Nasal (NS)" ,
            "B9 : Zygonion (ZG)" ,
            "B10: Conlylion laterale (CDL)" ,
            "B11: Zygomaxilare (ZM)" ,
            "B12: Lateral infradentale (LID)" ,
            "B13: Endomolare (ENM)" ,
            "B14:Bolton (BO)" ,
            "B15: Gonion (GO)" ,
            "B16: Coronion (CO)" ,
            "B17: Condylion superior (CS)"   
    );
    
    //..........................................................................
    
    public void setText(String name){
        txt_field_featurePoint.setText(name);
    }
    void setUser(String featurePoint) {
        this.featurePoint = featurePoint;
        thickness_textField.setText("10");
        System.out.println("feature Point is : " + featurePoint);
        } 
}