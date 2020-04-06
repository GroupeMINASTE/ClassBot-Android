package me.nathanfallet.classbot.models;

import org.json.JSONException;
import org.json.JSONObject;

public class APIVerification {

    public boolean classbot;

    public APIVerification(JSONObject object) {
        try {
            this.classbot = object.has("classbot") && object.getBoolean("classbot");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
