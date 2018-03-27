DELETE FROM "user_detail";
INSERT INTO "user_detail"
    (employee_number, first_name, first_name_phonetic, icon, last_name, last_name_phonetic, mailaddress)
VALUES
    ('99999', '999', '����', true, '99', '���イ���イ', '99999@hoge.jp'),
    ('97970', '太郎', 'たろう', false, '田中', 'たなか', 'tarou@tanaka.co.jp');