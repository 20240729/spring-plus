# SPRING PLUS 주차 개인과제

## 문제 해결 내역

### **1. 코드 개선 퀴즈 - @Transactional의 이해**

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/83c75a39-3aba-4ba4-a792-7aefe4b07895/34d42dcb-7d2a-4f20-96d2-5487ae2a2928/image.png)

- 할 일 저장 기능을 구현한 API(`/todos`)를 호출할 때, 아래와 같은 에러가 발생하고 있어요.

  ![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/83c75a39-3aba-4ba4-a792-7aefe4b07895/e70cf554-ee20-4fce-8910-2d8440253199/image.png)

    - 에러 로그 원문 보기

      jakarta.servlet.ServletException: Request processing failed: org.springframework.orm.jpa.JpaSystemException: could not execute statement [Connection is read-only. Queries leading to data modification are not allowed] [insert into todos (contents,created_at,modified_at,title,user_id,weather) values (?,?,?,?,?,?)]

- 에러가 발생하지 않고 정상적으로 할 일을 저장 할 수 있도록 코드를 수정해주세요.

>해결 방법 : WeatherDto의 필드의 final을 제거하고 기본 생성자 추가, TodoService의 할일 저장 메서드에 transactional 어노테이션 추가, 나머지에 readonly 추가

### **2. 코드 추가 퀴즈 - JWT의 이해**

<aside>
🚨 기획자의 긴급 요청이 왔어요!
아래의 요구사항에 맞춰 기획 요건에 대응할 수 있는 코드를 작성해주세요.

</aside>

- User의 정보에 nickname이 필요해졌어요.
    - User 테이블에 nickname 컬럼을 추가해주세요.
    - nickname은 중복 가능합니다.
- 프론트엔드 개발자가 JWT에서 유저의 닉네임을 꺼내 화면에 보여주길 원하고 있어요.

>해결 방법 : 기존 비밀번호 변경 로직을 수정해서 새 비밀번호를 입력 받았으면 비밀번호를 변경하고, 새 닉네임을 입력 받았으면 닉네임을 변경하도록 함.
JWT 필터에서 유저의 닉네임을 꺼내 AuthUser에 담도록 변경하고 UserResponse에 닉네임 정보를 추가.

### **3. 코드 개선 퀴즈 - AOP의 이해**

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/83c75a39-3aba-4ba4-a792-7aefe4b07895/228d9688-ea86-4dec-876b-9752689ab44f/image.png)

<aside>
😱 AOP가 잘못 동작하고 있어요!

</aside>

- `UserAdminController` 클래스의 `changeUserRole()` 메소드가 실행 전 동작해야해요.
- `AdminAccessLoggingAspect` 클래스에 있는 AOP가 개발 의도에 맞도록 코드를 수정해주세요.

>해결 방법 : 해당 AOP의 @After 어노테이션을 @Before로 수정

### **4. 테스트 코드 퀴즈 - 컨트롤러 테스트의 이해**

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/83c75a39-3aba-4ba4-a792-7aefe4b07895/e03201f2-47b3-4afc-beb7-6c2fbab8bc2b/image.png)

- 테스트 패키지 `org.example.expert.domain.todo.controller`의
  `todo_단건_조회_시_todo가_존재하지_않아_예외가_발생한다()` 테스트가 실패하고 있어요.

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/83c75a39-3aba-4ba4-a792-7aefe4b07895/e7342cca-35d7-45ec-95bf-a7702a35bc4f/image.png)

- 테스트가 정상적으로 수행되어 통과할 수 있도록 테스트 코드를 수정해주세요.

>해결 방법 : 테스트 코드에서 잘못된 요청인데도 ok 값을 기대하고 있던 것을 원본 코드의 실행 결과에 맞게 BAD_REQUEST 오류 발생을 기대하도록 테스트 코드 수정.
이 과정에서, getTodo에서 todoId 파라미터 이름을 인식하지 못하는 오류 발생. @PathVariable("todoId") long todoId 로 이름을 수동으로 지정해 줌.
튜터님의 조언에 따르면, build 과정에서 오류가 발생한 것이며, 특히 윈도우 사용자에게 자주 발생한다고 함. 지금은 이에 대해 깊게 알 필요는 없다고 하심.

### **5. 코드 개선 퀴즈 -  JPA의 이해**

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/83c75a39-3aba-4ba4-a792-7aefe4b07895/21a01448-eb2a-4e85-8ca0-cbe285a76376/image.png)

