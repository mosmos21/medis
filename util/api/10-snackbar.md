- ## 最新のUpdateId取得
    - ### [GET] https://{hostname}/{version}/update/latest
       Sbackbar通知をするために必要な最初のUpdateIdを取得するために使用



- ## 定期的にログインユーザに関わる更新情報を取得する
    - ### [GET] https://{hostname}/{version}/update/{updateId}

        定期的にログインユーザに関わるコメント、既読、監視タグのついた文書の投稿を通知する
        
        | パラメータ | 省略 |  |  
        | :---: | :---: | --- |
        | uodateId | x | アップデートID |   

        ---
        - レスポンス(200 OK)
            ```json
            [
                {
                    "documentId": "(documentId)",
                    "updateType": "(updateType)",
                    "updateId": "(updateId)",
                    "documentName": "(documentName)"
                },
            ]
            ```