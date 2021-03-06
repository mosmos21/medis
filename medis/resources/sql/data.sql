DELETE FROM "authority";
INSERT INTO "authority"
    (authority_id, authority_type)
VALUES
    ('a0000000000', '管理者ユーザ'),
    ('a0000000001', '一般ユーザ');

DELETE FROM "bookmark";

DELETE FROM "comment";

DELETE FROM "document_info";
INSERT INTO "document_info"
    (document_id, document_create_date, document_name, document_publish, employee_number, template_id)
VALUES
    ('d0000000000', '2018-03-15 16:30:39.721', 'ITパスポート⑪まとめテスト', 't', '97958', 't0000000000'),
    ('d0000000001', '2018-03-15 16:33:11.939', 'ITパスポート⑪まとめテスト', 't', '97962', 't0000000000'),
    ('d0000000002', '2018-03-15 16:37:06.673', 'ITパスポート⑪まとめテスト', 't', '97968', 't0000000000'),
    ('d0000000003', '2018-03-15 16:39:32.737', 'ITパスポート⑪まとめテスト', 't', '97966', 't0000000000'),
    ('d0000000004', '2018-03-15 16:40:30.031', 'ITパスポート⑪まとめテスト', 't', '97965', 't0000000000'),
    ('d0000000005', '2018-03-15 16:42:00.719', 'ITパスポート⑪まとめテスト', 't', '97967', 't0000000000'),
    ('d0000000006', '2018-03-15 16:46:42.374', 'A-AUTOプロダクト研修①', 'f', '97962', 't0000000000'),
    ('d0000000007', '2018-03-15 16:51:34.883', 'BIプロダクト研修①', 't', '97958', 't0000000000'),
    ('d0000000008', '2018-03-15 16:54:39.698', 'Be.Cloudプロダクト研修①', 't', '97967', 't0000000000'),
    ('d0000000009', '2018-03-15 16:58:01.58', 'ITSMプロダクト研修①', 'f', '97968', 't0000000000'),
    ('d0000000010', '2018-03-15 16:59:40.257', 'Waha!Transformerプロダクト研修①', 'f', '97966', 't0000000000'),
    ('d0000000011', '2018-03-15 17:02:01.126', '帳票プロダクト研修①', 't', '97965', 't0000000000'),
    ('d0000000012', '2018-03-15 17:07:14.683', 'コーディングに関するアンケート', 'f', '97965', 't0000000001'),
    ('d0000000013', '2018-03-15 17:07:59.92', 'コーディングに関するアンケート', 't', '97966', 't0000000001'),
    ('d0000000014', '2018-03-15 17:08:47.81', 'コーディングに関するアンケート', 'f', '97958', 't0000000001'),
    ('d0000000015', '2018-03-15 17:09:37.615', 'コーディングに関するアンケート', 'f', '97967', 't0000000001'),
    ('d0000000016', '2018-03-15 17:10:13.316', 'コーディングに関するアンケート', 't', '97962', 't0000000001'),
    ('d0000000017', '2018-03-15 17:11:15.718', 'コーディングに関するアンケート', 't', '97968', 't0000000001'),
    ('d0000000022', '2018-03-15 17:27:05.034', 'BIプロダクト研修①', 'f', '97966', 't0000000000'),
    ('d0000000020', '2018-03-15 17:27:23.705', 'Be.Cloudプロダクト研修①', 'f', '97967', 't0000000000'),
    ('d0000000019', '2018-03-15 17:27:46.287', 'A-AUTOプロダクト研修①', 't', '97968', 't0000000000'),
    ('d0000000021', '2018-03-15 17:28:15.386', '帳票プロダクト研修①', 't', '97958', 't0000000000'),
    ('d0000000023', '2018-03-15 17:28:58.007', 'Waha!Transformerプロダクト研修①', 't', '97962', 't0000000000'),
    ('d0000000018', '2018-03-15 17:29:27.97', 'ITSMプロダクト研修①', 'f', '97965', 't0000000000');

DELETE FROM "document_item";
INSERT INTO "document_item"
    (content_order, document_id, line_number, value)
