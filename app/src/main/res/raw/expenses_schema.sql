DROP TABLE IF EXISTS SyncState;
		
CREATE TABLE SyncState (
  -- The uuid of the remote (client or server) -- sent by the client at first connection.
  -- The client UUID is re-generated each time the database is cleared (e.g., reinstall or login with another user)
  id VARCHAR(250) NOT NULL,
  -- The last nextAnchor we have sent to the remote.
  -- this is the id of the last Journal row we have sent to the remote.
  lastAnchor INTEGER,
  -- The last received nextAnchor from remote.
  -- Actually, this is the id of the last Journal row the remote machine sent to us (stored for validation of the remote side sync state).
  lastAnchorFromRemote INTEGER,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS Journal;

CREATE TABLE Journal (
  -- possible values: User, Transaction, Account, Label, Category
  tableName VARCHAR(250) NOT NULL,
  rowUuid VARCHAR(36) NOT NULL,
  -- this id is updated with every insert or update
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  -- possible values: UPSERT, DELETE
  action VARCHAR(250) NOT NULL,
  UNIQUE (tableName, rowUUid)
);

DROP TABLE IF EXISTS Account;
		
CREATE TABLE Account (
  _id INTEGER PRIMARY KEY AUTOINCREMENT,
  uid VARCHAR(36) NOT NULL,
--   user INTEGER NOT NULL DEFAULT NULL,
  name MEDIUMTEXT NOT NULL DEFAULT 'NULL',
  comment VARCHAR(250) NOT NULL DEFAULT '',
  initialBalance DECIMAL NOT NULL DEFAULT 0,
  -- 'cache' column - could be computed from transactions
  currentBalance DECIMAL NOT NULL DEFAULT NULL,
  UNIQUE (uid)
);

-- Transaction is a reserved name :(

DROP TABLE IF EXISTS Trans;
		
CREATE TABLE Trans (
  -- underscore is needed for CursorAdapters... :(
  _id INTEGER PRIMARY KEY AUTOINCREMENT,
  uid VARCHAR(36) NOT NULL,
  account INTEGER NULL DEFAULT NULL,
  amount REAL NOT NULL DEFAULT NULL,
  time TEXT NOT NULL DEFAULT 'NULL',
  comment MEDIUMTEXT NOT NULL,
  type MEDIUMTEXT NOT NULL DEFAULT NULL,
  UNIQUE (uid),
  FOREIGN KEY (account) REFERENCES Account (_id)
);

DROP TABLE IF EXISTS Category;

CREATE TABLE Category (
  _id INTEGER PRIMARY KEY AUTOINCREMENT,
  name VARCHAR(250) NOT NULL,
  comment VARCHAR(250) NOT NULL DEFAULT ''
);

DROP TABLE IF EXISTS Tag;

CREATE TABLE Tag (
  _id INTEGER PRIMARY KEY AUTOINCREMENT,
  uid VARCHAR(36) NOT NULL,
  parent INTEGER DEFAULT NULL,
--  user INTEGER NOT NULL,
  category INTEGER DEFAULT NULL,
  label VARCHAR(250) NULL,
  comment VARCHAR(250) NOT NULL DEFAULT ''
--  UNIQUE (label)
--  UNIQUE (uid)
--  FOREIGN KEY (parent) REFERENCES Tag (_id),
--  FOREIGN KEY (user) REFERENCES User (id),
--  FOREIGN KEY (category) REFERENCES Category (_id)
);

DROP TABLE IF EXISTS TransactionTag;

CREATE TABLE TransactionTag (
  trans INTEGER NOT NULL,
  tag INTEGER NOT NULL,
  PRIMARY KEY (trans, tag),
  FOREIGN KEY (trans) REFERENCES Trans (id),
  FOREIGN KEY (tag) REFERENCES Tag (id)
);



-- INSERT INTO User (_id,name,email,password) VALUES
--   (1,'fery','fery@fery.hu','x');
-- 
-- INSERT INTO Account (_id,user,name,currentBalance) VALUES
--   (1,1,'wallet','1000');
-- 
-- INSERT INTO Trans (account,amount,time,comment,type) VALUES
--   (1,1500,'2014-01-01 08:00:15','Aldi','expense');
-- INSERT INTO Trans (account,amount,time,comment,type) VALUES
--   (1,2000,'2014-01-02 10:05:23','Manna','expense');
-- INSERT INTO Trans (account,amount,time,comment,type) VALUES
--   (1,1500,'2014-02-01 11:00:00','Aldi','expense');

