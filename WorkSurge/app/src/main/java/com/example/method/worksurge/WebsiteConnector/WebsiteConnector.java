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
    private String url = "http://www.nationalevacaturebank.nl/vacature/zoeken/overzicht/afstand/query//location/3066ga/output/html/items_per_page/50/page/1/ignore_ids";
    private WebsiteDataParser dataParser;

    public WebsiteConnector() {}

    public void readWebsite(String searchCrit, int radius, String loc) {
        //url = "http://www.nationalevacaturebank.nl/vacature/zoeken/overzicht/afstand/query//location/3066ga/output/html/items_per_page/50/page/1/ignore_ids";

        try {
            /*
                TODO: Save cookie so we can continue.
             */
            Document doc = Jsoup.connect(url).get();
            Elements newsHeadlines = doc.select(".resultlist-title");
            System.out.println(doc.getAllElements());
        }
        catch (IOException exc) {
            System.out.println("ZEIKEN");
        }
        //return Jsoup.parse(url).text();
    }

}
