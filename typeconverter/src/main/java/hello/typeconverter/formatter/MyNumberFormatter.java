package hello.typeconverter.formatter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.Formatter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/*
객체를 특정한 포멧에 맞추어 문자로 출력하거나 또는 그 반대의 역할을 하는 것에 특화된 기능이 Formatter이다.

Converter : 범용, 객체 -> 객체
Formatter : 문자에 특화, 문자 <-> 객체, Locale(현지화) 정보 활용 가능

참고 : AnnotationsFormatterFactory -> 필드의 타입이나 애노테이션 정보를 활용할 수 있는 Formatter
 */
@Slf4j
public class MyNumberFormatter implements Formatter<Number> {

    //문자를 객체로 변경
    @Override
    public Number parse(String text, Locale locale) throws ParseException {
        log.info("text={} locale={}", text, locale);
        //"1,000"처럼 중간의 쉼표를 적용하려면 자바가 기본으로 제공하는 NumberFormat 객체를 사용하면 된다.
        //Locale 정보를 활용해서 나라별로 다른 숫자 포멧을 만들어준다.
        NumberFormat format = NumberFormat.getInstance(locale);
        return format.parse(text);
    }

    //객체를 문자로 변경
    @Override
    public String print(Number object, Locale locale) {
        log.info("object={}, locale={}", object, locale);
        return NumberFormat.getInstance(locale).format(object);
    }
}
