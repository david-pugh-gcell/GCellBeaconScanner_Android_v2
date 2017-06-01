# GCellBeaconScanner_Android_v2
Examples using the improved GCell Beacon Scanning Library. Example of scanning for all beacons.

**Please See the Readme on the Master Branch for General Set up**

## Scanning for All Beacons within Range

This is the simplest way to use the library and it will return all oberved iBeacon devices with the range of the device. Firstly ensure that your Activity implements the *GCellScanManagerEvents* interface.

```java
public class MainActivity extends AppCompatActivity implements GCellScanManagerEvents{
    private GCellBeaconScanManager mBleScanMan;
    ...........
    
```
Performing scans is straightforward - just create a new instance of *GCellScanManger* and invoke the *startScanningForBeacons* method. 

```java
        //Create an instance of the Scan manager
        mBleScanMan = new GCellBeaconScanManager(this);

        //Start scanning for all beacons
        mBleScanMan.startScanningForBeacons(GCellScanType.ScanForAll);
```

Every 1 second the libvrary will return an updated list of discovered beacons through the *GcellScanManagerEvents onGCellUpdateBeaconList* call.  

```java
 @Override
    public void onGCellUpdateBeaconList(ArrayList<GCelliBeacon> discoveredBeacons) {
        for (GCelliBeacon beacon : discoveredBeacons) {
            System.out.println(beacon.getProxUuid().getStringFormattedUuid());
            System.out.println(beacon.getMajorNo());
            System.out.println(beacon.getMinorNo());
        }
    }

```

An example activity class is shown below - including adding some additional settings for the BLE scan using the *GCellPermissionSettings* class. 

'''java

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

'''
