-- CLEAN UP ANY PREEXISTING STRUCTURES
DROP TABLE IF EXISTS guild CASCADE;
DROP TABLE IF EXISTS channel CASCADE;
DROP TABLE IF EXISTS role CASCADE;
DROP TABLE IF EXISTS role_assignment CASCADE;
DROP TABLE IF EXISTS message CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS archerisms CASCADE;
DROP TABLE IF EXISTS rps_game CASCADE;

--------------------- CREATE NEW STRUCTURES ---------------------
-- IMPLEMENTED STRUCTURES:
--    - guild
--    - role
--    - role_assignment
--    - users
--    - message
--    - channel
-----------------------------------------------------------------

-- CREATE GUILD STRUCTURES
CREATE TABLE guild (
  guild_id VARCHAR(32) PRIMARY KEY NOT NULL UNIQUE,
  name VARCHAR(64) NOT NULL,
  icon VARCHAR(64),
  iconURL VARCHAR(128),
  owner_id VARCHAR(64) NOT NULL
);

CREATE UNIQUE INDEX guild_guild_id_uindex ON guild (
  guild_id
);
-- END CREATE GUILD STRUCTURES

-- CREATE ROLE STRUCTURES
CREATE TABLE role (
  role_id BIGINT PRIMARY KEY NOT NULL UNIQUE,
  guild_id VARCHAR(32) NOT NULL,
  name VARCHAR(64) NOT NULL,
  position BIGINT NOT NULL,
  managed BOOLEAN NOT NULL DEFAULT FALSE,
  hoist BOOLEAN NOT NULL DEFAULT FALSE,
  mentionable BOOLEAN NOT NULL DEFAULT FALSE,
  FOREIGN KEY (guild_id) REFERENCES guild(guild_id) ON DELETE RESTRICT
);

CREATE UNIQUE INDEX role_role_id_uindex ON role (
  role_id
);
-- END CREATE ROLE STRUCTURES

-- CREATE USERS STRUCTURES
CREATE TABLE users (
  user_id VARCHAR(32) PRIMARY KEY NOT NULL UNIQUE,
  username VARCHAR(32) NOT NULL,
  avatar VARCHAR(128),
  avatarURL VARCHAR(128),
  discriminator VARCHAR(32),
  isBot BOOLEAN NOT NULL DEFAULT FALSE
);

COMMENT ON COLUMN users.user_id IS 'The unique ID for a user.';
COMMENT ON COLUMN users.username IS 'The name of a user.';
COMMENT ON COLUMN users.avatar IS 'The avatar of a user.';
COMMENT ON COLUMN users.avatarURL IS 'The URL to access the users avatar image';
COMMENT ON COLUMN users.discriminator IS 'The uniquifying portion of a username';
COMMENT ON COLUMN users.isBot IS 'Whether or not a user is a bot.';

CREATE UNIQUE INDEX users_user_id_uindex ON users (
  user_id
);

COMMENT ON INDEX users_user_id_uindex IS 'Primary key index';
-- END CREATE USERS STRUCTURES


