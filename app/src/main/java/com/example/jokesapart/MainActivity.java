package com.example.jokesapart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    RequestQueue queue;
    String url= "https://official-joke-api.appspot.com/random_joke";
    TextView txtJokes,txtType,txtSetup,txtPunch,txtId;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue= Volley.newRequestQueue(this);
        txtId=findViewById(R.id.jokeId);
        txtJokes=findViewById(R.id.txtJoke);
        txtPunch=findViewById(R.id.punchLine);
        txtSetup=findViewById(R.id.setUp);
        txtType=findViewById(R.id.type);

        progressBar=findViewById(R.id.progressBar3);

    }

    public void getJokes(View view) {
        progressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                int ID = 0;
                String type, setup, punch;
                try {
                    ID = response.getInt("id");
                    type = response.getString("type");
                    setup = response.getString("setup");
                    punch = response.getString("punchline");
                    Joke joke = new Joke(ID, type, setup, punch);
                    txtId.setText(joke.getID() + "");
                    txtId.setVisibility(View.VISIBLE);
                    txtType.setText(joke.getType() + "");
                    txtType.setVisibility(View.VISIBLE);
                    txtSetup.setText(joke.getSetup() + "");
                    txtSetup.setVisibility(View.VISIBLE);
                    txtPunch.setText(joke.getPunchLine() + "");
                    txtPunch.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                txtJokes.setText("Response: " + ID);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String err=error.toString();
                txtJokes.setText("Cannot get daata: "+error.toString());
            }
        });

        queue.add(jsonObjectRequest);
    }


    public void logoutPage(View view) {
        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    public void filterPage(View view) {
        Intent intent=new Intent(MainActivity.this,Filter.class);
        startActivity(intent);
    }
}