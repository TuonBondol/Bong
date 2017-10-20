package com.dnkilic.bong.currency;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class CurrencyRepository {

	private CurrencyXmlParser mXmlParser;
	private String mCrossTranslationResult;

	private String mForexBuyingTranslationResult;	
	private String mForexSellingTranslationResult;	
	private String mBanknoteBuyingTranslationResult;	
	private String mBanknoteSellingTranslationResult;

	private ICurrencyTranslationListener mCurrencyTranslationListener;

	private String mFromCurrency;
	private String mToCurrency;
	private Integer mAmount;

	public CurrencyRepository(ICurrencyTranslationListener currencyTranslationListener) {
		mCurrencyTranslationListener = currencyTranslationListener;
	}

	public void loadCurrencyTranslator(String from, String to, String amount)
	{
		mFromCurrency = from;
		mToCurrency = to;
		mAmount = Integer.valueOf(amount);

		new LoadCurrencyData().execute();
	}	

	public void triggerCurrencyTranslationAdapter()
	{
		if (mFromCurrency.equals(mToCurrency))
		{
			//mCurrencyTranslationListener.onTranslationError(CurrencyManager.IDENTICAL_TRANSLATE_ATTEMPT_ERROR);
		}
		else
		{
			LinkedHashMap<String, ArrayList<Currency>> currencyParseList = mXmlParser.getCurrencies();

			for(Entry<String, ArrayList<Currency>> entry: currencyParseList.entrySet())
			{
				ArrayList<Currency> currencyList = entry.getValue();

				if (mFromCurrency.equals(Currency.USD) && mToCurrency.equals(Currency.EUR))
				{
					for(Currency currency : currencyList)
					{
						if (currency.getCurrencyCode().equals(Currency.EUR))
						{
							Double oneDollarAsEuro = 1 / Double.valueOf(currency.getCrossRateOther());
							double result = mAmount * oneDollarAsEuro;
							mCrossTranslationResult = String.valueOf(result);
						}
					}

					mCurrencyTranslationListener.onTranslationSuccessfulWithCrossRate(mCrossTranslationResult);
				} else if (mFromCurrency.equals(Currency.EUR) && mToCurrency.equals(Currency.USD))
				{
					for(Currency currency : currencyList)
					{
						if (currency.getCurrencyCode().equals(Currency.EUR))
						{
							double result = Double.valueOf(currency.getCrossRateOther()) * mAmount;
							mCrossTranslationResult = String.valueOf(result);
						}
					}

					mCurrencyTranslationListener.onTranslationSuccessfulWithCrossRate(mCrossTranslationResult);
				} else if (mFromCurrency.equals(Currency.TRY) && mToCurrency.equals(Currency.USD))
				{
					for(Currency currency : currencyList)
					{
						if (currency.getCurrencyCode().equals(Currency.USD))
						{
							double resultForexBuyingTranslationResult = 1 / Double.valueOf(currency.getForexBuying()) * mAmount;
							double resultForexSellingTranslationResult = 1 / Double.valueOf(currency.getForexSelling()) * mAmount;
							double resultBanknoteBuyingTranslationResult = 1 / Double.valueOf(currency.getBanknoteBuying()) * mAmount;
							double resultBanknoteSellingTranslationResult = 1 / Double.valueOf(currency.getBanknoteSelling()) * mAmount;

							mForexBuyingTranslationResult = String.valueOf(resultForexBuyingTranslationResult);
							mForexSellingTranslationResult = String.valueOf(resultForexSellingTranslationResult);
							mBanknoteBuyingTranslationResult = String.valueOf(resultBanknoteBuyingTranslationResult);
							mBanknoteSellingTranslationResult = String.valueOf(resultBanknoteSellingTranslationResult);

							break;
						}
					}

					mCurrencyTranslationListener.onTranslationSuccessful(mForexSellingTranslationResult, mForexBuyingTranslationResult, mBanknoteSellingTranslationResult, mBanknoteBuyingTranslationResult);
				} else if (mFromCurrency.equals(Currency.USD) && mToCurrency.equals(Currency.TRY))
				{
					for(Currency currency : currencyList)
					{
						if (currency.getCurrencyCode().equals(Currency.USD))
						{
							double resultForexBuyingTranslationResult = Double.valueOf(currency.getForexBuying()) * mAmount;
							double resultForexSellingTranslationResult = Double.valueOf(currency.getForexSelling()) * mAmount;
							double resultBanknoteBuyingTranslationResult = Double.valueOf(currency.getBanknoteBuying()) * mAmount;
							double resultBanknoteSellingTranslationResult = Double.valueOf(currency.getBanknoteSelling()) * mAmount;

							mForexBuyingTranslationResult = String.valueOf(resultForexBuyingTranslationResult);
							mForexSellingTranslationResult = String.valueOf(resultForexSellingTranslationResult);
							mBanknoteBuyingTranslationResult = String.valueOf(resultBanknoteBuyingTranslationResult);
							mBanknoteSellingTranslationResult = String.valueOf(resultBanknoteSellingTranslationResult);

							break;
						}
					}

					mCurrencyTranslationListener.onTranslationSuccessful(mForexSellingTranslationResult, mForexBuyingTranslationResult, mBanknoteSellingTranslationResult, mBanknoteBuyingTranslationResult);
				} else if (mFromCurrency.equals(Currency.TRY) && mToCurrency.equals(Currency.EUR))
				{
					for(Currency currency : currencyList)
					{
						if (currency.getCurrencyCode().equals(Currency.EUR))
						{
							double resultForexBuyingTranslationResult = 1 / Double.valueOf(currency.getForexBuying()) * mAmount;
							double resultForexSellingTranslationResult = 1 / Double.valueOf(currency.getForexSelling()) * mAmount;
							double resultBanknoteBuyingTranslationResult = 1 / Double.valueOf(currency.getBanknoteBuying()) * mAmount;
							double resultBanknoteSellingTranslationResult = 1 / Double.valueOf(currency.getBanknoteSelling()) * mAmount;

							mForexBuyingTranslationResult = String.valueOf(resultForexBuyingTranslationResult);
							mForexSellingTranslationResult = String.valueOf(resultForexSellingTranslationResult);
							mBanknoteBuyingTranslationResult = String.valueOf(resultBanknoteBuyingTranslationResult);
							mBanknoteSellingTranslationResult = String.valueOf(resultBanknoteSellingTranslationResult);

							break;
						}
					}

					mCurrencyTranslationListener.onTranslationSuccessful(mForexSellingTranslationResult, mForexBuyingTranslationResult, mBanknoteSellingTranslationResult, mBanknoteBuyingTranslationResult);
				} else if (mFromCurrency.equals(Currency.EUR) && mToCurrency.equals(Currency.TRY))
				{
					for(Currency currency : currencyList)
					{
						if (currency.getCurrencyCode().equals(Currency.EUR))
						{
							double resultForexBuyingTranslationResult = Double.valueOf(currency.getForexBuying()) * mAmount;
							double resultForexSellingTranslationResult = Double.valueOf(currency.getForexSelling()) * mAmount;
							double resultBanknoteBuyingTranslationResult = Double.valueOf(currency.getBanknoteBuying()) * mAmount;
							double resultBanknoteSellingTranslationResult = Double.valueOf(currency.getBanknoteSelling()) * mAmount;

							mForexBuyingTranslationResult = String.valueOf(resultForexBuyingTranslationResult);
							mForexSellingTranslationResult = String.valueOf(resultForexSellingTranslationResult);
							mBanknoteBuyingTranslationResult = String.valueOf(resultBanknoteBuyingTranslationResult);
							mBanknoteSellingTranslationResult = String.valueOf(resultBanknoteSellingTranslationResult);

							break;
						}
					}
					mCurrencyTranslationListener.onTranslationSuccessful(mForexSellingTranslationResult, mForexBuyingTranslationResult, mBanknoteSellingTranslationResult, mBanknoteBuyingTranslationResult);
				}
				else
				{
					//mCurrencyTranslationListener.onTranslationError(CurrencyManager.WRONG_REQUEST_ERROR);
				}
			}
		}
	}

	private class LoadCurrencyData extends AsyncTask<Void, Void, Integer> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mXmlParser = new CurrencyXmlParser();
		}

		@Override
		protected Integer doInBackground(Void... params) {
			return mXmlParser.parseCurrencyXml();
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);

			if (result != CurrencyXmlParser.SUCCESSFUL) {
				//mCurrencyTranslationListener.onTranslationError(CurrencyManager.PARSE_ERROR);
			} else {
				triggerCurrencyTranslationAdapter();
			}
		}
	}
}
