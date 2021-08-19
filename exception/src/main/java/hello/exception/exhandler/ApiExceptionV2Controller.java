package hello.exception.exhandler;

import hello.exception.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/*
@ExceptionHandler 예외 처리 방법
- @ExceptionHandler 애노테이션을 선언하고, 해당 컨트롤러에서 처리하고 싶은 예외를 지정해주면 된다.
- 애노테이션에서 예외를 지정해주지않으면 메서드의 파라미터의 예외가 처리된다.

실행 흐름
1. 컨트롤러 호출 결과 예외가 던져짐
2. ExceptionResolver중 가장 우선 순위가 높은 ExceptionHandlerExceptionResolver 실행
3. ExceptionHandlerExceptionResolver는 해당 컨트롤러에 예외를 처리할 수 있는 @ExceptionHandler가 붙은 메서드가 있는지 확인하고 있으면 실행
 */
@Slf4j
@RestController
public class ApiExceptionV2Controller {

    @GetMapping("/api2/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {

        if (id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자");
        }

        if (id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력 값");
        }

        if (id.equals("user-ex")) {
            throw new UserException("사용자 오류");
        }

        return new MemberDto(id, "hello " + id);
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String memberId;
        private String name;
    }
}
