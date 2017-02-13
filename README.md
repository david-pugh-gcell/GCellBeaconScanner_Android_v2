# GCellBeaconScanner_Android_v2
Examples using the improved GCell Beacon Scanning Library. Example of scanning for all beacons.

**Please See the Readme on the Master Branch for General Set up**

## Scanning for All Beacons within Range

This is the simplest way to use the librasry and it will return all oberved iBeacon devices with the range of the device. Firstly ensure that your Activity implements the *GCellScanManagerEvents* interface.

```java
public class MainActivity extends AppCompatActivity implements GCellScanManagerEvents{
    private GCellBeaconScanManager mBleScanMan;
    ...........
    
```
Performing scans is straightforward - just create a new instance of *GCellScanManger* and invoke teh *startScanningForBeacons* method. 

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
