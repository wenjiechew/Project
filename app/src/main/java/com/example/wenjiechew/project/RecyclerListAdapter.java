package com.example.wenjiechew.project;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


/**
 * Created by WenJieChew on 10/6/2016.
 */
public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.PlacesViewHolder>{

    private List<Places> places;

    public RecyclerListAdapter(List<Places> places){
        this.places = places;
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public static class PlacesViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView placeName;
        TextView date;

        PlacesViewHolder(View itemView){
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.placeCardView);
            placeName = (TextView)itemView.findViewById(R.id.placeNameText);
            date = (TextView)itemView.findViewById(R.id.dateText);
        }
    }

    @Override
    public PlacesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_cardview_layout, parent,false);
        PlacesViewHolder placesViewHolder = new PlacesViewHolder(view);
        return placesViewHolder;
    }

    @Override
    public void onBindViewHolder(PlacesViewHolder holder, int position) {
        Places item = places.get(position);
        holder.placeName.setText(item.get_placeName());
        holder.date.setText(item.get_date());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }


}
