/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.airworth.data;

import dmb.airworth.entities.Maintainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author dmich
 */
@Repository
public interface MaintainerRepository extends
        JpaRepository<Maintainer, String>{
    
}
