package com.example.bakingapp.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String SCHEME="https";
    private static final String AUTHORITY="d17h27t6h515a5.cloudfront.net";
    private static final String PATH1="topher";
    private static final String PATH2="2017";
    private static final String PATH3="May";
    private static final String PATH4="59121517_baking";
    private static final String PATH5="baking.json";

    public static URL buildUrl(){
        Uri.Builder uri = new Uri.Builder();

        URL url = null;

        uri.scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(PATH1)
                .appendPath(PATH2)
                .appendPath(PATH3)
                .appendPath(PATH4)
                .appendPath(PATH5);

        try {
            url=new URL(uri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
