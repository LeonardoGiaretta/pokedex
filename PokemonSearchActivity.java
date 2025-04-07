package com.example.pokedex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;


import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class PokemonSearchActivity extends AppCompatActivity {

    private EditText pokemonEditText;
    private Button searchButton;
    private ApiRequest apiRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        pokemonEditText = findViewById(R.id.pokemonEditText);
        searchButton = findViewById(R.id.searchButton);

        apiRequest = new ApiRequest(this);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pokemonName = pokemonEditText.getText().toString().toLowerCase();

                if (!pokemonName.isEmpty()) {
                    try {
                        fetchPokemonData(pokemonName);
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    Toast.makeText(PokemonSearchActivity.this, "Please enter a Pokémon name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fetchPokemonData(String pokemonName) throws UnsupportedEncodingException {
        String encodedName = URLEncoder.encode(pokemonName, "UTF-8");//per evitare problemi
        String url = "https://pokeapi.co/api/v2/pokemon/" + encodedName;

        apiRequest.getJsonObject(url, new ApiRequest.ApiResponseListener() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    int height = response.getInt("height");
                    int weight = response.getInt("weight");

                    //per prendere i tipi
                    JSONArray typesArray = response.getJSONArray("types");
                    StringBuilder typesBuilder = new StringBuilder();
                    for (int i = 0; i < typesArray.length(); i++) {
                        JSONObject typeObj = typesArray.getJSONObject(i).getJSONObject("type");
                        typesBuilder.append(typeObj.getString("name"));
                        if (i < typesArray.length() - 1) typesBuilder.append(", ");
                    }

                    // per prendere le abilità
                    JSONArray abilitiesArray = response.getJSONArray("abilities");
                    StringBuilder abilitiesBuilder = new StringBuilder();
                    for (int i = 0; i < abilitiesArray.length(); i++) {
                        JSONObject abilityObj = abilitiesArray.getJSONObject(i).getJSONObject("ability");
                        abilitiesBuilder.append(abilityObj.getString("name"));
                        if (i < abilitiesArray.length() - 1) abilitiesBuilder.append(", ");
                    }

                    Intent intent = new Intent(PokemonSearchActivity.this, PokemonDetailActivity.class);
                    intent.putExtra("pokemon_name", pokemonName);
                    intent.putExtra("pokemon_height", height);
                    intent.putExtra("pokemon_weight", weight);
                    intent.putExtra("pokemon_types", typesBuilder.toString());
                    intent.putExtra("pokemon_abilities", abilitiesBuilder.toString());
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(PokemonSearchActivity.this, "Error fetching Pokémon data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
