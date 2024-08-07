package project.repository;

import project.domain.DeveloperCompany;
import lombok.extern.log4j.Log4j2;
import project.connection.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

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

    public static void saveTransaction(List<DeveloperCompany> developerCompany) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            connection.setAutoCommit(false);
            preparedStatementSaveTransaction(connection, developerCompany);
            connection.commit();
        } catch (SQLException e) {
            log.error("Error while trying to update Developer Company '{}'", developerCompany, e);
        }
    }

    private static void preparedStatementSaveTransaction(Connection connection, List<DeveloperCompany> developerCompany) throws SQLException {
        String sql = "INSERT INTO `games_store`.`developer_company` (`name`) VALUES (?);";
        for (DeveloperCompany dc : developerCompany) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                log.info("Saving producer '{}'", dc.getName());
                ps.setString(1, dc.getName());
                ps.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
        String sql = "CALL `games_store`.`sp_get_devcompany_by_name`('%%%s%%');"
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
            log.info(resultSet.last());
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

    public static List<DeveloperCompany> findByNameAndInsertIfNull(String name) {
        log.info("Inserting Developer Company");
        String sql = "SELECT developer_companyId, name FROM games_store.developer_company WHERE name LIKE '%%%s%%';"
                .formatted(name);
        List<DeveloperCompany> dcList = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
             ResultSet resultSet = stmt.executeQuery(sql)) {
            if (resultSet.next()) return dcList;
                resultSet.moveToInsertRow();
                resultSet.updateString("name", name);
                resultSet.insertRow();
                resultSet.moveToCurrentRow();
                resultSet.beforeFirst();
                resultSet.next();
                DeveloperCompany dc = DeveloperCompany
                        .builder()
                        .id(resultSet.getInt("developer_companyid"))
                        .name(resultSet.getString("name"))
                        .build();
                dcList.add(dc);


        } catch (SQLException e) {
            log.error("Error while trying to find all Developer Companies", e);
        }
        return dcList;
    }
    public static List<DeveloperCompany> findByNameAndDelete(String name) {
        log.info("Deleting Developer Company");
        String sql = "SELECT developer_companyId, name FROM games_store.developer_company WHERE name LIKE '%s';"
                .formatted(name);
        List<DeveloperCompany> dcList = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
             ResultSet resultSet = stmt.executeQuery(sql)) {
            if (resultSet.next()) resultSet.deleteRow();


        } catch (SQLException e) {
            log.error("Error while trying to find all Developer Companies", e);
        }
        return dcList;
    }

    public static List<DeveloperCompany> findByNamePreparedStatement(String name) {
        String sql = "SELECT developer_companyId, name FROM games_store.developer_company WHERE name LIKE ?;";
        log.info("Finding Developer company mentioned");
        List<DeveloperCompany> dcList = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = createPreparedStatement(connection, sql, name);
             ResultSet resultSet = ps.executeQuery()) {

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

    private static PreparedStatement createPreparedStatement(Connection connection, String sql, String name) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, String.format("%%%s%%", name) );
        return ps;
    }

    public static void updatePreparedStatement(DeveloperCompany developerCompany) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = preparedStatementUpdate(connection, developerCompany)) {
            int rowsAffected = ps.executeUpdate();
            log.info("Updated Developer Company '{}', rows affected '{}'", developerCompany.getId(), rowsAffected);
        } catch (SQLException e) {
            log.error("Error while trying to update Developer Company '{}'", developerCompany.getId(), e);
        }
    }
    private static PreparedStatement preparedStatementUpdate(Connection connection, DeveloperCompany developerCompany) throws SQLException {
        String sql = "UPDATE `games_store`.`developer_company` SET `name` = ? WHERE (`developer_companyID` = ?);";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, developerCompany.getName());
        ps.setInt(2,  developerCompany.getId());
        return ps;
    }
    public static List<DeveloperCompany> findByNameCallableStatement(String name) {
        log.info("Finding Developer company mentioned");
        List<DeveloperCompany> dcList = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = createCallableStatement(connection, name);
             ResultSet resultSet = ps.executeQuery()) {

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
    private static PreparedStatement createCallableStatement(Connection connection, String name) throws SQLException {
        String sql = "CALL `games_store`.`sp_get_devcompany_by_name`(?);";
        CallableStatement cs = connection.prepareCall(sql);
        cs.setString(1, String.format("%%%s%%", name));
        return cs;
    }
    public static void logr() {
        log.info("test");
    }
}
