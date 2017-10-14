package ducnm.learnandroid;


import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by macos on 10/14/17.
 */

public class OnPostExUpdateListview implements IOnPostEx {
    private ArrayList<String> data;
    private ArrayAdapter<String> adapter;
    @Override
    public ArrayList<String> onPostEx(String response) {
        Log.d("here", response);
        try {
            JSONArray rawData = new JSONArray(response);

            // Getting JSON Array node
            for(int i = 0 ; i < rawData.length() ; i++){
                data.add(rawData.getJSONObject(i).getString("email"));
            }

            adapter.notifyDataSetChanged();
        } catch (JSONException e){

        }
        return data;
    }

    public void setListView(ArrayList<String> data){
        this.data = data;
    }

    public  void setAdapter(ArrayAdapter<String> adapter){
        this.adapter = adapter;
    }
}
