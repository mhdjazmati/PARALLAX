package project.parallax.emarti.logic.presenters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import project.parallax.emarti.logic.adapters.ChatMessageAdapter;
import project.parallax.emarti.logic.models.ChatMessageModel;
import project.parallax.emarti.ui.GeneralChatActivity;
import project.parallax.emarti.utility.AppConst;
import project.parallax.emarti.utility.PreferenceManager;

/**
 * Created by MohammadSommakia on 4/22/2018.
 */

public class ChatPresenter implements ChatMessageAdapter.ChatClickListener {
    GeneralChatActivity activity;
    RecyclerView recyclerView;
    Button sendMessageButton;
    EditText editTextChatBox;
    private LinearLayout linearLayout;
    private DatabaseReference rootRef;
    private ArrayList<ChatMessageModel> rowListItems;
    private ArrayList<String> apartmentId;
    private List<String> myMessage;
    private Map userMessageMap;
    private Map messageMap;
    private Map buildingsMap;
    private int k = 0;
    private String currentUserId;
    private String buildingName;
    private String date;
    private String committeeName;
    final String committeeId;
    ChatMessageAdapter rcAdapter;
    String sendingTime;
    String message;
    String buildingList = "";

    private DatabaseReference databaseNotification;


    public ChatPresenter(GeneralChatActivity generalChatActivity, RecyclerView recyclerView, Button sendMessageButton, EditText editTextChatBox, LinearLayout linearLayout) {
        this.activity = generalChatActivity;
        this.recyclerView = recyclerView;
        this.sendMessageButton = sendMessageButton;
        this.editTextChatBox = editTextChatBox;
        this.linearLayout = linearLayout;
        rootRef = FirebaseDatabase.getInstance().getReference();
        rowListItems = new ArrayList<>();
        apartmentId = new ArrayList<>();
        currentUserId = PreferenceManager.getInstance(activity).getString(AppConst.ID, null);
        rcAdapter = new ChatMessageAdapter(activity, rowListItems, true);
        committeeId = activity.getIntent().getStringExtra(AppConst.ID);
        myMessage = new ArrayList<>();
        databaseNotification = FirebaseDatabase.getInstance().getReference().child(AppConst.NOTIFICATIONS);

        rootRef.keepSynced(true);
        setUpRecyclerView();
        setUpActions();
        rcAdapter.setClickListener(this);
        receiveGeneralMessage();
        getMyMessage();
    }

