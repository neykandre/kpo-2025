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
@Schema(description = "Запрос на создание заказа")
public class OrderRequest {
    @Schema(description = "ID пользователя", example = "1", required = true)
    private Long userId;

    @Schema(description = "Сумма заказа", example = "100.50", required = true)
    private Double amount;

    @Schema(description = "Описание заказа", example = "Покупка кроссовок")
    private String description;
}