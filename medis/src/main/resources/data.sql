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