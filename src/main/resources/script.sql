CREATE OR REPLACE FUNCTION make_application() RETURNS TRIGGER AS
	$Body$
	BEGIN 
		UPDATE ACCOUNT SET is_application = TRUE WHERE account_id = NEW.account_id;
		RETURN NEW;
	END
	$Body$
	LANGUAGE plpgsql;
	
--Trigger

CREATE TRIGGER make_an_application AFTER INSERT ON account 
FOR EACH ROW EXECUTE PROCEDURE make_application();


DROP TABLE IF EXISTS profile;

CREATE TABLE profile(
	first_name VARCHAR(20) NOT NULL,
	last_name VARCHAR(30),
	user_name VARCHAR(32) NOT NULL UNIQUE PRIMARY key,
	user_pass VARCHAR(32) NOT NULL,
	user_level VARCHAR(3) NOT NULL
);


INSERT INTO profile (first_name, last_name, user_name, user_pass, user_level) VALUES 
	('June', 'Greenup', 'juneadmin', 'pass', 'ADM'),
	('Test Employee', 'Nullius', 'employee', 'ragdollpuppet', 'EMP');
	
DROP TABLE IF EXISTS account CASCADE ;

CREATE TABLE account(
	account_id SERIAL PRIMARY KEY,
	account_maker VARCHAR(32) REFERENCES profile(user_name),
	account_name VARCHAR(50) NOT NULL,
	account_type VARCHAR(10) NOT NULL,
	balance DOUBLE PRECISION CHECK (balance >= 0) NOT NULL,
	is_application boolean,
	account_hash integer	
);