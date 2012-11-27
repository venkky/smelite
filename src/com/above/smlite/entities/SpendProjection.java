package com.above.smlite.entities;

import org.sakaiproject.entitybus.entityprovider.annotations.EntityId;

public class SpendProjection {
	
	@EntityId
	private String _id="spend";
	
	private int _currentMonthExpenditure = 0;

	public int getCurrentMonthExpenditure() {
		return _currentMonthExpenditure;
	}

	public void setCurrentMonthExpenditure(int _currentMonthExpenditure) {
		this._currentMonthExpenditure = _currentMonthExpenditure;
	}
	
	public int getQuerterlyProjection() {
		return _currentMonthExpenditure * 3;
	}

	public int getYearlyProjection() {
		return _currentMonthExpenditure * 12;
	}
	
	private String getId(){
		return _id;
	}
}
