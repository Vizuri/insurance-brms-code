package com.vizuri.insurance.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.vizuri.insurance.domain.xmladapter.BooleanXmlAdapter;


@SuppressWarnings("restriction")
@XmlRootElement

public class Applicant {
	@XmlElement(name="appId")
	private Integer id;
	private String firstName;
	private String lastName;
	private Boolean fraud;
	private String email;
	private String phone;
	
	@XmlJavaTypeAdapter(value = BooleanXmlAdapter.class)
	private Boolean anyPreviousInsuranceDenials;
	
	@XmlJavaTypeAdapter(value = BooleanXmlAdapter.class)
	private Boolean filedForBankruptcy;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Boolean getFraud() {
		return fraud;
	}
	public void setFraud(Boolean fraud) {
		this.fraud = fraud;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Boolean getAnyPreviousInsuranceDenials() {
		return anyPreviousInsuranceDenials;
	}
	public void setAnyPreviousInsuranceDenials(Boolean anyPreviousInsuranceDenials) {
		this.anyPreviousInsuranceDenials = anyPreviousInsuranceDenials;
	}
	public Boolean getFiledForBankruptcy() {
		return filedForBankruptcy;
	}
	public void setFiledForBankruptcy(Boolean filedForBankruptcy) {
		this.filedForBankruptcy = filedForBankruptcy;
	}
	@Override
	public String toString() {
		return "Applicant [id=" + id + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", fraud=" + fraud + ", email="
				+ email + ", phone=" + phone + ", anyPreviousInsuranceDenials="
				+ anyPreviousInsuranceDenials + ", filedForBankruptcy="
				+ filedForBankruptcy + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((anyPreviousInsuranceDenials == null) ? 0
						: anyPreviousInsuranceDenials.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime
				* result
				+ ((filedForBankruptcy == null) ? 0 : filedForBankruptcy
						.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((fraud == null) ? 0 : fraud.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
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
		Applicant other = (Applicant) obj;
		if (anyPreviousInsuranceDenials == null) {
			if (other.anyPreviousInsuranceDenials != null)
				return false;
		} else if (!anyPreviousInsuranceDenials
				.equals(other.anyPreviousInsuranceDenials))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (filedForBankruptcy == null) {
			if (other.filedForBankruptcy != null)
				return false;
		} else if (!filedForBankruptcy.equals(other.filedForBankruptcy))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (fraud == null) {
			if (other.fraud != null)
				return false;
		} else if (!fraud.equals(other.fraud))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		return true;
	}
	
	
	

}
