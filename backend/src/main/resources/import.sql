--fishingapp

TRUNCATE TABLE fishingapp.user_role;
TRUNCATE TABLE fishingapp.roles CASCADE ;
TRUNCATE TABLE fishingapp.users CASCADE ;
commit;
----ROLES-----

INSERT INTO fishingapp.roles(id, creator, name) values (1,'ROD','ADMIN');
INSERT INTO fishingapp.roles(id, creator, name) values (2,'ROD','USER');
-----USERS---
INSERT INTO fishingapp.USERS (id, user_name, EMAIL,last_name,first_name, password) VALUES(1, 'yashchuk','yashchuk@gmail.com','Ящук','Родіон',fishingapp.sha256('yashchuk'));
INSERT INTO fishingapp.USERS (id, user_name, EMAIL, password) VALUES(2, 'mazepa', 'rostyslav.mazepa@gmail.com',fishingapp.sha256('mazepa'));
INSERT INTO fishingapp.USERS (id, user_name, EMAIL, password) VALUES(3, 'bataeva', 'olha.i.b5@gmail.com',fishingapp.sha256('bataeva'));
------user_roles----
INSERT INTO fishingapp.user_role(user_id, role_id) VALUES (1,1);
INSERT INTO fishingapp.user_role(user_id, role_id) VALUES (1,2);
INSERT INTO fishingapp.user_role(user_id, role_id) VALUES (2,1);
INSERT INTO fishingapp.user_role(user_id, role_id) VALUES (3,2);

