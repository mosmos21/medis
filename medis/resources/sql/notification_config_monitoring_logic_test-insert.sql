DELETE FROM "notification_config";
INSERT INTO "notification_config"
    (employee_number, tag_id, mail_notification, browser_notification)
VALUES
    ('admin', 'n0000000000', 'true', 'true'),
    ('admin', 's0000000000', 'false', 'false'),
    ('admin', 'g0000000000', 'false', 'false'),
    ('g00000', 'n0000000001', 'true', 'false'),
    ('g00000', 's0000000001', 'false', 'true'),
    ('g00000', 'g0000000000', 'false', 'true'),
    ('g00002', 'n0000000001', 'true', 'false'),
    ('g00002', 's0000000001', 'false', 'true'),
    ('g00002', 'g0000000000', 'true', 'false'),
    ('g00003', 'g0000000000', 'false', 'true');