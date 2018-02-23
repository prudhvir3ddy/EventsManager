package com.eventsmanager.root.eventsmanager.utils;

import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.eventsmanager.root.eventsmanager.Activities.Notifications;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

/**
 * Created by root on 18/1/18.
 */

public class Onesignal extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationOpenedHandler(new OneSignal.NotificationOpenedHandler() {
                    @Override
                    public void notificationOpened(OSNotificationOpenResult result) {
                        OSNotificationAction.ActionType actionType = result.action.type;
                        JSONObject data = result.notification.payload.additionalData;
                        String customKey;

                        if (data != null) {
                            customKey = data.optString("customkey", null);
                            if (customKey != null)
                                Log.i("OneSignalExample", "customkey set with value: " + customKey);
                        }

                        if (actionType == OSNotificationAction.ActionType.ActionTaken)
                            Log.i("OneSignalExample", "Button pressed with id: " + result.action.actionID);

                         Intent intent=new Intent(getApplicationContext(), Notifications.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .init();
    }
}
