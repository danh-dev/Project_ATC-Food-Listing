package vn.hdweb.team9.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "restaurant")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long id;

    @Column(name = "restaurant_name")
    private String restaurantName;

    @Column(name = "slug")
    private String slug;

    @Column(name = "description")
    private String description;

    @Column(name = "address")
    private String address;

    @Column(name = "logo")
    private String logo;

    @Column(name = "image")
    private String image;

    @Column(name = "open_time")
    private String openTime;

    @Column(name = "close_time")
    private String closeTime;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "create_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "restaurant",
               cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                           CascadeType.DETACH, CascadeType.REFRESH},
               fetch = FetchType.LAZY)
    private List<Food> listFood = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<RatingRestaurant> listRatingRestaurant = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Order> listOrder = new ArrayList<>();
    
    public Restaurant() {
    }
    
    public Restaurant(String restaurantName,
                      String slug,
                      String description,
                      String address,
                      String logo,
                      String image,
                      String openTime,
                      String closeTime,
                      boolean isActive
                     ) {
        this.restaurantName = restaurantName;
        this.slug = slug;
        this.description = description;
        this.address = address;
        this.logo = logo;
        this.image = image;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.isActive = isActive;
    }
    
    // add convenience methods for bi-directional relationship
    public void add(Food food) {
        if (listFood == null) {
            this.listFood = new ArrayList<>();
        }
        food.setRestaurant(this);
        this.listFood.add(food);
    }
}
