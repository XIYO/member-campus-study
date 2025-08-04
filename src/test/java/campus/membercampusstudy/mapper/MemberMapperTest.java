package campus.membercampusstudy.mapper;

import campus.membercampusstudy.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import org.junit.jupiter.api.DisplayName;

/**
 * MemberMapper 종합 통합 테스트
 * <p>
 * MyBatis 회원 매퍼의 CRUD 기능을 검증하는 포괄적인 테스트 클래스입니다.
 * 학습용 매퍼(MemberMapper)와 참고용 매퍼(MemberMapperRef) 모두에서 동작하며,
 * 프로필 기반으로 다른 결과를 보여줍니다.
 * <p>
 * 테스트 구성:
 * <ul>
 *   <li>기본 CRUD 작업 (등록, 조회, 수정, 삭제)</li>
 *   <li>조건부 검색 (이름, 나이, 성별, 이메일)</li>
 *   <li>엣지 케이스 (NULL 값, 존재하지 않는 데이터)</li>
 *   <li>중복 확인 및 유효성 검사</li>
 * </ul>
 * <p>
 * 실행 방법:
 * <ul>
 *   <li>기본 프로필: {@code ./gradlew test} (실패 - MyBatis 구현 필요)</li>
 *   <li>ref 프로필: {@code SPRING_PROFILES_ACTIVE=ref ./gradlew test} (성공 - 참고용)</li>
 * </ul>
 * 
 * @author XIYO
 * @since 2025-08-02
 * @see MemberMapper
 * @see MemberMapperRef
 */
@SpringBootTest
@Transactional
class MemberMapperTest {

    @Autowired(required = false)
    private IMemberMapper memberMapper;

    // ========== 기본 CRUD 테스트 ==========
    
    @Test
    @DisplayName("회원 등록 - INSERT문 작성 연습")
    void insertMember_success() {
        // 목적: @Insert 어노테이션으로 회원 등록 SQL 작성
        // 성공 조건: 회원 정보가 DB에 저장되고 ID가 자동 생성됨 
        assumeTrue(memberMapper != null, "MyBatis 매퍼가 없음 - ref 프로필로 실행하세요");
        
        Member member = Member.builder()
            .email("test@test.com")
            .name("홍길동")
            .phone("010-1234-5678")
            .age(25)
            .gender(Member.Gender.MALE)
            .build();
        
        memberMapper.insertMember(member);
        
        assertThat(member.getId()).isNotNull();
    }

    @Test
    @DisplayName("전체 회원 조회 - SELECT문 기본 작성")
    void findAllMembers_success() {
        // 목적: @Select 어노테이션으로 전체 회원 조회 SQL 작성
        // 성공 조건: 모든 회원 데이터가 List로 반환됨
        assumeTrue(memberMapper != null, "MyBatis 매퍼가 없음 - ref 프로필로 실행하세요");
        
        int initialCount = memberMapper.findAllMembers().size();
        
        Member member1 = Member.builder()
            .email("test1@test.com").name("홍길동").phone("010-1111-1111")
            .age(25).gender(Member.Gender.MALE).build();
        Member member2 = Member.builder()
            .email("test2@test.com").name("김영희").phone("010-2222-2222")
            .age(23).gender(Member.Gender.FEMALE).build();
        memberMapper.insertMember(member1);
        memberMapper.insertMember(member2);
        
        List<Member> members = memberMapper.findAllMembers();
        
        assertThat(members).hasSize(initialCount + 2);
        assertThat(members).extracting(Member::getEmail)
            .contains("test1@test.com", "test2@test.com");
    }

