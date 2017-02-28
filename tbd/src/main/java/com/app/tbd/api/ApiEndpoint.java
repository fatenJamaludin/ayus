package com.app.tbd.api;

import retrofit.Endpoint;

public class ApiEndpoint implements Endpoint {

    @Override
    public String getUrl() {

        /*LIVE*/
        /*return "https://appapi.airasiabig.com";*/

       /*STAGING*/
        return "http://airasiabig.me-tech.com.my";

    }

    @Override
    public String getName() {
        return "Production Endpoint";
    }
    //
}
