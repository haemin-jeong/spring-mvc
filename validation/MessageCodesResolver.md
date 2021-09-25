# MessageCodesResolver

- MessageCodesResolver는 검증 오류 코드로 메시지 코드들을 생성해준다.
- MessageCodesResolver는 인터페이스, 기본 구현체는 DefaultMessageCodesResolver 이다.
- ObjectError, FieldError의 codes 인자로 사용된다.

```java
public class MessageCodesResolverTest {

    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    @Test
    void messageCodesResolverObject() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");
        assertThat(messageCodes).containsExactly("required.item", "required");
    }

    @Test
    void messageCodesResolverField() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item", "itemName", String.class);
        assertThat(messageCodes).containsExactly("required.item.itemName", "required.itemName", "required.java.lang.String", "required");
    }
}
```

## DefaultMessageCodesResolver 기본 메시지 생성 규칙
- 숫자 순서대로 메시지를 `MessageSource`에서 찾는다(구체적 -> 덜구체적)
- 다음과 같은 규칙을 사용하여 메시지를 생성하여, 소스코드를 건드리지 않고 원하는 메시지를 단계별로 설정할 수 있디. 

### 객체 오류
1. 오류코드 + "." + 객체명 (예: required.item)
2. 오류코드 (예: required)

### 필드 오류
1. 오류코드 + "." + 객체명 + "." + 필드명 (예: required.item.itemName)
2. 오류코드 + "." + 필드명 (예: required.itemName)
3. 오류코드 + "." + 필드 타입 (예: required.java.lang.Integer)
4. 오류코드 (예: required)

## 동작 원리
1. `rejectValue()`, `reject()` 호출
2. 내부에서 `MessgaeCodesResolver` 를 사용해서 메시지 코드들을 생성하고 이를 `FieldError`, `ObjectError`의 생성자의 인자로 전달하여 보관
3. 타임리프 화면 렌더링시 `th:errors`가 실행되고 오류가 있다면 오류 메시지들을 구체적인 것부터 덜 구체적인 것까지 순서대로 탐색하며 메시지를 찾는데, 만약 메시지를 찾지 못한다면 디폴트 메시지를 출력

## 스프링이 만들어준 오류 메시지 처리
- 주로 타입 정보가 맞지 않거나 하는 경우 스프링이 직접 겅증 오류를 추가해준다.
- 개발자는 스프링이 생성해주는 오류코드를 보고 메시지 생성 규칙에 따라 단계별로 메시지를 작성하면된다.

예를 들어 타입 오류가 발생하면 스프링은 `typeMisMatch`라는 오류 코드를 사용하여 `MessageCodesResolver`를 사용하여 메시지 코드를 생성한다.
개발자는 예를 들어 다음과 같이 앞서 살펴본 메시지 생성 규칙에 따라 오류 메시지를 추가해주면 된다.
```properties
typeMismatch.java.lang.Integer=숫자를 입력해주세요.
typeMismatch=타입 오류입니다.
```
만약 개발자가 메시지를 따로 만들어주지 않으면 다음과 같은 기본 메시지가 출력된다.
```text
Failed to convert property value of type java.lang.String to required type java.lang.Integer for property price; nested exception is java.lang.NumberFormatException: For input string: "ㅁ"
```


