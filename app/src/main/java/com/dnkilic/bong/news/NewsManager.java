package com.dnkilic.bong.news;

import android.util.Log;


import java.util.ArrayList;

import com.dnkilic.bong.ITask;

import ai.api.model.AIResponse;

public class NewsManager implements NewsCallback, ITask {

    @Override
    public void execute(AIResponse result) {

    }

    @Override
    public void onRssReadError(int result) {

    }

    @Override
    public void onRssReadSuccess(ArrayList<News> news) {

    }
/*

    public static final int MAXIMUM_NEWS_COUNT = 3;

    private CallSteeringResult mCallSteeringResult;

    public NewsManager(CallSteeringResult callSteeringResult) {
        mCallSteeringResult = callSteeringResult;
    }

    @Override
    public void Execute() throws Exception {

        Log.i(MainActivity.TAG, "NewsManager is started.");

        if (mCallSteeringResult.getCommandType() == CommandType.READ_NEWS) {
            if (mCallSteeringResult.getSlotValues().get("@news") != null) {

                RssRepository mRssRepository = new RssRepository(this);
                String category = mCallSteeringResult.getSlotValues().get("@news").get(0);
                mRssRepository.loadRssData(category);
            } else {
                Announcer.makeTTS(new Announce.Builder("Kategori seçmediniz.")
                        .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                        .record()
                        .build());
            }
        }
    }

    @Override
    public void onRssReadError(int result) {
        switch (result) {
            case RssFeedParser.CHECK_RSS_SOURCE:
                Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_news_manager_check_rss_source))
                        .record()
                        .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                        .build());
                break;
            case RssFeedParser.ERROR:
                Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_news_manager_error_while_request))
                        .record()
                        .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                        .build());
                break;
        }

        Log.i(MainActivity.TAG, "NewsManager is ended with errorCode : " + result);
    }

    @Override
    public void onRssReadSuccess(ArrayList<News> news) {

        String announce = "";
        int newsCounter = 0;

        for (News entry : news) {
            if (newsCounter == MAXIMUM_NEWS_COUNT)
                break;

            announce = announce + "" + entry.getTitle() + "\n" + entry.getDescription() + " " + "[!beep.raw!]" + "\n\n";
            newsCounter++;
        }

        Log.i(MainActivity.TAG, "RSS data is read successfuly.");
        Log.i(MainActivity.TAG, "RSS announce : " + announce);

        Announcer.makeTTS(new Announce.Builder(announce, news)
                .addDialogType(DialogAdapter.TYPE_NEWS)
                .build());
    }

    @Override
    public void ExecuteWithAdditionalValues(String dictation) throws Exception {
    }

    @Override
    public void ExecuteFromOtherApplication() throws Exception {
    }

    @Override
    public void ExecuteWithConfirmationTreeDecision(int isConfirmed) throws Exception {
    }
*/
}
