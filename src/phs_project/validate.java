/*
 * validate.java
 *
 * Created on February 27, 2006, 7:56 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package phs_project;

/**
 *
 * @author Administrator
 */
public class validate extends javax.swing.JFrame {

    /** Creates a new instance of validate */
    public validate() {
    }

    public String getString(String str) {
        if ((str.length() == 0) || (str == "")) {
            return null;
        } else {
            return str;
        }
    }

    // Validate price
    public int getPrice(String str) {
        if (str.length() == 0) {
            return 0;
        } else {
            return Integer.parseInt(str);
        }
    }
}
