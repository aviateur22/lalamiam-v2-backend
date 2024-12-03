--Update schema, database, role, table, column as needed
BEGIN;
DO
$do$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'lalamiam') THEN
        CREATE ROLE project WITH LOGIN PASSWORD 'lalamiam';
        GRANT CONNECT ON DATABASE lalamiam_v2 TO lalamiam;
    END IF;
END
$do$;

CREATE SCHEMA IF NOT EXISTS sc_lalamiam;
ALTER SCHEMA sc_lalamiam OWNER TO lalamiam;


DROP TABLE IF EXISTS sc_lalamiam."captcha_image", sc_lalamiam."login", sc_lalamiam."token", sc_lalamiam."jwt", sc_lalamiam."delay_login", sc_lalamiam."professional_account", sc_lalamiam."employee_account", sc_lalamiam."user_account", sc_lalamiam."role_user", sc_lalamiam."role", sc_lalamiam."employee_professional", sc_lalamiam."employee", sc_lalamiam."document",  sc_lalamiam."professional", sc_lalamiam."users" CASCADE;

-- Utilisateur --
CREATE TABLE if NOT EXISTS sc_lalamiam.users(
    "id" BIGINT PRIMARY KEY,
    "user_name" text not null,
    "email" text not null,
    "password" text not null,
    "created_at" TIMESTAMPTZ NOT NULL DEFAULT now(),
    "updated_at" TIMESTAMPTZ
);
CREATE INDEX IF NOT EXISTS idx_users_email ON sc_lalamiam.users (email);

-- Professionnel --
CREATE TABLE if NOT EXISTS sc_lalamiam.professional(
    "user_id" BIGINT NOT NULL PRIMARY KEY REFERENCES sc_lalamiam."users"("id") on delete cascade,
    "first_name" text not null,
    "last_name" text not null,
    "phone" text not null,
    "created_at" TIMESTAMPTZ NOT NULL DEFAULT now(),
    "updated_at" TIMESTAMPTZ
);

-- Professionnel documents --
CREATE TABLE if NOT EXISTS sc_lalamiam.document(
    "id" BIGINT PRIMARY KEY,
    "user_id" BIGINT NOT NULL REFERENCES sc_lalamiam."users"("id") on delete cascade,
    "document_path" text not null,
    "created_at" TIMESTAMPTZ NOT NULL DEFAULT now(),
    "updated_at" TIMESTAMPTZ
);

-- Professionnel --
CREATE TABLE if NOT EXISTS sc_lalamiam.employee(
    "user_id" BIGINT NOT NULL PRIMARY KEY REFERENCES sc_lalamiam."users"("id") on delete cascade,
    "created_at" TIMESTAMPTZ NOT NULL DEFAULT now(),
    "updated_at" TIMESTAMPTZ
);

-- Liasion employé et professionel --
create table IF NOT EXISTS sc_lalamiam.employee_professional(
    "id" BIGINT PRIMARY KEY,
    "employee_id" BIGINT NOT NULL REFERENCES sc_lalamiam."users"("id") on delete cascade,
    "professional_id" INT NOT NULL REFERENCES sc_lalamiam."users"("id") on delete cascade,
    "created_at" TIMESTAMPTZ NOT NULL DEFAULT now(),
    "updated_at" TIMESTAMPTZ
);
CREATE INDEX IF NOT EXISTS idx_employee_professional ON sc_lalamiam.employee_professional (employee_id, professional_id);

-- Role --
create table IF NOT EXISTS sc_lalamiam.role(
    "id" INT PRIMARY KEY,
    "role" TEXT NOT NULL,
    "created_at" TIMESTAMPTZ NOT NULL DEFAULT now(),
    "updated_at" TIMESTAMPTZ
);

