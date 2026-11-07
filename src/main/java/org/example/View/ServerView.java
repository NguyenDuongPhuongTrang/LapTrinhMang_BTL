package org.example.View;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerView {
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    public void showMessage(String message) {
        String timestamp = sdf.format(new Date());
        System.out.println("[" + timestamp + "] " + message);
    }
}
