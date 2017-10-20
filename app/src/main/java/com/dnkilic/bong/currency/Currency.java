package com.dnkilic.bong.currency;

public class Currency {

	public static final String TRY = "TRY";
	public static final String XDR = "SPECIAL DRAWING RIGHTS";
	public static final String PKR = "PAKISTANI RUPEE";
	public static final String CNY = "CHINESE RENMINBI";
	public static final String IRR = "IRANIAN RIAL";
	public static final String RUB = "RUSSIAN ROUBLE";
	public static final String RON = "NEW LEU";
	public static final String BGN = "BULGARIAN LEV";
	public static final String JPY = "JAPENESE YEN";
	public static final String SAR = "SAUDI RIYAL";
	public static final String NOK = "NORWEGIAN KRONE";
	public static final String KWD = "KUWAITI DINAR";
	public static final String CAD = "CANADIAN DOLLAR";
	public static final String SEK = "SWEDISH KRONA";
	public static final String CHF = "SWISS FRANK";
	public static final String GBP = "POUND STERLING";
	public static final String EUR = "EUR";
	public static final String DKK = "DANISH KRONE";
	public static final String AUD = "AUSTRALIAN DOLLAR";
	public static final String USD = "USD";

	private String mCurrencyCode;
	private String mCurrencyName;
	private String mCurrencyNameLocale;
	private String mUnit;
	private String mForexBuying;
	private String mForexSelling;
	private String mBanknoteBuying;
	private String mBanknoteSelling;
	private String mCrossRateUSD;
	private String mCrossRateOther;
	
	public Currency() {
	}
	
	public String getCurrencyCode() {
		return mCurrencyCode;
	}
	
	public void setCurrencyCode(String mCurrencyCode) {
		this.mCurrencyCode = mCurrencyCode;
	}
	
	public String getCurrencyName() {
		return mCurrencyName;
	}
	
	public void setCurrencyName(String mCurrencyName) {
		this.mCurrencyName = mCurrencyName;
	}
	
	public String getCurrencyNameLocale() {
		return mCurrencyNameLocale;
	}
	
	public void setCurrencyNameLocale(String mCurrencyNameLocale) {
		this.mCurrencyNameLocale = mCurrencyNameLocale;
	}
	
	public String getUnit() {
		return mUnit;
	}
	
	public void setUnit(String mUnit) {
		this.mUnit = mUnit;
	}
	
	public String getForexBuying() {
		return mForexBuying;
	}
	
	public void setForexBuying(String mForexBuying) {
		this.mForexBuying = mForexBuying;
	}
	
	public String getForexSelling() {
		return mForexSelling;
	}
	
	public void setForexSelling(String mForexSelling) {
		this.mForexSelling = mForexSelling;
	}
	
	public String getBanknoteBuying() {
		return mBanknoteBuying;
	}
	
	public void setBanknoteBuying(String mBanknoteBuying) {
		this.mBanknoteBuying = mBanknoteBuying;
	}
	
	public String getBanknoteSelling() {
		return mBanknoteSelling;
	}
	
	public void setBanknoteSelling(String mBanknoteSelling) {
		this.mBanknoteSelling = mBanknoteSelling;
	}
	
	public String getCrossRateUSD() {
		return mCrossRateUSD;
	}
	
	public void setCrossRateUSD(String mCrossRateUSD) {
		this.mCrossRateUSD = mCrossRateUSD;
	}
	
	public String getCrossRateOther() {
		return mCrossRateOther;
	}
	
	public void setCrossRateOther(String mCrossRateOther) {
		this.mCrossRateOther = mCrossRateOther;
	}
}
