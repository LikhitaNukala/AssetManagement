package com.example.android.myscannerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Likhita on 05-07-2017.
 */

public class SimListAdapter extends BaseAdapter {

    private Context activity;
    private List<SimDetails> list = new ArrayList<>();
    private static LayoutInflater inflater=null;


    public SimListAdapter(Context a, List<SimDetails> list) {
        this.activity = a;
        this.list=list;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.sim_detail_list, null);

        TextView time=(TextView) vi.findViewById(R.id.createdtime);
        TextView deviceId=(TextView) vi.findViewById(R.id.device_id);

        TextView state = (TextView)vi.findViewById(R.id.sim_state); // title
  SimDetails simDetails = list.get(position);
        // Setting all values in listview
        time.setText(simDetails.getCreatedTime());
        state.setText(simDetails.getSimState());

        deviceId.setText(simDetails.getDeviceId());

        return vi;
    }
}