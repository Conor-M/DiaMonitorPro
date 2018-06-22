package uk.ac.ulster.mur.diamonitor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //Sets image from resources
        ImageView iv = (ImageView) findViewById(R.id.imageView);
        iv.setImageResource(R.drawable.plate);

    }
    public void HomeButtonClicked(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

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