VALUES
    ('1', 'd0000000000', '1', '小テスト、再テスト'),
    ('1', 'd0000000000', '2', 'まとめテスト'),
    ('2', 'd0000000000', '1', '苦手分野'),
    ('2', 'd0000000000', '2', 'これまでの小テストと今回のまとめテストを通じて、システム構成・ネットワークの分野が苦手という事が分かった。アクセスやリクエスト処理などのイメージがつかめていなっかたので、分かる人に教わろうと思う。'),
    ('2', 'd0000000000', '3', '効率の良い勉強方法'),
    ('2', 'd0000000000', '4', '明日の模擬テストのためにシステム構成・ネットワークの分野を中心に勉強し、過去問道場を使って対策を練ろうと思う。'),
    ('2', 'd0000000000', '5', 'OSS'),
    ('2', 'd0000000000', '6', 'OSSはソースコードの改良や再配布は許されているが、元のソフトウェアの著作権は放棄していないという事が分かった。'),
    ('3', 'd0000000000', '1', 'データベースの講義の前に、予習しておけることは何かありますか。'),
    ('1', 'd0000000001', '1', '再テスト'),
    ('1', 'd0000000001', '2', 'まとめテスト'),
    ('2', 'd0000000001', '1', 'テストを終えて'),
    ('2', 'd0000000001', '2', '再テストとまとめテストを行った。再テストでは同期に聞くことで、受ける以前よりも理解度が増したので再テストの機会を活かすことができた。不正解の問題を重点的に確認したが、以前正解していた問題も忘れているものが多かった。細かく覚える前にあと数回は参考書に目を通してから細部まで理解したい。全体を読み終えて、今までは一つの単語で終わっていたが、ほかの章にでる単語と関係のあるものもあった。単語ではイメージがしにくいので、図や絵を用いて関係性のわかるようにしたい。ITパスポートの内容が基本情報技術者試験にも含まれていると聞いたので、高得点をとれるように講義は終わったが毎日少しずつ学びたい。'),
    ('3', 'd0000000001', '1', 'ITパスポートの試験を受けるにあたり、参考書と過去問を解く以外にできることはあるでしょうか。'),
    ('1', 'd0000000002', '1', 'ITパスポートのまとめテストを行った'),
    ('2', 'd0000000002', '1', 'これからについて'),
    ('2', 'd0000000002', '2', 'ITパスポートの日々の小テストについては特に問題がなく終わらせることができた。自分の中で苦手意識があるのはマネジメントや経営戦略等の分野であるが過去問等の演習の繰り返しにより、ある程度の理解をすることができ、テストの点数にもつなげることができた。ただ、現状のままでは一定期間経過した段階で記憶が薄れて理解したものが抜けてしまうと思われるので、ある程度の間隔で復習による振り返りを行い、知識の定着の段階までもっていきたい。今後はデータベースやプログラミング等の実務に直結する研修が始まるため、力を入れていきたい。'),
    ('3', 'd0000000002', '1', 'マネジメントや経営戦略等（パソコン関連とは直接的な関係がないもの）を覚える（＝定着）させるときに実際に触れる等のことができないためなかなか覚えるのが難しく感じてしまいます。うまく覚えるためのコツなどはありますか。'),
    ('1', 'd0000000003', '1', 'ITパスポート(まとめテスト)'),
    ('2', 'd0000000003', '1', 'テストの反省'),
    ('2', 'd0000000003', '2', 'ITパスポートに関する今までの総合テストを行った。合格点は取れたもののPPMやCSFといった経営戦略の手法の名前を間違えてしまった。今回の研修で様々な経営戦略手法の名前を知ることが出来たが、それぞれの手法が頭の中で整理できておらず知識として曖昧な部分があると感じた。間違えてしまった分野の復習を徹底し、こういった暗記系の問題でミスをするといった事を無くしていきたい。'),
    ('3', 'd0000000003', '1', '特にありません'),
    ('1', 'd0000000004', '1', 'まとめテスト'),
    ('2', 'd0000000004', '1', 'まとめテスト'),
    ('2', 'd0000000004', '2', E'本日の研修は、再試験とITパスポートまとめテストを行った。今までのテストでは全て8割以上取れていたので再試験はなかった。しかし、満点を取ったのが1回しかなく、細かい用語が覚えられていないといった弱点が浮かび上がる結果となった。\n\n大学時代は、「なんとなく理解していればいい」くらいの気持ちで勉強していたが、ビジネスの場ではしっかりした知識を付けないと相手と対等に渡り合えない。まずは苦手な単語の暗記をアプリ等を使って行っていきたい。'),
    ('3', 'd0000000004', '1', '特になし。'),
    ('1', 'd0000000005', '1', 'まとめテスト'),
    ('2', 'd0000000005', '1', '弱点'),
    ('2', 'd0000000005', '2', '点数的には目標の9割を取ることが出来ていましたが、用語が覚えられていないことがわかりました。特にハードウェアの内容が弱かったので、パソコンがハード的にどういうしくみで動いているのかを意識しながら学習していきたいと思います。'),
    ('3', 'd0000000005', '1', '単元的にはデータベース、アルゴリズム、経営戦略の分野が勉強していて楽しいと感じました。実際にこの分野が生かせる仕事はありますか。'),
    ('1', 'd0000000006', '1', 'A-AUTOインストール'),
    ('1', 'd0000000006', '2', 'スタートアップガイド'),
    ('2', 'd0000000006', '1', 'インストール'),
    ('2', 'd0000000006', '2', '一つのアプリケーションをインストールするだけで様々な設定や他のインストールが必要なことを改めて知った。それぞれがどのような役割をするためにインストールされているのかは理解できなかった。A-AUTOの機能や役割を十分に理解するために、このような周囲の関係も理解したほうがいいのではないかと思った。しかし、このような知識がなくてもA-AUTOのインストールや操作ができるのは、ITの知識のないお客様目線であると考えられる。'),
    ('2', 'd0000000006', '3', 'A-AUTOの操作性'),
    ('2', 'd0000000006', '4', '操作をしてみてキューイングが成功、失敗を繰り返してしまった。日付を変更すると成功したりしたが、仕組みが理解できなかった。また、属性を各々設定しているはずだが、HやTのマークが意図しない表示をした。具体的には、HOLDにH,Tの両方がついてしまった。その他の設定はマニュアル読みながらできた。今回理解できなかった操作を出来るようにして、一通り操作をして全体のイメージをつかみたい。'),
    ('1', 'd0000000007', '1', 'MyQuery講義'),
    ('1', 'd0000000007', '2', 'MyQuery動作確認'),
    ('2', 'd0000000007', '1', 'sqlserverについて'),
    ('2', 'd0000000007', '2', 'sqlserverのsaユーザーのパスワードを忘れてしまったが、Microsoft SQL Server Management Studioで新しいパスワードを指定してサービスを再起動すればパスワードの変更ができることが分かった。'),
    ('2', 'd0000000007', '3', 'インポートの方法'),
    ('2', 'd0000000007', '4', 'メンバー登録する時は、組織、ユーザーの順番でインポートしなければならない。そのため新しく組織をインポートする際は、ユーザーに変化がなくてももう一度インポートする必要がある。'),
    ('2', 'd0000000007', '5', 'MyQueryの操作性'),
    ('2', 'd0000000007', '6', '新しい抽出条件の作成は操作が分かりやすく使いやすいと感じた。一覧,集計,クロス集計など異なる抽出方法があるのは魅力的だった。「実行時に起動」は使ってみないと動きが読めなかったので、なるべく早く慣れていたい。'),
    ('3', 'd0000000007', '1', '作成した抽出条件のカードの場所が分かり辛いと感じました。ホームは「お気に入り」と「検索」ではなく、「お気に入り」と「一覧」の方が場所が分かりやすいです。'),
    ('1', 'd0000000008', '1', 'Cloud Gear について'),
    ('1', 'd0000000008', '2', 'Cloud Gear ハンズオン'),
    ('2', 'd0000000008', '1', 'Cloud Gearについて'),
    ('2', 'd0000000008', '2', 'Cloud Gear の使い方と仕組みについて学びました。Cloud Gearはサービスマネジメントをする上ではレイアウトもシンプルで扱いやすいと感じました。しかし、その分カスタマイズ性に欠ける部分があり、シンプルとの両立できない部分なので、そこのバランスを取るのが難しいと感じました。グループ研修ではCloud Gearの機能を使ったサービスを考えるというのをやったのですが、なかなか新規性かつインパクトのあるアイデアを考えるのは難しかったです。実際に仕事をする場合にはこういったことを考えることが最も重要だと思うので、普段から周りを気にして自分に出来ることはないかを考えるようにしたいと感じました。'),
    ('3', 'd0000000008', '1', '特にありません。'),
    ('1', 'd0000000009', '1', 'LMIS on cloudの基本的な機能とオペレーション演習'),
    ('2', 'd0000000009', '1', 'LMISについて'),
    ('2', 'd0000000009', '2', '本日からLMIS on cloudの研修がスタートした。基本的な流れは講義で機能や内容の説明を行い実習で実際にインシデント管理や問題管理・ハードウェア管理などの登録を行い、例をもとにフローの流れを確認した。基本的なフローは非常にわかりやすく、それぞれの管理画面から関連情報や図としての関連情報を見ることが出来たのでそれらの点もわかりやすかった。製品の性質上、実際にインデント管理や問題管理を行うタイミングを理解するのが難しかったが、質問をすることで疑問を失くすことが出来た'),
    ('2', 'd0000000009', '3', 'LMISの操作性'),
    ('2', 'd0000000009', '4', '復習の時間に全体を通してオペレーションを行い、基本的な利用方法はマスターできたと感じている。残りはコンフィグの講義と演習課題があるので、引っかかることなく理解を行えるように今日覚えた基本的なことを忘れない様にしたい。'),
    ('3', 'd0000000009', '1', '特になし'),
    ('1', 'd0000000010', '1', 'Waha!Transformer 研修'),
    ('2', 'd0000000010', '1', 'Waha!Transformerについて'),
    ('2', 'd0000000010', '2', 'Waha!Transformerについての研修を行った。基本的な処理である連結や参照などについて理解する事が出来た。今までのプログラミングなどの研修と比べ、操作手順などで覚えるべき項目がたくさんあるので、一つ一つの操作を完璧にし、わからないことがある状態で次に進むような事が無いようにしたい。基本的な部分はわかるようになってきたものの、応用的な処理についてはまだまだ理解が及んでいないと思うので、今後の研修も気を引き締めて受けるようにしたい。'),
    ('1', 'd0000000011', '1', '帳票プロダクト研修'),
    ('1', 'd0000000011', '2', 'DASH!Printer'),
    ('1', 'd0000000011', '3', 'DURL'),
    ('1', 'd0000000011', '4', 'FromHelper'),
    ('1', 'd0000000011', '5', 'BSP-RM'),
    ('1', 'd0000000011', '6', 'XRF Composer'),
    ('1', 'd0000000011', '7', 'XRF Reader'),
    ('2', 'd0000000011', '1', '帳票プロダクトインストール'),
    ('2', 'd0000000011', '2', '本日の研修では、帳票プロダクトのインストールを行った。インストールでは、「BSP-RM Server」のインストールで最も躓いた。java8(jdk1.8.0_131)が入っている状態でインストールを行ったが、エラーメッセージでjre6を求められた。しかし、java6(jre6)を入れてもインストールできずに、結局は配布されたフォルダ内にあったjre8を入れることで解決した。'),
    ('2', 'd0000000011', '3', '気になった点'),
    ('2', 'd0000000011', '4', 'インストール時に気になった点としては、製品毎にインストーラの場所やマニュアルの場所が異なっておりわかりづらかった。昔からの製品なので統一することは難しいだろうが、rootディレクトリにReadMeファイルなどがあると良いと感じた。'),
    ('3', 'd0000000011', '1', ''),
    ('1', 'd0000000012', '1', 'true'),
    ('1', 'd0000000012', '2', 'false'),
    ('1', 'd0000000012', '3', 'false'),
    ('1', 'd0000000012', '4', 'false'),
    ('2', 'd0000000012', '1', 'true'),
    ('2', 'd0000000012', '2', 'true'),
    ('2', 'd0000000012', '3', 'true'),
    ('2', 'd0000000012', '4', 'false'),
    ('2', 'd0000000012', '5', 'true'),
    ('2', 'd0000000012', '6', 'true'),
    ('3', 'd0000000012', '1', 'java'),
    ('3', 'd0000000012', '2', 'iOSアプリ,Androidアプリ'),
    ('1', 'd0000000013', '1', 'false'),
    ('1', 'd0000000013', '2', 'true'),
    ('1', 'd0000000013', '3', 'false'),
    ('1', 'd0000000013', '4', 'false'),
    ('2', 'd0000000013', '1', 'false'),
    ('2', 'd0000000013', '2', 'true'),
    ('2', 'd0000000013', '3', 'false'),
    ('2', 'd0000000013', '4', 'false'),
    ('2', 'd0000000013', '5', 'true'),
    ('2', 'd0000000013', '6', 'false'),
    ('3', 'd0000000013', '1', ''),
    ('3', 'd0000000013', '2', ''),
    ('1', 'd0000000016', '1', 'false'),
    ('1', 'd0000000016', '2', 'true'),
    ('1', 'd0000000016', '3', 'false'),
    ('1', 'd0000000016', '4', 'false'),
    ('2', 'd0000000016', '1', 'false'),
    ('2', 'd0000000016', '2', 'true'),
    ('2', 'd0000000016', '3', 'false'),
    ('2', 'd0000000016', '4', 'false'),
    ('2', 'd0000000016', '5', 'true'),
    ('2', 'd0000000016', '6', 'false'),
    ('3', 'd0000000016', '1', ''),
    ('3', 'd0000000016', '2', ''),
    ('1', 'd0000000014', '1', 'false'),
    ('1', 'd0000000014', '2', 'false'),
    ('1', 'd0000000014', '3', 'false'),
    ('1', 'd0000000014', '4', 'true'),
    ('2', 'd0000000014', '1', 'false'),
    ('2', 'd0000000014', '2', 'true'),
    ('2', 'd0000000014', '3', 'false'),
    ('2', 'd0000000014', '4', 'false'),
    ('2', 'd0000000014', '5', 'false'),
    ('2', 'd0000000014', '6', 'false'),
    ('3', 'd0000000014', '1', ''),
    ('3', 'd0000000014', '2', ''),
    ('1', 'd0000000015', '1', 'true'),
    ('1', 'd0000000015', '2', 'false'),
    ('1', 'd0000000015', '3', 'false'),
    ('1', 'd0000000015', '4', 'false'),
    ('2', 'd0000000015', '1', 'false'),
    ('2', 'd0000000015', '2', 'true'),
    ('2', 'd0000000015', '3', 'false'),
    ('2', 'd0000000015', '4', 'false'),
    ('2', 'd0000000015', '5', 'true'),
    ('2', 'd0000000015', '6', 'true'),
    ('3', 'd0000000015', '1', 'その他'),
    ('3', 'd0000000015', '2', 'チャットアプリ'),
    ('1', 'd0000000017', '1', 'true'),
    ('1', 'd0000000017', '2', 'false'),
    ('1', 'd0000000017', '3', 'false'),
    ('1', 'd0000000017', '4', 'false'),
    ('2', 'd0000000017', '1', 'true'),
    ('2', 'd0000000017', '2', 'true'),
    ('2', 'd0000000017', '3', 'true'),
    ('2', 'd0000000017', '4', 'false'),
    ('2', 'd0000000017', '5', 'true'),
    ('2', 'd0000000017', '6', 'true'),
    ('3', 'd0000000017', '1', 'コーディングスキル'),
    ('3', 'd0000000017', '2', 'AtCoderレート1200'),
    ('1', 'd0000000022', '1', 'BIプロダクト研修'),
    ('2', 'd0000000022', '1', 'BI製品について'),
    ('2', 'd0000000022', '2', 'BIプロダクトについての研修を行った。今回はMyQureyの導入と、基本的な操作方法の研修を行った。データをインポートする際のコマンドで詰まる部分があり、原因は大文字で入力すべきオプションのところを小文字で入力していた事だった。コマンドについては大文字でも小文字でも関係が無いと認識していたので、今回で例外がある事に気づくことができ、改善する事が出来た。ツールについては操作方法がわかりやすく、全体的に円滑に進めることが出来た。'),
    ('3', 'd0000000022', '1', '特になし'),
    ('1', 'd0000000020', '1', 'Cloud Gear について'),
    ('1', 'd0000000020', '2', 'Cloud Gear ハンズオン'),
    ('2', 'd0000000020', '1', 'Cloud Gear の使い方'),
    ('2', 'd0000000020', '2', 'Cloud Gear の使い方と仕組みについて学びました。Cloud Gearはサービスマネジメントをする上ではレイアウトもシンプルで扱いやすいと感じました。しかし、その分カスタマイズ性に欠ける部分があり、シンプルとの両立できない部分なので、そこのバランスを取るのが難しいと感じました。グループ研修ではCloud Gearの機能を使ったサービスを考えるというのをやったのですが、なかなか新規性かつインパクトのあるアイデアを考えるのは難しかったです。実際に仕事をする場合にはこういったことを考えることが最も重要だと思うので、普段から周りを気にして自分に出来ることはないかを考えるようにしたいと感じました。'),
    ('3', 'd0000000020', '1', '特にありません。'),
    ('1', 'd0000000019', '1', 'A-AUTO for Windowsのインストール'),
    ('1', 'd0000000019', '2', 'A-AUTO Serverのインストール'),
    ('2', 'd0000000019', '1', 'A-AUTOについて'),
    ('2', 'd0000000019', '2', 'A-AUTOのインストールを行った。インストールは無事完了し、インストールの完了後はA-AUTOのスタートアップガイドを参考にオペレーションを行った。ネットワークの登録とジョブの登録、実行を行い簡易的な動作の確認を行った。設定する箇所が多くなかなか理解するのは大変だが、少しずつ理解を深めていきたいと思う。今日のオペレーションの中ではジョブの異常終了時の操作が少し理解しにくかったのでもう一度マニュアルを見ながら理解を深めたい。'),
    ('3', 'd0000000019', '1', '特になし'),
    ('1', 'd0000000021', '1', '帳票製品導入'),
    ('1', 'd0000000021', '2', '導入振り返り'),
    ('2', 'd0000000021', '1', '製品の組み合わせ'),
    ('2', 'd0000000021', '2', '帳票製品といってもユニリタには用途によっていくつか製品が分かれているのは知っていたが、製品を組み合わせて使うパターンを学んだ。SmartConductorと帳票DASH!は基本的にペアで使うが、そこにXRFcomposerもセットで使うこともある。'),
    ('2', 'd0000000021', '3', 'exeファイルについて'),
    ('2', 'd0000000021', '4', 'XRFcomposerだけ.exeファイルがなく、DesignerとReaderとインストール方法が違う理由が分からなかったが、composerは元々Linux上で使っていた製品で後からWindows上で起動できるようにした製品であることを学んだ。そして、.exeファイルはWindowsだけのインストーラであることも学んだ。'),
    ('3', 'd0000000021', '1', ''),
    ('1', 'd0000000023', '1', 'プロダクト研修'),
    ('2', 'd0000000023', '1', 'Waha!Transformerについて'),
    ('2', 'd0000000023', '2', E'今日はWaha!Transformerの基本的な使い方について学んだ。入力と出力の仕方はおおまかにわかった。ビューフィルターの種類が多く使い分けるのが困難だと思った。\n\n業務で使われているものを見たが、一回の入力から複数の出力が見られた。どのような違いがあるのか、想像しながら今後取り組みたい。説明を受けながら操作することができたが、一人で出来るかわからないので、復習できるところは復習して、プロダクト研修に苦手意識が芽生えないようにしたい。'),
    ('3', 'd0000000023', '1', 'あああああ'),
    ('1', 'd0000000018', '1', 'ITSM研修'),
    ('1', 'd0000000018', '2', 'LMIS on cloud'),
    ('2', 'd0000000018', '1', 'LMIS on cloud'),
    ('2', 'd0000000018', '2', '本日から、ITSM部の研修が始まった。本日は、LMIS on cloudの基本的な機能について講義をしていただき、実習を挟みながら進めていった。LMIS on cloudは、ITサービスマネジメントのベストプラクティスであるITILを取り入れており、ITILのプロセスに即して管理するツールである。しかし、ただ管理するだけでなく、どう分析し改善していくかがITサービスマネジメントでは重要だということを学んだ。LMIS on cloudはForce.comというSalesforceが提供するクラウドベースの開発プラットフォームの上で動いており、業務プロセスのワークフロー化やナレッジデータベースなど様々な機能があることがわかった'),
    ('3', 'd0000000018', '1', '特になし');

