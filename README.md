# GCellBeaconScanner_Android_v2
GCell Beacon Scanner for Android. Version 2 allowing operation as a service within Android and delivery of notifications based on beacon specificcations.

Examples and explanations of different ways to use the library are given as branches in the repository. Please see their indiviual Readme files for specififc documentation. 

### Installing the Library

### Compatibility
The library is designed and tested to work with Android 4.3 (API Level 18) onwards. This API introduces built-in platform support for Bluetooth Low Energy to scan and discover devices. As such please ensure that the min sdk version is set to 18 in the app/build.gradle:

```xml
defaultConfig {
        .....
        minSdkVersion 18
        ......
    }
```

# Using the Library

## Adding the Library to your Project

Use the module import wizard (File | New Module | Import .JAR or .AAR package) which will automatically add the .aar as a library module in your project. 

Then add the module as a dependency to the app - Go to File>Project Settings (Ctrl+Shift+Alt+S), under 'Modules' in the left hand window select 'app'. On the dependencies tab, click the green + symbol in the top right hand corner and add Module Dependency and select the library.

Build the newly imported module and you should now be able to import it into your main app and see the appropriate classes. For example:

```java
import com.gcell.ibeacon.gcellbeaconscanlibrary.GCellBeaconScanManager;
import com.gcell.ibeacon.gcellbeaconscanlibrary.GCellScanManagerEvents;

public class MainActivity extends AppCompatActivity implements GCellScanManagerEvents{
.......

}
```

## Setting Permissions
In order to detect beacons your app will need to have manifest permission to access to Bluetooth and your location. To enable these permissions add the following entries to the AndroidManifest.xml file in your app. 

```xml
        <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
        <uses-permission android:name="android.permission.BLUETOOTH" />
        <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />  
```

## Location Permission on Marshmallow onwards
For Android Marshmallow (API23 Version 6.0) onwards the app needs to check that the user has granted access to location everytime a scan is requested. The library automatically checks this and handles any user feedback. For the first time the app is run, the user is asked to allow or deny permission. If they deny, any pending scans are cancelled and when a scan is subsequently requested an explanation as to why permission is required will be shown. This explanation can be altered using the GCellPermissionSetting class. 

When the device Bluetooth is switched off any scanning will stop. You can also set the library to automatically enable Bluetooth if it is off using the same class. 

```java
 //Create a GCellPermissionSettings instance
        GCellPermissionSettings permissionSettings = new GCellPermissionSettings();

        //Set the explanation message - this will show if the user has previously denied access
        permissionSettings.setPermissionRequestExplanation("Pretty please - we need this permission so we can see beacons!");

        //Set the system to automatically switch on Bluetooth if it is OFF
        permissionSettings.setAutoSwitchonBlueTooth(true);

        //Set on the Scan Manager
        mBleScanMan.setGCellPermissionSettings(permissionSettings);
```
# Different Modes of Operation 
The library can be used in 4 main senarios. The first 3 are run from an Activity and scanning occurs when the Activity is in the foreground. The final example shows using scanning as a background activity. 

1. Scan for All beacons. A list of beacons is returned every second and the developer can then do what they wish with the results. 
2. Scan only for Defined Regions. Only beacons with specfific UUID, Majors and/or Minors are returned.
3. Scan and Delivery notifcations for specific beacons if certain criteria are met.
4. As a background service that delivers a local notification if certain crieria are met.