<aside>
🚨 기획자의 긴급 요청이 왔어요!
아래의 요구사항에 맞춰 기획 요건에 대응할 수 있는 코드를 작성해주세요.

</aside>

- 할 일 검색 시 `weather` 조건으로도 검색할 수 있어야해요.
    - `weather` 조건은 있을 수도 있고, 없을 수도 있어요!
- 할 일 검색 시 수정일 기준으로 기간 검색이 가능해야해요.
    - 기간의 시작과 끝 조건은 있을 수도 있고, 없을 수도 있어요!
- JPQL을 사용하고, 쿼리 메소드명은 자유롭게 지정하되 너무 길지 않게 해주세요.

<aside>
💡 필요할 시, 서비스 단에서 if문을 사용해 여러 개의 쿼리(JPQL)를 사용하셔도 좋습니다.

</aside>

>해결 방법 : 날씨, 특정 시간 이후, 특정 시간 이전 세 가지의 조건 유무에 따른 쿼리 8가지를 작성해 조건에 맞게 if문으로 적절한 쿼리를 사용하도록 함.

### **6. JPA Cascade**

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/83c75a39-3aba-4ba4-a792-7aefe4b07895/2ae0fb49-2098-4425-92f8-f2c9d610b70e/image.png)

<aside>
🤔 앗❗ 실수로 코드를 지웠어요!

</aside>

- 할 일을 새로 저장할 시, 할 일을 생성한 유저는 담당자로 자동 등록되어야 합니다.
- JPA의 `cascade` 기능을 활용해 할 일을 생성한 유저가 담당자로 등록될 수 있게 해주세요.

>해결 방법 : 조건을 만족시키기 위해 Todo 엔티티의 managers 항목에 cascade remove, persist, merge 추가
이로써 Todo가 등록될 때 매니저가 같이 저장되며(persist), DB에 업데이트 될 때 같이 업데이트되며(merge), 삭제될 때 같이 삭제됩니다(remove).

### **7. N+1**

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/83c75a39-3aba-4ba4-a792-7aefe4b07895/2315b8db-7f83-4984-8599-8c1079151444/image.png)

- `CommentController` 클래스의 `getComments()` API를 호출할 때 N+1 문제가 발생하고 있어요. N+1 문제란, 데이터베이스 쿼리 성능 저하를 일으키는 대표적인 문제 중 하나로, 특히 연관된 엔티티를 조회할 때 발생해요.
- 해당 문제가 발생하지 않도록 코드를 수정해주세요.

>해결 방법 : CommentRepository의 findByTodoIdWithUser의 쿼리에 join fetch 적용.

### **8. QueryDSL**

