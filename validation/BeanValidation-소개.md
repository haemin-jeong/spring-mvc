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


