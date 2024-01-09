package vn.hdweb.team9.domain.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter @Getter
public class SiteSetting {

    @NotEmpty
    private String key;

    private String value;

}
