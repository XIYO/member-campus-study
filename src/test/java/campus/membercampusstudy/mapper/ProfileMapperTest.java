package campus.membercampusstudy.mapper;

import campus.membercampusstudy.entity.Member;
import campus.membercampusstudy.entity.Profile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import org.junit.jupiter.api.DisplayName;

/**
 * ProfileMapper 종합 통합 테스트
 * <p>
 * MyBatis 프로필 매퍼의 CRUD 기능을 검증하는 포괄적인 테스트 클래스입니다.
 * 학습용 매퍼(ProfileMapper)와 참고용 매퍼(ProfileMapperRef) 모두에서 동작하며,
 * 회원과 프로필 간의 외래키 관계를 포함한 복합적인 테스트를 수행합니다.
 * <p>
 * 테스트 구성:
 * <ul>
 *   <li>기본 CRUD 작업 (등록, 조회, 수정, 삭제)</li>
 *   <li>외래키 관계 처리 (member_id 연관)</li>
 *   <li>조건부 검색 (닉네임, 주소, 우편번호)</li>
 *   <li>엣지 케이스 (NULL 값, 존재하지 않는 데이터)</li>
 *   <li>연관 데이터 삭제 및 무결성 검증</li>
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
 * @see ProfileMapper
 * @see ProfileMapperRef
 */
@SpringBootTest
@Transactional
class ProfileMapperTest {

    @Autowired(required = false)
    private IMemberMapper memberMapper;
    
    @Autowired(required = false)
    private IProfileMapper profileMapper;

    // ========== 기본 CRUD 테스트 ==========
    
    @Test
    @DisplayName("프로필 등록 - INSERT문 + 외래키 연결")
    void insertProfile_success() {
        // 목적: @Insert로 프로필 등록 SQL 작성, member_id 외래키 처리
        // 성공 조건: 프로필이 DB에 저장되고 ID가 자동 생성됨
        assumeTrue(memberMapper != null && profileMapper != null, "MyBatis 매퍼가 없음 - ref 프로필로 실행하세요");
        
        Member member = Member.builder()
            .email("test@test.com").name("홍길동").phone("010-1234-5678")
            .age(25).gender(Member.Gender.MALE).build();
        memberMapper.insertMember(member);
        
        Profile profile = Profile.createWithMemberId(
            member.getId(), "길동이", "홍길동", null,
            "12345", "서울시 강남구", "역삼동 123-45", "010-1234-5678", "메모"
        );
        
        profileMapper.insertProfile(profile);
        
        assertThat(profile.getId()).isNotNull();
    }

    @Test
    @DisplayName("전체 프로필 조회 - SELECT문 기본 + @Results 매핑")
    void findAllProfiles_success() {
        // 목적: @Select + @Results로 전체 프로필 조회 SQL 작성
        // 성공 조건: 모든 프로필 데이터가 List로 반환됨
        assumeTrue(memberMapper != null && profileMapper != null, "MyBatis 매퍼가 없음 - ref 프로필로 실행하세요");
        
        int initialCount = profileMapper.findAllProfiles().size();
        
        Member member1 = Member.builder()
            .email("test1@test.com").name("홍길동").phone("010-1111-1111")
            .age(25).gender(Member.Gender.MALE).build();
        Member member2 = Member.builder()
            .email("test2@test.com").name("김영희").phone("010-2222-2222")
            .age(23).gender(Member.Gender.FEMALE).build();
        memberMapper.insertMember(member1);
        memberMapper.insertMember(member2);
        
        Profile profile1 = Profile.createWithMemberId(member1.getId(), "길동이", "홍길동", null, null, null, null, null, null);
        Profile profile2 = Profile.createWithMemberId(member2.getId(), "영희", "김영희", null, null, null, null, null, null);
        profileMapper.insertProfile(profile1);
        profileMapper.insertProfile(profile2);
        
        List<Profile> profiles = profileMapper.findAllProfiles();
        
        assertThat(profiles).hasSize(initialCount + 2);
        assertThat(profiles).extracting(Profile::getNickname)
            .contains("길동이", "영희");
    }

