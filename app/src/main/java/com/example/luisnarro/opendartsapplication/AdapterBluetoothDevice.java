package com.example.luisnarro.opendartsapplication;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.List;

public class AdapterBluetoothDevice extends ArrayAdapter<BluetoothDevice> implements AdapterView.OnItemClickListener {

    private final Context context;
    private final int resource;
    private final int textViewResourceId;
    private final List<BluetoothDevice> deviceList;

    public AdapterBluetoothDevice(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<BluetoothDevice> deviceList) {
        super(context, resource, textViewResourceId, deviceList);

        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.deviceList = deviceList;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
