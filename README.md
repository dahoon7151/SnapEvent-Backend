# SnapEvent - 세일 알림 웹 서비스

## 🛒 프로젝트 개요
SnapEvent는 사용자가 원하는 브랜드의 세일 정보를 실시간으로 받아볼 수 있는 서비스입니다.

📅 **개발 기간:** 2023.09 ~ 2024.05  
👥 **팀원:** 백엔드 2명, 프론트엔드 1명  
🔗 **GitHub Repository:** [SnapEvent](https://github.com/Team-3-SnapEvent)  
🔗 **Notion 문서:** [API 명세서 및 기획](https://www.notion.so/Team-3-23F-67780bfed1bb41c09b89e51a0190e516?pvs=21)  

---

## 🏗 시스템 아키텍처
![System Architecture](https://link-to-system-architecture-image)

---

## 🛠 사용한 기술 스택
### **Backend**
- Spring Boot, Java, JPA, Spring Security
### **Frontend**
- React, Styled-Components, Yarn
### **Database**
- MySQL, Redis
### **Server / Deployment**
- AWS EC2, AWS RDS, AWS Route 53, Node.js, Nginx
### **Collaboration & Tools**
- Git/GitHub, Notion, Miro, Figma, IntelliJ, Postman

---

## 📌 주요 기능
✅ **회원 관리** (JWT + OAuth2 로그인, 회원가입, 로그아웃, 회원 정보 수정)  
✅ **게시판 기능** (게시글/댓글 CRUD, 좋아요 기능, 페이징 처리)  
✅ **구독 시스템** (브랜드 구독, 구독자 리스트, 비회원 구독 정보 유지)  
✅ **검색 및 필터링** (특정 브랜드 검색, 카테고리 필터링)  
✅ **프론트엔드 UI 개발 참여** (Styled-Components 활용, 모달 및 UI 컴포넌트 개발)  

---

## 📄 API 명세서
🔗 [API 명세서 (Notion)](https://www.notion.so/API-596be2293efd489387810d9e81c4c4aa?pvs=21)  

---

## 🔍 데이터베이스 설계 (ERD)
![ERD Diagram]([https://link-to-erd-image](https://github.com/user-attachments/assets/cb75c3c6-82fc-4d73-bbb9-1c49bf6913a9))

---

## 🔀 Git Flow 전략
- `main / develop / feature` 브랜치 전략 적용
- 각 feature 브랜치를 develop으로 병합하여 협업 진행

---

## 🛠 이슈 및 트러블슈팅

### **CORS 에러 해결**
- **문제:** Nginx, Spring Boot, React 간 API 요청 시 CORS 에러 발생
- **해결:** `access-control-allow-credentials: true` 설정 추가 및 `access-control-allow-origin`에서 `*` 제거 후 특정 도메인 허용

### **Spring Security 인증 객체 통합**
- **문제:** 일반 로그인(`UserDetails`)과 소셜 로그인(`OAuth2User`)의 인증 객체가 달라 일관된 사용자 정보 관리가 어려움
- **해결:** `CustomUserDetail` 클래스를 생성하여 두 객체를 통합, 로그인 방식과 관계없이 사용자 정보 관리 가능

### **JWT 토큰 저장 방식 개선**
- **문제:** 보안과 편리함을 고려한 JWT 저장 방식 결정 필요
- **해결:**
  - **Access Token**: JS Private 변수에 저장하여 보안 강화
  - **Refresh Token**: DB와 HttpOnly Cookie에 저장하여 보안 유지

### **서비스 성능 최적화**
- **JPA Auditing 적용:** 엔티티의 생성 및 수정 시간을 자동 관리
- **반정규화 적용:** 댓글 및 좋아요 개수를 엔티티 칼럼으로 저장하여 성능 최적화
- **고아 객체 삭제 적용:** 게시글 삭제 시 연관된 댓글과 좋아요도 자동 삭제

---

## ☁️ 배포 및 운영
### **배포 환경**
🔹 **AWS EC2**: 백엔드 서버 배포  
🔹 **AWS RDS (MySQL)**: 데이터 저장  
🔹 **AWS Route 53**: 도메인 연결  
🔹 **Nginx**: 리버스 프록시 설정 및 SSL 적용  

### **배포 다이어그램**
![Deployment Diagram](https://link-to-deployment-diagram-image)

---

## 📢 회고 및 배운 점
- **백엔드 API 최적화 및 보안 강화를 위한 JWT 및 OAuth2 적용 경험**  
- **Redis를 활용한 데이터 캐싱 및 성능 최적화 고민**  
- **Git Flow 전략을 활용한 협업 경험 및 CI/CD 적용 가능성 탐색**  

---

## 👥 팀원
👨‍💻 **Backend**: 강다훈, 팀원 A  
🎨 **Frontend**: 팀원 B  

---

## 📬 Contact
이메일: [dahoon7151@gmail.com](mailto:dahoon7151@gmail.com)  
GitHub: [https://github.com/dahoon7151](https://github.com/dahoon7151)  