-- CREATE ROLE ASSIGNMENT STRUCTURES
CREATE TABLE role_assignment (
  assignment_id BIGINT PRIMARY KEY NOT NULL UNIQUE,
  role_id BIGINT NOT NULL,
  user_id VARCHAR(32) NOT NULL,
  FOREIGN KEY (role_id) REFERENCES role(role_id) ON DELETE RESTRICT,
  FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX role_assignment_assignment_id_uindex ON role_assignment (
  assignment_id, role_id, user_id
);
-- END CREATE ROLE ASSIGNMENT STRUCTURES

-- CREATE CHANNEL STRUCTURES
CREATE TABLE channel (
  channel_id VARCHAR(32) PRIMARY KEY NOT NULL UNIQUE,
  name VARCHAR(64) NOT NULL,
  isPrivate BOOLEAN NOT NULL DEFAULT FALSE,
  parent VARCHAR(32) NOT NULL,
  position BIGINT NOT NULL,
  FOREIGN KEY (parent) REFERENCES guild(guild_id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX channel_channel_id_uindex ON channel (
  channel_id
);
-- END CREATE CHANNEL STRUCTURES

-- CREATE MESSAGE STRUCTURES
CREATE TABLE message (
  message_id VARCHAR(32) PRIMARY KEY NOT NULL UNIQUE,
  channel_id VARCHAR(32) NOT NULL,
  author VARCHAR(32) NOT NULL,
  content VARCHAR(512),
  created TIMESTAMP NOT NULL,
  edited TIMESTAMP,
  everyone BOOLEAN NOT NULL DEFAULT FALSE,
  isPinned BOOLEAN NOT NULL DEFAULT FALSE,
  FOREIGN KEY (author) REFERENCES users(user_id) ON DELETE RESTRICT
);

COMMENT ON COLUMN message.message_id IS 'The unique ID for a message.';
COMMENT ON COLUMN message.channel_id IS 'The channel in which the message was created.';
COMMENT ON COLUMN message.author IS 'The author of the message.';
COMMENT ON COLUMN message.content IS 'The text content of the message.';
COMMENT ON COLUMN message.created IS 'The timestamp at which the message was created.';

CREATE UNIQUE INDEX message_message_id_uindex ON message (
  message_id
);

COMMENT ON INDEX message_message_id_uindex IS 'Primary key index';
-- END CREATE MESSAGE STRUCTURES

-- START CREATE ARCHERISM STRUCTURE
CREATE TABLE archerisms (
  trigger_tx VARCHAR(16)  NOT NULL,
  msg_tx     VARCHAR(512) NOT NULL
);

-- START CREATE RPS STRUCTURES
create table rps_game
(
  game_id bigserial not null
    constraint rps_game_pkey
    primary key,
  winner varchar(32),
  created timestamp not null
)
;

create unique index rps_game_game_id_uindex
  on rps_game (game_id)
;

create table rps_player
(
  player_id varchar(32) not null,
  game_id bigint not null
    constraint rps_player_pkey
    primary key
    constraint rps_player_rps_game_game_id_fk
    references rps_game,
  choice varchar(16) not null,
  joined timestamp not null,
  creator boolean not null
);
alter table rps_player
  add constraint rps_player_rps_game_game_id_fk
foreign key (game_id) references rps_game
;

-- END CREATE RPS STRUCTURES



INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('help', '-----------------------------------------------------------------------------\n-- Available commands:\n-----------------------------------------------------------------------------\n-- * !archerism - display a random archer quote\n-- * !help - display help information on the commands you can use\n-----------------------------------------------------------------------------');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','Do you want ants!?!  Because THAT is how you get ants.');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','DANGER ZONE!');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','Danger zone.');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','*whispers* Danger zone. *whispers*');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','LAAAANNNNAAAAAAAAAAAAAAAAAAAA!!!!!!');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','I swear to god I had something for this!');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','Phrasing.');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','Er, phrasing.');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','Phrasing?');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','Boop.');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','Read a book.');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','RAAAAMMMMPPPAAAAAAAGGGGEEEE!!!!!');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','Just the tip.');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','Do you not?');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','Oh my god, is that Burt Reynolds!?');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','Can''t?  Or won''t?');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','I''ve never seen an oscelot!');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','Holy shit you guys, look at his little spots!');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','Look at his tufted ears!');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','No, Cyril.  When they''re dead, they''re just hookers!');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','Just one.  I''m scared if I quit drinking all at once, the cumulative hangover will literally kill me.');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','For I am a sinner in the hands of an angry god.  Bloody Mary, full of vodka, blessed are you among cocktails.  Pray for me now, and at the hour of my death, which I hope is soon.  Amen.');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','I can''t hear you over the sound of my giant, throbbing erection!');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','You killed a black astronaut, Cyril!  That''s like killing a unicorn!');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','Sorry, that''s just a sympathy boner.');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','Hello, airplanes?  It''s blimps.  You win.  Bye.');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','It''s like Meow-schwitz in there.');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','Relax, it''s North Korea, the nation-state equivalent of the short bus.');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','Those can''t seriously be your only shoes.');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','Big whoop!  I''m spooning a Barrett 50 cal.  I could kill a building.');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','I hate surprises.  Except surprise fellatio.  That, I like.');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','Eat a buffet of dicks.');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','Thanks, Jungle.  Eat a buffet of dicks.');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','Hm?  Sorry, I was picturing whore island.');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','Call Kenny Loggins ''cause you''re in the danger zone.');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','I''m not slurring my words, I''m talking in cursive.');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','All I''ve had today is like six gummy bears, and some scotch.');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','Hooray for small miracles.');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','M, as in Mancy.');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','Mawp.');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','Mawp!  Mawp!  Mawp!');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','MAWP!  MAWP!  MAWP!  Mawp!  Mawp.');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','It''s just like the gypsy woman said!');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','I am everybody''s type.');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','Your authority is not recognized in Fort Kickass.');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','I call it the Sterling Archer Triple-A Power Play, and yes, the A stands for awesome.');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','I have no response to that.');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','Hey, I know you''re upset, but if you ever mention my mother''s loins or their frothiness to me again, I don''t know what I''ll do ... but it will be bad. Now let''s go bury this dead hooker.');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','He thinks he''s people!');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','You know, if there''s one thing women totally love, it''s to be smothered by men.  Or choked, in your case.');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','I''m getting my turtleneck.  I''m not defusing a bomb in this!');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','Lying is like 95% of what I do.');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','Karate? The Dane Cook of martial arts? No, ISIS agents use Krav Maga.');
INSERT INTO archerisms (trigger_tx, msg_tx) VALUES ('!archerism','Hey. Hey, proposition: first person to untie me, guy or gal, I will let him or her give me a handy. Come on, let''s share the milk of human kindness!');