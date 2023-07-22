package com.example.ebook01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView addshelf,setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration =new AppBarConfiguration.Builder(
                R.id.bookshelfFragment,R.id.searchFragment,R.id.personFragment
        ).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


        addshelf = findViewById(R.id.main_addshelf);
        setting = findViewById(R.id.main_setting);
        addshelf.setOnClickListener(this);
        setting.setOnClickListener(this);
//        personfragment=(PersonFragment) getSupportFragmentManager().findFragmentById(R.id.personFragment);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.main_addshelf:
                Intent intent_addshelf = new Intent(MainActivity.this,AddShelfActivity.class);
                startActivity(intent_addshelf);
                break;
            case R.id.main_setting:
                Toast.makeText(this,"该功能暂未开放",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}