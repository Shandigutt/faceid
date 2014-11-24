/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package faceid;

import java.sql.*;
import javax.swing.*;

/**
 *
 * @author user
 */
public class JavaConnect {
    Connection conn=null;
    public static Connection ConnecrDb(){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn=DriverManager.getConnection("jdbc:sqlite:C:\\Users\\admtcvrd\\Documents\\NetBeansProjects\\FaceID\\1.sqlite");
            JOptionPane.showMessageDialog(null,"connection established");
            return conn;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
            return null;
        }
        
    }
    public static void updateDatabase(String age, String gender, String featurePoint, String Layer_number, String distance_mearurement){
        
    }
    
}
