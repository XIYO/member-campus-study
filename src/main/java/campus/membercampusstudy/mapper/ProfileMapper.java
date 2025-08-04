package campus.membercampusstudy.mapper;

import campus.membercampusstudy.entity.Profile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * MyBatis 학습용 프로필 매퍼 인터페이스
 * <p>
 * 이 인터페이스는 MyBatis 어노테이션 기반 SQL 작성을 학습하기 위한 실습용 매퍼입니다.
 * TODO 주석에 따라 {@code @Select}, {@code @Insert}, {@code @Update}, {@code @Delete}, {@code @Results} 어노테이션을 구현하세요.
 * <p>
 * 학습 목표:
 * <ul>
 *   <li>MyBatis 어노테이션 기반 SQL 작성</li>
 *   <li>결과 매핑 ({@code @Results}, {@code @Result} 어노테이션)</li>
 *   <li>조건부 쿼리 작성 (WHERE, LIKE)</li>
 *   <li>외래키 관계 처리 (member_id)</li>
 * </ul>
 * <p>
 * 참고: {@code ProfileMapperRef.java}에서 완성된 쿼리를 확인할 수 있습니다.
 * 
 * @author XIYO
 * @since 2025-08-02
 * @see ProfileMapperRef
 * @see IProfileMapper
 */
@Mapper
@org.springframework.context.annotation.Profile("!ref")
public interface ProfileMapper extends IProfileMapper {
    
    /**
     * 프로필을 데이터베이스에 등록합니다.
     * <p>
     * TODO: {@code @Insert} 어노테이션으로 프로필 등록 SQL 작성
     * <ul>
     *   <li>목적: INSERT문과 외래키 관계 처리 학습</li>
     *   <li>성공 조건: 프로필 정보가 DB에 저장되고 ID가 자동 생성됨</li>
     *   <li>힌트: {@code @Options(useGeneratedKeys = true, keyProperty = "id")} 사용</li>
     * </ul>
     * 참고: {@code ProfileMapperRef.java}에서 완성된 쿼리를 확인할 수 있습니다.
     * 
     * @param profile 등록할 프로필 정보
     */
    void insertProfile(Profile profile);
    
    /**
     * 모든 프로필을 조회합니다.
     * <p>
     * TODO: {@code @Select}와 {@code @Results} 어노테이션으로 전체 프로필 조회 SQL 작성
     * <ul>
     *   <li>목적: 기본 SELECT문과 결과 매핑 학습</li>
     *   <li>성공 조건: 모든 프로필 데이터가 List로 반환됨</li>
     *   <li>힌트: {@code @Results}로 컬럼과 필드 매핑 정의</li>
     * </ul>
     * 참고: {@code ProfileMapperRef.java}에서 완성된 쿼리를 확인할 수 있습니다.
     * 
     * @return 전체 프로필 목록
     */
    List<Profile> findAllProfiles();
    
    /**
     * ID로 특정 프로필을 조회합니다.
     * <p>
     * TODO: {@code @Select}와 {@code @Results} 어노테이션으로 ID 조회 SQL 작성
     * <ul>
     *   <li>목적: WHERE 조건문과 결과 매핑 학습</li>
     *   <li>성공 조건: 해당 ID의 프로필 정보가 반환됨 (없으면 null)</li>
     *   <li>힌트: {@code WHERE id = #{id}}와 {@code @Results} 사용</li>
     * </ul>
     * 참고: {@code ProfileMapperRef.java}에서 완성된 쿼리를 확인할 수 있습니다.
     * 
     * @param id 조회할 프로필 ID
     * @return 조회된 프로필 정보, 없으면 null
     */
    Profile findProfileById(@Param("id") Long id);
    
    /**
     * 회원 ID로 특정 프로필을 조회합니다.
     * <p>
     * TODO: {@code @Select}와 {@code @Results} 어노테이션으로 회원 ID 조회 SQL 작성
     * <ul>
     *   <li>목적: 외래키 기반 조회와 결과 매핑 학습</li>
     *   <li>성공 조건: 해당 회원의 프로필 정보가 반환됨 (없으면 null)</li>
     *   <li>힌트: {@code WHERE member_id = #{memberId}}와 {@code @Results} 사용</li>
     * </ul>
     * 참고: {@code ProfileMapperRef.java}에서 완성된 쿼리를 확인할 수 있습니다.
     * 
     * @param memberId 조회할 회원 ID
     * @return 조회된 프로필 정보, 없으면 null
     */
    Profile findProfileByMemberId(@Param("memberId") Long memberId);
    
    /**
     * 특정 회원 ID를 가진 프로필의 수를 조회합니다.
     * <p>
     * TODO: {@code @Select} 어노테이션으로 회원 ID 존재 확인 SQL 작성
     * <ul>
     *   <li>목적: COUNT 함수와 외래키 조건 처리 학습</li>
     *   <li>성공 조건: 해당 회원 ID를 가진 프로필 수가 반환됨 (0 또는 1)</li>
     *   <li>힌트: {@code SELECT COUNT(*) FROM profile WHERE member_id = #{memberId}} 사용</li>
     * </ul>
     * 참고: {@code ProfileMapperRef.java}에서 완성된 쿼리를 확인할 수 있습니다.
     * 
     * @param memberId 확인할 회원 ID
     * @return 해당 회원 ID를 가진 프로필 수
     */
    int countByMemberId(@Param("memberId") Long memberId);
    
