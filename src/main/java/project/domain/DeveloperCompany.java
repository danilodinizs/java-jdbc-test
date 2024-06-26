package project.domain;

import lombok.Builder;
import lombok.Value;
import lombok.Data;
import java.util.Objects;

@Value
@Builder
public class DeveloperCompany {
    Integer id;
    String name;
}
