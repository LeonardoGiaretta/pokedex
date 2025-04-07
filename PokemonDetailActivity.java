package com.example.pokedex;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PokemonDetailActivity extends AppCompatActivity {

    private TextView nameTextView, heightTextView, weightTextView, typesTextView, abilitiesTextView;
    private Button actionButton; //pulsante per tornare indietro

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon_detail_activity);

        nameTextView = findViewById(R.id.nameTextView);
        heightTextView = findViewById(R.id.heightTextView);
        weightTextView = findViewById(R.id.weightTextView);
        typesTextView = findViewById(R.id.typesTextView);
        abilitiesTextView = findViewById(R.id.abilitiesTextView);

        actionButton = findViewById(R.id.actionButton);

        String pokemonName = getIntent().getStringExtra("pokemon_name");
        int height = getIntent().getIntExtra("pokemon_height", 0);  // il valore predefinito è 0
        int weight = getIntent().getIntExtra("pokemon_weight", 0);
        String types = getIntent().getStringExtra("pokemon_types");
        String abilities = getIntent().getStringExtra("pokemon_abilities");

        nameTextView.setText("Nome: " + capitalize(pokemonName));
        heightTextView.setText("Altezza: " + height + "m");
        weightTextView.setText("Peso: " + weight + "kg");
        typesTextView.setText("Tipo/i: " + types);
        abilitiesTextView.setText("Abilità: " + abilities);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Torna all'activity precedente
            }
        });
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty())
            return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
