
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
import java.io.InputStream;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.FillTransition;
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
    
    private static int fileCount = 0;
    private ObservableList filesCollectin;
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
    private TextField txt_field_featurePoint;
   
    public void initialize(URL url, ResourceBundle rb) {
        //image1.fitHeightProperty().bind(workSpace.heightProperty());
        
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
          
        //colored the point when select list items
        fileList.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<String>() {
                public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) {
                        txt_field_featurePoint.setEditable(false);
                        txt_field_featurePoint.setText(new_val);
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

                            workSpace3.getChildren().add(circle3);
                            workSpace4.getChildren().add(circle4);
                        }           
            }
        }); 
//        fileList.setOnMouseClicked(new EventHandler<MouseEvent>(){
//
//          @Override
//          public void handle(MouseEvent arg0) {
//              
//                txt_field_featurePoint.setText("Selected: " +
//                fileList.getSelectionModel().getSelectedItems());
//                    
//                for(int j=0;j<20;j++){
//                if(fileList.getSelectionModel().getSelectedIndex() == 0){	
//                    x3=236;
//                    y3=46;
//                    x4=280;
//                    y4=53; 
//		}
//                if(fileList.getSelectionModel().getSelectedIndex() == 1){	
//                    x3=250;
//                    y3=210;
//                    x4=450;
//                    y4=212; 
//		}
//                else{
//                    ObservableList tempList = workSpace.getChildren();
//                    int size = tempList.size();
//                        for (int i = 1; i <= size-1; i++) {
//                            tempList.remove(1);
//                        }
//                    ObservableList tempList1 = workSpace1.getChildren();
//                    int size1 = tempList1.size();
//                        for (int i = 1; i <= size-1; i++) {
//                            tempList1.remove(1);
//                        }
//                }
//                    circle3.setCenterX(x3);
//                    circle3.setCenterY(y3);
//                    circle3.setFill(rgb(255,0,0));
//                    circle3.setRadius(5.0f);
//                    circle4.setCenterX(x4);
//                    circle4.setCenterY(y4);
//                    circle4.setFill(rgb(255,0,0));
//                    circle4.setRadius(5.0f);
//                    
//                    workSpace.getChildren().add(circle3);
//                    workSpace1.getChildren().add(circle4);
//                }
//          }
//      }); 
    }
    }    
     
    

