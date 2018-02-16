- ## ドキュメント情報の取得
    - ### [GET] https://{hostname}/{version}/documents/{documentId}
        ドキュメント情報の取得
        
        | パラメータ | 省略 |  |  
        | :---: | :---: | --- |
        | document | x | ドキュメントID |             

        ---
        - レスポンス(200 OK)
            ```json
            {
                "documentId": "(documentId)",
                "templateId": "(templateId)",
                "isPublish": true|false,
                "contents": [
                    {
                        "contentOrder": (contentOrder),
                        "items": [
                            "(item)",
                            ...
                        ]
                    },
                    ...
                ]
            }
            ```

- ## ドキュメント新規作成
    - ### [PUT] https://{hostname}/{version}/documents/create
        ドキュメントの新規作成

        ---
        - リクエスト
            ```json
            {
                "templateId": "(templateId)",
                "isDocumentPublish": true|false,
                "contents": [
                    {
                        "contentOrder": (contentOrder),
                        "items": [
                            "(item)",
                            ...
                        ]
                    },
                    ...
                ]
            }
            ```

        - レスポンス(201 CREATED)
            ```json
                {
                    "documentId": "(documentId)",
                    "templateId": "(templateId)",
                    "isPublish": true|false,
                    "contents": [
                        {
                            "contentOrder": (contentOrder),
                            "items": [
                                "(item)",
                                ...
                            ]
                        },
                        ...
                    ]
                }
                ```

- ## ドキュメント更新
    - ### [POST] https://{hostname}/{version}/documents/{documentId}
        ドキュメントの更新をする


        | パラメータ | 省略 |  |  
        | :---: | :---: | --- |
        | document | x | ドキュメントID | 
        
        ---
        - リクエスト
            ```json
            {
                "documentId": "(documentId)",
                "isDocumentPublish": true|false,
                "contents": [
                    {
                        "contentOrder": (contentOrder),
                        "items": [
                            "(item)",
                            ...
                        ]
                    },
                    ...
                ]
            }
            ```
        - レスポンス(201 CREATED)
