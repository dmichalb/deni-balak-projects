package corbos.fieldagent.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Agency implements Serializable {

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private int agencyId;
    
    @Column(nullable = false)
    private String name;

}
