package campus.membercampusstudy.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 회원 프로필 엔티티
 * <p>
 * 테이블: {@code profile} - JPA와 MyBatis 공용<br>
 * Member와 1:1 관계
 * 
 * @author XIYO
 * @since 2025-08-02
 */
@Entity
@Table(name = "profile")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "member_id", nullable = false, unique = true)
    private Long memberId;
    
    @Column(name = "nickname", length = 50)
    private String nickname;
    
    @Column(name = "name", length = 50)
    private String name;
    
    @Column(name = "profile_image_url", length = 500)
    private String profileImageUrl;
    
    @Column(name = "postal_code", length = 10)
    private String postalCode;
    
    @Column(name = "address", length = 200)
    private String address;
    
    @Column(name = "address_detail", length = 200)
    private String addressDetail;
    
    @Column(name = "mobile_phone", length = 20)
    private String mobilePhone;
    
    @Column(name = "memo", length = 1000)
    private String memo;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    /**
     * 정적 팩토리 메서드 - 회원 ID와 함께 프로필 생성
     */
    public static Profile createWithMemberId(Long memberId, String nickname, String name, String profileImageUrl,
                                             String postalCode, String address, String addressDetail,
                                             String mobilePhone, String memo) {
        Profile profile = new Profile();
        profile.memberId = memberId;
        profile.nickname = nickname;
        profile.name = name;
        profile.profileImageUrl = profileImageUrl;
        profile.postalCode = postalCode;
        profile.address = address;
        profile.addressDetail = addressDetail;
        profile.mobilePhone = mobilePhone;
        profile.memo = memo;
        return profile;
    }
    
    /**
     * 프로필 정보 업데이트
     */
    public void updateProfile(String nickname, String name, String profileImageUrl, String postalCode, 
                             String address, String addressDetail, String mobilePhone, String memo) {
        this.nickname = nickname;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.postalCode = postalCode;
        this.address = address;
        this.addressDetail = addressDetail;
        this.mobilePhone = mobilePhone;
        this.memo = memo;
    }
}