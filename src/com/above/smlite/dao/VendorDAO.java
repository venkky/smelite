package com.above.smlite.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.above.smlite.entities.SpendProjection;
import com.above.smlite.entities.Vendor;

public class VendorDAO {
	
	private final static String INSERT = "INSERT INTO " +
			"Vendor(id, name, pocreated,podetails, ponumber,potype)  " +
			"VALUES(?,?,?,?,?,?)";
	
	private final static String UPDATE = "UPDATE Vendor " +
			"SET name = ?, pocreated = ?,podetails = ?, ponumber = ?, potype = ?  " +
			"WHERE id = ?";
	
	private final static String SELECT = "SELECT " +
			"id, name, pocreated,podetails, ponumber,potype, datecreated  " +
			"FROM Vendor";
	
	private final static String DELETE = "DELETE FROM Vendor WHERE id = ?";
	
	private final static String SELECT_BY_PRIMARY_KEY = SELECT + " WHERE id = ?";
	
	private final static String SELECT_MONTHLY_EXPENDITURE = "{? = CALL getCurrentMonthExpenditure()}";
	
	public String create(Vendor vendor) throws Exception {
		
		Connection conn = DBUtil.getConnection();
		PreparedStatement pStmt = conn.prepareStatement(INSERT);
		
		try {
			
			pStmt.setString(1, vendor.getId());
			pStmt.setString(2, vendor.getName());
			pStmt.setBoolean(3, vendor.isPOCreated());
			pStmt.setString(4, vendor.getPoDetails());
			pStmt.setString(5, vendor.getPoNumber());
			pStmt.setString(6, vendor.getPoType());
			
			pStmt.executeUpdate();
		} finally {
			pStmt.close();
			conn.close();
		}
		
		return vendor.getId();
	}
	
	public List<Vendor> findAll() throws Exception {

		List<Vendor> vendors = new ArrayList<Vendor>();
		
		Connection conn = DBUtil.getConnection();
		PreparedStatement pStmt = conn.prepareStatement(SELECT);
		
		try {
			ResultSet rs = pStmt.executeQuery();

			while(rs.next()){
				vendors.add(getVendor(rs));
			}
			
		} finally {
			pStmt.close();
			conn.close();
		}
		
		return vendors;
	}

	public Vendor findByPrimaryKey(String id) throws Exception {
		
		Connection conn = DBUtil.getConnection();
		PreparedStatement pStmt = conn.prepareStatement(SELECT_BY_PRIMARY_KEY);
		
		try {
			pStmt.setString(1, id);
			ResultSet rs = pStmt.executeQuery();
			if(rs.next()){
				return getVendor(rs);
			}
		} finally {
			pStmt.close();
			conn.close();
		}
		return null;
	}
	
	public SpendProjection getSpendProjection() throws Exception {
		
		Connection conn = DBUtil.getConnection();
		CallableStatement pStmt = conn.prepareCall(SELECT_MONTHLY_EXPENDITURE);
		pStmt.registerOutParameter(1, Types.INTEGER);
		pStmt.execute();
		
		SpendProjection spendProjection  = new SpendProjection();
		spendProjection.setCurrentMonthExpenditure(pStmt.getInt(1));
		
		return spendProjection;
	}
	
	public void delete(String id) throws Exception {
		Connection conn = DBUtil.getConnection();
		PreparedStatement pStmt = conn.prepareStatement(DELETE);
		
		try {
			pStmt.setString(1, id);
			pStmt.executeUpdate();
		} finally {
			pStmt.close();
			conn.close();
		}
	}
	private Vendor getVendor(ResultSet rs) throws Exception {
		Vendor vendor = new Vendor();
		
		vendor.setId(rs.getString("id"));
		vendor.setName(rs.getString("name"));
		vendor.setPOCreated(rs.getBoolean("pocreated"));
		vendor.setPoDetails(rs.getString("podetails"));
		vendor.setPoNumber(rs.getString("ponumber"));
		vendor.setPoType(rs.getString("potype"));
		vendor.setDateCreated(rs.getTimestamp("datecreated").getTime());
		
		return vendor;
	}

	public void update(String id, Vendor vendor) throws Exception {
		Connection conn = DBUtil.getConnection();
		PreparedStatement pStmt = conn.prepareStatement(UPDATE);
		
		try {
			pStmt.setString(1, vendor.getName());
			pStmt.setBoolean(2, vendor.isPOCreated());
			pStmt.setString(3, vendor.getPoDetails());
			pStmt.setString(4, vendor.getPoNumber());
			pStmt.setString(5, vendor.getPoType());
			pStmt.setString(6, vendor.getId());
			
			pStmt.executeUpdate();
		} finally {
			pStmt.close();
			conn.close();
		}
	}
}
