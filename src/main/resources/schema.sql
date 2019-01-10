DROP TABLE IF EXISTS USERS;
DROP TABLE IF EXISTS ACCOUNT;

CREATE TABLE IF NOT EXISTS USERS (
  ID INTEGER NOT NULL AUTO_INCREMENT,
  DNI INTEGER NOT NULL,
  FIRST_NAME VARCHAR(100) NOT NULL,
  LAST_NAME VARCHAR(100) NOT NULL,
  PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS ACCOUNT (
  ID INTEGER NOT NULL AUTO_INCREMENT,
  USER_ID INTEGER NOT NULL,
  CBU VARCHAR(23) NOT NULL,
  AMOUNT DECIMAL DEFAULT 0,
  ACCOUNT_TYPE VARCHAR(4),
  PRIMARY KEY (ID)
);

ALTER TABLE ACCOUNT ADD FOREIGN KEY (`USER_ID`) REFERENCES USERS(`ID`);