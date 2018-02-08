exports.default = function (req) {
    if(req.body.employeeNumber == '99999' && req.body.password == 'hoge'){
        return {
            status: 200,
            headers: {
                'content-type': 'application/text',
            },
            body: 'success.json'
        };
    } else {
        return {
            status: 403
        }
    }
    
};