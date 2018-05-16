package project.parallax.emarti.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import project.parallax.emarti.R;
import project.parallax.emarti.logic.presenters.ChatPresenter;

public class GeneralChatActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button sendMessageButton;
    EditText editTextChatBox;
    ChatPresenter presenter;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recyclerView = findViewById(R.id.recycler_view_message_list);
        sendMessageButton = findViewById(R.id.button_chat_box_send);
        editTextChatBox = findViewById(R.id.edit_text_chat_box);
        linearLayout = findViewById(R.id.layout_chat_box);
        presenter = new ChatPresenter(this,recyclerView,sendMessageButton,editTextChatBox,linearLayout);
    }
}
