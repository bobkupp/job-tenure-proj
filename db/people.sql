-- Persistent database schema
-- sqlite3 /var/evertrue.db < /home/evertrue/db/people.sql

BEGIN TRANSACTION;

CREATE TABLE people (
  'firstname' STRING,
  'lastname' STRING,
  'age' INTEGER,
  'state' STRING
);
  
COMMIT;
