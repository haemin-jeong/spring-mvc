package hello.exception.exhandler.advice;

import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import hello.exception.exhandler.ViewException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
/*
@ControllerAdvice
- 대상으로 지정한 여러 컨트롤러에 @ExceptionHandler, @InitBinder 기능을 부여해주는 역할을 한다.
- 대상을 지정하지 않으면 글로벌 적용
- @RestControllerAdvice = @ControllerAdvice + @ResponseBody

대상 컨트롤러 지정 방법
- 특정 애노테이션이 있는 컨트롤러에 적용 : @ControllerAdvice(annotations = RestController.class)
- 특정 패키지에 적용 : @ControllerAdvice("org.example.controller")
- 특정 클래스에 적용 : @ControllerAdvice(assignableTypes = {ControllerInterface.class, AbstractController.class})
 */
@Slf4j
@RestControllerAdvice()
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandle(IllegalArgumentException e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    //ResponseEntity를 반환하면 HTTP 응답 코드를 동적으로 변경 가능
    @ExceptionHandler //예외를 지정하지 않으면 메서드 파라미터의 예외를 사용
    public ResponseEntity<ErrorResult> userExHandle(UserException e) {
        log.error("[exceptionHandler] ex", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandle(Exception e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("EX", "내부 오류");
    }

    //ModelAndView를 반환하여 오류 HTML을 응답하는데 사용할 수도 있다.
    @ExceptionHandler(ViewException.class)
    public ModelAndView ex(ViewException e) {
        log.info("exception e", e);
        return new ModelAndView("error");
    }
}
