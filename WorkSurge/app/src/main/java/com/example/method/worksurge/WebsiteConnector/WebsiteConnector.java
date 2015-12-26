package com.example.method.worksurge.WebsiteConnector;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Method on 23/12/2015.
 */
public class WebsiteConnector {
    /*
     *
     * This class is used to make the connection to our specified website
     * NationaleVacatureBank. The WebsiteConnector will establish the connection
     * and will send retrieved data to the parser.
     *
     */
    private final String url = "http://www.nationalevacaturebank.nl/";
    private WebsiteDataParser dataParser;

    public WebsiteConnector() {}

    public void taskExecute() {

        TalkToServer myTask = new TalkToServer();
        myTask.execute();
    }

    public void readWebsite() {
        try {
            Document doc = Jsoup.connect("http://en.wikipedia.org/").get();
            Elements newsHeadlines = doc.select("#mp-itn b a");

            System.out.println(newsHeadlines.toString());


        }
        catch (IOException exc) {
            System.out.println("ZEIKEN");
        }
        //return Jsoup.parse(url).text();
    }

    public class TalkToServer extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
        /*
         *    do things before doInBackground() code runs
         *    such as preparing and showing a Dialog or ProgressBar
        */
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        /*
         *    updating data
         *    such a Dialog or ProgressBar
        */

        }



        @Override
        protected Void doInBackground(Void... params) {
            readWebsite();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
        /*
         *    do something with data here
         *    display it or send to mainactivity
         *    close any dialogs/ProgressBars/etc...
        */
        }

    }

}
