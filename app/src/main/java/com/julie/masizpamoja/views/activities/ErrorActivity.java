package com.julie.masizpamoja.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.julie.masizpamoja.MainActivity;
import com.julie.masizpamoja.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ErrorActivity extends AppCompatActivity {

    @BindView(R.id.btn_try_again)
    Button tryAgain;

    String tag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        ButterKnife.bind(this);

        Intent tags = getIntent();
        tag = tags.getStringExtra("TAG");

        tryAgain.setOnClickListener(v -> {
            if (tag != null) {
                if (tag.equalsIgnoreCase("HomeFragment")) {

                    Intent i = new Intent(this, MainActivity.class);
                    i.putExtra("Error", "ErrorHome");
                    startActivity(i);
                }
            }

        });


    }
}