DELETE FROM "document_tag";
INSERT INTO "document_tag"
    (document_id, tag_order, tag_id)
VALUES
    ('d0000000000', '1', 'n0000000002'),
    ('d0000000000', '2', 'n0000000003'),
    ('d0000000000', '3', 's0000000000'),
    ('d0000000001', '1', 'n0000000002'),
    ('d0000000001', '2', 'n0000000003'),
    ('d0000000001', '3', 's0000000001'),
    ('d0000000002', '1', 'n0000000002'),
    ('d0000000002', '2', 'n0000000003'),
    ('d0000000002', '3', 's0000000002'),
    ('d0000000003', '1', 'n0000000002'),
    ('d0000000003', '2', 'n0000000003'),
    ('d0000000003', '3', 's0000000003'),
    ('d0000000004', '1', 's0000000004'),
    ('d0000000005', '1', 'n0000000002'),
    ('d0000000005', '2', 'n0000000003'),
    ('d0000000005', '3', 's0000000005'),
    ('d0000000006', '1', 'n0000000006'),
    ('d0000000006', '2', 'n0000000009'),
    ('d0000000006', '3', 's0000000001'),
    ('d0000000007', '1', 'n0000000006'),
    ('d0000000007', '2', 'n0000000010'),
    ('d0000000007', '3', 's0000000000'),
    ('d0000000008', '1', 'n0000000006'),
    ('d0000000008', '2', 'n0000000011'),
    ('d0000000008', '3', 's0000000005'),
    ('d0000000009', '1', 'n0000000006'),
    ('d0000000009', '2', 'n0000000012'),
    ('d0000000009', '3', 's0000000002'),
    ('d0000000010', '1', 'n0000000006'),
    ('d0000000010', '2', 'n0000000007'),
    ('d0000000010', '3', 's0000000003'),
    ('d0000000011', '1', 'n0000000006'),
    ('d0000000011', '2', 'n0000000013'),
    ('d0000000011', '3', 'n0000000014'),
    ('d0000000011', '4', 's0000000004'),
    ('d0000000012', '1', 'n0000000015'),
    ('d0000000012', '2', 's0000000004'),
    ('d0000000013', '1', 'n0000000015'),
    ('d0000000013', '2', 's0000000003'),
    ('d0000000014', '1', 'n0000000015'),
    ('d0000000014', '2', 's0000000000'),
    ('d0000000015', '1', 'n0000000015'),
    ('d0000000015', '2', 's0000000005'),
    ('d0000000016', '1', 'n0000000015'),
    ('d0000000016', '2', 's0000000001'),
    ('d0000000017', '1', 'n0000000015'),
    ('d0000000017', '2', 's0000000002'),
    ('d0000000022', '1', 'n0000000006'),
    ('d0000000022', '2', 'n0000000010'),
    ('d0000000022', '3', 's0000000003'),
    ('d0000000020', '1', 'n0000000006'),
    ('d0000000020', '2', 'n0000000011'),
    ('d0000000020', '3', 's0000000005'),
    ('d0000000019', '1', 'n0000000006'),
    ('d0000000019', '2', 'n0000000009'),
    ('d0000000019', '3', 's0000000002'),
    ('d0000000021', '1', 'n0000000006'),
    ('d0000000021', '2', 'n0000000013'),
    ('d0000000021', '3', 'n0000000014'),
    ('d0000000021', '4', 's0000000000'),
    ('d0000000023', '1', 'n0000000006'),
    ('d0000000023', '2', 'n0000000007'),
    ('d0000000023', '3', 's0000000001'),
    ('d0000000018', '1', 'n0000000006'),
    ('d0000000018', '2', 'n0000000012'),
    ('d0000000018', '3', 's0000000004');

