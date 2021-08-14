package hello.exception.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
public class ServletExController {
    //서블릿은 Exception, response.sendError(HTTP 상태코드, 오류 메시지)로 예외 처리를 지원한다.

    /*
    웹 애플리케이션 예외 흐름
    WAS까지 전파 <- 필터 <- 서블릿 <- 인터셉터 <- 컨트롤러에서 예외 발생
     */
    @GetMapping("/error-ex")
    public void errorEx() {
        throw new RuntimeException("예외 발생!");
    }

    /*
    sendError 흐름
    WAS(sendError 호출 확인) <- 필터 <- 서블릿 <- 인터셉터 <- 컨트롤러(response.sendError())
     */
    @GetMapping("/error-404")
    public void error404(HttpServletResponse response) throws IOException {
        response.sendError(404, "404 오류!");
    }

    @GetMapping("/error-500")
    public void error500(HttpServletResponse response) throws IOException {
        response.sendError(500);
    }
}
