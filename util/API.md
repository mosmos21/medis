# MEDIS API 一覧

medisのAPI情報の一覧  
ホスト名 {host} = localhost:8080

- ## トップページの更新情報
    - ### [GET] https://{host}/updates/{user}/{lastUpdateId}/{size}
        ユーザに関連する更新情報を取得する  
        引数を省略した場合はそのユーザに関連するすべての更新情報を取得する
        | 変数 | |
        | :---: | --- |
        | user | 情報を取得する社員番号 |
        | lastUpdateId | 最後に取得した更新情報ID |
        | size | 新しいものから何件まで取得するか指定 |

        ---
        - レスポンス
        ```json
        {
            "size": "(新しく更新があった情報の数)",
            "updates": [
                {
                    "id": "(updateId)",
                    "documentId": "(documentId)",
                    "documentName": "(documentName)",
                    "type": "(updateType)",
                    "employeeNumber": "(employeeNumber)",
                    "date": "(updateDate)"
                },
                ...
            ]
        }
        ```

- ## ドキュメント一覧の情報を取得
    - ### [GET] https://{host}/documents/{user}
    - ### [GET] https://{host}/documents/{user}/{size}
    - ### [GET] https://{host}/documents/{user}/publish
    - ### [GET] https://{host}/documents/{user}/publish/{size}
    - ### [GET] https://{host}/documents/{user}/private
    - ### [GET] https://{host}/documents/{user}/private/{size}


        ユーザの文書の情報の一覧を取得する
        ログインしているユーザ自身のドキュメントを指定する場合は、  
        publishedの引数を指定することが可能になる。  
        引数を指定していない場合はpublished=trueと同様の扱いとなる

        | 変数 |  |
        | :---: | --- |
        | user | 情報を取得する社員番号 |
        | type | all : すべての文書<br>true : 公開済み文書<br>false : 下書き文書 |
        | size | 新しいものから何件まで取得するか指定 |

        ---
        - レスポンス
        ```json
        "documents": [
            {
                "id": "(documentId)",
                "title": "(documentTitle)",,
                "employeeNumber": "(employeeNumber)",
                "createDate": "(createDate)",
                "isPublished": "(true|false)"
            },
            ...
        ]
        ```

- ## お気に入り文書の一覧を取得
    - ### [GET] https://{host}/documents/{user}/bookmark
    - ### [GET] https://{host}/documents/{user}/bookmark/{size}
        ユーザが登録しているお気に入り文書の情報の一覧を取得する  
        ログインしている社員番号と異なる社員番号を指定するとエラーになる

        | 変数 |  |
        | :---: | --- |
        | user | 情報を取得する社員番号 |
        | size | 新しいものから何件まで取得するか指定 |
        
        ---
        - レスポンス
        ```json
        {
            "size": "(登録されている文書の数)",
            "documents": [
                {
                    "id": "(documentId)",
                    "title": "(documentTitle)",,
                    "employeeNumber": "(employeeNumber)",
                    "createDate": "(createDate)"
                },
                ...
            ]
        }
        ```

- ## 監視タグ文書の一覧を取得
    - ### [GET] https://{host}/documents/{user}/monitoring_tag
    - ### [GET] https://{host}/documents/{user}/monitoring_tag/{size}
        ユーザが監視タグに登録したタグが付与されている文書の一覧を取得する
        ログインしている社員番号と異なる社員番号を指定するとエラーになる

        | 変数 |  |
        | :---: | --- |
        | user | 情報を取得する社員番号 |
        | size | 新しいものから何件まで取得するか指定 |
        
        ---
        - レスポンス
        ```json
        {
            "size": "(登録されている文書の数)",
            "documents": [
                {
                    "id": "(documentId)",
                    "title": "(documentTitle)",,
                    "employeeNumber": "(employeeNumber)",
                    "createDate": "(createDate)"
                },
                ...
            ]
        }
        ```


- ## ログイン時アカウント情報の取得
    - ### [GET] https://{host}/accounts/{user}
        ログイン画面から社員番号とパスワードを入力した後のアカウント情報を取得する
        

        | 変数 |  |
        | :---: | --- |
        | user | 入力された社員番号 |
        

        ---
        - レスポンス
        ```json
        {
            "isEnabled": "(isEnabled)",
            "authorityId": "(authorityId)",
            "passwordhash": "(passwordhash)"
        }
        ```

