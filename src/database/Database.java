package database;

import java.lang.reflect.Field;
import java.sql.*;

public abstract class Database<T> {
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    protected abstract String getDatabaseName();

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


    public boolean create(String[] fields, T model) {
        try {
            String query = String.format(
                    "INSERT INTO %s (%s) VALUES (%s)",
                    getDatabaseName(),
                    String.join(", ", fields),
                    getPlaceholders(fields.length)
            );
            preparedStatement = connection.prepareStatement(query);
            setModelValues(preparedStatement, model, fields);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException | IllegalAccessException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean delete(int id){
        try{
            String query = String.format("DELETE FROM %s WHERE id = %d", getDatabaseName(), id);
            preparedStatement = connection.prepareStatement(query);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean update(String[] fields, T model, String id) {
        try {
            String query = String.format(
                    "UPDATE %s SET %s WHERE id = %s",
                    this.getDatabaseName(),
                    getPlaceholders(fields),
                    id
            );

            preparedStatement = connection.prepareStatement(query);

            setModelValues(preparedStatement, model, fields);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException | IllegalAccessException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public StringBuilder getPlaceholders(String[] fields) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < fields.length; i++) {
            builder.append(fields[i]).append(" = ?");
            if(i < fields.length - 1) {
                builder.append(", ");
            }
        }
        return builder;
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
                field.setAccessible(true);

                Object value = field.get(model);
                stmt.setObject(i + 1, value);
            } catch (NoSuchFieldException e) {
                System.err.println("Field not found: " + fields[i]);
                stmt.setNull(i + 1, Types.NULL);
            }
        }
    }

}