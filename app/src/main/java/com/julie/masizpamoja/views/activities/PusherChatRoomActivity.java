package com.julie.masizpamoja.views.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.julie.masizpamoja.R;
import com.julie.masizpamoja.adapters.PusherMessageAdapter;
import com.julie.masizpamoja.models.Chat;
import com.julie.masizpamoja.models.GetAllChats;
import com.julie.masizpamoja.models.Messsage;
import com.julie.masizpamoja.models.SentMessage;
import com.julie.masizpamoja.models.User;
import com.julie.masizpamoja.utils.SharedPreferencesManager;
import com.julie.masizpamoja.viewmodels.PusherChatRoomViewModel;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PusherChatRoomActivity extends AppCompatActivity {


    private String pusherAppKey = "2f8445741d9a9f9eb13c";
    private String pusherAppCluster = "us2";

    PusherChatRoomViewModel pusherChatRoomViewModel;
    PusherMessageAdapter pusherMessageAdapter;


    @BindView(R.id.messsageList)
    RecyclerView messageList;

    private static RecyclerView.LayoutManager layoutManager;


    @BindView(R.id.btnSend)
    Button btnSend;

    @BindView(R.id.txtMessage)
    EditText txtMessage;



    String accessToken, message;

    Chat myChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pusher_chat_room);
        ButterKnife.bind(this);

        pusherChatRoomViewModel = ViewModelProviders.of(this).get(PusherChatRoomViewModel.class);

        accessToken = SharedPreferencesManager.getInstance(this).getToken();

        myChat = new Chat();


        pusherChatRoomViewModel.getAllChats("Bearer "+ accessToken);


        pusherChatRoomViewModel.getAllChatsResponse().observe(this, getAllChatsState -> {
            if(getAllChatsState.getGetAllChats() != null){
                handleAllMessages(getAllChatsState.getGetAllChats());
            }

            if(getAllChatsState.getMessage() != null){
                handleErrorMessage(getAllChatsState.getMessage());
            }

            if(getAllChatsState.getThrowable() != null){
                handleErrorThrowable(getAllChatsState.getThrowable());
            }
        });

        pusherChatRoomViewModel.getPostMessageResponse().observe(this, sentMessageState -> {

            if(sentMessageState.getSentMessage() != null){
                handleSendMessage(sentMessageState.getSentMessage());
            }

            if(sentMessageState.getMessage() != null){
                handleErrorMessage(sentMessageState.getMessage());
            }

            if(sentMessageState.getErrorThrowable() != null){
                handleErrorThrowable(sentMessageState.getErrorThrowable());
            }
        });


        btnSend.setOnClickListener(v->{
             message = txtMessage.getText().toString().trim();
            if(!TextUtils.isEmpty(message)){


                User user = new User();
                user.setName(SharedPreferencesManager.getInstance(this).getNames());
                user.setId(Integer.parseInt(SharedPreferencesManager.getInstance(this).getUniqueid()));
                user.setEmail(SharedPreferencesManager.getInstance(this).getEmail());



                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("message", message);
                    jsonObject.put("username", user);
                    jsonObject.put("uniqueId", SharedPreferencesManager.getInstance(this).getUniqueid());
                    jsonObject.put("time",Calendar.getInstance().getTimeInMillis());
                } catch (JSONException e) {

                    e.printStackTrace();
                }


                pusherChatRoomViewModel.postMessage(message, "Bearer "+ accessToken);
                setUpPusher();
            }else {
                Log.d("Txt Box empty", "error");
                return;
            }

        });
    }

    private void setUpPusher() {
        PusherOptions options = new PusherOptions();
        options.setCluster(pusherAppCluster);

        Pusher pusher = new Pusher(pusherAppKey, options);

        Channel channel = pusher.subscribe("chat");



        channel.bind("MessageSent", event -> {

            try {

                JSONObject data = new JSONObject();

                User username;
                String message;
                String id;
                String time;

                username = (User) data.get("username");
                message = data.getString("message");
                id = data.getString("uniqueId");
                time = data.getString("time");




                runOnUiThread(() -> {

                    Messsage messsage = new Messsage();
                    messsage.setText(message);
                    messsage.setId(Integer.parseInt(id));
                    messsage.setTime(time);
                    messsage.setUser(username);

                    myChat.setMesssage(messsage);


                    pusherMessageAdapter.addMessage(myChat);
                    // scroll the RecyclerView to the last added element
                    messageList.scrollToPosition(pusherMessageAdapter.getItemCount() - 1);
                });


                pusher.connect();
            }catch (Exception e){
                Log.d("Catch error", e.getMessage());
            }
        });


//        channel.bind("MessageSent") { event ->
//                val jsonObject = JSONObject(event.toString())
//
//            val message = Message(
//                    jsonObject["message"].toString(),
//                    jsonObject["user"] as User?,
//                    jsonObject["time"].toString()
//            )
//
//
//        }
    }

    private void handleSendMessage(SentMessage sentMessage) {
        Toast.makeText(this, sentMessage.getStatus(), Toast.LENGTH_SHORT).show();
    }

    private void handleAllMessages(GetAllChats getAllChats) {
        boolean status = getAllChats.getStatus();
        if(status){
            List<Chat> chats = getAllChats.getChats();
             initView(chats);
        }
    }

    private void initView(List<Chat> chats) {
         pusherMessageAdapter = new PusherMessageAdapter(this,chats);
         messageList.setAdapter(pusherMessageAdapter);
         layoutManager = new LinearLayoutManager(this);
         messageList.setLayoutManager(layoutManager);
         messageList.setNestedScrollingEnabled(false);

    }



    private void handleErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    private void handleErrorThrowable(Throwable throwable) {

        if(throwable instanceof IOException){
            Toast.makeText(this, "You are currently Offline", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Conversion Error", Toast.LENGTH_SHORT).show();
        }

    }
}
