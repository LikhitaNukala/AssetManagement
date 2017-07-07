package com.example.android.myscannerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Likhita on 21-06-2017.
 */

public class WifiAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<WifiList> listStorage;

    public WifiAdapter(Context context, List<WifiList> customizedListView) {
        layoutInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listStorage = customizedListView;
    }

    @Override
    public int getCount() {
        return listStorage.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder listViewHolder;
        if(convertView == null){
            listViewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.wifi_name_list, parent, false);

            listViewHolder.textInListView = (TextView)convertView.findViewById(R.id.wifi_time);
            listViewHolder.imageInListView = (TextView)convertView.findViewById(R.id.wifi_name);
            convertView.setTag(listViewHolder);
        }else{
            listViewHolder = (ViewHolder)convertView.getTag();
        }
        listViewHolder.textInListView.setText(listStorage.get(position).getTime());
        listViewHolder.imageInListView.setText(listStorage.get(position).getWifiName());

        return convertView;
    }
    static class ViewHolder{

        TextView textInListView;
        TextView imageInListView;
    }
}
