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
        [
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
        | type | publish : 公開済み文書<br>private : 下書き文書 |
        | size | 新しいものから何件まで取得するか指定 |

        ---
        - レスポンス
        ```json
        [
            {
                "id": "(documentId)",
                "title": "(documentTitle)",
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
        [
            {
                "id": "(documentId)",
                "title": "(documentTitle)",
                "employeeNumber": "(employeeNumber)",
                "createDate": "(createDate)"
            },
            ...
        ]
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
        [
            {
                "id": "(documentId)",
                "title": "(documentTitle)",
                "employeeNumber": "(employeeNumber)",
                "createDate": "(createDate)"
            },
            ...
        ]
        ```

- ## パスワード再設定用のデータの確認結果の取得
    - ### [POST] https://{host}/accounts/usercheck
        パスワード再設定時にユーザが入力する社員番号とメールアドレスが存在するか、またデータが一致するかをチェックし、番号で返す<br>
        問題なかった場合、一時キーの生成とメールを送る処理をおこなうロジックを動かす

        | 変数 |  |
        | :---: | --- |
        
        - リクエスト
        ```json
        {
            "employeeNumber": "(employeeNumber)",
            "mailaddress": "(mailaddress)"
        }

        ---
        - レスポンス
        ```json
        {
            "result": "(result)",
            "message": "(message)"
        }
        ```

- ## 一時キーの整合性、使用期限チェックの確認結果の取得
    - ### [POST] https://{host}/accounts/keycheck
        送られてきたパスワード再設定用のメールにあるURLに飛んだ際に、一時キーと社員番号の整合性と、一時キーの有効期限のチェックの結果を返す

        | 変数 |  |
        | :---: | --- |
        
        - リクエスト
        ```json
        {
            "tempKey": "(tempKey)"
        }

        ---
        - レスポンス
        ```json
        {
            "result": "(result)",
            "message": "(message)"
        }
        ```

- ## パスワードの更新
    - ### [POST] https://{host}/accounts/reset
        パスワードを再設定した際の結果を返す

        | 変数 |  |
        | :---: | --- |

        
        ---
        - リクエスト
        ```json
        {
            "employeeNumber": "(employeeNumber)",
            "mailaddress": "(mailaddress)",
            "password": "(password)"
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
        「設定」のユーザ情報を表示させる
        

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
    - ### [POST] https://{host}/settings/me
        「設定」のユーザ情報を更新する時に使用
        

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
        「設定」のトップページ設定の情報の取得時に使用
        

        | 変数 |  |
        | :---: | --- |
        

        ---
        - レスポンス
        ```json
        [
            {
                "boxId": "(boxId)",
                "toppageOrder": "(toppageOrder)",
                "boxName": "(boxName)"
            },
            ...
        ]
        ```

- ## トップページ設定情報の更新
    - ### [POST] https://{host}/settings/me/toppage
        「設定」のトップページ設定の更新を行うときに使用
        

        | 変数 |  |
        | :---: | --- |
        

        ---
        - リクエスト
        ```json
        [
            {
                "boxId": "(boxId)",
                "toppageOrder": "(toppageOrder)"
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
        「設定」の監視タグの現在選択しているタグ一覧を表示させる
        

        | 変数 |  |
        | :---: | --- |
        

        ---
        - レスポンス
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
    - ### [POST] https://{host}/settings/me/monitoring_tag
        「設定」の監視タグ一覧を更新する時に使用
        

        | 変数 |  |
        | :---: | --- |
        

        ---
        - リクエスト
        ```json
        [
            {
                "tagId": "(tagId)"
            },
            ...
        ]
        ```
        - レスポンス
        ```json
        {
            "isUpdated": "(isUpdated)"
        }

- ## 通知設定情報の取得
    - ### [GET] https://{host}/settings/me/notification
        「設定」の通知設定の現在の情報を取得

        | 変数 |  |
        | :---: | --- |
        

        ---
        - レスポンス
        ```json
        [
            {
                "tagId": "(tagId)",
                "tagName": "(tagName)",
                "isMailNotification": "(isMailNotification)",
                "isBrowserNotification": "(isBrowserNotification)"
            },
            ...
        ]
        ```

- ## 通知設定情報の更新
    - ### [POST] https://{host}/settings/me/notification
        「設定」の通知設定を更新する時に使用
        

        | 変数 |  |
        | :---: | --- |
        

        ---
        - リクエスト
        ```json
        [
            {
                "tagId": "(tagId)",
                "isMailNotification": "(isMailNotification)",
                "isBrowserNotification": "(isBrowserNotification)"
            },
            ...
        ]
        ```
        - レスポンス
        ```json
        {
            "isUpdated": "(isUpdated)"
        }

- ## テンプレート一覧情報の取得
    - ### [GET] https://{host}/templateList
    - ### [GET] https://{host}/settings/templateList/publish
    - ### [GET] https://{host}/settings/templateList/private
        管理者によるテンプレート編集、文書作成時のテンプレート選択時に使用する
        

        | 変数 |  |
        | :---: | --- |

        

        ---
        - レスポンス
        ```json
        [
            {
                "templateId": "(templateId)",
                "templateName": "(templateName)",
                "templateCreateDate": "(templateCreateDate)",
                "employeeNumber": "(employeeNumber)",
                "isTemplatePublish": "(isTemplatePublish)"
            },
            ...
        ]
        ```

- ## テンプレートベース情報の取得
    - ### [GET] https://{host}/template/bases
        テンプレートを作るベースの情報で、テンプレート作成、テンプレート編集、文書作成、文書編集時に使用する
        

        | 変数 |  |
        | :---: | --- |


        ---
        - レスポンス
        ```json
        {
            "templateId": "(templateId)",
            "templatebase" [
                {
                    "templateOrder": "(templateOrder)",
                    "blockId": "(blockId)",
                    "blockBaseHtml": "(blockBaseHtml)",
                    "isRequired": "(isRequired)",
                    "isVariable": "(isVariable)",
                    "addHtml": "(addHtml)",
                    "blockBaseInfo": "(blockBaseInfo)"
                },
                ...
            ]
        }
        ```

- ## テンプレートコンテント情報の取得
    - ### [GET] https://{host}/template/contents
        テンプレートを作る内容の情報で、テンプレート作成、テンプレート編集、文書作成、文書編集時に使用する

        | 変数 |  |
        | :---: | --- |


        ---
        - レスポンス
        ```json
        {
            "templateId": "(templateId)",
            "templateContent" [
                {
                    "templateOrder": "(templateOrder)",
                    "lineLength": "(lineLength)",
                    "isUserValiable": "(isUserValiable)",
                    "eachOrderContent": [
                        {
                            "lineNumber": "(lineNumber)",
                            "contentHtml": "(contentHtml)"
                        },
                        ...
                    ]
                },
                ...
            ]
        }
        ```

- ## テンプレートコンテント情報の更新
    - ### [POST] https://{host}/template/{templateId}
        テンプレート内容の更新用で、テンプレート編集、文章編集時に使用   

        | 変数 |  |
        | :---: | --- |
        | templateId | 情報を取得するテンプレート番号 |


        ---
        - リクエスト
        ```json
        {
            "templateId": "(templateId)",
            "isTemplatePabulish": "(isTemplatePublish)",
            "templateframe" [
                {
                    "templateOrder": "(templateOrder)",
                    "lineLength": "(lineLength)",
                    "isUserValiable": "(isUserValiable)",
                    "eachOrderContent": [
                        {
                            "lineNumber": "(lineNumber)",
                            "contentHtml": "(contentHtml)"
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


- ## テンプレート新規作成
    - ### [POST] https://{host}/template/new
        テンプレート新規作成時に使用

        

        | 変数 |  |
        | :---: | --- |
        

        ---
        - リクエスト
        ```json
        {
            "isTemplatePabulish": "(isTemplatePublish)",
            "templateframe" [
                {
                    "templateOrder": "(templateOrder)",
                    "blockId": "(blockId)",
                    "lineLength": "(lineLength)",
                    "isUserValiable": "(isUserValiable)",
                    "templateContent": [
                        {
                            "lineNumber": "(lineNumber)",
                            "contentHtml": "(contentHtml)"
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
    - ### [GET] https://{host}/system/users
        管理者がユーザの設定画面を表示するための情報を取得する時に使用
        

        | 変数 |  |
        | :---: | --- |


        ---
        - レスポンス
        ```json
        [
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

- ## ユーザ管理情報の更新
    - ### [POST] https://{host}/system/users/update
        管理者がユーザの情報を変更する時に使用

        | 変数 |  |
        | :---: | --- |
        

        ---
        - リクエスト
        ```json
        [
            {
                "employeeNumber": "(employeeNumber)",
                "isEnable": "(isEnable)",
                "authorityType": "(authorityType)"
            },
            ...
        ]
        ```
        - レスポンス
        ```json
        {
            "isUpdated": "(isUpdated)"
        }

- ## ユーザ新規作成
    - ### [POST] https://{host}/system/users/signup
        管理者が一版ユーザを作成する時に使用
        

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
            "password": "(password)"
        }
        ```
        - レスポンス
        ```json
        {
            "isCreated": "(isCreated)"
        }


- ## ドキュメント情報の取得
    - ### [GET] https://{host}/documents/{documentId}
        ユーザがドキュメントを開くときに使用する<br>
        使用場面として、文章編集時、文章閲覧時がある
        

        | 変数 |  |
        | :---: | --- |
        | documentId | 情報を取得するドキュメント番号 |               

        ---
        - レスポンス
        ```json
        [
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
        ```

- ## ドキュメント情報の更新
    - ### [POST] https://{host}/documents/{documentId}/update
        ユーザがドキュメントを編集し、更新する時に使用<bk>
        update関連は、isDocumentがTrueになって以降に更新されたときに記録されていく


        | 変数 |  |
        | :---: | --- |
        | documentId | 情報を取得するドキュメント番号 |
        

        ---
        - リクエスト
        ```json
        {
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

- ## ドキュメント新規作成
    - ### [POST] https://{host}/documents/new
        ユーザがドキュメントを新規作成する時に使用
        

        | 変数 |  |
        | :---: | --- |
        

        ---
        - リクエスト
        ```json
        {

            "templateId": "(templateId)",
            "isDocumentPublish": "(isDocumentPublish)",
            "contents": [
                {
                    "contentOrder": "(contentOrder)",
                    "contentsList": [
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
    - ### [GET] https://{host}/documents/{documentId}/comments
        ユーザが文章を開いたときに、コメントを表示させるために使用
        

        | 変数 |  |
        | :---: | --- |
        | documentId | 情報を取得するドキュメント番号 |  


        ---
        - レスポンス
        ```json
        [
            {
                "commentDate": "(commentDate)",
                "isIcon": "(isIcon)",
                "lastName": "(lastName)",
                "firstName": "(firstName)",
                "isRead": "(isRead)",
                "commentContent": "(commentContent)"
            },
            ...
        ]

- ## コメント投稿
    - ### [POST] https://{host}/documents/{documentId}/comments/new
        ユーザが新しくコメントを投稿する時に使用
        

        | 変数 |  |
        | :---: | --- |
        | documentId | 情報を取得するドキュメント番号 |
        

        ---
        - リクエスト
        ```json
        {
            "commentContent": "(commentContent)"
        }
        ```
        - レスポンス
        ```json
        {
            "isCreated": "(isCreated)"
        }

- ## 検索用情報の取得
    - ### [GET] https://{host}/documents/search
                検索をかける時のために使用
        

        | 変数 |  |
        | :---: | --- |
        

        ---
        - レスポンス
        ```json
        {
            "tagId": "(tagId)",
            "tagName": "(tagName)"
        }

- ## 検索結果の取得
    - ### [POST] https://{host}/documents/search
        検索するタグを記入し、検索を実行する時に使用

        | 変数 |  |
        | :---: | --- |
        

        ---
        - リクエスト
        ```json
        [
            {
                "tagId": "(tagId)"
            },
            ...
        ]
        ```
        - レスポンス
        ```json
        [
            {
                "documentId": "(documentId)",
                "documentTitle": "(documentTitle)",
                "employeeNumber": "(employeeNumber)",
                "createDate": "(createDate)",
                "isDocument": "(isDocument)"
            },
            ...
        ]

- ## タグ情報の取得
    - ### [GET] https://{host}/tags
        テンプレートや文章のタグ情報を取得する時に使用
        

        | 変数 |  |
        | :---: | --- |  


        ---
        - レスポンス
        ```json
        [
            {
                "tagId": "(tagId)",
                "fixedTagId": "(fixedTagId)",
                "tagName": "(tagName)",
                "isFixed": "(isFixed)"
            },
            ...
        ]

- ## タグ情報の更新
    - ### [POST] https://{host}/tags/update
        テンプレート編集や、ドキュメント編集をしたときに、タグ情報を更新するために使用
        

        | 変数 |  |
        | :---: | --- |
        

        ---
        - リクエスト
        ```json
        [
            {
                "tagName": "(tagName)"
            },
            ...
        ]
        ```
        - レスポンス
        ```json
        {
            "isUpdated": "(isUpdated)"
        }