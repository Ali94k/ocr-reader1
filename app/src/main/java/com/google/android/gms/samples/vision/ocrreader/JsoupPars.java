package com.google.android.gms.samples.vision.ocrreader;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by alike on 02.03.2017.
 */

public class JsoupPars extends AppCompatActivity {


    private EditText respText;

    String edtUrl = null;
    private TextView textValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.definition);
        textValue = (TextView) findViewById(R.id.edtURL);


        Intent intent = getIntent();
        edtUrl = intent.getStringExtra("URL");

        textValue.setText(edtUrl);

        Toast.makeText(getApplicationContext(), "onCreate",
                Toast.LENGTH_LONG).show();

        //final EditText edtUrl = (EditText) findViewById(R.id.edtURL);
        //final String edtUrl =new String();
        Button btnGo = (Button) findViewById(R.id.btnGo);
        respText = (EditText) findViewById(R.id.edtResp);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String siteUrl = edtUrl;

                ( new ParseURL() ).execute(siteUrl);
            }
        });
    }


    private class ParseURL extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            StringBuffer buffer = new StringBuffer();
/*            Toast.makeText(getApplicationContext(), "doInBackground",
                    Toast.LENGTH_LONG).show();*/
            try {
                //Log.d("JSwa", "Connecting to ["+strings[0]+"]");
                Document doc  = Jsoup.connect(strings[0]).get();
                //Log.d("JSwa", "Connected to ["+strings[0]+"]");
                // Get document (HTML page) title
                String title = doc.title();
                //Log.d("JSwA", "Title ["+title+"]");
                buffer.append("Title: " + title + "\r\n");

                // Get meta info
                Elements metaElems = doc.select("meta");
                buffer.append("META DATA\r\n");
                for (Element metaElem : metaElems) {
                    String name = metaElem.attr("name");
                    String content = metaElem.attr("content");
                    if(name.equals("description"))
                        buffer.append("name ["+name+"] - content ["+content+"] \r\n");
                }

                Elements topicList = doc.select("h2.topic");
                buffer.append("Topic list\r\n");
                for (Element topic : topicList) {
                    String data = topic.text();

                    buffer.append("Data ["+data+"] \r\n");
                }

            }
            catch(Throwable t) {
                t.printStackTrace();
            }

            return buffer.toString();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            respText.setText(s);
        }
    }
}
