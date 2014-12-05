
package faceid;
import com.sun.javafx.Utils;
import com.sun.javaws.Main;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FillTransition;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.R;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import static javafx.scene.paint.Color.color;
import static javafx.scene.paint.Color.rgb;
import javafx.scene.shape.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import sun.rmi.runtime.Log;
/**
 *
 * @author Anjali
 */
public class FXMLSecondPageController extends JFrame implements Initializable {
    
    private int x3 =0 ,x4 = 0,y3 = 0,y4 = 0;
    @FXML
    public ListView<String> fileList;
    @FXML
    private AnchorPane workSpace3;
    @FXML
    private AnchorPane workSpace4;
    @FXML
    private ImageView image1;
    @FXML
    private ImageView image2;
    @FXML
    private TextField txt_field;
    @FXML
    private Button btn_saveFeaturePoint;
    
    public void initialize(URL url, ResourceBundle rb) {
              
        //imageView.
        File file1 = new File("C:\\Users\\admtcvrd\\Documents\\NetBeansProjects\\FaceID\\src\\Images\\FrontSide.jpg");
        File file2 = new File("C:\\Users\\admtcvrd\\Documents\\NetBeansProjects\\FaceID\\src\\Images\\side.jpg");
        
        Image img1 = new Image(file1.toURI().toString());
        Image img2 = new Image(file2.toURI().toString());
            image1.setImage(img1); 
            image2.setImage(img2);
            
        //create circle1 object     
        Circle circle3 = new Circle(); 
        //create circle2 object
        Circle circle4 = new Circle(); 
        
         //creating listView
        ObservableList<String> featurePointList = FXCollections.observableArrayList(
            "Feature Point",
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
        ListView<String> listView = new ListView<String>(featurePointList);
        listView.getSelectionModel().getSelectedItem();
        
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
        
            @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//            System.out.println("ListView selection changed from oldValue = "+ oldValue + " to newValue = " + newValue);
        }
        });        
        fileList.setItems(featurePointList);
         
        //colored the point when select list items
        fileList.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<String>() {
                public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) {
                        txt_field.setEditable(false);
                        txt_field.setText(new_val);
                        for(int j=0;j<20;j++){
                            if(new_val == "M1: Bregma (BR)"){
                                x3=245;
                                y3=48;
                                x4=280;
                                y4=53;
                            }
                            else if(new_val == "M2: Glabella (GL)"){
                                x3=254;
                                y3=215;
                                x4=450;
                                y4=210;
                            }
                            else if(new_val == "M3: Nasion (NA)"){
                                x3=254;
                                y3=237;
                            }
                            else if(new_val == "M4: Nsdodpinslr (NAS)"){
                                x3=249; 
                                y3=345; 
                                x4=433;
                                y4=333;
                            }
                            else if(new_val == "M5: Prosthion (PR)"){
                                x3=251;
                                y3=390;
                                x4=460;
                                y4=370;
                            }
                            else if(new_val == "M6: Infradentale (ID)"){
                                x3=247;
                                y3=440;
                                x4=440;
                                y4=415;
                            }
                            else if(new_val == "M7: Pogonion (PG)"){
                                x3=247; 
                                y3=480; 
                                x4=437;
                                y4=450;
                            }
                            else if(new_val == "M8: Gnathion (GN)"){
                                x3=247;
                                y3=500;
                                x4=428;
                                y4=473;
                            }
                            else if(new_val == "M13: Opisthocranium (OPC)"){
                                circle3.isDisabled();
                                x4=93;
                                y4=280;
                            }
                            else if(new_val == "B1 : Staphanion (ST)"){
                                x3=114; 
                                y3=145; 
                                x4=330;
                                y4=148;
                            }
                            else if(new_val == "B2 : Euryon (EU)"){
                                x3=88;
                                y3=182;
                                x4=233;
                                y4=179;
                            }
                            else if(new_val == "B3 : Frontotemporale (FT)"){
                                x3=140;
                                y3=208;
                                x4=393;
                                y4=205;
                            }
                            else if(new_val == "B4 : Supraorbitale (SOR)"){
                                x3=182; 
                                y3=224; 
                                x4=430;
                                y4=218;
                            }
                            else if(new_val == "B5 : Maxillo-frontale (MF)"){
                                x3=225;
                                y3=255;
                                x4=421;
                                y4=245;
                            }
                            else if(new_val == "B6 : Ectoconchion (EC)"){
                                x3=142;
                                y3=282;
                                x4=400;
                                y4=276;
                            }
                            else if(new_val == "B7 : Orbitale (ORB)"){
                                x3=175; 
                                y3=305; 
                                x4=421;
                                y4=292;
                            }
                            else if(new_val == "B8 : Nasal (NS)"){
                                x3=221;
                                y3=335;
                                x4=432;
                                y4=320;
                            }
                            else if(new_val == "B9 : Zygonion (ZG)"){
                                x3=101;
                                y3=318;
                                x4=322;
                                y4=302;
                            }
                            else if(new_val == "B10: Conlylion laterale (CDL)"){
                                x3=105; 
                                y3=350; 
                                x4=288;
                                y4=336;
                            }
                            else if(new_val == "B11: Zygomaxilare (ZM)"){
                                x3=134;
                                y3=359;
                                x4=390;
                                y4=344;
                            }
                            else if(new_val == "B12: Lateral infradentale (LID)"){
                                x3=218;
                                y3=443;
                            }
                            else{
                                ObservableList tempList = workSpace3.getChildren();
                                int size = tempList.size();
                                    for (int i = 1; i <= size-1; i++) {
                                        tempList.remove(1);
                                    }
                                ObservableList tempList1 = workSpace4.getChildren();
                                int size1 = tempList1.size();
                                    for (int i = 1; i <= size-1; i++) {
                                        tempList1.remove(1);
                                    }
                            }
                            circle3.setCenterX(x3);
                            circle3.setCenterY(y3);
                            circle3.setFill(rgb(255,0,0));
                            circle3.setRadius(5.0f);
                            
                            circle4.setCenterX(x4);
                            circle4.setCenterY(y4);
                            circle4.setFill(rgb(255,0,0));
                            circle4.setRadius(5.0f);

//                            workSpace3.getChildren().add(circle3);
//                            workSpace4.getChildren().add(circle4);
                        }           
            }
        }); 
             
//        //clear button on action
        btn_saveFeaturePoint.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String featurePoint = txt_field.getText();
                System.out.println(featurePoint);
                System.out.println("txt field is"+featurePoint);
//                FXMLDocumentController controller = new FXMLDocumentController();
//                controller.txt_field_featurePoint.setText("adfasfasf");  
                
//                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));     
//                Parent root = null;          
//                try {
//                    txt_field.setText(featurePoint);
//                    root = (Parent)fxmlLoader.load();
//                } catch (IOException ex) {
//                    Logger.getLogger(FXMLSecondPageController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                FXMLDocumentController documentController = fxmlLoader.<FXMLDocumentController>getController();
////                String featurePoint = null;
//                documentController.setUser(featurePoint);
//                Scene scene = new Scene(root); 
//                Stage stage = new Stage();
//                stage.setScene(scene); 
//                stage.show();  
            }
        });
    }
    }    