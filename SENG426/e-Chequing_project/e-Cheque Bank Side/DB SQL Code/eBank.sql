CREATE DATABASE IF NOT EXISTS ebank;

USE eBank;


DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS eChequeOut;
DROP TABLE IF EXISTS eChequeIN;
DROP TABLE IF EXISTS cancelledCheque;

CREATE TABLE accounts (
	accountID varchar(20) NOT NULL,
	clientName varchar (30) NOT NULL,
	dcPath varchar (20) NOT NULL,
	balance double,
	PRIMARY KEY (accountID)
) ;

CREATE TABLE eChequeOut (
	chequeID varchar(20) NOT NULL,
	accountID varchar(20) NOT NULL,
                   balance double,
	PRIMARY KEY (chequeID),
	FOREIGN KEY (accountID) REFERENCES accounts(accountID)
) ;

CREATE TABLE eChequeIN (
	chequeID varchar(20) NOT NULL,
	accountID varchar(20) NOT NULL,
                   balance double,
	PRIMARY KEY (chequeID),
	FOREIGN KEY (accountID) REFERENCES accounts(accountID)
) ;

CREATE TABLE cancelledCheque (
                  chequeID varchar(20) NOT NULL,
                  accountID varchar(20) NOT NULL,
                  PRIMARY KEY (chequeID, accountID),
                  FOREIGN KEY (chequeID) REFERENCES  eChequeOut(chequeID),
                  FOREIGN KEY (accountID) REFERENCES accounts(accountID));
