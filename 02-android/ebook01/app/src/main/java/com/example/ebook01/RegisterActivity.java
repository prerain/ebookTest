package com.example.ebook01;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ebook01.utils.Httpurl;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    String baseUrl = Httpurl.getUrl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);

        EditText usernameEt = findViewById(R.id.register_username);
        EditText passwordEt =findViewById(R.id.register_password);
        EditText passwordEt2 = findViewById(R.id.register_password2);
        Button registerBtn = findViewById(R.id.register_btn_register);
        Button tologinBtn = findViewById(R.id.register_btn_login);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEt.getText().toString();
                String password = passwordEt.getText().toString();
                String password2 = passwordEt2.getText().toString();
                String userState = "3";
                int userPoint = 1000;
                if (judgeifEmpty(username, password, password2)) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String json = "{\n" +
                                    "    \"userName\":\"" + username + "\",\n" +
                                    "    \"userPassword\":\"" + password + "\",\n" +
                                    "    \"userState\":\"" + userState + "\",\n" +
                                    "    \"userPoint\":\"" + userPoint + "\"\n" +
                                    "}";
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder()
                                    .url(baseUrl + "/app/register")
                                    .post(RequestBody.create(json, MediaType.parse("application/json")))
                                    .build();
                            Response response = client.newCall(request).execute();//执行
                            String result = response.body().string();
                            Log.d("result",""+result);
                            if (result.equals("success")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                tologin();
                            }else if(result.equals("exist")){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(RegisterActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else if (result.equals("error")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(RegisterActivity.this, "未知错误稍后再试", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
                }
            }
        });

        //跳转登录页面
        tologinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tologin();
            }
        });

    }
    public Boolean judgeifEmpty(String uname,String pwd,String pwd2){
        if(uname.equals("")){
            Toast.makeText(this,"请输入账号",Toast.LENGTH_SHORT).show();
            return false;
        }else if(pwd.equals("")|| pwd2.equals("")){
            Toast.makeText(this,"请输入密码",Toast.LENGTH_SHORT).show();
            return false;
        }else if(!pwd.equals(pwd2)){
            Toast.makeText(this,"两次密码不一致",Toast.LENGTH_SHORT).show();
            return false;
        }else {return true;}
    }
    public void tologin(){
        //页面跳转方法
        Intent intent_register_loginBtn=new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent_register_loginBtn);
    }
}

