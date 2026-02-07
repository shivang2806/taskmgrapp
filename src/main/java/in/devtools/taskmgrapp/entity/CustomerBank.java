package in.devtools.taskmgrapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer_bank")
public class CustomerBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔹 FK → customers.id
    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false, unique = true)
    @OnDelete(action = OnDeleteAction.CASCADE) // DB-level cascade
    private Customer customer;

    private String bankName;
    private String accountHolderName;
    private String accountNumber;
    private String accountType;
    private String ifscCode;
    private String branchName;
    @Column(nullable = false)
    private Boolean isVerified = false;

    private LocalDateTime verifiedAt;

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
