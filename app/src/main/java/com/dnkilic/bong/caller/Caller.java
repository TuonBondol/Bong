package com.dnkilic.bong.caller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.ArrayList;

import com.dnkilic.bong.ITask;

import ai.api.model.AIResponse;

public class Caller implements ITask {

    @Override
    public void execute(AIResponse result) {

    }
/*

    private static final int REQUEST_CODE_CALL = 101;

    private AIResponse mCallSteeringResult;
    private ContactSaver mContacts;
    private String mPhoneNumber;
    private String mName;
    private Activity mActivity;

    public Caller(AIResponse callSteeringResult, Activity activity) {
        mCallSteeringResult = callSteeringResult;
        mContacts = new ContactSaver(activity);
        mContacts.generateContactMap();
        mActivity = activity;
    }

    public Caller(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void Execute() throws Exception {
        call();
    }

    private void call() throws Exception {


        ArrayList<String> names = null;//mCallSteeringResult.getSlotValues().get("@name_lastname");

        String name = "";
        for (String item : names) {
            if (name.isEmpty()) {
                name = item;
            } else {
                name = name + " " + item;
            }
        }

        ArrayList<ContactPerson> targetContacts = mContacts.newSearchAlgorithm(name);

        if (((TelephonyManager) mActivity.getApplicationContext()
                .getSystemService(Context.TELEPHONY_SERVICE))
                .getSubscriberId() == null) {
            //Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_call_manager_no_network))
            //        .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
            //        .record()
            //        .build());
        } else {
            if (targetContacts == null || targetContacts.isEmpty()) {
            //    Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_call_manager_cant_find_contact))
            //            .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
            //            .record()
            //            .build());
            } else {
                if (targetContacts.size() == 1) {
                    ContactPerson contact = targetContacts.get(0);
                    ArrayList<String> contacts = contact.getPhoneNumbers();

                    if (contacts.size() < 1) {
            //            Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_call_manager_there_is_no_number_in_contact))
            //                    .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
            //                    .record()
            //                    .build());
                    } else if (contacts.size() == 1) {
                        mPhoneNumber = contacts.get(0);
                        mName = name;

                        String announce = name + " adlı kişiyi arıyorum. Onaylıyor musun?";
            //            Announcer.makeTTS(new Announce.Builder(announce)
            //                    .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
            //                    .addCommandType(Announce.CONFIRMATION_ANNOUNCE)
            //                    .build());
                    } else {
                        mPhoneNumber = contacts.get(0);
                        mName = name;

                        String announce = "Rehberinde aynı isimle birden fazla kayıt var. Bulduğum ilk numarayı arıyorum onaylıyor musun?";
            //            Announcer.makeTTS(new Announce.Builder(announce)
            //                    .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
            //                    .addCommandType(Announce.CONFIRMATION_ANNOUNCE)
            //                    .build());
                    }
                } else {
                    String announce = "";

                    for (ContactPerson person : targetContacts) {
                        if (announce.isEmpty()) {
                            announce = announce + person.getOriginalNames().get(0);
                        } else {
                            announce = announce + ", " + person.getOriginalNames().get(0);
                        }
                    }

                    announce = "Kimi aramak istiyorsun? " + announce;

            //        Announcer.makeTTS(new Announce.Builder(announce)
            //                .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
            //                .record()
            //                .build());
                }
            }
        }
    }

    public void callTheNumber(String phoneNumber) {
        try {
            Intent i = new Intent(mActivity, CallManager.class);
            i.putExtra("phoneNumber", phoneNumber);
            mActivity.startActivityForResult(i, REQUEST_CODE_CALL);
        } catch (Exception e) {
            e.printStackTrace();
            //Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_call_manager_error_while_call_request))
            //        .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
            //        .record()
            //        .build());
        }
    }

    public void ExecuteFromOtherApplication() throws Exception {

        Log.i("deneme", mPhoneNumber);
        Log.i("deneme", mName);

        callTheNumber(mPhoneNumber);
    }

    @Override
    public void execute(AIResponse result) {

    }
*/
}


