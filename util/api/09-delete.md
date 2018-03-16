- ## 文書の削除
    - ### [DELETE] https://{hostname}/{version}/documents/{documentId}
        ユーザが自分の作成した文書を削除する時に使用
        

        | パラメータ | 省略 |  |  
        | :---: | :---: | --- |
        | documentId | x | ドキュメントID |   


        ---
        - 成功レスポンス(204 No Content)
        - 失敗レスポンス(409 Conflict)


- ## テンプレートの削除
    - ### [DELETE] https://{hostname}/{version}/templates/{templateId}
        使用されていないテンプレートを削除する時に使用
        

        | パラメータ | 省略 |  |  
        | :---: | :---: | --- |
        | templateId | x | テンプレートID |   


        ---
        - 成功レスポンス(204 No Content)
        - 失敗レスポンス(409 Conflict)
        