    @Test
    @DisplayName("ID로 프로필 조회 - WHERE 조건절")
    void findProfileById_success() {
        // 목적: @Select + WHERE절로 특정 프로필 조회 SQL 작성
        // 성공 조건: ID에 해당하는 프로필 정보가 정확히 반환됨
        assumeTrue(memberMapper != null && profileMapper != null, "MyBatis 매퍼가 없음 - ref 프로필로 실행하세요");
        
        Member member = Member.builder()
            .email("test@test.com").name("홍길동").phone("010-1234-5678")
            .age(25).gender(Member.Gender.MALE).build();
        memberMapper.insertMember(member);
        
        Profile profile = Profile.createWithMemberId(
            member.getId(), "길동이", "홍길동", null, null, null, null, null, null
        );
        profileMapper.insertProfile(profile);
        
        Profile found = profileMapper.findProfileById(profile.getId());
        
        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(profile.getId());
        assertThat(found.getNickname()).isEqualTo("길동이");
    }

    @Test
    @DisplayName("회원 ID로 프로필 조회 - 외래키 사용")
    void findProfileByMemberId_success() {
        // 목적: 외래키(member_id)로 프로필 조회 SQL 작성
        // 성공 조건: 특정 회원의 프로필이 정확히 조회됨
        assumeTrue(memberMapper != null && profileMapper != null, "MyBatis 매퍼가 없음 - ref 프로필로 실행하세요");
        
        Member member = Member.builder()
            .email("test@test.com").name("홍길동").phone("010-1234-5678")
            .age(25).gender(Member.Gender.MALE).build();
        memberMapper.insertMember(member);
        
        Profile profile = Profile.createWithMemberId(
            member.getId(), "길동이", "홍길동", null, null, null, null, null, null
        );
        profileMapper.insertProfile(profile);
        
        Profile found = profileMapper.findProfileByMemberId(member.getId());
        
        assertThat(found).isNotNull();
        assertThat(found.getMemberId()).isEqualTo(member.getId());
        assertThat(found.getNickname()).isEqualTo("길동이");
    }

    @Test
    @DisplayName("프로필 정보 수정 - UPDATE문 다중 컬럼")
    void updateProfile_success() {
        // 목적: @Update로 여러 컬럼 동시 수정 SQL 작성
        // 성공 조건: 모든 수정된 필드가 새로운 값으로 변경됨
        assumeTrue(memberMapper != null && profileMapper != null, "MyBatis 매퍼가 없음 - ref 프로필로 실행하세요");
        
        Member member = Member.builder()
            .email("test@test.com").name("홍길동").phone("010-1234-5678")
            .age(25).gender(Member.Gender.MALE).build();
        memberMapper.insertMember(member);
        
        Profile profile = Profile.createWithMemberId(
            member.getId(), "길동이", "홍길동", null, null, null, null, null, null
        );
        profileMapper.insertProfile(profile);
        
        profile.setNickname("새길동");
        profile.setName("홍길동");
        profile.setProfileImageUrl("new.jpg");
        profile.setPostalCode("54321");
        profile.setAddress("부산시");
        profile.setAddressDetail("해운대");
        profile.setMobilePhone("010-9999-9999");
        profile.setMemo("새메모");
        profileMapper.updateProfile(profile);
        
        Profile updated = profileMapper.findProfileById(profile.getId());
        assertThat(updated.getNickname()).isEqualTo("새길동");
        assertThat(updated.getProfileImageUrl()).isEqualTo("new.jpg");
        assertThat(updated.getPostalCode()).isEqualTo("54321");
        assertThat(updated.getAddress()).isEqualTo("부산시");
    }

    @Test
    @DisplayName("프로필 삭제 - DELETE문 기본")
    void deleteProfile_success() {
        // 목적: @Delete로 프로필 ID 삭제 SQL 작성
        // 성공 조건: 특정 ID의 프로필이 DB에서 완전히 삭제됨
        assumeTrue(memberMapper != null && profileMapper != null, "MyBatis 매퍼가 없음 - ref 프로필로 실행하세요");
        
        Member member = Member.builder()
            .email("test@test.com").name("홍길동").phone("010-1234-5678")
            .age(25).gender(Member.Gender.MALE).build();
        memberMapper.insertMember(member);
        
        Profile profile = Profile.createWithMemberId(
            member.getId(), "길동이", "홍길동", null, null, null, null, null, null
        );
        profileMapper.insertProfile(profile);
        
        profileMapper.deleteProfile(profile.getId());
        
        Profile deleted = profileMapper.findProfileById(profile.getId());
        assertThat(deleted).isNull();
    }

