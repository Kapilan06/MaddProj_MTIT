package com.example.jokesapart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Filter extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=(ListView) findViewById(R.id.list_View);

        // jokes list displayed in list view
        try {
            JSONObject obj= new JSONObject(LoadJsonJokes());
            JSONArray array=obj.getJSONArray( "jokes");
            HashMap<String,String> list;
            ArrayList<HashMap<String,String>> arrayList=new ArrayList<>();
            for (int j=0;j<array.length();j++){
                JSONObject o= array.getJSONObject(j);
                // String id_i=o.getString("id");
                //String type_t=o.getString("type");

                String setup_s=o.getString("setup");
                String punchline_p=o.getString("punchline");
                list= new HashMap<>();
                // list.put("id",id_i);
                // list.put("type",type_t);
                list.put("setup",setup_s);
                list.put("punchline",punchline_p);
                arrayList.add(list);
            }
            ListAdapter adapter=new SimpleAdapter(this,arrayList,R.layout.list_view_jokes,new String[]{"id","type","setup","punchline"},new int[]{R.id.Id,R.id.type,R.id.joke,R.id.jokeAns});
            listView.setAdapter(adapter);
        }
        catch (JSONException e){
            e.printStackTrace();
        }



    }

    public String LoadJsonJokes(){

        String json=null;
        try {
            InputStream in= this.getAssets().open("index.json");
            int size = in.available();
            byte[] buffer= new byte[size];
            in.read(buffer);
            in.close();
            json=new String(buffer,"UTF-8");

        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
        return json;
    }
}