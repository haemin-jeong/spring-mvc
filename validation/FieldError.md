# FieldError
- FieldError의 rejectValue 파라미터에 사용자가 입력했던 값(거절된 값)을 전달하여 폼에 입력했던 데이터가 유지되도록 할 수 있다.

생성자 파라미터
```java
public FieldError(String objectName, String field, String defaultMessage)

public FieldError(String objectName, String field, @Nullable Object rejectedValue, boolean bindingFailure,
@Nullable String[] codes, @Nullable Object[] arguments, @Nullable String defaultMessage) 

```
- objectName : 오류가 발생한 객체 이름
- field : 오류 필드 명
- rejectValue : 사용자가 입력한 값(거절된 값)
- bindingFailure : 타입 오류와 같은 바인딩 오류인지
- codes : 메시지 코드
- argument : 메시지에 사용하는 인자
- defaultMessage : 디폴트 오류 메시지

`@ModelAttribute`에 바인딩되는 시점에 타입 오류와 같은 바인딩 오류가 발생하면 스프링은 `FieldError` 객체를 생성해서 `rejectValue`에 사용자가 입력한 값을 저장하고 해당 객체를 `BindingResult`에 저장해주기 때문에 검증 오류 발생시 사용자가 입력했던 값을 폼에 유지할 수 있다.

타임리프의 `th:field`는 정상적인 흐름에선 모델 객체의 값을 사용하고, 오류가 발생시 `FieldError`에 저장한 값을 사용하여 출력해준다.
