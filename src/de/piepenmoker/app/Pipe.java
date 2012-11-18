package de.piepenmoker.app;

import java.net.URI;
import java.net.URISyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

public class Pipe {

    Integer id;
    String name;
    Integer price;
    Image image;

    public class Image {

        URI preview, teaser, full;

        Image (JSONObject json) {
            try {
                this.preview = new URI(json.getString("preview"));
                this.teaser = new URI(json.getString("teaser"));
                this.full = new URI(json.getString("full"));
            } catch (JSONException e) {
                // TODO
            } catch (URISyntaxException e) {
                // TODO
            }
        }
    }

    Pipe(JSONObject json) {
        try {
            this.id = json.getInt("id");
            this.name = json.getString("name");
            this.price = json.getInt("price");
            this.image = new Image(json.getJSONObject("image"));
        } catch (JSONException e) {
            // TODO
        }
    }
}
