package com.example.method.worksurge.WebsiteConnector;

import com.example.method.worksurge.Model.VacancyModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Method on 23/12/2015.
 */
public class WebsiteDataParser {
    /*
     *
     * This class is used to parse the crude data from the Connector
     * into smart data that we need.
     *
     */

    public WebsiteDataParser() {}

    public void parseData(Elements jobTitle, Elements jobUndertitle, Elements jobDetails)
    {
        List<String> title = stripHtmlTags(jobTitle);
        List<String> undertitle = stripHtmlTags(jobUndertitle);
        List<String> details = stripHtmlTags(jobDetails);
    }

    private List<String> stripHtmlTags(Elements elements)
    {
        /*
            In case Elements objects are of an uneven number, we have encountered an issue
            The issue is that an element is missing something, this will jumble up all information
            that we will print to the user. We need to know which vacancy is missing information so we
            can fill the gip appropriatly. Currently it fills the gap with information from another
            vacancy.
         */

        List<String> list = new ArrayList();

        // Strip ALL html tags from any lines, and detect if any lines are empty so we delete those.
        for(int i = 0; i > elements.size(); i++)
        {
            list.add(elements.get(i).toString());
        }

        return list;
    }

    public List<VacancyModel> attachToModelList(List<String> title, List<String> undertitle, List<String> details)
    {
        List<VacancyModel> vacancyModel = null;

        return null;
    }

    private VacancyModel attachToModel(String title, String undertitle, String details)
    {
        VacancyModel model = new VacancyModel();

        model.Title = title;
        model.Undertitle = undertitle;
        model.Details = details;

        return model;
    }

    // temp
    private int determineSmallestSize(Elements title, Elements undertitle, Elements details)
    {
        int min = Math.min(Math.min(title.size(), undertitle.size()), details.size());

        return min ;
    }
}
