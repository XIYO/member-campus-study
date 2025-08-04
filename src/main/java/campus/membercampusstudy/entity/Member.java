package campus.membercampusstudy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 회원 엔티티
 * <p>
 * 테이블: {@code member} - JPA와 MyBatis 공용
 * 
 * @author XIYO
 * @since 2025-08-02
 */
@Entity
@Table(name = "member")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false, length = 100)
    private String email;
    
    @Column(nullable = false, length = 50)
    private String name;
    
    @Column(nullable = false, length = 20)
    private String phone;
    
    @Column(length = 10)
    private Integer age;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;
    
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    // Profile 관계는 제거 - 필요시 Repository를 통해 조회
    
    public enum Gender {
        MALE, FEMALE, OTHER
    }
}