-- Liasion Role et utilisateur --
create table IF NOT EXISTS sc_lalamiam.role_user(
    "id" BIGINT PRIMARY KEY,
    "user_id" BIGINT NOT NULL REFERENCES sc_lalamiam."users"("id") on delete cascade,
    "role_id" INT NOT NULL REFERENCES sc_lalamiam."role"("id") on delete cascade,
    "created_at" TIMESTAMPTZ NOT NULL DEFAULT now(),
    "updated_at" TIMESTAMPTZ
);
CREATE INDEX IF NOT EXISTS idx_role_user ON sc_lalamiam.role_user(user_id, role_id);

-- Compte utilisateur--
create table IF NOT EXISTS sc_lalamiam.user_account(
    "id" BIGINT PRIMARY KEY,
    "user_id" BIGINT NOT NULL REFERENCES sc_lalamiam."users"("id") on delete cascade,
    "account_activation_at" TIMESTAMPTZ,
    "account_activation_limit_date_at" TIMESTAMPTZ,
    "is_account_active" BOOLEAN NOT NULL DEFAULT TRUE,
    "created_at" TIMESTAMPTZ NOT NULL DEFAULT now(),
    "updated_at" TIMESTAMPTZ
);
CREATE INDEX IF NOT EXISTS idx_user_account ON sc_lalamiam.user_account(user_id);

-- Compte employé --
create table IF NOT EXISTS sc_lalamiam.professional_account(
    "id" BIGINT PRIMARY KEY,
    "user_id" BIGINT NOT NULL REFERENCES sc_lalamiam."users"("id") on delete cascade,
    "account_activation_at" TIMESTAMPTZ,
    "account_activation_limit_date_at" TIMESTAMPTZ,
    "is_account_active" BOOLEAN NOT NULL DEFAULT TRUE,
    "created_at" TIMESTAMPTZ NOT NULL DEFAULT now(),
    "updated_at" TIMESTAMPTZ
);
CREATE INDEX IF NOT EXISTS idx_professional_account ON sc_lalamiam.professional_account(user_id);

-- Compte professionel--
create table IF NOT EXISTS sc_lalamiam.employee_account(
    "id" BIGINT PRIMARY KEY,
    "user_id" BIGINT NOT NULL REFERENCES sc_lalamiam."users"("id") on delete cascade,
    "account_activation_at" TIMESTAMPTZ,
    "is_account_active" BOOLEAN NOT NULL DEFAULT TRUE,
    "created_at" TIMESTAMPTZ NOT NULL DEFAULT now(),
    "updated_at" TIMESTAMPTZ
);
CREATE INDEX IF NOT EXISTS idx_employee_account ON sc_lalamiam.employee_account(user_id);

-- JWT --
CREATE TABLE if NOT EXISTS sc_lalamiam.jwt(
    "id" BIGINT PRIMARY KEY,
    "user_id" BIGINT NOT NULL REFERENCES sc_lalamiam."users"("id") on delete cascade,
    "jwt_token" text NOT NULL,
    "jwt_id" TEXT NOT NULL,
    "is_valid" BOOLEAN NOT NULL DEFAULT FALSE,
    "expired_at" TIMESTAMPTZ NOT NULL DEFAULT now(),
    "created_at" TIMESTAMPTZ NOT NULL DEFAULT now(),
    "updated_at" TIMESTAMPTZ
);
CREATE INDEX IF NOT EXISTS idx_jwt ON sc_lalamiam.jwt(user_id);


-- Token  --
create table IF NOT EXISTS sc_lalamiam.token(
    "id" BIGINT PRIMARY KEY,
    "user_id" BIGINT REFERENCES sc_lalamiam."users"("id") on delete cascade,
    "cryptography_text" TEXT NOT NULL,
    "cryptography_type" TEXT NOT NULL,
    "iv_key" TEXT,
    "valid_until" TIMESTAMPTZ NOT NULL,
    "created_at" TIMESTAMPTZ NOT NULL DEFAULT now(),
    "updated_at" TIMESTAMPTZ
);
CREATE INDEX IF NOT EXISTS idx_token ON sc_lalamiam.token(user_id);

