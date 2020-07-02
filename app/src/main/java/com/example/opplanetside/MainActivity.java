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

public class MainActivity extends AppCompatActivity {


TextView textView;
  public RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         textView = findViewById(R.id.playerTextView);
        this.queue = Volley.newRequestQueue(this);

        Button button = (Button)findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProfile();
            }
        });
        getPop();
    }
    public void openProfile(){
                Intent intent = new Intent(this, Profile.class);
                startActivity(intent);
            }

    public void getPop(){
        String url = "https://ps2.fisu.pw/api/population/?world=10";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("result");
                    JSONObject jsonObject = (JSONObject) jsonArray.get(0);


                    int popVS = (int) jsonObject.get("vs");
                    int popNC = (int) jsonObject.get("nc");
                    int popTR = (int) jsonObject.get("tr");
                    int popNS = (int) jsonObject.get("ns");
                    int pop = popNC + popNS + popTR + popVS;

                    textView.setText(Integer.toString(pop) + " players are online!");

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
