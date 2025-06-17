package hse.studying.gateway.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Запрос для создания счёта или пополнения баланса")
public class AccountDepositRequest {
    @Schema(description = "ID пользователя", example = "1", required = true)
    private Long userId;

    @Schema(description = "Сумма для пополнения", example = "50.0", required = true)
    private Double amount;
}
