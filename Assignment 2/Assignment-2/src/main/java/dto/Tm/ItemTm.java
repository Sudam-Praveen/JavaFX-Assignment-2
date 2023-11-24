package dto.Tm;

import javafx.scene.control.Button;
import lombok.*;

import java.awt.*;
@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ItemTm {
    private String code;
    private String description;
    private double unitPrice;
    private int qty;
    private Button btn;

}
