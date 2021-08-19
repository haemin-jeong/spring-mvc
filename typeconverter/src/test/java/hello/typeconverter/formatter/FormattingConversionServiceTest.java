package hello.typeconverter.formatter;

import hello.typeconverter.converter.IpPortToStringConverter;
import hello.typeconverter.converter.StringToIpPortConverter;
import hello.typeconverter.type.IpPort;
import org.junit.jupiter.api.Test;
import org.springframework.format.support.DefaultFormattingConversionService;

import static org.assertj.core.api.Assertions.assertThat;

public class FormattingConversionServiceTest {

    @Test
    void formattingConversionService() {

        /*
        FormattingConversionService : 포멧터를 지원하는 컨버전 서비스, 내부에서 어댑터 패턴을 사용해 Formatter가 Converter처럼 동작하도록 지원한다.
        DefaultFormattingConversionService : FormattingConversionService에 기본적인 통화, 숫자 관련 및 기본 포멧터를 추가해서 제공

        스프링부트는 DefaultFormattingConversionService를 상속 받은 WebConversionService를 내부에서 사용한다.
         */
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();

        //컨버터 등록
        conversionService.addConverter(new StringToIpPortConverter());
        conversionService.addConverter(new IpPortToStringConverter());

        //포멧터 등록
        conversionService.addFormatter(new MyNumberFormatter());

        //컨버터 사용
        IpPort ipPort = conversionService.convert("127.0.0.1:8080", IpPort.class);
        assertThat(ipPort).isEqualTo(new IpPort("127.0.0.1", 8080));

        //포멧터 사용
        assertThat(conversionService.convert(1000, String.class)).isEqualTo("1,000");
        assertThat(conversionService.convert("1,000", Long.class)).isEqualTo(1000L);
    }
}
