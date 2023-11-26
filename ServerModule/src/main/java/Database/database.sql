CREATE TABLE Games (
IDGames serial NOT NULL,
Dategame date NOT NULL,
State varchar(30) NOT NULL,
ScoreGames int,
PRIMARY KEY (IDGames));
 
CREATE TABLE Phrases (
IDPhrases serial NOT NULL,
Hint varchar(60) NOT NULL,
Phrase varchar(60) NOT NULL,
PRIMARY KEY (IDPhrases));
 
CREATE TABLE Manches (
IDManches serial NOT NULL,
ScoreManches int,
NumberManches int NOT NULL,
IDGames serial NOT NULL,
IDPhrases serial NOT NULL,
PRIMARY KEY (IDManches),
FOREIGN KEY (IDGames) REFERENCES Games (IDGames),
ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY (IDPhrases) REFERENCES Phrases (IDPhrases),
ON UPDATE CASCADE ON DELETE CASCADE);
 
CREATE TABLE Users (
IDUsers SERIAL NOT NULL,
NickName varchar(30) NOT NULL UNIQUE,
Name varchar(30) NOT NULL,
Surname varchar(30) NOT NULL,
Email varchar(30) NOT NULL UNIQUE,
Password varchar(30) NOT NULL,
GamesWin int,
Admin boolean NOT NULL,
Online boolean NOT NULL,
PRIMARY KEY (IDUsers));
 
CREATE TABLE Players (
IDPlayers SERIAL NOT NULL,
IDUsers int NOT NULL,
IDManches serial NOT NULL,
Jolly boolean,
MancheWin int,
PRIMARY KEY (IDPlayers),
FOREIGN KEY (IDUsers) REFERENCES Users (IDUsers) ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY (IDManches) REFERENCES Manches (IDManches) ON UPDATE CASCADE ON DELETE CASCADE);
 
CREATE TABLE Moves (
IDMoves serial NOT NULL,
MoveType varchar(30) NOT NULL,
Letters varchar(1),
ScoreMoves int,
SpinResult varchar(30),
Error boolean,
IDManches serial NOT NULL,
IDPlayers serial NOT NULL,
PRIMARY KEY (IDMoves),
FOREIGN KEY (IDPlayers) REFERENCES Players (IDPlayers) ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY (IDManches) REFERENCES Manches (IDManches) ON UPDATE CASCADE ON DELETE CASCADE);
 
CREATE TABLE Subs (
IDSubs SERIAL NOT NULL,
UsersType varchar(30) NOT NULL,
IDGames serial NOT NULL,
IDUsers int NOT NULL,
Gameswin int,
PRIMARY KEY (IDSubs)),
FOREIGN KEY (IDUsers) REFERENCES Players (IDUsers) ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY (IDGames) REFERENCES Manches (IDGames) ON UPDATE CASCADE ON DELETE CASCADE);