- ## パスワード再設定用のデータの確認結果の取得
    - ### [GET] https://{host}/accounts/password/{user}
        パスワード再設定時にユーザが入力する社員番号とメールアドレスが存在するか、またデータが一致するかをチェックし、番号で返す<br>
        問題なかった場合、一時キーの生成とメールを送る処理をおこなうロジックを動かす

        | 変数 |  |
        | :---: | --- |
        | user | 情報を取得する社員番号 |
        

        ---
        - レスポンス
        ```json
        {
            "responseNumber": "(responseNumber)"
        }
        ```

- ## 一時キーの整合性、使用期限チェックの確認結果の取得
    - ### [GET] https://{host}/accounts/password/{tempKey}
        送られてきたパスワード再設定用のメールにあるURLに飛んだ際に、一時キーと社員番号の整合性と、一時キーの有効期限のチェックの結果を返す

        | 変数 |  |
        | :---: | --- |
        | tempKey | 社員番号との整合性をとるための一時キー |
        

        ---
        - レスポンス
        ```json
        {
            "isAvailable": "(isAvailable)"
        }
        ```

- ## パスワードの更新
    - ### [PUT] https://{host}/accounts/password
        パスワードを再設定した際の結果を返す

        | 変数 |  |
        | :---: | --- |

        

        ---
        - リクエスト
        ```json
        {
            "employeeNumber": "(employeeNumber)",
            "mailaddress": "(mailaddress)",
            "passwordhash": "(passwordhash)"
        }
        ```
        - レスポンス
        ```json
        {
            "isUpdated": "(isUpdated)"
        }
        ```

- ## ユーザ情報設定情報の取得
    - ### [GET] https://{host}/settings/me
        

        | 変数 |  |
        | :---: | --- |
        

        ---
        - レスポンス
        ```json
        {
            "employeeNumber": "(employeeNumber)",
            "lastName": "(lastName)",
            "firstName": "(firstName)",
            "lastNamePhonetic": "(lastNamePhonetic)",
            "firstNamePhonetic": "(firstNamePhonetic)",
            "mailaddress": "(mailaddress)",
            "isIcon": "(isIcon)"
        }
        ```

- ## ユーザ情報設定情報の更新
    - ### [PUT] https://{host}/settings/me
        

        | 変数 |  |
        | :---: | --- |
        

        ---
        - リクエスト
        ```json
        {
            "employeeNumber": "(employeeNumber)",
            "lastName": "(lastName)",
            "firstName": "(firstName)",
            "lastNamePhonetic": "(lastNamePhonetic)",
            "firstNamePhonetic": "(firstNamePhonetic)",
            "isIcon": "(isIcon)"
        }
        ```
        - レスポンス
        ```json
        {
            "isUpdated": "(isUpdated)"
        }

- ## トップページ設定情報の取得
    - ### [GET] https://{host}/settings/me/toppage
        

        | 変数 |  |
        | :---: | --- |
        

        ---
        - レスポンス
        ```json
        "boxList": [
            {
                "employeeNumber": "(employeeNumber)",
                "boxId": "(boxId)",
                "toppageOrder": "(toppageOrder)",
                "boxName": "(boxName)"
            },
            ...
        ]
        ```

- ## トップページ設定情報の更新
    - ### [PUT] https://{host}/settings/me/toppage
        

        | 変数 |  |
        | :---: | --- |
        

        ---
        - リクエスト
        ```json
        "boxList": [
            {
                "employeeNumber": "(employeeNumber)",
                "boxId": "(boxId)",
                "toppageOrder": "(toppageOrder)",
            },
            ...
        ]
        ```
        - レスポンス
        ```json
        {
            "isUpdated": "(isUpdated)"
        }

- ## 監視タグ設定情報の取得
    - ### [GET] https://{host}/settings/me/monitoring_tag
        

        | 変数 |  |
        | :---: | --- |
        

        ---
        - レスポンス
        ```json
        {
            "size": "(監視しているタグの数)",
            "tags": [
                {
                    "employeeNumber": "(employeeNumber)",
                    "tagId": "(tagId)",
                    "tagName": "(tagName)"
                },
                ...
            ]
        }
        ```

