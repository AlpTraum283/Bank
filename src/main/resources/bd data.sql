insert into object (owner, name, date, type) values
(0,'first_user','2020-03-02','userentity'),
(1,'first_account','2006-03-23','accountentity'),
(1,'second_account','2007-04-04','accountentity'),
(0,'second_user','2009-09-09','userentity'),
(2,'third_account','2010-12-12','accountentity'),
(2,'fourth_account','2012-06-06','accountentity'),
(2,'first_transfer','2015-09-07','transferentity');


INSERT INTO attribute(name) VALUES
('balance'),
('draft'),
('password'),
('operation'),
('sum'),
('currency'),
('sender'),
('recipient'),
('status'),
('message');



INSERT INTO parameterDto(obj_id, attr_id, value) VALUES
(2,	1,	'800'),
(2,	6,	'USD'),
(1,	3,	'simplePassword'),
(4,	3,	'moreHarderPassword8'),
(2,	2,  '0'),
(3,	1,	'300'),
(3,	2,	'500'),
(3,	6,	'RUB'),
(5,	1,	'532'),
(5,	2,	'734'),
(5,	6,	'EUR'),
(6,	1,	'8457'),
(6,	2,	'2345'),
(6,	6,	'ILS'),
(7,	4,	'Payment'),
(7,	5,	'800'),
(7,	7,	'2'),
(7,	8,	'3');
