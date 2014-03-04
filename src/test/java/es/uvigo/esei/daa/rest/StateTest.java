package es.uvigo.esei.daa.rest;

import static es.uvigo.esei.daa.TestUtils.assertOkStatus;
import static es.uvigo.esei.daa.TestUtils.assertBadRequestStatus;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import es.uvigo.esei.daa.TestStateUtils;
import es.uvigo.esei.daa.entities.Address;

public class StateTest extends JerseyTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		TestStateUtils.createFakeContext();
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		TestStateUtils.initTestDatabase();
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
		
		TestStateUtils.clearTestDatabase();
	}

	@Override
	protected Application configure() {
		return new ResourceConfig(People.class)
			.register(JacksonJsonProvider.class)
			.property("com.sun.jersey.api.json.POJOMappingFeature", Boolean.TRUE);
	}

	@Override
	protected void configureClient(ClientConfig config) {
		super.configureClient(config);
		
		config.register(JacksonJsonProvider.class);
		config.property("com.sun.jersey.api.json.POJOMappingFeature", Boolean.TRUE);
	}
	
	@Test
	public void testList() throws IOException {
		final Response response = target("state").request().get();
		assertOkStatus(response);

		final List<Address> people = response.readEntity(new GenericType<List<Address>>(){});
		assertEquals(3, people.size());
	}

	@Test
	public void testGet() throws IOException {
		final Response response = target("state/2").request().get();
		assertOkStatus(response);
		
		final Address address = response.readEntity(Address.class);
		assertEquals(2, address.getId());
		assertEquals("Joaquin Loriga", address.getStreet());
		assertEquals(5, address.getNumber());
		assertEquals("Lalin", address.getLocality());
		assertEquals("Pontevedra", address.getProvince());
	}

	@Test
	public void testGetInvalidId() throws IOException {
		assertBadRequestStatus(target("state/100").request().get());
	}

	@Test
	public void testAdd() throws IOException {
		final Form form = new Form();
		form.param("street", "Xoel");
		form.param("number", "6");
		form.param("locality", "Ourense");
		form.param("province", "Ourense");
		
		final Response response = target("state")
			.request(MediaType.APPLICATION_JSON_TYPE)
			.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		assertOkStatus(response);
		
		final Address address = response.readEntity(Address.class);
		assertEquals(4, address.getId());
		assertEquals("Xoel", address.getStreet());
		assertEquals(6, address.getNumber());
		assertEquals("Ourense", address.getLocality());
		assertEquals("Ourense", address.getProvince());
	}

	@Test
	public void testAddMissingStreet() throws IOException {
		final Form form = new Form();
		form.param("number", "6");
		form.param("locality", "Ourense");
		form.param("province", "Ourense");
		
		final Response response = target("state")
			.request(MediaType.APPLICATION_JSON_TYPE)
			.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		
		assertBadRequestStatus(response);
	}

	@Test
	public void testAddMissingNumber() throws IOException {
		final Form form = new Form();
		form.param("street", "Xoel");
		form.param("locality", "Ourense");
		form.param("province", "Ourense");
		
		final Response response = target("state")
			.request(MediaType.APPLICATION_JSON_TYPE)
			.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		
		assertBadRequestStatus(response);
	}
	
	@Test
	public void testAddMissingLocality() throws IOException {
		final Form form = new Form();
		form.param("street", "Xoel");
		form.param("number", "6");
		form.param("province", "Ourense");
		
		final Response response = target("state")
			.request(MediaType.APPLICATION_JSON_TYPE)
			.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		
		assertBadRequestStatus(response);
	}
	
	@Test
	public void testAddMissingProvince() throws IOException {
		final Form form = new Form();
		form.param("street", "Xoel");
		form.param("number", "6");
		form.param("locality", "Ourense");
		
		final Response response = target("state")
			.request(MediaType.APPLICATION_JSON_TYPE)
			.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		
		assertBadRequestStatus(response);
	}
	
	@Test
	public void testModify() throws IOException {
		final Form form = new Form();
		form.param("street", "Xoel");
		form.param("number", "6");
		form.param("locality", "Ourense");
		form.param("province", "Ourense");
		
		final Response response = target("state/2")
			.request(MediaType.APPLICATION_JSON_TYPE)
			.put(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		assertOkStatus(response);
		
		final Address address = response.readEntity(Address.class);
		assertEquals(2, address.getId());
		assertEquals("Xoel", address.getStreet());
		assertEquals(6, address.getNumber());
		assertEquals("Ourense", address.getLocality());
		assertEquals("Ourense", address.getProvince());
	}

	@Test
	public void testModifyStreet() throws IOException {
		final Form form = new Form();
		form.param("street", "Iago");
		
		final Response response = target("state/2")
			.request(MediaType.APPLICATION_JSON_TYPE)
			.put(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		assertBadRequestStatus(response);
	}

	@Test
	public void testModifyNumber() throws IOException {
		final Form form = new Form();
		form.param("number", "100");
		
		final Response response = target("state/2")
			.request(MediaType.APPLICATION_JSON_TYPE)
			.put(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		
		assertBadRequestStatus(response);
	}
	
	@Test
	public void testModifyLocality() throws IOException {
		final Form form = new Form();
		form.param("locality", "Madrid");
		
		final Response response = target("state/2")
			.request(MediaType.APPLICATION_JSON_TYPE)
			.put(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		
		assertBadRequestStatus(response);
	}
	
	@Test
	public void testModifyProvince() throws IOException {
		final Form form = new Form();
		form.param("province", "province");
		
		final Response response = target("state/2")
			.request(MediaType.APPLICATION_JSON_TYPE)
			.put(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		
		assertBadRequestStatus(response);
	}

	@Test
	public void testModifyInvalidId() throws IOException {
		final Form form = new Form();
		form.param("street", "Principal");
		form.param("number", "21");
		form.param("locality", "Lalin");
		form.param("province", "Pontevedra");
		
		final Response response = target("state/100")
			.request(MediaType.APPLICATION_JSON_TYPE)
			.put(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		assertBadRequestStatus(response);
	}

	@Test
	public void testDelete() throws IOException {
		final Response response = target("state/2").request().delete();
		assertOkStatus(response);
		
		assertEquals(2, (int) response.readEntity(Integer.class));
	}

	@Test
	public void testDeleteInvalidId() throws IOException {
		assertBadRequestStatus(target("state/100").request().delete());
	}
}
