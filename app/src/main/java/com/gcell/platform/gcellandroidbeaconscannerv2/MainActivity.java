package com.gcell.platform.gcellandroidbeaconscannerv2;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;


import com.gcell.ibeacon.gcellbeaconscanlibrary.*;


public class MainActivity extends AppCompatActivity implements GCellBeaconRegionEvents{
    
    private ArrayList<GCellBeaconRegion> mBeaconRegions = new ArrayList<>();
    private boolean deBug = false;
    private GCellBeaconScanManager mBleScanMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create an instance of the Scan manager
        mBleScanMan = new GCellBeaconScanManager(this);

        //You can also set some options if you want
        addOptionalScanOptions();

        //Set up regions of interest
        GCellBeaconRegion defaultGCellRegion = new GCellBeaconRegion();

        // Add region to be monitored
        mBleScanMan.addRegion(defaultGCellRegion);

        // or create your own and send all together to the mananger

        /**
         GCellUuid exUuid = new GCellUuid("16435D4A-11FC-1549-198F-123A5B826545");
         GCellBeaconRegion exampleUuidRegion = new GCellBeaconRegion(exUuid, "com.uuid");
         GCellBeaconRegion exampleMajorRegion = new GCellBeaconRegion(exUuid, 10, "com.major");
         GCellBeaconRegion exampleMinorRegion = new GCellBeaconRegion(exUuid, 10, 24, "com.minor");
         mBeaconRegions.add(exampleUuidRegion);
         mBeaconRegions.add(exampleMajorRegion);
         mBeaconRegions.add(exampleMinorRegion);
         mBleScanMan.setBeaconRegions(mBeaconRegions);
         */

        //Start scanning for regions
        mBleScanMan.startScanningForBeacons(GCellScanType.ScanForRegions);
    }


    private void addOptionalScanOptions(){

        //Set debug = true to see Log feedback as we scan
        mBleScanMan.setDeBug(deBug);

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
    public void didEnterBeaconRegion(GCellBeaconRegion gCellBeaconRegion) {
        System.out.println("Entered Region: " + gCellBeaconRegion.description());
    }

    @Override
    public void didExitBeaconRegion(GCellBeaconRegion gCellBeaconRegion) {
        System.out.println("Exited Region: " + gCellBeaconRegion.description());
    }

    @Override
    public void didRangeBeaconsinRegion(GCellBeaconRegion gCellBeaconRegion, List<GCelliBeacon> discoveredBeacons) {
        System.out.println(gCellBeaconRegion.description());

        for (GCelliBeacon beacon : discoveredBeacons) {
            System.out.println(beacon.getProxUuid().getStringFormattedUuid());
            System.out.println(beacon.getMajorNo());
            System.out.println(beacon.getMinorNo());
        }
    }
}
