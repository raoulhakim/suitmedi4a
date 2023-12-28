package com.example.raoulhakim_suitmedi4a;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText inputName, inputPalindrome;
    private Button btnCheck, btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        initializeViews();

        setButtonClickListeners();
    }

    private void initializeViews() {
        inputName = findViewById(R.id.inputName);
        inputPalindrome = findViewById(R.id.inputPalindrome);
        btnCheck = findViewById(R.id.btnCheck);
        btnNext = findViewById(R.id.btnNext);
    }

    private void setButtonClickListeners() {
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPalindrome();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToNextActivity();
            }
        });
    }

    private void checkPalindrome() {
        String input = inputPalindrome.getText().toString();
        boolean isPalindrome = isPalindrome(input);

        showPalindromeDialog(isPalindrome);
    }

    private void showPalindromeDialog(boolean isPalindrome) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");

        if (isPalindrome) {
            builder.setMessage("isPalindrome");
        } else {
            builder.setMessage("not palindrome");
        }

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Close dialog when OK button is pressed
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void navigateToNextActivity() {
        String name = inputName.getText().toString();
        Intent intent = new Intent(MainActivity.this, HomepageActivity.class);
        intent.putExtra("name", name);
        startActivity(intent);
    }

    private boolean isPalindrome(String str) {
        if (str.isEmpty()) {
            return false;
        }
        String text = str.replaceAll("\\s+", "").toLowerCase();
        StringBuilder reversed = new StringBuilder(text).reverse();
        String reversedStr = reversed.toString();

        return text.equals(reversedStr);
    }
}
