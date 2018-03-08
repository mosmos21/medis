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
    ('g00000', 'a0000000001', true, '$2a$10$6NEsyDGiTPwpDlM49ySKOesITMTOJlIC04Ca2Eshh7rA5zWDvhJj2');


DELETE FROM "user_detail";
INSERT INTO "user_detail"
    (employee_number, first_name, first_name_phonetic, icon, last_name, last_name_phonetic, mailaddress)
VALUES
    ('admin', '者', 'しゃ', false, '管理', 'かんり', 'medis.masa0@gmail.com'),
    ('g00000', '太郎', 'たろう', false, 'ユニリタ', 'ゆにりた', 'medis.masa0@gmail.com');


DELETE FROM "tag";
INSERT INTO "tag"
    (tag_id, tag_name)
VALUES
    ('g0000000000', 'コメント通知設定用');


DELETE FROM "template_info";
INSERT INTO "template_info"
    (template_id, employee_number, template_create_date, template_name, template_publish)
VALUES
    ('t0000000000', 'admin', '2018-03-08 09:50:08.802', '日報テンプレートサンプル', true);


DELETE FROM "template_content";
INSERT INTO "template_content"
    (content_order, template_id, block_id)
VALUES
    (1, 't0000000000', 'b0000000001'),
    (2, 't0000000000', 'b0000000005'),
    (3, 't0000000000', 'b0000000002');


DELETE FROM "template_item";
INSERT INTO "template_item"
    (content_order, line_number, template_id, value)
VALUES
    (1, 1, 't0000000000', '研修内容'),
    (1, 2, 't0000000000', '以下の欄に今日やったことを箇条書きで記入してください'),
    (2, 1, 't0000000000', '学んだこと'),
    (2, 2, 't0000000000', '今日学んだことを記入してください。'),
    (3, 1, 't0000000000', '困ったこと・相談事項'),
    (3, 2, 't0000000000', '困ったことや相談したいことがある場合は記入してください。');

DELETE FROM "document_info";
INSERT INTO "document_info"
    (document_id, document_create_date, document_name, document_publish, employee_number, template_id)
VALUES
    ('d0000000000', '2018-03-08 09:48:35.899', '技術研修(ITパスポート)', true, 'g00000', 't0000000000');


DELETE FROM "document_item";
INSERT INTO "document_item"
    (content_order, document_id, line_number, value)
VALUES
    (1, 'd0000000000', 1, '小テスト、再テスト'),
    (1, 'd0000000000', 2, 'まとめテスト'),
    (2, 'd0000000000', 1, 'その１'),
    (2, 'd0000000000', 2, 'これまでの小テストと今回のまとめテストを通じて、システム構成・ネットワークの分野が苦手という事が分かった。アクセスやリクエスト処理などのイメージがつかめていなっかたので、分かる人に教わろうと思う。'),
    (2, 'd0000000000', 3, 'その2'),
    (2, 'd0000000000', 4, '明日の模擬テストのためにシステム構成・ネットワークの分野を中心に勉強し、過去問道場を使って対策を練ろうと思う。'),
    (2, 'd0000000000', 5, 'その３'),
    (2, 'd0000000000', 6, 'OSSはソースコードの改良や再配布は許されているが、元のソフトウェアの著作権は放棄していないという事が分かった'),
    (3, 'd0000000000', 1, 'データベースの講義の前に、予習しておけることは何かありますか。');


DELETE FROM "update_info";
INSERT INTO "update_info"
    (update_id, document_id, employee_number, update_date, update_type)
VALUES
    ('u0000000000', 'd0000000000', 'g00000', '2018-03-08 09:48:35.966', 'v0000000000');