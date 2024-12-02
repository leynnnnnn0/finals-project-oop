import java.util.Date;

public class Resident {
    private String first_name;
    private String middle_name;
    private String last_name;
    private Date date_of_birth;
    private String sex;
    private String contact_number;
    private String email;
    private String nationality;
    private String complete_address;

    public Resident(String first_name, String middle_name, String last_name, Date date_of_birth, String sex, String contact_number, String email, String nationality, String complete_address) {
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        this.date_of_birth = date_of_birth;
        this.sex = sex;
        this.contact_number = contact_number;
        this.email = email;
        this.nationality = nationality;
        this.complete_address = complete_address;
    }



}
