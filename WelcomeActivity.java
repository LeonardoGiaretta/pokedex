package com.example.pokedex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
    }

    public void startSearchActivity(View view) {
        Intent intent = new Intent(WelcomeActivity.this, PokemonSearchActivity.class);
        startActivity(intent);
    }
}
