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
INSERT INTO fishingapp.roles (created_by, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO fishingapp.roles (created_by, name) VALUES (1, 'ROLE_USER');
INSERT INTO fishingapp.roles (created_by, name) VALUES (1, 'ROLE_TEMP_USER');

------user_roles----
INSERT INTO fishingapp.user_role (user_id, role_id)
  SELECT
    u.id,
    r.id
  FROM fishingapp.users u CROSS JOIN fishingapp.roles r
  WHERE (u.user_name = 'yashchuk' OR
         (u.user_name = 'mazepa' AND r.name = 'ROLE_ADMIN') OR
         (u.user_name = 'bataeva' AND r.name = 'ROLE_USER')) AND
        NOT EXISTS(SELECT NULL
                   FROM user_role ur
                   WHERE ur.user_id = u.id AND ur.role_id = r.id);

INSERT INTO fishingapp.fishes (fish_name) VALUES
  ('Быстрянка'), ('Белуга'), ('Бобырец'), ('Берш'), ('Бычок-гонец'), ('Бычок-головач'), ('Белоглазка'),
  ('Быстрянка русская'),
  ('Верховка'), ('Вьюн'), ('Вырезуб'), ('Гольян обыкновенный'), ('Гольян озерный'), ('Горчак'), ('Голавль'),
  ('Густера'),
  ('Голец европейский'), ('Ерш обыкновенный'), ('Ерш носарь'), ('Ерш Балони'), ('Ерш полосатый'), ('Елец'),
  ('Елец Данилевского'),
  ('Жерех'), ('Карп'), ('Карась серебрянный'), ('Карась золотой'), ('Красноперка'), ('Колюшка трехиглая'),
  ('Колюшка малая южная'),
  ('Колюшка девятииглая'), ('Линь'), ('Лещ'), ('Лосось дунайский'), ('Минога карпатская'), ('Минога украинская'),
  ('Налим'), ('Осетр Атлантический'),
  ('Осетр русский'), ('Осетр персидский'), ('Окунь'), ('Перкарина'), ('Подкаменщик обыкновенный'),
  ('Подкаменщик пестроногий'), ('Пуголовка звездчатая'),
  ('Пуголовка Браунера'), ('Плотва обыкновенная'), ('Плотва паннонская'), ('Подуст'), ('Подуст волжский'),
  ('Пескарь обыкновенный'),
  ('Пескарь белоперый днепровский'), ('Пескарь белоперый днестровский'), ('Пескарь дунайский'),
  ('Пуголовка звездчатая'),
  ('Пузанок'), ('Рыбец'), ('Рыбец малый'), ('Севрюга'), ('Стерлядь'), ('Синец'), ('Сом'), ('Судак'), ('Судак морской'),
  ('Угорь'),
  ('Уклейка'), ('Умбра'), ('Усач обыкновенный'), ('Усач днепровский'), ('Усач крымский'),
  ('Усач дунайско-днестровский'),
  ('Усач Валецкого'), ('Форель ручьевая'), ('Форель озерная'), ('Хариус европейский'), ('Чехонь'), ('Чоп большой'),
  ('Чоп малый'),
  ('Шип'), ('Шемая крымская'), ('Шемая азовская'), ('Шемая черноморская'), ('Щиповка обыкновенная'),
  ('Щиповка сибирская'),
  ('Щиповка южнорусская'), ('Щиповка дунайская'), ('Щиповка азовская'), ('Щиповка таврическая'),
  ('Щиповка северная золотистая'),
  ('Щука'), ('Язь'), ('Амур белый'), ('Амур черный'), ('Гамбузия'), ('Медака китайская'), ('Ротан'), ('Сом канальный'),
  ('Солнечный окунь'),
  ('Толстолобик белый'), ('Толстолобик пестрый'), ('Чебачок амурский'), ('Форель радужная');

INSERT INTO fishingapp.fish_type_dic (name) VALUES
  ('ХИЩНАЯ'), ('МИРНАЯ');