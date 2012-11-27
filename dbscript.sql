delimiter $$

CREATE DATABASE `smlite` /*!40100 DEFAULT CHARACTER SET utf8 */$$

USE smlite$$

CREATE TABLE `vendor` (
    `id` bigint(20) NOT NULL,
    `name` varchar(45) DEFAULT NULL,
    `pocreated` bit(1) DEFAULT NULL,
    `podetails` text,
    `ponumber` varchar(10) DEFAULT NULL,
    `potype` varchar(45) DEFAULT NULL,
    `datecreated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `id_UNIQUE` (`id`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8$$

CREATE DEFINER=`root`@`localhost` FUNCTION `getCurrentMonthExpenditure`() RETURNS int(11)
BEGIN
	DECLARE flag INT DEFAULT 0;
	DECLARE PO_COUNT INT DEFAULT 0;
	DECLARE PO_TYPE VARCHAR(45) DEFAULT "";
	DECLARE expenditure INT DEFAULT 0;
	DECLARE poPrice INT DEFAULT 1;

	DECLARE tCursor CURSOR FOR 
		SELECT  count(id) as pocount, potype FROM vendor
		WHERE YEAR(datecreated)=YEAR(now())
			AND MONTH(datecreated) = MONTH(now())
			AND pocreated= true
		GROUP BY potype;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET flag = 1;

	OPEN tCursor;

	REPEAT
		FETCH tCursor INTO PO_COUNT, PO_TYPE;

		IF 'Staff Augmentation' = PO_TYPE THEN SET poPrice = 100;
		ELSEIF ('Outbound Projects' = PO_TYPE) THEN SET poPrice = 75;
		ELSEIF ('Software Licensing' = PO_TYPE) THEN SET poPrice = 25;
		ELSEIF ('Hardware Purchase' = PO_TYPE) THEN SET poPrice = 50;
		END IF;
		
		IF flag = 0 THEN
			SET expenditure = expenditure + (poPrice * PO_COUNT);
		END IF;

	UNTIL flag
	END REPEAT;

	CLOSE tCursor;
RETURN expenditure;
END$$