-- Connexion utilisateur --
create table IF NOT EXISTS sc_lalamiam.login(
    "id" BIGINT PRIMARY KEY,
    "user_id" BIGINT NOT NULL REFERENCES sc_lalamiam."users"("id") on delete cascade,
    "is_login_success" BOOLEAN NOT NULL,
    "has_to_be_check" BOOLEAN NOT NULL DEFAULT TRUE,
    "login_at" TIMESTAMPTZ NOT NULL
);
CREATE INDEX IF NOT EXISTS idx_login ON sc_lalamiam.login(user_id);

-- Delai de connexion au compte --
create table IF NOT EXISTS sc_lalamiam.delay_login(
    "id" BIGINT PRIMARY KEY,
    "user_id" BIGINT NOT NULL REFERENCES sc_lalamiam."users"("id") on delete cascade,
    "delay_login_until" TIMESTAMPTZ NOT NULL DEFAULT NOW() + INTERVAL '5 minutes',
    "created_at" TIMESTAMPTZ NOT NULL DEFAULT now(),
    "updated_at" TIMESTAMPTZ
);
CREATE INDEX IF NOT EXISTS idx_delay_login ON sc_lalamiam.delay_login(user_id);

create table IF NOT EXISTS sc_lalamiam.captcha_image(
    "id" BIGINT PRIMARY KEY,
    "name" TEXT NOT NULL,
    "path" TEXT NOT NULL,
    "response" TEXT NOT NULL,
    "created_at" TIMESTAMPTZ NOT NULL DEFAULT now(),
    "updated_at" TIMESTAMPTZ
);

ALTER table IF EXISTS sc_lalamiam.users OWNER TO lalamiam;
ALTER table IF EXISTS sc_lalamiam.employee OWNER TO lalamiam;
ALTER table IF EXISTS sc_lalamiam.professional OWNER TO lalamiam;
ALTER table IF EXISTS sc_lalamiam.employee_professsional OWNER TO lalamiam;
ALTER table IF EXISTS sc_lalamiam.document OWNER TO lalamiam;
ALTER table IF EXISTS sc_lalamiam.jwt OWNER TO lalamiam;
ALTER table IF EXISTS sc_lalamiam.role OWNER TO lalamiam;
ALTER table IF EXISTS sc_lalamiam.role_user OWNER TO lalamiam;
ALTER table IF EXISTS sc_lalamiam.user_account OWNER TO lalamiam;
ALTER table IF EXISTS sc_lalamiam.employee_account OWNER TO lalamiam;
ALTER table IF EXISTS sc_lalamiam.professional_account OWNER TO lalamiam;
ALTER table IF EXISTS sc_lalamiam.token OWNER TO lalamiam;
ALTER table IF EXISTS sc_lalamiam.login OWNER TO lalamiam;
ALTER table IF EXISTS sc_lalamiam.delay_login OWNER TO lalamiam;
ALTER table IF EXISTS sc_lalamiam.captcha_image OWNER TO lalamiam;

GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE sc_lalamiam.users TO lalamiam;
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE sc_lalamiam.employee TO lalamiam;
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE sc_lalamiam.professional TO lalamiam;
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE sc_lalamiam.employee_professional TO lalamiam;
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE sc_lalamiam.document TO lalamiam;
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE sc_lalamiam.jwt TO lalamiam;
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE sc_lalamiam.role TO lalamiam;
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE sc_lalamiam.role_user TO lalamiam;
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE sc_lalamiam.user_account TO lalamiam;
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE sc_lalamiam.professional_account TO lalamiam;
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE sc_lalamiam.employee_account TO lalamiam;
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE sc_lalamiam.token TO lalamiam;
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE sc_lalamiam.login TO lalamiam;
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE sc_lalamiam.delay_login TO lalamiam;
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE sc_lalamiam.captcha_image TO lalamiam;

