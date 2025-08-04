package campus.membercampusstudy.mapper;

import campus.membercampusstudy.entity.Profile;
import campus.membercampusstudy.repository.IProfile;

import java.util.List;

/**
 * 회원 프로필 매퍼 인터페이스
 * <p>
 * MyBatis 프로필 매퍼의 공통 인터페이스입니다.
 * 학습용(ProfileMapper)과 구현체(ProfileMapperRef) 모두 이 인터페이스를 구현합니다.
 * IProfile 인터페이스를 상속받아 JPA Repository와 일관된 인터페이스를 제공합니다.
 */
public interface IProfileMapper extends IProfile {
    
    // MyBatis 전용 메서드들 (IProfile의 구현을 위해 필요)
    
    /**
     * 회원 프로필을 데이터베이스에 등록합니다. (MyBatis 전용)
     * 
     * @param profile 등록할 프로필 정보
     */
    void insertProfile(Profile profile);
    
    /**
     * 모든 프로필을 조회합니다. (MyBatis 전용)
     * 
     * @return 전체 프로필 목록
     */
    List<Profile> findAllProfiles();
    
    /**
     * ID로 특정 프로필을 조회합니다. (MyBatis 전용)
     * 
     * @param id 프로필 ID
     * @return 조회된 프로필 정보, 없으면 null
     */
    Profile findProfileById(Long id);
    
    /**
     * 회원 ID로 특정 프로필을 조회합니다. (MyBatis 전용)
     * 
     * @param memberId 회원 ID
     * @return 조회된 프로필 정보, 없으면 null
     */
    Profile findProfileByMemberId(Long memberId);
    
    /**
     * 특정 회원 ID를 가진 프로필의 수를 조회합니다. (MyBatis 전용)
     * 
     * @param memberId 확인할 회원 ID
     * @return 해당 회원 ID를 가진 프로필 수
     */
    int countByMemberId(Long memberId);
    
    /**
     * 프로필 정보를 업데이트합니다. (MyBatis 전용)
     * 
     * @param profile 업데이트할 프로필 정보
     */
    void updateProfile(Profile profile);
    
    /**
     * 프로필을 삭제합니다. (MyBatis 전용)
     * 
     * @param id 삭제할 프로필 ID
     */
    void deleteProfile(Long id);
    
    /**
     * 회원 ID로 프로필을 삭제합니다. (MyBatis 전용)
     * 
     * @param memberId 삭제할 회원 ID
     */
    void deleteProfileByMemberId(Long memberId);
    
    /**
     * 닉네임으로 프로필을 검색합니다. (MyBatis 전용)
     * 
     * @param nickname 검색할 닉네임
     * @return 검색된 프로필 목록
     */
    List<Profile> findProfilesByNicknameContaining(String nickname);
    
    /**
     * 주소로 프로필을 검색합니다. (MyBatis 전용)
     * 
     * @param address 검색할 주소
     * @return 검색된 프로필 목록
     */
    List<Profile> findProfilesByAddressContaining(String address);
    
    /**
     * 우편번호로 프로필을 검색합니다. (MyBatis 전용)
     * 
     * @param postalCode 검색할 우편번호
     * @return 검색된 프로필 목록
     */
    List<Profile> findProfilesByPostalCode(String postalCode);
    
    // IProfile 인터페이스 기본 구현은 어댑터 패턴으로 MyBatis 메서드를 래핑
}