DELETE FROM "notification_config";
INSERT INTO "notification_config"
    (employee_number, tag_id, mail_notification, browser_notification)
VALUES
    ('g00000', 'g0000000000', 'false', 'false'),
    ('g00000', 'n0000000006', 'false', 'false'),
    ('g00000', 's0000000001', 'false', 'false'),
    ('97958', 'g0000000000', 'false', 'false'),
    ('97962', 'g0000000000', 'false', 'false'),
    ('97965', 'g0000000000', 'false', 'false'),
    ('97966', 'g0000000000', 'false', 'false'),
    ('97967', 'g0000000000', 'false', 'false'),
    ('97968', 'g0000000000', 'false', 'false');

DELETE FROM "tag";
INSERT INTO "tag"
    (tag_id, tag_name)
VALUES
    ('g0000000000', 'コメント通知設定用'),
    ('n0000000001', '2018年度新人研修'),
    ('n0000000002', 'ITパスポート研修'),
    ('n0000000003', '小テスト'),
    ('n0000000004', '技術研修'),
    ('n0000000005', 'アンケート'),
    ('n0000000006', 'プロダクト研修'),
    ('n0000000007', 'Waha!Transformer'),
    ('n0000000008', 'Webアプリ研修'),
    ('s0000000000', '浅野 雅則'),
    ('s0000000001', '加賀谷 優輝'),
    ('s0000000002', '橋本 尚儒'),
    ('s0000000003', '須藤 宏崇'),
    ('s0000000004', '新里 亮太'),
    ('s0000000005', '中川路 玄哉'),
    ('n0000000009', 'A-AUTO'),
    ('n0000000010', 'MyQuery'),
    ('n0000000011', 'Be.Cloud'),
    ('n0000000012', 'LMIS'),
    ('n0000000013', '帳票'),
    ('n0000000014', 'インストール'),
    ('n0000000015', 'コーディングスキル');

