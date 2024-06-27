package project.repository;

import project.domain.DeveloperCompany;
import lombok.extern.log4j.Log4j2;
import project.connection.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DeveloperCompanyRepository {
    public static void insert(DeveloperCompany developerCompany) {
        String sql = "INSERT INTO `games_store`.`developer_company` (`name`) VALUES ('%s');".formatted(developerCompany.getName());
        try (Connection connection = ConnectionFactory.getConnection();
             Statement stmt = connection.createStatement()) {
            int rowsAffected = stmt.executeUpdate(sql);
            log.info("Inserted Developer Company '{}' in the database, rows affected '{}'", developerCompany.getId(), rowsAffected);
        } catch (SQLException e) {
            log.error("Error while trying to insert Developer Company '{}'", developerCompany.getId(), e);
        }
    }

    public static void delete(DeveloperCompany developerCompany) {
        String sql = "DELETE FROM `games_store`.`developer_company` WHERE (`developer_companyid` = '%d');".formatted(developerCompany.getId());
        try (Connection connection = ConnectionFactory.getConnection();
             Statement stmt = connection.createStatement()) {
            int rowsAffected = stmt.executeUpdate(sql);
            log.info("Deleted Developer Company '{}', rows affected '{}'", developerCompany.getId(), rowsAffected);
        } catch (SQLException e) {
            log.error("Error while trying to delete Developer Company '{}'", developerCompany.getId(), e);
        }
    }

    public static void update(DeveloperCompany developerCompany) {
        String sql = "UPDATE `games_store`.`developer_company` SET `name` = '%s' WHERE (`developer_companyID` = '%d');"
                .formatted(developerCompany.getName(), developerCompany.getId());
        try (Connection connection = ConnectionFactory.getConnection();
             Statement stmt = connection.createStatement()) {
            int rowsAffected = stmt.executeUpdate(sql);
            log.info("Updated Developer Company '{}', rows affected '{}'", developerCompany.getId(), rowsAffected);
        } catch (SQLException e) {
            log.error("Error while trying to update Developer Company '{}'", developerCompany.getId(), e);
        }
    }
    public static void findAll() {
        String sql = "SELECT developer_companyId, name FROM games_store.developer_company;";
        log.info("Finding all Developer companies");
        try (Connection connection = ConnectionFactory.getConnection();
             Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql)) {
            List<DeveloperCompany> dcList = new ArrayList<>();
            while(resultSet.next()) {
                var id = resultSet.getInt("developer_companyid");
                var name = resultSet.getString("name");
                DeveloperCompany dc = DeveloperCompany.builder().id(id).name(name).build();
                dcList.add(dc);
            }

            for (DeveloperCompany d : dcList) {
                log.info(d);
            }
        } catch (SQLException e) {
            log.error("Error while trying to find all Developer Companies", e);
        }
    }
    public static List<DeveloperCompany> findByName(String name) {
        String sql = "SELECT developer_companyId, name FROM games_store.developer_company WHERE name LIKE '%%%s%%';"
                .formatted(name);
        log.info("Finding Developer company mentioned");
        List<DeveloperCompany> dcList = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)) {
            while (resultSet.next()) {
                DeveloperCompany dc = DeveloperCompany
                        .builder()
                        .id(resultSet.getInt("developer_companyid"))
                        .name(resultSet.getString("name"))
                        .build();
                dcList.add(dc);
            }
        } catch (SQLException e) {
            log.error("Error while trying to find all Developer Companies", e);
        }
        return dcList;
    }
    public static void showDCMetaData( ) {
        String sql = "SELECT * FROM games_store.developer_company;";
        log.info("Showing Developer Company MetaData");
        try (Connection connection = ConnectionFactory.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            log.info(columnCount);
            for (int i = 1; i <= columnCount; i++) {
                log.info("Table name '{}", metaData.getTableName(i));
                log.info("Column name '{}", metaData.getColumnName(i));
                log.info("Column size '{}", metaData.getColumnDisplaySize(i));
                log.info("Column type '{}", metaData.getColumnTypeName(i));
            }

//            if (resultSet.next()) {
//                var id = resultSet.getInt("developer_companyid");
//                var name = resultSet.getString("name");
//                DeveloperCompany dc = DeveloperCompany.builder().id(id).name(name).build();
//                log.info("Developer company has found: " + dc);
//            }

        } catch (SQLException e) {
            log.error("Error while trying to find all Developer Companies", e);
        }
    }

    public static void showDriverMetaData( ) {
        String sql = "SELECT * FROM games_store.developer_company;";
        log.info("Showing Driver MetaData");
        try (Connection connection = ConnectionFactory.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            if(metaData.supportsResultSetType(ResultSet.TYPE_FORWARD_ONLY)) {
                log.info("Supports TYPE_FORWARD_ONLY");
                if (metaData.supportsResultSetConcurrency(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE)) {
                    log.info("and Supports CONCUR_UPDATABLE");
                }
            }

            if(metaData.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE)) {
                log.info("Supports TYPE_SCROLL_INSENSITIVE");
                if (metaData.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                    log.info("and Supports CONCUR_UPDATABLE");
                }
            }

            if(metaData.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE)) {
                log.info("Supports TYPE_SCROLL_SENSITIVE");
                if (metaData.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                    log.info("and Supports CONCUR_UPDATABLE");
                }
            }


        } catch (SQLException e) {
            log.error("Error while trying to find all Developer Companies", e);
        }
    }

    public static void showTypeScrollWorking() {
        String sql = "SELECT * FROM games_store.developer_company;";
        try (Connection connection = ConnectionFactory.getConnection();
             Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
             ResultSet resultSet = stmt.executeQuery(sql)) {
            log.info(resultSet.first());
            log.info(DeveloperCompany.builder().id(resultSet.getInt("developer_companyid")).name(resultSet.getString("name")).build());
        } catch (SQLException e) {
            log.error("Error while trying to find all Developer Companies", e);
        }
    }

    public static List<DeveloperCompany> findByNameAndUpdate(String name) {
        log.info("Updating Developer Company to UpperCase");
        String sql = "SELECT developer_companyId, name FROM games_store.developer_company WHERE name LIKE '%%%s%%';"
                .formatted(name);
        List<DeveloperCompany> dcList = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
             ResultSet resultSet = stmt.executeQuery(sql)) {
            while (resultSet.next()) {
                resultSet.updateString("name", resultSet.getString("name").toUpperCase());
                resultSet.updateRow();
                DeveloperCompany dc = DeveloperCompany
                        .builder()
                        .id(resultSet.getInt("developer_companyid"))
                        .name(resultSet.getString("name"))
                        .build();
                dcList.add(dc);
            }

        } catch (SQLException e) {
            log.error("Error while trying to find all Developer Companies", e);
        }
        return dcList;
    }
}
