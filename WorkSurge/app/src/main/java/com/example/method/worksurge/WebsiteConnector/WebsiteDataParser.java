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

    public List<VacancyModel> parseData(Elements jobTitle, Elements jobUndertitle, Elements jobDetails)
    {
        if(jobTitle == null || jobUndertitle == null || jobDetails == null)
            return null;

        List<String> title = stripHtmlTags(jobTitle);
        List<String> undertitle = stripHtmlTags(jobUndertitle);
        List<String> details = stripHtmlTags(jobDetails);
        return attachToVacancyModelList(title, undertitle, details);
    }

    private List<String> stripHtmlTags(Elements elements)
    {
        List<String> list = new ArrayList();

        // Strip ALL html tags from any lines, and detect if any lines are empty so we delete those.
        for(int i = 0; i < elements.size(); i++) // TODO: Optimize?
        {
            list.add(elements.get(i).toString());
            list.set(i, Jsoup.parse(list.get(i)).text().toString());
        }

        return list;
    }

    public List<VacancyModel> attachToVacancyModelList(List<String> title, List<String> undertitle, List<String> details)
    {
        List<VacancyModel> vacancyModel = new ArrayList();

        /*
            In case Elements objects are of an uneven number, we have encountered an issue
            The issue is that an element is missing something, this will jumble up all information
            that we will print to the user. We need to know which vacancy is missing information so we
            can fill the gip appropriatly. Currently it fills the gap with information from another
            vacancy.
         */
        int min = determineSmallestSize(title, undertitle, details);

        for(int i = 0; i < min; i++)
        {
            vacancyModel.add(attachToModel(title.get(i), undertitle.get(i), details.get(i)));
        }

        return vacancyModel;
    }

    private VacancyModel attachToModel(String title, String undertitle, String details)
    {
        VacancyModel model = new VacancyModel();

        model.setTitle(title);
        model.setUndertitle(undertitle);
        model.setDetails(details);

        return model;
    }

    // temp
    private int determineSmallestSize(List<String> title, List<String> undertitle, List<String> details)
    {
        int min = Math.min(Math.min(title.size(), undertitle.size()), details.size());

        return min ;
    }
}
