package uk.ac.ulster.mur.diamonitor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
/**
 * Activity to allow the user to search the FatSecret Database
 * this activity function to take the search term
 *
 *
 * @author  Conor Murphy
 * @version 1.0
 * @since   2018-1-20
 *
 */
public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Search Food Database");

        setContentView(R.layout.activity_search);
        //Sets image from resources
        ImageView iv = (ImageView) findViewById(R.id.imageView);
        iv.setImageResource(R.drawable.plate);

    }
    /**
     * Brings the user back to the home activity on click of the button
     * @param view Required as is an implementation of the onClick defined in xml for this activity
     */
    public void HomeButtonClicked(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    /**
     * When search button is clicked the user is brought to the SearchResult.class with
     * the search term passed as an Extra in the intent
     *
     * @param view
     */
    public void SearchButtonClicked(View view){
        //brings the user to the results of search
        Intent i = new Intent(this, SearchResult.class);

        EditText etSearchTerm = (EditText)findViewById(R.id.etSearchTerm);

        //sets string searchMessage to etSearchTerm users search parameter
        String searchMessage = etSearchTerm.getText().toString();
        //uses bundle to pass searchMessage to the intent activity
        i.putExtra("searchTerm", searchMessage);
        startActivity(i);

    }

}
