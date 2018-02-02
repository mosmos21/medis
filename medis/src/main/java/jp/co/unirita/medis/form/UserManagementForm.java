package jp.co.unirita.medis.form;

import lombok.Data;

@Data
public class UserManagementForm {
	public String employeeNumber;
	public String lastName;
	public String firstName;
	public String lastNamePhonetic;
	public String firstNmaePhonetic;
	public String mailaddress;
	public boolean isEnabled;

}
