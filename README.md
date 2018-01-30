# GCellBeaconScanner_Android_v2
Examples using the improved GCell Beacon Scanning Library. Examples include scanning for all beacons, scanning for regions, receiving notifications and running scans as a service.

## Scanning in the background. 
This example shows how you can allow an Activity to start a service that scans for beacons in the background and delivers a local notification if a listed beacon is observed action and the time/strength criteria is met. It uses the same Notification Specification file outlined in the Scan For Notification example.
This service is sticky and Android will restart it if it is closed due to memory constraints. Permissions on Android 6 and above are still managed as part of the library. 

## Allowing the Service to Run
Add an entry for the service in the App Manifest file. If should fall within the Application tags, e.g., 

```xml
    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <service android:name="com.gcell.ibeacon.gcellbeaconscanlibrary.GCellBeaconScanManagerService" />
    </application>
```

## Starting the Service
Within the activity create an Intent to start the service. You can send extra data and settings as Extras - just create the appropriate*GCellNotifcationSettings*, *GCellPermissionSettings* and *GCellBeaconServiceScanSetting* objects.   

```java
 private void startBeaconScanningService() {
        Intent intent = new Intent(this, GCellBeaconScanManagerService.class);
        
        //add andy extra settings - these are optional and defaults will be used if none are supplied
        intent.putExtra("service_settings", addOptionalScanSettings());
        intent.putExtra("notification_settings", addNotifcationSettings());
        intent.putExtra("permission_settings", addOptionalPermissionOptions());

        //If true the service will automatically load a JSON file and create the appropriate Regions
        this.startService(intent);

    }

```

You can customise various aspects of the scan and notification. The most useful are:

1. Scan Length and Delay times - each scan length, and any delay between scans, can be customised. Longer and more regular scans creates a more reactive experience, but will affect battery drain
2. The delivered Notifcation title. The Notifcation body will be the 'comment' entry in the action in the beacon.json file. 

```java
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

       
        //Set Title of the delivered Notification, 
        //the body will be the notifcation comment in the beacon.json file
        notSettings.setNotificationTitle("Example Notification from a Service!");

        return notSettings;

    }
```
