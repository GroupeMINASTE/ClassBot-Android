package me.nathanfallet.classbot.models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class APIList {

    public Cours[] cours;
    public Devoirs[] devoirs;

    public APIList(JSONObject object) {
        try {
            if (object.has("cours")) {
                JSONArray array = object.getJSONArray("cours");
                ArrayList<Cours> result = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    result.add(new Cours(array.getJSONObject(i)));
                }
                this.cours = result.toArray(new Cours[]{});
            } else {
                this.cours = new Cours[]{};
            }
            if (object.has("devoirs")) {
                JSONArray array = object.getJSONArray("devoirs");
                ArrayList<Devoirs> result = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    result.add(new Devoirs(array.getJSONObject(i)));
                }
                this.devoirs = result.toArray(new Devoirs[]{});
            } else {
                this.devoirs = new Devoirs[]{};
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
