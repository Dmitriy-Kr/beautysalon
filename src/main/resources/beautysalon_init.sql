INSERT `role`(name) VALUES('admin');
INSERT `role`(name) VALUES('employee');
INSERT `role`(name) VALUES('client');
INSERT `profession`(name) VALUES("Мастер маникюра"), ("Визажист"), ("Массажист");
INSERT `account`(login, password, role_id) VALUE('Dave', 12345, 1);
INSERT `account`(login, password, role_id) VALUE('Mary23', 1945, 1);
INSERT `account`(login, password, role_id) VALUE('Ivan45', 345, 2);
INSERT `account`(login, password, role_id) VALUE('Vovan', 15, 3);
INSERT `account`(login, password, role_id) VALUE('Irina26', 1585, 3);
INSERT `account`(login, password, role_id) VALUE('Veronika', 1695, 2);
INSERT `account`(login, password, role_id) VALUE('Mika', 1656, 2);
INSERT `account`(login, password, role_id) VALUE('Vale56', 1586, 2);
INSERT `account`(login, password, role_id) VALUE('Vera26', 1345, (SELECT `id` FROM `role` WHERE `name` = "client"));
INSERT `admin`(account_id, name, surname) VALUE(1, 'Дмитрий', 'Пупков');
INSERT `admin`(account_id, name, surname) VALUE(2, 'Мария', 'Чукина');
INSERT `employee`(account_id, name, surname, profession_id, rating) VALUE(3, 'Иван', 'Петров', 1, 4.8);
INSERT `employee`(account_id, name, surname, profession_id, rating) VALUE(6, 'Вероника', 'Гиппиус', 1, 4.2);
INSERT `employee`(account_id, name, surname, profession_id, rating) VALUE(7, 'Мишель', 'Яилова', 3, 4.5);
INSERT `employee`(account_id, name, surname, profession_id, rating) VALUE(8, 'Валентина', 'Мишина', 2, 4.1);
INSERT `service`(name, price, spend_time, profession_id) VALUE('Маникюр', 200, "00:30:00", 1);
INSERT `service`(name, price, spend_time, profession_id) VALUE('Массаж', 700, "01:00:00", 3);
INSERT `service`(name, price, spend_time, profession_id) VALUE('Стрижка', 400, "01:00:00", (SELECT `id` FROM `profession` WHERE `name` = "Визажист"));
-- UPDATE `service` SET `spend_time` = "00:30:00" WHERE `name` = "Маникюр";
-- UPDATE `service` SET `spend_time` = "01:00:00" WHERE `name` = "Стрижка";
INSERT `client`(account_id, name, surname) VALUE(4, 'Владимир', 'Кличко');
INSERT `client`(account_id, name, surname) VALUE(5, 'Ирина', 'Лукова');
INSERT `client`(account_id, name, surname) VALUE((SELECT `id` FROM `account` WHERE `login` = "Vera26"), 'Вероника', 'Кудина');
INSERT `ordering`(`ordering_date_time`, `service_id`, `employee_id`, `client_id`, `status`)
VALUE("2021-11-05 13:00:00", 1, 1, 1, "active");