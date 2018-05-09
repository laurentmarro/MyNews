package com.example.android.mynews.Models;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.android.mynews.R;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncHTTP extends AsyncTask<String, Integer, String> {

    private AppCompatActivity myActivity;

    public AsyncHTTP(AppCompatActivity mainActivity) {
        myActivity = mainActivity;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... strings) {
        publishProgress(1);
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }

        URL url = null;
        HttpURLConnection urlConnection = null;
        String result = null;
        try {
            url = new URL(strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection(); // Open
            InputStream in = new BufferedInputStream(urlConnection.getInputStream()); // Stream
            publishProgress(2);
            result = readStream(in); // Read stream
        }
        catch (MalformedURLException e) { e.printStackTrace(); }
        catch (IOException e) { e.printStackTrace(); }
        finally { if (urlConnection != null)
            urlConnection.disconnect();  }

        publishProgress(4);
        return result; // returns the result
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        ProgressBar pb = (ProgressBar) myActivity.findViewById(R.id.progressBar);
        pb.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        TextView text = (TextView) myActivity.findViewById(R.id.text);
        text.setText(s); // Updates the textview
        ProgressBar pb = (ProgressBar) myActivity.findViewById(R.id.progressBar);
        pb.setProgress(5);
    }

    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }
}