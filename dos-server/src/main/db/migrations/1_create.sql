CREATE TABLE IF NOT EXISTS `pages` (
	`id` VARCHAR(50) NOT NULL,
	`url` VARCHAR(50) NOT NULL,
	`foundAt` BIGINT(20) NOT NULL,
	`downloadedAt` BIGINT(20) NOT NULL,
	`contents` BLOB,
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;