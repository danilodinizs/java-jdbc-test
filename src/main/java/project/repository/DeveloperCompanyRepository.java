package project.repository;

import project.domain.DeveloperCompany;
import lombok.extern.log4j.Log4j2;
import project.connection.ConnectionFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    public static void findByName(DeveloperCompany developerCompany) {
        String sql = "SELECT developer_companyId, name FROM games_store.developer_company WHERE name LIKE '%%%s%%';"
                .formatted(developerCompany.getName());
        log.info("Finding Developer company mentioned");
        try (Connection connection = ConnectionFactory.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)) {
            if (resultSet.next()) {
                var id = resultSet.getInt("developer_companyid");
                var name = resultSet.getString("name");
                DeveloperCompany dc = DeveloperCompany.builder().id(id).name(name).build();
                log.info("Developer company has found: " + dc);
            }

        } catch (SQLException e) {
            log.error("Error while trying to find all Developer Companies", e);
        }
    }
}
