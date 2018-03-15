DELETE FROM "user_detail";
INSERT INTO "user_detail"
    (employee_number, first_name, first_name_phonetic, icon, last_name, last_name_phonetic, mailaddress)
VALUES
    ('medis', 'med', 'めでぃ', true, 'is', 'す', 'test@hoge.jp'),
    ('gu', 'g', 'ぐ', true, 'u', 'ぐ', 'test@hoge.jp'),
    ('test', 'te', 'て', false, 'st', 'すと', 'medis.masa0@gmail.com'),
    ('admin', '者', 'しゃ', false, '管理', 'かんり', 'medis.masa0@gmail.com'),
    ('g00000', '太郎', 'たろう', false, 'ユニリタ', 'ゆにりた', 'medis.masa0@gmail.com'),
    ('97968', '尚儒', 'たかひと', false, '橋本', 'はしもと', 'takahito_hashimoto@unirita.co.jp'),
    ('97958', '雅則', 'まさのり', false, '浅野', 'あさの', 'masanori_asano@unirita.co.jp'),
    ('97965', '亮太', 'りょうた', false, '新里', 'しんざと', 'ryota_shinzato@unirita.co.jp'),
    ('97966', '宏崇', 'ひろたか', false, '須藤', 'すどう', 'hirotaka_sudo@unirita.co.jp'),
    ('97967', '玄哉', 'ひろや', false, '中川路', 'なかかわじ', 'hiroya_nakakawaji@unirita.co.jp'),
    ('97962', '優紀', 'ゆうき', false, '加賀谷', 'かがや', 'yuuki_kagaya@unirita.co.jp'),
    ('99999', 'サンプル', 'さんぷる', true, 'ユーザ', 'ゆーざ', 'sample@hoge.jp');