-- 회원 캠퍼스 스터디 더미 데이터
-- JPA와 MyBatis 테스트를 위한 초기 데이터

-- 회원 데이터 삽입 (AUTO_INCREMENT 사용)
INSERT INTO member (email, name, phone, age, gender, created_at, updated_at) VALUES
('john.doe@example.com', '김철수', '010-1234-5678', 28, 'MALE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('jane.smith@example.com', '이영희', '010-9876-5432', 25, 'FEMALE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 프로필 데이터 삽입 (회원 ID 1, 2에 대응)
INSERT INTO profile (member_id, nickname, name, profile_image_url, postal_code, address, address_detail, mobile_phone, memo, created_at, updated_at) VALUES
(1, '개발자철수', '김철수', 'https://example.com/profiles/john.jpg', '12345', '서울시 강남구 테헤란로', '123번지 456호', '010-1234-5678', '백엔드 개발자입니다. Java와 Spring을 주로 사용합니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, '디자이너영희', '이영희', 'https://example.com/profiles/jane.jpg', '67890', '서울시 서초구 서초대로', '789번지 101호', '010-9876-5432', 'UI/UX 디자이너입니다. 사용자 경험을 중시합니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);