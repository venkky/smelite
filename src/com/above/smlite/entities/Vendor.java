package com.above.smlite.entities;

import java.io.Serializable;

import org.sakaiproject.entitybus.entityprovider.annotations.EntityDateCreated;
import org.sakaiproject.entitybus.entityprovider.annotations.EntityFieldRequired;
import org.sakaiproject.entitybus.entityprovider.annotations.EntityId;

public class Vendor implements Serializable {
		
	private static final long serialVersionUID = -1798725690250982609L;

	@EntityId 
	private String _id;
	
	@EntityFieldRequired
	private String _name = null;
    
	private boolean _isPOCreated = false;
	private String _poNumber = null;
	private String _poType = null;
	private String _poDetails = null;
	
	@EntityDateCreated protected long _dateCreated;

	public Vendor() {
		super();
	}
	
	public Vendor(String _id, String _name, boolean _isPOCreated, String _poNumber,
			String _poType, String _poDetails) {
		super();
		this._id = _id;
		this._name = _name;
		this._isPOCreated = _isPOCreated;
		this._poNumber = _poNumber;
		this._poType = _poType;
		this._poDetails = _poDetails;
		this._dateCreated = System.currentTimeMillis();
	}
	
	public String getId() {
		return _id;
	}
	public void setId(String id) {
		this._id = id;
	}
	public String getName() {
		return _name;
	}
	public void setName(String _name) {
		this._name = _name;
	}
	public boolean isPOCreated() {
		return _isPOCreated;
	}
	public void setPOCreated(boolean _isPOCreated) {
		this._isPOCreated = _isPOCreated;
	}
	public String getPoNumber() {
		return _poNumber;
	}
	public void setPoNumber(String _poNumber) {
		this._poNumber = _poNumber;
	}
	public String getPoType() {
		return _poType;
	}
	public void setPoType(String _poType) {
		this._poType = _poType;
	}
	public String getPoDetails() {
		return _poDetails;
	}
	public void setPoDetails(String _poDetails) {
		this._poDetails = _poDetails;
	}

	public long getDateCreated() {
		return _dateCreated;
	}

	public void setDateCreated(long dateCreated) {
		this._dateCreated = dateCreated;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_id == null) ? 0 : _id.hashCode());
		result = prime * result + (_isPOCreated ? 1231 : 1237);
		result = prime * result + ((_name == null) ? 0 : _name.hashCode());
		result = prime * result
				+ ((_poDetails == null) ? 0 : _poDetails.hashCode());
		result = prime * result
				+ ((_poNumber == null) ? 0 : _poNumber.hashCode());
		result = prime * result + ((_poType == null) ? 0 : _poType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vendor other = (Vendor) obj;
		if (_id == null) {
			if (other._id != null)
				return false;
		} else if (!_id.equals(other._id))
			return false;
		if (_isPOCreated != other._isPOCreated)
			return false;
		if (_name == null) {
			if (other._name != null)
				return false;
		} else if (!_name.equals(other._name))
			return false;
		if (_poDetails == null) {
			if (other._poDetails != null)
				return false;
		} else if (!_poDetails.equals(other._poDetails))
			return false;
		if (_poNumber == null) {
			if (other._poNumber != null)
				return false;
		} else if (!_poNumber.equals(other._poNumber))
			return false;
		if (_poType == null) {
			if (other._poType != null)
				return false;
		} else if (!_poType.equals(other._poType))
			return false;
		return true;
	}
	
}
