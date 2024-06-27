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
        //DeveloperCompanyService.findAll();
        List<DeveloperCompany> valve = DeveloperCompanyService.findByNameAndUpdate("lv");
        //DeveloperCompanyService.showTypeScrollWorking();
        log.info(valve);

    }
}
