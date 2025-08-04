package campus.membercampusstudy.controller;

import campus.membercampusstudy.entity.Member;
import campus.membercampusstudy.entity.Profile;
import campus.membercampusstudy.mapper.IMemberMapper;
import campus.membercampusstudy.mapper.IProfileMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * MyBatis 회원 관리 컨트롤러
 * <p>
 * MyBatis Mapper를 직접 사용하여 회원 및 프로필 관리 기능을 제공합니다.
 * 이 컨트롤러는 MyBatis 학습을 위한 실습용 API들을 포함하고 있습니다.
 * <p>
 * 주요 기능:
 * <ul>
 *   <li>회원 CRUD 작업 (JSON 및 Form 방식)</li>
 *   <li>프로필 관리</li>
 *   <li>이메일 중복 확인</li>
 * </ul>
 * 
 * @author XIYO
 * @since 2025-08-02
 */
@Tag(name = "MyBatis 회원 관리", description = "MyBatis Mapper를 직접 사용한 회원 관리 API")
@RestController
@RequestMapping("/api/mybatis/members")
@Slf4j
public class MyBatisMemberController {
    
    private final IMemberMapper memberMapper;
    private final IProfileMapper memberProfileMapper;
    
    /**
     * MyBatis 회원 관리 컨트롤러 생성자
     * 
     * @param memberMapper 회원 매퍼 인터페이스
     * @param memberProfileMapper 프로필 매퍼 인터페이스
     */
    public MyBatisMemberController(IMemberMapper memberMapper, IProfileMapper memberProfileMapper) {
        this.memberMapper = memberMapper;
        this.memberProfileMapper = memberProfileMapper;
    }
    
    /**
     * 새로운 회원을 등록합니다
     * <p>
     * 이메일 중복을 확인한 후 회원을 등록합니다.
     * 
     * @param member 등록할 회원 정보
     * @return 등록된 회원 정보
     */
    @Operation(summary = "회원 가입", description = "새로운 회원을 등록합니다")
    @PostMapping
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        log.info("MyBatis 회원가입 요청: {}", member.getEmail());
        
        // 이메일 중복 확인
        if (memberMapper.countByEmail(member.getEmail()) > 0) {
            return ResponseEntity.badRequest().build();
        }
        
