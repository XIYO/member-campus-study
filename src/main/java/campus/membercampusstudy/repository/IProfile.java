package campus.membercampusstudy.repository;

import campus.membercampusstudy.entity.Profile;

import java.util.List;
import java.util.Optional;

/**
 * 회원 프로필 데이터 접근 공통 인터페이스
 * <p>
 * JPA Repository와 MyBatis Mapper가 공통으로 구현하는 인터페이스입니다.
 * 이를 통해 데이터 접근 계층의 일관성을 보장합니다.
 * 
 * Note: JPA Repository와의 메서드 충돌을 방지하기 위해 검색 메서드들만 정의합니다.
 */
public interface IProfile {
    
    // 기본 CRUD 메서드들은 JPA Repository에서 제공하므로 제외
    // 대신 공통 검색 메서드들만 정의
    
    /**
     * 회원 ID로 프로필을 조회합니다.
     * 
     * @param memberId 회원 ID
     * @return 조회된 프로필 정보 (Optional)
     */
    Optional<Profile> findByMemberId(Long memberId);
    
    /**
     * 회원 ID로 프로필 존재 여부를 확인합니다.
     * 
     * @param memberId 회원 ID
     * @return 존재 여부
     */
    boolean existsByMemberId(Long memberId);
    
    /**
     * 회원 ID로 프로필을 삭제합니다.
     * 
     * @param memberId 회원 ID
     */
    void deleteByMemberId(Long memberId);
    
    /**
     * 닉네임으로 프로필을 검색합니다 (부분일치).
     * 
     * @param nickname 검색할 닉네임
     * @return 검색된 프로필 목록
     */
    List<Profile> findByNicknameContaining(String nickname);
    
    /**
     * 주소로 프로필을 검색합니다 (부분일치).
     * 
     * @param address 검색할 주소
     * @return 검색된 프로필 목록
     */
    List<Profile> findByAddressContaining(String address);
    
    /**
     * 우편번호로 프로필을 검색합니다.
     * 
     * @param postalCode 검색할 우편번호
     * @return 검색된 프로필 목록
     */
    List<Profile> findByPostalCode(String postalCode);
}