package com.dnkilic.bong.appopener;

import android.app.Activity;
import android.content.Intent;

import java.util.HashMap;

import com.dnkilic.bong.ITask;

import ai.api.model.AIResponse;

public class ApplicationOpener implements ITask {

    @Override
    public void execute(AIResponse result) {

    }
/*

    private Activity mActivity;
    private HashMap<String, String> mApplicationList = new HashMap<String, String>() {{
        put("whatsapp", "com.whatsapp");
        put("facebook", "com.facebook.katana");
        put("instagram", "com.instagram.android");
        put("googleplus", "com.google.android.apps.plus");
        put("chrome", "com.android.chrome");
        put("pinterest", "com.pinterest");
        put("messenger", "com.facebook.orca");
        put("twitter", "com.twitter.android");
        put("googlemaps", "com.google.android.apps.maps");
    }};

    public ApplicationOpener(Activity activity) {
        mActivity = activity;
    }

    private boolean openApp(String packageName) {
        Intent i = mActivity.getPackageManager().getLaunchIntentForPackage(packageName);
        if (i == null) {
            return false;
        }
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        mActivity.startActivity(i);
        return true;
    }



    @Override
    public void execute(AIResponse aiResponse) {


        if(mCallSteeringResult.getSlotValues().get("@application") != null)
        {
            if(isApplicationInstalled(mCallSteeringResult.getSlotValues().get("@application").get(0)))
            {
                Announcer.makeTTS(new Announce.Builder(mCallSteeringResult.getAnnounceText())
                        .addCommandType(Announce.LAUNCH_ANOTHER_APP_ANNOUNCE)
                        .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                        .record()
                        .build());
            }
            else
            {
                Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_application_opener_application_not_installed))
                        .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                        .record()
                        .build());
            }
        }
        else
        {
            Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_application_opener_application_not_installed))
                    .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                    .record()
                    .build());
        }
    }

    public void ExecuteFromOtherApplication() throws Exception {
        if(mCallSteeringResult.getSlotValues().get("@application") != null)
        {
            switch (mCallSteeringResult.getSlotValues().get("@application").get(0))
            {
                case "galeri":
                    openGallery();
                    break;
                case "whatsapp":
                    openApp("com.whatsapp");
                    break;
                case "facebook":
                    openApp("com.facebook.katana");
                    break;
                case "instagram":
                    openApp("com.instagram.android");
                    break;
                case "googleplus":
                    openApp("com.google.android.apps.plus");
                    break;
                case "chrome":
                    openApp("com.android.chrome");
                    break;
                case "pinterest":
                    openApp("com.pinterest");
                    break;
                case "messenger":
                    openApp("com.facebook.orca");
                    break;
                case "twitter":
                    openApp("com.twitter.android");
                    break;
                case "googlemaps":
                    openApp("com.google.android.apps.maps");
                    break;
                default:
                    Announcer.makeTTS(new Announce.Builder("Şuan için " + mCallSteeringResult.getSlotValues().get("@application").get(0) + " uygulamasını açamıyorum")
                            .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                            .record()
                            .build());
                    break;
            }
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_VIEW, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        mActivity.startActivity(galleryIntent);
    }

    private boolean isApplicationInstalled(String applicationName) {
        String packageName = mApplicationList.get(applicationName);
        Intent i = mActivity.getPackageManager().getLaunchIntentForPackage(packageName);
        return i != null;
    }
*/
}

