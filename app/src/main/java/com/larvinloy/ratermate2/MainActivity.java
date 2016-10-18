package com.larvinloy.ratermate2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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

        //** Step 1 - Take values from Category and Add to Array **//
        //Take text from Category 1 field and convert to String variable
        EditText et = (EditText) findViewById(R.id.categoryText1);
        String text1= et.getEditableText().toString();

        //Take text from Category 2 field and convert to String variable
        EditText et2 = (EditText) findViewById(R.id.categoryText2);
        String text2= et2.getEditableText().toString();

        //Add to Categories ArrayList
        add.addCategory(text1);
        add.addCategory(text2);

        //Execute Insert function
        new EndpointsAsyncTask(this).execute();

    }

    //Add Button Adds Category to Categories ArrayList
    public void buttonCategory(View v) {




        //Changes TextView to display Value
//        TextView myAwesomeTextView = (TextView)findViewById(R.id.textDisplayCategory);
//
//        myAwesomeTextView.setText(text);

    }

    public static ArrayList<String> getCategories(){

        return add.getCategories();
    }

}
