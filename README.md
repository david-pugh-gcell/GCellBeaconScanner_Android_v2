# GCellBeaconScanner_Android_v2
GCell Beacon Scanner for Android. Version 2 allowing operation as a service within Android and delivery of notifications based on beacon specificcations.

Examples and explanations of different ways to use the library are given as branches in the repository. 

### Installing the Library

### Compatibility
The library is designed and tested to work with Android 4.3 (API Level 18) onwards. This API introduces built-in platform support for Bluetooth Low Energy to scan and discover devices. As usch please ensure that the min sdk version is set to 18 in the app/build.gradle:

````xml
defaultConfig {
        .....
        minSdkVersion 18
        ......
    }
````

# Using the Library

##Adding the Library to your Project

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


##Setting Permissions
In order to detect beacons your app will need to have manifest permission to access to Bluetooth and your location. To enable these permissions add the following entries to the AndroidManifest.xml file in your app. 

````xml
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
    <uses-permission android:name="android.permission.BLUETOOTH" />
    
```
