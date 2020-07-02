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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Profile extends AppCompatActivity {


    public RequestQueue queue;
    TextView lastLogin;
    TextView name;
    TextView balance;
    TextView level;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);
        this.queue = Volley.newRequestQueue(this);

        Button button = findViewById(R.id.testButton);
        name = findViewById(R.id.profileName);
        lastLogin = findViewById(R.id.profileLastLogin);
        balance = findViewById(R.id.profileBalance);
        level = findViewById(R.id.profileLevel);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProfileFriend();
            }
        });
        getStats();
    }

    public void openProfileFriend(){
 Intent intent = new Intent(this, Profilefriend.class);
    startActivity(intent);

    }

    public void getStats(){
        String url = "http://census.daybreakgames.com/s:dennosdemain/get/ps2:v2/character/?name.first_lower=fghkjhlkjhlkjh";
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

                    name.setText(stringName);
                    level.setText("Battle rank: " + stringLevel);
                    balance.setText("Certs: " + stringBalance);
                    lastLogin.setText("Last login: " + stringLastLogin);

                    

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
}