    @Test
    @DisplayName("ID로 회원 조회 - WHERE 조건절 작성")
    void findMemberById_success() {
        // 목적: @Select + WHERE절로 특정 회원 조회 SQL 작성
        // 성공 조건: ID에 해당하는 회원 정보가 정확히 반환됨
        assumeTrue(memberMapper != null, "MyBatis 매퍼가 없음 - ref 프로필로 실행하세요");
        
        Member member = Member.builder()
            .email("test@test.com").name("홍길동").phone("010-1234-5678")
            .age(25).gender(Member.Gender.MALE).build();
        memberMapper.insertMember(member);
        
        Member found = memberMapper.findMemberById(member.getId());
        
        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(member.getId());
        assertThat(found.getEmail()).isEqualTo("test@test.com");
        assertThat(found.getName()).isEqualTo("홍길동");
    }

    @Test
    @DisplayName("회원 정보 수정 - UPDATE문 작성")
    void updateMember_success() {
        // 목적: @Update 어노테이션으로 회원 정보 수정 SQL 작성
        // 성공 조건: 특정 회원의 정보가 새로운 값으로 변경됨
        assumeTrue(memberMapper != null, "MyBatis 매퍼가 없음 - ref 프로필로 실행하세요");
        
        Member member = Member.builder()
            .email("test@test.com").name("홍길동").phone("010-1234-5678")
            .age(25).gender(Member.Gender.MALE).build();
        memberMapper.insertMember(member);
        
        member.setName("홍길순");
        member.setAge(30);
        memberMapper.updateMember(member);
        
        Member updated = memberMapper.findMemberById(member.getId());
        assertThat(updated.getName()).isEqualTo("홍길순");
        assertThat(updated.getAge()).isEqualTo(30);
    }

    @Test
    @DisplayName("회원 삭제 - DELETE문 작성")
    void deleteMember_success() {
        // 목적: @Delete 어노테이션으로 회원 삭제 SQL 작성
        // 성공 조건: 특정 ID의 회원이 DB에서 완전히 삭제됨
        assumeTrue(memberMapper != null, "MyBatis 매퍼가 없음 - ref 프로필로 실행하세요");
        
        Member member = Member.builder()
            .email("test@test.com").name("홍길동").phone("010-1234-5678")
            .age(25).gender(Member.Gender.MALE).build();
        memberMapper.insertMember(member);
        
        memberMapper.deleteMember(member.getId());
        
        Member deleted = memberMapper.findMemberById(member.getId());
        assertThat(deleted).isNull();
    }

    // ========== 검색 기능 테스트 ==========

    @Test
    @DisplayName("이메일로 회원 조회 - UNIQUE 제약조건 활용")
    void findMemberByEmail_success() {
        // 목적: 유니크 컬럼(email)으로 회원 조회 SQL 작성
        // 성공 조건: 특정 이메일의 회원이 정확히 1명 조회됨
        assumeTrue(memberMapper != null, "MyBatis 매퍼가 없음 - ref 프로필로 실행하세요");
        
        Member member = Member.builder()
            .email("unique@test.com").name("홍길동").phone("010-1234-5678")
            .age(25).gender(Member.Gender.MALE).build();
        memberMapper.insertMember(member);
        
        Member found = memberMapper.findMemberByEmail("unique@test.com");
        
        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo("unique@test.com");
    }

