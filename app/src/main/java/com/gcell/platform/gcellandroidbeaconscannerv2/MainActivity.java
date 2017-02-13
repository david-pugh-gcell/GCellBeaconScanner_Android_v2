package com.gcell.platform.gcellandroidbeaconscannerv2;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gcell.ibeacon.gcellbeaconscanlibrary.*;


public class MainActivity extends AppCompatActivity implements GCellNotificationEvents{


    private GCellBeaconScanManager mBleScanMan;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create an instance of the Scan manager
        mBleScanMan = new GCellBeaconScanManager(this);

        //You can also set some options if you want
        addOptionalScanOptions();

        //Set up the location/contents of the Notification Specification Settings
        addNotifcationSettings();

        //Start scanning for all beacons
        mBleScanMan.startScanningForBeacons(GCellScanType.ScanForNotifications);
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


    private void addNotifcationSettings(){
        GCellNotificationSettings notSettings = new GCellNotificationSettings();

        //Set the name of the notification spec file
        notSettings.setNotificationSpecFileName("beacons.json");

        //Recover the file from the local assets folder
        notSettings.setNotificationSpecFileLocation(GCellFileLocationTypes.assets);

        //Alternatively you could down the file or create the settings locally and send the JSON as a string
        /**
         String notJson =
         "{
             'beacons':[
                                {
                                 'comment':'Example',
                                 'UUID':'96530d4d-09af-4159-b99e-951a5e826584',
                                 'major':100,
                                 'minor':1,
                                 'actions':   [
                                                {
                                                'actionName':'custom2',
                                                'minActionRssi': -55,
                                                'recurrance': 20
                                                }
                                            ]
                                }
                    ]
         }"

         notSettings.setFileContents(notJson);
         */

        mBleScanMan.setGCellNotificationSettings(notSettings);
    }



    //Notification Events

    @Override
    public void receivedNotification(GCellBeaconAction gCellBeaconAction) {
        //Received a notifcation. There is a Local Notifcation class in the library
        //That we will use use for convience

        GCellLocalNotification not = new GCellLocalNotification();

        not.sendNotifcation(gCellBeaconAction.getActionName(), this, false);

    }

    @Override
    public void notificationFileError(Integer integer, String s) {

        GCellLocalNotification not = new GCellLocalNotification();
    }
}
