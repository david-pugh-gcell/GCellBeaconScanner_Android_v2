# GCellBeaconScanner_Android_v2
Examples using the improved GCell Beacon Scanning Library. Example of receiving notifications.

**Please See the Readme on the Master Branch for General Set up**

## Receiving Notifications

A notification can be fed back to your activity if a beacon is observed and certain criteria are met.

### Notification Specification

A specification of what beacons to look for should be generated along with any actions thqat should be performed when they are seen. This should be in JSON format, as outlined below:
* The **actionName** is the string that is called when the action is triggered
* The **minActionRssi** determines the min Signal Strength (RSSI) that the phone should see before triggering the action (the higher the number - that is the closer to 0 - the closer you will be to the beacon, e.g., -90dB would be approx 20m and -50dB would be approx 1-2m). 
* The **reccurance** value is the time in seconds between subsequent triggers. E.g., a value of 300 would mean the time between multiple actionName triggers would be 5 minutes (300 seconds). This means we can control the ammount of times a user would be presented with information or notifications, improving user experience. 

```json
{
    "beacons":[
               {
               "comment":"Example 1",
               "UUID":"A36AD2B5-0736-43FB-8572-63DB53886FF3",
               "major":200,
               "minor":108,
               "actions":   [
                             {
                             "actionName":"custom1",
                             "minActionRssi": -55,
                             "recurrance": 5
                             },
                             {
                             "actionName":"custom3",
                             "minActionRssi": -55,
                             "recurrance": 10
                             }
                             
                             ]
               },
               {
               "comment":"Example 2",
               "UUID":"96530d4d-09af-4159-b99e-951a5e826584",
               "major":100,
               "minor":1,
               "actions":   [
                             {
                             "actionName":"custom2",
                             "minActionRssi": -55,
                             "recurrance": 20
                             }
                             ]
               }
               
               ]
}
```

The easiest way to do this is to create the file and store it in the Assets folder of your app in Android Studio. The library will automatically look for the file here.
Alternativley you can supply the JSON string, e..g., after downloading from a remote URL. 

##Scanning for Notification
Firstly ensure that your Activity implements the *GCellNotifcationEvents* interface.

```java
public class MainActivity extends AppCompatActivity implements GCellNotificationEvents{
    private GCellBeaconScanManager mBleScanMan;
    ...........
```

Then create a new Scan manager instance. If the *beacons.json* file is not stored in local assets, or has a different name then supply the appropriate details through a *GCellNotificationSetiings* object.

```java
        GCellNotificationSettings notSettings = new GCellNotificationSettings();

        //Set the name of the notification spec file
        notSettings.setNotificationSpecFileName("beacons.json");

        //Recover the file from the local assets folder
        notSettings.setNotificationSpecFileLocation(GCellFileLocationTypes.assets);
        
        mBleScanMan.setGCellNotificationSettings(notSettings);
        
```
OR
        
```java

        //Alternatively you could down the file or create the settings locally and send the JSON as a string
         GCellNotificationSettings notSettings = new GCellNotificationSettings();
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

        mBleScanMan.setGCellNotificationSettings(notSettings);

```
Then start scanning.
```java

   //Start scanning for all beacons
        mBleScanMan.startScanningForBeacons(GCellScanType.ScanForNotifications);
        ```
        
Any beacon actions that should be performed will be returned via the *GCellNotificationEvents* interface. You can handle this however you want. Here we deliver A local notification using GCellNotification - this is just a convience wrapper around the standard Android Wrapper.

```java
    @Override
    public void receivedNotification(GCellBeaconAction gCellBeaconAction) {
        //Received a notification. There is a Local Notifcation class in the library
        //That we will use use for convience

        GCellLocalNotification not = new GCellLocalNotification();

        not.sendNotifcation(gCellBeaconAction.getActionName(), this, false);

    }

    @Override
    public void notificationFileError(Integer integer, String s) {

        GCellLocalNotification not = new GCellLocalNotification();
    }

```
        
