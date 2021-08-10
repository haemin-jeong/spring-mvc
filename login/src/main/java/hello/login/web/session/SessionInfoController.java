package hello.login.web.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
/*
세션의 종료 시점 : HttpSession은 사용자가 가장 최근에 서버에 요청한 시간(lastAccessedTime)을 기준으로 설정된 시간동안 유지된다.

세션 타임아웃 설정
- session.setMaxInactiveInterval(1800) : 초단위, 기본값 1800(30분)
- 글로벌 설정 : application.properties -> server.servlet.session.timeout=60 : 글로벌 설정은 분단위로만 설정할 수 있다.

세션 타임 아웃 발생
lastAccessedTime 이후로 timeout 시간이 지나면 WAS가 내부에서 해당 세션을 제거한다.

세션 사용시 주의점
- 세션은 메모리에 저장되기 때문에 세션에는 몇가지 간단하고 진짜 필요한 정보만 핏하게 보관해야한다.
- 세션 유지 시간은 기본이 30분이라는 것을 기준으로 고민하여 선택하면 된다.
- 세션 유지 시간을 너무 길게 가져가거나 보관하는 데이터의 양이 많을 경우 메모리 사용량이 늘어나 장애로 이어질 수 있다.
 */

@Slf4j
@RestController
public class SessionInfoController {

    @GetMapping("/session-info")
    public String sessionInfo(HttpServletRequest request) {

        HttpSession session = request.getSession();
        if (session == null) {
            return "세션이 없습니다.";
        }

        //세션 데이터 출력
        session.getAttributeNames().asIterator().forEachRemaining(name -> log.info("session name={}, value={}", name, session.getAttribute(name)));

        //JSESSOIONID의 값이다.
        log.info("sessionId={}", session.getId());

        //세션의 유효 시간(초 단위)
        log.info("maxInactiveInterval={}", session.getMaxInactiveInterval());

        //세션 생성 일시
        log.info("creationTime={}", new Date(session.getCreationTime()));

        //세션과 연결된 사용자가 최근에 서버에 접근한 시간
        log.info("lastAccessedTime={}", new Date(session.getLastAccessedTime()));

        //새로 생생도니 세션인지
        log.info("isNew={}", session.isNew());

        return "세션 출력";
    }
}