DELETE FROM "tempkey_info";

DELETE FROM "template_content";
INSERT INTO "template_content"
    (content_order, template_id, block_id)
VALUES
    (1, 't0000000000', 'b0000000001'),
    (2, 't0000000000', 'b0000000005'),
    (3, 't0000000000', 'b0000000002'),
    (1, 't0000000001', 'b0000000004'),
    (2, 't0000000001', 'b0000000003'),
    (3, 't0000000001', 'b0000000005'),
    (1, 't0000000002', 'b0000000000');

DELETE FROM "template_info";
INSERT INTO "template_info"
    (template_id, employee_number, template_create_date, template_name, template_publish)
VALUES
    ('t0000000000', 'admin', '2018-03-08 09:50:08.802', '日報テンプレートサンプル', true),
    ('t0000000001', 'admin', '2018-03-09 16:39:31.748', 'アンケートテンプレート', true),
    ('t0000000002', 'admin', '2018-03-09 16:39:13.491', '非公開テンプレートサンプル', false);

DELETE FROM "template_item";
INSERT INTO "template_item"
    (content_order, line_number, template_id, value)
VALUES
    (1, 1, 't0000000000', '研修内容'),
    (1, 2, 't0000000000', '今日やったことを箇条書きで記入してください'),
    (2, 1, 't0000000000', '学んだこと'),
    (2, 2, 't0000000000', '今日学んだことを記入してください。'),
    (3, 1, 't0000000000', '困ったこと・相談事項'),
    (3, 2, 't0000000000', '困ったことや相談したいことがある場合は記入してください。'),
    (1, 1, 't0000000001', 'コーディングスキル'),
    (1, 2, 't0000000001', '自身のコーディングスキルについて、以下の中から最も当てはまるものを選択してください'),
    (1, 3, 't0000000001', '人に指導することが出来る'),
    (1, 4, 't0000000001', 'サポートが無くてもコーディングを行うことが出来る'),
    (1, 5, 't0000000001', 'サポートを受けながらコーディングを行うことが出来る'),
    (1, 6, 't0000000001', 'ほとんどわからない'),
    (2, 1, 't0000000001', 'プログラミング言語'),
    (2, 3, 't0000000001', 'C/C++'),
    (2, 4, 't0000000001', 'Java'),
    (2, 5, 't0000000001', 'Python'),
    (2, 6, 't0000000001', 'Haskell'),
    (2, 7, 't0000000001', 'javascript'),
    (2, 8, 't0000000001', 'TypeScript'),
    (3, 1, 't0000000001', 'スキルセット、成果物'),
    (3, 2, 't0000000001', '上記の質問でチェックを付けた言語に対して、具体的なスキルセットや成果物がある場合は記入してください'),
    (1, 1, 't0000000002', '非公開テンプレート'),
    (1, 2, 't0000000002', 'この文章は公開フラグをfalseに設定している為公開されていません。'),
    (2, 2, 't0000000001', E'以下の選択肢の中から自分が使えるものを選択してください。\n(複数選択可)');

