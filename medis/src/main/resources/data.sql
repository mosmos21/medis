DELETE FROM "authority";
INSERT INTO "authority"
    (authority_id, authority_type)
VALUES
    ('a0000000000', '管理者ユーザ'),
    ('a0000000001', '一般ユーザ');


DELETE FROM "user_info";
INSERT INTO "user_info"
    (employee_number, authority_id, enabled, password)
VALUES
    ('admin',  'a0000000000', true, '$2a$10$hpwdIdUJsispkI0fnMlXKeWGLdD3TgPydT6xVM18R2ZDdm5fJGSKe'),
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
    ('admin', '者', 'しゃ', false, '管理', 'かんり', 'medis.masa0@gmail.com'),
    ('g00000', '太郎', 'たろう', false, 'ユニリタ', 'ゆにりた', 'medis.masa0@gmail.com'),
    ('97968', '尚儒', 'たかひと', false, '橋本', 'はしもと', 'takahito_hashimoto@unirita.co.jp'),
    ('97958', '雅則', 'まさのり', false, '浅野', 'あさの', 'masanori_asano@unirita.co.jp'),
    ('97965', '亮太', 'りょうた', false, '新里', 'しんざと', 'ryota_shinzato@unirita.co.jp'),
    ('97966', '宏崇', 'ひろたか', false, '須藤', 'すどう', 'hirotaka_sudo@unirita.co.jp'),
    ('97967', '玄哉', 'ひろや', false, '中川路', 'なかかわじ', 'hiroya_nakakawaji@unirita.co.jp'),
    ('97962', '優紀', 'ゆうき', false, '加賀谷', 'かがや', 'yuuki_kagaya@unirita.co.jp');


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
    ('n0000000006', 'コーディングスキル');


DELETE FROM "template_info";
INSERT INTO "template_info"
    (template_id, employee_number, template_create_date, template_name, template_publish)
VALUES
    ('t0000000000', 'admin', '2018-03-08 09:50:08.802', '日報テンプレートサンプル', true),
    ('t0000000001', 'admin', '2018-03-09 16:39:31.748', 'アンケートテンプレート', true),
    ('t0000000002', 'admin', '2018-03-09 16:39:13.491', '非公開テンプレートサンプル', false);

DELETE FROM "template_tag";
INSERT INTO "template_tag"
    (tag_order, template_id, tag_id)
VALUES
    (1, 't0000000000', 'n0000000001'),
    (2, 't0000000000', 'n0000000002'),
    (1, 't0000000001', 'n0000000004'),
    (2, 't0000000001', 'n0000000005');

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


DELETE FROM "template_item";
INSERT INTO "template_item"
    (content_order, line_number, template_id, value)
VALUES
    (1, 1, 't0000000000', '研修内容'),
    (1, 2, 't0000000000', '以下の欄に今日やったことを箇条書きで記入してください'),
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
    (2, 2, 't0000000001', '以下の選択肢の中から自分が使えるものを選択してください。\n(複数選択可)');

DELETE FROM "update_info";
INSERT INTO "update_info"
    (update_id, document_id, employee_number, update_date, update_type)
VALUES
    ('u0000000000', 'd0000000000', 'g00000', '2018-03-08 09:48:35.966', 'v0000000000');


DELETE FROM "notification_config";
INSERT INTO "notification_config"
    (employee_number, tag_id, mail_notification, browser_notification)
VALUES
    ('g00000', 'g0000000000', 'false', 'false'),
    ('97958', 'g0000000000', 'false', 'false'),
    ('97962', 'g0000000000', 'false', 'false'),
    ('97965', 'g0000000000', 'false', 'false'),
    ('97966', 'g0000000000', 'false', 'false'),
    ('97967', 'g0000000000', 'false', 'false'),
    ('97968', 'g0000000000', 'false', 'false');