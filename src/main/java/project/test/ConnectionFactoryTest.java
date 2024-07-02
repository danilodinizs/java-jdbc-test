package project.test;

import project.domain.DeveloperCompany;
import lombok.extern.log4j.Log4j2;
import project.connection.ConnectionFactory;
import project.repository.DeveloperCompanyRepository;
import project.repository.DeveloperCompanyRepositoryRS;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;


@Log4j2
public class ConnectionFactoryTest {
    public static void main(String[] args) {
        //DeveloperCompany developerCompany = DeveloperCompany.builder().name("Valve").build();
        //DeveloperCompanyService.insert(developerCompany);
//        System.out.println(DeveloperCompanyService.findByNameCallableStatement("oc"));
        //DeveloperCompanyService.findByNameCallableStatement("oc");

        //DeveloperCompanyService.showTypeScrollWorking();
        //List<DeveloperCompany> valve = DeveloperCompanyService.findByNameAndUpdate("lv");
        //DeveloperCompanyService.showTypeScrollWorking();

        //List<DeveloperCompany> riotGomes = DeveloperCompanyService.findByNamePreparedStatement("ri");
//        for (DeveloperCompany d : riotGomes) {
//            System.out.println(d);
//      }
        DeveloperCompany dc1 = DeveloperCompany.builder().name("Valve").build();
        DeveloperCompany dc2 = DeveloperCompany.builder().name("Mojang").build();
        DeveloperCompany dc3 = DeveloperCompany.builder().name("Blizzard").build();

        //DeveloperCompanyService.updatePreparedStatement(dc);
        DeveloperCompanyRepository.saveTransaction(List.of(dc1, dc2, dc3));
    }
}

