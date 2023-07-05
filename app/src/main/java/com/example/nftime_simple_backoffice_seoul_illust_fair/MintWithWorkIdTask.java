package com.example.nftime_simple_backoffice_seoul_illust_fair;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class MintWithWorkIdTask extends AsyncTask<String, Void, Integer> {
    private AsyncResponse<Integer> delegate = null;
    private Context context;

    public MintWithWorkIdTask(Context context, AsyncResponse<Integer> delegate){
        this.delegate = delegate;
        this.context = context;
    }

    public interface AsyncResponse<T> {
        void onAsyncSuccess(T result);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected Integer doInBackground(String... strings) {
        String workId = strings[0];
        String address = strings[1];
        try {
            URL httpEndpoint = null;
            httpEndpoint = new URL("http://3.39.120.32/klip/mint");
            String jsonBody = "{" +
                    "\"id\":\""+ workId +"\"," +
                    "\"user_address\":\"" + address +"\""
                    + "}";

            HttpURLConnection myConnection =
                    (HttpURLConnection) httpEndpoint.openConnection();
            myConnection.setRequestMethod("POST");
            myConnection.setUseCaches(false);
            myConnection.setDoOutput(true);
            myConnection.setDoInput(true);
            myConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            OutputStream outputStream = myConnection.getOutputStream();
            outputStream.write(jsonBody.getBytes(StandardCharsets.UTF_8));
            outputStream.close();

            int resCode = myConnection.getResponseCode();

            if (resCode == 200 || resCode == 201) {
                // Success
                // Further processing here
                InputStream responseBody = myConnection.getInputStream();

                String text = new BufferedReader(
                        new InputStreamReader(responseBody, StandardCharsets.UTF_8))
                        .lines()
                        .collect(Collectors.joining("\n"));

                return resCode;
            } else {
                // Error handling code goes here
                InputStream responseBody = myConnection.getInputStream();

                String errStr = new BufferedReader(
                        new InputStreamReader(responseBody, StandardCharsets.UTF_8))
                        .lines()
                        .collect(Collectors.joining("\n"));

                Log.d("test", errStr);
                Toast.makeText(context, errStr, Toast.LENGTH_LONG);
            }

            myConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        delegate.onAsyncSuccess(result);
    }
}
