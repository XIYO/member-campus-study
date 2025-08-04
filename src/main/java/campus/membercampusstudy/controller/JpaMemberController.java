package campus.membercampusstudy.controller;

import campus.membercampusstudy.entity.Member;
import campus.membercampusstudy.entity.Profile;
import campus.membercampusstudy.repository.MemberRepository;
import campus.membercampusstudy.repository.ProfileRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * JPA 회원 관리 컨트롤러
 * <p>
 * JPA Repository를 사용하여 회원 및 프로필 관리 기능을 제공합니다.
 * 이 컨트롤러는 JPA와 MyBatis를 비교 학습할 수 있도록 설계되었습니다.
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
@Tag(name = "JPA 회원 관리", description = "JPA Repository를 직접 사용한 회원 관리 API")
@RestController
@RequestMapping("/api/jpa/members")
@RequiredArgsConstructor
@Slf4j
public class JpaMemberController {
    
    private final MemberRepository memberRepository;
    private final ProfileRepository memberProfileRepository;
    
    @Operation(summary = "회원 가입", description = "새로운 회원을 등록합니다")
    @PostMapping
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        log.info("JPA 회원가입 요청: {}", member.getEmail());
        
        // 이메일 중복 확인
        if (memberRepository.existsByEmail(member.getEmail())) {
            return ResponseEntity.badRequest().build();
        }
        
        Member savedMember = memberRepository.save(member);
        return ResponseEntity.ok(savedMember);
    }
    
    @Operation(summary = "전체 회원 조회", description = "등록된 모든 회원 목록을 조회합니다")
    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        log.info("JPA 전체 회원 조회 요청");
        List<Member> members = memberRepository.findAll();
        return ResponseEntity.ok(members);
    }
    
    @Operation(summary = "회원 상세 조회", description = "ID로 특정 회원을 조회합니다")
    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        log.info("JPA 회원 상세 조회 요청: {}", id);
        Optional<Member> member = memberRepository.findById(id);
        return member.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "이메일로 회원 조회", description = "이메일로 회원을 조회합니다")
    @GetMapping("/email/{email}")
    public ResponseEntity<Member> getMemberByEmail(@PathVariable String email) {
        log.info("JPA 이메일 회원 조회 요청: {}", email);
        Optional<Member> member = memberRepository.findByEmail(email);
        return member.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "이메일 중복 확인", description = "이메일 중복 여부를 확인합니다")
    @GetMapping("/email/{email}/exists")
    public ResponseEntity<Boolean> checkEmailExists(@PathVariable String email) {
        log.info("JPA 이메일 중복 확인: {}", email);
        boolean exists = memberRepository.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }
    
    @Operation(summary = "회원 탈퇴", description = "회원을 삭제합니다")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        log.info("JPA 회원 탈퇴 요청: {}", id);
        
        if (!memberRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        // 프로필 먼저 삭제
        memberProfileRepository.deleteByMemberId(id);
        // 회원 삭제
        memberRepository.deleteById(id);
        
        return ResponseEntity.ok().build();
    }
    
    @Operation(summary = "프로필 등록/수정", description = "회원 프로필을 등록하거나 수정합니다")
    @PostMapping("/{id}/profile")
    public ResponseEntity<Profile> saveProfile(@PathVariable Long id, @RequestBody Profile profile) {
        log.info("JPA 프로필 등록/수정 요청: {}", id);
        
        if (!memberRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        // 회원 ID 설정
        profile.setMemberId(id);
        
        Profile savedProfile = memberProfileRepository.save(profile);
        return ResponseEntity.ok(savedProfile);
    }
    
    @Operation(summary = "프로필 조회", description = "회원의 프로필을 조회합니다")
    @GetMapping("/{id}/profile")
    public ResponseEntity<Profile> getProfile(@PathVariable Long id) {
        log.info("JPA 프로필 조회 요청: {}", id);
        
        Optional<Profile> profile = memberProfileRepository.findByMemberId(id);
        return profile.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    // ====== Form 기반 API (학습용) ======
    
    @Operation(summary = "회원 등록 (Form)", description = "Form 데이터로 새로운 회원을 등록합니다")
    @PostMapping("/form")
    public ResponseEntity<Member> createMemberForm(@ModelAttribute Member member) {
        log.info("JPA Form 회원 등록 요청: {}", member);
        
        // 기본 검증
        if (member.getEmail() == null || member.getEmail().trim().isEmpty()) {
            log.warn("이메일이 누락됨");
            return ResponseEntity.badRequest().build();
        }
        
        Member savedMember = memberRepository.save(member);
        log.info("JPA Form 회원 등록 성공: {}", savedMember.getId());
        
        return ResponseEntity.ok(savedMember);
    }
    
    @Operation(summary = "회원 수정 (Form)", description = "Form 데이터로 회원 정보를 수정합니다")
    @PutMapping("/{id}/form")
    public ResponseEntity<Member> updateMemberForm(@PathVariable Long id, @ModelAttribute Member member) {
        log.info("JPA Form 회원 수정 요청 - ID: {}, Member: {}", id, member);
        
        Optional<Member> existingMemberOpt = memberRepository.findById(id);
        if (existingMemberOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Member existingMember = existingMemberOpt.get();
        
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
        
        Member updatedMember = memberRepository.save(existingMember);
        log.info("JPA Form 회원 수정 성공: {}", updatedMember.getId());
        
        return ResponseEntity.ok(updatedMember);
    }
    
    @Operation(summary = "프로필 등록 (Form)", description = "Form 데이터로 새로운 프로필을 등록합니다")
    @PostMapping("/{id}/profile/form")
    public ResponseEntity<Profile> createProfileForm(@PathVariable Long id, @ModelAttribute Profile profile) {
        log.info("JPA Form 프로필 등록 요청 - Member ID: {}, Profile: {}", id, profile);
        
        try {
            // 회원 존재 확인
            if (!memberRepository.existsById(id)) {
                log.warn("회원을 찾을 수 없음 - ID: {}", id);
                return ResponseEntity.notFound().build();
            }
            
            // 회원 ID 설정
            profile.setMemberId(id);
            log.debug("프로필에 회원 ID 설정 완료 - Profile: {}", profile);
            
            Profile savedProfile = memberProfileRepository.save(profile);
            log.info("JPA Form 프로필 등록 성공: {}", savedProfile.getId());
            
            return ResponseEntity.ok(savedProfile);
            
        } catch (Exception e) {
            log.error("JPA Form 프로필 등록 실패 - Member ID: {}, 에러: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
}