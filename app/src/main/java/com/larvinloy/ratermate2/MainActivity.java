package com.larvinloy.ratermate2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.larvinloy.ratermate2.logic.Paillier;
import com.larvinloy.ratermate2.logic.PublicEncryption;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    static addCategory add = new addCategory();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Test Connection
    public void buttonStart(View v) {
        new EndpointsAsyncTask(this).execute();

    }

    //Add Button Adds Category to Categories ArrayList
    public void buttonCategory(View v) {
        EditText et = (EditText) findViewById(R.id.categoryText);
        String text= et.getEditableText().toString();

        add.addCategory(text);

        //Changes TextView to display Value
//        TextView myAwesomeTextView = (TextView)findViewById(R.id.textDisplayCategory);
//
//        myAwesomeTextView.setText(text);

    }

    public static ArrayList<String> getCategories(){
        return add.getCategories();
    }

}