DELETE FROM "template_tag";
INSERT INTO "template_tag"
    (tag_order, template_id, tag_id)
VALUES
    (1, 't0000000000', 'n0000000001'),
    (1, 't0000000001', 'n0000000004'),
    (2, 't0000000001', 'n0000000005');

DELETE FROM "update";
INSERT INTO "update"
    (update_type, update_name)
VALUES
    ('v0000000000', '新規作成'),
    ('v0000000001', '更新'),
    ('v0000000002', 'コメント記入'),
    ('v0000000003', '既読');

DELETE FROM "update_info";
INSERT INTO "update_info"
    (update_id, document_id, employee_number, update_date, update_type)
VALUES
    ('u0000000000', 'd0000000000', '97958', '2018-03-15 16:30:39.888', 'v0000000000'),
    ('u0000000001', 'd0000000001', '97962', '2018-03-15 16:33:11.976', 'v0000000000'),
    ('u0000000002', 'd0000000002', '97968', '2018-03-15 16:37:06.7', 'v0000000000'),
    ('u0000000003', 'd0000000003', '97966', '2018-03-15 16:39:32.776', 'v0000000000'),
    ('u0000000004', 'd0000000004', '97965', '2018-03-15 16:40:30.058', 'v0000000000'),
    ('u0000000005', 'd0000000005', '97967', '2018-03-15 16:41:46.387', 'v0000000000'),
    ('u0000000006', 'd0000000005', '97967', '2018-03-15 16:42:00.756', 'v0000000000'),
    ('u0000000007', 'd0000000005', '97967', '2018-03-15 16:42:00.77', 'v0000000001'),
    ('u0000000008', 'd0000000006', '97962', '2018-03-15 16:46:42.412', 'v0000000000'),
    ('u0000000009', 'd0000000007', '97958', '2018-03-15 16:51:34.93', 'v0000000000'),
    ('u0000000010', 'd0000000008', '97967', '2018-03-15 16:54:39.715', 'v0000000000'),
    ('u0000000011', 'd0000000009', '97968', '2018-03-15 16:58:01.616', 'v0000000000'),
    ('u0000000012', 'd0000000010', '97966', '2018-03-15 16:59:40.269', 'v0000000000'),
    ('u0000000013', 'd0000000011', '97965', '2018-03-15 17:02:01.17', 'v0000000000'),
    ('u0000000014', 'd0000000012', '97965', '2018-03-15 17:07:14.744', 'v0000000000'),
    ('u0000000015', 'd0000000013', '97966', '2018-03-15 17:07:59.962', 'v0000000000'),
    ('u0000000016', 'd0000000014', '97958', '2018-03-15 17:08:47.827', 'v0000000000'),
    ('u0000000017', 'd0000000015', '97967', '2018-03-15 17:09:37.663', 'v0000000000'),
    ('u0000000018', 'd0000000016', '97962', '2018-03-15 17:10:13.35', 'v0000000000'),
    ('u0000000019', 'd0000000017', '97968', '2018-03-15 17:11:15.782', 'v0000000000'),
    ('u0000000020', 'd0000000018', '97965', '2018-03-15 17:18:25.727', 'v0000000000'),
    ('u0000000021', 'd0000000019', '97968', '2018-03-15 17:19:48.568', 'v0000000000'),
    ('u0000000022', 'd0000000020', '97967', '2018-03-15 17:21:14.149', 'v0000000000'),
    ('u0000000023', 'd0000000021', '97958', '2018-03-15 17:22:58.594', 'v0000000000'),
    ('u0000000024', 'd0000000022', '97966', '2018-03-15 17:25:03.314', 'v0000000000'),
    ('u0000000025', 'd0000000023', '97962', '2018-03-15 17:26:19.616', 'v0000000000'),
    ('u0000000026', 'd0000000022', '97966', '2018-03-15 17:27:05.058', 'v0000000000'),
    ('u0000000027', 'd0000000022', '97966', '2018-03-15 17:27:05.069', 'v0000000001'),
    ('u0000000028', 'd0000000020', '97967', '2018-03-15 17:27:23.728', 'v0000000000'),
    ('u0000000029', 'd0000000020', '97967', '2018-03-15 17:27:23.733', 'v0000000001'),
    ('u0000000030', 'd0000000019', '97968', '2018-03-15 17:27:46.32', 'v0000000000'),
    ('u0000000031', 'd0000000019', '97968', '2018-03-15 17:27:46.325', 'v0000000001'),
    ('u0000000032', 'd0000000021', '97958', '2018-03-15 17:28:15.415', 'v0000000000'),
    ('u0000000033', 'd0000000021', '97958', '2018-03-15 17:28:15.419', 'v0000000001'),
    ('u0000000034', 'd0000000023', '97962', '2018-03-15 17:28:58.042', 'v0000000000'),
    ('u0000000035', 'd0000000023', '97962', '2018-03-15 17:28:58.047', 'v0000000001'),
    ('u0000000036', 'd0000000018', '97965', '2018-03-15 17:29:27.987', 'v0000000000'),
    ('u0000000037', 'd0000000018', '97965', '2018-03-15 17:29:27.991', 'v0000000001');

