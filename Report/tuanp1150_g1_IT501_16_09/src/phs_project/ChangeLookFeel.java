/*
 * ChangeLookFeel.java
 *
 * Created on March 11, 2006, 9:23 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package phs_project;
import java.awt.Component;
import javax.swing.*;
/**
 *
 * @author Administrator
 */
public class ChangeLookFeel{

    private Component hotelForm;
    
    /** Creates a new instance of ChangeLookFeel */
    public ChangeLookFeel() {
    }
   
    public void configureUI() {
        String lafName =  "com.incors.plaf.kunststoff.KunststoffLookAndFeel";                  
        try{
            UIManager.setLookAndFeel(lafName);                     
        } catch (Exception e) {
            System.err.println("Can't set look & feel:" + e);
        }
    }    
}
