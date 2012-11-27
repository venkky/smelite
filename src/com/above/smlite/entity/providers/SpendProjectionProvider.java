package com.above.smlite.entity.providers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.sakaiproject.entitybus.EntityReference;
import org.sakaiproject.entitybus.entityprovider.CoreEntityProvider;
import org.sakaiproject.entitybus.entityprovider.EntityProviderManager;
import org.sakaiproject.entitybus.entityprovider.capabilities.RESTful;
import org.sakaiproject.entitybus.entityprovider.extension.Formats;
import org.sakaiproject.entitybus.entityprovider.search.Search;

import com.above.smlite.dao.VendorDAO;
import com.above.smlite.entities.SpendProjection;

public class SpendProjectionProvider extends AbstractRESTProvider implements CoreEntityProvider, RESTful {

	VendorDAO _vendorDao = null;
	
	private final static Logger LOGGER = Logger.getLogger(SpendProjectionProvider.class.getName());
			
	public SpendProjectionProvider(EntityProviderManager entityProviderManager) {
		super(entityProviderManager);
		_vendorDao = new VendorDAO();
	}
	
	@Override
	public String getEntityPrefix() {
		return "spendprojection";
	}

	@Override
	public Object getEntity(EntityReference ref) {
		try {
			return _vendorDao.getSpendProjection();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		return null;
	}

	@Override
	public List<SpendProjection> getEntities(EntityReference ref, Search search) {
		List<SpendProjection> projections = new ArrayList<SpendProjection>();
		try {
			projections.add(_vendorDao.getSpendProjection());
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		return projections;
	}

	@Override
	public String createEntity(EntityReference ref, Object entity,
			Map<String, Object> params) {
		return null;
	}

	@Override
	public Object getSampleEntity() {
		return new SpendProjection();
	}

	@Override
	public void updateEntity(EntityReference ref, Object entity,
			Map<String, Object> params) {
		
	}

	@Override
	public void deleteEntity(EntityReference ref, Map<String, Object> params) {
		
	}

	@Override
	public String[] getHandledOutputFormats() {
		return new String[] { Formats.HTML, Formats.JSON, Formats.XML, Formats.FORM };
	}

	@Override
	public String[] getHandledInputFormats() {
		return new String[] { Formats.HTML, Formats.JSON, Formats.XML };
	}

	@Override
	public boolean entityExists(String id) {
		return true;
	}


}
