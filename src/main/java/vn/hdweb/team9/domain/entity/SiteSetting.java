package vn.hdweb.team9.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter @Getter
public class SiteSetting {

    @NotEmpty
    @Id
    private String key;

    private String value;

}
