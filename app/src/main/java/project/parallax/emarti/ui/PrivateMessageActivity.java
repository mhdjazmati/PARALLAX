package project.parallax.emarti.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;

import project.parallax.emarti.R;
import project.parallax.emarti.logic.presenters.PrivateMessagePresenter;

public class PrivateMessageActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button sendMessageButton;
    EditText editTextChatBox;
    PrivateMessagePresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recyclerView = findViewById(R.id.recycler_view_message_list);
        sendMessageButton = findViewById(R.id.button_chat_box_send);
        editTextChatBox = findViewById(R.id.edit_text_chat_box);
        presenter = new PrivateMessagePresenter(this,recyclerView,sendMessageButton,editTextChatBox);
    }

}
