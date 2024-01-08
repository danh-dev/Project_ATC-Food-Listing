package vn.hdweb.team9.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
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

    @OneToMany(mappedBy = "category",
               cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                         CascadeType.DETACH, CascadeType.REFRESH},
               fetch = FetchType.LAZY)
    private List<Food> listFood;
    
    public Category() {
    }
    
    public Category(String categoryName, String slug, String image) {
        this.categoryName = categoryName;
        this.slug = slug;
        this.image = image;
    }
    
    // add convenience methods for bi-directional relationship
    public void add(Food food) {
        if (listFood == null) {
            this.listFood = new ArrayList<>();
        }
        food.setCategory(this);
        this.listFood.add(food);
    }
    
    @Override
    public String toString() {
        return "Category{" + "id=" + id + ", categoryName='" + categoryName + '\'' + ", slug='" + slug + '\'' +
               ", image='" + image + '\'' + ", createdAt=" + createdAt + ", listFood=" + listFood + '}';
    }
}
