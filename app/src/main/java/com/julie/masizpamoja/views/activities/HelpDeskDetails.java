package com.julie.masizpamoja.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.julie.masizpamoja.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HelpDeskDetails extends AppCompatActivity {


    @BindView(R.id.helpDeskDetails)
    TextView helpDeskDetails;



//    @BindView(R.id.more_info)
//    FloatingActionButton moreInfo;



    String mHelpDeskTitle,mHelpDeskDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_desk_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Need Help");

        ButterKnife.bind(this);


        Intent detailsIntent = getIntent();
        mHelpDeskDetails = detailsIntent.getStringExtra("helpDeskDetails");
        mHelpDeskTitle = detailsIntent.getStringExtra("helpDeskTitle");

        getSupportActionBar().setTitle(mHelpDeskTitle);
        helpDeskDetails.setText(mHelpDeskDetails);




//      moreInfo.setOnClickListener(v->{
//          startActivity(new Intent(HelpDeskDetails.this,ContactsActivity.class));
//      });


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            super.onBackPressed();
        }


        return super.onOptionsItemSelected(item);
    }
}
