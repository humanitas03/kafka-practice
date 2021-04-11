### Kafka Consumer Multi Channel binding 테스트

* Concept
    * multi.mytopc1 ~ 3에 consumer 1개가 바인딩이 된다.
    * 바인딩에 필요한 Spring cloud functional 빈은 아래와 같이 설정한다.
        ```java
       @Bean
          public Function<Tuple3<Flux<List<Person>>, Flux<List<Person>>, Flux<List<Person>>>, Flux<List<Person>>> multiChannelConsumer() {
              //상세 처리 생략
              return Flux<List<Person>> res; //Flux 래핑한 컬렉션 리턴
          }
       ```
      
* kafka 띄우기(docker)
>  cd docker
>  docker-compose up -d

* 테스트용 호출 서버 띄우기(kafka-multi-channel-practice-rest-tester)
    * kafka-multi-channel-practice-rest-tester
    * POST http://localhost:12000/post
    * 요청으로 들어온 Person의 userName에 "WJ" 문자열이 포함된 경우 Error를 리턴한다.
    * 에러 메시지 예시
    ``json
    {
      "errorMessage": "The username contains exception String('WJ') [ 9ERWJ ]",
      "errorCode": "5002"
    }
    ``

* DB는 H2 파일 DB 사용
    * Reference : https://elfinlas.github.io/2019/06/22/spring-boot-use-h2/
    

* intelliJ에서 database 확인