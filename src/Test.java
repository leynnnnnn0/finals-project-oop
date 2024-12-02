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
        Database<Resident> database = new Database<>();

        Resident res = new Resident("Jashreil",
                "",
                "Almoguerra",
                Date.valueOf("2004-07-15"),
                "Femela",
                "09323232",
                "jashreil@gmai.com",
                "Filipino",
                "Paranaque, Manila");

        System.out.println(database.tableName("residents").create(fields, res));
    }
}
