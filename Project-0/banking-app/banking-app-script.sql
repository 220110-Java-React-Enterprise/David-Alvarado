USE BankingApp;

CREATE TABLE users (
	user_id int NOT NULL AUTO_INCREMENT UNIQUE,
	f_name varchar(100) NOT NULL,
	l_name varchar(100) NOT NULL,
	phone varchar(100) NOT NULL,
	email varchar(200) NOT NULL, 
	username varchar(100) NOT NULL UNIQUE,
	password varchar(100) NOT NULL,
	PRIMARY KEY (user_id)
);


CREATE TABLE account (
	account_id int NOT NULL AUTO_INCREMENT UNIQUE,
	current_balance Decimal(10,2) NOT NULL,
	PRIMARY KEY	(account_id)
);


/*CREATE TABLE acc_transaction(
	transaction_id int NOT NULL AUTO_INCREMENT UNIQUE,
	transaction_conducted int NOT NULL,
	account_id int NOT NULL,
	PRIMARY KEY (transaction_id)
	FOREIGN KEY (account_id) REFERENCES account(account_id), account_jct(account_id)
)
*/

CREATE TABLE user_account_jct (
	junction_id int NOT NULL AUTO_INCREMENT UNIQUE,
	account_id int NOT NULL,
	user_id int NOT NULL,
	PRIMARY KEY (junction_id),
	FOREIGN KEY (user_id) REFERENCES users(user_id),
	FOREIGN KEY (account_id) REFERENCES account(account_id)
	
)

