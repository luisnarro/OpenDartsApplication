package com.example.luisnarro.opendartsapplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    Button b_on, b_off, b_discoverable, b_list, b_discover;
    ListView ls_devicelist;
    ArrayList<BluetoothDevice> listItems = new ArrayList<>();
    ArrayAdapter adapter;

    private static final int REQUEST_ENABLED = 0;
    private static final int REQUEST_DISCOVERABLE = 0;

    BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b_on = findViewById(R.id.b_on);
        b_off = findViewById(R.id.b_off);
        b_discoverable = findViewById(R.id.b_discoverable);
        b_list = findViewById(R.id.b_list);
        b_discover = findViewById(R.id.b_discover);

        ls_devicelist = findViewById(R.id.ls_devicelist);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth not suported", Toast.LENGTH_SHORT).show();
            finish();
        }

        b_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, REQUEST_ENABLED);
            }
        });
        b_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothAdapter.disable();
            }
        });
        b_discoverable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!bluetoothAdapter.isDiscovering()){
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(intent, REQUEST_DISCOVERABLE);
                }
            }
        });
        b_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
                ArrayList<String> devices = new ArrayList<>();

                for (BluetoothDevice bt : pairedDevices){
                    devices.add(bt.getName());
                }

                ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, devices);

                ls_devicelist.setAdapter(arrayAdapter);
            }
        });

        b_discover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listItems.clear();
                adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, listItems);
                ls_devicelist.setAdapter(adapter);

                IntentFilter bluetoothFilter = new IntentFilter();
                bluetoothFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
                bluetoothFilter.addAction(BluetoothDevice.ACTION_FOUND);
                bluetoothFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
                registerReceiver(mReceiver, bluetoothFilter);
                if (bluetoothAdapter.isDiscovering()) {
                    bluetoothAdapter.cancelDiscovery();
                }
                bluetoothAdapter.startDiscovery();
            }
        });
    }

    @Override
    protected void onDestroy() {
        bluetoothAdapter.cancelDiscovery();
        super.onDestroy();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the name and address to an array adapter to show in a ListView
                listItems.add(device);
                adapter.notifyDataSetChanged();
                ls_devicelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Log.v("Elemento \"toString\": ", ls_devicelist.getItemAtPosition(i).toString());
                    }
                });
            }
            // When discovery starts
            else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)){

            }
            // When discovery finish
            else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){

            }
        }
    };
}

