package phs_project;

import java.util.*;
import java.text.*;
import javax.swing.*;

public class digitalClock implements Runnable {

    Thread clockThread;
    Calendar now;
    Date today;
    DateFormatSymbols dfsLocales;
    SimpleDateFormat sdfFormat;
    SimpleDateFormat sdfFormat2;
    String current_time;
    JTextField txtName;

    public digitalClock(JTextField txtName) {
        this.txtName = txtName;
        dfsLocales = new DateFormatSymbols(Locale.getDefault());
        sdfFormat = new SimpleDateFormat("HH:mm:ss", dfsLocales);
        sdfFormat2 = new SimpleDateFormat("dd/MM/yyyy", dfsLocales);
        //sdfFormat2 = new SimpleDateFormat("MM/dd/yyyy", dfsLocales);
        clockThread = new Thread(this);
        clockThread.start();
    }

    public void run() {
        long sleepTime = 1000;

        while (true) {
            try {
                now = Calendar.getInstance();
                today = new Date(now.getTimeInMillis());
                current_time = " " + sdfFormat2.format(today) + ":" + sdfFormat.format(today);

                txtName.setText(current_time);

                Thread.sleep(sleepTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
