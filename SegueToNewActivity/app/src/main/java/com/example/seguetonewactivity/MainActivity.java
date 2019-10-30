package com.example.seguetonewactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

        ListView listView;

        // Define string array.
        String[] listValue = new String[] {"ONE","TWO","THREE","FOUR","FIVE","SIX","SEVEN","EIGHT"};

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            listView = (ListView)findViewById(R.id.listView1);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_2, android.R.id.text1, listValue);

            listView.setAdapter(adapter);

            // ListView on item selected listener.
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // TODO Auto-generated method stub

                    // Getting listview click value into String variable.
                    String TempListViewClickedValue = listValue[position].toString();

                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);

                    // Sending value to another activity using intent.
                    intent.putExtra("ListViewClickedValue", TempListViewClickedValue);

                    startActivity(intent);

                }
            });

        }
}

