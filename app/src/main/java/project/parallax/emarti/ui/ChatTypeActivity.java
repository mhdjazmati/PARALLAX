package project.parallax.emarti.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import project.parallax.emarti.R;
import project.parallax.emarti.utility.AppConst;

public class ChatTypeActivity extends AppCompatActivity {

    private Button privateChatButton;
    private Button generalChatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_type);
        privateChatButton = findViewById(R.id.private_chat_button);
        generalChatButton = findViewById(R.id.general_chat_button);

        privateChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BuildingCommitteeActivity.class);
                intent.putExtra(AppConst.PRIVATE_MESSAGE, true);
                startActivity(intent);

            }
        });

        generalChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BuildingCommitteeActivity.class);
                intent.putExtra(AppConst.PRIVATE_MESSAGE, false);
                startActivity(intent);
            }
        });
    }
}
