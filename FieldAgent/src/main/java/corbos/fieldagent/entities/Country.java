package corbos.fieldagent.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Country implements Serializable {

    @Id
    private String countryCode;
    
    @Column(nullable = false)
    private String name;

}
