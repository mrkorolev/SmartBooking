DROP TABLE IF EXISTS appointments;
DROP TABLE IF EXISTS users_roles;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    user_id bigserial PRIMARY KEY,
    f_name varchar(70) NOT NULL,            -- to save the BCrypto result length (> 50 characters)
    l_name varchar(70) NOT NULL,
    email text UNIQUE NOT NULL,
    username varchar(50) NOT NULL,
    password varchar(70) NOT NULL,          -- to be hashed, what else to hash will be requirements elicitation !
    dt_created timestamp NOT NULL,
    is_active boolean NOT NULL
--    UNIQUE(f_name, l_name)
);

CREATE TABLE roles(
    role_id integer PRIMARY KEY,
    role_name varchar(20) UNIQUE NOT NULL
);

CREATE TABLE users_roles(
    user_id integer REFERENCES users(user_id) ON DELETE CASCADE,
    role_id integer REFERENCES roles(role_id) ON DELETE CASCADE,
    PRIMARY KEY(role_id, user_id)
);

CREATE TABLE appointments(
    appointment_id bigserial PRIMARY KEY,
    user_id integer REFERENCES users(user_id),
    title varchar(50) NOT NULL,
    reason text NOT NULL,
    status varchar(50) NOT NULL,                    -- Enum: requested, cancelled, pending
    dt_generated timestamp NOT NULL,
    dt_requested timestamp
);


-- insert statements (for mocking)
-- Roles:
INSERT INTO roles VALUES(0, 'USER');
INSERT INTO roles VALUES(1, 'ADMIN');
INSERT INTO roles VALUES(2, 'PATIENT');

-- Original (non-hashed) information

-- Name: Nathan  Thompson  --- password: vjkejrgkvhbkw123
-- Name: Broodie  Good  --- password: wqlkjfveurt234
-- Name: Kyle  Wright  --- password: vvjktouiiywqgeuqr345
-- Name: Stewart  McDonald  --- password: uouhwelthnui8t678
-- Name: Matthew  Walters  --- password: voihrtymytquwye789


-- Users:
INSERT INTO users(f_name, l_name, email, username, password, dt_created, is_active)
    VALUES('$2a$10$XhT6VzqVESAio368Zf6UIOGdiKeOb/5AVCdwToA1jv0fcT6yyxTGC',
    '$2a$10$o.mMTBgxOa7iYofUuIJ5wew2qNBL9A7S7OKjx4/KpwtMHSxUL4zEC',
    'nathan.thompson@gmail.com',
    'nathan123',
    '$2a$10$4Dq/ekjSA0y54iuyssdBqeXOSb1B/6J5BJKf9ShGYL3kkUeG/NyYa', current_timestamp - interval'10 days', true);

INSERT INTO users(f_name, l_name, email, username, password, dt_created, is_active)
    VALUES('$2a$10$Q5b1HCuo18tEzpuIN5nhPuvWlWA62E1uIFM6Bm/UH8Hvd2MDcPHI.',
    '$2a$10$d4EddISbILuOhduOjwoUyOTcNAFFKysuk3EhJfa4.fx9719iLNO7O',
    'broodie.good@final.edu,tr',
    'brodie123',
    '$2a$10$Wk.wLAqj/DpIOAsBJgMYhem7OzSzqXW/ykJj7pOZRIFpmVn2UgnQi', current_timestamp - interval'9 days', true);

INSERT INTO users(f_name, l_name, email, username, password, dt_created, is_active)
    VALUES('$2a$10$jl/4NKrlFVPaiLfjYRiX1uMU0okTLvyboS9RTsEdwk/C.3qzM.Jta',
    '$2a$10$vHRCTMYzUQFozjgnEktSQet4pf7vo3K0Ugw/mylqUUO.9aJ05bCEq',
    'kyle.wright@final.edu.tr',
    'kyle123',
    '$2a$10$hLXGVMUxuGtXEvFk9AJ8Je0fZOclBlNDp1kejvTyhzN5eOfa0FSbu', current_timestamp - interval'8 days', true);

INSERT INTO users(f_name, l_name, email, username, password, dt_created, is_active)
    VALUES('$2a$10$V831Eoh5yB/F8Bzu77a2W.ZpLeUH92N/D0uTfebQox.IdmfGCDBHe',
    '$2a$10$JUXMFXI6xd1wuWZGkOVcX./Ky/wA05xBgwFRN0hRNMKQ5MGtRDu8C',
    'stewart.mcdonald@gmail.com',
    'stewart123',
    '$2a$10$zhp2cJ.rzcjUS.2EL/B88.T0vfdP4azgHeI7DILwDrc/eIigi5CQq', current_timestamp - interval'7 days', true);

INSERT INTO users(f_name, l_name, email, username, password, dt_created, is_active)
    VALUES('$2a$10$rWyHGyI0iqVVccf.vSfK6Og/BkzCt.bn2lw3.FK5fyfBSZcq/SNDG',
    '$2a$10$KUWk99dE47yusFLiGSNlTuzAOmTwCwUw2mM7QCXZo2ofHu1TkQyuW',
    'matthew.walters@final.edu.tr',
    'matthew123',
    '$2a$10$TOh1l.GQ6w7PC88zWeaL8OeeL.4svXdBtNpSHyRnobUkOXLOCh8LC', current_timestamp - interval'6 days', true);

-- Assigning roles:
INSERT INTO users_roles(user_id, role_id) VALUES(1, 0);
INSERT INTO users_roles(user_id, role_id) VALUES(1, 1);

INSERT INTO users_roles(user_id, role_id) VALUES(2, 0);
INSERT INTO users_roles(user_id, role_id) VALUES(2, 2);

INSERT INTO users_roles(user_id, role_id) VALUES(3, 0);
INSERT INTO users_roles(user_id, role_id) VALUES(3, 2);

INSERT INTO users_roles(user_id, role_id) VALUES(4, 0);
INSERT INTO users_roles(user_id, role_id) VALUES(4, 1);

INSERT INTO users_roles(user_id, role_id) VALUES(5, 0);
INSERT INTO users_roles(user_id, role_id) VALUES(5, 2);


-- Empty appoitment list:
INSERT INTO appointments(title, reason, dt_generated, status)
    VALUES('No title', 'No reason',
    current_timestamp + interval'5 days', 'UNUSED');
INSERT INTO appointments(title, reason, dt_generated, status)
    VALUES('No title', 'No reason',
    current_timestamp + interval'6 days', 'UNUSED');
INSERT INTO appointments(title, reason, dt_generated, status)
    VALUES('No title', 'No reason',
    current_timestamp + interval'7 days', 'UNUSED');
INSERT INTO appointments(title, reason, dt_generated, status)
    VALUES('No title', 'No reason',
    current_timestamp + interval'8 days', 'UNUSED');
INSERT INTO appointments(title, reason, dt_generated, status)
    VALUES('No title', 'No reason',
    current_timestamp + interval'9 days', 'UNUSED');


-- Checking tables content:
SELECT * FROM users;
SELECT * FROM roles;
SELECT * FROM users_roles;
SELECT * FROM appointments;

