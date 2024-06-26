package project.repository;

import project.domain.DeveloperCompany;
import lombok.extern.log4j.Log4j2;
import project.connection.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Log4j2
public class DeveloperCompanyRepository {
    public static void save(DeveloperCompany developerCompany) {
        String sql = "INSERT INTO `games_store`.`developer_company` (`name`) VALUES ('%s');".formatted(developerCompany.getName());
        try (Connection connection = ConnectionFactory.getConnection();
             Statement stmt = connection.createStatement()) {
            int rowsAffected = stmt.executeUpdate(sql);
            log.info(rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
