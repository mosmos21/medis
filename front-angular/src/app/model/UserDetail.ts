import { Inject } from "@angular/core";

export class UserDetail {

	public employeeNumber: string;
	public lastName: string;
	public firstName: string;
	public lastNamePhonetic: string;
	public firstNamePhonetic: string;
	public mailaddress: string;
	public iconUrl: string;

	constructor(hostname: string, data?: Object) {
		if (data == null) return;
		this.employeeNumber = data['employeeNumber'];
		this.lastName = data['lastName'];
		this.firstName = data['firstName'];
		this.lastNamePhonetic = data['lastNamePhonetic'];
		this.firstNamePhonetic = data['firstNamePhonetic'];
		this.iconUrl = hostname + 'icon/me';
	}

	public getName(): string {
		return this.lastName + ' ' + this.firstName;
	}
}