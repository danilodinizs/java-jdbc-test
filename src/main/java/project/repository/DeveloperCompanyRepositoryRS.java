package project.repository;

import lombok.extern.log4j.Log4j2;
import project.connection.ConnectionFactory;
import project.domain.DeveloperCompany;
import project.listener.CustomRowSetListener;

import javax.sql.rowset.JdbcRowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DeveloperCompanyRepositoryRS {
    public static List<DeveloperCompany> findByNameJdbcRowSet(String name) {
        String sql = "CALL `games_store`.`sp_get_devcompany_by_name`(?);";
        List<DeveloperCompany> developerCompanies = new ArrayList<>();
        try(JdbcRowSet jrs = ConnectionFactory.getJdbcRowSet()) {
            jrs.setCommand(sql);
            jrs.setString(1, String.format("%%%s%%", name));
            jrs.execute();
            while (jrs.next()) {
                DeveloperCompany dc = DeveloperCompany.builder()
                        .id(jrs.getInt("developer_companyid"))
                        .name(jrs.getString("name"))
                        .build();
                developerCompanies.add(dc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return developerCompanies;
    }
    public static void updateJdbcRowSet(DeveloperCompany developerCompany) {
        String sql = "SELECT * FROM `games_store`.`developer_company` WHERE (`developer_companyID` = ?);";
        try(JdbcRowSet jrs = ConnectionFactory.getJdbcRowSet()) {
            jrs.addRowSetListener(new CustomRowSetListener());
            jrs.setCommand(sql);
            jrs.setInt(1, developerCompany.getId());
            jrs.execute();
            if(!jrs.next()) return;
            jrs.updateString("name", developerCompany.getName());
            jrs.updateRow();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DeveloperCompany developerCompany = DeveloperCompany.builder().id(1).name("Riot Gomes").build();
        updateJdbcRowSet(developerCompany);
    }
}
