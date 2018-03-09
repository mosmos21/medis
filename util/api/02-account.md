- ## パスワード再設定用のデータの確認結果の取得
    - ### [POST] https://{hostname}/{version}/accounts/usercheck
        パスワード再設定時にユーザが入力する社員番号とメールアドレスが存在するか、  
        またデータが一致するかをチェックし、番号で返す  
        問題なかった場合、一時キーの生成とメールを送る処理をおこなうロジックを動かす
        
        ---
        - リクエスト
            ```json
            {
                "employeeNumber": "(employeeNumber)",
                "mailaddress": "(mailaddress)"
            }
            ```

        - レスポンス(200 OK)
            ```json
            {
                "result": "(result)",
                "message": "(message)"
            }
            ```

- ## 一時キーの整合性、使用期限チェックの確認結果の取得
    - ### [POST] https://{hostname}/{version}/accounts/keycheck
        送られてきたパスワード再設定用のメールにあるURLに飛んだ際に、一時キーと社員番号の整合性と、一時キーの有効期限のチェックの結果を返す
        
        - リクエスト
            ```json
            {
                "tempKey": "(tempKey)"
            }
            ```

        ---
        - レスポンス(200 OK)
            ```json
            {
                "result": "(result)",
                "message": "(message)"
            }
            ```

- ## パスワードの更新
    - ### [POST] https://{hostname}/{version}/accounts/reset
        パスワードを再設定する

        | パラメータ | 省略 |  |  
        | :---: | :---: | --- |  
        | user | x | 社員番号 |

        
        ---
        - リクエスト
            ```json
            {
                "employeeNumber": "(employeeNumber)",
                "mailaddress": "(mailaddress)",
                "password": "(password)"
            }
            ```

        - レスポンス(201 OK)

- ## ユーザ情報設定情報の取得
    - ### [GET] https://{hostname}/{version}/settings/me
        「設定」のユーザ情報を表示させる
        
        ---
        - レスポンス(200 OK)
            ```json
            {
                "employeeNumber": "(employeeNumber)",
                "lastName": "(lastName)",
                "firstName": "(firstName)",
                "lastNamePhonetic": "(lastNamePhonetic)",
                "firstNamePhonetic": "(firstNamePhonetic)",
                "mailaddress": "(mailaddress)",
                "isIcon": true|false
            }
            ```

- ## ユーザ情報設定情報の更新
    - ### [POST] https://{hostname}/{version}/settings/me
        ユーザ情報の更新

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
                "isIcon": true|false
            }
            ```

        - レスポンス(201 CREATED)

- ## トップページボックス一覧の取得
    - ### [GET] https://{hostname}/{version}/settings/toppageboxes
        トップページの表示順を取得
        
        ---
        - レスポンス(200 OK)
            ```json
            [
                {
                    "boxId": "(boxId)",
                    "boxName": "(boxName)"
                },
                ...
            ]
            ```

- ## トップページ設定情報の取得
    - ### [GET] https://{hostname}/{version}/settings/me/toppage
        トップページの表示順を取得
        
        ---
        - レスポンス(200 OK)
            ```json
            [
                {
                    "boxId": "(boxId)",
                    "toppageOrder": (toppageOrder),
                    "boxName": "(boxName)"
                },
                ...
            ]
            ```

- ## トップページ設定情報の更新
    - ### [POST] https://{hostname}/{version}/settings/me/toppage
        トップページの表示順を更新
        
        ---
        - リクエスト
            ```json
            [
                {
                    "boxId": "(boxId)",
                    "toppageOrder": (toppageOrder),
                    "boxName": "(boxName)"
                },
                ...
            ]
            ```
        - レスポンス(201 CREATED)

- ## 監視タグ設定情報の取得
    - ### [GET] https://{hostname}/{version}/settings/me/monitoring_tags
        監視しているタグの一覧を取得
        
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

- ## 監視タグ設定情報の更新
    - ### [POST] https://{hostname}/{version}/settings/me/monitoring_tags
        監視しているタグの一覧を更新

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

- ## 通知設定情報の取得(タグ)
    - ### [GET] https://{hostname}/{version}/settings/me/tag_notifications
        通知設定情報を取得

        ---
        - レスポンス(200 OK)
            ```json
            [
                {
                    "tagId": "(tagId)",
                    "tagName": "(tagName)",
                    "isMailNotification": true|false,
                    "isBrowserNotification": true|false
                },
                ...
            ]
            ```

- ## 通知設定情報の更新(タグ)
    - ### [POST] https://{hostname}/{version}/settings/me/tag_notifications
        通知設定情報を更新する
        
        ---
        - リクエスト
            ```json
            [
                {
                    "tagId": "(tagId)",
                    "isMailNotification": true|false,
                    "isBrowserNotification": true|false
                },
                ...
            ]
            ```
        - レスポンス(201 CREATED)


- ## 通知設定情報の取得(コメント)
    - ### [GET] https://{hostname}/{version}/settings/me/comment_notifications
        通知設定情報を取得<br>
        tagIdはコメント専用のタグを作る

        ---
        - レスポンス(200 OK)
            ```json
            {
                "isMailNotification": true|false,
                "isBrowserNotification": true|false
            }
            ```

- ## 通知設定情報の更新(コメント)
    - ### [POST] https://{hostname}/{version}/settings/me/comment_notifications
        通知設定情報を更新する<br>
        tagIdはコメント専用のタグを作る
        
        ---
        - リクエスト
            ```json
            {
                "isMailNotification": true|false,
                "isBrowserNotification": true|false
            }
            ```
        - レスポンス(201 CREATED)