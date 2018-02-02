--fishingapp

TRUNCATE TABLE fishingapp.user_role;
TRUNCATE TABLE fishingapp.roles CASCADE;
TRUNCATE TABLE fishingapp.users CASCADE;
COMMIT;

-----USERS---
INSERT INTO fishingapp.USERS (user_name, EMAIL, last_name, first_name, password)
VALUES ('yashchuk', 'yashchuk@gmail.com', 'Ящук', 'Родіон', fishingapp.bcrypt('yashchuk'));
INSERT INTO fishingapp.USERS (user_name, EMAIL, password)
VALUES ('mazepa', 'rostyslav.mazepa@gmail.com', fishingapp.bcrypt('mazepa'));
INSERT INTO fishingapp.USERS (user_name, EMAIL, password)
VALUES ('bataeva', 'olha.i.b5@gmail.com', fishingapp.bcrypt('bataeva'));

----ROLES-----
INSERT INTO fishingapp.roles (created_by, name) VALUES (1, 'ADMIN');
INSERT INTO fishingapp.roles (created_by, name) VALUES (1, 'USER');
INSERT INTO fishingapp.roles (created_by, name) VALUES (1, 'TEMP_USER');

------user_roles----
INSERT INTO fishingapp.user_role (user_id, role_id)
  SELECT
    u.id,
    r.id
  FROM fishingapp.users u CROSS JOIN fishingapp.roles r
  WHERE (u.user_name = 'yashchuk' OR
         (u.user_name = 'mazepa' AND r.name = 'ADMIN') OR
         (u.user_name = 'bataeva' AND r.name = 'USER')) AND
        NOT EXISTS(SELECT NULL
                   FROM user_role ur
                   WHERE ur.user_id = u.id AND ur.role_id = r.id);