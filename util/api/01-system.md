- ## ログイン
    - ### [POST] https://{hostname}/{version}/login
        ログインする

        ---
        - リクエスト
            ```json
            {
                "employeeNumber": "(employeeNumber)",
                "password": "(password)"
            }
            ```

        - レスポンス(200 OK)
            ```json
            {
                "employeeNumber": "(employeeNumber)",
                "authorityId": "(authorityId)",
                "enabled": true|false,
                "username": "(username)",
                "authorities": "(authorities)",
                "accountNonLocked": true|false,
                "credentialsNonExpired": true|false,
                "accountNonExpired": true|false
            }
            ```

- ## ログアウト
    - ### [DELETE] https://{hostname}/{version}/logout
        ログアウトする 
        
        - レスポンス(204 NO CONTENT)

- ## ユーザ権限種別一覧の取得
    - ### [GET] http://{hostname}/{version}/system/authorities  
        権限種別一覧の取得  
        配列はauthorityIdの昇順

        ---
        - レスポンス(200 OK)
            ```json
            [
                {
                    "authorityId": "(authorityId)",
                    "authorityType": "(authorityType)"
                }
            ]
            ```

- ## ユーザ管理情報の取得
    - ### [GET] https://{hostname}/{version}/system/users?size={size}
        ユーザの一覧を取得
        

        | パラメータ | 省略 | | 
        | :---: | :---: | --- |  
        | size | o(page指定時は省略不可) |取得件数(省略時全件取得)| |
        | page | o | 取得件数(省略時全件取得)| |


        ---
        - レスポンス(200 OK)
            ```json
            [
                {
                    "employeeNumber": "(employeeNumber)",
                    "lastName": "(lastName)",
                    "firstName": "(firstName)",
                    "lastNamePhonetic": "(lastNamePhonetic)",
                    "firstNamePhonetic": "(firstNamePhonetic)",
                    "mailaddress": "(mailaddress)",
                    "isIcon": true|false,
                    "authorityType": "(authorityType)",
                    "isEnable": true|false
                },
                ...
            ]
            ```

- ## ユーザ新規作成
    - ### [PUT] https://{hostname}/{version}/system/users/new
        新規ユーザを作成する

        ---
        - リクエスト
            ```json
            {
                "employeeNumber": "(employeeNumber)",
                "lastName": "(lastName)",
                "firstName": "(firstName)",
                "lastNamePhonetic": "(lastNamePhonetic)",
                "firstNamePhonetic": "(firstNamePhonetic)",
                "mailaddress": "(mailaddress)",
                "isIcon": true|false,
                "authorityType": "(authorityType)",
                "isEnable": true|false,
            }
            ```

        - レスポンス(201 CREATED)

- ## ユーザ管理情報の更新
    - ### [POST] https://{hostname}/{version}/system/users
        ユーザの有効状態、権限を変更する

        ---
        - リクエスト
            ```json
            [
                {
                    "employeeNumber": "(employeeNumber)",
                    "isEnable": true|false,
                    "authorityType": "(authorityType)"
                },
                ...
            ]
            ```
            
        - レスポンス(201 CREATED)