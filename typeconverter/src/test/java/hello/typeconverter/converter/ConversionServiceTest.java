package hello.typeconverter.converter;

import hello.typeconverter.type.IpPort;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;

import static org.assertj.core.api.Assertions.*;

public class ConversionServiceTest {

    /*
    타입 컨버터를 하나하나 직접 찾아서 사용하는 것은 불편한다.
    스프링은 개별 컨버터를 모아두고 편리하게 사용할 수 있는 컨버전 서비스 기능을 제공한다.

    인터페이스 분리 원칙 - ISP(Interface Segregation Principal)
    DefaultConversionService는 두 인터페이스를 구현한다.
    - ConversionService : 컨버터 사용에 초점, 컨버팅이 가능한지 확인하는 기능(canConvert())과 컨버팅(convert())기능 제공
    - ConverterRegistry : 컨버터 등록에 초점, addConverter()
    이렇게 함으로써 컨버터를 사용하는 클라이언트는 ConversionService만 의존하면 된다. 컨버터 등록과 관리에 관한 내용을 몰라도 된다.

    스프링은 내부에서 ConversionService를 사용해서 @RequestParam과 같은 곳에서 타입을 변환한다.
     */
    @Test
    void conversionService() {
        //등록
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new StringToIntegerConverter());
        conversionService.addConverter(new IntegerToStringConverter());
        conversionService.addConverter(new IpPortToStringConverter());
        conversionService.addConverter(new StringToIpPortConverter());

        //사용
        assertThat(conversionService.convert("10", Integer.class)).isEqualTo(10);
        assertThat(conversionService.convert(10, String.class)).isEqualTo("10");

        assertThat(conversionService.convert("127.0.0.1:8080", IpPort.class)).isEqualTo(new IpPort("127.0.0.1", 8080));
        assertThat(conversionService.convert(new IpPort("127.0.0.1", 8080), String.class)).isEqualTo("127.0.0.1:8080");
    }
}
