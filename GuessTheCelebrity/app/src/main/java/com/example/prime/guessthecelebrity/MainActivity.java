package com.example.prime.guessthecelebrity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    ImageView imageView;
    ArrayList<String> celebURLs = new ArrayList<String>();
    ArrayList<String> celebNames = new ArrayList<String>();
    int chosenCeleb = 0;
    int locationOfCorrectAnswer = 0;
    String[] answers = new String[4];

    public void celebChosen(View view) throws ExecutionException, InterruptedException {
        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))){

            Toast.makeText(getApplicationContext(),"Correct!",Toast.LENGTH_SHORT).show();


        }
        else{
            Toast.makeText(getApplicationContext(),"Wrong! It was " + celebNames.get(chosenCeleb),Toast.LENGTH_LONG).show();
        }
        createNewQuestion();
    }

    public class DownloadImage extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... urls) {

            URL url = null;
            try {
                url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream in = urlConnection.getInputStream();
                Bitmap mybitmap = BitmapFactory.decodeStream(in);
                return mybitmap;

            } catch (MalformedURLException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();
            }



            return null;
        }
    }





    public class DownloadTask extends AsyncTask<String, Void, String> {
        //1st arg - URL we are sending to fetch data
        //2nd arg - name of the method we may/may not use to show the progress of this task
        //3rd arg - returned by DownloadTask (return URL content as String)
        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try{
                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);


                int data = reader.read(); //keeps track of location content

                while(data!=-1){

                    char current = (char) data;

                    result += current;

                    data = reader.read();
                }
                return result;

            }catch(Exception e){

                e.printStackTrace();

            }
            return null;



        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);


        DownloadTask task = new DownloadTask();
        String result = null;
        try {

            result = task.execute("http://www.posh24.com/celebrities").get();
            String splitter = "<div class=\"sidebarContainer\">";

            String[] splitResult = result.split(splitter);

            Pattern p = Pattern.compile("img src=\"(.*?)\"");
            Matcher m = p.matcher(splitResult[0]);
            while (m.find()){
                celebURLs.add(m.group(1));
                //System.out.println(m.group(1));
            }

            p = Pattern.compile("alt=\"(.*?)\"");
            m = p.matcher(splitResult[0]);
            while (m.find()){
                celebNames.add(m.group(1));
                //System.out.println(m.group(1));
            }

            createNewQuestion();

        } catch (InterruptedException e) {

            e.printStackTrace();

        } catch (ExecutionException e) {

            e.printStackTrace();
        }



        //www.posh24.com/celebrities
    }
    public void createNewQuestion() throws ExecutionException, InterruptedException {

        Random random = new Random();
        chosenCeleb = random.nextInt(celebURLs.size());


        DownloadImage imageTask = new DownloadImage();
        Bitmap celebImage;
        celebImage = imageTask.execute(celebURLs.get(chosenCeleb)).get();
        imageView.setImageBitmap(celebImage);

        locationOfCorrectAnswer = random.nextInt(4);

        int incorrectAnswerLocation = random.nextInt(4);
        for(int i = 0 ; i < 4; i++)
        {
            if(i == locationOfCorrectAnswer){
                answers[i] = celebNames.get(chosenCeleb);
            } else {

                incorrectAnswerLocation = random.nextInt(celebURLs.size());

                while(incorrectAnswerLocation==chosenCeleb)
                {
                    incorrectAnswerLocation = random.nextInt(celebURLs.size());
                }
                answers[i] = celebNames.get(incorrectAnswerLocation);

            }

        }

        button1.setText(answers[0]);
        button2.setText(answers[1]);
        button3.setText(answers[2]);
        button4.setText(answers[3]);
    }
}
