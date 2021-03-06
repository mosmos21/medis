
- ## テンプレート一覧情報の取得
    - ### [GET] https://{hostname}/{version}/templates?size={size}

        テンプレートの基本情報の一覧を取得  
        リストはテンプレートIDの降順にソート
        

        | パラメータ | 省略 |  |  
        | :---: | :---: | --- |
        | size | o | 取得件数(省略時全件取得) |
        

        ---
        - レスポンス(200 OK)
            ```json
            [
                {
                    "templateId": "(templateId)",
                    "templateName": "(templateName)",
                    "templateCreateDate": "(templateCreateDate)",
                    "employeeNumber": "(employeeNumber)",
                    "templatePublish": true|false
                },
                ...
            ]
            ```

- ## 公開済みテンプレート一覧情報の取得
    - ### [GET] http://{hostname}/{version}/templates/public?size={size}

        公開済みテンプレートの一覧を取得する

        | パラメータ | 省略 |  |  
        | :---: | :---: | --- |
        | size | o | 取得件数(省略時全件取得) |

        ---
        - レスポンス(200 OK)
            ```json
            [
                {
                    "templateId": "(templateId)",
                    "templateName": "(templateName)",
                    "templateCreateDate": "(templateCreateDate)",
                    "employeeNumber": "(employeeNumber)",
                    "templatePublish": true|false
                },
                ...
            ]
            ```

- ## テンプレートの公開状態の変更
    - ### [POST] http://{hostname}/{version}/templates/{templateId}/{type}

        テンプレートの公開、非公開を変更する

        | パラメータ | 省略 |  |  
        | :---: | :---: | --- |
        | templateId | x | テンプレートID |
        | type| x | public: 公開する<br>private: 非公開にする |

        ---
        - レスポンス(201 CREATED)


- ## テンプレートブロック情報の取得
    - ### [GET] https://{hostname}/{version}/templates/blocks
        テンプレートブロックの一覧を取得

        ---
        - レスポンス(200 OK)
            ```json
            {
                "blockId": "(blockId)",
                "blockName": "(blockName)",
                "unique": true|false,
                "items": [
                    {
                        "size": (size),
                        "templateType": "(templateType)",
                        "documentType": "(documentType)",
                        "value": "(value)"
                    },
                    ...
                ],
                "additionalType": null|"template"|"document",
                "addItems": [
                    {
                        "size": (size),
                        "templateType": "(templateType)",
                        "documentType": "(documentType)",
                        "value": "(value)"
                    },
                ]
            },
            ```

- ## テンプレートコンテント情報の取得
    - ### [GET] https://{hostname}/{version}/templates/{templateId}
        テンプレート情報の取得

        | パラメータ | 省略 |  |  
        | :---: | :---: | --- |
        | templateID | x | テンプレートID |

        ---
        - レスポンス(200 OK)
            ```json
            {
                "templateId":(templateId),
                "publish": true|false,
                "contents": [
                    {
                        "contentOrder": (contentOrder),
                        "blockId": "(blockId)",
                        "items": [
                            "(item)",
                            ...
                        ]
                    },
                    ...
                ]
            }
            ```

- ## テンプレート新規作成
    - ### [PUT] https://{hostname}/{version}/templates/create
        テンプレートの新規作成を行う

        ---
        - リクエスト
            ```json
            {
                "publish": true|false,
                "contents": [
                    {
                        "contentOrder": (contentOrder),
                        "blockId": "(blockId)",
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
                "templateId":(templateId),
                "publish": true|false,
                "contents": [
                    {
                        "contentOrder": (contentOrder),
                        "blockId": "(blockId)",
                        "items": [
                            "(item)",
                            ...
                        ]
                    },
                    ...
                ]
            }
            ```

- ## テンプレート更新
    - ### [POST] https://{hostname}/{version}/templates/{templateId}
        テンプレートの更新を行う

        | パラメータ | 省略 |  |  
        | :---: | :---: | --- |
        | templateId | x | テンプレートID |

        ---
        - リクエスト
            ```json
            [
                {
                    "contentOrder": (contentOrder),
                    "blockId": "(blockId)",
                    "items": [
                        "(item)",
                        ...
                    ]
                },
                ...
            ]
            ```
            
        - レスポンス(201 CREATED)
