- ## タグの一覧の取得
    - ### [GET] https://{hostname}/{version}/tags
        タグの一覧の取得
        リストはタグIDの降順

        ---
        - レスポンス(200 OK)
            ```json
            [
                {
                    "tagId": "(tagId)",
                    "tagName": "(tagName)"
                },
                ...
            ]
            ```

- ## テンプレートについているタグ情報の取得
    - ### [GET] https://{hostname}/{version}/templates/{templateId}/tags
        テンプレートのタグ情報を取得する時に使用
        

        | パラメータ | 省略 |  |  
        | :---: | :---: | --- |
        | templateId | x | テンプレートID |


        ---
        - レスポンス(200 OK)
            ```json
            [
                {
                    "tagId": "(tagId)",
                    "tagName": "(tagName)"
                },
                ...
            ]
            ```

- ## テンプレートにタグの付与
    - ### [PUT] https://{hostname}/{version}/templates/{templateId}/tags
        新規作成時にタグをつける        

        | パラメータ | 省略 |  |  
        | :---: | :---: | --- |
        | templateId | x | テンプレートID |
        
        ---
        - リクエスト
            ```json
            [
                {
                    "tagId": "(tagId)",
                    "tagName": "(tagName)"
                },
                ...
            ]
            ```

        - レスポンス(201 CREATED)

- ## テンプレートにタグ情報の更新
    - ### [POST] https://{hostname}/{version}/template/{templateId}/tags
        テンプレートについているタグの更新        

        | パラメータ | 省略 |  |  
        | :---: | :---: | --- |
        | templateId | x | テンプレートID |
        
        ---
        - リクエスト
            ```json
            [
                {
                    "tagId": "(tagId)",
                    "tagName": "(tagName)"
                },
                ...
            ]
            ```

        - レスポンス(201 CREATED)


- ## ドキュメントタグ情報の取得
    - ### [GET] https://{hostname}/{version}/documents/{documentId}/tags
        ドキュメントのタグ情報を取得する時に使用
        

        | パラメータ | 省略 |  |  
        | :---: | :---: | --- |
        | documentId | x | ドキュメントID |


        ---
        - レスポンス(200 OK)
            ```json
            [
                {
                    "tagId": "(tagId)",
                    "tagName": "(tagName)"
                },
                ...
            ]
            ```

- ## ドキュメントにタグの付与
    - ### [PUT] https://{hostname}/{version}/documents/{documentId}/tags
        新規作成時にタグをつける        

        | パラメータ | 省略 |  |  
        | :---: | :---: | --- |
        | documentId | x | ドキュメントID |
        
        ---
        - リクエスト
            ```json
            [
                {
                    "tagId": "(tagId)",
                    "tagName": "(tagName)"
                },
                ...
            ]
            ```

        - レスポンス(201 CREATED)

- ## ドキュメントにタグ情報の更新
    - ### [POST] https://{hostname}/{version}/documents/{documentId}/tags
        ドキュメントについているタグの更新        

        | パラメータ | 省略 |  |  
        | :---: | :---: | --- |
        | documentId | x | ドキュメントID |
        
        ---
        - リクエスト
            ```json
            [
                {
                    "tagId": "(tagId)",
                    "tagName": "(tagName)"
                },
                ...
            ]
            ```

        - レスポンス(201 CREATED)