package hello.itemservice.domain.item;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.ScriptAssert;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
/*
@ScriptAssert로 오브젝트 오류 처리
- 생성되는 메시지 코드 : ScriptAssert.item, ScriptAssert
- 실제 사용시 제약이 많고 복잡. 실무에서는 객체의 범위를 넘어서는 검증 기능도 종종 필요한데 이 경우 대응이 어렵다.
- 결론 : 오브젝트 오류는 자바 코드로 작성하는 것을 권장
 */
//@ScriptAssert(lang = "javascript", script = "_this.price * _this.quantity >= 10000")
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
