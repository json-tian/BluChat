package com.example.user.BlueChat;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.user.bluetooth_communication.R;

import java.util.ArrayList;


public class MessageListAdapter extends ArrayAdapter<String> {

    private LayoutInflater mLayoutInflater;
    private ArrayList<String> Nmessages;
    private ArrayList<String> Nusernames;
    private int  mViewResourceId;

    public MessageListAdapter(Context context, int tvResourceId, ArrayList<String> messages, ArrayList<String> usernames){
        super(context, tvResourceId,messages);
        this.Nmessages = messages;
        this.Nusernames = usernames;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = tvResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mLayoutInflater.inflate(mViewResourceId, null);

        String message = Nmessages.get(position);
        String username = Nusernames.get(position);

        TextView deviceName = (TextView) convertView.findViewById(R.id.tvDeviceName2);
        TextView person_name = (TextView) convertView.findViewById(R.id.tvDeviceAddress2);

        person_name.setText(username);
        deviceName.setText(message);

        return convertView;
    }

}
