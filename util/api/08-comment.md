- ## コメント情報の取得
    - ### [GET] https://{hostname}/{version}/documents/{documentId}/comments
        ユーザが文章を開いたときに、コメントを表示させるために使用
        

        | パラメータ | 省略 |  |  
        | :---: | :---: | --- |
        | documentId | x | ドキュメントID |   


        ---
        - レスポンス(200 OK)
            ```json
            [
                {
                    "commentId": "(commentId)",
                    "commentDate": "(commentDate)",
                    "icon": true|false,
                    "employeeNumber": "(employeeNumber)",
                    "lastName": "(lastName)",
                    "firstName": "(firstName)",
                    "read": true|false,
                    "commentContent": "(commentContent)"
                },
                ...
            ]
            ```

- ## コメント投稿
    - ### [PUT] https://{hostname}/{version}/documents/{documentId}/comments/create
        ユーザが新しくコメントを投稿する時に使用
        

        | パラメータ | 省略 |  |  
        | :---: | :---: | --- |
        | documentId | x | ドキュメントID |   


        ---
        - リクエスト
            ```json
            {
                "employeeNumber": "(employeeNumber)",
                "commentContent": "(commentContent)"
            }
            ```

- ## コメントの既読
    - ### [POST] https://{hostname}/{version}/documents/{documentId}/comments/{commentId}/read
        コメントを既読にする  

        | パラメータ | 省略 |  |  
        | :---: | :---: | --- |
        | documentId | x | ドキュメントID |   
        | commentId | x | コメントID | 

        ---
        - リクエスト
            ```json
            {
                "employeeNumber": "(employeeNumber)"
            }
            ```  
