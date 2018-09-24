package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    /**
     * @param json the raw JSON form of a Sandwich
     * @return the Sandwich object created from the input String, or else null if it can't be parsed
     */
    public static Sandwich parseSandwichJson(String json) {

        // JSON keys
        final String nameKey = "name";
        final String mainNameKey = "mainName";
        final String alsoKnownAsKey = "alsoKnownAs";
        final String placeOfOriginKey = "placeOfOrigin";
        final String descriptionKey = "description";
        final String imageKey = "image";
        final String ingredientsKey = "ingredients";

        /*
        EXAMPLE JSON:

        {\"name\":
            {\"mainName\":\"Ham and cheese sandwich\"
            ,\"alsoKnownAs\":
                []
            }
        ,\"placeOfOrigin\":\"\"
        ,\"description\":\"A ham and cheese
            sandwich is a common type of sandwich. It is made by putting cheese and sliced ham
            between two slices of bread. The bread is sometimes buttered and/or toasted. Vegetables
            like lettuce, tomato, onion or pickle slices can also be included. Various kinds of
            mustard and mayonnaise are also
            common.\"
        ,\"image\":\"https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Grilled_ham_and_cheese_014.JPG/800px-Grilled_ham_and_cheese_014.JPG\"
        ,\"ingredients\":
            [\"Sliced bread\",\"Cheese\",\"Ham\"]}
         */

        try {
            Sandwich parsedSandwich = new Sandwich();

            JSONObject jsonSandwich = new JSONObject(json);

            // main name
            JSONObject jsonName = jsonSandwich.getJSONObject(nameKey);
            parsedSandwich.setMainName(jsonName.optString(mainNameKey, ""));

            // also known as
            JSONArray jsonAlsoKnownAs = jsonName.optJSONArray(alsoKnownAsKey);

            if(jsonAlsoKnownAs != null && jsonAlsoKnownAs.length() > 0) {
                List<String> AKA_List = new ArrayList<String>();

                for (int i = 0; i < jsonAlsoKnownAs.length(); i++) {
                    AKA_List.add(jsonAlsoKnownAs.getString(i));
                }

                parsedSandwich.setAlsoKnownAs(AKA_List);
            }

            // origin
            parsedSandwich.setPlaceOfOrigin(jsonSandwich.optString(placeOfOriginKey, ""));

            // description
            parsedSandwich.setDescription(jsonSandwich.optString(descriptionKey, ""));

            // image
            parsedSandwich.setImage(jsonSandwich.optString(imageKey, ""));

            // ingredients
            JSONArray jsonIngredients = jsonSandwich.optJSONArray(ingredientsKey);

            if(jsonIngredients != null && jsonIngredients.length() > 0) {
                List<String> Ingredients_List = new ArrayList<String>();

                for(int i = 0; i < jsonIngredients.length(); i++){
                    Ingredients_List.add(jsonIngredients.getString(i));
                }

                parsedSandwich.setIngredients(Ingredients_List);
            }

            return parsedSandwich;
        }
        catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
}
