package me.nathanfallet.classbot.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Devoirs {

    public int id;
    public String name;
    public String due;
    public String content;
    public String user;

    public Devoirs(JSONObject object) {
        try {
            this.id = object.has("id") ? object.getInt("id") : 0;
            this.name = object.has("name") ? object.getString("name") : "";
            this.due = object.has("due") ? object.getString("due") : "";
            this.content = object.has("content") ? object.getString("content") : "";
            this.user = object.has("user") ? object.getString("user") : "";
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
