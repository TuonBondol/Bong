package com.dnkilic.bong.alarm;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.provider.AlarmClock;

import java.util.Calendar;

import com.dnkilic.bong.ITask;

import ai.api.model.AIResponse;

public class AlarmSetter implements ITask {

	private static final int REQUEST_CODE_SET_ALARM = 102;
	private static final int REQUEST_CODE_SET_TIMER = 103;

	private static final int ERROR_INSUFFICIENT_ANDROID_VERSION = 0;
	private static final int ERROR_COUNTDOWN_LIMIT_OVERFLOW = 1;
	private static final int SUCCESSFUL = 2;

	private Activity mActivity;

	public AlarmSetter(Activity activity) {
		mActivity = activity;
	}

	private int setCountDownClock(String duration) {

		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {

			int seconds;
			int day, hour, minute, second;

			String [] durationArray = duration.split("\\.");

			if(durationArray.length > 1)
			{
				day = Integer.parseInt(durationArray[0]);

				String hours = durationArray[1];
				String [] hourArray = hours.split(":");

				hour = Integer.parseInt(hourArray[0]);
				minute = Integer.parseInt(hourArray[1]);
				second = Integer.parseInt(hourArray[2]);

				seconds = second + minute * 60 + hour * 60 * 60 + day * 60 * 60 * 24;
			}
			else
			{
				String [] hourArray = duration.split(":");

				hour = Integer.parseInt(hourArray[0]);
				minute = Integer.parseInt(hourArray[1]);
				second = Integer.parseInt(hourArray[2]);

				seconds = second + minute * 60 + hour * 60 * 60;
			}

			if(seconds > 360000)
			{
				return AlarmSetter.ERROR_COUNTDOWN_LIMIT_OVERFLOW;
			}

			return setCountDown(seconds);

		}
		else
		{
			return AlarmSetter.ERROR_INSUFFICIENT_ANDROID_VERSION;
		}
	}

	private void setAlarmClock(String time) {
		Calendar alarmTime = stringTimeToCalendar(time);
		setAlarm(alarmTime);
	}

	private void setAlarm(Calendar cal) {
		Intent alarmIntent = new Intent(AlarmClock.ACTION_SET_ALARM);
		alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		alarmIntent.putExtra(AlarmClock.EXTRA_HOUR, cal.get(Calendar.HOUR_OF_DAY));
		alarmIntent.putExtra(AlarmClock.EXTRA_MINUTES, cal.get(Calendar.MINUTE));
		alarmIntent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
		mActivity.startActivityForResult(alarmIntent, REQUEST_CODE_SET_ALARM);

		//Announcer.makeTTS(new Announce.Builder(AnnounceUtility.insertHourSlotToAnnounce(mCallSteeringResult.getAnnounceText(), mCallSteeringResult.getSlotValues().get("@time").get(0))).addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED).build());
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	private int setCountDown(int seconds) {
		Intent alarmIntent = new Intent(AlarmClock.ACTION_SET_TIMER);
		alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		alarmIntent.putExtra(AlarmClock.EXTRA_LENGTH, seconds);
		alarmIntent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
		mActivity.startActivityForResult(alarmIntent, REQUEST_CODE_SET_TIMER);
		return AlarmSetter.SUCCESSFUL;
	}

	private Calendar stringTimeToCalendar(String time) {
		String[] hourMin = time.split(":");
		Calendar alarmTime = Calendar.getInstance();
		alarmTime.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hourMin[0]));
		alarmTime.set(Calendar.MINUTE, Integer.valueOf(hourMin[1]));
		return alarmTime;
	}

	@Override
	public void execute(AIResponse result) {

	}
}