CREATE SEQUENCE if not exists sc_lalamiam.users_pk_seq START WITH 1 INCREMENT BY 1 NO CYCLE;
CREATE SEQUENCE if not exists sc_lalamiam.document_pk_seq START WITH 1 INCREMENT BY 1 NO CYCLE;
CREATE SEQUENCE if not exists sc_lalamiam.jwt_pk_seq START WITH 1 INCREMENT BY 1 NO CYCLE;
CREATE SEQUENCE if not exists sc_lalamiam.role_user_pk_seq START WITH 1 INCREMENT BY 1 NO CYCLE;
CREATE SEQUENCE if not exists sc_lalamiam.user_account_pk_seq START WITH 1 INCREMENT BY 1 NO CYCLE;
CREATE SEQUENCE if not exists sc_lalamiam.employee_account_pk_seq START WITH 1 INCREMENT BY 1 NO CYCLE;
CREATE SEQUENCE if not exists sc_lalamiam.professional_account_pk_seq START WITH 1 INCREMENT BY 1 NO CYCLE;
CREATE SEQUENCE if not exists sc_lalamiam.token_pk_seq START WITH 1 INCREMENT BY 1 NO CYCLE;
CREATE SEQUENCE if not exists sc_lalamiam.login_pk_seq START WITH 1 INCREMENT BY 1 NO CYCLE;
CREATE SEQUENCE if not exists sc_lalamiam.delay_login_pk_seq START WITH 1 INCREMENT BY 1 NO CYCLE;
CREATE SEQUENCE if not exists sc_lalamiam.captcha_image_pk_seq START WITH 1 INCREMENT BY 1 NO CYCLE;

ALTER SEQUENCE if exists sc_lalamiam.users_pk_seq OWNER TO lalamiam;
ALTER SEQUENCE if exists sc_lalamiam.users_pk_seq owned by sc_lalamiam.users.id;
ALTER TABLE sc_lalamiam.users ALTER COLUMN id SET DEFAULT NEXTVAL('sc_lalamiam.users_pk_seq');

ALTER SEQUENCE if exists sc_lalamiam.jwt_pk_seq OWNER TO lalamiam;
ALTER SEQUENCE if exists sc_lalamiam.jwt_pk_seq owned by sc_lalamiam.jwt.id;
ALTER TABLE sc_lalamiam.jwt ALTER COLUMN id SET DEFAULT NEXTVAL('sc_lalamiam.jwt_pk_seq');

ALTER SEQUENCE if exists sc_lalamiam.role_user_pk_seq OWNER TO lalamiam;
ALTER SEQUENCE if exists sc_lalamiam.role_user_pk_seq owned by sc_lalamiam.role_user.id;
ALTER TABLE sc_lalamiam.role_user ALTER COLUMN id SET DEFAULT NEXTVAL('sc_lalamiam.role_user_pk_seq');

ALTER SEQUENCE if exists sc_lalamiam.user_account_pk_seq OWNER TO lalamiam;
ALTER SEQUENCE if exists sc_lalamiam.user_account_pk_seq owned by sc_lalamiam.user_account.id;
ALTER TABLE sc_lalamiam.user_account ALTER COLUMN id SET DEFAULT NEXTVAL('sc_lalamiam.user_account_pk_seq');

ALTER SEQUENCE if exists sc_lalamiam.employee_account_pk_seq OWNER TO lalamiam;
ALTER SEQUENCE if exists sc_lalamiam.employee_account_pk_seq owned by sc_lalamiam.employee_account.id;
ALTER TABLE sc_lalamiam.employee_account ALTER COLUMN id SET DEFAULT NEXTVAL('sc_lalamiam.employee_account_pk_seq');

ALTER SEQUENCE if exists sc_lalamiam.professional_account_pk_seq OWNER TO lalamiam;
ALTER SEQUENCE if exists sc_lalamiam.professional_account_pk_seq owned by sc_lalamiam.professional_account.id;
ALTER TABLE sc_lalamiam.professional_account ALTER COLUMN id SET DEFAULT NEXTVAL('sc_lalamiam.professional_account_pk_seq');

