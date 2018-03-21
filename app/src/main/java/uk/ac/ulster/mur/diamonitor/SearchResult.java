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
import com.fatsecret.platform.model.CompactRecipe;
import com.fatsecret.platform.model.Food;
import com.fatsecret.platform.model.Recipe;
import com.fatsecret.platform.services.Response;
import com.fatsecret.platform.services.android.Request;
import com.fatsecret.platform.services.android.ResponseListener;

import java.util.ArrayList;
import java.util.List;

public class SearchResult extends AppCompatActivity {
    private ArrayList<FSFood> fsFoodArrayList= new ArrayList<FSFood>();
    private ListView listFoodList;
    private ArrayAdapter<FSFood> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        listFoodList = findViewById(R.id.listFoodList);

        Bundle searchData = getIntent().getExtras();
        if(searchData==null){
            return;
        }
        String query = searchData.getString("searchTerm");


        String key = "5623ffedc11840e09e7e97b85bce9b79";
        String secret = "8d63996be6ef4f789f44decedbc44a2b";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Listener listener = new Listener();

        Request req = new Request(key, secret, listener);

        //This response contains the list of food items at zeroth page for your query
        req.getFoods(requestQueue, query, 0);

        //This response contains the list of food items at page number 3 for your query
        //If total results are less, then this response will have empty list of the food items
        req.getFoods(requestQueue, query, 3);

        //This food object contains detailed information about the food item
        req.getFood(requestQueue, 29304L);

        //This response contains the list of recipe items at zeroth page for your query
        req.getRecipes(requestQueue, query, 0);

        //This response contains the list of recipe items at page number 2 for your query
        //If total results are less, then this response will have empty list of the recipe items
        req.getRecipes(requestQueue, query, 2);

        //This recipe object contains detailed information about the recipe item
        req.getRecipe(requestQueue, 315L);


        adapter = new ArrayAdapter<FSFood>(this,android.R.layout.simple_list_item_1,fsFoodArrayList);


        listFoodList.setAdapter(adapter);
    }

    class Listener implements ResponseListener {
        @Override
        public void onFoodListRespone(Response<CompactFood> response) {
            System.out.println("TOTAL FOOD ITEMS: " + response.getTotalResults());

            List<CompactFood> foods = response.getResults();
            //This list contains summary information about the food items

            System.out.println("=========FOODS============");
            for (CompactFood food: foods) {
                System.out.println(food.getName());
                FSFood thisFood = new FSFood();
                thisFood.setDescription(food.getDescription());
                thisFood.setName(food.getName());
                thisFood.setType(food.getType());
                if(thisFood.getType()=="Brand")
                    thisFood.setBrandName(food.getBrandName());
                fsFoodArrayList.add(thisFood);
                System.out.println(thisFood.toString());

            }
            adapter.notifyDataSetChanged();


        }

        @Override
        public void onRecipeListRespone(Response<CompactRecipe> response) {
            System.out.println("TOTAL RECIPES: " + response.getTotalResults());

            List<CompactRecipe> recipes = response.getResults();
            System.out.println("=========RECIPES==========");
            for (CompactRecipe recipe: recipes) {
                System.out.println(recipe.getName());
            }
        }

        @Override
        public void onFoodResponse(Food food) {
            System.out.println("FOOD NAME: " + food.getName());
            System.out.println("FOOD NAME: " + food.getDescription());
        }

        @Override
        public void onRecipeResponse(Recipe recipe) {
            System.out.println("RECIPE NAME: " + recipe.getName());
        }


    }public void HomeButtonClicked(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }



}