package com.above.smlite.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.above.smlite.dao.DBUtil;
import com.above.smlite.dao.VendorDAO;
import com.above.smlite.entities.SpendProjection;
import com.above.smlite.entities.Vendor;
import com.above.smlite.util.UniqueIDGenerator;

public class VendorDAOTest extends TestCase {

	
	private Connection conn = null;
	private VendorDAO _vendorDao = null;
	
	private static final Vendor testVendor1 = new Vendor(UniqueIDGenerator.getID(), "TestVendor1", true, "PO001", "Staff Augmentation", "This is a test PO");
	private static final Vendor testVendor2 = new Vendor(UniqueIDGenerator.getID(), "TestVendor2", false, null, null, null);
	
	public void setUp() throws Exception {
		if(conn == null){
			conn = DBUtil.getConnection();
		}
		if(_vendorDao == null){
			_vendorDao = new VendorDAO();
		}
		cleanVendorTable();
	}

	public void tearDown() throws Exception {
		conn.close();
		conn = null;
		_vendorDao = null;
	}

	public void testCreate() throws Exception {
		
		List<Vendor> vendors = _vendorDao.findAll();
		assertEquals(0, vendors.size());
		
		String vendor1 = _vendorDao.create(testVendor1);
		assertNotNull(vendor1);
		
		String vendor2 = _vendorDao.create(testVendor2);
		assertNotNull(vendor2);
		
		testVendor1.setId(vendor1);
		testVendor2.setId(vendor2);
		
		vendors = _vendorDao.findAll();
		assertEquals(2, vendors.size());
		
		Vendor createdVendor1 = _vendorDao.findByPrimaryKey(vendor1);
		Vendor createdVendor2 = _vendorDao.findByPrimaryKey(vendor2);
		
		assertNotNull(createdVendor1);
		assertNotNull(createdVendor2);
		
		compareVendors(createdVendor1, testVendor1);
		compareVendors(createdVendor2, testVendor2);
	}

	public void testFindAll() throws Exception {
		
		List<Vendor> vendors = _vendorDao.findAll();
		assertEquals(0, vendors.size());
		
		_vendorDao.create(testVendor1);
		_vendorDao.create(testVendor2);
		
		vendors = _vendorDao.findAll();
		assertEquals(2, vendors.size());
		
		Map<String, Vendor> testVendors = new HashMap<String, Vendor>();
		testVendors.put(testVendor1.getId(), testVendor1);
		testVendors.put(testVendor2.getId(), testVendor2);
		
		for (Iterator<Vendor> iterator = vendors.iterator(); iterator.hasNext();) {
			Vendor vendor = iterator.next();
			
			assertEquals(true, testVendors.containsKey(vendor.getId()));
			
			compareVendors(vendor, testVendors.get(vendor.getId()));
		}
	}

	public void testFindByPrimaryKey() throws Exception {
		
		String vendor1 = _vendorDao.create(testVendor1);
		String vendor2 = _vendorDao.create(testVendor2);
		
		Vendor vendorOne = _vendorDao.findByPrimaryKey(vendor1);
		
		assertNotNull(vendorOne);
		compareVendors(vendorOne, testVendor1);
		
		Vendor vendorTwo = _vendorDao.findByPrimaryKey(vendor2);
		
		assertNotNull(vendorTwo);
		compareVendors(vendorTwo, testVendor2);
		
	}

	public void testDelete() throws Exception {
		String vendor1 = _vendorDao.create(testVendor1);
		String vendor2 = _vendorDao.create(testVendor2);
		
		List<Vendor> vendors = _vendorDao.findAll();
		assertEquals(2, vendors.size());
		
		_vendorDao.delete(vendor1);
		vendors = _vendorDao.findAll();
		assertEquals(1, vendors.size());
		
		_vendorDao.delete(vendor2);
		vendors = _vendorDao.findAll();
		assertEquals(0, vendors.size());
	}

	public void testUpdate() throws Exception {
		String vendor2 = _vendorDao.create(testVendor2);
		
		Vendor toUpdate = new Vendor();
		toUpdate.setId(testVendor2.getId());
		toUpdate.setName("UPDATED_NAME");
		toUpdate.setPOCreated(true);
		toUpdate.setPoNumber("UPPO01");
		toUpdate.setPoType("Outbound Projects");
		toUpdate.setPoDetails("UPDATED_PO_DETAILS");
		
		_vendorDao.update(vendor2, toUpdate);
		
		Vendor updatedVendor = _vendorDao.findByPrimaryKey(vendor2);
		
		compareVendors(toUpdate, updatedVendor);
	}
	
	public void testSpendProjection() throws Exception {
		String vendor2 = _vendorDao.create(testVendor1);
		SpendProjection _spendProjection = _vendorDao.getSpendProjection();
		
		assertNotNull(_spendProjection);
		assertEquals(100, _spendProjection.getCurrentMonthExpenditure());
		assertEquals(300, _spendProjection.getQuerterlyProjection());
		assertEquals(1200, _spendProjection.getYearlyProjection());
	}
	
	private void cleanVendorTable() throws Exception {
		PreparedStatement stmt = conn.prepareStatement("DELETE FROM Vendor");
		stmt.executeUpdate();
	}
	
	private void compareVendors(Vendor vendor, Vendor testVendor){
		assertEquals(vendor.getId(), testVendor.getId());
		assertEquals(vendor.getName(), testVendor.getName());
		assertEquals(vendor.isPOCreated(), testVendor.isPOCreated());
		assertEquals(vendor.getPoDetails(), testVendor.getPoDetails());
		assertEquals(vendor.getPoType(), testVendor.getPoType());
		assertEquals(vendor.getPoNumber(), testVendor.getPoNumber());
	}
}
