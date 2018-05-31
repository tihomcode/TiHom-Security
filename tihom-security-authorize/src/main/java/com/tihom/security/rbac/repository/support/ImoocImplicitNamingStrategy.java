package com.tihom.security.rbac.repository.support;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.spi.MetadataBuildingContext;

/**
 * @author TiHom
 *
 */
public class ImoocImplicitNamingStrategy extends ImplicitNamingStrategyJpaCompliantImpl {


	@Override
	protected Identifier toIdentifier(String stringForm, MetadataBuildingContext buildingContext) {
		return super.toIdentifier("imooc_"+stringForm.toLowerCase(), buildingContext);
	}

}
