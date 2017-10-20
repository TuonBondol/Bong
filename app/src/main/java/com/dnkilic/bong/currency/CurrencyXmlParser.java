package com.dnkilic.bong.currency;

import android.util.Log;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class CurrencyXmlParser {

    public static final int SUCCESSFUL = 0;
    public static final int FAILED = 1;

	private LinkedHashMap<String, ArrayList<Currency>> mCurrentCurrencyRates;
	
	public CurrencyXmlParser() {
	}

    public int parseCurrencyXml() {
        ArrayList<Currency> currencies = new ArrayList<>();
        try {
			System.setProperty("http.keepAlive", "false");
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			URL tcmbTodayXml = new URL("http://www.tcmb.gov.tr/kurlar/today.xml");
			Document doc = builder.parse(tcmbTodayXml.openStream());
			
			NodeList nodesCurrency = doc.getElementsByTagName("Currency");
			for (int i = 0; i < nodesCurrency.getLength(); i++) {
				Currency currency = new Currency();
				Element element = (Element) nodesCurrency.item(i);
				currency.setCurrencyCode(element.getAttribute("CurrencyCode"));
				currency.setCurrencyName(getElementValue(element, "CurrencyName"));
				currency.setCurrencyNameLocale(getElementValue(element, "Isim"));
				currency.setBanknoteBuying(getFloat(getElementValue(element, "BanknoteBuying")));
				currency.setBanknoteSelling(getFloat(getElementValue(element, "BanknoteSelling")));
				currency.setCrossRateOther(getFloat(getElementValue(element, "CrossRateOther")));
				currency.setCrossRateUSD(getFloat(getElementValue(element, "CrossRateUSD")));
				currency.setForexBuying(getFloat(getElementValue(element, "ForexBuying")));
				currency.setForexSelling(getFloat(getElementValue(element, "ForexSelling")));
				currency.setUnit(getElementValue(element, "Unit"));
				currencies.add(currency);
			}
			
			NodeList nodesDate = doc.getElementsByTagName("Tarih_Date");
			Element element = (Element) nodesDate.item(0);
			
			mCurrentCurrencyRates = new LinkedHashMap<>();
			mCurrentCurrencyRates.put(element.getAttribute("Tarih"), currencies);
			
		} catch (Exception e) {
			e.printStackTrace();
            return CurrencyXmlParser.FAILED;
        }

        return CurrencyXmlParser.SUCCESSFUL;
    }

	private String getCharacterDataFromElement(Element element) {
		try {
			Node child = element.getFirstChild();
			if (child instanceof CharacterData) {
				CharacterData data = (CharacterData) child;
				return data.getData();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	private String getFloat(String value) {
		if ((value != null) && !(value.equals(""))) {
			double rate = Double.valueOf(value);
			DecimalFormat df = new DecimalFormat("0.0000");
			return df.format(rate).replace(",",".");
		}
		return "0.0000";
	}

	private String getElementValue(Element parent, String label) {
		return getCharacterDataFromElement((Element) parent
				.getElementsByTagName(label).item(0));
	}

	public LinkedHashMap<String, ArrayList<Currency>> getCurrencies() {
		return mCurrentCurrencyRates;
	}

}
