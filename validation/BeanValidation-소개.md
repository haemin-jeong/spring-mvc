# BeanValidation

- BeanValidation을 사용하면 애노테이션으로 검증 로직을 편리하게 적용할 수 있다.
- BeanValidation은 기술 표준으로 검증 애노테이션과 여러 인터페이스의 모음이며, 대표적인 구현체로 HibernateValidator가 있다.

```java
@Data
public class Item {

    private Long id;

    @NotBlank //빈값 + 공백만 있는 경우를 허용하지 않는다.
    private String itemName;

    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;

    @NotNull
    @Max(9999)
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}

```

HibernateValidator 참고
- 공식 메뉴얼 : https://docs.jboss.org/hibernate/validator/6.2/reference/en-US/html_single/
- 검증 애노테이션 모음 :  https://docs.jboss.org/hibernate/validator/6.2/reference/en-US/ html_single/#validator-defineconstraints-spec

## 스프링과 통합

`spring-boot-starter-validation` 라이브러리를 의존성에 추가하면 스프링부트는 자동으로 `LocalValidatorFactoryBean`을 글로벌 `Validator`로 등록한다. 

`LocalValidatorFactoryBean`은 `@NotNull`과 같은 검증 애노테이션을 보고 검증을 수행하여 오류가 발생하면 `FieldError`, `ObjectError`를 생성해서 `BindingResult`에 담아준다. 그렇기 때문에 검증 대상 객체에 `@Valid` 또는 `@Validated`만 적용해주면 된다.

**바인딩에 성공한 필드만 Bean Validation이 적용된다.**

`@ModelAttribute` 적용 객체 각각의 필드에 타입 변환을 시도하고, 성공하면 Bean Validation을 적용하고, 실패하면 Bean Validation을 적용하지 않고 `typeMismatch` 에러로 `FieldError`를 추가한다.

글로벌 `Validator`를 직정 등록하면 스프링 부트는 Bean Validator를 글로벌 Validator로 등록하지 않는다. 주의하자.
```java
@SpringBootApplication
public class ItemServiceApplication implements WebMvcConfigurer {
    
    // 글로벌 검증기 추가
    @Override
    public Validator getValidator() {
        return new ItemValidator();
    }
    
    // ...
}          
```



