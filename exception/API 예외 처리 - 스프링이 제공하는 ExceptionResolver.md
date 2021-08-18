스프링 부트가 기본으로 제공하는 ExceptionResolver는 HandlerExceptionResolverComposite에 다음과 같은 순서로 등록되어 있다.

1. ExceptionHandlerExceptionResovler 
2. ResponseStatusExceptionResolver :
3. DefaultHandlerExceptionResolver 

### ExceptionHandlerExceptionResolver

- `@ExceptionHandler`를 처리한다. API 예외처리는 대부분 이 기능으로 해결한다.

- HandlerExceptionResolver를 직접 사용하기는 복잡하다. (API 오류 응답시 response에 직접 데이터롤 넣는 경우, ModelAndView를 반환해야 하는 것 등) -> 이러한 불편함을 해결하기 위해 `@ExceptionHandler` 기능을 사용한다.

### ResponseStatusExceptionResolver

- `@ResponseStatus` 애노테이션이 달려있는 예외와 `ResponseStatusException` 예외를 처리한다.
  - @ResponseStatus : 예외 클래스에 직접 적용해야되기 때문에 라이브러리와 같은 내가 코드를 수정할 수 없는 곳에 사용할 수 없고, 동적으로 사용할 수 없다.
  - ResponseStatusException : 직접 예외를 던지기 때문에, @ResponseStatus를 사용할 수 없을 때 사용하면 된다.
- **HTTP 응답 코드를 변경하고, 메시지를 담는다.**
- `ResponseStatusExceptionResolver` 코드를 확인해보면 결국 `response.sendError(statusCode, resolvedReason)`를 호출하는 것을 확인할 수 있다. 즉, `sendError()`를 호출하기 때문에 WAS에서 오류 페이지(/error)를 내부 요청한다.

### DefaultHandlerExceptionResolver

- 스프링 내부 기본 예외를 처리한다.
- 예 : `TypeMismatchException` 는 파라미터 바인딩 시점에 타입이 맞지 않으면 발생한다. 해당 예외가 발생했을 때, 아무 처리를 하지 않으면 예외가 서블릿 컨테이너까지 올라가 500 오류가 발생하게 된다. 그런데 파라미터 바인딩 시점에 예외가 발생하는 것은 대부분 클라이언트에서 요청을 잘못 했기 때문이고, `DefaultHandlerExceptionResolver`가 예외를 잡아서 400오류로 변경해준다.
- `DefaultHandlerExceptionResolver` 코드를 확인해보면 결국 `response.sendError(HttpServletResponse.SC_BAD_REQUEST)`를 호출하는 것을 확인할 수 있다. 즉, `sendError()`를 호출하기 때문에 WAS에서 오류 페이지(/error)를 내부 요청한다.



