- ## 更新情報の一覧を取得
    - ### [GET] https://{hostname}/{version}/infomations/{user}?size={size}
    - ### [GET] https://{hostname}/{version}/infomations/{user}/{lastUpdateId}?size={size}
        トップページに表示する更新情報の一覧を取得する  
        ログインしている社員番号と異なる社員番号を指定するとエラーになる


        | パラメータ | 省略 |  |  
        | :---: | :---: | --- |
        | user | x | 社員番号 |
        | lastUpdateId | o | 最新の更新情報Id |
        | size | o | 取得件数 |
        
        ---
        - レスポンス(200 OK)
            ```json
            [
                {
                    "updateId": "(updateId)",
                    "documentId": "(documentId)",
                    "documentTitle": "(documentTitle)",
                    "updateType": "(updateType)",
                    "employeeNumber": "(employeeNumber)",
                    "updateDate": "(updateDate)"
                },
                ...
            ]
            ```


- ## ドキュメント一覧の情報を取得
    - ### [GET] https://{hostname}/{version}/documents/{user}?size={size}
    - ### [GET] https://{hostname}/{version}/documents/{user}/{type}?size={size}
        トップページに表示する更新情報の一覧を取得する  
        ログインしている社員番号と異なる社員番号を指定するとエラーになる


        | パラメータ | 省略 |  |  
        | :---: | :---: | --- |
        | user | x | 社員番号 |
        | type | o | publish : 公開済み文書<br>private : 下書き文書 |
        | size | o | 取得件数 |
        
        ---
        - レスポンス(200 OK)
            ```json
            [
                {
                    "documentId": "(documentId)",
                    "documentTitle": "(documentTitle)",
                    "employeeNumber": "(employeeNumber)",
                    "documentCreateDate": "(documentCreateDate)",
                    "isPublished": true|false
                },
                ...
            ]
            ```


- ## お気に入り文書の一覧を取得
    - ### [GET] https://{hostname}/{version}/documents/{user}/bookmark?size={size}
        ユーザが登録しているお気に入り文書の情報の一覧を取得する  
        ログインしている社員番号と異なる社員番号を指定するとエラーになる


        | パラメータ | 省略 |  |  
        | :---: | :---: | --- |
        | user | x | 社員番号 |
        | size | o | 取得件数 |
        
        ---
        - レスポンス(200 OK)
            ```json
            [
                {
                    "documentId": "(documentId)",
                    "documentTitle": "(documentTitle)",
                    "employeeNumber": "(employeeNumber)",
                    "documentCreateDate": "(documentCreateDate)",
                    "isPublished": true|false
                },
                ...
            ]
            ```

- ## お気に入りボタンを押した時
    - ### [POST] https://{hostname}/{version}/documents/{user}/bookmark/{documentId}
        ユーザがお気に入りボタンを押した時に情報を更新する  
        ログインしている社員番号と異なる社員番号を指定するとエラーになる


        | パラメータ | 省略 |  |  
        | :---: | :---: | --- |
        | user | x | 社員番号 |
        | document | x | ドキュメントID |


        
        
- ## 監視タグ文書の一覧を取得
    - ### [GET] https://{hostname}/{version}/documents/{user}/monitoring_tags?size={size}
        ユーザが監視タグに登録したタグが付与されている文書の一覧を取得する
        ログインしている社員番号と異なる社員番号を指定するとエラーになる

        | パラメータ | 省略 |  |  
        | :---: | :---: | --- |
        | user | x | 社員番号 |
        | size | o | 取得件数 |
        
        ---
        - レスポンス(200 OK)
            ```json
            [
                {
                    "documentId": "(documentId)",
                    "documentTitle": "(documentTitle)",
                    "employeeNumber": "(employeeNumber)",
                    "documentCreateDate": "(documentCreateDate)",
                    "isPublished": true|false
                },
                ...
            ]
            ```
