package de.piepenmoker.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class DownloadImageTask extends AsyncTask<URI, Void, Bitmap> {

    Context context;

    public DownloadImageTask(Context context) {
        this.context = context;
    }

    private String getUriHash(URI uri) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            // TODO
        }
        byte[] digest = md.digest(uri.toString().getBytes());
        return new BigInteger(1, digest).toString(16);
    }

    private File getUriCacheFile(URI uri) {
        String hash = getUriHash(uri);
        File cacheDir = context.getCacheDir();

        return new File(cacheDir, hash);
    }

    private Bitmap getCachedBitmap(URI uri) {
        Bitmap bmp = null;

        File x = getUriCacheFile(uri);
        if (x.exists()) {
            bmp = BitmapFactory.decodeFile(x.toString());
        }
        return bmp;
    }

    private void storeBitmapCache(URI uri, Bitmap bmp) {
        File x = getUriCacheFile(uri);
        try {
            FileOutputStream out = new FileOutputStream(x);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
        } catch (Exception e) {
            e.printStackTrace();
            // TODO
        }
    }

    protected Bitmap doInBackground(URI... uri) {
        Bitmap bmp = getCachedBitmap(uri[0]);

        if (bmp == null) {
            try {
                InputStream in = uri[0].toURL().openStream();
                bmp = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
                // TODO
            }
            storeBitmapCache(uri[0], bmp);
        }

        return bmp;
    }
}
