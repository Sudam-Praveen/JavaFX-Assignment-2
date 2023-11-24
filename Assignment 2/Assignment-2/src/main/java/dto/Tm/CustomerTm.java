package dto.Tm;

import javafx.scene.control.Button;
import lombok.*;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerTm {
    private String id;
    private String name;
    private String address;
    private double salary;
    private Button btn;
}
