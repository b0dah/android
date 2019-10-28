package com.example.listviewexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private String[] dataSet = {"fisrt", "second", ""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionListener();
    }

    public void actionListener(){
        listView = (ListView) findViewById(R.id.listView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.cell_layout, dataSet);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener (
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String valueAtCurentPosition = (String) listView.getItemAtPosition(i);
                    Toast.makeText(
                            MainActivity.this,
                            "Pos " + i + " value" + valueAtCurentPosition,
                            Toast.LENGTH_LONG
                    ).show();
                }
            }

        );
    }
}
