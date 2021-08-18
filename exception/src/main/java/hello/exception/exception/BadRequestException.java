package hello.exception.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/*
BadRequestException 예외가 컨트롤러 밖으로 던져지면 ResponseStatusExceptionResolver가
ResponseStatus  애노테이션을 확인하고 상태코드를 변경하고 메시지도 담는다.
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "잘못된 요청 오류")
//@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "error.bad") -> reason을 메시지소스에서 찾는 기능도 제공
public class BadRequestException extends RuntimeException {
}
