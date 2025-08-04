package campus.membercampusstudy.mapper;

import campus.membercampusstudy.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * MyBatis 학습용 회원 매퍼 인터페이스
 * <p>
 * 이 인터페이스는 MyBatis 어노테이션 기반 SQL 작성을 학습하기 위한 실습용 매퍼입니다.
 * TODO 주석에 따라 {@code @Select}, {@code @Insert}, {@code @Update}, {@code @Delete} 어노테이션을 구현하세요.
 * <p>
 * 학습 목표:
 * <ul>
 *   <li>MyBatis 어노테이션 기반 SQL 작성</li>
 *   <li>매개변수 바인딩 ({@code @Param} 어노테이션)</li>
 *   <li>자동 키 생성 ({@code @Options} 어노테이션)</li>
 *   <li>조건부 쿼리 작성 (WHERE, LIKE, BETWEEN)</li>
 * </ul>
 * <p>
 * 참고: {@code MemberMapperRef.java}에서 완성된 쿼리를 확인할 수 있습니다.
 * 
 * @author XIYO
 * @since 2025-08-02
 * @see MemberMapperRef
 * @see IMemberMapper
 */
@Mapper
@org.springframework.context.annotation.Profile("!ref")
public interface MemberMapper extends IMemberMapper {
    
    /**
     * 회원을 데이터베이스에 등록합니다.
     * <p>
     * TODO: {@code @Insert} 어노테이션으로 회원 등록 SQL 작성
     * <ul>
     *   <li>목적: INSERT문 작성 연습 및 자동 키 생성 학습</li>
     *   <li>성공 조건: 회원 정보가 DB에 저장되고 ID가 자동 생성됨</li>
     *   <li>힌트: {@code @Options(useGeneratedKeys = true, keyProperty = "id")} 사용</li>
     * </ul>
     * 참고: {@code MemberMapperRef.java}에서 완성된 쿼리를 확인할 수 있습니다.
     * 
     * @param member 등록할 회원 정보
     */
    void insertMember(Member member);
    
    /**
     * 모든 회원을 조회합니다.
     * <p>
     * TODO: {@code @Select} 어노테이션으로 전체 회원 조회 SQL 작성
     * <ul>
     *   <li>목적: 기본 SELECT문 작성 연습</li>
     *   <li>성공 조건: 모든 회원 데이터가 List로 반환됨</li>
     *   <li>힌트: {@code SELECT * FROM member} 사용</li>
     * </ul>
     * 참고: {@code MemberMapperRef.java}에서 완성된 쿼리를 확인할 수 있습니다.
     * 
     * @return 전체 회원 목록
     */
    List<Member> findAllMembers();
    
    /**
     * ID로 특정 회원을 조회합니다.
     * <p>
     * TODO: {@code @Select} 어노테이션으로 ID 조회 SQL 작성
     * <ul>
     *   <li>목적: WHERE 조건문과 매개변수 바인딩 학습</li>
     *   <li>성공 조건: 해당 ID의 회원 정보가 반환됨 (없으면 null)</li>
     *   <li>힌트: {@code WHERE id = #{id}} 사용</li>
     * </ul>
     * 참고: {@code MemberMapperRef.java}에서 완성된 쿼리를 확인할 수 있습니다.
     * 
     * @param id 조회할 회원 ID
     * @return 조회된 회원 정보, 없으면 null
     */
    Member findMemberById(@Param("id") Long id);
    
    /**
     * 이메일로 특정 회원을 조회합니다.
     * <p>
     * TODO: {@code @Select} 어노테이션으로 이메일 조회 SQL 작성
     * <ul>
     *   <li>목적: 문자열 조건문과 {@code @Param} 어노테이션 학습</li>
     *   <li>성공 조건: 해당 이메일의 회원 정보가 반환됨 (없으면 null)</li>
     *   <li>힌트: {@code WHERE email = #{email}} 사용</li>
     * </ul>
     * 참고: {@code MemberMapperRef.java}에서 완성된 쿼리를 확인할 수 있습니다.
     * 
     * @param email 조회할 회원 이메일
     * @return 조회된 회원 정보, 없으면 null
     */
    Member findMemberByEmail(@Param("email") String email);
    
