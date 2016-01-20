package com.example.method.worksurge.WebsiteConnector;

import com.example.method.worksurge.Model.VacancyDetailModel;
import com.example.method.worksurge.Model.VacancyMapDetail;
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

    // For Extra Detail
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

        // Returns List<VacancyModel>
        return dataParser.parseData(jobTitle, jobUndertitle, jobDetails, jobUrl);
    }

    public VacancyDetailModel readWebsite(String urlDetail)
    {
        Document doc = connect(urlDetail);

        Elements jobTitle = doc.select(".content-wrapper>main.jobdetail h1");
        Elements jobDetails = doc.select(".content-wrapper>main.jobdetail .content p");
        Elements jobCompany = doc.select("dl.meta dd");
        Elements jobMeta = doc.select("dl.meta");
        Elements jobTelefoon = doc.select(".tel");

        // Returns List<VacancyDetailModel>
        return dataParser.parseData(jobTitle, jobDetails, jobCompany, jobMeta, jobTelefoon);
    }

    public List<VacancyMapDetail> readWebsiteMap(List<VacancyModel> model)
    {
        List<VacancyMapDetail> list = new ArrayList<VacancyMapDetail>();
        /*
        for(VacancyModel item : model)
        {
            //Document doc = connect(item.getURL());
            Document doc = connect("https://www.randstad.nl/vacatures/1710605/gave-bijbaan-als-meubel-verkoper%21");
            if(doc != null)
            {
                if(doc.select(".street-address") != null)
                {
                    VacancyMapDetail tempMap = new VacancyMapDetail();
                    tempMap.setVacancyDetailModel(item);
                    tempMap.setAddress(dataParser.parseData(doc.select(".street-address")));
                    list.add(tempMap);
                }
            }
        }
        */

        //Document doc = connect(item.getURL());
        Document doc = connect("https://www.randstad.nl/vacatures/1710605/gave-bijbaan-als-meubel-verkoper%21");
        if(doc != null)
        {
            if(doc.select(".street-address") != null)
            {
                Elements tempEle = doc.select(".street-address");
                String address = dataParser.parseData(tempEle);
                VacancyMapDetail tempMap = new VacancyMapDetail();
                tempMap.setVacancyDetailModel(model.get(0));
                tempMap.setAddress(address);
                list.add(tempMap);
            }
        }

        // Returns List<VacancyMapDetail>
        return list;
    }

}
