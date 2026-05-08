package com.example.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * 従業員の扶養人数を更新する Form.
 *
 * @author shunsei
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDependentsCountUpdateForm {
    /** 従業員ID */
    @NotNull()
    private Integer id;
    
    /** 扶養人数 */
    @NotNull(message = "扶養人数を入力してください")
    @Min(value = 0, message = "0以上の整数を入力してください")
    private Integer dependentsCount;
}
