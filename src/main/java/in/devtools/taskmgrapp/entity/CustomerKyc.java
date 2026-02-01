package in.devtools.taskmgrapp.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "customer_kyc")
public class CustomerKyc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔹 FK → customers.id
    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false, unique = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Customer customer;

    @Column(columnDefinition = "json")
    private String panDetails;

    @Column(nullable = false)
    private Boolean panVerified = false;

    private LocalDateTime panVerifiedAt;

    @Column(columnDefinition = "json")
    private String adharDetails;

    @Column(nullable = false)
    private Boolean adharVerified = false;

    private LocalDateTime adharVerifiedAt;

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