ALTER SEQUENCE if exists sc_lalamiam.token_pk_seq OWNER TO lalamiam;
ALTER SEQUENCE if exists sc_lalamiam.token_pk_seq owned by sc_lalamiam.token.id;
ALTER TABLE sc_lalamiam.token ALTER COLUMN id SET DEFAULT NEXTVAL('sc_lalamiam.token_pk_seq');

ALTER SEQUENCE if exists sc_lalamiam.login_pk_seq OWNER TO lalamiam;
ALTER SEQUENCE if exists sc_lalamiam.login_pk_seq owned by sc_lalamiam.login.id;
ALTER TABLE sc_lalamiam.login ALTER COLUMN id SET DEFAULT NEXTVAL('sc_lalamiam.login_pk_seq');

ALTER SEQUENCE if exists sc_lalamiam.delay_login_pk_seq OWNER TO lalamiam;
ALTER SEQUENCE if exists sc_lalamiam.delay_login_pk_seq owned by sc_lalamiam.delay_login.id;
ALTER TABLE sc_lalamiam.delay_login ALTER COLUMN id SET DEFAULT NEXTVAL('sc_lalamiam.delay_login_pk_seq');

ALTER SEQUENCE if exists sc_lalamiam.captcha_image_pk_seq OWNER TO lalamiam;
ALTER SEQUENCE if exists sc_lalamiam.captcha_image_pk_seq owned by sc_lalamiam.captcha_image.id;
ALTER TABLE sc_lalamiam.captcha_image ALTER COLUMN id SET DEFAULT NEXTVAL('sc_lalamiam.captcha_image_pk_seq');

--Clear password: test
INSERT INTO sc_lalamiam.users ("user_name", "email" , "password") VALUES
('client', 'client@hotmail.fr', '$2y$10$9PSCTWQiEIbXulYGOZi7.u6x5S6.8XuM0dL3EH72sigNHLlUW2wzy'),
('pro', 'pro@hotmail.fr', '$2y$10$9PSCTWQiEIbXulYGOZi7.u6x5S6.8XuM0dL3EH72sigNHLlUW2wzy'),
('admin', 'admin@hotmail.fr', '$2y$10$9PSCTWQiEIbXulYGOZi7.u6x5S6.8XuM0dL3EH72sigNHLlUW2wzy');

INSERT INTO sc_lalamiam.role ("id", "role") VALUES
(1, 'ROLE_CLIENT'),
(2, 'ROLE_PRO'),
(3, 'ROLE_ADMIN');

INSERT INTO sc_lalamiam.user_account ("user_id", "is_account_active") VALUES
(1, TRUE),
(2, TRUE),
(3, TRUE);

INSERT INTO sc_lalamiam.role_user ("user_id", "role_id") VALUES
(1, 1),
(2, 1),
(2, 2),
(3, 1),
(3, 2),
(3, 3);

INSERT INTO sc_lalamiam.captcha_image("name","path", "response") VALUES
('la carotte.png', 'infra/image/captcha/la carotte.png', 'carotte'),
('la coccinelle.png', 'infra/image/captcha/la coccinelle.png', 'coccinelle'),
('la pluie.png', 'infra/image/captcha/la pluie.png', 'pluie'),
('le chat.png', 'infra/image/captcha/le chat.png', 'chat'),
('le parasol.png', 'infra/image/captcha/le parasol.png', 'parasol'),
('raisin.png', 'infra/image/captcha/raisin.png', 'raisin'),
('un caddie.png', 'infra/image/captcha/un caddie.png', 'caddie'),
('un champignon.png', 'infra/image/captcha/un champignon.png', 'champignon'),
('un citron.png', 'infra/image/captcha/un citron.png', 'citron'),
('un coeur.png', 'infra/image/captcha/un coeur.png', 'coeur'),
('une orange.png', 'infra/image/captcha/une orange.png', 'orange'),
('une poire.png', 'infra/image/captcha/une poire.png', 'poire');




COMMIT;