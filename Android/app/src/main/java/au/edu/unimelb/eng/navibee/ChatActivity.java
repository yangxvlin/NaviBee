package au.edu.unimelb.eng.navibee;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import au.edu.unimelb.eng.navibee.Social.Conversation;
import au.edu.unimelb.eng.navibee.Social.ConversationManager;
import au.edu.unimelb.eng.navibee.Social.FriendManager;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener{

    private Conversation conversation;
    private String targetUID;

    private RecyclerView chatRecyclerView;
    private RecyclerView.Adapter chatAdapter;
    private RecyclerView.LayoutManager chatLayoutManager;

    private int currentMsgCount = 0;

    BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            loadNewMsg();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        targetUID = getIntent().getStringExtra("TARGET_USER_ID");
        conversation = ConversationManager.getInstance().getConversationByUID(targetUID);


        chatRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_chat);
        chatRecyclerView.setHasFixedSize(true);
        chatLayoutManager = new LinearLayoutManager(this);
        chatRecyclerView.setLayoutManager(chatLayoutManager);
        chatAdapter = new ChatAdapter(conversation.getCurrentMessageList());
        chatRecyclerView.setAdapter(chatAdapter);

        currentMsgCount = conversation.getMessageCount();

        IntentFilter intFilt = new IntentFilter(Conversation.BROADCAST_NEW_MESSAGE);
        registerReceiver(br, intFilt);
    }

    private void loadNewMsg() {
        while (currentMsgCount < conversation.getMessageCount()) {
            ((ChatAdapter) chatAdapter).addMessage(conversation.getMessage(currentMsgCount));
            currentMsgCount += 1;
        }
    }

    @Override
    public void onClick(View view) {

    }


    public static class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MessageViewHolder> {


        public static class MessageViewHolder extends RecyclerView.ViewHolder{

            public MessageViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }

        private ArrayList<Conversation.Message> mDataset;

        public void addMessage(Conversation.Message message) {
            mDataset.add(message);
            this.notifyItemInserted(mDataset.size()-1);
        }


        // Provide a suitable constructor (depends on the kind of dataset)
        public ChatAdapter(ArrayList<Conversation.Message> myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MessageViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_sender_message, parent, false);
            MessageViewHolder vh = new MessageViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(MessageViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            ((TextView) holder.itemView.findViewById(R.id.message_text)).setText(mDataset.get(position).getData());

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }
}
