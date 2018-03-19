export class UserDetail {

    public employeeNumber: string;
    public lastName: string;
    public firstName: string;
    public lastNamePhonetic: string;
    public firstNamePhonetic: string;
    public mailaddress: string;
    public icon: boolean;

    constructor(data?: Object) {
        if(data == null) return;
        this.employeeNumber = data['employeeNumber'];
        this.lastName = data['lastName'];
        this.firstName = data['firstName'];
        this.lastNamePhonetic = data['lastNamePhonetic'];
        this.firstNamePhonetic = data['firstNamePhonetic'];
        this.icon = data['icon'];
     }

    public getName(): string {
        return this.lastName + ' ' + this.firstName;
    }
}