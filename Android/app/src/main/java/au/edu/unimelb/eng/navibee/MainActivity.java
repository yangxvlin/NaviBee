package au.edu.unimelb.eng.navibee;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import au.edu.unimelb.eng.navibee.navigation.DestinationsActivity;
import au.edu.unimelb.eng.navibee.social.ConversationManager;
import au.edu.unimelb.eng.navibee.social.FriendActivity;
import au.edu.unimelb.eng.navibee.social.FriendManager;
import au.edu.unimelb.eng.navibee.utils.NetworkImageHelper;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private PopupAdapter mAdapter;
    private ListPopupWindow mPopupWindow;
    private ImageView mOverflowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupActionBar();

        firestoreTimestamp();

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        NetworkImageHelper.loadRoundImage(
                mOverflowButton,
                Objects.requireNonNull(mUser.getPhotoUrl()).toString());

        findViewById(R.id.landing_sos_btn).setOnClickListener(this);
        findViewById(R.id.landing_social_btn).setOnClickListener(this);

        FriendManager.init();
        ConversationManager.init();
        setFCMToken();

        // Setup popup list view from overflow menu.
        mPopupWindow = new ListPopupWindow(this);
        mPopupWindow.setAnimationStyle(R.style.AppTheme_OverflowMenuAnimation);
        String[] mPopupWindowItems = new String[]{
                "%ROW_FOR_USER_PROFILE%",
                getResources().getString(R.string.action_settings),
                getResources().getString(R.string.action_log_out)
        };
        mAdapter = new PopupAdapter(mPopupWindowItems, menuClickListeners);

        findViewById(R.id.main_activity_layout).post(() -> {
            mPopupWindow.setModal(true);
            mPopupWindow.setAnchorView(mOverflowButton);
            mPopupWindow.setAdapter(mAdapter);
            mPopupWindow.setWidth(dpToPx(getResources().getInteger(R.integer.popup_menu_main_width)));
            mPopupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);
            mPopupWindow.setDropDownGravity(Gravity.END);
            mPopupWindow.setVerticalOffset(-mOverflowButton.getLayoutParams().height);
        });
    }

    // The behavior for java.util.Date objects stored in Firestore is going to chang
    private void firestoreTimestamp() {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        FirebaseFirestore.getInstance().setFirestoreSettings(settings);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.landing_events_btn:
                startActivity(new Intent(this, EventActivity.class));
                break;

            case R.id.landing_sos_btn:
                startActivity(new Intent(getApplicationContext(), SosActivity.class));
                break;

            case R.id.landing_social_btn:
                startActivity(new Intent(this, FriendActivity.class));
                break;
        }
    }

    public void startNavigationActivity(View view) {
        startActivity(new Intent(this, DestinationsActivity.class));
    }

    private View.OnClickListener[] menuClickListeners = new View.OnClickListener[] {
            null,
            // Settings
            (View v) -> {
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                mPopupWindow.dismiss();
            },
            // Log out
            (View v) -> logOut()
    };

    private void setFCMToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        return;
                    }
                    // Get Instance ID token
                    String token = task.getResult().getToken();
                    String uid = Objects.requireNonNull(FirebaseAuth.getInstance()
                            .getCurrentUser()).getUid();

                    Map<String, Object> docData = new HashMap<>();
                    docData.put("uid", uid);
                    docData.put("lastSeen", new Timestamp(new Date()));
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("fcmTokens").document(token).set(docData);

                });
    }

    private void logOut() {
        // sign out firebase
        FirebaseAuth.getInstance().signOut();

        // sign out google login
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignIn.getClient(this, gso).signOut();

        // reset token to prevent further messages
        try {
            FirebaseInstanceId.getInstance().deleteInstanceId();
        } catch (Exception e) {
            Timber.e(e, "Error occurred while resetting tokens.");
        }


        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        startActivity(intent);
        this.finish();
    }

    private int dpToPx(int dp) {
        float density = getApplicationContext().getResources()
                .getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    private void setupActionBar() {
        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowCustomEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayShowHomeEnabled(false);
        ab.setDisplayHomeAsUpEnabled(false);
        ab.setHomeButtonEnabled(false);
        ab.setCustomView(R.layout.action_bar_main_activity);
        mOverflowButton = ab.getCustomView().findViewById(R.id.action_bar_main_activity_user_icon);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mOverflowButton.setClipToOutline(true);
        }

        mOverflowButton.setOnClickListener(v -> mPopupWindow.show());
    }

    private class PopupAdapter extends BaseAdapter {
        private String[] menuItems;
        private View.OnClickListener[] listeners;

        PopupAdapter(String[] menuItems, View.OnClickListener[] listeners) {
            this.menuItems = menuItems;
            this.listeners = listeners;
        }

        @Override
        public int getCount() {
            return menuItems.length;
        }

        @Override
        public Object getItem(int position) {
            return menuItems[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (position == 0) {
                if (!(convertView instanceof ConstraintLayout)) {
                    holder = new ViewHolder();
                    convertView = getLayoutInflater().inflate(R.layout.popup_menu_main_profile, null);
                    holder.menuItemView = convertView;
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                ((TextView) holder.menuItemView.findViewById(R.id.popup_menu_main_profile_name))
                        .setText(mUser.getDisplayName());
                ((TextView) holder.menuItemView.findViewById(R.id.popup_menu_main_profile_secondary))
                        .setText(mUser.getEmail());
                ImageView profile = holder.menuItemView.findViewById(R.id.popup_menu_main_profile_picture);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    profile.setClipToOutline(true);
                }
                NetworkImageHelper.loadRoundImage(profile,
                        Objects.requireNonNull(mUser.getPhotoUrl()).toString());
            } else {
                if (!(convertView instanceof LinearLayout)) {
                    holder = new ViewHolder();
                    convertView = getLayoutInflater().inflate(R.layout.popup_menu_button, null);
                    holder.menuItemView = convertView;
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                TextView text = holder.menuItemView.findViewById(R.id.popup_menu_button_text);
                text.setText(menuItems[position]);
                holder.menuItemView.setOnClickListener(listeners[position]);
            }
            return convertView;
        }

        private class ViewHolder {
            View menuItemView;
        }

    }
}

