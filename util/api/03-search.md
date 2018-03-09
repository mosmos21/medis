- ## 検索結果の取得
    - ### [GET] https://{hostname}/{version}/search?tags={tags}
        検索するタグを記入し、検索を実行する時に使用

        | パラメータ | 省略 |  |  
        | :---: | :---: | --- |
        | tags | x | 検索するタグを'+'で連結した文字列<br>日本語はURLエンコードする |
        
        ---
        - レスポンス(200 OK)
            ```json
            [
                {
                    "documentId": "(documentId)",
                    "documentName": "(documentName)",
                    "employeeNumber": "(employeeNumber)",
                    "templateId": "(templateId)",
                    "documentCreateDate": "(documentCreateDate)",
                    "documentPublish": true|false
                },
                ...
            ]
            ```