- ## 監視タグ設定情報の更新
    - ### [PUT] https://{host}/settings/me/monitoring_tag
        

        | 変数 |  |
        | :---: | --- |
        

        ---
        - リクエスト
        ```json
        {
            "tags": [
                {
                    "employeeNumber": "(employeeNumber)",
                    "tagId": "(tagId)",
                },
                ...
            ]
        }
        ```
        - レスポンス
        ```json
        {
            "isUpdated": "(isUpdated)"
        }

- ## 通知設定情報の取得
    - ### [GET] https://{host}/settings/me/notification
        

        | 変数 |  |
        | :---: | --- |
        

        ---
        - レスポンス
        ```json
        {
            "size": "(監視しているタグの数)",
            "notification_config": [
                {
                    "employeeNumber": "(employeeNumber)",
                    "tagId": "(tagId)",
                    "tagName": "(tagName)",
                    "isMailNotification": "(isMailNotification)",
                    "isBrowserNotification": "(isBrowserNotification)"
                },
                ...
            ]
        }
        ```

- ## 通知設定情報の更新
    - ### [PUT] https://{host}/settings/me/notification
        

        | 変数 |  |
        | :---: | --- |
        

        ---
        - リクエスト
        ```json
        {
            "nitification_config": [
                {
                    "employeeNumber": "(employeeNumber)",
                    "tagId": "(tagId)",
                    "isMailNotification": "(isMailNotification)",
                    "isBrowserNotification": "(isBrowserNotification)"
                },
                ...
            ]
        }
        ```
        - レスポンス
        ```json
        {
            "isUpdated": "(isUpdated)"
        }

- ## テンプレート一覧情報の取得
    - ### [GET] https://{host}/settings/templateList
    - ### [GET] https://{host}/settings/templateList/publish
    - ### [GET] https://{host}/settings/templateList/private
        管理者によるテンプレート編集、文書作成時のテンプレート選択時に使用する
        

        | 変数 |  |
        | :---: | --- |

        

        ---
        - レスポンス
        ```json
        {
            "size": "(表示させるテンプレートの数)",
            "templateList": [
                {
                    "templateId": "(templateId)",
                    "templateName": "(templateName)",
                    "templateCreateDate": "(templateCreateDate)",
                    "employeeNumber": "(employeeNumber)",
                    "isTemplatePublish": "(isTemplatePublish)"
                },
                ...
            ]
        }
        ```

- ## テンプレート情報の取得
    - ### [GET] https://{host}/template/{templateId}
        

        | 変数 |  |
        | :---: | --- |
        | templateId | 情報を取得するテンプレート番号 |


        ---
        - レスポンス
        ```json
        {
            "templateId": "(templateId)",
            "fixedTagId": "(fixedTagId)",
            "tagId": "(tagId)",
            "tagName": "(tagName)",
            "templateframe" [
                {
                    "templateOrder": "(templateOrder)",
                    "blockId": "(blockId)",
                    "blockBaseHtml": "(blockBaseHtml)",
                    "isUnique": "(isUnique)",
                    "isVariable": "(isVariable)",
                    "addHtml": "(addHtml)",
                    "lineLength": "(lineLength)",
                    "isUserValiable": "(isUserValiable)",
                    "templateContent": [
                        {
                            "lineNumber": "(lineNumber)",
                            "contentHtml": "(contentHtml)",
                        },
                        ...
                    ]
                },
                ...
            ]
        }
        ```