    private void getMyMessage() {
        if (committeeId == null)
            rootRef.child(AppConst.GENERAL_MESSAGES).child(currentUserId).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    String message = dataSnapshot.child(AppConst.MESSAGE).getValue().toString();
                    String sendingTime = dataSnapshot.child(AppConst.SENDING_TIME).getValue().toString();
                    String buildingsReceived = dataSnapshot.child(AppConst.BUILDINGS_LIST).getValue().toString();
                    rcAdapter.add(new ChatMessageModel(message, buildingsReceived, sendingTime, currentUserId, 0), k, committeeName);
                    k++;
                    recyclerView.setAdapter(rcAdapter);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });
    }


    private void receiveGeneralMessage() {
        committeeName = activity.getIntent().getStringExtra(AppConst.USER_NAME);
        // that's mean current user is apartment
        if (committeeId != null) {
            linearLayout.setVisibility(View.GONE);

            rootRef.child(AppConst.APARTMENTS).child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    buildingName = dataSnapshot.child(AppConst.BUILDING_NAME).getValue().toString();

                    rootRef.child(AppConst.GENERAL_MESSAGES).child(committeeId).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String buildingList = dataSnapshot.child(AppConst.BUILDINGS_LIST).getValue().toString();
                            if (buildingList.contains(buildingName)) {
                                message = dataSnapshot.child(AppConst.MESSAGE).getValue().toString();
                                sendingTime = dataSnapshot.child(AppConst.SENDING_TIME).getValue().toString();
                                rcAdapter.add(new ChatMessageModel(message, committeeId, sendingTime), k, committeeName);
                                k++;
                                recyclerView.setAdapter(rcAdapter);
                            }
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void setUpActions() {
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageMap = new HashMap();
                userMessageMap = new HashMap();
                buildingsMap = new HashMap();
                buildingList = "";
                apartmentId.removeAll(apartmentId);
                sendMassage();
            }
        });
    }

    private void sendMassage() {
        Intent intent = activity.getIntent();
        final ArrayList<String> buildingNameList = intent.getStringArrayListExtra(AppConst.BUILDING_NAME);
        final ArrayList<String> buildingKeyList = intent.getStringArrayListExtra(AppConst.BUILDING_KEY);
        DateFormat df = new SimpleDateFormat("h:mm a");
        date = df.format(Calendar.getInstance().getTime());
        final String message = editTextChatBox.getText().toString();
        if (!message.equals("") && currentUserId != null) {
            String currentUserRef = AppConst.GENERAL_MESSAGES + "/" + currentUserId;
            DatabaseReference userMessage = rootRef.child(AppConst.GENERAL_MESSAGES).child(currentUserRef).push();
            messageMap.put(AppConst.MESSAGE, message);
            String pushId = userMessage.getKey();
            for (int j = 0; j < buildingNameList.size(); j++)
                buildingList += buildingNameList.get(j) + " ";
            messageMap.put(AppConst.BUILDINGS_LIST, buildingList);
            messageMap.put(AppConst.SENDING_TIME, date);
            userMessageMap.put(currentUserRef + "/" + pushId, messageMap);

            rootRef.updateChildren(userMessageMap, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    rootRef.child(AppConst.APARTMENTS).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                String buildingName = snapshot.child(AppConst.BUILDING_NAME).getValue().toString();
                                if (buildingList.contains(buildingName)) {
                                    apartmentId.add(snapshot.getKey());
                                }
                            }

                            for (int k = 0; k < apartmentId.size(); k++) {
                                HashMap<String, String> notificationMap = new HashMap();
                                notificationMap.put(AppConst.FROM, currentUserId);
                                notificationMap.put(AppConst.TYPE, AppConst.GENERAL_MESSAGES);
                                notificationMap.put(AppConst.MESSAGE, message);
                                databaseNotification.child(apartmentId.get(k)).push().setValue(notificationMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                    }
                                });

                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    editTextChatBox.setText("");
                }
            });
        }
    }

//    private void sendMassage() {
//        Intent intent = activity.getIntent();
//        final ArrayList<String> buildingNameList = intent.getStringArrayListExtra(AppConst.BUILDING_NAME);
//        final ArrayList<String> buildingKeyList = intent.getStringArrayListExtra(AppConst.BUILDING_KEY);
//        DateFormat df = new SimpleDateFormat("h:mm a");
//        date = df.format(Calendar.getInstance().getTime());
//        final String message = editTextChatBox.getText().toString();
//        String buildingList = "";
//        if (!message.equals("") && currentUserId != null) {
//            for (int i = 0; i < buildingKeyList.size(); i++) {
//                String currentUserRef = AppConst.GENERAL_MESSAGES + "/" + currentUserId + "/" + buildingNameList.get(i);
//                String otherUserRef = AppConst.GENERAL_MESSAGES + "/" + buildingNameList.get(i) + "/" + currentUserId;
//                DatabaseReference userMessage = rootRef.child(AppConst.GENERAL_MESSAGES).child(currentUserRef).child(otherUserRef).push();
//                String pushId = userMessage.getKey();
//                for (int j = 0; j < buildingNameList.size(); j++)
//                    buildingList += buildingNameList.get(j) + " ";
//                messageMap.put(AppConst.MESSAGE, message);
//                messageMap.put(AppConst.BUILDINGS_LIST, buildingList);
//                messageMap.put(AppConst.SENDING_TIME, date);
//                userMessageMap.put(currentUserRef + "/" + pushId, messageMap);
//                userMessageMap.put(otherUserRef + "/" + pushId, messageMap);
//            }
//
//            final String finalBuildingList = buildingList;
//            rootRef.updateChildren(userMessageMap, new DatabaseReference.CompletionListener() {
//                @Override
//                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//
////                    rcAdapter.add(new ChatMessageModel(message, date, finalBuildingList), currentUserId, committeeName, k);
////                    k++;
////                    recyclerView.setAdapter(rcAdapter);
//                    editTextChatBox.setText("");
//
//                }
//            });
//
//        }
//    }

    private void setUpRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

    }

    @Override
    public void itemClicked(View view, int position) {

    }
}
