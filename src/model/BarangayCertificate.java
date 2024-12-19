package model;

import database.Database;

public class BarangayCertificate extends Database<BarangayCertificate> {
    private int id;
    private String full_name;
    private String complete_address;
    private String additional_certification;
    private String reason_for_request;
    private String status;

    public BarangayCertificate(String full_name, String complete_address, String additional_certification, String reason_for_request, String status) {
        this.full_name = full_name;
        this.complete_address = complete_address;
        this.additional_certification = additional_certification;
        this.reason_for_request = reason_for_request;
        this.status = status;
    }

    public BarangayCertificate(){}

    public String getFullName() {
        return full_name;
    }

    public String getCompleteAddress() {
        return complete_address;
    }

    public String getAdditionalCertification() {
        return additional_certification;
    }

    public String getStatus() {
        return status;
    }

    public String getReasonForRequest() {
        return reason_for_request;
    }

    public int getId() {
        return id;
    }

    @Override
    protected String getDatabaseName() {
        return "barangay_certificates";
    }
}
