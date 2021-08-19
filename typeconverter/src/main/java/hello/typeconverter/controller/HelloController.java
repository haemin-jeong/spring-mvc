package hello.typeconverter.controller;

import hello.typeconverter.type.IpPort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HelloController {

    //V1: HTTP 요청 파라미터는 모두 문자로 처리된다. 아래와 같이 변환 과정을 거처야 다른 타입으로 사용할 수 있다.
    @GetMapping("/hello-v1")
    public String helloV1(HttpServletRequest request) {
        String data = request.getParameter("data");
        Integer intValue = Integer.valueOf(data);
        System.out.println("intValue = " + intValue);
        return "ok";
    }

    /*
    V2: @RequestParam, @PathVariable, @ModelAttribute를 사용하면 타입을 변환하여 편리하게 받을 수 있는데 이것은 스프링이 중간에서 타입을 변환해주었기 때문이다.

    스프링이 해주는 변환 이외에 추가적인 타입 변환이 필요하면 Converter 인터페이스를 구현해서 등록하면 된다.
     */
    @GetMapping("/hello-v2")
    public String helloV2(@RequestParam Integer data) {
        System.out.println("data = " + data);
        return "ok";
    }

    //String -> IpPort 컨버터 테스트
    @GetMapping("/ip-port")
    public String ipPort(@RequestParam IpPort ipPort) {
        System.out.println("ipPort.getIp() = " + ipPort.getIp());
        System.out.println("ipPort.getPort() = " + ipPort.getPort());
        return "ok";
    }
}
