package project.parallax.emarti.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import project.parallax.emarti.R;
import project.parallax.emarti.ui.GeneralChatActivity;

/**
 * Created by MohammadSommakia on 5/13/2018.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {


    private static int value = 0;
    Notification.InboxStyle inboxStyle = new Notification.InboxStyle();

    public FirebaseMessagingService() {
        super();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        int notifyId = 0;

        String notificationTitle = remoteMessage.getNotification().getTitle();
        String notificationBody = remoteMessage.getNotification().getBody();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        if (!(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)) {
            String getJson = sp.getString("USER_NAME", null);
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            ArrayList<String> getArrayList;
            getArrayList = gson.fromJson(getJson, type);

            if (getArrayList != null) {
                if (getArrayList.contains(notificationTitle)) {
                    notifyId = getArrayList.indexOf(notificationTitle) + 1;
                    fun(notificationTitle, notificationBody, notifyId, false);
                } else {
                    getArrayList.add(notificationTitle);
                    notifyId = getArrayList.size();
                    SharedPreferences.Editor editor = sp.edit();
                    String setJson = gson.toJson(getArrayList);
                    editor.putString("USER_NAME", setJson);
                    editor.apply();
                    fun(notificationTitle, notificationBody, notifyId, true);

                }
            } else {
                getArrayList = new ArrayList<>();
                getArrayList.add(notificationTitle);
                notifyId = getArrayList.size();
                SharedPreferences.Editor editor = sp.edit();
                String setJson = gson.toJson(getArrayList);
                editor.putString("USER_NAME", setJson);
                editor.apply();
                fun(notificationTitle, notificationBody, notifyId, true);
            }
        } else {
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            inboxStyle.addLine(notificationBody);
            Notification.Builder builder = new Notification.Builder(this);
            builder.setContentTitle(notificationTitle);
            builder.setContentText(notificationBody);
            builder.setNumber(++value);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                builder.setChannelId(getString(R.string.channel_id));
            }
            builder.setStyle(inboxStyle);
            Notification notification = builder.build();

            notificationManager.notify(notifyId, notification);
        }
    }

    private void fun(String notificationTitle, String notificationBody, int notifyId, boolean isNewId) {
        if (isNewId) {
            inboxStyle = new Notification.InboxStyle();
            NotificationManager nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Notification.Builder builder = new Notification.Builder(this);
            builder.setContentTitle(notificationTitle);
            builder.setContentText(notificationBody);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setAutoCancel(true);

            inboxStyle.setBigContentTitle(value + "New messages");
            PendingIntent pendingIntent =
                    PendingIntent.getActivity(getApplicationContext(), notifyId,
                            new Intent(getApplicationContext(),
                                    GeneralChatActivity.class),
                            PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setStyle(inboxStyle);
            builder.setContentIntent(pendingIntent);
            nManager.notify(notifyId, builder.build());

        } else {
            inboxStyle.addLine(notificationBody);
            Notification.Builder builder = new Notification.Builder(this);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentTitle(notificationTitle);
            builder.setContentText(notificationBody);
            inboxStyle.setBigContentTitle(value + "New messages");
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), notifyId,
                    new Intent(getApplicationContext(), GeneralChatActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setStyle(inboxStyle);
            builder.setContentIntent(pendingIntent);
            Notification notification = builder.build();
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(notifyId, notification);
        }
    }
}
