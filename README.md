# GCellBeaconScanner_Android_v2
Examples using the improved GCell Beacon Scanning Library. Examples include scanning for all beacons, scanning for regions, receiving notiifcations and running scans as a service.


**Please See the Readme on the Master Branch for General Set up**

## Scanning for Beacon Regions within Range

This is a common way to use the library and it will return all oberved iBeacon devices with the range of the device that are part of a predefined Beacon Region.

### Beacon Regions
A beacon reguon is a collection of one or more beacons.Regions represent the area a user can be in if they can see one or more beacons within range. You can use this capability to generate alerts or to provide other relevant information when the user enters or exits a beacon region. 
Rather than being identified by fixed geographical coordinates, a beacon region is identified by the device’s proximity to Bluetooth low-energy beacons that advertise a combination of the following values:

1. A proximity UUID (universally unique identifier), which is a 128-bit value that uniquely identifies one or more beacons as a certain type or from a certain organization or project
2. A major value, which is a 16-bit unsigned integer that can be used to group related beacons that have the same proximity UUID
3. A minor value, which is a 16-bit unsigned integer that differentiates beacons with the same proximity UUID and major value.

Beacon regions can be defined by their proximity UUID only, Proximity UUID and Major number or by the proximity UUID, Major and Minor Numbers. This gives developers flexibility in how they define iBeacon projects and infastructure. The GCellBeaconRegion class has a number of constructures corresponding to these different definitions, but you can also automatically define a region based on the default GCell UUID.

```java
  GCellUuid exUuid = new GCellUuid("16435D4A-11FC-1549-198F-123A5B826545");
  GCellBeaconRegion exampleUuidRegion = new GCellBeaconRegion(exUuid, "com.uuid");
  GCellBeaconRegion exampleMajorRegion = new GCellBeaconRegion(exUuid, 10, "com.major");
  GCellBeaconRegion exampleMinorRegion = new GCellBeaconRegion(exUuid, 10, 24, "com.minor");
```

This allows various levels of granularity in determining if you get a callback when a beacon is observed. 

##Scanning for Regions

Firstly ensure that your Activity implements the *GCellBeaconRegionEvents* interface.

```java
public class MainActivity extends AppCompatActivity implements GCellBeaconRegionEvents{
    private GCellBeaconScanManager mBleScanMan;
    ...........
```

Then just create a new Scan manager instance, create the regions of interest and send them to the scan manager and start scanning.

```java

    //Create an instance of the Scan manager
        mBleScanMan = new GCellBeaconScanManager(this);
        
        //Set up regions of interest
        GCellBeaconRegion defaultGCellRegion = new GCellBeaconRegion();

        // Add region to be monitored
        mBleScanMan.addRegion(defaultGCellRegion);

        //Start scanning for regions
        mBleScanMan.startScanningForBeacons(GCellScanType.ScanForRegions);
```

Callbacks will be made when the device enters and exits a region, as well as when observed beacons that correspond to a region are observed. These are delivered through the *GCellBeaconRegionEvents* interface.

```java
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
```
