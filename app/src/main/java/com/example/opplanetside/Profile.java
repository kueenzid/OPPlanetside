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
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);
        this.queue = Volley.newRequestQueue(this);

        Button button = findViewById(R.id.testButton);
        textView = findViewById(R.id.profileName);
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

                    System.out.println((JSONArray) response.get("character_list"));
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