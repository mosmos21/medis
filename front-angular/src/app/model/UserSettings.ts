export class UserSettings {

	public employeeNumber: string;
	public lastName: string;
	public firstName: string;
	public lastNamePhonetic: string;
	public firstNamePhonetic: string;
	public mailaddress: string;
	public isIcon: boolean;

	constructor() { };

	setSettings(data: Object): void {
		if (data == null) return;
		this.employeeNumber = data['employeeNumber'];
		this.lastName = data['lastName'];
		this.firstName = data['firstName'];
		this.lastNamePhonetic = data['lastNamePhonetic'];
		this.firstNamePhonetic = data['firstNamePhonetic'];
		this.mailaddress = data['mailaddress'];
		this.isIcon = data['icon'];
	}
}