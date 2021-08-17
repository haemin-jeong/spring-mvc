package hello.exception.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
스프링 MVC의 HandlerExceptionResolver(줄여서 ExceptionResolver)를 사용하여 컨트롤러(핸들러) 밖으로 예외가 던져진 경우 WAS로 전달되기 전에 잡아서 해당 예외를 해결하고, 동작을 새로 정의할 수 있다.
참고 : ExceptionResolver로 예외를 해결해도 postHandle()은 호출되지 않는다.
 */
@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

    /**
     * IllegalArgumentException이 발생하면 HTTP 상태코드 400으로 처리
     *
     * @param request
     * @param response
     * @param handler : 핸들러(컨트롤러) 정보
     * @param ex : 핸들러(컨트롤러)에서 발생한 예외
     * @return - 빈 ModelAndView 반환 -> 뷰를 렌더링 하지않고, 정상 흐름으로 서블릿이 리턴된다.
     *         - ModelAndView 지정 -> View, Model 등의 정보를 지정해서 반환하면 뷰를 렌더링한다.
     *         - null -> 다음 ExceptionResolver를 찾아서 실행. 만약 처리할 수 있는 ExceptionResolver가 없으면 예외를 처리하지 못하고 서블릿 밖으로 던진다.
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        try {
            if (ex instanceof IllegalArgumentException) {
                log.info("IllegalArgumentException resolver to 400");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
                return new ModelAndView();
            }
        } catch (IOException e) {
            log.error("resolver ex", e);
        }

        return null;
    }
}
