package com.example.wenjiechew.project;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private String TAG = "ListView";

    private int year, month, day;

    private String listName;

    private DBAccess dbAccess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbAccess = DBAccess.getInstance(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"add fab button");

                //Inflating custom layout
                LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
                View customView = inflater.inflate(R.layout.custom_additinenarylist_dialog_layout, null);

                //Define datepicker
                final EditText listEditText = (EditText) customView.findViewById(R.id.listEditText);
                final EditText datePickerText = (EditText) customView.findViewById(R.id.txtdate);

               datePickerText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                   @Override
                   public void onFocusChange(View v, boolean hasFocus) {
                       if(hasFocus){
                           DateDialog dialog = new DateDialog(v);
                           FragmentTransaction ft = getFragmentManager().beginTransaction();
                           dialog.show(ft,"DatePicker");
                       }
                   }
               });



                //Build Dialog
                AlertDialog.Builder buider = new AlertDialog.Builder(MainActivity.this);
                buider.setView(customView);
                buider.setTitle("New Itinerary List");
                buider.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Places places = new Places();
                        places.set_placeName(listEditText.getText().toString());

                        //// TODO: 9/6/2016 TO DO THE DATE TO UPLOAD TO DB
                        dbAccess.openWrite();
                        dbAccess.Insert(dbAccess.TABLENAME_PLACE, places);
                        dbAccess.close();


                    }

                });
                buider.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                buider.create().show();




            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
