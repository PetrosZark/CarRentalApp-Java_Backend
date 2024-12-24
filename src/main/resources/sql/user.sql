INSERT INTO users (
    id,
    created_at,
    updated_at,
    uuid,
    username,
    password,
    firstname,
    lastname,
    email,
    vat,
    date_of_birth,
    phone,
    gender,
    role,
    is_active
) VALUES (
    1,
    '2024-12-01 00:00:00',
    '2024-12-01 00:00:00',
    'a1b2c3d4-e5f6-7g8h-9i10-jk11lm12n13o',
    'SuperAdmin',
    '$2a$12$IS4eMd5WCaVMRZb07kYlIuJCdOJKUap5NsGOcESbIknrb.C2pFOaa', --'Sa123456!'
    'Super',
    'Admin',
    'super.admin@example.com',
    '012345678',
    '1983-10-12',
    '1234567890',
    'MALE',
    'SUPER_ADMIN',
    b'1'
         );
ALTER TABLE users AUTO_INCREMENT = 2;