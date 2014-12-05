/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package faceid;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Tharaka
 */
public class FaceID extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Tissue Thickness Extractor");
//        stage.setFullScreen(true);  
        stage.show();  
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.loadLibrary("opencv_java249");//Load opencv library
        launch(args);    
    }   
}
