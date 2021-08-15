package hello.exception.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {

    /*
    오류가 발생하면 오류 페이지를 호출하기 위해 WAS 내부에서 호출이 다시 발생하는데, 이때 필터와 서블릿, 인터셉터도 다시 호출이된다.
    그런데 로그인 같은 경우를 생각해보면 이미 오류가 발생하기 전에 필터 또는 인터셉터에서 인증 체크를 완료했는데 똑같은 필터나 인터셉터가 한번 더 호출되는 것은 비효율적이다.
    서블릿의 필터는 이런 문제를 해결하기위해 클라이언트로부터온 정상 요청인지, 오류 페이지를 위한 내부 요청인지를 구분하기 위해 DispatchType이라는 추가 정보를 제공한다.

    DispatcherType dispatcherType = request.getDispatcherType();
    - REQUEST : 클라이언트 요청
    - ERROR : 오류 요청
    - FORWARD : MVC에서 서블릿에서 다른 서블릿이나 JSP를 호출할 때, RequestDispatcher.forward(request, response)
    - INCLUDE : 서블릿에서 다른 서블릿이나 JSP의 결과를 포함할 때, RequestDispatcher.include(request, response)
    - ASYNC : 서블릿 비동기 호출
     */

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        String uuid = UUID.randomUUID().toString();

        try {
            log.info("REQUEST [{}][{}][{}]", uuid, request.getDispatcherType(), response);
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("RESPONSE [{}][{}][{}]", uuid, request.getDispatcherType(), requestURI);
        }
    }

    @Override
    public void destroy() {
        log.info("log filter destroy");
    }
}
