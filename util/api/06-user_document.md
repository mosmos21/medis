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
                    "documentTile": "(documentTitle)",
                    "employeeNumber": "(employeeNumber)",
                    "documentCreateDate": "(dicumentCreateDate)",
                    "isPublished": true|false
                },
                ...
            ]
            ```

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
                    "documentTile": "(documentTitle)",
                    "employeeNumber": "(employeeNumber)",
                    "documentCreateDate": "(dicumentCreateDate)",
                    "isPublished": true|false
                },
                ...
            ]
            ```
