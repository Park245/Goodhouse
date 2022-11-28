package com.example.goodhouse;

import static java.lang.Thread.sleep;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    TextView id, token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        Firebase firebase = new Firebase();
        btnLogin = (Button) findViewById(R.id.loginButton);
        id = (TextView) findViewById(R.id.loginId);
        token = (TextView) findViewById(R.id.loginPassword);

        View.OnClickListener loginEvent = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idString = id.getText().toString();
                String tokenString = token.getText().toString();
                LoginData loginData = new LoginData(idString, tokenString);
                boolean loginResult = loginData.sendLoginData();
                if(loginResult) {
                    //다음화면 전환
                    //firebase.SignIn(Integer.parseInt(idString), Integer.parseInt(tokenString)); //처음 가입일 경우
                    firebase.LogIn(Integer.parseInt(idString), Integer.parseInt(tokenString)); //login
                    try
                    {
                        firebase.getInfo();
                        firebase.putNoise();
                        sleep(1000);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "아이디 혹은 토큰값이 올바르지 않습니다.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        };
        btnLogin.setOnClickListener(loginEvent);
        //dd

    }
}
class LoginData{
    String id = "";
    String token = "";

    LoginData(String sangjun19, String token){
        this.id = sangjun19;
        this.token = token;
    }

    boolean sendLoginData(){ //미구현 부분 임시아이디와 토큰으로 구현
        if(id.equals("12345")&&token.equals("108")) return true;
        else return false;
    }
}