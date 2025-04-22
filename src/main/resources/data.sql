INSERT INTO tbl_app_user (
    first_name, last_name, email, password, role, enabled, created_by, created_at
) VALUES
      ('Admin', 'Admin', 'admin@portal.com', 'password123', 'ADMIN', 1, 'system', NOW()),
      ('Jane', 'Smith', 'jane.smith@example.com', 'password456', 'STUDENT', 1, 'system', NOW()),
      ('Alice', 'Johnson', 'alice.johnson@example.com', 'alicePass789', 'STUDENT', 1, 'admin', NOW()),
      ('Bob', 'Williams', 'bob.williams@example.com', 'bobSecure321', 'STUDENT', 1, 'system', NOW());
