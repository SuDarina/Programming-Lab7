CREATE TABLE IF NOT EXISTS users(
  id SERIAL PRIMARY KEY,
  login VARCHAR(10) UNIQUE NOT NULL,
  password VARCHAR UNIQUE NOT NULL,
  online INTEGER NOT NULL
);
INSERT INTO users (id, login, password, online) VALUES (DEFAULT, 'Pavel', '202CB962AC59075B964B07152D234B70', 0);
INSERT INTO users (id, login, password, online) VALUES (DEFAULT, 'Egor', '81DC9BDB52D04DC20036DBD8313ED055', 0);

CREATE TABLE IF NOT EXISTS Coordinates(
  id SERIAL PRIMARY KEY,
  x INTEGER NOT NULL,
  y FLOAT NOT NULL
);

INSERT INTO Coordinates (id, x, y) VALUES (DEFAULT, 11, 11.09);
INSERT INTO Coordinates (id, x, y) VALUES (DEFAULT, 111, 111.08);

CREATE TABLE IF NOT EXISTS Person(
  id SERIAL PRIMARY KEY,
  height FLOAT NOT NULL,
  passportID VARCHAR(41) NOT NULL
);

INSERT INTO Person (id, height, passportID) VALUES (DEFAULT, 187.09, 'uyyuhiu76567568568');
INSERT INTO Person (id, height, passportID) VALUES (DEFAULT, 179.056, 'huijiuhugytyguy09809890');

CREATE TABLE IF NOT EXISTS Statuses(
  id   SERIAL PRIMARY KEY,
  Status VARCHAR(30) NOT NULL
);

INSERT INTO Statuses (id, Status) VALUES (DEFAULT, 'HIRED');
INSERT INTO Statuses (id, Status) VALUES (DEFAULT, 'RECOMMENDED_FOR_PROMOTION');
INSERT INTO Statuses (id, Status) VALUES (DEFAULT, 'REGULAR');

CREATE TABLE IF NOT EXISTS CreationDate (
  id SERIAL PRIMARY KEY,
  date TIMESTAMP NOT NULL
);

INSERT INTO CreationDate (id, date) VALUES (DEFAULT, now());
INSERT INTO CreationDate (id, date) VALUES (DEFAULT, now());

CREATE TABLE IF NOT EXISTS Positions(
  id   SERIAL PRIMARY KEY,
  Position VARCHAR(30)
);

INSERT INTO Positions (id, Position) VALUES (DEFAULT, 'DIRECTOR');
INSERT INTO Positions (id, Position) VALUES (DEFAULT, 'MANAGER');
INSERT INTO Positions (id, Position) VALUES (DEFAULT, 'HUMAN_RESOURCES');
INSERT INTO Positions (id, Position) VALUES (DEFAULT, 'HEAD_OF_DEPARTMENT');
INSERT INTO Positions (id, Position) VALUES (DEFAULT, 'LEAD_DEVELOPER');

CREATE TABLE IF NOT EXISTS Workers(
  id SERIAL PRIMARY KEY,
  name VARCHAR NOT NULL,
  Coordinates INTEGER NOT NULL,
  FOREIGN KEY (Coordinates) REFERENCES Coordinates (id),
  salary INTEGER NOT NULL,
  Position INTEGER NULL,
  FOREIGN KEY (Position) REFERENCES Positions (id),
  Status INTEGER NOT NULL,
  FOREIGN KEY (Status) REFERENCES Statuses (id),
  Person INTEGER NOT NULL,
  FOREIGN KEY (Person) REFERENCES Person (id),
  CreationDate INTEGER NOT NULL,
  FOREIGN KEY (CreationDate) REFERENCES CreationDate (id),
  creater INTEGER NOT NULL,
  FOREIGN KEY (creater) REFERENCES users (id)
);

INSERT INTO Workers (id, name, Coordinates, salary, Position, Status, Person, CreationDate, creater) VALUES (DEFAULT, 'bob', 1, 12345, null, 3, 1, 1, 1);
INSERT INTO Workers (id, name, Coordinates, salary, Position, Status, Person, CreationDate, creater) VALUES (DEFAULT, 'sally', 2, 88797, 2, 2, 2, 2, 1);
