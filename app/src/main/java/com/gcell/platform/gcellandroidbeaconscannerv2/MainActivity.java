package com.gcell.platform.gcellandroidbeaconscannerv2;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;

import com.gcell.ibeacon.gcellbeaconscanlibrary.*;


public class MainActivity extends AppCompatActivity implements GCellScanManagerEvents{

    
    private GCellBeaconScanManager mBleScanMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create an instance of the Scan manager
        mBleScanMan = new GCellBeaconScanManager(this);

        //You can also set some options if you want
        addOptionalScanOptions();

        //Start scanning for all beacons
        mBleScanMan.startScanningForBeacons(GCellScanType.ScanForAll);
    }


    private void addOptionalScanOptions(){

        //Set debug = true to see Log feedback as we scan
        mBleScanMan.setDeBug(true);

        //Create a GCellPermissionSettings instance
        GCellPermissionSettings permissionSettings = new GCellPermissionSettings();

        //Set the explanation message - this will show if the user has previously denied access
        permissionSettings.setPermissionRequestExplanation("Pretty please - we need this permission so we can see beacons!");

        //Set the system to automatically switch on Bluetooth if it is OFF
        permissionSettings.setAutoSwitchonBlueTooth(true);

        //Set on the Scan Manager
        mBleScanMan.setGCellPermissionSettings(permissionSettings);

    }


    @Override
    public void onGCellUpdateBeaconList(ArrayList<GCelliBeacon> discoveredBeacons) {
        for (GCelliBeacon beacon : discoveredBeacons) {
            System.out.println(beacon.getProxUuid().getStringFormattedUuid());
            System.out.println(beacon.getMajorNo());
            System.out.println(beacon.getMinorNo());
        }
    }

}
