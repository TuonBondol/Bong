package com.dnkilic.bong.currency;

public interface ICurrencyTranslationListener {
	void onTranslationSuccessful(String forexSelling, String forexBuying, String banknoteSelling, String banknoteBuying);
	void onTranslationSuccessfulWithCrossRate(String result);
	void onTranslationError(int result);
}
