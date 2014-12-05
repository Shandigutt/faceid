/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package faceid;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author admtcvrd
 */
public class UserData {
        
        private StringProperty ageGroup;
        private StringProperty gender;
        private StringProperty featurePoint;
        private StringProperty cTScanLayerNumber;
        private StringProperty distance;

        UserData(String ageGroup, String gender, String featurePoint, String cTScanLayerNumber, String distance ) {
            this.ageGroup = new SimpleStringProperty(ageGroup);
            this.gender = new SimpleStringProperty(gender);
            this.featurePoint = new SimpleStringProperty(featurePoint);
            this.cTScanLayerNumber = new SimpleStringProperty(cTScanLayerNumber);
            this.distance = new SimpleStringProperty(distance);
            //System.out.println(ageProperty()+" : "+genderProperty()+" : "+featuerPointProperty()+" : "+cTScanProperty()+" : "+distanceProperty());
        }
        public StringProperty ageGroupProperty() {
            return ageGroup;
        }
        public StringProperty genderProperty() {
            return gender;
        }
        public StringProperty featurePointProperty() {
            return featurePoint;
        }
        public StringProperty cTScanLayerNumberProperty() {
            return cTScanLayerNumber;
        }
        public StringProperty distanceProperty() {
            return distance;
        }
    }
