import database.Database;

import java.sql.Date;
import java.util.Arrays;

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
                "jane@gmai.com",
                "American",
                "New York, Cubao");

        System.out.println(resident.create(fields, resident));
    }
}
