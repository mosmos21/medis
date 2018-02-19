INSERT INTO "user_info"
    (employee_number, authority_id, is_enabled, password)
VALUES
   ('medis', '0', 'true', 'medis'),
   ('gu', '1', 'true', 'gupass'),
   ('99999', '1', 'true', 'gupass99999'),
   ('11111', '1', 'true', 'gupass11111'),
   ('aaaaa', '1', 'true', 'gupassaaaaa');

INSERT INTO "box"
    (box_id, box_logic_name, box_name)
VALUES
    ('0', '', '���{��'),
    ('1', '', '????????e????????'),
    ('2', '', '????^?O??????'),
    ('3', '', '???C?????????'),
    ('4', '', '?e???v???[?g??');

INSERT INTO "toppage"
    (box_id, employee_number, toppage_order)
VALUES
    ('0', 'gu', '0'),
    ('1', 'gu', '3'),
    ('2', 'gu', '1'),
    ('3', 'gu', '2'),
    ('4', 'gu', '4');

INSERT INTO "document_info"
    (document_id, employee_number, template_id, document_create_date, is_document_publish)
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


INSERT INTO template_info
	(template_id, employee_number, is_template_publish, template_create_date, template_name)
VALUES
	('t0000000001', '97968', true, now(), '�e�X�g�e���v���[�g2'),
	('t0000000002', '97968', true, now(), '�e�X�g�e���v���[�g3'),
	('t0000000003', '97968', true, now(), '�e�X�g�e���v���[�g4'),
	('t0000000004', '97968', true, now(), '�e�X�g�e���v���[�g5'),
	('t0000000000', '97968', true, now(), '�e�X�g�e���v���[�g1');