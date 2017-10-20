package com.dnkilic.bong.calculator;

import android.util.Log;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.dnkilic.bong.ITask;

import ai.api.model.AIResponse;

public class Calculator implements ITask {

    private AIResponse mCallSteeringResult;

    public Calculator(AIResponse callSteeringResult) {
        mCallSteeringResult = callSteeringResult;
    }

    @Override
    public void execute(AIResponse result) {
        try {
            calculate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void calculate() throws Exception {


        ArithmeticEvaluator mArithmeticEvaluator = new ArithmeticEvaluator();

        Double result = 0.0;//mArithmeticEvaluator.CalculateArithmeticExpression(mCallSteeringResult.getSlotValues().get("@arithmetic_operation"));

        if (result == Double.POSITIVE_INFINITY || result == Double.NEGATIVE_INFINITY) {
           // Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_divide_by_zero_error))
           //         .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
           //         .record()
           //         .build());
        } else if ((result % 1) != 0) {

            BigDecimal fd = new BigDecimal(result);
            BigDecimal cutted = fd.setScale(5, RoundingMode.DOWN);
            result = cutted.doubleValue();

            //Announcer.makeTTS(new Announce.Builder(insertCalculationResultToAnnounce(mCallSteeringResult.getAnnounceText(), result.toString()))
            //        .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
            //        .build());
        } else {
            //Announcer.makeTTS(new Announce.Builder(insertCalculationResultToAnnounce(mCallSteeringResult.getAnnounceText(), Integer.toString(result.intValue())))
            //        .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
            //        .build());
        }

    }

    private String insertCalculationResultToAnnounce(String announce, String operationResult) {
        return announce + " " + operationResult;
    }
}


