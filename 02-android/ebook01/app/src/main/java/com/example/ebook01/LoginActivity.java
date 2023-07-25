package com.example.ebook01;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ebook01.dao.UserDao;
import com.example.ebook01.entity.User;
import com.example.ebook01.network.IRequestTask;
import com.example.ebook01.network.LoginRequest;
import com.example.ebook01.network.OkhttpClientManager;
import com.example.ebook01.ui.MarkFragment;
import com.example.ebook01.utils.Httpurl;
import com.example.ebook01.utils.ShareHelper;
import com.example.ebook01.utils.SqliteUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    String baseUrl = Httpurl.getUrl();
    ShareHelper shareHelper;
    EditText usernameEt, passwordEt;
    Button registerBtn, loginBtn;
    UserDao userDao;
    SqliteUtil sqliteUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        //注册ID
        usernameEt = findViewById(R.id.login_et_username);
        passwordEt = findViewById(R.id.login_et_password);
        registerBtn = findViewById(R.id.login_btn_register);
        loginBtn = findViewById(R.id.login_btn_login);
        //注册监听器
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        //创建数据库
        sqliteUtil = SqliteUtil.getInstance(this);
        sqliteUtil.getReadableDatabase();

        userDao = new UserDao(LoginActivity.this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.login_btn_register:
                Intent intent_login_regisetBtn = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent_login_regisetBtn);
                break;
            case R.id.login_btn_login:
                String username = usernameEt.getText().toString();
                String password = passwordEt.getText().toString();
                User user = new User();
                if (check(username, password)) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                IRequestTask requestTask = new LoginRequest(username,password);
                                Response response = OkhttpClientManager.INSTANCE.executeRequest(requestTask);
                                ResponseBody body = response.body();
                                String result = body.string();//获取后端接口返回过来的JSON格式的结果
                                Log.d("result=", result);
                                JSONArray jsonArray = new JSONArray(result);//将文本格式的JSON转换为JSON数组
                                for (int i = 0; i < jsonArray.length(); i++) { //遍历这个数组
                                    JSONObject jsonObject = jsonArray.getJSONObject(i); //取出JSON元素
                                    user.setUserId(jsonObject.getInt("userId"));
                                    user.setUserName(jsonObject.getString("userName"));
                                    user.setUserPassword(jsonObject.getString("userPassword"));
                                    user.setUserState(jsonObject.getString("userState"));
                                    user.setUserPoint(jsonObject.getInt("userPoint"));
                                }
                                if (user.getUserId() == 0) {

                                } else if (!(user.getUserPassword().equals(password))) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else if (user.getUserState().equals("4")) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(LoginActivity.this, "账号不可用", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    Log.d("user=", "" + user);
                                    User searchUser = userDao.searchbyName(user.getUserName());
                                    if (searchUser == null) {
                                        userDao.addUser(user);
                                    }
                                    savrtoSP(user);
                                    toMain();
                                    Log.d("跳转", "main");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this, "网络请求失败！", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }).start();

                }
                break;
        }
    }

    public void toMain() {
        Intent intent_maninindex = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent_maninindex);
    }

    public Boolean check(String username, String password) {
        if (username.equals("")) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.equals("")) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public void savrtoSP(User user) {
        shareHelper = new ShareHelper(getApplicationContext());
        shareHelper.put("username", user.getUserName());
        shareHelper.put("password", user.getUserPassword());
        shareHelper.put("point", user.getUserPoint());
    }

}
