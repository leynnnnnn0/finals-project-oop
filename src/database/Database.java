package database;

import java.lang.reflect.Field;
import java.sql.*;

public class Database<T> {
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private String tableName;

    public Database() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/barangay_system",
                    "root",
                    ""
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Database<T> tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public boolean create(String[] fields, T model) {
        try {
            String query = String.format(
                    "INSERT INTO %s (%s) VALUES (%s)",
                    this.tableName,
                    String.join(", ", fields),
                    getPlaceholders(fields.length)
            );
            preparedStatement = connection.prepareStatement(query);
            setModelValues(preparedStatement, model, fields);
            System.out.println(preparedStatement);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException | IllegalAccessException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }



    private String getPlaceholders(int count) {
        return String.join(", ", java.util.Collections.nCopies(count, "?"));
    }

    private void setModelValues(PreparedStatement stmt, T model, String[] fields)
            throws SQLException, IllegalAccessException {
        Class<?> modelClass = model.getClass();

        for (int i = 0; i < fields.length; i++) {
            try {
                Field field = modelClass.getDeclaredField(fields[i]);
                System.out.println("FIELD: " + field);
                field.setAccessible(true);

                Object value = field.get(model);
                System.out.println("VALUE" + value);
                stmt.setObject(i + 1, value);
            } catch (NoSuchFieldException e) {
                System.err.println("Field not found: " + fields[i]);
                stmt.setNull(i + 1, Types.NULL);
            }
        }
    }

}