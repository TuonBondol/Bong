package com.dnkilic.bong.currency;

import android.util.Log;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.dnkilic.bong.ITask;

import ai.api.model.AIResponse;

public class CurrencyManager implements ICurrencyTranslationListener, ITask {

    @Override
    public void onTranslationSuccessful(String forexSelling, String forexBuying,
            String banknoteSelling, String banknoteBuying) {

    }

    @Override
    public void onTranslationSuccessfulWithCrossRate(String result) {

    }

    @Override
    public void onTranslationError(int result) {

    }

    @Override
    public void execute(AIResponse result) {

    }
/*

    public static final int PARSE_ERROR = 2;
    public static final int WRONG_REQUEST_ERROR = 3;
    public static final int IDENTICAL_TRANSLATE_ATTEMPT_ERROR = 4;

    private CallSteeringResult mCallSteeringResult;
    private String mFrom;
    private String mToCurrency;

    public CurrencyManager(CallSteeringResult callSteeringResult) {
        mCallSteeringResult = callSteeringResult;
    }

    @Override
    public void Execute() throws Exception {
        translate();
    }

    private void translate() {

        Log.i(MainActivity.TAG, "CurrencyManager is started...");

        if(mCallSteeringResult.getCommandType() == CommandType.CURRENCY_WITH_AMOUNT)
        {
            String amount = mCallSteeringResult.getSlotValues().get("@currency").get(0).replaceAll("[^0-9]", "");

            mToCurrency = null;
            if (mCallSteeringResult.getSlotValues().get("@currency_type") != null && !mCallSteeringResult.getSlotValues().get("@currency_type").isEmpty())
            {
                mToCurrency = mCallSteeringResult.getSlotValues().get("@currency_type").get(0).toUpperCase();
            }
            else
            {
                mToCurrency = "TRY";
            }

            if (mCallSteeringResult.getSlotValues().get("@currency").get(0).contains("lira") || mCallSteeringResult.getSlotValues().get("@currency").get(0).contains("türk lirası")
                    || mCallSteeringResult.getSlotValues().get("@currency").get(0).contains("türk") || mCallSteeringResult.getSlotValues().get("@currency").get(0).contains("TRY")
                    || mCallSteeringResult.getSlotValues().get("@currency").get(0).contains("tl"))
            {
                mFrom = "TRY";
                requestCurrencyTranslation(mFrom, mToCurrency, amount);
            } else if (mCallSteeringResult.getSlotValues().get("@currency").get(0).contains("dolar") || mCallSteeringResult.getSlotValues().get("@currency").get(0).contains("dollar")
                    || mCallSteeringResult.getSlotValues().get("@currency").get(0).contains("dolar") || mCallSteeringResult.getSlotValues().get("@currency").get(0).contains("USD"))
            {
                mFrom = "USD";
                requestCurrencyTranslation(mFrom, mToCurrency, amount);
            } else if (mCallSteeringResult.getSlotValues().get("@currency").get(0).contains("euro") || mCallSteeringResult.getSlotValues().get("@currency").get(0).contains("yuro")
                    || mCallSteeringResult.getSlotValues().get("@currency").get(0).contains("EUR"))
            {
                mFrom = "EUR";
                requestCurrencyTranslation(mFrom, mToCurrency, amount);
            }
        }
        else if(mCallSteeringResult.getCommandType() == CommandType.CURRENCY_WITHOUT_AMOUNT)
        {
            if (mCallSteeringResult.getSlotValues().get("@currency_type").size() > 1)
            {
                mFrom = mCallSteeringResult.getSlotValues().get("@currency_type").get(0).toUpperCase();
                mToCurrency = mCallSteeringResult.getSlotValues().get("@currency_type").get(1).toUpperCase();
                String amount = "1";
                requestCurrencyTranslation(mFrom, mToCurrency, amount);
            }
            else
            {
                mFrom = mCallSteeringResult.getSlotValues().get("@currency_type").get(0).toUpperCase();
                mToCurrency = "TRY";
                String amount = "1";
                requestCurrencyTranslation(mFrom, mToCurrency, amount);
            }
        }
    }

    private void requestCurrencyTranslation(String from, String to, String amount)
    {
        CurrencyRepository currencyRepository = new CurrencyRepository(this);
        currencyRepository.loadCurrencyTranslator(from, to, amount);
    }

    @Override
    public void onTranslationSuccessful(String forexSelling, String forexBuying, String banknoteSelling, String banknoteBuying) {

        Float result = Float.valueOf(banknoteSelling);
        BigDecimal fd = new BigDecimal(result);
        BigDecimal cutted = fd.setScale(2, RoundingMode.DOWN);
        result = cutted.floatValue();

        if(mFrom.equals("USD"))
        {
            mFrom = "Dolar";
        }
        else if(mFrom.equals("EUR"))
        {
            mFrom = "Euro";
        }

        if(mToCurrency.equals("USD"))
        {
            mToCurrency = "Dolar";
        }
        else if(mToCurrency.equals("EUR"))
        {
            mToCurrency = "Euro";
        }

        String announce = mCallSteeringResult.getAnnounceText();

        if(mCallSteeringResult.getCommandType() == CommandType.CURRENCY_WITH_AMOUNT)
        {
            announce = announce.replace("=currency", mCallSteeringResult.getSlotValues().get("@currency").get(0));
            announce = announce.replace("=result", String.valueOf(result) + " " + mToCurrency);
        }
        else
        {
            announce = announce.replace("=currency_type", mFrom);
            announce = announce.replace("=result", String.valueOf(result) + " " + mToCurrency);
        }

        Announcer.makeTTS(new Announce.Builder(announce)
                .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                .build());

        Log.i(MainActivity.TAG, "CurrencyManager is ended.");
    }

    @Override
    public void onTranslationSuccessfulWithCrossRate(String result) {

        Float translationResult = Float.valueOf(result);
        BigDecimal fd = new BigDecimal(translationResult);
        BigDecimal cutted = fd.setScale(2, RoundingMode.DOWN);
        translationResult = cutted.floatValue();

        if(mFrom.equals("USD"))
        {
            mFrom = "dolar";
        }
        else if(mFrom.equals("EUR"))
        {
            mFrom = "euro";
        }

        if(mToCurrency.equals("USD"))
        {
            mToCurrency = "dolar";
        }
        else if(mToCurrency.equals("EUR"))
        {
            mToCurrency = "euro";
        }

        String announce = mCallSteeringResult.getAnnounceText();

        if(mCallSteeringResult.getCommandType() == CommandType.CURRENCY_WITH_AMOUNT)
        {
            announce = announce.replace("=currency", mCallSteeringResult.getSlotValues().get("@currency").get(0));
            announce = announce.replace("=result", String.valueOf(translationResult)+ " " + mToCurrency);
        }
        else
        {
            announce = announce.replace("@currency_type", mFrom);
            announce = announce.replace("=result", String.valueOf(translationResult)+ " " + mToCurrency);
        }

        Announcer.makeTTS(new Announce.Builder(announce)
                .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                .build());
    }

    @Override
    public void onTranslationError(int result) {
        switch(result)
        {
            case CurrencyManager.IDENTICAL_TRANSLATE_ATTEMPT_ERROR:
                Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_currency_manager_translation_request_for_identical_currency)).record().addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED).build());
                break;
            case CurrencyManager.PARSE_ERROR:
                Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_currency_manager_error_while_processing_bank_data)).record().addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED).build());
                break;
            case CurrencyManager.WRONG_REQUEST_ERROR:
                Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_currency_manager_wrong_translation_request)).record().addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED).build());
                break;
        }
    }

    @Override
    public void execute(AIResponse result) {

    }
*/
}

