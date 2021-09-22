# BindingResult
- 스프링이 제공하는 검증 오류를 보관하는 객체
- `BindingResult`는 자동으로 `Model`에 포함되며 검증할 대상인 `@ModelAttribue` 파라미터의 바로 다음에 위치해야 한다. 
- `BindingResult`를 사용하면 `@ModelAttribute`에 데이터 바인딩 오류가 발생해도 오류 정보를 BindingResult에 담고, 컨트롤러가 정상적으로 호출된다.
  - BindingResult 없으면 400 오류 발생, 컨트롤러 호출 X, 오류 페이지로 이동

### BindingResult에 검증 오류를 적용하는 3가지 방법
1. `@ModelAttribute`의 객체에 타입 오류 등으로 바인딩 실패시 스프링이 `FieldError`를 생성해서 `BindingResult`에 넣어준다.
2. 직접 에러 객체를 생성해서 넣어준다.
3. `Validator`를 사용한다.

### BindingResult, Errors
```java
public interface BindingResult extends Errors {
  ...
}
```
- `BindingResult`는 인터페이스이고, `Errors` 인터페이스를 상속받는다.
- `Errors`는 단순히 오류 저장과 조회 기능 제공, `BindingResult`는 추가적인 기능들을 제공
- 컨트롤러 메서드 파라미터로 실제 넘어오는 타입은 `BeanPropertyBindingResult`인데 `BindingResult`와 `Errors`를 모두 구현하고 있기 떄문에 어떤 타입으로 사용하든 상관없다(관례상 `BindingResult`를 주로 사용)