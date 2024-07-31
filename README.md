# cj-product

## 기술스택

- Java 21, Spring Boot, JPA, Redis(Embedded), h2(Embedded)

## 빌드 및 실행

  - IDE : IntelliJ IDEA
    - Run > Run Configuration > Active Profile : local


  - 실행 테스트를 위한 테스트 요청 정보는 /http/cj-product.http 파일을 참고하세요.


  - Swagger 문서
    - Annotation으로 작성
        - URL : `http://localhost:8080/swagger-ui/index.html`


  - Sample Data
    - `resources/data.sql` 파일에 샘플 데이터가 있습니다.
    - 샘플 데이터는 다음과 같습니다.
      
    | 상품ID | 상품명   | 상품제고 |
    |---|:---|---|
    | 40189146 | 햇반 | 210 |
    | 40199729 | 비비고 삼계탕 | 800 |
    | 40164991 | 비비고 왕교자 | 105 |
    | 40167542 | 비비고 포기배추 김치 | 10 |
    | 40164907 | 동치미 물냉명 | 4 |
    | 40184977 | 비비고 사골곰탕 | 300 |
    | 40199955 | 비비고 통새우 만두 | 200 |
    | 40164841 | 고메 중화짬뽕 | 652 |


