package com.example.garbagecollector;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.content.Context.MODE_PRIVATE;

// TODO: multiple line fetching

public class FileHolder {

    static void writeLoginDataToFile(String username, String password, String socket, Context context) {
        try {

            FileOutputStream fileHolder = context.openFileOutput("LoginDetails.txt", MODE_PRIVATE);
            fileHolder.write(socket.getBytes());
            fileHolder.close();

            //Toast.makeText(context, "Text saved", Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String fetchLoginDataFromFile(Context context) {

        StringBuffer stringBuffer = new StringBuffer();

        try {
            FileInputStream fileHolder = context.openFileInput("LoginDetails.txt");
            InputStreamReader reader = new InputStreamReader(fileHolder);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuffer.toString();
    }
}
