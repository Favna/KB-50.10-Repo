package com.example.method.worksurge.WebsiteConnector;

import com.example.method.worksurge.Model.VacancyDetailModel;
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

    public List<VacancyModel> parseData(Elements jobTitle, Elements jobUndertitle, Elements jobDetails, Elements jobUrl)
    {
        // TODO: Handle Null in WebsiteConnector
        if(jobTitle == null || jobUndertitle == null || jobDetails == null || jobUrl == null)
            return null;

        List<String> title = stripHtmlTags(jobTitle);
        List<String> undertitle = stripHtmlTags(jobUndertitle);
        List<String> details = stripHtmlTags(jobDetails);
        List<String> url = getHrefValue(jobUrl);

        return attachToVacancyModelList(title, undertitle, details, url);
    }

    // For Extra Detail
    public VacancyDetailModel parseData(Elements jobTitle, Elements jobDetails, Elements jobCompany, Elements jobMeta, Elements jobTelefoon)
    {
        // TODO: Handle Null in WebsiteConnector
        if(jobTitle == null || jobDetails == null || jobCompany == null || jobMeta == null || jobTelefoon == null)
            return null;

        List<String> title = stripHtmlTags(jobTitle);
        List<String> details = stripHtmlTags(jobDetails);
        List<String> company = stripHtmlTags(jobCompany);
        List<String> meta = stripHtmlTags(jobMeta);
        List<String> telefoon = stripHtmlTags(jobTelefoon);

        return attachToVacancyModelList(title, details, company, meta, telefoon);
    }

    // For Map Detail
    public String parseData(Elements jobAddress)
    {
        // TODO: Handle Null in WebsiteConnector
        if(jobAddress == null)
            return null;

        if(jobAddress.size() > 1 )
            return null;

        return stripHtmlTags(jobAddress.get(0).text().toString());
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

    // Overloaded to return a single String element
    private String stripHtmlTags(String element)
    {
        return Jsoup.parse(element).text().toString();
    }

    public List<String> getHrefValue(Elements elements)
    {
        List<String> list = new ArrayList();

        // Strip all tags except the href value
        for (int i = 0; i < elements.size(); i++)
            list.add("https://www.randstad.nl" + elements.get(i).attr("href"));

        return list;
    }

    // Base information
    public List<VacancyModel> attachToVacancyModelList(List<String> title, List<String> undertitle, List<String> details, List<String> url)
    {
        List<VacancyModel> vacancyModel = new ArrayList();

        /*
            In case Elements objects are of an uneven number, we have encountered an issue
            The issue is that an element is missing something, this will jumble up all information
            that we will print to the user. We need to know which vacancy is missing information so we
            can fill the gip appropriatly. Currently it fills the gap with information from another
            vacancy.
         */
        int min = determineSmallestSize(title, undertitle, details); // TODO: Add url

        for(int i = 0; i < min; i++)
            vacancyModel.add(attachToModel(title.get(i), undertitle.get(i), details.get(i), url.get(i)));

        return vacancyModel;
    }

    // For Extra Detail
    public VacancyDetailModel attachToVacancyModelList(List<String> title, List<String> detail, List<String> company, List<String> meta, List<String> telefoon)
    {
        VacancyDetailModel vacancyModel = new VacancyDetailModel();

        // Remove unnecessary data
        for(int i = 0; i < detail.size(); i++)
        {
            if(detail.get(i).length() > 100/* Select the correct data in <p></p> instead of this.. Some way.*/)
            {
                String temp = detail.get(i).toString();
                detail = new ArrayList<>();
                detail.add(temp);
            }
        }

        vacancyModel = attachToModel(title.get(0), detail.get(0), company.get(0), meta, telefoon.size() == 0 ? "" : telefoon.get(0));

        return vacancyModel;
    }

    // Base Information
    private VacancyModel attachToModel(String title, String undertitle, String details, String url)
    {
        VacancyModel model = new VacancyModel();

        model.setTitle(title);
        model.setUndertitle(undertitle);
        model.setDetails(details);
        model.setURL(url);

        return model;
    }


    // For Extra Detail
    private VacancyDetailModel attachToModel(String title, String detail, String company, List<String> meta, String telefoon)
    {
        VacancyDetailModel model = new VacancyDetailModel();

        model.setTitle(title);
        model.setDetail(detail);
        model.setCompany(company);
        model.setMeta(meta);
        if(!telefoon.isEmpty())
            model.setTelefoon(telefoon);

        return model;
    }

    // temp
    private int determineSmallestSize(List<String> title, List<String> undertitle, List<String> details)
    {
        int min = Math.min(Math.min(title.size(), undertitle.size()), details.size());

        return min ;
    }
}
