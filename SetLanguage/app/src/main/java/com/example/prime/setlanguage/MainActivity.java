package com.example.prime.setlanguage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    TextView langTV;
    CharSequence language[] = {"English","Spanish"};

    public void openDialogueBox(){
        final CharSequence language[] = {"English","Spanish"};
        final SharedPreferences sharedPreferences = this.getSharedPreferences("package com.example.prime.setlanguage", Context.MODE_PRIVATE);
        final String selectedLanguage=sharedPreferences.getString("language","");
        final TextView langTV = (TextView) findViewById(R.id.languageTV);

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Set language")
                .setMessage("Do you prefer English or Spanish?")
                .setPositiveButton("English", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        sharedPreferences.edit().putString("language","English").apply();
                        langTV.setText(sharedPreferences.getString("language",""));
                        Log.i("Language",sharedPreferences.getString("language",""));
                    }
                }).setNegativeButton("Spanish", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sharedPreferences.edit().putString("language","Spanish").apply();
                langTV.setText(sharedPreferences.getString("language",""));
                Log.i("Language",sharedPreferences.getString("language",""));
            }
        })
                .show();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SharedPreferences sharedPreferences = this.getSharedPreferences("package com.example.prime.setlanguage", Context.MODE_PRIVATE);
        final String selectedLanguage=sharedPreferences.getString("language","");
        langTV = (TextView) findViewById(R.id.languageTV);
            //Log.i("language","NULL");
        if(selectedLanguage==""){
            openDialogueBox();

        } else {
            langTV.setText(sharedPreferences.getString("language",""));
            Log.i("Language",sharedPreferences.getString("language",""));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.changeLanguage) {
            openDialogueBox();
            Log.i("Action","pressed");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
