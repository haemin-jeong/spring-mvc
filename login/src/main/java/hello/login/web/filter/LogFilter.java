package hello.login.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

/*
필터의 흐름
- 정상 흐름 : HTTP 요청 -> WAS -> 필터 -> 서블릿 -> 컨트롤러
- 적절하지 않은 요청 흐름 : HTTP 요청 -> WAS -> 필터(서블릿 호출X)

필터 체인
- HTTP 요청 -> WAS -> 필터1 -> 필터2 -> 필터3 -> 서블릿 -> 컨트롤러
 */

@Slf4j
public class LogFilter implements Filter {

    //필터 초기화 메서드, 서블릿 컨테이너가 생성될 떄 호출
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter init");
    }

    //HTTP 요청이 올 때마다 호출, 필터의 로직을 구현하는 곳
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        String uuid = UUID.randomUUID().toString();

        try {
            log.info("REQUEST [{}][{}]", uuid, requestURI);

            //다음 필터가 있으면 필터를 호출하고, 필터가 없으면 서블릿을 호출
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("RESPONSE [{}][{}]", uuid, requestURI);
        }
    }

    //필터 종료 메서드, 서블릿 컨테이너가 종료될 때 호출
    @Override
    public void destroy() {
        log.info("log filter destroy");
    }
}
