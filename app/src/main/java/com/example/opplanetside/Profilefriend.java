package com.example.opplanetside;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Profilefriend extends AppCompatActivity {

    public String friendId;
    public TextView profileLastLogin4;
    public TextView profileBalance4;
    public TextView profileLevel4;
    public TextView profileName4;
    public RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilefriend);

        Intent intent = getIntent();
        String friendId = intent.getStringExtra("name");

        profileName4 = findViewById(R.id.profileName4);
        profileLevel4 = findViewById(R.id.profileLevel4);
        profileBalance4 = findViewById(R.id.profileBalance4);
        profileLastLogin4 = findViewById(R.id.profileLastLogin4);

        Button button = findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHome();
            }
        });
        getStats(friendId);

    }

    public void getStats(String id){
        String url = "http://census.daybreakgames.com/s:dennosdemain/get/ps2:v2/character/?name.first_lower=" + id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = (JSONArray) response.get("character_list");
                    JSONObject jsonObject = (JSONObject) jsonArray.get(0);

                    String stringName = jsonObject.getJSONObject("name").get("first").toString();
                    String stringLevel = jsonObject.getJSONObject("battle_rank").get("value").toString();
                    String stringBalance = jsonObject.getJSONObject("certs").get("available_points").toString();
                    String stringLastLogin = jsonObject.getJSONObject("times").get("last_login_date").toString();

                    profileName4.setText(stringName);
                    profileLevel4.setText("Battle rank: " + stringLevel);
                    profileBalance4.setText("Certs: " + stringBalance);
                    profileLastLogin4.setText("Last login: " + stringLastLogin);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("RIP");
            }
        });
        this.queue.add(jsonObjectRequest);
    }

    public void openHome(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}