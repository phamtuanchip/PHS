/*
 * UserFormat.java
 *
 * Created on April 18, 2006, 3:28 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package phs_project;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Administrator
 */
public class UserFormat {

    /** Creates a new instance of UserFormat */
    public UserFormat() {
    }

    public String getFormat(Date date, String type) {
        String result = "";
        Format fm;
        if (type.equals("ngay")) {
            fm = new SimpleDateFormat("MM/dd/yy");
            result = fm.format(date);
        }
        if (type.equals("ngaygio")) {
            fm = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
            result = fm.format(date);
        }
        if (type.equals("gio")) {
            fm = new SimpleDateFormat("HH:mm:ss");
            result = fm.format(date);
        }
        if (type.equals("thongthuong")) {
            fm = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            result = fm.format(date);
        }
        return result;
    }
}
