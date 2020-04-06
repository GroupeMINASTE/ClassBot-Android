package me.nathanfallet.classbot.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Cours {

    public int id;
    public String name;
    public String start;
    public String user;

    public Cours(JSONObject object) {
        try {
            this.id = object.has("id") ? object.getInt("id") : 0;
            this.name = object.has("name") ? object.getString("name") : "";
            this.start = object.has("start") ? object.getString("start") : "";
            this.user = object.has("user") ? object.getString("user") : "";
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
