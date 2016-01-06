package com.example.method.worksurge.WebsiteConnector;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private String url = "http://www.nationalevacaturebank.nl/vacature/zoeken/overzicht/afstand/query//location/3066ga/output/html/items_per_page/50/page/1/ignore_ids";
    private String url_backup = "https://www.randstad.nl/vacatures/?zoekterm=info&locatie=Rotterdam&afstand=10";
    private WebsiteDataParser dataParser;

    public WebsiteConnector() {}

    public void readWebsite(String searchCrit, int radius, String loc) {
        //url = "http://www.nationalevacaturebank.nl/vacature/zoeken/overzicht/afstand/query//location/3066ga/output/html/items_per_page/50/page/1/ignore_ids";

        try {
            /*
                TODO: Save cookie so we can continue.
             */
            /*Document doc = Jsoup.connect(url).get();
            Elements newsHeadlines = doc.select(".resultlist-title");
            System.out.println(doc.getAllElements());*/
            Document doc = Jsoup.connect(url_backup).get();
            Elements jobTitle = doc.select("ol.results>li h3 a");
            Elements jobUndertitle = doc.select("dl.meta");
            Elements jobDetails = doc.select("ol.results>li .description"); // Currently does not work
            Elements jobUrl;

            List<String> jobsList = new ArrayList();
            jobsList.add("Title: " + jobTitle.toString() + " Undertitle: " + jobUndertitle.toString() + " Details: " + jobDetails.toString());
            for(String E : jobsList)
            {
                System.out.println(E);
            }
        }
        catch (IOException exc) {
            System.out.println("ZEIKEN");
        }
        //return Jsoup.parse(url).text();
    }

}