    @Test
    @DisplayName("이메일 중복 확인 - COUNT 함수 사용")
    void countByEmail_success() {
        // 목적: COUNT() 함수로 이메일 중복 검사 SQL 작성
        // 성공 조건: 특정 이메일의 개수가 정수로 반환됨
        assumeTrue(memberMapper != null, "MyBatis 매퍼가 없음 - ref 프로필로 실행하세요");
        
        Member member = Member.builder()
            .email("unique@test.com").name("홍길동").phone("010-1234-5678")
            .age(25).gender(Member.Gender.MALE).build();
        memberMapper.insertMember(member);
        
        int count = memberMapper.countByEmail("unique@test.com");
        
        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("이름 검색 - LIKE 연산자 사용")
    void findByNameContaining_success() {
        // 목적: LIKE + CONCAT으로 부분 일치 검색 SQL 작성
        // 성공 조건: 이름에 특정 문자열이 포함된 모든 회원 조회
        assumeTrue(memberMapper != null, "MyBatis 매퍼가 없음 - ref 프로필로 실행하세요");
        
        int initialCount = memberMapper.findByNameContaining("홍").size();
        
        Member member1 = Member.builder()
            .email("test1@test.com").name("홍길동").phone("010-1111-1111")
            .age(25).gender(Member.Gender.MALE).build();
        Member member2 = Member.builder()
            .email("test2@test.com").name("홍영수").phone("010-2222-2222")
            .age(23).gender(Member.Gender.MALE).build();
        memberMapper.insertMember(member1);
        memberMapper.insertMember(member2);
        
        List<Member> members = memberMapper.findByNameContaining("홍");
        
        assertThat(members).hasSize(initialCount + 2);
        assertThat(members).extracting(Member::getName)
            .contains("홍길동", "홍영수");
    }

    @Test
    @DisplayName("나이 범위 검색 - BETWEEN 연산자 사용")
    void findByAgeBetween_success() {
        // 목적: BETWEEN 또는 >= AND <= 조건으로 범위 검색 SQL 작성
        // 성공 조건: 지정된 나이 범위 내의 모든 회원 조회
        assumeTrue(memberMapper != null, "MyBatis 매퍼가 없음 - ref 프로필로 실행하세요");
        
        Member member1 = Member.builder()
            .email("test1@test.com").name("홍길동").phone("010-1111-1111")
            .age(25).gender(Member.Gender.MALE).build();
        Member member2 = Member.builder()
            .email("test2@test.com").name("김영희").phone("010-2222-2222")
            .age(30).gender(Member.Gender.FEMALE).build();
        memberMapper.insertMember(member1);
        memberMapper.insertMember(member2);
        
        List<Member> members = memberMapper.findByAgeBetween(20, 27);
        
        assertThat(members).extracting(Member::getName).contains("홍길동");
        assertThat(members).extracting(Member::getName).doesNotContain("김영희");
        assertThat(members).allMatch(member -> member.getAge() >= 20 && member.getAge() <= 27);
    }

    @Test
    @DisplayName("성별로 회원 조회 - ENUM 타입 처리")
    void findByGender_success() {
        // 목적: ENUM 타입을 문자열로 비교하는 SQL 작성
        // 성공 조건: 특정 성별의 모든 회원이 조회됨
        assumeTrue(memberMapper != null, "MyBatis 매퍼가 없음 - ref 프로필로 실행하세요");
        
        Member member1 = Member.builder()
            .email("test1@test.com").name("홍길동").phone("010-1111-1111")
            .age(25).gender(Member.Gender.MALE).build();
        Member member2 = Member.builder()
            .email("test2@test.com").name("김영희").phone("010-2222-2222")
            .age(23).gender(Member.Gender.FEMALE).build();
        memberMapper.insertMember(member1);
        memberMapper.insertMember(member2);
        
        List<Member> members = memberMapper.findByGender("MALE");
        
        assertThat(members).extracting(Member::getName).contains("홍길동");
        assertThat(members).extracting(Member::getName).doesNotContain("김영희");
        assertThat(members).allMatch(member -> member.getGender() == Member.Gender.MALE);
    }

    // ========== 엣지 케이스 테스트 ==========

    @Test
    @DisplayName("존재하지 않는 ID 조회 - NULL 처리 확인")
    void findMemberById_nonExistentId_returnsNull() {
        // 목적: 조회 결과가 없을 때 NULL 반환 확인
        // 성공 조건: 존재하지 않는 ID로 조회 시 null 반환
        assumeTrue(memberMapper != null, "MyBatis 매퍼가 없음 - ref 프로필로 실행하세요");
        
        Member notFound = memberMapper.findMemberById(99999L);
        
        assertThat(notFound).isNull();
    }

    @Test
    @DisplayName("존재하지 않는 이메일 조회 - NULL 처리 확인")
    void findMemberByEmail_nonExistentEmail_returnsNull() {
        // 목적: 존재하지 않는 이메일 조회 시 NULL 처리 확인
        // 성공 조건: 존재하지 않는 이메일로 조회 시 null 반환
        assumeTrue(memberMapper != null, "MyBatis 매퍼가 없음 - ref 프로필로 실행하세요");
        
        Member notFound = memberMapper.findMemberByEmail("nonexistent@test.com");
        
        assertThat(notFound).isNull();
    }

    @Test
    @DisplayName("존재하지 않는 이메일 카운트 - 0 반환 확인")
    void countByEmail_nonExistentEmail_returnsZero() {
        // 목적: COUNT 함수가 결과 없을 때 0 반환하는지 확인
        // 성공 조건: 존재하지 않는 이메일의 카운트가 0
        assumeTrue(memberMapper != null, "MyBatis 매퍼가 없음 - ref 프로필로 실행하세요");
        
        int count = memberMapper.countByEmail("nonexistent@test.com");
        
        assertThat(count).isEqualTo(0);
    }

    @Test
    @DisplayName("존재하지 않는 이름 검색 - 빈 리스트 반환")
    void findByNameContaining_nonExistentName_returnsEmptyList() {
        // 목적: 검색 결과가 없을 때 빈 리스트 반환 확인
        // 성공 조건: 매칭되는 이름이 없을 때 빈 List 반환
        assumeTrue(memberMapper != null, "MyBatis 매퍼가 없음 - ref 프로필로 실행하세요");
        
        List<Member> members = memberMapper.findByNameContaining("존재하지않는이름");
        
        assertThat(members).isEmpty();
    }

    @Test
    @DisplayName("범위 밖 나이 검색 - 빈 리스트 반환")
    void findByAgeBetween_outOfRange_returnsEmptyList() {
        // 목적: BETWEEN 조건에 맞지 않을 때 빈 리스트 반환 확인
        // 성공 조건: 나이 범위에 해당하지 않을 때 빈 List 반환
        assumeTrue(memberMapper != null, "MyBatis 매퍼가 없음 - ref 프로필로 실행하세요");
        
        Member member = Member.builder()
            .email("test@test.com").name("홍길동").phone("010-1234-5678")
            .age(25).gender(Member.Gender.MALE).build();
        memberMapper.insertMember(member);
        
        List<Member> members = memberMapper.findByAgeBetween(30, 40); // 25세는 범위 밖
        
        assertThat(members).extracting(Member::getName).doesNotContain("홍길동");
    }

    @Test
    @DisplayName("존재하지 않는 ID 삭제 - 예외 없이 처리")
    void deleteMember_nonExistentId_noError() {
        // 목적: 존재하지 않는 데이터 삭제 시 오류 없이 처리되는지 확인
        // 성공 조건: 존재하지 않는 ID 삭제 시 예외 발생하지 않음
        assumeTrue(memberMapper != null, "MyBatis 매퍼가 없음 - ref 프로필로 실행하세요");
        
        // 존재하지 않는 ID 삭제 시도 - 예외가 발생하지 않아야 함
        memberMapper.deleteMember(99999L);
        
        // 테스트 통과하면 성공
        assertThat(true).isTrue();
    }

    @Test
    @DisplayName("잘못된 성별 값 검색 - 빈 리스트 반환")
    void findByGender_invalidGender_returnsEmptyList() {
        // 목적: 유효하지 않은 ENUM 값 조회 시 빈 리스트 반환 확인
        // 성공 조건: 잘못된 성별 값으로 조회 시 빈 List 반환
        assumeTrue(memberMapper != null, "MyBatis 매퍼가 없음 - ref 프로필로 실행하세요");
        
        List<Member> members = memberMapper.findByGender("INVALID_GENDER");
        
        assertThat(members).isEmpty();
    }
}