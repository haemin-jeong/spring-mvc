package hello.typeconverter.controller;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
/*
Formatter 인터페이스의 구현 클래스를 찾아보면 날짜나 시간 관련한 스프링이 기본으로 제공하는 수많은 포멧터가 있는 알 수 있다.
포멧터는 기본 형식이 지정되어 있기 때문에, 객체의 각 필드마다 다른 형식으로 포멧을 지정하는 것은 쉽지 않다.
스프링은 이러한 문제를 해결하기 위해 애노테이션 기반으로 원하는 형식을 지정해 사용할 수 있는 포멧터 두가지를 제공한다.
- @NumberFormat : 숫자 관련 형식 지정 포멧터, NumberFormatAnnotationFormatterFactory
- @DateTimeFormat : 날짜 관련 형식 지정 포멧터, Jsr310DateTimeFormatAnnotationFormatterFactory

AnnotationFormatterFactory : 필드의 타입이나 애노테이션 정보를 활용할 수 있는 포멧터
 */
@Controller
public class FormatterController {

    @GetMapping("/formatter/edit")
    public String formatterForm(Model model) {
        Form form = new Form();
        form.setNumber(10000);
        form.setLocalDateTime(LocalDateTime.now());

        model.addAttribute("form", form);
        return "formatter-form";
    }

    @PostMapping("/formatter/edit")
    public String formatterEdit(@ModelAttribute Form form) {
        return "formatter-view";
    }

    @Data
    static class Form {

        @NumberFormat(pattern = "###,###")
        private Integer number;

        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime localDateTime;
    }
}
