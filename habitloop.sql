-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th6 02, 2026 lúc 08:48 AM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.1.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `habitloop`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `activity_feed`
--

CREATE TABLE `activity_feed` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `message` varchar(255) NOT NULL,
  `challenge_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `activity_feed`
--

INSERT INTO `activity_feed` (`id`, `created_at`, `message`, `challenge_id`, `user_id`) VALUES
(6, '2026-06-01 15:13:35.000000', 'NguyenVanA vừa check-in! 🔥', 2, 5),
(7, '2026-06-01 15:13:53.000000', 'TamHuy vừa check-in! 🔥', 2, 4),
(8, '2026-06-01 15:14:41.000000', 'NguyenVanB đã tham gia challenge! 🎉', 2, 6),
(9, '2026-06-01 15:18:00.000000', 'NguyenVanB vừa check-in! 🔥', 2, 6),
(10, '2026-06-01 18:49:56.000000', 'NguyenVanC vừa check-in! 🔥', 3, 7),
(11, '2026-06-01 18:51:01.000000', 'TamHuy đã tham gia challenge! 🎉', 3, 4),
(12, '2026-06-01 18:51:09.000000', 'TamHuy vừa check-in! 🔥', 3, 4),
(13, '2026-06-01 18:56:48.000000', 'NguyenPhucTamHuy vừa check-in! 🔥', 4, 8),
(14, '2026-06-01 18:57:16.000000', 'TamHuy đã tham gia challenge! 🎉', 4, 4),
(15, '2026-06-01 18:57:34.000000', 'TamHuy vừa check-in! 🔥', 4, 4);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `badges`
--

CREATE TABLE `badges` (
  `id` bigint(20) NOT NULL,
  `condition_type` varchar(50) DEFAULT NULL,
  `condition_value` int(11) DEFAULT NULL,
  `icon` varchar(10) DEFAULT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `badges`
--

INSERT INTO `badges` (`id`, `condition_type`, `condition_value`, `icon`, `name`) VALUES
(1, 'STREAK_7', 7, '🥉', 'Streak 7 ngày'),
(2, 'STREAK_30', 30, '🥈', 'Streak 30 ngày'),
(3, 'STREAK_100', 100, '🥇', 'Streak 100 ngày'),
(4, 'STREAK_7', 7, '🥉', 'Streak 7 ngày'),
(5, 'STREAK_30', 30, '🥈', 'Streak 30 ngày'),
(6, 'STREAK_100', 100, '🥇', 'Streak 100 ngày');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `challenges`
--

CREATE TABLE `challenges` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `end_date` date NOT NULL,
  `invite_code` varchar(10) DEFAULT NULL,
  `is_public` bit(1) DEFAULT NULL,
  `start_date` date NOT NULL,
  `title` varchar(100) NOT NULL,
  `creator_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `challenges`
--

INSERT INTO `challenges` (`id`, `created_at`, `description`, `end_date`, `invite_code`, `is_public`, `start_date`, `title`, `creator_id`) VALUES
(2, '2026-06-01 12:21:54.000000', 'Có đủ vốn từ vựng trong 3 tháng', '2026-09-01', '6FA02B', b'1', '2026-06-01', 'Học 15 từ vựng tiếng anh mỗi ngày', 4),
(3, '2026-06-01 18:49:33.000000', 'Dậy sớm và chạy bộ mỗi buổi sáng để tập thể dụng', '2026-07-01', 'CB71AD', b'1', '2026-06-01', 'Chạy bộ mỗi sáng trong 30 ngày', 7),
(4, '2026-06-01 18:56:26.000000', 'Dậy sớm và tập thể dụng vận động', '2026-07-01', '7BE402', b'0', '2026-06-01', 'Chạy bộ mỗi buổi sáng ', 8);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `challenge_members`
--

