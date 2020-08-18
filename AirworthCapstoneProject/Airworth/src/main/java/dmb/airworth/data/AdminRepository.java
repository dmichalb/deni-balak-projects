/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmb.airworth.data;

import dmb.airworth.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author dmich
 */
public interface AdminRepository extends
        JpaRepository<Admin, String>{
    
}
