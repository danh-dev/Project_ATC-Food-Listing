CREATE TABLE `roles` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `role_name` varchar(20)
);

CREATE TABLE `users` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `user_name` varchar(100),
  `password` varchar(255),
  `full_name` varchar(150),
  `phone` varchar(15),
  `email` varchar(150),
  `birth_day` date,
  `role_id` int,
  `create_at` timestamp
);

CREATE TABLE `rating_food` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `user_id` int,
  `food_id` int,
  `content` text,
  `rate_point` int(2),
  `create_at` timestamp
);

CREATE TABLE `rating_res` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `user_id` int,
  `res_id` int,
  `content` text,
  `rate_point` int(2),
  `create_at` timestamp
);

CREATE TABLE `categories` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `name_cat` varchar(100),
  `create_at` timestamp
);

CREATE TABLE `foods` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `food_name` varchar(255),
  `images` text,
  `description` text,
  `time_waiting` varchar(20),
  `price` decimal(10,2),
  `cat_id` int,
  `create_at` timestamp
);

CREATE TABLE `orders` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `user_id` int,
  `res_id` int,
  `notes` text,
  `rating_res` bool,
  `create_at` timestamp
);

CREATE TABLE `res_cat` (
  `cat_id` int,
  `res_id` int,
  `create_at` timestamp,
  PRIMARY KEY (`cat_id`, `res_id`)
);

CREATE TABLE `restaurants` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `res_name` varchar(255),
  `slogan` varchar(255),
  `description` text,
  `image` text,
  `address` varchar(255),
  `create_at` timestamp
);

CREATE TABLE `coupon` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `res_id` int,
  `type_coupon` varchar(10),
  `value` int,
  `start_date` timestamp,
  `end_date` timestamp,
  `create_at` timestamp
);

CREATE TABLE `order_detail` (
  `food_id` int,
  `order_id` int,
  `create_at` timestamp,
  PRIMARY KEY (`food_id`, `order_id`)
);

ALTER TABLE `users` ADD FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);

ALTER TABLE `rating_food` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `rating_food` ADD FOREIGN KEY (`food_id`) REFERENCES `foods` (`id`);

ALTER TABLE `foods` ADD FOREIGN KEY (`cat_id`) REFERENCES `categories` (`id`);

ALTER TABLE `rating_res` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `rating_res` ADD FOREIGN KEY (`res_id`) REFERENCES `restaurants` (`id`);

ALTER TABLE `orders` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `orders` ADD FOREIGN KEY (`res_id`) REFERENCES `restaurants` (`id`);

ALTER TABLE `order_detail` ADD FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`);

ALTER TABLE `order_detail` ADD FOREIGN KEY (`food_id`) REFERENCES `foods` (`id`);

ALTER TABLE `res_cat` ADD FOREIGN KEY (`cat_id`) REFERENCES `categories` (`id`);

ALTER TABLE `res_cat` ADD FOREIGN KEY (`res_id`) REFERENCES `restaurants` (`id`);

ALTER TABLE `coupon` ADD FOREIGN KEY (`res_id`) REFERENCES `restaurants` (`id`);
