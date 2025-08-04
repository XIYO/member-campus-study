package campus.membercampusstudy.repository;

import campus.membercampusstudy.entity.Member;

import java.util.List;
import java.util.Optional;

/**
 * 회원 데이터 접근 공통 인터페이스
 * <p>
 * JPA Repository와 MyBatis Mapper가 공통으로 구현하는 인터페이스입니다.
 * 이를 통해 데이터 접근 계층의 일관성을 보장합니다.
 * 
 * Note: JPA Repository와의 메서드 충돌을 방지하기 위해 다른 이름을 사용합니다.
 */
public interface IMember {
    
    // 기본 CRUD 메서드들은 JPA Repository에서 제공하므로 제외
    // 대신 공통 검색 메서드들만 정의
    
    /**
     * 이메일로 회원을 조회합니다.
     * 
     * @param email 회원 이메일
     * @return 조회된 회원 정보 (Optional)
     */
    Optional<Member> findByEmail(String email);
    
    /**
     * 이메일로 회원 존재 여부를 확인합니다.
     * 
     * @param email 확인할 이메일
     * @return 존재 여부
     */
    boolean existsByEmail(String email);
    
    /**
     * 이름으로 회원을 검색합니다 (부분일치).
     * 
     * @param name 검색할 이름
     * @return 검색된 회원 목록
     */
    List<Member> findByNameContaining(String name);
    
    /**
     * 나이 범위로 회원을 검색합니다.
     * 
     * @param minAge 최소 나이
     * @param maxAge 최대 나이
     * @return 검색된 회원 목록
     */
    List<Member> findByAgeBetween(Integer minAge, Integer maxAge);
    
    /**
     * 성별로 회원을 검색합니다.
     * 
     * @param gender 성별
     * @return 검색된 회원 목록
     */
    List<Member> findByGender(Member.Gender gender);
}