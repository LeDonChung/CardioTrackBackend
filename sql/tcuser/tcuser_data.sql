-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.11.7-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for tcuser
CREATE DATABASE IF NOT EXISTS `tcuser` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `tcuser`;

-- Dumping structure for table tcuser.roles
CREATE TABLE IF NOT EXISTS `roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKch1113horj4qr56f91omojv8` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table tcuser.roles: ~3 rows (approximately)
INSERT INTO `roles` (`id`, `code`, `name`) VALUES
	(1, 'USER', 'USER'),
	(2, 'EXPERT', 'EXPERT'),
	(3, 'INVENTOR', 'INVENTOR');

-- Dumping structure for table tcuser.users
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dob` date DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `gender` tinyint(4) DEFAULT NULL CHECK (`gender` between 0 and 2),
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `verify` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table tcuser.users: ~102 rows (approximately)
INSERT INTO `users` (`id`, `dob`, `enabled`, `full_name`, `gender`, `password`, `username`, `verify`) VALUES
	(6, '1982-07-02', b'1', 'Phạm Vương Đỗ Đặng', NULL, '$2a$10$WZHmb.nYX1AQxTVMRYdOZOvrrSeU8L9bK6iZXjlxvG4BmccP8XDLG', '(340) 690-4563', b'1'),
	(7, '1987-05-16', b'1', 'Ngô Tô Đức Đặng', NULL, '$2a$10$sM7GDkYslZOMeykwKHFTbOjPHBH0ujqb7IljBVPIiwO4nxHSaOkge', '(340) 677-4824', b'1'),
	(8, '2005-04-09', b'1', 'Hà Thu Trần Phú', NULL, '$2a$10$sL.rQ.u6oPP7GGp5HtOBCunkKZ/qrh0vJVeT16Y7z95J/qEw0y.V6', '(340) 884-0767', b'1'),
	(9, '1983-01-11', b'1', 'Đặng Tiến Đỗ Đinh', NULL, '$2a$10$wqvpKLB24/GrtWypxnDBYOkYpXvWwQRBXhqS2PZHdPEiL63fAPuGu', '(340) 555-0688', b'1'),
	(10, '1963-08-29', b'1', 'Đoàn Tô', NULL, '$2a$10$CanKDPJ9nUXVC1XdGLAlb.pSPeUXacQkcm23RyYNOG.sLHj1VPTCK', '(340) 643-8603', b'1'),
	(11, '2003-04-14', b'1', 'Trịnh Kha', NULL, '$2a$10$e3jTtt7rRUwkcsmJRjjeiufthf2Pa6Sl9xSvX7Bl75edO4hUeDLfK', '(340) 344-5885', b'1'),
	(12, '1968-05-17', b'1', 'Tăng Trần Trương Kha', NULL, '$2a$10$KwwuROXmg6dwnpYM/wBbk.4YhK52fUOwPEpMnedOLf5eJTXPUx0xq', '(340) 249-9621', b'1'),
	(13, '1978-02-14', b'1', 'Hà Nhân Như Phạm', NULL, '$2a$10$FkF4bo1GfuIfiz5v2QIdXe00wxOg9kY74U77Pg0y1/ODxAgsGVs/m', '(340) 884-1458', NULL),
	(14, '1987-01-29', b'1', 'Đinh Ngô Long Nguyễn', NULL, '$2a$10$..aYfIlyfL11ovlDoY5PIO.W0oqmKuX8vR9xpUJSecOGjQNtBdLbO', '(340) 643-3972', NULL),
	(15, '1963-11-11', b'1', 'Nguyễn Khoa Văn', NULL, '$2a$10$q8wgFiTixd2C6D7fqxZmgO2hyQDCp3ghEAZmWT3k35lJ4cwE7WL3i', '(340) 725-2652', NULL),
	(16, '2001-01-10', b'1', 'Đỗ Sơn', NULL, '$2a$10$eMaaQJIWj3Nbz2f.JDgGsOo0x9vDrazEcoOhHMr0/IxiMxJWXw7ji', '(340) 998-1971', NULL),
	(17, '1967-09-29', b'1', 'Phạm Hòa', NULL, '$2a$10$m29Dml61DHVAcA.4MBmHM.pFKdB0mfk41jwh.Imp06I3amBuVCFOy', '(340) 513-4906', NULL),
	(18, '1988-08-31', b'1', 'Tô Thị', NULL, '$2a$10$ZuL05RahWaAcSWb8YxsLJuo0h1mcs/4JvsCQxa8kHNUp5lXgFfH8K', '(340) 344-1676', NULL),
	(19, '1975-04-09', b'1', 'Dương Nhân', NULL, '$2a$10$5eC2n.Uq2jxrriHPbqPR5eBi5ERIS7MaxuIEkCQplPoh4Ev3XmmLW', '(340) 884-4835', NULL),
	(20, '1986-01-06', b'1', 'Tô Vân Dương', NULL, '$2a$10$l8bgxjBSAlWpk2pKaSa1LeAQlsAaZrvr9tXBicp925CG3GDOdEzri', '(340) 642-6569', NULL),
	(21, '1992-09-28', b'1', 'Lâm Vũ Đỗ', NULL, '$2a$10$YWsmGwSux7BFphkzlQTtLOyxD0fyJTlAbW2xFqX3aABowrKnGU6ye', '(340) 473-7326', NULL),
	(22, '1979-05-02', b'1', 'Đoàn Minh', NULL, '$2a$10$DNRGzglF6mRq6/RLUl5VtuICtv4RkbjhqhyQRMT.M9XJNoLvB6T9C', '(340) 884-2053', NULL),
	(23, '1975-11-10', b'1', 'Dương Đào Khoa Hữu', NULL, '$2a$10$lBBAV8T018SzInikinC34.U6gTvSOq6PCoHi7iO1yt2pvhACSuW9C', '(340) 514-2639', NULL),
	(24, '1977-10-29', b'1', 'Hoàng Tăng Nguyễn Vân', NULL, '$2a$10$38RZeuZp5/w4ezbaIWQ6Ku9/x47h.ZsCzaoaSWJaZlwoajo5UpTBy', '(340) 998-9157', NULL),
	(25, '1980-05-21', b'1', 'Dương Vũ', NULL, '$2a$10$TlUJHgqOVsp2FUZHyBqGyOfn5wTLEzzdydNBokhzpaz5YArb7JXX2', '(340) 344-1416', NULL),
	(26, '1985-01-28', b'1', 'Lý Phú Cường Thủy', NULL, '$2a$10$FVoEkkmHjnufc7Q1TajAKeb8HC0PfgCoSPGSXNUHQnxXADpaVKDkC', '(340) 489-2020', NULL),
	(27, '1988-04-19', b'1', 'Hà Hồ', NULL, '$2a$10$obE7SlrUG5zNqnnshcFmwO8qtCYko/x7K3K37TiiQ50.ENP0KM17m', '(340) 998-6879', NULL),
	(28, '1983-12-31', b'1', 'Tăng Kha', NULL, '$2a$10$KYdovbloz14btne6OLxExu8hQUEitjJAxpqXCggV8IdADVrIjrpia', '(340) 444-8765', NULL),
	(29, '1982-10-04', b'1', 'Lý Đào Thắm', NULL, '$2a$10$i4aUb8RsCDecU8oRInK7MenVozIHGW92dD702HsdNZ3Gk/tpjMViS', '(340) 998-7621', NULL),
	(30, '1983-04-12', b'1', 'Mai Long', NULL, '$2a$10$r0/RPOWqhlOIU9zdtct7xOAxoKaCRp04c9HfF.Ghc4Z06u46s7KHq', '(340) 884-3865', NULL),
	(31, '1991-01-12', b'1', 'Phan Bửu Đỗ', NULL, '$2a$10$eSWN6RM5NU39SkyAPB3sqOlgP.KHCNn5qURsqdnb64rxxoE5JXIFe', '(340) 884-8078', NULL),
	(32, '1986-03-23', b'1', 'Lâm Vương Hoàng Văn', NULL, '$2a$10$wSs.ewL83plVMa0gv6d8IuWQ7HleJHDQnMmN2np59CDCcelUF963K', '(340) 884-3313', NULL),
	(33, '1971-06-26', b'1', 'Lý Quốc', NULL, '$2a$10$tVSq69ihY60PZMJR9tLv4uNHGwkOmvS9yc6gFFd1YDykhliHI00ma', '(340) 998-9262', NULL),
	(34, '1981-08-17', b'1', 'Đặng Kim', NULL, '$2a$10$EAKb/wuUBBfJ4v9ztno1o.exUSWaCrHA2eJtnTpJ48kZM5HJEfj5i', '(340) 277-1531', NULL),
	(35, '1992-06-04', b'1', 'Trương Sa Nam', NULL, '$2a$10$tP9iCABDBo7POd7V9Z.uO.vDbU7T3e9uyyiiso6JWqVEUc3PlJqeS', '(340) 332-5441', NULL),
	(36, '1961-09-29', b'1', 'Hồ Khang Phạm Bùi', NULL, '$2a$10$GA3Jx2MTxPpqWmQzncifL.OCebBlsObmif8fk5cAvgDE65BQQw8rm', '(340) 489-5395', NULL),
	(37, '1963-05-21', b'1', 'Lâm Vinh Trương', NULL, '$2a$10$Kti11vVGRwqlQs7FIXNsYOF7IFUtvbLufkkzbtKFEbojloUuKZwi.', '(340) 626-6206', NULL),
	(38, '2002-06-03', b'1', 'Phan Đào', NULL, '$2a$10$cM0a8.igH4dWmxitwcJLqO7dPwKDr1oDhZaEX0o4LgJz8lndFQZfe', '(340) 332-9177', NULL),
	(39, '1994-07-31', b'1', 'Trần Lê', NULL, '$2a$10$rAtUBRc9lS3.bKL.yAxDdeVGJkpUB0HYLxT.BB507uNNoVTNau9J2', '(340) 332-8397', NULL),
	(40, '1960-08-13', b'1', 'Đỗ Hoàng Hòa', NULL, '$2a$10$ZmlpAiNZyAFI9uE2czrPS.lUNh9mS/l2gIBhvc2KjIcGrKijYnheK', '(340) 714-9659', NULL),
	(41, '1988-12-15', b'1', 'Tô Sinh Sa Dung', NULL, '$2a$10$mzY7h8/9gJnX.IhCzZhyveiM1CEDIwOy7mAjWAgFQrCCeG22dT0IO', '(340) 884-4360', NULL),
	(42, '1973-09-10', b'1', 'Tô Trinh Tiến Long', NULL, '$2a$10$JPy8ydg0SS7/UwIV//.gFewPRpCSTl7p0oKKpJetSkB/PnGEiwaV6', '(340) 998-5757', NULL),
	(43, '1985-01-28', b'1', 'Dương Quốc Vũ Phan', NULL, '$2a$10$4kjNhwd9n1dksnRi7XIrbepfVvHMABvY6ukywOtzrLWWUH0Rk8ElK', '(340) 332-8903', NULL),
	(44, '1994-10-29', b'1', 'Tăng Đỗ', NULL, '$2a$10$8iWN.8TunBoRfL3r4nXX6uYpaAV9V2x5WXP//mg6jAZ.4DXInTnXG', '(340) 774-8697', NULL),
	(45, '1968-01-11', b'1', 'Hoàng Nhàn', NULL, '$2a$10$uEEm9Mp6oKhhAA6Ioi//Ueykf/ex.WhPm6zstapHaWaiaAQkGvLLu', '(340) 332-5470', NULL),
	(46, '2005-10-29', b'1', 'Đoàn Như Mai', NULL, '$2a$10$LGEtfp5m4P5UQPWUkkmzYuC0ZshOXEFguFzFo7JhzLcH0UFK6zriq', '(340) 277-5104', NULL),
	(47, '1963-03-21', b'1', 'Ngô Sơn Tăng', NULL, '$2a$10$8v9PWTIUMEwcWFqld6rQgeU2Gh9wUPlyBHM.rErhl3izBAjf5EY0q', '(340) 727-3712', NULL),
	(48, '1975-10-04', b'1', 'Hồ Tô', NULL, '$2a$10$31GalUaBNcgUDFZ.MC29beNsPPoTQBILQki1lKh6jpEelrE0knC1i', '(340) 201-8207', NULL),
	(49, '1990-11-17', b'1', 'Dương Đoàn', NULL, '$2a$10$7sbzy3kbuTyConfkQT1zDO5/Sq3u9w6lAQPoew5L.DgU76Ub9Tqre', '(340) 713-7543', NULL),
	(50, '1999-08-26', b'1', 'Trịnh Dung Trịnh Kha', NULL, '$2a$10$vblCMSArdJ8w5tn/TyKsAOoznH.dxDMsNn12CeZgLM07VLB0xHWZq', '(340) 643-6464', NULL),
	(51, '1975-10-19', b'1', 'Bùi Thu Ngọc', NULL, '$2a$10$0Y3hJTftTbI2Ft4QKsq4quilXKme2EqqmM9ngZTfHqYHsIUocTSr2', '(340) 725-4671', NULL),
	(52, '1984-11-13', b'1', 'Đoàn Phạm', NULL, '$2a$10$e8jmsJ.GHMBErZGAETcKZO85VlXzTCMAJuEy2eVCxfxxYKeGOuA9K', '(340) 884-2965', NULL),
	(53, '1984-06-24', b'1', 'Tăng Tô Hòa', NULL, '$2a$10$SAI1LsjeAcU6Hvr86vIsZew2mTF0dxoNoaid8DODnn04bXK/cfCEq', '(340) 249-6836', NULL),
	(54, '1991-01-06', b'1', 'Ngô Sơn Thi', NULL, '$2a$10$8aaJlmjxPtHM5i9DOx7cMuwi9ejiV0xhRb5z/fajKAUHrOJ.Xw2pS', '(340) 555-4061', NULL),
	(55, '1981-08-21', b'1', 'Mai Thắm', NULL, '$2a$10$9vcg4S47zFw/jpCcEtz/A.QqUI1gv3L9XL4ho9YBrhQJMlP1QMKEa', '(340) 727-6812', NULL),
	(56, '1967-02-21', b'1', 'Trần Hiền Loan', NULL, '$2a$10$oDoYLuGClgFc/b8Xgt/ItuA3bIzK5fHsKNfa806f0BOvhAN5VcjxK', '(340) 513-9623', NULL),
	(57, '1976-12-19', b'1', 'Hoàng Nam', NULL, '$2a$10$iTWE8bM3WmWVAB7Oprm2m.SJrdaH0RlMOFGkztxZ9qsOfuQcv9gN.', '(340) 444-7737', NULL),
	(58, '1980-06-11', b'1', 'Đỗ Bửu', NULL, '$2a$10$2WIiUAiCZsFPxz4jtnr7b.SE38VOlYUvcjfjmLBrVUKp3JRi2Ktw.', '(340) 727-1228', NULL),
	(59, '1982-02-17', b'1', 'Hoàng Triệu', NULL, '$2a$10$SyMuxaG6OqmBCo5RqdOcFuRvpJw0OL/yolbVh1YRPgEAP0WmAjD2q', '(340) 249-8327', NULL),
	(60, '1963-04-26', b'1', 'Nguyễn Tăng Khang Nhân', NULL, '$2a$10$SQ.h47JI8HLQTxGzxVwpQ.iitILIZ3J/c9.jELY.2lSpwTGSchU8a', '(340) 998-5239', NULL),
	(61, '1986-07-28', b'1', 'Bùi Đoàn Tú', NULL, '$2a$10$99RO2ezPblspNmaAIl83dOLGaHlzI51GfROvx7/wC9D9Rd9iQrGdC', '(340) 714-4816', NULL),
	(62, '1960-01-02', b'1', 'Trương Sinh Lý', NULL, '$2a$10$T9DbS52XLlJ0EtonCByup.P5YykVwqJtQaw41s0C07wIJUw9OuMe.', '(340) 210-0319', NULL),
	(63, '1967-08-23', b'1', 'Hoàng Thị Thắng Hòa', NULL, '$2a$10$wH0s9OVkUJ2IwL2q9qjqkOfVQFtqUSCZOFHH5iNjJ5G6JaYTAyJby', '(340) 884-5074', NULL),
	(64, '1992-02-29', b'1', 'Phùng Vân', NULL, '$2a$10$W5xawXaaAfwsuetLyp07AO5FxpmwAY29/6J3ZQz25W.5TEzOYD2ye', '(340) 642-1432', NULL),
	(65, '1979-10-14', b'1', 'Dương Vinh Trịnh', NULL, '$2a$10$Co9PCCPyQ//LcdZSQnlNx.9W.YJLF7lNGnv3PpCAFw9.s7FywQ.jq', '(340) 998-2966', NULL),
	(66, '2004-05-23', b'1', 'Phùng Kha Hòa Phùng', NULL, '$2a$10$LpYh4R48pHrgZhRp.kY/teQkCsZt3Ocmcwyq3wcfJX3jO.mWVcUbe', '(340) 209-6369', NULL),
	(67, '2001-04-21', b'1', 'Vũ Phong Thu', NULL, '$2a$10$JVngnZbcqcxZW2kGZSPtl.Xlb8.GEeOV.caQ4M6MTxajVBvKY.YZK', '(340) 210-2609', NULL),
	(68, '1978-12-01', b'1', 'Ngô Tăng Tuân Phan', NULL, '$2a$10$O1vKGmlzLgFH9SvwRKXZ1eNnls1AAxRrtNWd8IRuV2tXqelkH198u', '(340) 884-9308', NULL),
	(69, '1973-03-09', b'1', 'Hà Thắng Hữu Tăng', NULL, '$2a$10$Wnc7wm6kM.sbKUosH/.KUeA4VKWzBS6wNKnnCNjdwA2igTLKj8q5O', '(340) 489-1043', NULL),
	(70, '1990-12-21', b'1', 'Nguyễn Triệu Thủy Đào', NULL, '$2a$10$uSGsQhwQ4FB8NR9sKjxKIuC79CStkeQ1g4MjGsLzZNUhR6iqYx1Gi', '(340) 226-8062', NULL),
	(71, '2006-07-07', b'1', 'Đặng Tăng', NULL, '$2a$10$UWdpPHtUP5NvoeF1pKRUsewV1mzYcTWOra5C/gvr2wj2vw00iVMaa', '(340) 998-4056', NULL),
	(72, '1988-06-07', b'1', 'Phan Phong Nguyễn', NULL, '$2a$10$6xKiiBHkKw4zRCYMXuRzVOZH.Me1garHtAk01elYhVI8QfKl1TJjS', '(340) 344-4527', NULL),
	(73, '1987-02-13', b'1', 'Nguyễn Triệu Hà Hòa', NULL, '$2a$10$LjtByCTaVVdR4bb3pjMRueIcSBPcoTYhjYMbt5X2qsb6mAfMRdkfK', '(340) 249-5617', NULL),
	(74, '1987-01-20', b'1', 'Phùng Phạm Bùi', NULL, '$2a$10$0o0eatKW1FCtaTMJwxsgxeoiGWd9L2AJVeDk9glILVRFx8o.Wx5Wa', '(340) 725-0789', NULL),
	(75, '2004-04-24', b'1', 'Đặng Thi', NULL, '$2a$10$rdHiSKcg3hQss6BQESQ1rOxbRP5Z51r7WE0bqFfL6ipJJ1B.JQfea', '(340) 513-7650', NULL),
	(76, '1994-08-16', b'1', 'Lê Như', NULL, '$2a$10$bbW/rs7P8IGKoVl9WY5RQ.QK0wObv0sLsFAcJ6sHqLHxUdp1txcbu', '(340) 884-1007', NULL),
	(77, '2001-12-10', b'1', 'Lý Hữu Tăng Lâm', NULL, '$2a$10$E4yDAWbaELs/Xsj35wc4AuvRgRHdpPYnBqs3EAo29C1GbkNtS.kXm', '(340) 514-9989', NULL),
	(78, '1982-10-30', b'1', 'Hoàng Nguyễn Thi', NULL, '$2a$10$Lp2C2ppmo47zDmgCyacoN.jr7HIjKPddJ2a6X0SG7nR5siypeoQv6', '(340) 344-3314', NULL),
	(79, '1968-11-04', b'1', 'Bùi Trương Hữu Trương', NULL, '$2a$10$QdcIiydWAYrt1LEe8QvMhOmuAuQ2UUFp2xkGCEjkmY8XFE4ulVy9e', '(340) 643-5612', NULL),
	(80, '2002-09-29', b'1', 'Đoàn Long Dương', NULL, '$2a$10$kVPDwWzzWmTdxDDmgcwAvuU4t9fBG2aQBQfA1MRSElKPyQ8FlxTlC', '(340) 884-8231', NULL),
	(81, '1977-10-09', b'1', 'Trần Cường', NULL, '$2a$10$sr./hOrNBZyGOuuYy64ZUOVmvVvukUOz2pT4RF1VfyRYJ5BM4eAJi', '(340) 998-2165', NULL),
	(82, '1972-08-24', b'1', 'Đỗ Thu', NULL, '$2a$10$EKL3Mleqp2SrTmt9CLN/kukW6.WFIReYHjZAtdBjWStaRkdu0PvV2', '(340) 693-2782', NULL),
	(83, '1967-10-21', b'1', 'Hồ Nhân', NULL, '$2a$10$Y.OqpXcLTihhfNovRtq0c.beKxBifQFQnzzgxtJxt0eH26y3cX3wK', '(340) 555-4254', NULL),
	(84, '1964-12-27', b'1', 'Bùi Thắm Lâm', NULL, '$2a$10$yJWSNqTBujgkDSBC6QnF.uClEONapH8OLmMX76uuT1GzYsAlpMW/m', '(340) 642-3388', NULL),
	(85, '1978-02-15', b'1', 'Phùng Dương Hiền', NULL, '$2a$10$s798js2ZOnPlSwPyKoqBPe20DW5Shqx5WzFViuTrPZym/9eMBBQ8W', '(340) 489-1980', NULL),
	(86, '1969-05-26', b'1', 'Phùng Khang Nhung Vương', NULL, '$2a$10$5YWi6xycHJtMyX/SM3qn5eVSRoIwedH0nS5He29ywrgtZ3SrIgO/O', '(340) 210-5044', NULL),
	(87, '1987-12-22', b'1', 'Hoàng Quốc Nguyễn Đức', NULL, '$2a$10$5s9QRSKBnUbqch5.caWcL.PYdQKkbjECifY/xg5ii8J5W7Rmdjblm', '(340) 344-0234', NULL),
	(88, '1963-08-06', b'1', 'Vương Thủy', NULL, '$2a$10$wdnpYc7InCX2WeO4U7CYo.UJsqpiK0b/PtUuti2P2HTpHu6y.hY8q', '(340) 423-0021', NULL),
	(89, '1991-02-19', b'1', 'Đặng Thị Tâm', NULL, '$2a$10$ev1SkZUzXKix/evb6zwao.v04F8wvrc5yGFSe8eWXvCTpfOs8e88K', '(340) 514-1846', NULL),
	(90, '1998-09-06', b'1', 'Đinh Thủy Văn', NULL, '$2a$10$CiQ3.2NzfIQzWA5uPaQfu./YP3wsLEL0HL3fM4ivBS2RMcoxrLaOq', '(340) 204-7765', NULL),
	(91, '2005-06-20', b'1', 'Hồ Trinh', NULL, '$2a$10$uouwz2C43tPAAU34BeHcaeedqohTAN6rRNuEeXQMw7IZBA7T0tYkK', '(340) 332-5157', NULL),
	(92, '1968-02-23', b'1', 'Phan Dương Ngọc', NULL, '$2a$10$fVDZG9SxzD/xrOS/mI6aPug1aiAgwMLpDzZx8W2Kb3O92D/tOasMC', '(340) 777-8893', NULL),
	(93, '1960-07-26', b'1', 'Hoàng Ngô', NULL, '$2a$10$S0Ehp0Z.mfuE1Dw/xggkuONU6EKUoeowy/b5o1HeqA1u1f2tlAKam', '(340) 332-9687', NULL),
	(94, '1969-06-09', b'1', 'Ngô Phan Thảo Khang', NULL, '$2a$10$iomOhyengJySPxI8bs3Ur.Eghq2W340MLS.pKuIvpmsa1y1h5oYui', '(340) 344-4799', NULL),
	(95, '1974-03-19', b'1', 'Vương Phú Đào Hà', NULL, '$2a$10$MemG7RfF6On/sxUz83am3..YqUalPC7mbGkElI9ChpraQr4vNHdx6', '(340) 998-7717', NULL),
	(96, '2006-02-15', b'1', 'Dương Thủy Thanh Tô', NULL, '$2a$10$nBJwYg7riG/xmD.QCcY9qed6CK/0jBwa1m7cMisua4cuSxAUYCicG', '(340) 884-8580', NULL),
	(97, '1975-03-04', b'1', 'Đinh Hải', NULL, '$2a$10$RontaFuQ5mNCkLDf9N6Sd.fNC7wbMpUJ/R7cQE29nVIIhiTaRpl6a', '(340) 998-4414', NULL),
	(98, '1971-07-12', b'1', 'Lê Kim Sa Thị', NULL, '$2a$10$9I/lXSx.b1b5OL6PiXH45evKHrjnEcmN0UEW.vxWCjSOSnN9Xigme', '(340) 332-4291', NULL),
	(99, '1965-07-14', b'1', 'Lý Khang Sinh', NULL, '$2a$10$9PBPKhKGAumYhKpj4t..8ejd7OVnzYlgYYq6jwRuRJprlZUUywnfW', '(340) 998-7608', b'1'),
	(100, '2002-10-10', b'1', 'Lâm Thắng Thắm', NULL, '$2a$10$O4rGGnwpWF315PmIqMD8f.5fPg8eXRDDdGVbMhSCNFK2KLKKYvtqy', '(340) 514-5227', b'1'),
	(101, '1997-06-30', b'1', 'Lâm Tăng', NULL, '$2a$10$XdOdMIYtMsM8pgwMXykoQekQeZsRicWDWSQpVWNwReEgGx3G2olD6', '(340) 727-5853', b'1'),
	(102, '1984-04-23', b'1', 'Đào Nguyễn', NULL, '$2a$10$aY3pnJjb58ALirzJtRqOtu7cRbHBpcvSbLoyK2qe0swieeb/xy3qi', '(340) 473-5349', b'1'),
	(103, '2002-08-21', b'1', 'Phạm Trinh Nhân Kha', NULL, '$2a$10$GWxp66s.FdeO3pNwuXpWgOK7U0PoQL2NDPH1ctsVrULn4CuTqApzu', '(340) 713-2484', b'1'),
	(104, '1988-10-10', b'1', 'Phùng Vân Đoàn Long', NULL, '$2a$10$IB/PySX0RgaAE7FXHJFPX.3oWy9xmOzQ2xITABrrqVpbvwSCVsv5i', '(340) 725-4143', b'1'),
	(105, '1976-11-21', b'1', 'Tăng Lâm Tuân Hòa', NULL, '$2a$10$uBGssU/F8ve7.35sVBzOo.tJjf7JZbojCQkBGXyUhalG9q/gG.ojK', '(340) 200-2901', b'1'),
	(106, '1993-05-27', b'1', 'Trương Lý Đinh Tô', NULL, '$2a$10$.TGAh7ZeGSE4eDXrwSTAd.a5jPZIoeuQS8v2lu.z4aU8/nEOqeDKC', '(340) 228-5117', b'1'),
	(109, NULL, b'1', NULL, NULL, '$2a$10$42kgIJJiDibXFYlZXBcaHOEbYQYmMrVZvi6VDJrBe719gQpgA.j56', '', b'1');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
