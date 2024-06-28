package project.test;

import lombok.extern.log4j.Log4j2;
import project.domain.DeveloperCompany;
import project.repository.DeveloperCompanyRepository;
import project.service.DeveloperCompanyService;

import java.util.List;

@Log4j2
public class ConnectionFactoryTest {
    public static void main(String[] args) {
        //DeveloperCompany developerCompany = DeveloperCompany.builder().name("Valve").build();
        //DeveloperCompanyService.insert(developerCompany);
        //DeveloperCompanyService.findByName(DeveloperCompany.builder().name("iso").build());
        //DeveloperCompanyService.showTypeScrollWorking();
        //List<DeveloperCompany> valve = DeveloperCompanyService.findByNameAndUpdate("lv");
        //DeveloperCompanyService.showTypeScrollWorking();

        //List<DeveloperCompany> riotGomes = DeveloperCompanyService.findByNamePreparedStatement("ri");
//        for (DeveloperCompany d : riotGomes) {
//            System.out.println(d);
//      }
        DeveloperCompany dc = DeveloperCompany.builder().id(1).name("Riot Games").build();
        DeveloperCompanyService.updatePreparedStatement(dc);
    }
}

