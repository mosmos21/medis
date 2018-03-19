export class User {
    
    public employeeNumber: string;
	public authorityId: string;
    public enabled: boolean;
    
    constructor(data?: Object) {
        if(data == null) return;
        this.employeeNumber = data['employeeNumber'];
        this.authorityId = data['authorityId'];
        this.enabled = data['enabled'];
    }
}