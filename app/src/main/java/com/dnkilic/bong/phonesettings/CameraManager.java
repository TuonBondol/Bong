package com.dnkilic.bong.phonesettings;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;

import java.io.File;
import java.util.Date;

public class CameraManager {

    private static final int REQUEST_CODE_TAKE_PHOTO = 104;
    private String mPictureDirectory;
    private Activity mActivity;

    public CameraManager(Activity activity) {
        mActivity = activity;
        mPictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/MobilAsistan/";
        File file = new File(mPictureDirectory);
        if (!file.mkdirs()) {
        }
    }

    public void launchCamera() {
        try {

            Date date = new Date();
            CharSequence name = DateFormat.format("MM-dd-yy hh-mm-ss", date.getTime());

            File file = new File(mPictureDirectory, name + ".jpg");
            if (!file.createNewFile()) {
            }

            Uri outputFileUri = Uri.fromFile(file);

            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

            mActivity.startActivityForResult(cameraIntent, REQUEST_CODE_TAKE_PHOTO);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


