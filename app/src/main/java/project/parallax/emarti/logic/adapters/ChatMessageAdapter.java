package project.parallax.emarti.logic.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import project.parallax.emarti.R;
import project.parallax.emarti.logic.models.ChatMessageModel;
import project.parallax.emarti.utility.AppConst;
import project.parallax.emarti.utility.PreferenceManager;

/**
 * Created by Mohammad Sommakia on 4/21/2018.
 */

public class ChatMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<ChatMessageModel> messageList;
    ChatClickListener clickListener;
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    boolean visibleBuildings = false;
    private String committeeName;
    int currentMessage;
    private String committeeId;

    public ChatMessageAdapter(Context context, List<ChatMessageModel> messageList, boolean visibleBuildings) {
        this.context = context;
        this.messageList = messageList;
        this.visibleBuildings = visibleBuildings;
    }

    private List<ChatMessageModel> getmessageList()
    {
        return messageList;
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        ChatMessageModel message = messageList.get(position);

        if (message.getSenderId().equals(PreferenceManager.getInstance(context).getString(AppConst.ID, null))) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_sent, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_received, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ChatMessageModel message = messageList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);

        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public void setClickListener(ChatClickListener clickListener) {
        this.clickListener = clickListener;
    }



    public void add(ChatMessageModel chatMessageModel, int k, String committeeName) {
        this.committeeName = committeeName;
        messageList.add(chatMessageModel);
        currentMessage = k;
        notifyItemInserted(k);
    }

    class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;
        ImageView profileImage;


        public ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.text_message_body);
            timeText = itemView.findViewById(R.id.text_message_time);
            nameText = itemView.findViewById(R.id.text_message_name);
            profileImage = itemView.findViewById(R.id.image_message_profile);
        }

        void bind(ChatMessageModel message) {
            messageText.setText(message.getMessage());
            // Format the stored timestamp into a readable String using method.
            timeText.setText(message.getSendingTime());
            nameText.setText(committeeName);


            // Insert the profile image from the URL into the ImageView.
//            Utils.displayRoundImageFromUrl(context, message.getSender().getProfileUrl(), profileImage);
        }
    }

    public class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, visibility, receivedBuildings;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.text_message_body);
            timeText = itemView.findViewById(R.id.text_message_time);
            visibility = itemView.findViewById(R.id.visibility);
            receivedBuildings = itemView.findViewById(R.id.received_buildings);
            if (!visibleBuildings) {
                visibility.setVisibility(View.GONE);
                receivedBuildings.setVisibility(View.GONE);
            }
        }

        void bind(ChatMessageModel message) {
            messageText.setText(message.getMessage());

            // Format the stored timestamp into a readable String using method.
            timeText.setText(message.getSendingTime());
            receivedBuildings.setText(message.getBuildingsList());
            if (visibleBuildings)
            {
                receivedBuildings.setText(message.getBuildingsList());
            }

        }
    }

    public interface ChatClickListener {
        public void itemClicked(View view, int position);
    }
}
