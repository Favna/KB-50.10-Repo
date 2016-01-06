package com.example.method.worksurge.WebsiteConnector;

import android.os.AsyncTask;

import com.example.method.worksurge.Model.VacancyModel;

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
    private String url_backup = "https://www.randstad.nl/vacatures/?zoekterm=&locatie=Rotterdam&afstand=10"; // TODO: criteria
    private WebsiteDataParser dataParser;

    public WebsiteConnector() {
        dataParser = new WebsiteDataParser();
    }

    private Document connect(String searchCrit, int radius, String loc)
    {
        // Set default if empty
        if(radius == 0)
            radius = 10;

        try {
            // Change url connection details

            // Establish Connection
            return Jsoup.connect(url_backup).get();
        }
        catch (IOException e) {
            return null;
        }
    }

    public List<VacancyModel> readWebsite(String searchCrit, int radius, String loc) {
        //url = "http://www.nationalevacaturebank.nl/vacature/zoeken/overzicht/afstand/query//location/3066ga/output/html/items_per_page/50/page/1/ignore_ids";
        Document doc = connect(searchCrit, radius, loc);

            Elements jobTitle = doc.select("ol.results>li h3 a");
            Elements jobUndertitle = doc.select("dl.meta");
            Elements jobDetails = doc.select("ol.results>li .description"); // Currently does not work
            Elements jobUrl;

        List<String> jobsList = new ArrayList();

        // Returns List<VacancyModel>
        return dataParser.parseData(jobTitle, jobUndertitle, jobDetails);
    }

}
