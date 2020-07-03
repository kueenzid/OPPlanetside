package com.example.opplanetside;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;
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
import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class Profile extends AppCompatActivity {


    public RequestQueue queue;
    TextView lastLogin;
    TextView name;
    TextView balance;
    TextView level;
    Vector<TextView> vectorTextView;
    Vector<String> vectorFriendsName;
    public String stringFriendName;
    private Timer timer;
    private TimerTask timerTask;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);
        this.queue = Volley.newRequestQueue(this);

        name = findViewById(R.id.profileName);
        lastLogin = findViewById(R.id.profileLastLogin);
        balance = findViewById(R.id.profileBalance);
        level = findViewById(R.id.profileLevel);
        vectorTextView = new Vector<>();
        vectorFriendsName = new Vector<>();
        getStats();
        getfriends();




        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new TimerTask() {
                    @Override
                    public void run() {
                        for(int i = 0; i < vectorFriendsName.size(); i++){
                            vectorTextView.get(i).setText(vectorFriendsName.get(i));
                            System.out.println(vectorFriendsName.size());
                            System.out.println(vectorTextView.size());
                        }
                    }
                });
            }
        }, 0, 5000);

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

    public void getfriends(){
        String url = "http://census.daybreakgames.com/s:dennosdemain/get/ps2:v2/character/?character_id=5428690458388482097&c:resolve=friends";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = (JSONArray) response.get("character_list");
                    JSONObject jsonObject = (JSONObject) jsonArray.get(0);

                    JSONArray jsonArray2 = jsonObject.getJSONArray("friend_list");
                    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

                    for(int i = 0; i < jsonArray2.length(); i++){
                        JSONObject jsonObject2 = (JSONObject) jsonArray2.get(i);
                        String friendId = jsonObject2.get("character_id").toString();

                        TextView textView = new TextView(Profile.this);
                        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        textView.setId(i);
                        textView.isClickable();
                        getNames(friendId);
                        textView.setText("Loading");
                        linearLayout.addView(textView);
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                
                            }
                        });
                        vectorTextView.add(textView);


                    }

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
    public void getNames(String id) {

            String url = "http://census.daybreakgames.com/s:dennosdemain/get/ps2:v2/character/?character_id=" + id;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        JSONArray jsonArray = (JSONArray) response.get("character_list");
                        JSONObject jsonObject = (JSONObject) jsonArray.get(0);
                        stringFriendName = jsonObject.getJSONObject("name").get("first").toString();

                        System.out.println(stringFriendName);

                        vectorFriendsName.add(stringFriendName);
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