CREATE TABLE `challenge_members` (
  `id` bigint(20) NOT NULL,
  `joined_at` datetime(6) DEFAULT NULL,
  `total_checkins` int(11) DEFAULT NULL,
  `challenge_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `challenge_members`
--

INSERT INTO `challenge_members` (`id`, `joined_at`, `total_checkins`, `challenge_id`, `user_id`) VALUES
(2, '2026-06-01 12:21:55.000000', 1, 2, 4),
(3, '2026-06-01 15:01:41.000000', 1, 2, 5),
(4, '2026-06-01 15:14:41.000000', 1, 2, 6),
(5, '2026-06-01 18:49:33.000000', 1, 3, 7),
(6, '2026-06-01 18:51:01.000000', 1, 3, 4),
(7, '2026-06-01 18:56:26.000000', 1, 4, 8),
(8, '2026-06-01 18:57:16.000000', 1, 4, 4);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `checkins`
--

CREATE TABLE `checkins` (
  `id` bigint(20) NOT NULL,
  `checkin_date` date NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  `habit_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `checkins`
--

INSERT INTO `checkins` (`id`, `checkin_date`, `note`, `habit_id`, `user_id`) VALUES
(3, '2026-06-01', NULL, 3, 4),
(4, '2026-02-21', NULL, 5, 4),
(5, '2026-02-22', NULL, 5, 4),
(6, '2026-02-23', NULL, 5, 4),
(7, '2026-02-24', NULL, 5, 4),
(8, '2026-02-25', NULL, 5, 4),
(9, '2026-02-26', NULL, 5, 4),
(10, '2026-02-27', NULL, 5, 4),
(11, '2026-02-28', NULL, 5, 4),
(12, '2026-03-01', NULL, 5, 4),
(13, '2026-03-02', NULL, 5, 4),
(14, '2026-03-03', NULL, 5, 4),
(15, '2026-03-04', NULL, 5, 4),
(16, '2026-03-05', NULL, 5, 4),
(17, '2026-03-06', NULL, 5, 4),
(18, '2026-03-07', NULL, 5, 4),
(19, '2026-03-08', NULL, 5, 4),
(20, '2026-03-09', NULL, 5, 4),
(21, '2026-03-10', NULL, 5, 4),
(22, '2026-03-11', NULL, 5, 4),
(23, '2026-03-12', NULL, 5, 4),
(24, '2026-03-13', NULL, 5, 4),
(25, '2026-03-14', NULL, 5, 4),
(26, '2026-03-15', NULL, 5, 4),
(27, '2026-03-16', NULL, 5, 4),
(28, '2026-03-17', NULL, 5, 4),
(29, '2026-03-18', NULL, 5, 4),
(30, '2026-03-19', NULL, 5, 4),
(31, '2026-03-20', NULL, 5, 4),
(32, '2026-03-21', NULL, 5, 4),
(33, '2026-03-22', NULL, 5, 4),
(34, '2026-03-23', NULL, 5, 4),
(35, '2026-03-24', NULL, 5, 4),
(36, '2026-03-25', NULL, 5, 4),
(37, '2026-03-26', NULL, 5, 4),
(38, '2026-03-27', NULL, 5, 4),
(39, '2026-03-28', NULL, 5, 4),
(40, '2026-03-29', NULL, 5, 4),
(41, '2026-03-30', NULL, 5, 4),
(42, '2026-03-31', NULL, 5, 4),
(43, '2026-04-01', NULL, 5, 4),
(44, '2026-04-02', NULL, 5, 4),
(45, '2026-04-03', NULL, 5, 4),
(46, '2026-04-04', NULL, 5, 4),
(47, '2026-04-05', NULL, 5, 4),
(48, '2026-04-06', NULL, 5, 4),
(49, '2026-04-07', NULL, 5, 4),
(50, '2026-04-08', NULL, 5, 4),
(51, '2026-04-09', NULL, 5, 4),
(52, '2026-04-10', NULL, 5, 4),
(53, '2026-04-11', NULL, 5, 4),
(54, '2026-04-12', NULL, 5, 4),
(55, '2026-04-13', NULL, 5, 4),
(56, '2026-04-14', NULL, 5, 4),
(57, '2026-04-15', NULL, 5, 4),
(58, '2026-04-16', NULL, 5, 4),
(59, '2026-04-17', NULL, 5, 4),
(60, '2026-04-18', NULL, 5, 4),
(61, '2026-04-19', NULL, 5, 4),
(62, '2026-04-20', NULL, 5, 4),
(63, '2026-04-21', NULL, 5, 4),
(64, '2026-04-22', NULL, 5, 4),
(65, '2026-04-23', NULL, 5, 4),
(66, '2026-04-24', NULL, 5, 4),
(67, '2026-04-25', NULL, 5, 4),
(68, '2026-04-26', NULL, 5, 4),
(69, '2026-04-27', NULL, 5, 4),
(70, '2026-04-28', NULL, 5, 4),
(71, '2026-04-29', NULL, 5, 4),
(72, '2026-04-30', NULL, 5, 4),
(73, '2026-05-01', NULL, 5, 4),
(74, '2026-05-02', NULL, 5, 4),
(75, '2026-05-03', NULL, 5, 4),
(76, '2026-05-04', NULL, 5, 4),
(77, '2026-05-05', NULL, 5, 4),
(78, '2026-05-06', NULL, 5, 4),
(79, '2026-05-07', NULL, 5, 4),
(80, '2026-05-08', NULL, 5, 4),
(81, '2026-05-09', NULL, 5, 4),
(82, '2026-05-10', NULL, 5, 4),
(83, '2026-05-11', NULL, 5, 4),
(84, '2026-05-12', NULL, 5, 4),
(85, '2026-05-13', NULL, 5, 4),
(86, '2026-05-14', NULL, 5, 4),
(87, '2026-05-15', NULL, 5, 4),
(88, '2026-05-16', NULL, 5, 4),
(89, '2026-05-17', NULL, 5, 4),
(90, '2026-05-18', NULL, 5, 4),
(91, '2026-05-19', NULL, 5, 4),
(92, '2026-05-20', NULL, 5, 4),
(93, '2026-05-21', NULL, 5, 4),
(94, '2026-05-22', NULL, 5, 4),
(95, '2026-05-23', NULL, 5, 4),
(96, '2026-05-24', NULL, 5, 4),
(97, '2026-05-25', NULL, 5, 4),
(98, '2026-05-26', NULL, 5, 4),
(99, '2026-05-27', NULL, 5, 4),
(100, '2026-05-28', NULL, 5, 4),
(101, '2026-05-29', NULL, 5, 4),
(102, '2026-05-30', NULL, 5, 4),
(103, '2026-05-31', NULL, 5, 4),
(131, '2026-06-01', NULL, 6, 4),
(132, '2026-05-31', NULL, 6, 4),
(133, '2026-05-30', NULL, 6, 4),
(134, '2026-05-29', NULL, 6, 4),
(135, '2026-05-28', NULL, 6, 4),
(136, '2026-05-27', NULL, 6, 4),
(137, '2026-05-26', NULL, 6, 4),
(138, '2026-05-25', NULL, 6, 4),
(139, '2026-05-24', NULL, 6, 4),
(140, '2026-05-23', NULL, 6, 4),
(141, '2026-05-22', NULL, 6, 4),
(142, '2026-05-21', NULL, 6, 4),
(143, '2026-05-20', NULL, 6, 4),
(144, '2026-05-19', NULL, 6, 4),
(145, '2026-05-18', NULL, 6, 4),
(146, '2026-05-17', NULL, 6, 4),
(147, '2026-05-16', NULL, 6, 4),
(148, '2026-05-15', NULL, 6, 4),
(149, '2026-05-14', NULL, 6, 4),
(150, '2026-05-13', NULL, 6, 4),
(151, '2026-05-12', NULL, 6, 4),
(152, '2026-05-11', NULL, 6, 4),
(153, '2026-05-10', NULL, 6, 4),
(154, '2026-05-09', NULL, 6, 4),
(155, '2026-05-08', NULL, 6, 4),
(156, '2026-05-07', NULL, 6, 4),
(157, '2026-05-06', NULL, 6, 4),
(158, '2026-05-05', NULL, 6, 4),
(159, '2026-05-04', NULL, 6, 4),
(160, '2026-05-03', NULL, 6, 4),
(162, '2026-06-01', NULL, 7, 4),
(163, '2026-05-31', NULL, 7, 4),
(164, '2026-05-30', NULL, 7, 4),
(165, '2026-05-29', NULL, 7, 4),
(166, '2026-05-28', NULL, 7, 4),
(167, '2026-05-27', NULL, 7, 4),
(168, '2026-05-26', NULL, 7, 4),
(169, '2026-06-01', NULL, 8, 4),
(170, '2026-05-31', NULL, 8, 4),
(171, '2026-05-30', NULL, 8, 4),
(172, '2026-05-29', NULL, 8, 4),
(173, '2026-05-28', NULL, 8, 4),
(174, '2026-05-27', NULL, 8, 4),
(175, '2026-05-26', NULL, 8, 4),
(176, '2026-05-25', NULL, 8, 4),
(177, '2026-05-24', NULL, 8, 4),
(178, '2026-05-23', NULL, 8, 4),
(179, '2026-05-22', NULL, 8, 4),
(180, '2026-05-21', NULL, 8, 4),
(181, '2026-05-20', NULL, 8, 4),
(182, '2026-05-19', NULL, 8, 4),
(183, '2026-05-18', NULL, 8, 4),
(184, '2026-06-01', NULL, 10, 7),
(185, '2026-06-01', NULL, 11, 8),
(186, '2026-06-01', NULL, 9, 4),
(187, '2026-06-01', NULL, 5, 4);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `habits`
--

CREATE TABLE `habits` (
  `id` bigint(20) NOT NULL,
  `color` varchar(20) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `current_streak` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `frequency` enum('DAILY','WEEKLY') NOT NULL,
  `icon` varchar(10) DEFAULT NULL,
  `longest_streak` int(11) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `habits`
--

INSERT INTO `habits` (`id`, `color`, `created_at`, `current_streak`, `description`, `frequency`, `icon`, `longest_streak`, `name`, `user_id`) VALUES
(3, '#eeff00', '2026-06-01 12:20:50.000000', 1, 'Đọc ít nhất 10 quyển 1 ngày', 'DAILY', '📚', 1, 'Đọc sách 30p vào buổi sáng', 4),
(5, '#6366f1', '2026-06-01 15:19:50.000000', 101, 'Đọc ít nhất 20 trang mỗi ngày', 'DAILY', '📚', 101, 'Đọc sách 30 phút', 4),
(6, '#10b981', '2026-06-01 15:19:50.000000', 30, 'Chạy 3km mỗi sáng', 'DAILY', '🏃', 30, 'Chạy bộ buổi sáng', 4),
(7, '#f59e0b', '2026-06-01 15:19:50.000000', 7, 'Thiền buổi sáng trước khi làm việc', 'DAILY', '🧘', 7, 'Thiền 10 phút', 4),
(8, '#3b82f6', '2026-06-01 15:19:50.000000', 15, 'Uống đủ nước mỗi ngày', 'DAILY', '💧', 15, 'Uống 2 lít nước', 4),
(9, '#6366f1', '2026-06-01 15:27:27.000000', 1, '', 'DAILY', '💧', 1, 'Uống nước 3 lần mỗi buổi', 4),
(10, '#ff0000', '2026-06-01 18:48:09.000000', 1, 'Học tập về java spring boot', 'DAILY', '✍️', 1, 'Học về spring boot mỗi ngày', 7),
(11, '#fb4b4b', '2026-06-01 18:55:06.000000', 1, 'Học java spring boot mỗi ngày ', 'DAILY', '✍️', 1, 'Học về java spring boot ', 8);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `notifications`
--

CREATE TABLE `notifications` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `is_read` bit(1) DEFAULT NULL,
  `message` varchar(255) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `notifications`
--

INSERT INTO `notifications` (`id`, `created_at`, `is_read`, `message`, `user_id`) VALUES
(7, '2026-06-01 15:20:41.000000', b'1', '⏰ Challenge \"Học 15 từ vựng tiếng anh mỗi ngày\" sẽ kết thúc vào 2026-09-01!', 4),
(8, '2026-06-01 14:20:41.000000', b'1', '🥇 Chúc mừng! Bạn đã đạt streak 100 ngày liên tiếp!', 4),
(9, '2026-05-30 15:20:41.000000', b'1', '🥈 Bạn đã đạt streak 30 ngày. Tuyệt vời!', 4),
(10, '2026-05-22 15:20:41.000000', b'1', '🥉 Bạn đã đạt streak 7 ngày đầu tiên!', 4);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `level` int(11) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(50) NOT NULL,
  `xp` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`id`, `created_at`, `email`, `level`, `password`, `username`, `xp`) VALUES
(4, '2026-06-01 12:04:12.000000', 'tamhuy@gmail.com', 13, '$2a$10$YXg.5dJ7QQrbFaCCgY99suG.LCiRUDpOk8ieoHQEPFTa5ZLJcHk2S', 'TamHuy', 1270),
(5, '2026-06-01 15:00:29.000000', 'NguyenVanA@gmail.com', 1, '$2a$10$hFZijeA4jDWiOriGwzGYO.eVvMHbQwc6BcqMuILBAeE/2hRwkkjJi', 'NguyenVanA', 0),
(6, '2026-06-01 15:14:21.000000', 'NguyenVanB@gmail.com', 1, '$2a$10$qFQk4mHjZmhsOX4x0KaePOKs0igB6kOwTqDHO5CLXYn8DQqIUwRQ.', 'NguyenVanB', 0),
(7, '2026-06-01 18:46:28.000000', 'NguyenvanC@gmail.com', 1, '$2a$10$6xO1r3mbYqgkooEJVrUvL.mg74hzq3.Jt6onMt2VyOY1Qu/mzpl9y', 'NguyenVanC', 10),
(8, '2026-06-01 18:53:49.000000', 'tamhuy@gmai.com', 1, '$2a$10$ovWpKzM79CRHZ1xkW19ReeXNed8NI268Pfbibucq.Izd4AU5JWMq2', 'NguyenPhucTamHuy', 10);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user_badges`
--

CREATE TABLE `user_badges` (
  `id` bigint(20) NOT NULL,
  `earned_at` datetime(6) DEFAULT NULL,
  `badge_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `user_badges`
--

INSERT INTO `user_badges` (`id`, `earned_at`, `badge_id`, `user_id`) VALUES
(1, '2026-06-01 15:20:12.000000', 1, 4),
(2, '2026-06-01 15:20:12.000000', 2, 4),
(3, '2026-06-01 15:20:12.000000', 3, 4),
(4, '2026-06-01 15:20:12.000000', 4, 4),
(5, '2026-06-01 15:20:12.000000', 5, 4),
(6, '2026-06-01 15:20:12.000000', 6, 4);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `activity_feed`
--
ALTER TABLE `activity_feed`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKq25cfmlw03pl1f57kbwcyk3mm` (`challenge_id`),
  ADD KEY `FKt4b7nk6xcwhq07id938sysuom` (`user_id`);

--
-- Chỉ mục cho bảng `badges`
--
ALTER TABLE `badges`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `challenges`
--
ALTER TABLE `challenges`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKbws17o0vxui39v7wwywvwedac` (`invite_code`),
  ADD KEY `FKop2iriey5oadmctd4re2vpfbx` (`creator_id`);

--
-- Chỉ mục cho bảng `challenge_members`
--
ALTER TABLE `challenge_members`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKg9670fle3e264kcfw9rknkotx` (`challenge_id`,`user_id`),
  ADD KEY `FK5umq8sqstldtslh7ymq7pmba1` (`user_id`);

--
-- Chỉ mục cho bảng `checkins`
--
ALTER TABLE `checkins`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKrq1meewmpepf2i4m3h8rjdynu` (`habit_id`,`checkin_date`),
  ADD KEY `FK81gcnk1emrj19gv3x67u4f8bx` (`user_id`);

--
-- Chỉ mục cho bảng `habits`
--
ALTER TABLE `habits`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKg3n2qqwmsyv3517xdcosouk9i` (`user_id`);

--
-- Chỉ mục cho bảng `notifications`
--
ALTER TABLE `notifications`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK9y21adhxn0ayjhfocscqox7bh` (`user_id`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  ADD UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`);

--
-- Chỉ mục cho bảng `user_badges`
--
ALTER TABLE `user_badges`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK5r2v5xn0il3p8dc9nf4v94r2b` (`user_id`,`badge_id`),
  ADD KEY `FKk6e00pguaij0uke6xr81gt045` (`badge_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `activity_feed`
--
ALTER TABLE `activity_feed`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT cho bảng `badges`
--
ALTER TABLE `badges`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `challenges`
--
ALTER TABLE `challenges`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `challenge_members`
--
ALTER TABLE `challenge_members`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT cho bảng `checkins`
--
ALTER TABLE `checkins`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=188;

--
-- AUTO_INCREMENT cho bảng `habits`
--
ALTER TABLE `habits`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT cho bảng `notifications`
--
ALTER TABLE `notifications`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT cho bảng `user_badges`
--
ALTER TABLE `user_badges`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `activity_feed`
--
ALTER TABLE `activity_feed`
  ADD CONSTRAINT `FKq25cfmlw03pl1f57kbwcyk3mm` FOREIGN KEY (`challenge_id`) REFERENCES `challenges` (`id`),
  ADD CONSTRAINT `FKt4b7nk6xcwhq07id938sysuom` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `challenges`
--
ALTER TABLE `challenges`
  ADD CONSTRAINT `FKop2iriey5oadmctd4re2vpfbx` FOREIGN KEY (`creator_id`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `challenge_members`
--
ALTER TABLE `challenge_members`
  ADD CONSTRAINT `FK5umq8sqstldtslh7ymq7pmba1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKhe9ryb28n7t992r6ji7v3755e` FOREIGN KEY (`challenge_id`) REFERENCES `challenges` (`id`);

--
-- Các ràng buộc cho bảng `checkins`
--
ALTER TABLE `checkins`
  ADD CONSTRAINT `FK81gcnk1emrj19gv3x67u4f8bx` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKccsnc541yawn636shrdwmkpyb` FOREIGN KEY (`habit_id`) REFERENCES `habits` (`id`);

--
-- Các ràng buộc cho bảng `habits`
--
ALTER TABLE `habits`
  ADD CONSTRAINT `FKg3n2qqwmsyv3517xdcosouk9i` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `notifications`
--
ALTER TABLE `notifications`
  ADD CONSTRAINT `FK9y21adhxn0ayjhfocscqox7bh` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `user_badges`
--
ALTER TABLE `user_badges`
  ADD CONSTRAINT `FKk6e00pguaij0uke6xr81gt045` FOREIGN KEY (`badge_id`) REFERENCES `badges` (`id`),
  ADD CONSTRAINT `FKr46ah81sjymsn035m4ojstn5s` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
