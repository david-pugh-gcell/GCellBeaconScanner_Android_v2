package com.gcell.platform.gcellandroidbeaconscannerv2;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/** Class to open a remote file via a HTTP request
 * Created by david.pugh on 31/01/2017.
 */

class GCellGetHttpFile extends AsyncTask<String, String, String> {

    private ExampleFileEvents mFileEvents;

    GCellGetHttpFile(ExampleFileEvents con){
        mFileEvents = con;
    }

    @Override
    protected String doInBackground(String... params) {
        int count;
        String output = null;
        try {
            URL url = new URL(params[0]);
            URLConnection conection = url.openConnection();
            conection.connect();
            // Get file length
            int lenghtOfFile = conection.getContentLength();
            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(),10*1024);
            // Output stream to write file in phone directory
            //OutputStream output = new FilterOutputStream(this.getFilesDir().getAbsolutePath())
            // new FileOutputStream(Environment.getExternalStorageDirectory().getPath()+"/beacons.json");



            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            byte[] b = new byte[8192];
            for (int bytesRead; (bytesRead = input.read(b)) != -1;) {
                bos.write(b, 0, bytesRead);
            }

            output = bos.toString("UTF-8");

            /**byte data[] = new byte[1024];
            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                // Publish the progress which triggers onProgressUpdate method
                publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                // Write data to file
                //output.write(data, 0, count);
                String output = count.toString(someEncoding);
            }*/
            // Flush output
            //output.flush();
            // Close streams
            //output.close();
            input.close();
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
        return output;
    }

    @Override
    protected void onPostExecute(String result) {
        mFileEvents.fileDownloaded(result);
    }
}

