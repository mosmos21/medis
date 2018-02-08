# モックサーバ

参考サイトは[ここ](https://qiita.com/arenahito/items/75e59b921497f5427b69)

- mock/user以下にあるものがinitで作られたサンプルのもの
- ポート番号を8080に変更済み
- mock/login以下にあるものがテストで作成したもの


## コマンドラインから動作確認をする場合の例

- ### GETの場合
    ```
    curl http://localhost:8080/documents
    ```

- ### POSTの場合
    ```
    curl -X POST http://localhost:8080/mock/login -d "employeeNumber=99999&password=hoge"
    ```

