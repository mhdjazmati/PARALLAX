package project.parallax.emarti.logic.presenters;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import project.parallax.emarti.logic.adapters.ChatMessageAdapter;
import project.parallax.emarti.logic.models.ChatMessageModel;
import project.parallax.emarti.ui.PrivateMessageActivity;
import project.parallax.emarti.utility.AppConst;
import project.parallax.emarti.utility.PreferenceManager;




public class PrivateMessagePresenter implements ChatMessageAdapter.ChatClickListener {


    private PrivateMessageActivity activity;
    private RecyclerView recyclerView;
    private Button sendMessageButton;
    private EditText editTextChatBox;
    private DatabaseReference rootRef;
    private ArrayList<ChatMessageModel> rowListItems;
    private Map userMessageMap;
    private Map messageRecivedMap;
    private Map messageSentMap;
    private Map buildingsMap;
    private int k = 0;
    private String currentUserId;
    private String buildingName;
    private String date;
    private String committeeName;
    private ChatMessageAdapter rcAdapter;
    private String otherUserId;
    private String messageKey;
    private boolean secondTime;
    private String message;
    private Map storedMessage;


    public PrivateMessagePresenter(PrivateMessageActivity privateMessageActivity, RecyclerView recyclerView,
                                   Button sendMessageButton, EditText editTextChatBox) {
        this.activity = privateMessageActivity;
        this.recyclerView = recyclerView;
        this.sendMessageButton = sendMessageButton;
        this.editTextChatBox = editTextChatBox;
        rootRef = FirebaseDatabase.getInstance().getReference();
        rowListItems = new ArrayList<>();
        currentUserId = PreferenceManager.getInstance(activity).getString(AppConst.ID, null);
        otherUserId = activity.getIntent().getStringExtra(AppConst.ID);
        committeeName = activity.getIntent().getStringExtra(AppConst.USER_NAME);
        storedMessage = new HashMap();
        rcAdapter = new ChatMessageAdapter(activity, rowListItems,false);

        rootRef.keepSynced(true);
        setUpRecyclerView();
        setUpActions();
        rcAdapter.setClickListener(this);
        receivePrivateMessage();
//        recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount()-1);
//        messageMap = new HashMap();
//        userMessageMap = new HashMap();
//        buildingsMap = new HashMap();
//        getMyMessage();



    }

    private void setUpRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

    }

    private void setUpActions() {
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageSentMap = new HashMap();
                messageRecivedMap = new HashMap();
                userMessageMap = new HashMap();
                buildingsMap = new HashMap();
                sendMassage();
                editTextChatBox.setText("");



            }
        });
    }

    private void sendMassage() {
        DateFormat df = new SimpleDateFormat("h:mm a");
        date = df.format(Calendar.getInstance().getTime());
        final String message = editTextChatBox.getText().toString();
        if (!message.equals("") && currentUserId != null) {
            final String currentUserRef = AppConst.PRIVATE_MESSAGE + "/" + currentUserId + "/" + otherUserId;
            String otherUserRef = AppConst.PRIVATE_MESSAGE + "/" + otherUserId + "/" + currentUserId;
            DatabaseReference userMessage = rootRef.child(AppConst.PRIVATE_MESSAGE).child(currentUserRef).child(otherUserRef).push();
            messageKey = userMessage.getKey();
            messageSentMap.put(AppConst.MESSAGE, currentUserId+message);
            messageSentMap.put(AppConst.SENDING_TIME, date);
            messageRecivedMap.put(AppConst.MESSAGE,message);
            messageRecivedMap.put(AppConst.SENDING_TIME,date);
            userMessageMap.put(currentUserRef + "/" + messageKey, messageSentMap);
            userMessageMap.put(otherUserRef + "/" + messageKey, messageRecivedMap);

            rootRef.updateChildren(userMessageMap, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                    rcAdapter.add(new ChatMessageModel(message, currentUserRef, date), k, committeeName);

                }
            });


        }
    }

    private void receivePrivateMessage() {


        rootRef.child(AppConst.PRIVATE_MESSAGE).child(currentUserId).child(otherUserId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatMessageModel model = dataSnapshot.getValue(ChatMessageModel.class);
                if (model.getMessage().startsWith(currentUserId)) {
                    rcAdapter.add(new ChatMessageModel(model.getMessage().substring(otherUserId.length(),model.getMessage().length()), currentUserId, model.getSendingTime()), k, committeeName);
//                    rcAdapter.add(model, currentUserId, committeeName, k);
                }

                else

                rcAdapter.add(new ChatMessageModel(model.getMessage(), otherUserId, model.getSendingTime()), k, committeeName);
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

    @Override
    public void itemClicked(View view, int position) {

    }
}
