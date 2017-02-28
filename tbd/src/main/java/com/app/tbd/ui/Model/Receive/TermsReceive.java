package com.app.tbd.ui.Model.Receive;

/*
 * Created by Metech USer
 */

 /* Response From API */

import java.util.List;

public class TermsReceive {

    public TermsReceive getTermObj() {
        return termObj;
    }

    private final TermsReceive termObj;
    private String status;
    private String message;

    private List<Term> term;

    public TermsReceive(TermsReceive param_termObj) {
        this.termObj = param_termObj;
    }


    public List<Term> getTerm() {
        return term;
    }

    public void setTerm(List<Term> term) {
        this.term = term;
    }

    public class Term {

        private String id;
        private String title;
        private String body;



        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }




    }


    public String getStatus() {
        return status;
    }

    public String getMessage() {return message;}

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
