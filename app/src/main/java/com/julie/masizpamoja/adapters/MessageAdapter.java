package com.julie.masizpamoja.adapters;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.julie.masizpamoja.R;
import com.julie.masizpamoja.models.SavedMessage;
import com.julie.masizpamoja.views.activities.ChatRoomActivity;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends ArrayAdapter<SavedMessage> {
    private List<SavedMessage> messages = new ArrayList<>();

    public MessageAdapter(Context context, int resource, List<SavedMessage> messages) {
        super(context, resource, messages);
        this.messages = messages;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i(ChatRoomActivity.TAG, "getView:");

        SavedMessage message = getItem(position);
       // SavedMessage message = messages.get(position);

        if (!messages.isEmpty() && messages != null) {

            if (TextUtils.isEmpty(message.getMessage())) {


                convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.user_connected, parent, false);

                TextView messageText = convertView.findViewById(R.id.message_body);

                Log.i(ChatRoomActivity.TAG, "getView: is empty ");
                String userConnected = message.getUsername();
                messageText.setText(userConnected);

            } else if(message.getUniqueId() != null) {
                if (message.getUniqueId().equals(ChatRoomActivity.uniqueId)) {
                    Log.i(ChatRoomActivity.TAG, "getView: " + message.getUniqueId() + " " + ChatRoomActivity.uniqueId);


                    convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.my_message, parent, false);
                    TextView messageText = convertView.findViewById(R.id.message_body);
                    messageText.setText(message.getMessage());

                } else {
                    Log.i(ChatRoomActivity.TAG, "getView: is not empty");

                    convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.their_message, parent, false);

                    TextView messageText = convertView.findViewById(R.id.message_body);
                    TextView usernameText = (TextView) convertView.findViewById(R.id.name);

                    messageText.setVisibility(View.VISIBLE);
                    usernameText.setVisibility(View.VISIBLE);

                    messageText.setText(message.getMessage());
                    usernameText.setText(message.getUsername());
                }
            }

        }

        return convertView;
    }
}
