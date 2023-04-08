

# 🍋 사전준비
1. openjdk 17 설치
2. intelliJ 설치
3. git 및 소스관리툴(sourcetree 등) 설치
4. DB 접속 툴(DBeaver 등) 설치
5. Git 활용수준을 아래 2단계 수준이상으로 맞추기
6. 기술 사전학습(springboot, jpa, redis)

# 🍎 Git 활용수준
1. 개인적으로 Git을 활용하는 단계
    - Git 으로 branch 관리없이 commit하고 push 용도로만 사용하고 있다.
1. Git으로 협업이 가능한 단계(🙏)
    - Git을 이용해 branch를 생성해서 개발하고, 병합하고 충돌을 해결할 수 있다.
    - 로컬과 origin의 브랜치 현황을 통해 진행사항을 확인가능하며, 협업으로 개발이 가능하다.
    - github에 있는 pull request나 review 등 기능을 사용해봤다. github 배지획득 😀
    - 각종 툴을 통해 Git 관리를 어느정도 해봤다(git command, SourceTree, GitKraken 등
    - Git flow 등 깃브랜치 전략을 이해하고 사용해봤다.

1. Git으로 업무에서 활용이 가능한 단계
    - 실제 업무에서 Git을 활용해서 여러사람과 협업을 수행하고, 거기서 발생하는 다양한 이슈를 해결해봤다.
    - amend, revert, cherry-pick, stash 등 기능을 자유롭게 사용한다.



<p>

# 🍐 Git 브랜치 전략
1. [main, dev]브랜치에는 직접 커밋을 올리지 않는다.
1. 기능 개발을 하기전에 [dev]브랜치를 기준으로 새로운 브랜치를 만든다.
1. 이 브랜치 이름은 [feature/기능이름] 형식으로 하고, 한 명만 커밋을 올린다.
1. [feature/기능이름] 브랜치에서 기능 개발이 끝나면, [feature/기능이름]에서 [dev]브랜치를 병합한다.(충돌이 발생하면 해결)
1. 위 병합이 이상없으면, [dev] 브랜치로 pull request를 요청한다.
1. N명이상 review를 거쳐 승인이되면, [dev]브랜치로 Merge하며, 자동으로 개발서버에 반영한다.
1. 운영담당자는 [dev]반영내용 검토를 거쳐 [main] 으로의 pull request & Merge하며, 운영서버 반영한다.

# 🍇 Git 브랜치 조건
1. [feature]브랜치에서는 빌드실패/테스트코드 실패를 허용한다.
1. [dev]브랜치에는 빌드실패/테스트코드 실패를 허용하지 않는다.
1. [main]브랜치는 운영에 반영가능한 수준이어야 한다(심각한 버그가 있으면 안된다)


# 🍒 패키지 전략
1. application layer를 퍼사드패턴으로 적용 https://gdtbgl93.tistory.com/142
2. domain에서 리턴하는 결과는 info클래스로 리턴

