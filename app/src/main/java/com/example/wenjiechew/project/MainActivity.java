package com.example.wenjiechew.project;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    private String TAG = "ListView";

    private DBAccess dbAccess;
    RecyclerView recyclerView;
    RecyclerListAdapter recyclerListAdapter;

    private List<Places> placesList;
    private Places places = new Places();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbAccess = DBAccess.getInstance(this);



        //Recycler View Initialisation
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FillRVList();



        final LayoutInflater inflater = (LayoutInflater) getLayoutInflater();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"add fab button");

                //Inflating custom layout
                View customView = inflater.inflate(R.layout.custom_additinenarylist_dialog_layout, null);

                //Define datepicker and listName
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
                        places.set_date(datePickerText.getText().toString());

                        dbAccess.openWrite();
                        dbAccess.Insert(dbAccess.TABLENAME_PLACE, places);
                        dbAccess.close();

                        FillRVList();



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
    protected void onResume() {
        super.onResume();

        FillRVList();
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


    private void FillRVList(){
        Log.d(TAG, "Filling list");

        dbAccess.openRead();
        placesList = places.getAllPlaces(dbAccess);
        dbAccess.close();

        recyclerListAdapter = new RecyclerListAdapter(placesList);
        recyclerView.setAdapter(recyclerListAdapter);
    }
}
