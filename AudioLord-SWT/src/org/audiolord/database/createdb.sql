CREATE TABLE IF NOT EXISTS library (
	library_id int auto_increment primary key,
	path varchar(512) not null,
    constraint unique_library_path unique(path)
);

CREATE TABLE IF NOT EXISTS artist  (
    artist_id int auto_increment primary key,
    name varchar(255) not null,
    unique_key varchar(255) not null,
    search_key varchar(255) not null,
    various_artists bit default 0,
    rating int default 0
);

CREATE TABLE IF NOT EXISTS album  (
    album_id int auto_increment primary key,
    name varchar(255) not null,
    unique_key varchar(255) not null,
    search_key varchar(255) not null,
    rating int default 0,
    artist_id int not null,
    foreign key(artist_id) references artist(artist_id) on delete cascade
);

CREATE TABLE IF NOT EXISTS genre  (
    genre_id int auto_increment primary key,
    name varchar(255) not null,
    unique_key varchar(255) not null,
    search_key varchar(255) not null,
    rating int default 0
);

CREATE TABLE IF NOT EXISTS track  (
    track_id int auto_increment primary key,
    name varchar(255) not null,
    unique_key varchar(255) not null,
    search_key varchar(255) not null,
    rating int default 0,
    bitrate int null,
    duration int null,
    artist_id int not null,
    album_id int not null,
    genre_id int null,
    library_id int not null,
    foreign key (artist_id) references artist(artist_id) on delete cascade,
    foreign key (album_id) references album(album_id) on delete cascade,
    foreign key (genre_id) references genre(genre_id) on delete set null,
    foreign key (library_id) references library(library_id) on delete cascade
    
);

CREATE TABLE IF NOT EXISTS playlist  (
    playlist_id int auto_increment primary key,
    name varchar(255) not null,
    rating int default 0
);

CREATE TABLE IF NOT EXISTS playlist_track  (
    playlist_id int,
    track_id int,
    primary key (playlist_id, track_id),
    foreign key (playlist_id) references playlist(playlist_id) on delete cascade, 
    foreign key (track_id) references track(track_id) on delete cascade
);