    @Test
    @DisplayName("회원 ID로 프로필 삭제 - 외래키 삭제")
    void deleteProfileByMemberId_success() {
        // 목적: @Delete로 외래키(member_id) 사용 삭제 SQL 작성
        // 성공 조건: 특정 회원의 프로필이 완전히 삭제됨
        assumeTrue(memberMapper != null && profileMapper != null, "MyBatis 매퍼가 없음 - ref 프로필로 실행하세요");
        
        Member member = Member.builder()
            .email("test@test.com").name("홍길동").phone("010-1234-5678")
            .age(25).gender(Member.Gender.MALE).build();
        memberMapper.insertMember(member);
        
        Profile profile = Profile.createWithMemberId(
            member.getId(), "길동이", "홍길동", null, null, null, null, null, null
        );
        profileMapper.insertProfile(profile);
        
        profileMapper.deleteProfileByMemberId(member.getId());
        
        Profile deleted = profileMapper.findProfileByMemberId(member.getId());
        assertThat(deleted).isNull();
    }

    // ========== 검색 기능 테스트 ==========

    @Test
    @DisplayName("회원 ID로 프로필 존재 확인 - COUNT 활용")
    void existsByMemberId_success() {
        // 목적: COUNT를 boolean으로 변환하는 default 메서드 학습
        // 성공 조건: 프로필이 있으면 true, 없으면 false 반환
        assumeTrue(memberMapper != null && profileMapper != null, "MyBatis 매퍼가 없음 - ref 프로필로 실행하세요");
        
        Member member = Member.builder()
            .email("test@test.com").name("홍길동").phone("010-1234-5678")
            .age(25).gender(Member.Gender.MALE).build();
        memberMapper.insertMember(member);
        
        Profile profile = Profile.createWithMemberId(
            member.getId(), "길동이", "홍길동", null, null, null, null, null, null
        );
        profileMapper.insertProfile(profile);
        
        boolean exists = profileMapper.existsByMemberId(member.getId());
        
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("닉네임 검색 - LIKE + CONCAT 연산자")
    void findByNicknameContaining_success() {
        // 목적: LIKE + CONCAT으로 닉네임 부분 일치 검색 SQL 작성
        // 성공 조건: 닉네임에 특정 문자열이 포함된 모든 프로필 조회
        assumeTrue(memberMapper != null && profileMapper != null, "MyBatis 매퍼가 없음 - ref 프로필로 실행하세요");
        
        int initialCount = profileMapper.findByNicknameContaining("길").size();
        
        Member member1 = Member.builder()
            .email("test1@test.com").name("홍길동").phone("010-1111-1111")
            .age(25).gender(Member.Gender.MALE).build();
        Member member2 = Member.builder()
            .email("test2@test.com").name("김영수").phone("010-2222-2222")
            .age(23).gender(Member.Gender.MALE).build();
        memberMapper.insertMember(member1);
        memberMapper.insertMember(member2);
        
        Profile profile1 = Profile.createWithMemberId(member1.getId(), "홍길동이", "홍길동", null, null, null, null, null, null);
        Profile profile2 = Profile.createWithMemberId(member2.getId(), "길수", "김영수", null, null, null, null, null, null);
        profileMapper.insertProfile(profile1);
        profileMapper.insertProfile(profile2);
        
        List<Profile> profiles = profileMapper.findByNicknameContaining("길");
        
        assertThat(profiles).hasSize(initialCount + 2);
        assertThat(profiles).extracting(Profile::getNickname)
            .contains("홍길동이", "길수");
    }

    @Test
    @DisplayName("주소 검색 - 문자열 부분 검색")
    void findByAddressContaining_success() {
        // 목적: 주소 속 특정 단어로 다중 프로필 검색 SQL 작성
        // 성공 조건: 주소에 특정 지역명이 포함된 프로필만 조회
        assumeTrue(memberMapper != null && profileMapper != null, "MyBatis 매퍼가 없음 - ref 프로필로 실행하세요");
        
        Member member1 = Member.builder()
            .email("test1@test.com").name("홍길동").phone("010-1111-1111")
            .age(25).gender(Member.Gender.MALE).build();
        Member member2 = Member.builder()
            .email("test2@test.com").name("김영희").phone("010-2222-2222")
            .age(23).gender(Member.Gender.FEMALE).build();
        memberMapper.insertMember(member1);
        memberMapper.insertMember(member2);
        
        Profile profile1 = Profile.createWithMemberId(member1.getId(), "길동이", "홍길동", null, null, "서울시 강남구", null, null, null);
        Profile profile2 = Profile.createWithMemberId(member2.getId(), "영희", "김영희", null, null, "부산시 해운대구", null, null, null);
        profileMapper.insertProfile(profile1);
        profileMapper.insertProfile(profile2);
        
        List<Profile> profiles = profileMapper.findByAddressContaining("서울");
        
        assertThat(profiles).extracting(Profile::getNickname).contains("길동이");
        assertThat(profiles).extracting(Profile::getNickname).doesNotContain("영희");
    }

    @Test
    @DisplayName("우편번호로 검색 - 정확한 일치 검색")
    void findByPostalCode_success() {
        // 목적: 우편번호 정확한 일치로 다중 프로필 검색 SQL 작성
        // 성공 조건: 동일한 우편번호를 가진 모든 프로필 조회
        assumeTrue(memberMapper != null && profileMapper != null, "MyBatis 매퍼가 없음 - ref 프로필로 실행하세요");
        
        Member member = Member.builder()
            .email("test@test.com").name("홍길동").phone("010-1234-5678")
            .age(25).gender(Member.Gender.MALE).build();
        memberMapper.insertMember(member);
        
        Profile profile = Profile.createWithMemberId(
            member.getId(), "길동이", "홍길동", null, "12345", "서울시", null, null, null
        );
        profileMapper.insertProfile(profile);
        
        List<Profile> profiles = profileMapper.findByPostalCode("12345");
        
        assertThat(profiles).extracting(Profile::getNickname).contains("길동이");
        assertThat(profiles).allMatch(p -> "12345".equals(p.getPostalCode()));
        Profile testProfile = profiles.stream()
            .filter(p -> "길동이".equals(p.getNickname()))
            .findFirst()
            .orElseThrow();
        assertThat(testProfile.getAddress()).isEqualTo("서울시");
    }

    // ========== 엣지 케이스 테스트 ==========

    @Test
    @DisplayName("존재하지 않는 프로필 ID 조회 - NULL 처리")
    void findProfileById_nonExistentId_returnsNull() {
        // 목적: 조회 결과가 없을 때 NULL 반환 확인
        // 성공 조건: 존재하지 않는 프로필 ID로 조회 시 null 반환
        assumeTrue(memberMapper != null && profileMapper != null, "MyBatis 매퍼가 없음 - ref 프로필로 실행하세요");
        
        Profile notFound = profileMapper.findProfileById(99999L);
        
        assertThat(notFound).isNull();
    }

    @Test
    @DisplayName("존재하지 않는 회원 ID로 프로필 조회 - NULL 처리")
    void findProfileByMemberId_nonExistentMemberId_returnsNull() {
        // 목적: 외래키로 조회 시 결과 없을 때 NULL 반환 확인
        // 성공 조건: 존재하지 않는 회원 ID로 조회 시 null 반환
        assumeTrue(memberMapper != null && profileMapper != null, "MyBatis 매퍼가 없음 - ref 프로필로 실행하세요");
        
        Profile notFound = profileMapper.findProfileByMemberId(99999L);
        
        assertThat(notFound).isNull();
    }

    @Test
    @DisplayName("존재하지 않는 회원 ID 존재 확인 - FALSE 반환")
    void existsByMemberId_nonExistentMemberId_returnsFalse() {
        // 목적: COUNT = 0일 때 false 반환하는 default 메서드 학습
        // 성공 조건: 존재하지 않는 회원 ID에 대해 false 반환
        assumeTrue(memberMapper != null && profileMapper != null, "MyBatis 매퍼가 없음 - ref 프로필로 실행하세요");
        
        boolean exists = profileMapper.existsByMemberId(99999L);
        
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("존재하지 않는 닉네임 검색 - 빈 리스트 반환")
    void findByNicknameContaining_nonExistentNickname_returnsEmptyList() {
        // 목적: LIKE 검색 결과가 없을 때 빈 리스트 반환 확인
        // 성공 조건: 매칭되는 닉네임이 없을 때 빈 List 반환
        assumeTrue(memberMapper != null && profileMapper != null, "MyBatis 매퍼가 없음 - ref 프로필로 실행하세요");
        
        List<Profile> profiles = profileMapper.findByNicknameContaining("존재하지않는닉네임");
        
        assertThat(profiles).isEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 주소 검색 - 빈 리스트 반환")
    void findByAddressContaining_nonExistentAddress_returnsEmptyList() {
        // 목적: 주소 부분 검색 결과가 없을 때 빈 리스트 반환 확인
        // 성공 조건: 매칭되는 주소가 없을 때 빈 List 반환
        assumeTrue(memberMapper != null && profileMapper != null, "MyBatis 매퍼가 없음 - ref 프로필로 실행하세요");
        
        List<Profile> profiles = profileMapper.findByAddressContaining("존재하지않는주소");
        
        assertThat(profiles).isEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 우편번호 검색 - 빈 리스트 반환")
    void findByPostalCode_nonExistentPostalCode_returnsEmptyList() {
        // 목적: 정확한 우편번호 검색 결과가 없을 때 빈 리스트 반환 확인
        // 성공 조건: 매칭되는 우편번호가 없을 때 빈 List 반환
        assumeTrue(memberMapper != null && profileMapper != null, "MyBatis 매퍼가 없음 - ref 프로필로 실행하세요");
        
        List<Profile> profiles = profileMapper.findByPostalCode("99999");
        
        assertThat(profiles).isEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 프로필 ID 삭제 - 예외 없이 처리")
    void deleteProfile_nonExistentId_noError() {
        // 목적: 존재하지 않는 데이터 삭제 시 오류 없이 처리되는지 확인
        // 성공 조건: 존재하지 않는 프로필 ID 삭제 시 예외 발생하지 않음
        assumeTrue(memberMapper != null && profileMapper != null, "MyBatis 매퍼가 없음 - ref 프로필로 실행하세요");
        
        // 존재하지 않는 ID 삭제 시도 - 예외가 발생하지 않아야 함
        profileMapper.deleteProfile(99999L);
        
        // 테스트 통과하면 성공
        assertThat(true).isTrue();
    }

    @Test
    @DisplayName("존재하지 않는 회원 ID로 프로필 삭제 - 예외 없이 처리")
    void deleteProfileByMemberId_nonExistentMemberId_noError() {
        // 목적: 외래키로 삭제 시 존재하지 않아도 오류 없이 처리되는지 확인
        // 성공 조건: 존재하지 않는 회원 ID로 삭제 시 예외 발생하지 않음
        assumeTrue(memberMapper != null && profileMapper != null, "MyBatis 매퍼가 없음 - ref 프로필로 실행하세요");
        
        // 존재하지 않는 회원 ID로 프로필 삭제 시도 - 예외가 발생하지 않아야 함
        profileMapper.deleteProfileByMemberId(99999L);
        
        // 테스트 통과하면 성공
        assertThat(true).isTrue();
    }

    @Test
    @DisplayName("NULL 값 포함 프로필 등록 - NULL 처리")
    void insertProfile_withNullValues_success() {
        // 목적: 선택적 필드에 NULL 값 삽입 가능하도록 SQL 작성
        // 성공 조건: 필수 필드만 있어도 프로필 생성 가능
        assumeTrue(memberMapper != null && profileMapper != null, "MyBatis 매퍼가 없음 - ref 프로필로 실행하세요");
        
        Member member = Member.builder()
            .email("test@test.com").name("홍길동").phone("010-1234-5678")
            .age(25).gender(Member.Gender.MALE).build();
        memberMapper.insertMember(member);
        
        // 대부분 필드가 NULL인 프로필 생성
        Profile profile = Profile.createWithMemberId(
            member.getId(), "최소프로필", null, null, null, null, null, null, null
        );
        
        profileMapper.insertProfile(profile);
        
        assertThat(profile.getId()).isNotNull();
        
        Profile found = profileMapper.findProfileById(profile.getId());
        assertThat(found.getNickname()).isEqualTo("최소프로필");
        assertThat(found.getName()).isNull();
        assertThat(found.getAddress()).isNull();
    }
}