DELETE FROM "user_info";
INSERT INTO "user_info"
    (employee_number, authority_id, enabled, password)
VALUES
    ('admin', 'a0000000000', true, '$2a$10$hpwdIdUJsispkI0fnMlXKeWGLdD3TgPydT6xVM18R2ZDdm5fJGSKe'),
    ('g00000', 'a0000000001', true, '$2a$10$6NEsyDGiTPwpDlM49ySKOesITMTOJlIC04Ca2Eshh7rA5zWDvhJj2'),
    ('97958', 'a0000000001', true, '$2a$10$3/3zAAbnIlFv/OnXnXCi0OTT42lOv3XEtOwo5ZvSiozaEfDFp.me6'),
    ('97965', 'a0000000001', true, '$2a$10$hHTigoEvsfRj4KDhq8F.o.1UTUUC//2tY9uObFXyNASE5MrHsXPdK'),
    ('97966', 'a0000000001', true, '$2a$10$yJm/uuHY9oV2hYQ89R1g6esVZo9JJTa/8MwKpFJgHqAAIDlEIZoxK'),
    ('97967', 'a0000000001', true, '$2a$10$/Cf/SJ1P/2db4UJNm9cD8.PyV528NLFVYlDKeStjS4tWqZCJAd9ti'),
    ('97968', 'a0000000001', true, '$2a$10$BnLAYTMkSO4Hk2Wg74S9IuypDy4B1PEKDo2aQJM11CLtkCcPpNaBy'),
    ('97962', 'a0000000001', true, '$2a$10$GFoPnfFZE1Ft7U2Gs/NfhOgVRohtrArhzvvshPcwy6Fx4vESsi.KC');

