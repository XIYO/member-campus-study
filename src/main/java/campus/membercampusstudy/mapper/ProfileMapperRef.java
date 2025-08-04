package campus.membercampusstudy.mapper;

import campus.membercampusstudy.entity.Profile;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 회원 프로필 매퍼 인터페이스
 * <p>
 * MyBatis 프로필 매퍼의 공통 인터페이스입니다.
 * 학습용(ProfileMapper)과 구현체(ProfileMapperRef) 모두 이 인터페이스를 구현합니다.
 * IProfile 인터페이스를 상속받아 JPA Repository와 일관된 인터페이스를 제공합니다.
 */
@Mapper
@org.springframework.context.annotation.Profile("ref")
public interface ProfileMapperRef extends IProfileMapper {
    
    /**
     * 프로필 등록
     */
    @Insert("""
            INSERT INTO profile (member_id, nickname, name, profile_image_url, postal_code, address, address_detail, mobile_phone, memo, created_at, updated_at)
            VALUES (#{memberId}, #{nickname}, #{name}, #{profileImageUrl}, #{postalCode}, #{address}, #{addressDetail}, #{mobilePhone}, #{memo}, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertProfile(Profile profile);
    
    /**
     * 전체 프로필 조회
     */
    @Select("SELECT * FROM profile")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "memberId", column = "member_id"),
            @Result(property = "nickname", column = "nickname"),
            @Result(property = "name", column = "name"),
            @Result(property = "profileImageUrl", column = "profile_image_url"),
            @Result(property = "postalCode", column = "postal_code"),
            @Result(property = "address", column = "address"),
            @Result(property = "addressDetail", column = "address_detail"),
            @Result(property = "mobilePhone", column = "mobile_phone"),
            @Result(property = "memo", column = "memo"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    List<Profile> findAllProfiles();
    
    /**
     * ID로 프로필 조회
     */
    @Select("SELECT * FROM profile WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "memberId", column = "member_id"),
            @Result(property = "nickname", column = "nickname"),
            @Result(property = "name", column = "name"),
            @Result(property = "profileImageUrl", column = "profile_image_url"),
            @Result(property = "postalCode", column = "postal_code"),
            @Result(property = "address", column = "address"),
            @Result(property = "addressDetail", column = "address_detail"),
            @Result(property = "mobilePhone", column = "mobile_phone"),
            @Result(property = "memo", column = "memo"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    Profile findProfileById(@Param("id") Long id);
    
    /**
     * 회원 ID로 프로필 조회
     */
    @Select("SELECT * FROM profile WHERE member_id = #{memberId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "memberId", column = "member_id"),
            @Result(property = "nickname", column = "nickname"),
            @Result(property = "name", column = "name"),
            @Result(property = "profileImageUrl", column = "profile_image_url"),
            @Result(property = "postalCode", column = "postal_code"),
            @Result(property = "address", column = "address"),
            @Result(property = "addressDetail", column = "address_detail"),
            @Result(property = "mobilePhone", column = "mobile_phone"),
            @Result(property = "memo", column = "memo"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    Profile findProfileByMemberId(@Param("memberId") Long memberId);
    
    /**
     * 닉네임으로 프로필 조회
     */
    @Select("SELECT * FROM profile WHERE nickname = #{nickname}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "memberId", column = "member_id"),
            @Result(property = "nickname", column = "nickname"),
            @Result(property = "name", column = "name"),
            @Result(property = "profileImageUrl", column = "profile_image_url"),
            @Result(property = "postalCode", column = "postal_code"),
            @Result(property = "address", column = "address"),
            @Result(property = "addressDetail", column = "address_detail"),
            @Result(property = "mobilePhone", column = "mobile_phone"),
            @Result(property = "memo", column = "memo"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    Profile findProfileByNickname(@Param("nickname") String nickname);
    
    /**
     * 닉네임 중복 확인
     */
    @Select("SELECT COUNT(*) FROM profile WHERE nickname = #{nickname}")
    int countByNickname(@Param("nickname") String nickname);
    
    /**
     * 휴대전화번호로 프로필 조회
     */
    @Select("SELECT * FROM profile WHERE mobile_phone = #{mobilePhone}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "memberId", column = "member_id"),
            @Result(property = "nickname", column = "nickname"),
            @Result(property = "name", column = "name"),
            @Result(property = "profileImageUrl", column = "profile_image_url"),
            @Result(property = "postalCode", column = "postal_code"),
            @Result(property = "address", column = "address"),
            @Result(property = "addressDetail", column = "address_detail"),
            @Result(property = "mobilePhone", column = "mobile_phone"),
            @Result(property = "memo", column = "memo"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    Profile findProfileByMobilePhone(@Param("mobilePhone") String mobilePhone);
    
    /**
     * 닉네임으로 검색 (부분일치)
     */
    @Select("SELECT * FROM profile WHERE nickname LIKE CONCAT('%', #{nickname}, '%')")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "memberId", column = "member_id"),
            @Result(property = "nickname", column = "nickname"),
            @Result(property = "name", column = "name"),
            @Result(property = "profileImageUrl", column = "profile_image_url"),
            @Result(property = "postalCode", column = "postal_code"),
            @Result(property = "address", column = "address"),
            @Result(property = "addressDetail", column = "address_detail"),
            @Result(property = "mobilePhone", column = "mobile_phone"),
            @Result(property = "memo", column = "memo"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    List<Profile> findByNicknameContaining(@Param("nickname") String nickname);
    
    /**
     * 이름으로 검색 (부분일치)
     */
    @Select("SELECT * FROM profile WHERE name LIKE CONCAT('%', #{name}, '%')")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "memberId", column = "member_id"),
            @Result(property = "nickname", column = "nickname"),
            @Result(property = "name", column = "name"),
            @Result(property = "profileImageUrl", column = "profile_image_url"),
            @Result(property = "postalCode", column = "postal_code"),
            @Result(property = "address", column = "address"),
            @Result(property = "addressDetail", column = "address_detail"),
            @Result(property = "mobilePhone", column = "mobile_phone"),
            @Result(property = "memo", column = "memo"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    List<Profile> findByNameContaining(@Param("name") String name);
    
    /**
     * 우편번호로 검색
     */
    @Select("SELECT * FROM profile WHERE postal_code = #{postalCode}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "memberId", column = "member_id"),
            @Result(property = "nickname", column = "nickname"),
            @Result(property = "name", column = "name"),
            @Result(property = "profileImageUrl", column = "profile_image_url"),
            @Result(property = "postalCode", column = "postal_code"),
            @Result(property = "address", column = "address"),
            @Result(property = "addressDetail", column = "address_detail"),
            @Result(property = "mobilePhone", column = "mobile_phone"),
            @Result(property = "memo", column = "memo"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    List<Profile> findByPostalCode(@Param("postalCode") String postalCode);
    
    /**
     * 프로필 정보 수정
     */
    @Update("""
            UPDATE profile 
            SET nickname = #{nickname}, name = #{name}, profile_image_url = #{profileImageUrl}, 
                postal_code = #{postalCode}, address = #{address}, address_detail = #{addressDetail}, 
                mobile_phone = #{mobilePhone}, memo = #{memo}, updated_at = CURRENT_TIMESTAMP 
            WHERE id = #{id}
            """)
    void updateProfile(Profile profile);
    
    /**
     * 프로필 삭제
     */
    @Delete("DELETE FROM profile WHERE id = #{id}")
    void deleteProfile(@Param("id") Long id);
    
    /**
     * 회원 ID로 프로필 삭제
     */
    @Delete("DELETE FROM profile WHERE member_id = #{memberId}")
    void deleteProfileByMemberId(@Param("memberId") Long memberId);
    
    /**
     * 회원 ID로 프로필 존재 확인
     */
    @Select("SELECT COUNT(*) FROM profile WHERE member_id = #{memberId}")
    int countByMemberId(@Param("memberId") Long memberId);
    
    /**
     * 회원 ID로 프로필 존재 여부 확인 (boolean 반환)
     */
    default boolean existsByMemberId(Long memberId) {
        return countByMemberId(memberId) > 0;
    }
    
    /**
     * 주소로 검색 (부분일치)
     */
    @Select("SELECT * FROM profile WHERE address LIKE CONCAT('%', #{address}, '%')")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "memberId", column = "member_id"),
            @Result(property = "nickname", column = "nickname"),
            @Result(property = "name", column = "name"),
            @Result(property = "profileImageUrl", column = "profile_image_url"),
            @Result(property = "postalCode", column = "postal_code"),
            @Result(property = "address", column = "address"),
            @Result(property = "addressDetail", column = "address_detail"),
            @Result(property = "mobilePhone", column = "mobile_phone"),
            @Result(property = "memo", column = "memo"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    List<Profile> findByAddressContaining(@Param("address") String address);
    
    /**
     * 닉네임으로 검색 (부분일치) - IProfileMapper 메서드명에 맞춤
     */
    @Select("SELECT * FROM profile WHERE nickname LIKE CONCAT('%', #{nickname}, '%')")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "memberId", column = "member_id"),
            @Result(property = "nickname", column = "nickname"),
            @Result(property = "name", column = "name"),
            @Result(property = "profileImageUrl", column = "profile_image_url"),
            @Result(property = "postalCode", column = "postal_code"),
            @Result(property = "address", column = "address"),
            @Result(property = "addressDetail", column = "address_detail"),
            @Result(property = "mobilePhone", column = "mobile_phone"),
            @Result(property = "memo", column = "memo"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    List<Profile> findProfilesByNicknameContaining(@Param("nickname") String nickname);
    
    /**
     * 주소로 검색 (부분일치) - IProfileMapper 메서드명에 맞춤
     */
    @Select("SELECT * FROM profile WHERE address LIKE CONCAT('%', #{address}, '%')")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "memberId", column = "member_id"),
            @Result(property = "nickname", column = "nickname"),
            @Result(property = "name", column = "name"),
            @Result(property = "profileImageUrl", column = "profile_image_url"),
            @Result(property = "postalCode", column = "postal_code"),
            @Result(property = "address", column = "address"),
            @Result(property = "addressDetail", column = "address_detail"),
            @Result(property = "mobilePhone", column = "mobile_phone"),
            @Result(property = "memo", column = "memo"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    List<Profile> findProfilesByAddressContaining(@Param("address") String address);
    
    /**
     * 우편번호로 검색 - IProfileMapper 메서드명에 맞춤
     */
    @Select("SELECT * FROM profile WHERE postal_code = #{postalCode}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "memberId", column = "member_id"),
            @Result(property = "nickname", column = "nickname"),
            @Result(property = "name", column = "name"),
            @Result(property = "profileImageUrl", column = "profile_image_url"),
            @Result(property = "postalCode", column = "postal_code"),
            @Result(property = "address", column = "address"),
            @Result(property = "addressDetail", column = "address_detail"),
            @Result(property = "mobilePhone", column = "mobile_phone"),
            @Result(property = "memo", column = "memo"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    List<Profile> findProfilesByPostalCode(@Param("postalCode") String postalCode);
}