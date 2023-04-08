
# 🎸 Naver checkstyle 적용 
1. formatter/naver-intellij-formatter.xml
2. formatter/naver-checkstyle-ruls
3. formatter/naver-checkstyle-suppressions.xml


# 🪕 build.gradle 내용추가
```
plugins {
    id 'checkstyle'
}

checkstyle {
    maxWarnings = 0 // 규칙이 어긋나는 코드가 하나라도 있을 경우 빌드 fail을 내고 싶다면 이 선언을 추가한다.
    configFile = file("${rootDir}/formatter/naver-checkstyle-rules.xml")
    configProperties = ["suppressionFile" : "${rootDir}/formatter/naver-checkstyle-suppressions.xml"]
    toolVersion ="8.24"  // checkstyle 버전 8.24 이상 선언
}
```

# 🎧 intelliJ 툴 설정
1. 설정 - 에디터 - Code Style - 구성표(Scheme)에서 import Scheme - IntelliJ IDEA code style XML
2. 위 경로에서 다운받은 naver-intellij-formatter.xml 등록 
3. Plugin 설치 - CheckStyle-IDEA
4. 설정 - 도구 - CheckStyle 에서 + 를 눌러서 naver-checkstyle-rules.xml 설정
5. Suppression에는 naver-checkstyle-suppressions.xml 입력


# 🏵 참고내용
https://naver.github.io/hackday-conventions-java/#_gradle
https://055055.tistory.com/97

