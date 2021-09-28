package hello.itemservice.web.validation;

import hello.itemservice.web.validation.form.ItemSaveForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
@Valid, @Validated는 HttpMessageConverter(@RequestBody)에도 적용할 수 있다.
(@ModelAttribute는 HTTP 요청 파라미터(쿼리스트링, Post form)를 다룰 때 사용, @ReqeustBody는 HTTP body의 데이터를 객체로 변환할 때 사용)

API의 경우 3가지 경우가 있다.
- 성공 요청
- 실패 요청 : JSON을 객체로 생성하는 것 자체가 실패. 객체를 생성하는데 실패했기때문에 컨트롤러 자체가 호출되지 않아 Validator도 호춛되지 않는다.
- 검증 오류 요청 : JSON을 객체로 생성하는것은 성공했으나 검증 실패

@ModelAttribute : 각각 필드 단위로 정교하게 바인딩, 특정 필드가 바인딩되지 않아도 나머지 필드는 정상 바인딩되며 Validator로 검증할 수 있다.
@RequestBody : HttpMessageConverter 단계에서 JSON 데이터를 객체로 변환하지 못하면 컨트롤러 호출과 Validator 적용이 불가능하며 예외 발생
 */
@Slf4j
@RestController
@RequestMapping("/validation/api/items")
public class ValidationItemApiController {

    @PostMapping("/add")
    public Object addItem(@Validated @RequestBody ItemSaveForm form, BindingResult bindingResult) {

        log.info("API 컨트롤러 호출");

        if (bindingResult.hasErrors()) {
            log.info("검증 오류 발생 errors={}", bindingResult);

            //예시기 때문에 오류 객체들을 바로 반환했지만, 실무에서는 별도로 API 스펙을 정의해서 그에 맞는 객체로 반환해야 한다.
            return bindingResult.getAllErrors();
        }

        log.info("성공 로직 실행");
        return form;
    }
}
