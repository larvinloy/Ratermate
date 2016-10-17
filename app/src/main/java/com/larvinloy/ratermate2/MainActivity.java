package com.larvinloy.ratermate2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    addCategory addCategory;

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

        addCategory.addCategory(text);

        TextView myAwesomeTextView = (TextView)findViewById(R.id.textDisplayCategory);

        myAwesomeTextView.setText(text);

    }

}
