package model;

import database.Database;

import java.sql.Time;
import java.util.Date;

public class Blotter extends Database<Blotter> {
    public String reference_number;
    public String complainant_or_reporter;
    public String noted_by;
    public String against;
    public Time reported_time;
    public Date reported_date;
    public String details;

    public Blotter(){}

    public Blotter(String reference_number, String complainant_or_reporter, String noted_by,
                   String against, Time reported_time, Date reported_date, String details) {
        this.reference_number = reference_number;
        this.complainant_or_reporter = complainant_or_reporter;
        this.noted_by = noted_by;
        this.against = against;
        this.reported_time = reported_time;
        this.reported_date = reported_date;
        this.details = details;
    }

    public String getReference_number() {
        return reference_number;
    }

    public String getComplainant_or_reporter() {
        return complainant_or_reporter;
    }

    public String getNoted_by() {
        return noted_by;
    }

    public String getAgainst() {
        return against;
    }

    public Time getReported_time() {
        return reported_time;
    }

    public Date getReported_date() {
        return reported_date;
    }

    public String getDetails() {
        return details;
    }

    @Override
    protected String getDatabaseName() {
        return "blotters";
    }
}