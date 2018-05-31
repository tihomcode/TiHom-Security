package com.tihom.security.rbac.repository;


import com.tihom.security.rbac.domain.Admin;
import org.springframework.stereotype.Repository;

/**
 * @author TiHom
 *
 */
@Repository
public interface AdminRepository extends ImoocRepository<Admin> {

	Admin findByUsername(String username);

}
