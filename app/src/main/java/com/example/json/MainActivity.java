package com.example.json;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1 = (TextView) findViewById(R.id.t1);

        jsonTask jTask = new jsonTask();
        jTask.execute();

    }


    public class jsonTask extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... strings) {

            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;
            String name;
            Integer age;
            String description;
            try {
                URL url = new URL("https://api.myjson.com/bins/14ww1m");
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String line = "";
                StringBuffer lastBuffer = new StringBuffer();
                while ((line = bufferedReader.readLine()) != null) {
                    

                    stringBuffer.append(line);

                }
                String file = stringBuffer.toString();


                JSONObject fileObject = new JSONObject(file);
                JSONArray jsonArray = fileObject.getJSONArray("studentinfo");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject arrayObject = jsonArray.getJSONObject(i);
                    name = arrayObject.getString("name");
                    age = arrayObject.getInt("age");
                    description = arrayObject.getString("description");

                    lastBuffer.append(name + "\n" + age + "\n" + description + "\n\n");
                }
                return lastBuffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                httpURLConnection.disconnect();
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            t1.setText(s);
        }
    }
}