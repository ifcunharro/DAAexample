package es.uvigo.esei.daa.entities;

public class Address {
	private int id;
	private String street;
	private long number;
	private String locality;
	private String province;
	
	public Address() {
	}
	
	public Address(int id, String street, int number, String locality, String province) {
		this.id = id;
		this.street = street;
		this.number = number;
		this.locality = locality;
		this.province = province;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
	
	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}
	
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
}
