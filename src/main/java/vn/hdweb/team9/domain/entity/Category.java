package vn.hdweb.team9.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "slug")
    private String slug;

    @Column(name = "image")
    private String image;

    @Column(name = "create_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "category")
    private List<Food> listFood = new ArrayList<>();
}