DELETE FROM "user_detail";
INSERT INTO "user_detail"
    (employee_number, first_name, first_name_phonetic, icon, last_name, last_name_phonetic, mailaddress)
VALUES
    ('admin', '者', 'シャ', false, '管理', 'カンリ', 'medis.masa0@gmail.com'),
    ('g00000', '太郎', 'タロウ', false, 'ユニリタ', 'ユニリタ', 'medis.masa0@gmail.com'),
    ('97968', '尚儒', 'タカヒト', false, '橋本', 'ハシモト', 'takahito_hashimoto@unirita.co.jp'),
    ('97958', '雅則', 'マサノリ', false, '浅野', 'アサノ', 'masanori_asano@unirita.co.jp'),
    ('97965', '亮太', 'リョウタ', false, '新里', 'シンザト', 'ryota_shinzato@unirita.co.jp'),
    ('97966', '宏崇', 'ヒロタカ', false, '須藤', 'スドウ', 'hirotaka_sudo@unirita.co.jp'),
    ('97967', '玄哉', 'ヒロヤ', false, '中川路', 'ナカカワジ', 'hiroya_nakakawaji@unirita.co.jp'),
    ('97962', '優輝', 'ユウキ', false, '加賀谷', 'カガヤ', 'yuuki_kagaya@unirita.co.jp');