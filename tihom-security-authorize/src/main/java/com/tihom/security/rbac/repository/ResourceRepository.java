package com.tihom.security.rbac.repository;


import com.tihom.security.rbac.domain.Resource;
import org.springframework.stereotype.Repository;

/**
 * @author TiHom
 *
 */
@Repository
public interface ResourceRepository extends ImoocRepository<Resource> {

	Resource findByName(String name);

}
