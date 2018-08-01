package com.framgia.music_20.data.source.remote;

import android.os.AsyncTask;
import com.framgia.music_20.data.source.OnDataListener;
import com.framgia.music_20.utils.Constant;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetDataUrl extends AsyncTask<String, Void, String> {

    private OnDataListener mOnDataListener;
    private Exception mException;

    public GetDataUrl(OnDataListener onDataListener) {
        mOnDataListener = onDataListener;
    }

    @Override
    protected String doInBackground(String... strings) {
        String stringData = "";
        try {
            stringData = getStringBuilderUrl(strings[0]);
        } catch (IOException e) {
            e.printStackTrace();
            mOnDataListener.onFail(e);
        }
        return stringData;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s != null) {
            mOnDataListener.onSuccess(s);
        }
    }

    private String getStringBuilderUrl(String urlString) throws IOException {
        HttpURLConnection httpURLConnection;
        URL url;
        url = new URL(urlString);
        httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod(Constant.METHOD_GET);
        httpURLConnection.setReadTimeout(Constant.TIME_READ);
        httpURLConnection.setConnectTimeout(Constant.TIME_CONNECT);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.connect();
        InputStream minputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(minputStream));
        StringBuffer stringBuffer = new StringBuffer();
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line);
        }
        bufferedReader.close();
        httpURLConnection.disconnect();
        return stringBuffer.toString();
    }
}