        memberMapper.insertMember(member);
        return ResponseEntity.ok(member);
    }
    
    /**
     * 등록된 모든 회원 목록을 조회합니다
     * 
     * @return 전체 회원 목록
     */
    @Operation(summary = "전체 회원 조회", description = "등록된 모든 회원 목록을 조회합니다")
    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        log.info("MyBatis 전체 회원 조회 요청");
        List<Member> members = memberMapper.findAllMembers();
        return ResponseEntity.ok(members);
    }
    
    /**
     * ID로 특정 회원을 조회합니다
     * 
     * @param id 조회할 회원 ID
     * @return 조회된 회원 정보 또는 404 Not Found
     */
    @Operation(summary = "회원 상세 조회", description = "ID로 특정 회원을 조회합니다")
    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        log.info("MyBatis 회원 상세 조회 요청: {}", id);
        Member member = memberMapper.findMemberById(id);
        return member != null ? ResponseEntity.ok(member) : ResponseEntity.notFound().build();
    }
    
    /**
     * 이메일로 회원을 조회합니다
     * 
     * @param email 조회할 이메일 주소
     * @return 조회된 회원 정보 또는 404 Not Found
     */
    @Operation(summary = "이메일로 회원 조회", description = "이메일로 회원을 조회합니다")
    @GetMapping("/email/{email}")
    public ResponseEntity<Member> getMemberByEmail(@PathVariable String email) {
        log.info("MyBatis 이메일 회원 조회 요청: {}", email);
        Member member = memberMapper.findMemberByEmail(email);
        return member != null ? ResponseEntity.ok(member) : ResponseEntity.notFound().build();
    }
    
    @Operation(summary = "이메일 중복 확인", description = "이메일 중복 여부를 확인합니다")
    @GetMapping("/email/{email}/exists")
    public ResponseEntity<Boolean> checkEmailExists(@PathVariable String email) {
        log.info("MyBatis 이메일 중복 확인: {}", email);
        boolean exists = memberMapper.countByEmail(email) > 0;
        return ResponseEntity.ok(exists);
    }
    
    @Operation(summary = "회원 탈퇴", description = "회원을 삭제합니다")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        log.info("MyBatis 회원 탈퇴 요청: {}", id);
        
        Member member = memberMapper.findMemberById(id);
        if (member == null) {
            return ResponseEntity.notFound().build();
        }
        
        // 프로필 먼저 삭제
        memberProfileMapper.deleteProfileByMemberId(id);
        // 회원 삭제
        memberMapper.deleteMember(id);
        
        return ResponseEntity.ok().build();
    }
    
    @Operation(summary = "프로필 등록/수정", description = "회원 프로필을 등록하거나 수정합니다")
    @PostMapping("/{id}/profile")
    public ResponseEntity<Profile> saveProfile(@PathVariable Long id, @RequestBody Profile profile) {
        log.info("MyBatis 프로필 등록/수정 요청: {}", id);
        
        Member member = memberMapper.findMemberById(id);
        if (member == null) {
            return ResponseEntity.notFound().build();
        }
        
        // 회원 ID 설정
        profile.setMemberId(id);
        
        // 기존 프로필이 있는지 확인
        Profile existingProfile = memberProfileMapper.findProfileByMemberId(id);
        if (existingProfile != null) {
            profile.setId(existingProfile.getId());
            memberProfileMapper.updateProfile(profile);
        } else {
            memberProfileMapper.insertProfile(profile);
        }
        
        return ResponseEntity.ok(profile);
    }
    
    @Operation(summary = "프로필 조회", description = "회원의 프로필을 조회합니다")
    @GetMapping("/{id}/profile")
    public ResponseEntity<Profile> getProfile(@PathVariable Long id) {
        log.info("MyBatis 프로필 조회 요청: {}", id);
        
        Profile profile = memberProfileMapper.findProfileByMemberId(id);
        return profile != null ? ResponseEntity.ok(profile) : ResponseEntity.notFound().build();
    }
    
    // ====== Form 기반 API (학습용) ======
    
    @Operation(summary = "회원 등록 (Form)", description = "Form 데이터로 새로운 회원을 등록합니다")
    @PostMapping("/form")
    public ResponseEntity<Member> createMemberForm(@ModelAttribute Member member) {
        log.info("MyBatis Form 회원 등록 요청: {}", member);
        
        // 기본 검증
        if (member.getEmail() == null || member.getEmail().trim().isEmpty()) {
            log.warn("이메일이 누락됨");
            return ResponseEntity.badRequest().build();
        }
        
        memberMapper.insertMember(member);
        log.info("MyBatis Form 회원 등록 성공: {}", member.getId());
        
        return ResponseEntity.ok(member);
    }
    
    @Operation(summary = "회원 수정 (Form)", description = "Form 데이터로 회원 정보를 수정합니다")
    @PutMapping("/{id}/form")
    public ResponseEntity<Member> updateMemberForm(@PathVariable Long id, @ModelAttribute Member member) {
        log.info("MyBatis Form 회원 수정 요청 - ID: {}, Member: {}", id, member);
        
        Member existingMember = memberMapper.findMemberById(id);
        if (existingMember == null) {
            return ResponseEntity.notFound().build();
        }
        
        // null이 아닌 필드만 업데이트
        if (member.getEmail() != null && !member.getEmail().trim().isEmpty()) {
            existingMember.setEmail(member.getEmail());
        }
        if (member.getName() != null && !member.getName().trim().isEmpty()) {
            existingMember.setName(member.getName());
        }
        if (member.getPhone() != null && !member.getPhone().trim().isEmpty()) {
            existingMember.setPhone(member.getPhone());
        }
        if (member.getAge() != null) {
            existingMember.setAge(member.getAge());
        }
        if (member.getGender() != null) {
            existingMember.setGender(member.getGender());
        }
        
        memberMapper.updateMember(existingMember);
        log.info("MyBatis Form 회원 수정 성공: {}", existingMember.getId());
        
        return ResponseEntity.ok(existingMember);
    }
    
    @Operation(summary = "프로필 등록 (Form)", description = "Form 데이터로 새로운 프로필을 등록합니다")
    @PostMapping("/{id}/profile/form")
    public ResponseEntity<Profile> createProfileForm(@PathVariable Long id, @ModelAttribute Profile profile) {
        log.info("MyBatis Form 프로필 등록 요청 - Member ID: {}, Profile: {}", id, profile);
        
        // 회원 존재 확인
        Member member = memberMapper.findMemberById(id);
        if (member == null) {
            return ResponseEntity.notFound().build();
        }
        
        // 회원 ID 설정
        profile.setMemberId(id);
        
        // 기존 프로필이 있는지 확인
        Profile existingProfile = memberProfileMapper.findProfileByMemberId(id);
        if (existingProfile != null) {
            profile.setId(existingProfile.getId());
            memberProfileMapper.updateProfile(profile);
        } else {
            memberProfileMapper.insertProfile(profile);
        }
        
        log.info("MyBatis Form 프로필 등록 성공: {}", profile.getId());
        
        return ResponseEntity.ok(profile);
    }
}