    /**
     * 닉네임으로 프로필을 검색합니다 (부분일치).
     * <p>
     * TODO: {@code @Select}와 {@code @Results} 어노테이션으로 닉네임 검색 SQL 작성 (부분일치)
     * <ul>
     *   <li>목적: LIKE 연산자와 결과 매핑 학습</li>
     *   <li>성공 조건: 닉네임에 검색어가 포함된 모든 프로필이 반환됨</li>
     *   <li>힌트: {@code WHERE nickname LIKE CONCAT('%', #{nickname}, '%')}와 {@code @Results} 사용</li>
     * </ul>
     * 참고: {@code ProfileMapperRef.java}에서 완성된 쿼리를 확인할 수 있습니다.
     * 
     * @param nickname 검색할 닉네임
     * @return 검색된 프로필 목록
     */
    List<Profile> findProfilesByNicknameContaining(@Param("nickname") String nickname);
    
    /**
     * 주소로 프로필을 검색합니다 (부분일치).
     * <p>
     * TODO: {@code @Select}와 {@code @Results} 어노테이션으로 주소 검색 SQL 작성 (부분일치)
     * <ul>
     *   <li>목적: 문자열 부분일치 검색과 결과 매핑 학습</li>
     *   <li>성공 조건: 주소에 검색어가 포함된 모든 프로필이 반환됨</li>
     *   <li>힌트: {@code WHERE address LIKE CONCAT('%', #{address}, '%')}와 {@code @Results} 사용</li>
     * </ul>
     * 참고: {@code ProfileMapperRef.java}에서 완성된 쿼리를 확인할 수 있습니다.
     * 
     * @param address 검색할 주소
     * @return 검색된 프로필 목록
     */
    List<Profile> findProfilesByAddressContaining(@Param("address") String address);
    
    /**
     * 우편번호로 프로필을 검색합니다.
     * <p>
     * TODO: {@code @Select}와 {@code @Results} 어노테이션으로 우편번호 검색 SQL 작성
     * <ul>
     *   <li>목적: 정확일치 검색과 결과 매핑 학습</li>
     *   <li>성공 조건: 지정된 우편번호의 모든 프로필이 반환됨</li>
     *   <li>힌트: {@code WHERE postal_code = #{postalCode}}와 {@code @Results} 사용</li>
     * </ul>
     * 참고: {@code ProfileMapperRef.java}에서 완성된 쿼리를 확인할 수 있습니다.
     * 
     * @param postalCode 검색할 우편번호
     * @return 검색된 프로필 목록
     */
    List<Profile> findProfilesByPostalCode(@Param("postalCode") String postalCode);
    
    /**
     * 프로필 정보를 업데이트합니다.
     * <p>
     * TODO: {@code @Update} 어노테이션으로 프로필 정보 수정 SQL 작성
     * <ul>
     *   <li>목적: UPDATE문과 여러 필드 동시 수정 학습</li>
     *   <li>성공 조건: 지정된 ID의 프로필 정보가 수정됨</li>
     *   <li>힌트: {@code SET nickname = #{nickname}, ... WHERE id = #{id}} 사용</li>
     * </ul>
     * 참고: {@code ProfileMapperRef.java}에서 완성된 쿼리를 확인할 수 있습니다.
     * 
     * @param profile 업데이트할 프로필 정보
     */
    void updateProfile(Profile profile);
    
    /**
     * 프로필을 삭제합니다.
     * <p>
     * TODO: {@code @Delete} 어노테이션으로 프로필 삭제 SQL 작성
     * <ul>
     *   <li>목적: DELETE문과 조건부 삭제 학습</li>
     *   <li>성공 조건: 지정된 ID의 프로필이 삭제됨</li>
     *   <li>힌트: {@code DELETE FROM profile WHERE id = #{id}} 사용</li>
     * </ul>
     * 참고: {@code ProfileMapperRef.java}에서 완성된 쿼리를 확인할 수 있습니다.
     * 
     * @param id 삭제할 프로필 ID
     */
    void deleteProfile(@Param("id") Long id);
    
    /**
     * 회원 ID로 프로필을 삭제합니다.
     * <p>
     * TODO: {@code @Delete} 어노테이션으로 회원 ID로 프로필 삭제 SQL 작성
     * <ul>
     *   <li>목적: 외래키 기반 삭제와 연관 데이터 처리 학습</li>
     *   <li>성공 조건: 해당 회원의 프로필이 삭제됨</li>
     *   <li>힌트: {@code DELETE FROM profile WHERE member_id = #{memberId}} 사용</li>
     * </ul>
     * 참고: {@code ProfileMapperRef.java}에서 완성된 쿼리를 확인할 수 있습니다.
     * 
     * @param memberId 삭제할 회원 ID
     */
    void deleteProfileByMemberId(@Param("memberId") Long memberId);
}