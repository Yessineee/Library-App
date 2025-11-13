
use biblio;
DELIMITER $$
CREATE PROCEDURE get_user_borrowed_books(IN user_id varchar(20))
BEGIN
	SELECT id,nom,
           datepret,
           datepret + interval 30 DAY as dateretourprevue
		FROM historique
		JOIN book ON livre = id
		WHERE usr=user_id and dateretour is null;
END $$
DELIMITER ;
call get_user_borrowed_books('jeff');