package com.tihom.security.rbac.repository.spec;


import com.tihom.security.rbac.domain.Admin;
import com.tihom.security.rbac.dto.AdminCondition;
import com.tihom.security.rbac.repository.support.ImoocSpecification;
import com.tihom.security.rbac.repository.support.QueryWraper;

/**
 * @author TiHom
 *
 */
public class AdminSpec extends ImoocSpecification<Admin, AdminCondition> {

	public AdminSpec(AdminCondition condition) {
		super(condition);
	}

	@Override
	protected void addCondition(QueryWraper<Admin> queryWraper) {
		addLikeCondition(queryWraper, "username");
	}

}
