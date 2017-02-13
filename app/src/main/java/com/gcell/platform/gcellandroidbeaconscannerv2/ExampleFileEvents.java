package com.gcell.platform.gcellandroidbeaconscannerv2;

/**Interface - callback when file has been downloaded from remote HTTP source
 * Created by david.pugh on 01/02/2017.
 */

interface ExampleFileEvents {

    /**
     * Indicates that the remote file has been downloaded and its contents are reporssented by contents parameter
     * @param contents contents of the file
     */
    void fileDownloaded(String contents);
}
