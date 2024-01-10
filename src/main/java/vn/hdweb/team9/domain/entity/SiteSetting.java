package vn.hdweb.team9.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter @Getter
public class SiteSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "site_setting_id")
    private Long id;

    @Column(name = "key_setting")
    private String key;

    @Column(name = "value_setting")
    private String value;

}
