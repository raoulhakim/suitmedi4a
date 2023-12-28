package com.example.raoulhakim_suitmedi4a;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class HomepageActivity extends AppCompatActivity {

    private TextView txtUser, txtSelectUser;
    private Button btnChoose;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeViews();
        setButtonClickListeners();

        this.name = getIntent().getStringExtra("name");
        txtUser.setText(name);
    }

    private void initializeViews() {
        txtUser = findViewById(R.id.txtUser);
        txtSelectUser = findViewById(R.id.txtSelectUser);
        btnChoose = findViewById(R.id.btnChoose);
    }

    private void setButtonClickListeners() {
        ActivityResultLauncher<Intent> getData = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if (o.getResultCode() == Activity.RESULT_OK && o.getData()!=null) {
                            String firstname = o.getData().getStringExtra("firstname");
                            String lastname = o.getData().getStringExtra("lastname");
                            txtSelectUser.setText(firstname + " " + lastname);
                        }
                    }
                }
        );

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchThirdActivity(getData);
            }
        });
    }

    private void launchThirdActivity(ActivityResultLauncher<Intent> launcher) {
        Intent intent = new Intent(HomepageActivity.this, UserpageActivity.class);
        launcher.launch(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
