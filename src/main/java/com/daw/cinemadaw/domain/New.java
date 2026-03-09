package com.daw.cinemadaw.domain;

public class New {
    private String headline;
    private String body;

    public New(String body, String headline) {
        this.body = body;
        this.headline = headline;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


}
