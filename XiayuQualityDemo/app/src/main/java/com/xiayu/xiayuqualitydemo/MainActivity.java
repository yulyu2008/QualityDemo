package com.xiayu.xiayuqualitydemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public static final String s_aa = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getData("xiayu");
    }

    private void getData(String xiayu) {
        File            file        =new File(xiayu);
        FileInputStream inputStream =null;
        try {
            inputStream =new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            int read = inputStream.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}