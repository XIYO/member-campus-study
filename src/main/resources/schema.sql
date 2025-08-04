-- 회원 캠퍼스 스터디 데이터베이스 스키마
-- JPA와 MyBatis가 공통으로 사용하는 테이블 정의

-- 기존 테이블 삭제 (개발용)
DROP TABLE IF EXISTS profile CASCADE;
DROP TABLE IF EXISTS member CASCADE;

-- 회원 테이블 (AUTO_INCREMENT 사용)
CREATE TABLE member (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    name VARCHAR(50) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    age INTEGER,
    gender VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 회원 프로필 테이블 (AUTO_INCREMENT 사용)
CREATE TABLE profile (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL UNIQUE,
    nickname VARCHAR(50),
    name VARCHAR(50),
    profile_image_url VARCHAR(500),
    postal_code VARCHAR(10),
    address VARCHAR(200),
    address_detail VARCHAR(200),
    mobile_phone VARCHAR(20),
    memo VARCHAR(1000),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (member_id) REFERENCES member(id) ON DELETE CASCADE
);

-- 인덱스 생성
CREATE INDEX idx_member_email ON member(email);
CREATE INDEX idx_profile_member_id ON profile(member_id);
CREATE INDEX idx_profile_nickname ON profile(nickname);