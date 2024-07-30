
CREATE TYPE user_role AS ENUM ('USER', 'SUPER_ADMIN', 'ADMIN');

ALTER TABLE public.users
ADD COLUMN role user_role NOT NULL DEFAULT 'USER';
