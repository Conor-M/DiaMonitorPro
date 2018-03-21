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

        ImageView iv = (ImageView) findViewById(R.id.imageView);
        iv.setImageResource(R.drawable.plate);

    }
    public void HomeButtonClicked(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void SearchButtonClicked(View view){
        Intent i = new Intent(this, SearchResult.class);

        final EditText etSearchTerm = (EditText)findViewById(R.id.etSearchTerm);
        String searchMessage = etSearchTerm.getText().toString();
        i.putExtra("searchTerm", searchMessage);
        startActivity(i);

    }

}
