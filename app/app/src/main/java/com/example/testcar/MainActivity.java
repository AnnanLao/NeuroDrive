package com.example.testcar;

import androidx.appcompat.app.AppCompatActivity;

import com.neurosky.connection.*;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.net.Uri; //for hyperlink in url
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    TextView myLabel;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;

    OutputStream mmOutputStream;
    
    //TODO: set up    android:onClick="goToUrl"      in image xml view. 
    
    private void goToUrl(View view) {
        String url = "https://github.com/DIT112-V20/group-08";
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
    
    //connect 8 directions button with the car
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myLabel = (TextView) findViewById(R.id.myLabel);
        
        Button connectCar = (Button) findViewById(R.id.connectCar);
        connectCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    findCarBT();
                    openCarBT();
                } catch (IOException ex) {
                }
            }
        });
        Button forward = (Button) findViewById(R.id.forward);
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    goForward();
                } catch (IOException ex) {
                }
            }
        });
        Button left = (Button) findViewById(R.id.left);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    goLeft();
                } catch (IOException ex) {
                }
            }
        });
        Button right = (Button) findViewById(R.id.right);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    goRight();
                } catch (IOException ex) {
                }
            }
        });
        Button backward = (Button) findViewById(R.id.backward);
        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    goBack();
                } catch (IOException ex) {
                }
            }
        });
        Button forwardRight = (Button) findViewById(R.id.forwardRight);
        forwardRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    goForwardRight();
                } catch (IOException ex) {
                }
            }
        });
        Button forwardLeft = (Button) findViewById(R.id.forwardLeft);
        forwardLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    goForwardLeft();
                } catch (IOException ex) {
                }
            }
        });
        Button backwardRight = (Button) findViewById(R.id.backwardRight);
        backwardRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    goBackwardRight();
                } catch (IOException ex) {
                }
            }
        });
        Button backwardLeft = (Button) findViewById(R.id.backwardLeft);
        backwardLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    goBackwardLeft();
                } catch (IOException ex) {
                }
            }
        });
    }
    
    //find car bluetooth and respond through text if no bluetooth found.
    void findCarBT() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            myLabel.setText("No bluetooth adapter available");
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                if (device.getName().equals("Car"))
                {
                    mmDevice = device;
                    myLabel.setText("Bluetooth Device Found");
                    break;
                } else {
                    myLabel.setText("Bluetooth Device NOT Found");
                }
            }
        }

    }
 
    //open car bluetooth 
    void openCarBT() throws IOException {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
        if (mmDevice != null) {
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
            mmSocket.connect();
            mmOutputStream = mmSocket.getOutputStream();

        }
    }
    
    // The getBytes() method encodes a given String into a sequence of bytes and returns an array of bytes. 
    // 8 directons proceeding methods
    void goForward() throws IOException {
        String msg = "f";
        mmOutputStream.write(msg.getBytes());
        myLabel.setText("Going Forward!");
    }
    void goBack() throws IOException {
        String msg = "b";
        mmOutputStream.write(msg.getBytes());
        myLabel.setText("Going Backwards!");
    }
    void goLeft() throws IOException {
        String msg = "l";
        mmOutputStream.write(msg.getBytes());
        myLabel.setText("Going Left!");
    }
    void goRight() throws IOException {
        String msg = "r";
        mmOutputStream.write(msg.getBytes());
        myLabel.setText("Going Right!");
    }
    void goForwardLeft() throws IOException {
        String msg = "fl";
        mmOutputStream.write(msg.getBytes());
        myLabel.setText("Going Forward Left!");
    }
    void goForwardRight() throws IOException {
        String msg = "fr";
        mmOutputStream.write(msg.getBytes());
        myLabel.setText("Going Forward Right!");
    }
    void goBackwardLeft() throws IOException {
        String msg = "bl";
        mmOutputStream.write(msg.getBytes());
        myLabel.setText("Going Backward Left!");
    }
    void goBackwardRight() throws IOException {
        String msg = "br";
        mmOutputStream.write(msg.getBytes());
        myLabel.setText("Going Backward Right!");
    }
}
