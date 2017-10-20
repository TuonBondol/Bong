package com.dnkilic.bong.caller;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class CallManager extends Activity {
    private static final int REQUEST_CODE_PICK_CONTACT = 105;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CallViaIntent();
    }

    private void CallViaIntent() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }

        String phoneNumber = extras.getString("phoneNumber");
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivityForResult(callIntent, REQUEST_CODE_PICK_CONTACT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finish();
    }
}
