DELETE FROM "template_info";
INSERT INTO "template_info"
    (template_id, employee_number, template_name, template_create_date, template_publish)
VALUES
    ('t0000000000', '99999', 'sample template info No.1',  '2018-03-01 00:00:00', true ),
    ('t0000000001', '99999', 'sample template info No.2',  '2018-03-01 00:00:00', true ),
    ('t0000000002', '99999', 'sample template info No.3',  '2018-03-01 00:00:00', true ),
    ('t0000000003', '99999', 'sample template info No.4',  '2018-03-01 00:00:00', false),
    ('t0000000004', '99999', 'sample template info No.5',  '2018-03-01 00:00:00', false),
    ('t0000000005', '99999', 'sample template info No.6',  '2018-03-01 00:00:00', false),
    ('t0000000006', '88888', 'sample template info No.7',  '2018-03-01 00:00:00', true ),
    ('t0000000007', '88888', 'sample template info No.8',  '2018-03-01 00:00:00', true ),
    ('t0000000008', '88888', 'sample template info No.9',  '2018-03-01 00:00:00', false),
    ('t0000000009', '88888', 'sample template info No.10', '2018-03-01 00:00:00', false);