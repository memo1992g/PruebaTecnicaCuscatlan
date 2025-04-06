package backend.apiscart.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usuarios",schema = "sch_purchase_order")
@Data
public class Usuarios {

    @Id
    private Long id;
    private String username;
    private String password;
}
