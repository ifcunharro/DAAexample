package es.uvigo.esei.daa.dao;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import es.uvigo.esei.daa.TestStateUtils;
import es.uvigo.esei.daa.entities.Address;

public class StateDAOTest {
	private StateDAO dao;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		TestStateUtils.createFakeContext();
	}

	@Before
	public void setUp() throws Exception {
		TestStateUtils.initTestDatabase();
		this.dao = new StateDAO();
	}

	@After
	public void tearDown() throws Exception {
		TestStateUtils.clearTestDatabase();
		this.dao = null;
	}

	@Test
	public void testGet() throws DAOException {
		final Address address = this.dao.get(2);
		
		assertEquals(2, address.getId());
		assertEquals("Joaquin Loriga", address.getStreet());
		assertEquals(5, address.getNumber());
		assertEquals("Lalin", address.getLocality());
		assertEquals("Pontevedra", address.getProvince());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetInvalidId() throws DAOException {
		this.dao.get(100);
	}

	@Test
	public void testList() throws DAOException {
		assertEquals(3, this.dao.list().size());
	}

	@Test
	public void testDelete() throws DAOException {
		this.dao.delete(3);
		
		assertEquals(2, this.dao.list().size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteInvalidId() throws DAOException {
		this.dao.delete(100);
	}

	@Test
	public void testModify() throws DAOException {
		this.dao.modify(2, "Jose Antonio", 13, "Lalin", "Pontevedra");
		
		final Address address = this.dao.get(2);
		
		assertEquals(2, address.getId());
		assertEquals("Jose Antonio", address.getStreet());
		assertEquals(13, address.getNumber());
		assertEquals("Lalin", address.getLocality());
		assertEquals("Pontevedra", address.getProvince());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testModifyInvalidId() throws DAOException {
		this.dao.modify(100, "Jose Antonio", 13, "Lalin", "Pontevedra");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testModifyNullStreet() throws DAOException {
		this.dao.modify(2, null, 13, "Lalin", "Pontevedra");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testModifyNullNumber() throws DAOException {
		this.dao.modify(2, "Jose Antonio", null, "Lalin", "Pontevedra");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testModifyNullLocality() throws DAOException {
		this.dao.modify(2, "Jose Antonio", 13, null, "Pontevedra");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testModifyNullProvince() throws DAOException {
		this.dao.modify(2, "Jose Antonio", 13, "Lalin", null);
	}

	@Test
	public void testAdd() throws DAOException {
		final Address address = this.dao.add("John", 30,"Castellon","Valencia");
		
		assertEquals("John", address.getStreet());
		assertEquals(30, address.getNumber());
		assertEquals("Castellon", address.getLocality());
		assertEquals("Valencia", address.getProvince());
		
		final Address addressGet = this.dao.get(address.getId());

		assertEquals(address.getId(), addressGet.getId());
		assertEquals("John", addressGet.getStreet());
		assertEquals(30, addressGet.getNumber());
		assertEquals("Castellon", addressGet.getLocality());
		assertEquals("Valencia", addressGet.getProvince());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddNullStreet() throws DAOException {
		this.dao.add(null, 30,"Castellon","Valencia");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddNullNumber() throws DAOException {
		this.dao.add("John", null,"Castellon","Valencia");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddNullLocality() throws DAOException {
		this.dao.add("John", 30,null,"Valencia");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddNullProvince() throws DAOException {
		this.dao.add("John", 30,"Castellon",null);
	}
}
