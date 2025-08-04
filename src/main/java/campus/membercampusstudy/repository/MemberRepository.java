package campus.membercampusstudy.repository;

import campus.membercampusstudy.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA 회원 리포지토리 (학습용 - 구현 필요)
 * 
 * TODO: 이 리포지토리 인터페이스에 필요한 메서드들을 추가하세요.
 * 
 * 힌트:
 * - JpaRepository를 상속받으면 기본 CRUD는 자동 제공됩니다
 * - 이메일로 회원을 찾는 메서드가 필요합니다
 * - 이메일 중복을 확인하는 메서드가 필요합니다
 * 
 * 예시:
 * - Optional<Member> findByEmail(String email);
 * - boolean existsByEmail(String email);
 * 
 * @author XIYO
 * @version 1.0
 * @since 2025-08-02
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, IMember {
    
    // 기본 메서드들 (빌드를 위해 필수)
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);
    
    // 추가 검색 메서드들 (JPA가 자동 구현)
    List<Member> findByNameContaining(String name);
    List<Member> findByAgeBetween(Integer minAge, Integer maxAge);
    List<Member> findByGender(Member.Gender gender);
    List<Member> findByNameContainingAndGender(String name, Member.Gender gender);
    List<Member> findByAgeGreaterThanEqual(Integer age);
    List<Member> findByAgeLessThanEqual(Integer age);
    
}