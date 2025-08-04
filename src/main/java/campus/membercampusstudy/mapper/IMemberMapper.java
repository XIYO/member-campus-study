package campus.membercampusstudy.mapper;

import campus.membercampusstudy.entity.Member;
import campus.membercampusstudy.repository.IMember;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 회원 매퍼 인터페이스
 * <p>
 * MyBatis 매퍼의 공통 인터페이스입니다.
 * 학습용(MemberMapper)과 구현체(MemberMapperRef) 모두 이 인터페이스를 구현합니다.
 * IMember 인터페이스를 상속받아 JPA Repository와 일관된 인터페이스를 제공합니다.
 */
public interface IMemberMapper extends IMember {
    
    // MyBatis 전용 메서드들 (IMember의 구현을 위해 필요)
    
    /**
     * 회원을 데이터베이스에 등록합니다. (MyBatis 전용)
     * 
     * @param member 등록할 회원 정보
     */
    void insertMember(Member member);
    
    /**
     * 모든 회원을 조회합니다. (MyBatis 전용)
     * 
     * @return 전체 회원 목록
     */
    List<Member> findAllMembers();
    
    /**
     * ID로 특정 회원을 조회합니다. (MyBatis 전용)
     * 
     * @param id 회원 ID
     * @return 조회된 회원 정보, 없으면 null
     */
    Member findMemberById(Long id);
    
    /**
     * 이메일로 특정 회원을 조회합니다. (MyBatis 전용)
     * 
     * @param email 회원 이메일
     * @return 조회된 회원 정보, 없으면 null
     */
    Member findMemberByEmail(String email);
    
    /**
     * 특정 이메일을 가진 회원의 수를 조회합니다. (MyBatis 전용)
     * 
     * @param email 확인할 이메일
     * @return 해당 이메일을 가진 회원 수
     */
    int countByEmail(String email);
    
    /**
     * 회원 정보를 업데이트합니다. (MyBatis 전용)
     * 
     * @param member 업데이트할 회원 정보
     */
    void updateMember(Member member);
    
    /**
     * 회원을 삭제합니다. (MyBatis 전용)
     * 
     * @param id 삭제할 회원 ID
     */
    void deleteMember(Long id);
    
    // MyBatis는 enum을 String으로 처리하므로 String 타입 메서드도 추가
    List<Member> findByGender(@Param("gender") String gender);
    
    // IMember 인터페이스 기본 구현은 어댑터 패턴으로 MyBatis 메서드를 래핑
}