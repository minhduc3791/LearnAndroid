package ducnm.learnandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

    private void getData() {
        String url ="http://thandongamnhac.vn/binhchon/export.php?offset=" + preLast;

        OnPostExUpdateListview onPostExUpdateListview = new OnPostExUpdateListview();
        onPostExUpdateListview.setListView(data);
        onPostExUpdateListview.setAdapter(adapter);

        IHTTPRequest ihttpRequest = new IHTTPRequest();
        ihttpRequest.setiOnPostEx(onPostExUpdateListview);
        ihttpRequest.execute(url);
    }
}
