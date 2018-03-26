DELETE FROM "document_info";
INSERT INTO "document_info"
    (document_id, employee_number, template_id, document_create_date, document_publish,document_name)
VALUES
    ('d0000000000', '99999', 't0000000000', '2018/01/01', 'true','講義について'),
    ('d0000000001', '99999', 't0000000001', '2018/01/02', 'false','朝礼について'),
    ('d0000000002', 'g00001', 't0000000010', '2018/01/01', 'true','労働規則について'),
    ('d0000000003', '99999', 't0000000010', '2018/01/01', 'false','アイパスについて');