package dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ItemDto {
    private String code;
    private String description;
    private double unitPrice;
    private int qty;
}
