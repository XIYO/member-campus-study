package campus.membercampusstudy.mapper;

import campus.membercampusstudy.entity.Member;
import org.apache.ibatis.annotations.*;
import org.springframework.context.annotation.Profile;

import java.util.List;

/**
 * 회원 매퍼 인터페이스
 * <p>
 * MyBatis 매퍼의 공통 인터페이스입니다.
 * 학습용(MemberMapper)과 구현체(MemberMapperRef) 모두 이 인터페이스를 구현합니다.
 * IMember 인터페이스를 상속받아 JPA Repository와 일관된 인터페이스를 제공합니다.
 */
@Mapper
@Profile("ref")
public interface MemberMapperRef extends IMemberMapper {
    
    /**
     * 회원 등록
     */
    @Insert("""
            INSERT INTO member (email, name, phone, age, gender, created_at, updated_at)
            VALUES (#{email}, #{name}, #{phone}, #{age}, #{gender}, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertMember(Member member);
    
    /**
     * 전체 회원 조회
     */
    @Select("SELECT * FROM member")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "email", column = "email"),
            @Result(property = "name", column = "name"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "age", column = "age"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    List<Member> findAllMembers();
    
    /**
     * ID로 회원 조회
     */
    @Select("SELECT * FROM member WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "email", column = "email"),
            @Result(property = "name", column = "name"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "age", column = "age"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    Member findMemberById(@Param("id") Long id);
    
    /**
     * 이메일로 회원 조회
     */
    @Select("SELECT * FROM member WHERE email = #{email}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "email", column = "email"),
            @Result(property = "name", column = "name"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "age", column = "age"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    Member findMemberByEmail(@Param("email") String email);
    
    /**
     * 이메일 중복 확인
     */
    @Select("SELECT COUNT(*) FROM member WHERE email = #{email}")
    int countByEmail(@Param("email") String email);
    
    /**
     * 이름으로 검색 (부분일치)
     */
    @Select("SELECT * FROM member WHERE name LIKE CONCAT('%', #{name}, '%')")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "email", column = "email"),
            @Result(property = "name", column = "name"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "age", column = "age"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    List<Member> findByNameContaining(@Param("name") String name);
    
    /**
     * 나이 범위로 검색
     */
    @Select("SELECT * FROM member WHERE age BETWEEN #{minAge} AND #{maxAge}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "email", column = "email"),
            @Result(property = "name", column = "name"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "age", column = "age"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    List<Member> findByAgeBetween(@Param("minAge") Integer minAge, @Param("maxAge") Integer maxAge);
    
    /**
     * 성별로 검색
     */
    @Select("SELECT * FROM member WHERE gender = #{gender}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "email", column = "email"),
            @Result(property = "name", column = "name"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "age", column = "age"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    List<Member> findByGender(@Param("gender") String gender);
    
    /**
     * 회원 정보 수정
     */
    @Update("""
            UPDATE member 
            SET name = #{name}, phone = #{phone}, age = #{age}, gender = #{gender}, updated_at = CURRENT_TIMESTAMP 
            WHERE id = #{id}
            """)
    void updateMember(Member member);
    
    /**
     * 회원 삭제
     */
    @Delete("DELETE FROM member WHERE id = #{id}")
    void deleteMember(@Param("id") Long id);
}