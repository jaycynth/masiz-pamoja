package com.julie.masizpamoja.views.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.julie.masizpamoja.R;
import com.julie.masizpamoja.adapters.ChatRoomAdapter;
import com.julie.masizpamoja.models.SavedMessage;
import com.julie.masizpamoja.utils.SharedPreferencesManager;
import com.julie.masizpamoja.viewmodels.SavedMessageViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import static com.julie.masizpamoja.utils.Constants.CHAT_SERVER_URL;

public class ChatRoomActivity extends AppCompatActivity {

    private static RecyclerView.LayoutManager layoutManager;


    private EditText textField;
    private ImageButton sendButton;

    public static final String TAG = "MainActivity";
    public static String uniqueId;

    private String Username;

    private Boolean hasConnection = false;
    private RecyclerView messageListView;
    private ChatRoomAdapter messageAdapter;

    private Thread thread2;
    private boolean startTyping = false;
    private int time = 2;

    SavedMessageViewModel savedMessageViewModel;

    List<SavedMessage> messageFormatList = new ArrayList<>();


    private Socket mSocket;

    {
        try {
            mSocket = IO.socket(CHAT_SERVER_URL);
        } catch (URISyntaxException e) {
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i(TAG, "handleMessage: typing stopped " + startTyping);
            if (time == 0) {
                setTitle("Chat Room");
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                Log.i(TAG, "handleMessage: typing stopped time is " + time);
                startTyping = false;
                time = 2;
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        getSupportActionBar().setTitle("Chat Room");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textField = findViewById(R.id.textField);
        sendButton = findViewById(R.id.sendButton);
        messageListView = findViewById(R.id.messageListView);

        Username = SharedPreferencesManager.getInstance(this).getNames();


        if (savedInstanceState != null) {
            hasConnection = savedInstanceState.getBoolean("hasConnection");
        }

        if (!hasConnection) {
            mSocket.connect();
            mSocket.on("connect user", onNewUser);
            mSocket.on("chat message", onNewMessage);
            mSocket.on("on typing", onTyping);

            JSONObject userId = new JSONObject();
            try {
                userId.put("username", Username + " Connected");
                mSocket.emit("connect user", userId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Log.i(TAG, "onCreate: " + hasConnection);
        hasConnection = true;
        Log.i(TAG, "onCreate: " + Username + " " + "Connected");


        savedMessageViewModel = ViewModelProviders.of(this).get(SavedMessageViewModel.class);

        if (SharedPreferencesManager.getInstance(this).getUniqueid() == null) {
            uniqueId = UUID.randomUUID().toString();
            SharedPreferencesManager.getInstance(this).saveUserId(uniqueId);
        } else {
            uniqueId = SharedPreferencesManager.getInstance(this).getUniqueid();
        }

        Log.i(TAG, "onCreate: " + uniqueId);


        savedMessageViewModel.getListLiveData().observe(this, savedMessages -> {
            if (savedMessages != null && !savedMessages.isEmpty()) {
                messageFormatList = savedMessages;
                initView(messageFormatList);

            }
        });



        sendButton.setOnClickListener(this::sendMessage);

        onTypeButtonEnable();

    }

    private void initView(List<SavedMessage> messageFormatList) {
        messageAdapter = new ChatRoomAdapter(this, messageFormatList);
        messageListView.setAdapter(messageAdapter);
        layoutManager = new LinearLayoutManager(this);
        messageListView.setLayoutManager(layoutManager);
        messageListView.setNestedScrollingEnabled(false);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("hasConnection", hasConnection);
    }

    public void onTypeButtonEnable() {
        textField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                JSONObject onTyping = new JSONObject();
                try {
                    onTyping.put("typing", true);
                    onTyping.put("username", Username);
                    onTyping.put("uniqueId", uniqueId);
                    mSocket.emit("on typing", onTyping);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (charSequence.toString().trim().length() > 0) {
                    sendButton.setEnabled(true);
                } else {
                    sendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "run: ");
                    Log.i(TAG, "run: " + args.length);
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    String message;
                    String id;
                    String time;
                    try {
                        username = data.getString("username");
                        message = data.getString("message");
                        id = data.getString("uniqueId");
                        time = data.getString("time");


                        addMessage(username, message, id,time);
                        Log.i(TAG, "run: " + username + message + id + time);




//                        SavedMessage format = new SavedMessage(id, username, message,time);
//                        Log.i(TAG, "run:4 ");
//                        messageFormatList.add(format);




                        Log.i(TAG, "run:5 ");

                    } catch (Exception e) {
                        return;
                    }
                }
            });
        }
    };

    Emitter.Listener onNewUser = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int length = args.length;

                    if (length == 0) {
                        return;
                    }
                    //Here i'm getting weird error///////run :1 and run: 0
                    Log.i(TAG, "run: ");
                    Log.i(TAG, "run: " + args.length);
                    String username = args[0].toString();
                    try {
                        JSONObject object = new JSONObject(username);
                        username = object.getString("username");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    SavedMessage format = new SavedMessage(null, username, null,null);
                    messageFormatList.add(format);
                    initView(messageFormatList);

                    Log.i(TAG, "run: " + username);
                }
            });
        }
    };


    Emitter.Listener onTyping = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    Log.i(TAG, "run: " + args[0]);
                    try {
                        Boolean typingOrNot = data.getBoolean("typing");
                        String userName = data.getString("username") + " is Typing......";
                        String id = data.getString("uniqueId");

                        if (id.equals(uniqueId)) {
                            typingOrNot = false;
                        } else {
                            setTitle(userName);
                        }

                        if (typingOrNot) {

                            if (!startTyping) {
                                startTyping = true;
                                thread2 = new Thread(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                                while (time > 0) {
                                                    synchronized (this) {
                                                        try {
                                                            wait(1000);

                                                            Log.i(TAG, "run: typing " + time);
                                                        } catch (InterruptedException e) {
                                                            e.printStackTrace();
                                                        }
                                                        time--;
                                                    }
                                                    handler2.sendEmptyMessage(0);
                                                }

                                            }
                                        }
                                );
                                thread2.start();
                            } else {
                                time = 2;
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private void addMessage(String username, String message, String uniqueId, String time) {

        SavedMessage savedMessage = new SavedMessage();
        savedMessage.setUsername(username);
        savedMessage.setMessage(message);
        savedMessage.setUniqueId(uniqueId);
        savedMessage.setCreatedAt(time);
        savedMessageViewModel.saveMessage(savedMessage);

    }

    public void sendMessage(View view) {
        Log.i(TAG, "sendMessage: ");

        String message = textField.getText().toString().trim();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        String t = sdf.format(c.getTime());
        
        if (TextUtils.isEmpty(message)) {
            Log.i(TAG, "sendMessage:2 ");
            return;
        }
        textField.setText("");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("message", message);
            jsonObject.put("username", Username);
            jsonObject.put("uniqueId", uniqueId);
            jsonObject.put("time",t);
        } catch (JSONException e) {

            e.printStackTrace();
        }
        Log.i(TAG, "sendMessage: 1" + mSocket.emit("chat message", jsonObject));
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (!hasConnection) {
            mSocket.connect();
            mSocket.on("connect user", onNewUser);
            mSocket.on("chat message", onNewMessage);
            mSocket.on("on typing", onTyping);

            JSONObject userId = new JSONObject();
            try {
                userId.put("username", Username + " Connected");
                mSocket.emit("connect user", userId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Log.i(TAG, "onCreate: " + hasConnection);
        hasConnection = true;
        Log.i(TAG, "onCreate: " + Username + " " + "Connected");



        savedMessageViewModel.getListLiveData().observe(this, savedMessages -> {
            if (savedMessages != null && !savedMessages.isEmpty()) {
                messageFormatList = savedMessages;
                initView(messageFormatList);

            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();



        if (isFinishing()) {
            Log.i(TAG, "onDestroy: ");

            JSONObject userId = new JSONObject();
            try {
                userId.put("username", Username + " DisConnected");
                mSocket.emit("connect user", userId);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mSocket.disconnect();
            mSocket.off("chat message", onNewMessage);
            mSocket.off("connect user", onNewUser);
            mSocket.off("on typing", onTyping);
//            Username = "";

        } else {
            Log.i(TAG, "onDestroy: is rotating.....");
        }

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}

    