- ## テンプレート情報の更新
    - ### [PUT] https://{host}/settings/template/{templateId}
        

        | 変数 |  |
        | :---: | --- |
        | templateId | 情報を取得するテンプレート番号 |


        ---
        - リクエスト
        ```json
        {
            "templateId": "(templateId)",
            "templateCreateDate": "(templateCreateDate)",
            "isTemplatePabulish": "(isTemplatePublish)",
            "fixedTagId": "(fixedTagId)",
            "tagId": "(tagId)",
            "tagName": "(tagName)",
            "templateframe" [
                {
                    "templateOrder": "(templateOrder)",
                    "blockId": "(blockId)",
                    "blockBaseHtml": "(blockBaseHtml)",
                    "isUnique": "(isUnique)",
                    "isVariable": "(isVariable)",
                    "addHtml": "(addHtml)",
                    "lineLength": "(lineLength)",
                    "isUserValiable": "(isUserValiable)",
                    "templateContent": [
                        {
                            "lineNumber": "(lineNumber)",
                            "contentHtml": "(contentHtml)",
                        },
                        ...
                    ]
                },
                ...
            ]
        }
        ```
        - レスポンス
        ```json
        {
            "isUpdated": "(isUpdated)"
        }

- ## テンプレート新規作成用の情報の取得
    - ### [GET] https://{host}/settings/template
        

        | 変数 |  |
        | :---: | --- |
        

        ---
        - レスポンス
        ```json
        {
            "blockId": "(blockId)",
            "isUserValiable": "(isUserValiable)",
            "blockBaseHtml": "(blockBaseHtml)",
            "isUnique": "(isUnique)",
            "isVariable": "(isVariable)",
            "addHtml": "(addHtml)"
        }
        ```

- ## テンプレート新規作成
    - ### [POST] https://{host}/settings/template
        

        | 変数 |  |
        | :---: | --- |
        

        ---
        - リクエスト
        ```json
        {
            "templateId": "(templateId)",
            "employeeNumber": "(employeeNumber)",
            "templateCreateDate": "(templateCreateDate)",
            "isTemplatePabulish": "(isTemplatePublish)",
            "fixedTagId": "(fixedTagId)",
            "tagId": "(tagId)",
            "tagName": "(tagName)",
            "templateframe" [
                {
                    "templateOrder": "(templateOrder)",
                    "blockId": "(blockId)",
                    "blockBaseHtml": "(blockBaseHtml)",
                    "isUnique": "(isUnique)",
                    "isVariable": "(isVariable)",
                    "addHtml": "(addHtml)",
                    "lineLength": "(lineLength)",
                    "isUserValiable": "(isUserValiable)",
                    "templateContent": [
                        {
                            "lineNumber": "(lineNumber)",
                            "contentHtml": "(contentHtml)",
                        },
                        ...
                    ]
                },
                ...
            ]
        }
        ```
        - レスポンス
        ```json
        {
            "isCreated": "(isCreated)"
        }

- ## ユーザ管理情報の取得
    - ### [GET] https://{host}/settings/users
        

        | 変数 |  |
        | :---: | --- |


        ---
        - レスポンス
        ```json
        {
            "size": "(表示させるテンプレートの数)",

            "userList": [
                {
                    "employeeNumber": "(employeeNumber)",
                    "lastName": "(lastName)",
                    "firstName": "(firstName)",
                    "lastNamePhonetic": "(lastNamePhonetic)",
                    "firstNamePhonetic": "(firstNamePhonetic)",
                    "mailaddress": "(mailaddress)",
                    "isIcon": "(isIcon)",
                    "authorityType": "(authorityType)",
                    "isEnable": "(isEnable)"
                },
                ...
            ]
        }

- ## ユーザ管理情報の更新
    - ### [PUT] https://{host}/settings/users
        

        | 変数 |  |
        | :---: | --- |
        

        ---
        - リクエスト
        ```json
        {
            "userList": [
                {
                    "isEnable": "(isEnable)",
                    "authorityType": "(authorityType)"
                },
                ...
            ]
        }
        ```
        - レスポンス
        ```json
        {
            "isUpdated": "(isUpdated)"
        }

- ## ユーザ新規作成
    - ### [POST] https://{host}/settings/users
        

        | 変数 |  |
        | :---: | --- |
        

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
            "isIcon": "(isIcon)",
            "authorityType": "(authorityType)",
            "isEnable": "(isEnable)",
            "passwordhash": "(passwordhash)"
        }
        ```
        - レスポンス
        ```json
        {
            "isCreated": "(isCreated)"
        }

