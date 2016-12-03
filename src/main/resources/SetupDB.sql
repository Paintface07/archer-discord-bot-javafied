-- CLEAN UP ANY PREEXISTING STRUCTURES
DROP TABLE IF EXISTS guild CASCADE;
DROP TABLE IF EXISTS role CASCADE;
DROP TABLE IF EXISTS role_assignment CASCADE;
DROP TABLE IF EXISTS message CASCADE;
DROP TABLE IF EXISTS users CASCADE;

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
  guild_id BIGINT PRIMARY KEY NOT NULL UNIQUE,
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
  guild_id BIGINT NOT NULL,
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
  user_id BIGINT PRIMARY KEY NOT NULL UNIQUE,
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
  user_id BIGINT NOT NULL,
  FOREIGN KEY (role_id) REFERENCES role(role_id) ON DELETE RESTRICT,
  FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX role_assignment_assignment_id_uindex ON role_assignment (
  assignment_id, role_id, user_id
);
-- END CREATE ROLE ASSIGNMENT STRUCTURES

-- CREATE CHANNEL STRUCTURES
CREATE TABLE channel (
  channel_id BIGINT PRIMARY KEY NOT NULL UNIQUE,
  name VARCHAR(64) NOT NULL,
  isPrivate BOOLEAN NOT NULL DEFAULT FALSE,
  parent BIGINT NOT NULL,
  position BIGINT NOT NULL,
  FOREIGN KEY (guild_id) REFERENCES guild(guild_id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX channel_channel_id_uindex ON channel (
  channel_id
);
-- END CREATE CHANNEL STRUCTURES

-- CREATE MESSAGE STRUCTURES
CREATE TABLE message (
  message_id BIGINT PRIMARY KEY NOT NULL UNIQUE,
  channel_id BIGINT NOT NULL,
  author BIGINT NOT NULL,
  content VARCHAR(512),
  created TIMESTAMP NOT NULL,
  edited TIMESTAMP NOT NULL,
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