## Showerhead
물 대신 돈을 뿌리는 샤워기

![샤워기](https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQN269Xia1G5GMBaUPFn90gIqwC5NvkseNFlw&usqp=CAU)

### Diagram
![diagram](./entity_diagram.png)

### Tech Stack

- Kotlin
- MySQL
- Redis
- Spring Boot
- Spring Data JPA
- JUnit5 + TestContainers

### Strategy

- 핵심 컴포넌트에 대한 단위 테스트를, 전체 서비스에 대한 통합 테스트를 진행한다.
    - 핵심 컴포넌트에는 **도메인 클래스** 와 **서비스 클래스** 를 포함한다. 
    - Presentation layer 이전의 모든 컴포넌트에 대해 테스트한다. 
- *10분간만 뿌리기 요청이 유효하다* 는 요구사항에 착안하여, 뿌리기 요청을 캐싱한다. 
    - 실제 RDBMS에서의 `SELECT` 이전에 유효하지 않은 요청은 튕겨낸다. 
    - 같은 뿌리기 요청에 대해 수금한 이력이 있는 사용자가 요청할 경우 캐싱하여, 조회 이전에 튕겨낸다. 
- 고유한 token을 만들기 위해 `UUID` 를 사용한다. 
