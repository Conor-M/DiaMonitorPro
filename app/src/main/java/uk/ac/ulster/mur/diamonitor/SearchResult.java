package uk.ac.ulster.mur.diamonitor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.fatsecret.platform.model.CompactFood;
import com.fatsecret.platform.services.Response;
import com.fatsecret.platform.services.android.Request;
import com.fatsecret.platform.services.android.ResponseListener;

import java.util.ArrayList;
import java.util.List;
/**
 * Activity Display the search results
 *
 *
 * @author  Conor Murphy
 * @version 1.0
 * @since   2018-1-20
 *
 */
public class SearchResult extends AppCompatActivity {
    private ArrayList<FSFood> fsFoodArrayList= new ArrayList<FSFood>();
    private ListView listFoodList;
    private ArrayAdapter<FSFood> adapter;
    /**
     * Creates the view of the activity when the activity is first started
     * sets title of the activity to be displayed
     * Builds the Query for the request to the API
     *
     * @param savedInstanceState Required as is an implementation of the onClick defined in xml for this activit
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Search Results");
        setContentView(R.layout.activity_search_result);
        listFoodList = findViewById(R.id.listFoodList);

        Bundle searchData = getIntent().getExtras();
        if(searchData==null){
            return;
        }
        String query = searchData.getString("searchTerm");
        //KEY FOR FATSECRET API
        String key = "5623ffedc11840e09e7e97b85bce9b79";
        //SECRET KEY FOR API
        String secret = "8d63996be6ef4f789f44decedbc44a2b";

        //Builds the request for the API
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Listener listener = new Listener();
        Request req = new Request(key, secret, listener);
        //This response contains the list of food items at page number 1 for your query
        //If total results are less, then this response will have empty list of the food items
        req.getFoods(requestQueue, query, 1);
        adapter = new ArrayAdapter<FSFood>(this,android.R.layout.simple_list_item_1,fsFoodArrayList);
        listFoodList.setAdapter(adapter);
    }

    /**
     * Listener implementation that builds the array that displays the foods and descriptions then
     * updates the adapter to display all the objects in the foodlist array
     */
    class Listener implements ResponseListener {
        @Override
        public void onFoodListRespone(Response<CompactFood> response) {
            List<CompactFood> foods = response.getResults();
            //This list contains summary information about the food items
            for (CompactFood food: foods) {
                FSFood thisFood = new FSFood();
                thisFood.setDescription(food.getDescription());
                thisFood.setName(food.getName());
                thisFood.setType(food.getType());
                if(thisFood.getType()=="Brand")
                    thisFood.setBrandName(food.getBrandName());
                fsFoodArrayList.add(thisFood);
            }
            adapter.notifyDataSetChanged();
        }
    }
    /**
     * Brings the user back to the home activity on click of the button
     * @param view Required as is an implementation of the onClick defined in xml for this activity
     */
    public void HomeButtonClicked(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }



}