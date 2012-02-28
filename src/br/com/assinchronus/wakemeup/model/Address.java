package br.com.assinchronus.wakemeup.model;

import java.io.Serializable;

public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	public String street;
	public String zip;
	public String subAdmin;
	public String city;
	public double latitude;
	public double longitude;
	
	public transient Position position;
}