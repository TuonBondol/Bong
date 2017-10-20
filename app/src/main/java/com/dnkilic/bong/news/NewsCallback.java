package com.dnkilic.bong.news;

import java.util.ArrayList;

public interface NewsCallback {
    void onRssReadError(int result);
    void onRssReadSuccess(ArrayList<News> news);
}
