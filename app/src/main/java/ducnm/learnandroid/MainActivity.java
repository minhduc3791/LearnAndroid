package ducnm.learnandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private int preLast;
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> data = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preLast = 0;

        listView = (ListView) findViewById(android.R.id.list);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);
        getData();

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                switch(view.getId())
                {
                    case android.R.id.list:
                        final int lastItem = firstVisibleItem + visibleItemCount;

                        if(lastItem == totalItemCount)
                        {
                            if(preLast != lastItem)
                            {
                                //to avoid multiple calls for last item
                                Log.d("Last", "Last");
                                preLast = lastItem;
                                getData();
                            }
                        }
                }
            }
        });
    }

    private void getData(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://thandongamnhac.vn/binhchon/export.php?offset=" + preLast;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.

                        try {
                            JSONArray datas = new JSONArray(response);

                            // Getting JSON Array node
                            for(int i = 0 ; i < datas.length() ; i++){
                                data.add(datas.getJSONObject(i).getString("email"));
                            }
                        } catch (JSONException e){

                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Log error", "That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
