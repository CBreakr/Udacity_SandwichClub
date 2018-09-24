package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
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

    private TextView mDescriptionView;
    private TextView mOriginView;
    private TextView mAlsoKnownAsView;
    private TextView mIngredientsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Log.d("DetailActivity", "starting the activity onCreate");

        // find the views
        ImageView ingredientsIv = findViewById(R.id.image_iv);

        mDescriptionView = (TextView) findViewById(R.id.description_tv);
        mOriginView = (TextView) findViewById(R.id.origin_tv);
        mAlsoKnownAsView = (TextView) findViewById(R.id.also_known_tv);
        mIngredientsView = (TextView) findViewById(R.id.ingredients_tv);

        // fill the views
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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            Log.d("DetailActivity", "couldn't get sandwich");
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.mipmap.ic_launcher_round)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich currentSandwich) {
        mAlsoKnownAsView.setText(JoinStringList(", ", currentSandwich.getAlsoKnownAs()));
        if(mAlsoKnownAsView.getText().toString().isEmpty()){
            mAlsoKnownAsView.setText(R.string.AKA_replacement);
        }

        mOriginView.setText(currentSandwich.getPlaceOfOrigin());
        if(mOriginView.getText().toString().isEmpty()){
            mOriginView.setText(R.string.origin_replacement);
        }

        mIngredientsView.setText(JoinStringList(", ", currentSandwich.getIngredients()));
        if(mIngredientsView.getText().toString().isEmpty()){
            mIngredientsView.setText(R.string.ingredients_replacement);
        }

        mDescriptionView.setText(currentSandwich.getDescription());
        if(mDescriptionView.getText().toString().isEmpty()){
            mDescriptionView.setText(R.string.description_replacement);
        }
    }

    //
    // as the name suggests, it joins together the elements of List of Strings
    // into a single string with commas
    //
    //
    // TextUtils.join fails when a null list is passed in
    // (rather than return a null string, as I'd want)
    // so this handles that case differently
    //
    private String JoinStringList(String delim, List<String> inputList) {
        if(inputList != null && inputList.size() > 0){
            return TextUtils.join(delim, inputList);
        }
        return null;
    }
}
