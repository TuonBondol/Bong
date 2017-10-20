package com.dnkilic.bong.news;

import android.util.Log;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class RssFeedParser {

    public static final int ERROR = 0;
    public static final int SUCCESS = 1;
    public static final int CHECK_RSS_SOURCE = 2;

    private ArrayList<News> mFeeds;

    public RssFeedParser() {
    }

    public Integer parseNewsFeed(String category) {
        mFeeds = new ArrayList<>();
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            URL rssUrl = new URL("http://aa.com.tr/tr/rss/default?cat=" + category);
            Document doc = builder.parse(rssUrl.openStream());

            NodeList nodesEntry = doc.getElementsByTagName("item");
            for (int i = 0; i < nodesEntry.getLength(); i++) {
                NodeList nodesElement = nodesEntry.item(i).getChildNodes();

                for (int j = 0; j < nodesElement.getLength(); j++) {
                    Element element = (Element) nodesEntry.item(j);
                    News newsEntry = new News();
                    newsEntry.setId(getElementValue(element, "guid"));
                    newsEntry.setLink(getElementValue(element, "link"));
                    newsEntry.setPublishDate(getElementValue(element, "pubDate"));
                    newsEntry.setDescription(getElementValue(element, "description"));
                    newsEntry.setTitle(getElementValue(element, "title"));
                    newsEntry.setImageLink(getElementValue(element, "image"));
                    mFeeds.add(newsEntry);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }

        if (mFeeds == null || mFeeds.isEmpty()) {
            Log.v("RssFeedParser", "RSS feed is empty.");
            return CHECK_RSS_SOURCE;
        }

        Log.v("RssFeedParser", "RSS feed count : " + mFeeds.size());

        return SUCCESS;
    }

    private String getCharacterDataFromElement(Element element) {
        try {
            Node child = element.getFirstChild();
            if (child instanceof CharacterData) {
                CharacterData data = (CharacterData) child;
                return data.getData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    private String getElementValue(Element parent, String label) {
        return getCharacterDataFromElement((Element) parent
                .getElementsByTagName(label).item(0));
    }

    public ArrayList<News> getNews() {
        return mFeeds;
    }
}
