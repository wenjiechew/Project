package com.example.wenjiechew.project;

/**
 * Created by WenJieChew on 10/6/2016.
 */
public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
