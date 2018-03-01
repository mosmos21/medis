DELETE FROM "user_info";
INSERT INTO "user_info"
    (employee_number, authority_id, enabled, password)
VALUES
   ('medis', '0', true, 'medis'),
   ('gu',    '1', true, 'gupass'),
   ('99999', '1', true, 'gupass99999'),
   ('11111', '1', true, 'gupass11111'),
   ('aaaaa', '1', true, 'gupassaaaaa');


DELETE FROM "document_info";
INSERT INTO "document_info"
    (document_id, employee_number, template_id, document_create_date, document_publish)
VALUES
    ('d0000000000', '99999', 't0000000000', '2018/01/01', 'true'),
    ('d0000000001', '99999', 't0000000001', '2018/01/01', 'true'),
    ('d0000000002', '99999', 't0000000002', '2018/01/01', 'true'),
    ('d0000000003', '99999', 't0000000003', '2018/01/01', 'true'),
    ('d0000000004', '99999', 't0000000004', '2018/01/01', 'true'),
    ('d0000000005', '99999', 't0000000005', '2018/01/01', 'false'),
    ('d0000000006', '99999', 't0000000006', '2018/01/01', 'false'),
    ('d0000000007', '99999', 't0000000007', '2018/01/01', 'true'),
    ('d0000000008', '11111', 't0000000008', '2018/01/01', 'true'),
    ('d0000000009', '11111', 't0000000009', '2018/01/01', 'true'),
    ('d0000000010', '11111', 't0000000010', '2018/01/01', 'true'),
    ('d0000000011', '11111', 't0000000011', '2018/01/01', 'true'),
    ('d0000000012', '11111', 't0000000012', '2018/01/01', 'true'),
    ('d0000000013', '11111', 't0000000013', '2018/01/01', 'false'),
    ('d0000000014', '11111', 't0000000014', '2018/01/01', 'false'),
    ('d0000000015', 'aaaaa', 't0000000015', '2018/01/01', 'true');


DELETE FROM "template_info";
INSERT INTO template_info
	(template_id, employee_number, template_publish, template_create_date, template_name)
VALUES
	('t0000000001', '97968', true, now(), 'テストテンプレート2'),
	('t0000000002', '97968', true, now(), 'テストテンプレート3'),
	('t0000000003', '97968', true, now(), 'テストテンプレート4'),
	('t0000000004', '97968', true, now(), 'テストテンプレート5'),
	('t0000000000', '97968', true, now(), 'テストテンプレート1');