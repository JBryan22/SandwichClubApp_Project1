package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);

        } catch (Exception e) {
            e.printStackTrace();
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView alsoKnownAsTextView;
        TextView ingredientTextView;
        TextView placeOfOriginTextView;
        TextView descriptionTextView;
        alsoKnownAsTextView = findViewById(R.id.also_known_tv);
        List<String> alsoKnowsAs = sandwich.getAlsoKnownAs();
        if (alsoKnowsAs.size() > 0) {
            alsoKnownAsTextView.append(" " + alsoKnowsAs.get(0));
            for (int i = 1; i < alsoKnowsAs.size(); i++) {
                alsoKnownAsTextView.append(", " + alsoKnowsAs.get(i));
            }
        }
        ingredientTextView = findViewById(R.id.ingredients_tv);
        List<String> ingredients = sandwich.getIngredients();
        if (ingredients.size() > 0) {
            ingredientTextView.append("\n \u2022" + ingredients.get(0));
            for (int i = 1; i < ingredients.size(); i++) {
                ingredientTextView.append("\n \u2022" + ingredients.get(i));
            }
        }
        placeOfOriginTextView =  findViewById(R.id.origin_tv);
        placeOfOriginTextView.append(sandwich.getPlaceOfOrigin());
        descriptionTextView =  findViewById(R.id.description_tv);
        descriptionTextView.append(sandwich.getDescription());
    }
}
