/*
SQLyog Community v13.2.0 (64 bit)
MySQL - 8.0.32 : Database - cinemas
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`cinemas` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `cinemas`;

/*Table structure for table `actor` */

DROP TABLE IF EXISTS `actor`;

CREATE TABLE `actor` (
  `actor_id` smallint unsigned NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted_id` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`actor_id`),
  KEY `idx_actor_last_name` (`last_name`)
) ENGINE=InnoDB AUTO_INCREMENT=252 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `actor` */

insert  into `actor`(`actor_id`,`first_name`,`last_name`,`last_update`,`deleted_id`) values 
(202,'Тиэко','Байсё','2023-05-14 21:41:38',0),
(203,'Такуя ','Кимура','2023-05-14 21:48:58',0),
(204,'Акихиро','Мива','2023-05-15 20:28:47',0),
(205,'Тацуя','Гасюин','2023-05-15 20:30:27',0),
(206,'Брюс','Уиллис','2023-05-17 01:37:32',0),
(207,'Милла','Йовович','2023-05-17 01:39:01',0),
(208,'Гэри','Олдман','2023-05-17 01:40:56',0),
(209,'Гэри','Олдман','2023-05-17 02:57:36',209),
(210,'Н.Т.Р. Рао','Джуниор','2023-05-17 03:04:13',0),
(211,'Алиа','Бхатт','2023-05-17 03:05:28',0),
(212,'Аджай','Девган','2023-05-17 03:06:31',0),
(213,'Том','Харди','2023-05-17 03:10:23',0),
(214,'Шарлиз','Терон','2023-05-17 03:11:54',0),
(215,'Брэндон','Ли','2023-05-17 03:16:38',0),
(216,'Рошель','Дэвис','2023-05-17 03:17:40',0),
(217,'Эрни','Хадсон','2023-05-17 03:18:57',0),
(218,'Том','Круз','2023-05-17 03:22:11',0),
(219,'Брэд','Питт','2023-05-17 03:23:34',0),
(220,'Антонио','Бандерас','2023-05-17 03:24:16',0),
(221,'Кирстен','Данст','2023-05-17 03:25:14',0),
(222,'Сальма','Хайек','2023-05-17 03:29:17',0),
(223,'Харви','Гильен','2023-05-17 03:30:35',0),
(224,'Флоренс','Пью','2023-05-17 03:31:47',0),
(225,'Рэйф','Файнс','2023-05-17 03:42:43',0),
(226,'Тони','Револори','2023-05-17 03:43:55',0),
(227,'Сирша','Ронан','2023-05-17 03:44:44',0),
(228,'Эдриан','Броуди','2023-05-17 03:45:34',0),
(229,'Джонни','Депп','2023-05-17 04:43:07',0),
(230,'Джеффри','Раш','2023-05-17 04:44:17',0),
(231,'Орландо','Блум','2023-05-17 04:45:12',0),
(232,'Кира','Найтли','2023-05-17 04:46:02',0),
(233,'Майкл','Сера','2023-05-17 04:49:07',0),
(234,'Мэри Элизабет','Уинстэд','2023-05-17 04:50:11',0),
(235,'Вивьен','Ли','2023-05-17 04:54:44',0),
(236,'Кларк','Гэйбл','2023-05-17 04:55:47',0),
(237,'Лесли','Хауард','2023-05-17 04:56:41',0),
(238,'Оливия ','де Хэвиллэнд','2023-05-17 04:57:52',0),
(239,'Вайнона','Райдер','2023-05-17 05:01:47',0),
(244,'Джим','Keppи','2023-11-20 22:34:59',0),
(250,'Том','Холанд','2023-11-21 01:07:12',0),
(251,'Андрей','Миронов','2023-12-03 18:58:05',0);

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `category_id` tinyint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(25) NOT NULL,
  `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted_id` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `category` */

insert  into `category`(`category_id`,`name`,`last_update`,`deleted_id`) values 
(1,'Боевик','2023-05-15 20:50:14',0),
(2,'Анимация','2023-05-07 00:26:47',0),
(3,'Детский','2023-05-07 00:26:55',0),
(4,'Классический','2023-05-07 00:27:05',0),
(5,'Комедия','2023-05-07 00:27:10',0),
(6,'Документальный','2023-05-07 00:27:19',0),
(7,'Драма','2023-05-07 00:27:23',0),
(8,'Семейный','2023-05-07 00:27:28',0),
(9,'Зарубежный','2023-05-07 00:27:35',0),
(10,'Игровой','2023-05-07 00:27:48',0),
(11,'Ужас','2023-05-07 00:27:54',0),
(12,'Мьзикл','2023-05-07 00:28:03',0),
(13,'Новый','2023-05-07 00:28:24',0),
(14,'Фантастика','2023-05-07 00:30:45',0),
(15,'Спортивный','2023-05-07 00:30:50',0),
(16,'Приключенческий','2023-05-07 00:30:55',0),
(17,'Фэнтези','2023-05-07 00:31:05',0),
(18,'Романтика','2023-05-07 00:31:15',0),
(21,'Триллер','2023-05-17 03:34:12',0),
(22,'Мелодрама','2023-05-17 04:53:23',0),
(23,'Арт-хаус','2023-11-20 23:48:16',0);

/*Table structure for table `cinema` */

DROP TABLE IF EXISTS `cinema`;

CREATE TABLE `cinema` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `address` varchar(1024) NOT NULL,
  `email` varchar(50) NOT NULL,
  `deleted_id` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `cinema` */

insert  into `cinema`(`id`,`name`,`address`,`email`,`deleted_id`) values 
(1,'Аврора','г. Минск, ул. Притыцкого 23','',0),
(2,'Октябрь','г. Минск, пр-т Незавиcимости, 73','october@mail.ru',0),
(3,'Беларусь','г. Минск, ул. Романовская Слобода, 28','',0),
(4,'тестттт','Минск пппппп','',4),
(5,'wwwww 34525423','ewfytretyerty','',5);

/*Table structure for table `cinema_room` */

DROP TABLE IF EXISTS `cinema_room`;

CREATE TABLE `cinema_room` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `rows` int NOT NULL DEFAULT '0',
  `seats` int NOT NULL DEFAULT '0',
  `deleted_id` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `cinema_room` */

insert  into `cinema_room`(`id`,`name`,`rows`,`seats`,`deleted_id`) values 
(1,'Юнга',10,20,0),
(2,'Лазурный зал',15,20,0),
(3,'Красный зал',20,25,0),
(4,'www 1',20,30,4),
(5,'eee',4,5,5),
(8,'Главный зал',40,50,0),
(9,'3D зал',10,15,0),
(10,'2D зал',20,10,0),
(12,'gold',6,7,0);

/*Table structure for table `employes` */

DROP TABLE IF EXISTS `employes`;

CREATE TABLE `employes` (
  `ideml` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `experince` int NOT NULL DEFAULT '4',
  PRIMARY KEY (`ideml`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `employes` */

/*Table structure for table `film` */

DROP TABLE IF EXISTS `film`;

CREATE TABLE `film` (
  `film_id` smallint unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(128) NOT NULL,
  `description` text,
  `release_year` year DEFAULT NULL,
  `length` smallint unsigned DEFAULT NULL,
  `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `director` varchar(128) NOT NULL,
  `video_url` varchar(1024) NOT NULL,
  PRIMARY KEY (`film_id`),
  KEY `idx_title` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=1005 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `film` */

insert  into `film`(`film_id`,`title`,`description`,`release_year`,`length`,`last_update`,`director`,`video_url`) values 
(1,'Ходячий замок','Злая ведьма заточила 18-летнюю Софи в тело старухи. В поисках того, кто поможет ей вернуться к своему облику, Софи знакомится с могущественным волшебником Хаулом и его демоном Кальцифером. Кальцифер должен служить Хаулу по договору, условия которого он не может разглашать. Девушка и демон решают помочь друг другу избавиться от злых чар… 123',2006,86,'2023-05-02 23:39:57',' Хаяо Миядзаки','https://www.youtube.com/embed/otCW9XEZwlY'),
(2,'5 элемент','Каждые 5000 лет между параллельными измерениями открываются врата, и силы зла и хаоса стремятся нарушить существующую гармонию. И каждые 5000 лет нашей планете нужен герой, который рискнёт встать на пути этого зла. В XXIII веке наступила \"красная\" дата вселенского календаря и нью-йоркскому таксисту Корбену Далласу выпала совсем не радостная карта - святая миссия Спасения. Ему на помощь приходит посланник высшего разума - на вид хрупкая девушка, уязвлённая агрессивностью земной цивилизации. Её имя Лилу. Когда пробьёт роковой час, она дополнит четыре элемента всего сущего: Огонь, Воду, Землю, Воздух. И Любовь спасёт мир…',1997,113,'2023-05-02 23:45:08',' Люк Бессон','https://www.youtube.com/embed/TsnQCIMUBZ0'),
(3,'RRR: Рядом ревёт революция','Фильм рассказывает историю реально существовавших индийских революционеров, Аллури Раджу и Комарама Бхима. И хотя исторических сведений об их знакомстве на сегодняшний день нет, режиссер решил показать, как могла бы выглядеть дружба двух великих исторических деятелей. Сюжет разворачивается в 1920 году, рассказывая о жизни героев до того, как он стали известны, и показывает их путь к борьбе против Британского колониального правительства в Индии. Два народных героя вынуждены встать плечом к плечу, чтобы в нелегкое время защищать родную землю.',2022,187,'2023-05-02 23:59:46',' С.С. Раджамули','https://www.youtube.com/embed/NgBoMJy386M'),
(4,'Безумный Макс: Дорога ярости','Вскоре после отмщения за смерть жены и сына, Макс Рокатански покинул ряды «Основного силового патруля» и уехал в глушь, где скитается в одиночестве, пока мир медленно падает в последствии нефтяного кризиса и глобальной войны. Не имея ничего, кроме своей машины «Перехватчик», Максу предстоит научиться, как выжить в пост-апокалиптической пустоши и сражаться с жестокими, безжалостными воинами, которые населяют её. Преследуемый призраками беспокойного прошлого Макс уверен, что лучший способ выжить - скитаться в одиночестве. Несмотря на это, он присоединяется к бунтарям, бегущим через всю пустыню на боевой фуре, под предводительством отчаянной Фуриосы. Они сбежали из Цитадели, страдающей от тирании Несмертного Джо, и забрали у него кое-что очень ценное. Разъярённый диктатор бросает все свои силы в погоню за мятежниками, ступая на тропу войны - дорогу ярости.',2015,137,'2023-05-02 23:58:21',' Джордж Миллер','https://www.youtube.com/embed/36yEfPaQcSc'),
(5,'Ворон','Это была мрачная и страшная ночь. Ночь жуткого праздника Хэллоуин. Банда насильников и убийц, ворвавшись в дом молодой пары, жестоко расправилась с рок-музыкантом Эриком и его невестой Шелли.\r\n\r\nСпустя год Эрик Дрэйвен выбирается из могилы и отправляется на поиски убийц. Человек ли он? Или Ворон, жаждущий возмездия?',1994,123,'2023-05-03 00:04:42',' Алекс Пройас','https://www.youtube.com/embed/JpTyZLP0KuM'),
(6,'Интервью с вампиром','К падкому на сенсации журналисту приходит вампир, чтобы поведать историю своей жизни. Все началось в 1791 году, когда молодой плантатор Луи, потеряв жену и ребенка, захотел умереть, но «родился для мрака» и обрёл вечную жизнь.',1994,128,'2023-05-03 00:07:31',' Нил Джордан','https://www.youtube.com/embed/pytb_4gzROE'),
(7,'Кот в сапогах: Последнее желание','Бездумно потратив восемь жизней, Кот в сапогах впадает в панику: обстоятельства намекают, что бравому авантюристу пора выйти на пенсию и совсем скоро сразиться со смертью. Кот отправляется в дом престарелых, чтобы провести там последние дни, где он вынужденно носит ошейник и унизительно ходит в один лоток с остальными котами. Но ход его жизни может изменить звезда желаний, находящаяся в глуши тёмного леса.',2022,100,'2023-05-03 00:10:23',' Джоэл Кроуфорд, Хануэль Меркадо','https://www.youtube.com/embed/JA8Xw0ffel8'),
(8,'Отель «Гранд Будапешт»','«Гранд отель «Будапешт» рассказывает об увлекательных приключениях легендарного консьержа Густава Х. и его юного друга, портье Зеро Мустафы. Сотрудники гостиницы становятся свидетелями кражи и поисков бесценных картин эпохи Возрождения, борьбы за огромное состояние богатой семьи и… драматических изменений в Европе между двумя кровопролитными войнами XX века.',2014,115,'2023-05-03 00:13:08',' Уэс Андерсон','https://www.youtube.com/embed/OUw5JaaGNQc'),
(9,'Пираты Карибского моря: Проклятие Черной жемчужины','Капитан Джек Воробей, очаровательный негодяй, бороздит прозрачные воды Карибского моря, которое служит ему обширной площадкой для игр в приключения и тайны. Но идиллия мгновенно идёт ко дну, когда худший его враг, коварный капитан Барбосса, крадёт \"Чёрную жемчужину\", корабль Джека. Но и на этом злодей не останавливается: он нападает на Порт-Роял и похищает дочь губернатора, очаровательную Элизабет Суэнн. Друг детства Элизабет, Уилл Тёрнер, объединяется с Джеком, и вместе они уводят самый быстроходный корабль британского флота \"Перехватчик\" в отчаянной попытке догнать \"Чёрную Жемчужину\" и освободить девушку.\r\n\r\n \r\n\r\nВ погоню за Джеком и Уиллом на линейном корабле \"Разящий\" отправляется честолюбивый командор Норрингтон, чья помолвка с Элизабет вдруг оказалась под угрозой. Друзья не догадываются об этом, как не догадываются они о страшной тайне: проклятье, наложенное на Барбоссу и его команду, превратило их в живых мертвецов, проявляющих свою чудовищную сущность лишь под лучами лунного света. Только после того, как однажды разграбленный клад будет возвращен назад до последней монеты, проклятье будет снято.',2003,151,'2023-05-03 00:16:11',' Гор Вербински','https://www.youtube.com/embed/XX0zmgbcMm8'),
(10,'Скотт Пилигрим против всех','Спокойная жизнь 22-летнего Скотта Пилигрима, безработного басиста не слишком известной рок-группы, подошла к концу - он влюбился в девушку своей мечты. Но любовь к красавице Рамоне связана с нешуточной опасностью: между Скоттом и девушкой стоят семь ее злобных \"бывших\", которых парню предстоит одолеть, узнав, попутно, что Рамона обладает очень необычными способностями.',2010,99,'2023-05-03 00:19:31','Эдгар Райт','https://www.youtube.com/embed/SmwYlnTC_50'),
(11,'Унесенные ветром','Масштабная эпопея по роману Маргарет Митчелл о любви и ненависти на фоне Гражданской войны в США. Героиня фильма - красавица и эгоистка Скарлетт О’ Хара - никому не хочет отдавать свое сердце до тех пор, пока не дует ветер перемен, подгоняемый войной между Севером и Югом. Чтобы спасти свою семью, девушка готова поступиться собой и выйти замуж за нелюбимого Ретта Батлера. Инфантильная барышня быстро учится жизни и узнает цену деньгам. Несмотря на разразившуюся войну, она всеми силами пытается спасти семейную ферму.',1939,221,'2023-05-03 00:23:14','Виктор Флеминг, Джордж Кьюкор, Сэм Вуд','https://www.youtube.com/embed/hihM44IZdlE'),
(12,'Эдвард – руки-ножницы','Разъезжая по городу в поисках новых клиентов, женщина-коммивояжер, торгующая косметикой, находит в старинном особняке странного парня с садовыми ножницами вместо кистей рук. Оказывается, что это искусственное разумное существо, пожилой создатель которого умер, не успев наделить свое творение человеческими руками. Хотя юноша ничего не знает об окружающем мире и не может делать многое из того, для чего люди пользуются руками, люди со временем привыкают к нему и начинают пользоваться его услугами как садовника и парикмахера. Дочь женщины, нашедшей «гомункула», также постепенно приспосабливается к новому соседу и даже проникается к нему любовью.',1990,105,'2023-05-03 00:27:03','Тим Бёртон','https://www.youtube.com/embed/0CKIDbx4WEw');

/*Table structure for table `film_actor` */

DROP TABLE IF EXISTS `film_actor`;

CREATE TABLE `film_actor` (
  `actor_id` smallint unsigned NOT NULL,
  `film_id` smallint unsigned NOT NULL,
  `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`actor_id`,`film_id`),
  KEY `idx_fk_film_id` (`film_id`),
  CONSTRAINT `fk_film_actor_actor` FOREIGN KEY (`actor_id`) REFERENCES `actor` (`actor_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_film_actor_film` FOREIGN KEY (`film_id`) REFERENCES `film` (`film_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `film_actor` */

insert  into `film_actor`(`actor_id`,`film_id`,`last_update`) values 
(202,1,'2023-05-14 21:45:06'),
(203,1,'2023-05-14 21:50:04'),
(204,1,'2023-05-15 20:29:23'),
(205,1,'2023-05-15 20:30:41'),
(206,2,'2023-05-17 01:41:40'),
(207,2,'2023-05-17 01:41:45'),
(208,2,'2023-05-17 01:41:50'),
(210,3,'2023-05-17 03:06:55'),
(211,3,'2023-05-17 03:07:00'),
(212,3,'2023-05-17 03:07:09'),
(213,4,'2023-05-17 03:12:39'),
(214,4,'2023-05-17 03:12:43'),
(215,5,'2023-05-17 03:19:29'),
(216,5,'2023-05-17 03:19:34'),
(217,5,'2023-05-17 03:19:15'),
(218,6,'2023-05-17 03:25:56'),
(219,6,'2023-05-17 03:25:39'),
(220,6,'2023-05-17 03:25:31'),
(220,7,'2023-05-17 03:28:22'),
(221,6,'2023-05-17 03:25:48'),
(222,7,'2023-05-17 03:29:41'),
(223,7,'2023-05-17 03:30:58'),
(224,7,'2023-05-17 03:31:58'),
(225,8,'2023-05-17 03:43:04'),
(226,8,'2023-05-17 03:44:08'),
(227,8,'2023-05-17 03:44:56'),
(228,8,'2023-05-17 03:45:51'),
(229,9,'2023-05-17 04:43:43'),
(229,12,'2023-05-17 05:00:58'),
(230,9,'2023-05-17 04:44:32'),
(231,9,'2023-05-17 04:45:28'),
(232,9,'2023-05-17 04:46:17'),
(233,10,'2023-05-17 04:49:25'),
(234,10,'2023-05-17 04:50:29'),
(235,11,'2023-05-17 04:55:32'),
(236,11,'2023-05-17 04:56:05'),
(237,11,'2023-05-17 04:57:05'),
(238,11,'2023-05-17 04:58:09'),
(239,12,'2023-05-17 05:02:10');

/*Table structure for table `film_category` */

DROP TABLE IF EXISTS `film_category`;

CREATE TABLE `film_category` (
  `film_id` smallint unsigned NOT NULL,
  `category_id` tinyint unsigned NOT NULL,
  `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`film_id`,`category_id`),
  KEY `fk_film_category_category` (`category_id`),
  CONSTRAINT `fk_film_category_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_film_category_film` FOREIGN KEY (`film_id`) REFERENCES `film` (`film_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `film_category` */

insert  into `film_category`(`film_id`,`category_id`,`last_update`) values 
(1,2,'2023-05-06 22:39:42'),
(1,3,'2023-05-06 22:39:17'),
(1,8,'2023-05-06 22:39:10'),
(2,1,'2023-05-17 01:42:29'),
(2,14,'2023-05-17 01:42:47'),
(2,16,'2023-05-17 01:42:36'),
(3,1,'2023-05-17 03:02:50'),
(3,7,'2023-05-17 03:02:54'),
(4,1,'2023-05-17 03:09:01'),
(4,14,'2023-05-17 03:09:10'),
(4,16,'2023-05-17 03:09:06'),
(5,1,'2023-05-17 03:15:41'),
(5,7,'2023-05-17 03:15:45'),
(5,17,'2023-05-17 03:15:48'),
(6,7,'2023-05-17 03:21:06'),
(6,11,'2023-05-17 03:21:10'),
(7,2,'2023-05-17 03:28:03'),
(7,5,'2006-02-15 05:07:09'),
(7,16,'2023-05-17 03:28:08'),
(8,5,'2023-05-17 03:33:19'),
(8,16,'2023-05-17 03:33:23'),
(8,21,'2023-05-17 03:35:54'),
(9,1,'2023-05-17 04:41:57'),
(9,16,'2023-05-17 04:42:01'),
(9,17,'2023-05-17 04:42:05'),
(10,1,'2023-05-17 04:48:06'),
(10,4,'2023-05-17 04:48:12'),
(10,17,'2023-05-17 04:48:15'),
(11,4,'2023-05-17 04:52:41'),
(11,7,'2023-05-17 04:53:51'),
(11,22,'2023-05-17 04:53:37'),
(12,7,'2023-05-17 05:00:29'),
(12,17,'2023-05-17 05:00:37'),
(12,22,'2023-05-17 05:00:34');

/*Table structure for table `movie_session` */

DROP TABLE IF EXISTS `movie_session`;

CREATE TABLE `movie_session` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `session_time` datetime NOT NULL,
  `ticket_price` decimal(10,0) NOT NULL,
  `cinema_room_id` int unsigned NOT NULL,
  `film_film_id` smallint unsigned NOT NULL,
  `deleted_id` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_movie_session_cinema_room1_idx` (`cinema_room_id`),
  KEY `fk_movie_session_film1_idx` (`film_film_id`),
  CONSTRAINT `fk_movie_session_cinema_room1` FOREIGN KEY (`cinema_room_id`) REFERENCES `cinema_room` (`id`),
  CONSTRAINT `fk_movie_session_film1` FOREIGN KEY (`film_film_id`) REFERENCES `film` (`film_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `movie_session` */

insert  into `movie_session`(`id`,`session_time`,`ticket_price`,`cinema_room_id`,`film_film_id`,`deleted_id`) values 
(1,'2023-03-02 20:22:00',7,3,1,0),
(2,'2023-05-02 20:22:00',20,3,6,2),
(3,'2023-03-02 15:00:00',9,3,1,0),
(4,'2023-06-11 10:00:00',2,3,1,0),
(5,'2023-05-30 17:00:00',10,8,2,0),
(6,'2023-05-30 21:00:00',12,8,2,0),
(7,'2023-06-11 12:00:00',10,1,1,0),
(8,'2023-06-11 10:00:00',10,8,1,0);

/*Table structure for table `name_product` */

DROP TABLE IF EXISTS `name_product`;

CREATE TABLE `name_product` (
  `id_pr` int NOT NULL AUTO_INCREMENT,
  `name_product` varchar(45) NOT NULL,
  `price` int NOT NULL,
  PRIMARY KEY (`id_pr`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `name_product` */

/*Table structure for table `opinion` */

DROP TABLE IF EXISTS `opinion`;

CREATE TABLE `opinion` (
  `opinion_id` int NOT NULL,
  `mark` int DEFAULT NULL,
  `emotion` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`opinion_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `opinion` */

/*Table structure for table `production` */

DROP TABLE IF EXISTS `production`;

CREATE TABLE `production` (
  `idprod` int NOT NULL AUTO_INCREMENT,
  `idempl` int NOT NULL,
  `id_pr` int NOT NULL,
  `percent_flaw` int NOT NULL,
  PRIMARY KEY (`idprod`),
  KEY `production_fk0` (`idempl`),
  KEY `production_fk1` (`id_pr`),
  CONSTRAINT `production_fk0` FOREIGN KEY (`idempl`) REFERENCES `employes` (`ideml`),
  CONSTRAINT `production_fk1` FOREIGN KEY (`id_pr`) REFERENCES `name_product` (`id_pr`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `production` */

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int unsigned NOT NULL,
  `name` varchar(128) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `role` */

insert  into `role`(`id`,`name`) values 
(1,'Зритель'),
(2,'Администратор');

/*Table structure for table `ticket` */

DROP TABLE IF EXISTS `ticket`;

CREATE TABLE `ticket` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `row` int NOT NULL,
  `seat` int NOT NULL,
  `movie_session_id` int unsigned NOT NULL,
  `user_id` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ticket_movie_session1_idx` (`movie_session_id`),
  KEY `fk_ticket_user1_idx` (`user_id`),
  CONSTRAINT `fk_ticket_movie_session1` FOREIGN KEY (`movie_session_id`) REFERENCES `movie_session` (`id`),
  CONSTRAINT `fk_ticket_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=455 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `ticket` */

insert  into `ticket`(`id`,`row`,`seat`,`movie_session_id`,`user_id`) values 
(406,6,5,1,5),
(407,6,6,1,5),
(408,6,8,1,5),
(409,6,12,1,5),
(410,6,15,1,5),
(411,7,9,1,5),
(412,7,10,1,5),
(413,8,11,1,5),
(414,9,8,1,5),
(415,9,15,1,5),
(416,10,15,1,5),
(417,10,16,1,5),
(418,11,16,1,5),
(419,4,8,3,5),
(420,4,12,3,5),
(421,4,13,3,5),
(422,5,9,3,5),
(423,9,8,3,5),
(424,10,7,3,5),
(425,11,6,3,5),
(426,11,4,3,5),
(427,8,15,3,5),
(428,9,15,3,5),
(429,7,10,4,5),
(430,7,13,4,5),
(431,8,11,4,5),
(432,9,12,4,5),
(433,8,13,4,5),
(434,1,24,4,5),
(435,1,25,4,5),
(436,5,23,4,5),
(437,6,23,4,5),
(438,1,24,4,5),
(439,1,25,4,5),
(440,10,7,8,5),
(441,10,9,8,5),
(442,10,16,8,5),
(443,10,8,8,5),
(444,10,14,8,5),
(445,20,46,8,5),
(446,20,47,8,5),
(447,20,48,8,5),
(448,11,16,8,5),
(449,12,17,8,5),
(450,13,18,8,5),
(451,14,19,8,5),
(452,7,11,3,5),
(453,7,12,3,5),
(454,7,13,3,5);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `pass` varchar(128) NOT NULL,
  `deleted_id` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user` */

insert  into `user`(`id`,`name`,`pass`,`deleted_id`) values 
(1,'kate','123456',0),
(5,'Катя','~ur\0\0',0),
(6,'Петя','~ur\0\0',0),
(7,'тест 123','~ur\0\0',7),
(12,'agness','whatislove',0),
(18,'agnessa','whatislove',0),
(22,'vlad','e^07agn',0),
(24,'elly','butterfly',0),
(33,'Jo','123456',0),
(34,'Zuko','honor123',0),
(35,'agness','5678765',0),
(36,'','',0),
(37,'Алиса','fox12345',0),
(38,'Таня','adminpass',0);

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int unsigned NOT NULL,
  `role_id` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_role_user1_idx` (`user_id`),
  KEY `fk_user_role_role1_idx` (`role_id`),
  CONSTRAINT `fk_user_role_role1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `fk_user_role_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user_role` */

insert  into `user_role`(`id`,`user_id`,`role_id`) values 
(4,6,2),
(5,5,1),
(6,5,2),
(7,18,1),
(11,22,2),
(18,33,1),
(19,34,1),
(20,12,1),
(21,36,1),
(22,1,1),
(23,37,1),
(24,38,2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
