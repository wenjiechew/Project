package com.example.wenjiechew.project;

import android.app.FragmentTransaction;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;


import android.support.v4.content.ContextCompat;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.Toolbar;

import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import com.woxthebox.draglistview.DragListView;



public class MainActivity extends AppCompatActivity implements SwipeActionAdapter.SwipeActionListener {
    private String TAG = "ListView";

    private DBAccess dbAccess;
    private Places places = new Places();

    private List<Places> placesList;
    private DragListView mDragListView;
    protected SwipeActionAdapter mAdapter;


    private LayoutInflater inflater;

    PlacesAdapter listAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//
        //Initialise Datebase Access Instance
        dbAccess = DBAccess.getInstance(this);

        //Initialise ListView

        mDragListView = (DragListView) findViewById(R.id.drag_list_view);
        mDragListView.getRecyclerView().setVerticalScrollBarEnabled(true);
        mDragListView.setDragListListener(new DragListView.DragListListenerAdapter() {
            @Override
            public void onItemDragStarted(int position) {
                Toast.makeText(mDragListView.getContext(), "Start - position: " + position, Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onItemDragEnded(int fromPosition, int toPosition) {
                if (fromPosition != toPosition) {
                    Toast.makeText(mDragListView.getContext(), "End - position: " + toPosition, Toast.LENGTH_SHORT).show();
                }
            }
        });
        setupListRecyclerView();

        inflater = getLayoutInflater();

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

    private void setupListRecyclerView() {

        FillPlaceList();

        mDragListView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        listAdapter = new PlacesAdapter(placesList, R.layout.custom_itinenarylist_layout, R.id.holder2, false);
        mDragListView.setAdapter(listAdapter, true);
        mDragListView.setCanDragHorizontally(false);

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(
                this,
                R.layout.row_bg,
                R.id.text,
                listAdapter
        );

        mAdapter = new SwipeActionAdapter(stringArrayAdapter);



       // mDragListView.setCustomDragItem(new MyDragItem(MainActivity.this), R.layout.custom_itinenarylist_layout));
    }

    private void FillPlaceList(){
        Log.d(TAG, "Filling list");

        dbAccess.openRead();
        placesList = places.getAllPlaces(dbAccess);
        dbAccess.close();

        Log.d(TAG, placesList.get(0).get_placeName());

    }





}
