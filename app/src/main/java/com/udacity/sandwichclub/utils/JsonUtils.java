package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        JSONObject sandwichObject = new JSONObject(json);
        JSONObject nameObject = sandwichObject.getJSONObject("name");
        String mainName = nameObject.getString("mainName");
        List<String> alsoKnownAs = new ArrayList<>();
        JSONArray alsoKnownAsArray = nameObject.getJSONArray("alsoKnownAs");
        if (alsoKnownAsArray != null) {
            for(int i = 0; i < alsoKnownAsArray.length(); i++) {
                alsoKnownAs.add(alsoKnownAsArray.getString(i));
            }
        }
        String placeOfOrigin = sandwichObject.getString("placeOfOrigin");
        String description = sandwichObject.getString("description");
        String imageURL = sandwichObject.getString("image");
        List<String> ingredients = new ArrayList<>();
        JSONArray ingredientArr = sandwichObject.getJSONArray("ingredients");
        if (ingredientArr != null) {
            for(int i = 0; i < ingredientArr.length(); i++) {
               ingredients.add(ingredientArr.getString(i));
            }
        }

        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, imageURL, ingredients);
    }
}
