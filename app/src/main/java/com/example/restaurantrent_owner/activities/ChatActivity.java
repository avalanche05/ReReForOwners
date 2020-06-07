package com.example.restaurantrent_owner.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurantrent_owner.Message;
import com.example.restaurantrent_owner.R;
import com.example.restaurantrent_owner.Server;
import com.example.restaurantrent_owner.adapters.MessageAdapter;

import java.util.ArrayList;

// activity для чата по заказу
public class ChatActivity extends AppCompatActivity {

    MessageAdapter messageAdapter;
    private ListView messageList;
    private EditText messageEditText;
    // массив сообщений по конкретному заказу(заполняется в Server)
    public static ArrayList<Message> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messageList = findViewById(R.id.messagesListView);
        messageEditText = findViewById(R.id.messageField);

        // создаём адаптер и присваеваем его messageList
        messageAdapter = new MessageAdapter(this, messages);
        messageList.setAdapter(messageAdapter);



    }

    // метод отслеживающий нажатие на кнопку отправки сообщения
    public void sendMessageButton(View view) {
        String message = messageEditText.getText().toString();
        // проверяем не пустое ли сообщение
        if (!message.isEmpty()) {
            // отправляем сообщение
            Server.sendMessage(new Message(getIntent().getLongExtra("idRent", -1), true, message),messageAdapter);
            messageEditText.setText("");

        }
    }

}