    /**
     * 특정 이메일을 가진 회원의 수를 조회합니다.
     * <p>
     * TODO: {@code @Select} 어노테이션으로 이메일 중복 확인 SQL 작성
     * <ul>
     *   <li>목적: COUNT 함수와 중복 확인 로직 학습</li>
     *   <li>성공 조건: 해당 이메일을 가진 회원 수가 반환됨 (0 또는 1)</li>
     *   <li>힌트: {@code SELECT COUNT(*) FROM member WHERE email = #{email}} 사용</li>
     * </ul>
     * 참고: {@code MemberMapperRef.java}에서 완성된 쿼리를 확인할 수 있습니다.
     * 
     * @param email 확인할 이메일
     * @return 해당 이메일을 가진 회원 수
     */
    int countByEmail(@Param("email") String email);
    
    /**
     * 이름으로 회원을 검색합니다 (부분일치).
     * <p>
     * TODO: {@code @Select} 어노테이션으로 이름 검색 SQL 작성 (부분일치)
     * <ul>
     *   <li>목적: LIKE 연산자와 부분일치 검색 학습</li>
     *   <li>성공 조건: 이름에 검색어가 포함된 모든 회원이 반환됨</li>
     *   <li>힌트: {@code WHERE name LIKE CONCAT('%', #{name}, '%')} 사용</li>
     * </ul>
     * 참고: {@code MemberMapperRef.java}에서 완성된 쿼리를 확인할 수 있습니다.
     * 
     * @param name 검색할 이름
     * @return 검색된 회원 목록
     */
    List<Member> findByNameContaining(@Param("name") String name);
    
    /**
     * 나이 범위로 회원을 검색합니다.
     * <p>
     * TODO: {@code @Select} 어노테이션으로 나이 범위 검색 SQL 작성
     * <ul>
     *   <li>목적: BETWEEN 연산자와 범위 검색 학습</li>
     *   <li>성공 조건: 지정된 나이 범위에 속하는 모든 회원이 반환됨</li>
     *   <li>힌트: {@code WHERE age BETWEEN #{minAge} AND #{maxAge}} 사용</li>
     * </ul>
     * 참고: {@code MemberMapperRef.java}에서 완성된 쿼리를 확인할 수 있습니다.
     * 
     * @param minAge 최소 나이
     * @param maxAge 최대 나이
     * @return 검색된 회원 목록
     */
    List<Member> findByAgeBetween(@Param("minAge") Integer minAge, @Param("maxAge") Integer maxAge);
    
    /**
     * 성별로 회원을 검색합니다.
     * <p>
     * TODO: {@code @Select} 어노테이션으로 성별 검색 SQL 작성
     * <ul>
     *   <li>목적: Enum 타입 처리와 정확일치 검색 학습</li>
     *   <li>성공 조건: 지정된 성별의 모든 회원이 반환됨</li>
     *   <li>힌트: {@code WHERE gender = #{gender}} 사용 (MALE/FEMALE)</li>
     * </ul>
     * 참고: {@code MemberMapperRef.java}에서 완성된 쿼리를 확인할 수 있습니다.
     * 
     * @param gender 검색할 성별 (MALE/FEMALE)
     * @return 검색된 회원 목록
     */
    List<Member> findByGender(@Param("gender") String gender);
    
    /**
     * 회원 정보를 업데이트합니다.
     * <p>
     * TODO: {@code @Update} 어노테이션으로 회원 정보 수정 SQL 작성
     * <ul>
     *   <li>목적: UPDATE문 작성과 여러 필드 동시 수정 학습</li>
     *   <li>성공 조건: 지정된 ID의 회원 정보가 수정됨</li>
     *   <li>힌트: {@code SET email = #{email}, name = #{name}, ... WHERE id = #{id}} 사용</li>
     * </ul>
     * 참고: {@code MemberMapperRef.java}에서 완성된 쿼리를 확인할 수 있습니다.
     * 
     * @param member 업데이트할 회원 정보
     */
    void updateMember(Member member);
    
    /**
     * 회원을 삭제합니다.
     * <p>
     * TODO: {@code @Delete} 어노테이션으로 회원 삭제 SQL 작성
     * <ul>
     *   <li>목적: DELETE문 작성과 조건부 삭제 학습</li>
     *   <li>성공 조건: 지정된 ID의 회원이 삭제됨</li>
     *   <li>힌트: {@code DELETE FROM member WHERE id = #{id}} 사용</li>
     * </ul>
     * 참고: {@code MemberMapperRef.java}에서 완성된 쿼리를 확인할 수 있습니다.
     * 
     * @param id 삭제할 회원 ID
     */
    void deleteMember(@Param("id") Long id);
}