- ## ユーザ管理情報の取得
    - ### [GET] https://{host}/settings/users
        

        | 変数 |  |
        | :---: | --- |


        ---
        - レスポンス
        ```json
        {
            "size": "(表示させるテンプレートの数)",
            "userList": [
                {
                    "employeeNumber": "(employeeNumber)",
                    "lastName": "(lastName)",
                    "firstName": "(firstName)",
                    "lastNamePhonetic": "(lastNamePhonetic)",
                    "firstNamePhonetic": "(firstNamePhonetic)",
                    "mailaddress": "(mailaddress)",
                    "isIcon": "(isIcon)",
                    "authorityType": "(authorityType)",
                    "isEnable": "(isEnable)"
                },
                ...
            ]
        }

- ## ドキュメント情報の取得
    - ### [GET] https://{host}/documents/{documentId}
        

        | 変数 |  |
        | :---: | --- |
        | documentId | 情報を取得するドキュメント番号 |               

        ---
        - レスポンス
        ```json
        {
            "documentId": "(documentId)",
            "documentTitle": "(documentTitle)",
            "tagId": "(tagId)",
            "tagName": "(tagName)",
            "isFixed": "(isFixed)",
            "contentframe": [
                {
                    "contentOrder": "(contentOrder)",
                    "contentList": [
                        {
                            "lineNumber": "(lineNumber)",
                            "content": "(content)"
                        },
                        ...
                    ]
                },
                ...
            ]
        }
        ```

- ## ドキュメント情報の更新
    - ### [PUT] https://{host}/documents/{documentId}
        update関連は、isDocumentがTrueになって以降に更新されたときに記録されていく

        | 変数 |  |
        | :---: | --- |
        | documentId | 情報を取得するドキュメント番号 |
        

        ---
        - リクエスト
        ```json
        {
            "documentId": "(documentId)",
            "documentTitle": "(documentTitle)",
            "tagId": "(tagId)",
            "tagName": "(tagName)",
            "isFixed": "(isFixed)",
            "isDocument": "(isDocument)",
            "updateType": "(updateType)",
            "updateDate": "(updateDate)",
            "contentframe": [
                {
                    "contentId": "(contentId)",
                    "contentOrder": "(contentOrder)",
                    "contentList": [
                        {
                            "lineNumber": "(lineNumber)",
                            "isTextarea": "(isTextarea)",
                            "content": "(content)"
                        },
                        ...
                    ]
                },
                ...
            ]
        }
        ```
        - レスポンス
        ```json
        {
            "isUpdated": "(isUpdated)"
        }

- ## ドキュメント新規作成用の情報の取得
    - ### [GET] https://{host}/documents
        

        | 変数 |  |
        | :---: | --- |
        

        ---
        - レスポンス
        ```json
        {
            "lastName": "(lastName)",
            "firstName": "(firstName)",
            "date": "(date)"
        }
        ```


- ## ドキュメント新規作成
    - ### [POST] https://{host}/documents
        

        | 変数 |  |
        | :---: | --- |
        

        ---
        - リクエスト
        ```json
        {
            "documentId": "(documentId)",
            "documentTitle": "(documentTitle)",
            "documentCreateDate": "(documentCreateDate)",
            "tagId": "(tagId)",
            "tagName": "(tagName)",
            "isFixed": "(isFixed)",
            "isDocument": "(isDocument)",
            "contentframe": [
                {
                    "contentId": "(contentId)",
                    "contentOrder": "(contentOrder)",
                    "contentList": [
                        {
                            "lineNumber": "(lineNumber)",
                            "isTextarea": "(isTextarea)",
                            "content": "(content)"
                        },
                        ...
                    ]
                },
                ...
            ]
        }
        ```
        - レスポンス
        ```json
        {
            "isCreated": "(isCreated)"
        }

- ## コメント情報の取得
    - ### [GET] https://{host}/documents/{documentId}/comment
        

        | 変数 |  |
        | :---: | --- |
        | documentId | 情報を取得するドキュメント番号 |  


        ---
        - レスポンス
        ```json
        {
            "commentList": [
                {
                    "commentDate": "(commentDate)",
                    "lastName": "(lastName)",
                    "firstName": "(firstName)",
                    "isRead": "(isRead)",
                    "commentContent": "(commentContent)"
                },
                ...
            ]
        }

- ## コメント投稿
    - ### [POST] https://{host}/documents/{documentId}/comment
        

        | 変数 |  |
        | :---: | --- |
        | documentId | 情報を取得するドキュメント番号 |
        

        ---
        - リクエスト
        ```json
        {
            "employeeNumber": "(employeeNumber)",
            "documentId": "(documentId)",
            "commentDate": "(commentDate)",
            "lastName": "(lastName)",
            "firstName": "(firstName)",
            "commentContent": "(commentContent)"
        }
        ```
        - レスポンス
        ```json
        {
            "isCreated": "(isCreated)"
        }

- ## 検索用情報の取得
    - ### [GET] https://{host}/tags/search
        

        | 変数 |  |
        | :---: | --- |
        

        ---
        - レスポンス
        ```json
        {
            "tagName": "(tagName)"
        }