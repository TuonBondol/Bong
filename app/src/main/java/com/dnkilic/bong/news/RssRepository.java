package com.dnkilic.bong.news;

import android.os.AsyncTask;

public class RssRepository {

    private RssFeedParser mRssFeedParser;
    private NewsCallback mNewsListener;

    public RssRepository(NewsCallback newsCallback) {
        mNewsListener = newsCallback;
    }

    public void loadRssData(String category) {
        new LoadRssData().execute(category);
    }

    private class LoadRssData extends AsyncTask<String, Void, Integer> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mRssFeedParser = new RssFeedParser();
        }

        @Override
        protected Integer doInBackground(String... params) {
            return mRssFeedParser.parseNewsFeed(params[0]);
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            switch (result) {
                case RssFeedParser.SUCCESS:
                    mNewsListener.onRssReadSuccess(mRssFeedParser.getNews());
                    break;
                case RssFeedParser.CHECK_RSS_SOURCE:
                    mNewsListener.onRssReadError(RssFeedParser.CHECK_RSS_SOURCE);
                    break;
                case RssFeedParser.ERROR:
                    mNewsListener.onRssReadError(RssFeedParser.ERROR);
                    break;
            }

        }
    }
}
