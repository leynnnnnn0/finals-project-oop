import model.Resident;

import java.sql.Date;

public class Test {
    public static void main(String[] args) {
        String[] fields = {
                "first_name",
                "last_name",
                "middle_name",
                "date_of_birth",
                "sex",
                "contact_number",
                "email",
                "nationality",
                "complete_address"
        };

        Resident resident = new Resident("Jane",
                "",
                "Smith",
                Date.valueOf("2004-09-3"),
                "Female",
                "09321323143",
                "janfdfe@gmai.com",
                "American",
                "New York, Cubao");
        resident.create();

//        resident.delete(1);
//        resident.update(fields, resident, "1");


    }
}
