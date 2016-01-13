package com.example.method.worksurge.WebsiteConnector;

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
    private String url = "https://www.randstad.nl/vacatures/?zoekterm=@search&locatie=@location&afstand=@distance";
    private String url_backup = "https://www.randstad.nl/vacatures/?zoekterm=@search&locatie=@location&afstand=@distance"; // TODO: criteria
    private WebsiteDataParser dataParser;

    public WebsiteConnector() {
        dataParser = new WebsiteDataParser();
    }

    private Document connect(String detailUrl)
    {
        try {
            // Establish Connection
            Document doc = Jsoup.connect(detailUrl).get();

            // Return document
            return doc;
        }
        catch (IOException e) {
            return null;
        }
    }
    private Document connect(String searchCrit, int radius, String loc)
    {
        // Set default if empty
        if(radius == 0)
            radius = 10;

        try {
            // Change url connection details
            url = url.replace("@search", searchCrit);
            url = url.replace("@distance", Integer.toString(radius));
            url = url.replace("@location", "Den+Haag");
            System.out.println("S: " + searchCrit + " R: " + radius + "\n" + url);

            // Establish Connection
            Document doc = Jsoup.connect(url).get();

            url = url_backup;
            // Return document
            return doc;
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
        Elements jobDetails = doc.select("ol.results>li .description");
        Elements jobUrl = doc.select(".jobboard h3>a");

        List<String> jobsList = new ArrayList();

        // Returns List<VacancyModel>
        return dataParser.parseData(jobTitle, jobUndertitle, jobDetails, jobUrl);
    }

    public List<VacancyModel> readWebsite(String urlDetail)
    {
        Document doc = connect(urlDetail);

        Elements jobTitle = doc.select("ol.results>li h3 a");
        Elements jobUndertitle = doc.select("dl.meta");
        Elements jobDetails = doc.select("ol.results>li .description");
        Elements jobCompany = doc.select("");

        List<String> jobsList = new ArrayList();

        // Returns List<VacancyModel>
        return dataParser.parseData(jobTitle, jobUndertitle, jobDetails, jobCompany);
    }

}
