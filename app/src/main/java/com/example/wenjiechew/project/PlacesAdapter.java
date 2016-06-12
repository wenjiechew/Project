package com.example.wenjiechew.project;

/**
 * Created by WenJieChew on 12/6/2016.
 */

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.woxthebox.draglistview.DragItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class PlacesAdapter extends DragItemAdapter<Places, PlacesAdapter.ViewHolder > {
    String TAG = "Place Adapter";

    private int mLayoutId;
    private int mGrabHandleId;
    private List<Places> mplaces;

    public PlacesAdapter(List<Places> nPlaces, int layoutId, int grabHandleId, boolean dragOnLongPress){
        super(dragOnLongPress);
        mLayoutId = layoutId;
        mGrabHandleId = grabHandleId;
        setHasStableIds(true);
        mplaces = nPlaces;
        Log.d(TAG, nPlaces.get(0).get_placeName());
        Log.d(TAG, mplaces.get(0).get_placeName());
        setItemList(mplaces);
        //setItemList(nPlaces);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        super.onBindViewHolder(holder, position);

        holder.placeNameText.setText(mItemList.get(position).get_placeName());
        Log.d(TAG, "onBindView " + position + " ---- " + mItemList.get(position).get_placeName());
        Log.d(TAG, "onBindView " + position);
        Log.d(TAG, "onBindView " + position + " ---- " + mItemList.get(position).get_date());
        holder.listDateText.setText(mItemList.get(position).get_date());
    }


    @Override
    public long getItemId(int position) { return mplaces.get(position).get_id(); }

    public class ViewHolder extends DragItemAdapter<Places, PlacesAdapter.ViewHolder>.ViewHolder {
        public TextView placeNameText;
        public TextView listDateText;

        public ViewHolder(final View itemView){
            super(itemView, mGrabHandleId);
            placeNameText = (TextView) itemView.findViewById(R.id.placeNameText);
            listDateText = (TextView) itemView.findViewById(R.id.dateText);
        }

        @Override
        public void onItemClicked(View view){
            Toast.makeText(view.getContext(), "Item Clicked", Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onItemLongClicked(View view){
            Toast.makeText(view.getContext(), "Item Long Clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}
