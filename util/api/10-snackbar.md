- **最新のUpdateIdの取得**

    - [GET] https://{hostname}/{version}/update/latest
       Sbackbar通知をするために必要な最初のUpdateIdを取得するために使用

        - レスポンス(200 OK)
        `updateId`
***

- **定期的に更新情報を取得**

    - [GET] https://{hostname}/{version}/update/latest
       Sbackbar通知をするために定期的にUpdateInfoテーブルを監視して、ログインユーザに関わる更新情報を取得する際に使用される
        - レスポンス(200 OK)



`<json [ { "documentId": "(documentId)", "updateType": "(updateType)","updateId": "(updateId)", "documentName": "(documentName)" }] >`