package au.edu.unimelb.eng.navibee.social;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.acl.Group;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import au.edu.unimelb.eng.navibee.R;
import au.edu.unimelb.eng.navibee.utils.NetworkImageHelper;

public class GroupDetailActivity extends AppCompatActivity {
    private ConversationManager cm = ConversationManager.getInstance();
    private GroupConversation conv;
    private ArrayList<String> memberList;
    private String convId;
    private String creator;
    private String createDate;

    public static class MemberAdapter extends BaseAdapter{
        private Context context;
        private ArrayList<String> members;

        public MemberAdapter(Context context, ArrayList<String> members) {
            this.context = context;
            this.members = members;

        }

        public int getCount() {
            return members.size();
        }

        public Object getItem(int position) {
            return members.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            String member = members.get(position);
            if (convertView == null){
                LayoutInflater inflater = LayoutInflater.from(context);
                view = inflater.inflate(R.layout.group_chat_member_item, parent, false);
                TextView viewName = (TextView) view.findViewById(R.id.group_member_name);
                ImageView viewIcon = (ImageView) view.findViewById(R.id.group_member_icon);
                UserInfoManager.getInstance().getUserInfo(member, userInfo -> {
                    viewName.setText(userInfo.getName());
                    NetworkImageHelper.loadImage(viewIcon, userInfo.getPhotoUrl());
                });
            }
            else{
                view = convertView;
            }
            view.setId(position);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!member.equals(ConversationManager.getInstance().getUid())){
                        Intent intent = new Intent(context, FriendDetail.class);
                        intent.putExtra("FRIEND_ID", member);
                        context.startActivity(intent);
                    }
                }
            });
            return view;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);

        convId = getIntent().getStringExtra("CONV_ID");
        conv = (GroupConversation) cm.getConversation(convId);
        creator = conv.getCreator();
        Date createDate = conv.getCreateDate();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm");
        this.createDate = dateFormat.format(createDate);
        memberList = new ArrayList<>(conv.getMembers());

        GridView gridview = (GridView) findViewById(R.id.activity_group_detail_members);
        gridview.setAdapter(new MemberAdapter(this, memberList));

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_group_detail_inviteFriend_button:
                break;
            case R.id.activity_group_detail_LeaveGroup_button:
                break;
        }
    }
}
