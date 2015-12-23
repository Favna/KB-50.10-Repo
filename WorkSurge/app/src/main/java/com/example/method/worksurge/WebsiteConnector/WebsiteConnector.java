package com.example.method.worksurge.WebsiteConnector;

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
}
