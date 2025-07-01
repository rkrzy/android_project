
# android_project
2025-1학기 소프트웨어 설계 및 실험 과목 텀프로젝트
## 프로젝트 소개
2025-1학기 소프트웨어 설계 및 실험 과목 텀프로젝트 주제를 고민하다가 혼자서 CS, 알고리즘 공부를 수행하는 사람들에게 다른 사람들과 대전 방식으로 공부를 하면서 지루함을 덜어줄 수 있는 애플리케이션을 만들자고 생각하여 프로젝트를 진행하게 되었습니다. 기본적인 기능으로
CS, PS대전 및 Github, solved.ac 아이디를 이용해서 자신의 티어, 잔디 내역을 보여줍니다. 또한, 대전의 결과에 따라서 지급되는 경험치를 기반으로 공격력, 방어력, 체력이 상승하고 해당 능력치를 활용해서 차후 개선방안으로 전투에 활용될 수 있게 만들었습니다. 그리고 AI를 이용해서
백준문제의 티어를 기반으로 전투시 데미지를 줄 수 있게 만들었습니다.

## 프로젝트 기능 소개
- PS대전
- CS대전
- Github ID 기반 커밋내역 확인
- Solved.ac ID 기반 백준티어 확인
- 능력치 기능

## DB테이블 설계
설계는 관계형 데이터베이스를 생각하고 했지만 데이터베이스는 FireBase를 이용해서 수행했기 때문에 실제 저장된 내용과 다릅니다!
![Image](https://github.com/user-attachments/assets/66fb8d9f-9b07-4c39-8816-8ce48ff0e74a)

## 와이어프레임 및 화면 기능 명세
![Image](https://github.com/user-attachments/assets/dd66a1fa-bf0d-46fc-af9d-d6789943b53e)
## 📝 Git 커밋 타입 가이드

| 타입      | 설명 |
|-----------|------|
| `feat`    | ✨ 새로운 기능에 대한 커밋 |
| `fix`     | 🐛 버그 수정에 대한 커밋 |
| `build`   | 🔧 빌드 관련 파일 수정, 모듈 설치 또는 삭제 |
| `chore`   | 🔨 그 외 자잘한 수정 (빌드, 패키지 매니저 설정 등) |
| `ci`      | 🤖 CI 설정 관련 수정 |
| `docs`    | 📝 문서 수정 (README 등) |
| `style`   | 💄 코드 포맷, 세미콜론 누락, 들여쓰기 등 스타일 수정 |
| `refactor`| ♻️  코드 리팩토링 (기능 변화 없음) |
| `test`    | ✅ 테스트 코드 추가 또는 수정 |
| `perf`    | ⚡ 성능 개선 관련 코드 수정 |


## 🧱 기술 스택
- Kotlin, Jetpack Compose, MVVM
- Firebase, Retrofit, GitHub

## 🧱 기술 스택

- Android Studio + Kotlin + Jetpack Compose
- Firebase Authentication
- Retrofit
- Git + GitHub 협업

## 🛠 협업 규칙

- 만드는 기능마다 브랜치 생성
- 구현 완료 후 main으로 pull
- 확인하고 merge진행
- 커밋 메시지 규칙 준수

