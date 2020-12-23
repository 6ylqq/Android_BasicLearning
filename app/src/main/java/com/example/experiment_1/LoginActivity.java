package com.example.experiment_1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author ylqq
 */
public class LoginActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //初始化preference和editor
        preferences = this.getPreferences(Context.MODE_PRIVATE);
        editor = preferences.edit();

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final CheckBox checkBox = findViewById(R.id.remember_password);
        final Button loginButton = findViewById(R.id.login);

        boolean isRemember = preferences.getBoolean("remember_password", false);
        if (isRemember) {
            String account = preferences.getString("account", "");
            String password = preferences.getString("password", "");
            usernameEditText.setText(account);
            passwordEditText.setText(password);
            checkBox.setChecked(true);
        }


        loginButton.setOnClickListener(v -> {
            String account = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            if (account.equals("admin") && password.equals("123456")) {
                if (checkBox.isChecked()) {
                    //存入到preference中去
                    editor.putBoolean("remember_password", true);
                    editor.putString("account", account);
                    editor.putString("password", password);
                } else {
                    editor.clear();
                }
                //提交修改
                editor.apply();
                //跳转到mainActivity，然后再跳回来
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "account or password is invalid", Toast.LENGTH_LONG).show();
            }
        });
        // Enables Always-on
    }

    public void TestLogin() {

    }
}