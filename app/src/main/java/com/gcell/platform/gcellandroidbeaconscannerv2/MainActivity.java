package com.gcell.platform.gcellandroidbeaconscannerv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gcell.ibeacon.gcellbeaconscanlibrary.*;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startBeaconScanningService();
    }


    /**
     * Start the beacon scanning service
     **/
    private void startBeaconScanningService() {
        Intent intent = new Intent(this, GCellBeaconScanManagerService.class);


        //add andy extra settings - these are optional and defaults will be used if none are supplied
        intent.putExtra("service_settings", addOptionalScanSettings());
        intent.putExtra("notification_settings", addNotifcationSettings());
        intent.putExtra("permission_settings", addOptionalPermissionOptions());

        //If true the service will automatically load a JSON file and create the appropriate Regions
        this.startService(intent);

    }

    private GCellPermissionSettings addOptionalPermissionOptions(){

        //Create a GCellPermissionSettings instance
        GCellPermissionSettings permissionSettings = new GCellPermissionSettings();

        //Set the explanation message - this will show if the user has previously denied access
        permissionSettings.setPermissionRequestExplanation("Pretty please - we need this permission so we can see beacons!");

        //Set the system to automatically switch on Bluetooth if it is OFF
        permissionSettings.setAutoSwitchonBlueTooth(true);

        return permissionSettings;
    }

    private GCellBeaconScanServiceSettings addOptionalScanSettings(){

        //Create a new GCellBeaconScanServiceSettings instance to store service settings
        GCellBeaconScanServiceSettings servSettings = new  GCellBeaconScanServiceSettings();

        servSettings.setDeBug(true);
        //Set the length of each scan, and any delay between scans in ms
        //Longer and more regular scans will see beacons more quickly but will affect battery drain
        // The default is 1000ms ON and 9000ms OFF
        servSettings.setScanLength(1000);
        servSettings.setDelayBetweenScans(9000);

        return servSettings;
    }


    private GCellNotificationSettings addNotifcationSettings(){
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

        //Set
        notSettings.setNotificationTitle("Example Notification from a Service!");

        return notSettings;

    }


}
