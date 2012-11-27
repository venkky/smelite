package com.above.smlite.entity.providers;

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
import com.above.smlite.entities.Vendor;
import com.above.smlite.util.UniqueIDGenerator;

public class VendorProvider extends AbstractRESTProvider implements CoreEntityProvider, RESTful {
	
	VendorDAO _vendorDao = null;
	
	private final static Logger LOGGER = Logger.getLogger(VendorProvider.class .getName()); 
	
	public VendorProvider(EntityProviderManager entityProviderManager) {
		super(entityProviderManager);
		_vendorDao = new VendorDAO();
	}

	@Override
	public String getEntityPrefix() {
		return "vendor";
	}

	@Override
	public String createEntity(EntityReference ref, Object entity,
			Map<String, Object> params) {
		
		if(entity == null){
			return null;
		}
		
		String reference = null;
		Vendor vendor = (Vendor) entity;
		try {
			vendor.setId(UniqueIDGenerator.getID());
			reference = _vendorDao.create((Vendor) vendor);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		
		return reference;
	}

	@Override
	public Object getSampleEntity() {
		return new Vendor();
	}

	@Override
	public void updateEntity(EntityReference ref, Object entity,
			Map<String, Object> params) {
		try {
			_vendorDao.update(ref.getId(), (Vendor) entity);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	@Override
	public Object getEntity(EntityReference reference) {
		if (reference.getId() == null) {
            return new Vendor();
        }
		try {
			return _vendorDao.findByPrimaryKey(reference.getId());
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
        throw new IllegalArgumentException("Invalid id:" + reference.getId());
	}

	@Override
	public void deleteEntity(EntityReference ref, Map<String, Object> params) {
		try {
			_vendorDao.delete(ref.getId());
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	@Override
	public List<Vendor> getEntities(EntityReference ref, Search search) {
		List<Vendor> entities = null;
		
		try {
			entities = _vendorDao.findAll();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		
		return entities; 
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
		Vendor vendor = null;
		try {
			vendor = _vendorDao.findByPrimaryKey(id);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		return vendor != null;
	}

}
