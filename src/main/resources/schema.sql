DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS films CASCADE;
DROP TABLE IF EXISTS friends CASCADE;
DROP TABLE IF EXISTS likes CASCADE;
DROP TABLE IF EXISTS genres CASCADE;
DROP TABLE IF EXISTS film_genres CASCADE;
DROP TABLE IF EXISTS film_mpa CASCADE;
DROP TABLE IF EXISTS mpas CASCADE;

CREATE TABLE IF NOT EXISTS users (
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(64),
    login VARCHAR(64) NOT NULL,
    email VARCHAR(64) NOT NULL,
    birthday TIMESTAMP
);

CREATE TABLE IF NOT EXISTS genres (
    genre_id INTEGER PRIMARY KEY,
    genre_name VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS mpas (
    mpa_id INTEGER PRIMARY KEY,
    mpa_name VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS films (
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(150),
    release_date TIMESTAMP,
    duration INTEGER,
    rating VARCHAR(50),
    genre INTEGER,
    mpa INTEGER
);

CREATE TABLE IF NOT EXISTS friends (
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_id INTEGER REFERENCES users (id),
    is_approved BOOLEAN,
    friend_id INTEGER REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS likes (
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    film_id INTEGER REFERENCES films (id) ON DELETE CASCADE,
    user_id INTEGER REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS film_genres (
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    film_id INTEGER REFERENCES films (id) ON DELETE CASCADE,
    genre_id INTEGER REFERENCES genres (genre_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS film_mpa (
    film_id INTEGER REFERENCES films (id) ON DELETE CASCADE,
    mpa_id INTEGER REFERENCES mpas (mpa_id) ON DELETE CASCADE
);