![TodoService.getTodo 메소드](https://prod-files-secure.s3.us-west-2.amazonaws.com/83c75a39-3aba-4ba4-a792-7aefe4b07895/21df06d6-a203-4ca0-ab92-88d1e79aad11/image.png)

TodoService.getTodo 메소드

- JPQL로 작성된 `findByIdWithUser` 를 QueryDSL로 변경합니다.
- 7번과 마찬가지로 N+1 문제가 발생하지 않도록 유의해 주세요!

>해결 방법 : 커스텀 레포지토리를 만들고 그곳에 QueryDSL을 활용한 쿼리문을 만든 후 기존 레포지토리에 상속시킴.
이 과정에서 Q클래스가 자동으로 생성되지 않는 문제가 발생함.

QueryDSL의 QClass가 생성되지 않아서 해 본 것들.
1.  gradle에 의존성 추가
    // QueryDSL 추가
    implementation 'com.querydsl:querydsl-jpa:5.0.0'
    annotationProcessor 'com.querydsl:querydsl-apt:5.0.0:jpa'
    해결되지 않고 컴파일 오류 발생. 어느 시점에 적용하고 문제가 생겼는지 기억이 나지 않아 정상적으로 실행된 것이 확실했던 커밋의 build.gradle을 가져 와 덮어씌움. 다시 원점으로 돌아감.



2. 의존성 추가와 함께 설정 추가
   // queryDSL
   implementation 'com.querydsl:querydsl-jpa:5.0.0:jakarta'
   annotationProcessor "com.querydsl:querydsl-apt:${dependencyManagement.importedProperties['querydsl.version']}:jakarta"
   annotationProcessor "jakarta.annotation:jakarta.annotation-api"
   annotationProcessor "jakarta.persistence:jakarta.persistence-api"
   추가한 의존성



// Querydsl 설정부
def generated = 'src/main/generated'

// querydsl QClass 파일 생성 위치를 지정
tasks.withType(JavaCompile) {
options.getGeneratedSourceOutputDirectory().set(file(generated))
}

// java source set 에 querydsl QClass 위치 추가
sourceSets {
main.java.srcDirs += [ generated ]
}

// gradle clean 시에 QClass 디렉토리 삭제
clean {
delete file(generated)
}


추가한 설정



해결되지 않음. 오류는 발생하지 않음.

generated 패키지는 생겼지만 비어 있음.



3. 설정 - build tools - gradle에서 gradle-wrapper.properties 파일이 없다는 경고를 보고 해당 파일이 어떤 역할을 하는지 알아본 후, 다른 프로젝트에서 붙여 넣음.


`gradle-wrapper.properties` 파일은 Gradle Wrapper를 설정하는 데 사용되는 설정 파일입니다. Gradle Wrapper는 프로젝트에서 특정 버전의 Gradle을 사용하도록 보장하는 도구로, 이를 통해 프로젝트를 빌드하는 시스템에 Gradle이 설치되어 있지 않더라도, 동일한 버전의 Gradle을 다운받아 빌드할 수 있게 합니다.

### 각 항목의 역할 설명:
1. **distributionBase=GRADLE_USER_HOME**
    - 이 설정은 Gradle Wrapper가 다운로드한 Gradle 배포판을 저장할 기본 디렉터리 위치를 지정합니다. `GRADLE_USER_HOME`은 일반적으로 사용자의 Gradle 홈 디렉터리를 의미합니다.

2. **distributionPath=wrapper/dists**
    - 배포 파일이 저장될 경로를 지정합니다. 여기서는 `wrapper/dists` 디렉터리에 Gradle 배포 파일이 저장됩니다.

3. **distributionUrl=https\://services.gradle.org/distributions/gradle-8.10.1-bin.zip**
    - 프로젝트에서 사용할 Gradle의 버전을 지정합니다. 이 URL은 지정된 버전의 Gradle을 다운로드할 수 있는 위치를 나타냅니다. 이 예에서는 Gradle 8.10.1 버전의 바이너리 파일을 다운로드하도록 설정되어 있습니다.

4. **networkTimeout=10000**
    - 네트워크 요청의 타임아웃 시간을 밀리초 단위로 설정합니다. 10000 밀리초(10초) 동안 응답이 없으면 타임아웃이 발생합니다.

5. **validateDistributionUrl=true**
    - Gradle Wrapper가 `distributionUrl`에서 제공된 URL의 유효성을 검사하도록 설정합니다. URL이 올바르지 않으면 다운로드를 시도하지 않습니다.

6. **zipStoreBase=GRADLE_USER_HOME**
    - 압축 파일(`zip` 형식)의 기본 저장 위치를 지정합니다. `GRADLE_USER_HOME`은 위에서 언급한 것처럼 사용자의 Gradle 홈 디렉터리를 의미합니다.

7. **zipStorePath=wrapper/dists**
    - 다운로드된 Gradle 배포 파일이 저장될 경로입니다. 여기서는 `wrapper/dists`에 저장됩니다.

이 파일을 통해 Gradle 프로젝트를 어떤 버전으로, 어디에 다운로드 및 저장할지 설정하고, 그 과정을 관리합니다.


이를 보고 이전에 사용했던 아웃소싱 프로젝트의 gradle-wrapper.properties를 그대로 이식해도 문제 없겠다 판단, 복사하여 붙여 넣음.

(이 때, .\gradlew --refresh-dependencies 명령어로 의존성을 다시 다운로드 하기도 했음. 이게 문제 해결의 원인일 수도.)

이후 gradle을 한번 reload 하고 어플리케이션을 실행하니

문제가 해결되어 Q클래스가 정상적으로 생성된 것을 확인할 수 있었음.

### **9. Spring  Security**

<aside>
⚙️

Spring Security를 도입하기로 결정했어요!

</aside>

- 기존 `Filter`와 `Argument Resolver`를 사용하던 코드들을 Spring Security로 변경해주세요.
    - 접근 권한 및 유저 권한 기능은 그대로 유지해주세요.
    - 권한은 Spring Security의 기능을 사용해주세요.
- 토큰 기반 인증 방식은 유지할 거예요. JWT는 그대로 사용해주세요.

>해결 방법 : SecurityConfig 추가, JwtSecurityFilter 추가, JwtAuthenticationToken 추가 후 기존 코드를 사양에 맞게 수정.
