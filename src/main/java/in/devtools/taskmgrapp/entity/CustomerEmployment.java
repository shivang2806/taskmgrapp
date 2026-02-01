package in.devtools.taskmgrapp.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "customer_employment")
public class CustomerEmployment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔹 FK → customers.id
    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false, unique = true)
    @OnDelete(action = OnDeleteAction.CASCADE) // DB-level cascade
    private Customer customer;

    private String companyName;
    private String salary;
    private String location;
    private Integer experience;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
