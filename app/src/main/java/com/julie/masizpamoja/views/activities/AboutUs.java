package com.julie.masizpamoja.views.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.julie.masizpamoja.R;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutUs extends AppCompatActivity {
  String number[];
  ListView listView;

  @BindView(R.id.be_part_of_us_btn)
  CircularProgressButton bePartOfUs;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_about_us);
    ButterKnife.bind(this);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setTitle("About Us");

    bePartOfUs.setOnClickListener(v->{
      LayoutInflater inflater = getLayoutInflater();
      View alertLayout = inflater.inflate(R.layout.layout_custom_dialog, null);
      TextInputEditText name = alertLayout.findViewById(R.id.name);
      TextInputEditText email = alertLayout.findViewById(R.id.email);

      AlertDialog.Builder alert = new AlertDialog.Builder(this);
      alert.setTitle("Be Part Of Us");
      alert.setView(alertLayout);
      alert.setCancelable(false);
      alert.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

      alert.setPositiveButton("Get Started", (dialog, which) -> {
        String nname = name.getText().toString();
        String nemail = email.getText().toString();
        Toast.makeText(getBaseContext(), "Successfullly subscribed" , Toast.LENGTH_SHORT).show();
      });
      AlertDialog dialog = alert.create();
      //dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimary));

      dialog.show();
    });


    number = getResources().getStringArray(R.array.core_values);

    listView = (ListView) findViewById(R.id.listView);
    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, number);
    listView.setAdapter(adapter);

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