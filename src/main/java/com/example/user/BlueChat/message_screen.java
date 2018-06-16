package com.example.user.BlueChat;

import android.media.MediaPlayer;
import android.view.View.OnKeyListener;
import android.view.View;
import android.view.KeyEvent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.bluetooth_communication.R;

import java.nio.charset.Charset;


public class message_screen extends AppCompatActivity {

    Button btnSend;
    EditText etSend;
    public static boolean newMessage = false;
    public MessageListAdapter nMessageListAdapter;
    public TextView tv_typing;
    public ListView lv_messages;
    private MediaPlayer sound_send;
    private MediaPlayer sound_receive;

    public static boolean isTyping = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messaging_room);
        tv_typing = (TextView) findViewById(R.id.tv_typing);
        lv_messages = (ListView) findViewById(R.id.listview_message);
        btnSend = (Button) findViewById(R.id.btnSend2);
        etSend = (EditText) findViewById(R.id.editText2);
        sound_receive = MediaPlayer.create(this, R.raw.sound_receive);
        sound_send = MediaPlayer.create(this, R.raw.sound_send);

        etSend.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                //If the keyevent is a key-down event on the "enter" button
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if (!etSend.getText().toString().equals("")) {
                        String string1 = etSend.getText().toString() + "neuutron" + MainActivity.username;
                        byte[] bytes = string1.getBytes(Charset.defaultCharset());
                        for (int i = 0; i < MainActivity.mBluetoothConnection.size(); i++) {
                            MainActivity.mBluetoothConnection.get(i).write(bytes, i);

                        }
                        etSend.setText("");
                    }

                    return true;
                }
                return false;
            }
        });

        CountDownTimer timer = new CountDownTimer(2100000000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (MainActivity.nSender.size() == MainActivity.nMessages.size() && newMessage == true) {
                    sound_receive.start();
                    nMessageListAdapter = new MessageListAdapter(getApplicationContext(), R.layout.message_adapter_view, MainActivity.nMessages, MainActivity.nSender);

                    lv_messages.setAdapter(nMessageListAdapter);
                    lv_messages.setSelection(nMessageListAdapter.getCount());
                    newMessage = false;
                }
                if (etSend.getText().toString().equals("")) {
                    byte[] bytes1 = "hi".getBytes(Charset.defaultCharset());
                    for (int i = 0; i < MainActivity.mBluetoothConnection.size(); i++)
                        MainActivity.mBluetoothConnection.get(i).write(bytes1, i);
                } else {
                    byte[] bytes1 = "hello".getBytes(Charset.defaultCharset());
                        for (int i = 0; i < MainActivity.mBluetoothConnection.size(); i++)
                            MainActivity.mBluetoothConnection.get(i).write(bytes1, i);
                }
                if (isTyping == false) {
                    tv_typing.setVisibility(View.INVISIBLE);
                } else if (isTyping == true) {
                    tv_typing.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFinish() {

            }
        }.start();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etSend.getText().toString().equals("")) {
                    String string1 = etSend.getText().toString() + "neuutron" + MainActivity.username;
                    byte[] bytes = string1.getBytes(Charset.defaultCharset());
                    for(int i = 0; i < MainActivity.mBluetoothConnection.size(); i ++) {
                        MainActivity.mBluetoothConnection.get(i).write(bytes, i);
                    }
                    etSend.setText("");
                }
            }
        });


    }

    @Override
    public void onBackPressed () {
    }
}
