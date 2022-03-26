create database if not exists DATASET;
use DATASET;

create table if not exists MOVIES(
	movieId bigint primary key,
    title varchar(1000),
    genres varchar(1000)
) engine=InnoDB default charset=utf8;

create table if not exists GENOME_SCORES(
	movieId bigint,
    FOREIGN KEY(movieId) REFERENCES MOVIES(movieId),
    tagId bigint,
    relevance decimal(18, 18)
) engine=InnoDB default charset=utf8;

create table if not exists LINKS(
	movieId bigint, 
    FOREIGN KEY(movieId) REFERENCES MOVIES(movieId),
    imdbId bigint, 
    tmdbId bigint
) engine=InnoDB default charset=utf8;

#Timestamps represent seconds since midnight 
#Coordinated Universal Time (UTC) of January 1, 1970.
##
#Ratings are made on a 5-star scale, with half-star 
#increments (0.5 stars - 5.0 stars).
create table if not exists RATINGS(
	userId bigint,
    movieId bigint,
    FOREIGN KEY(movieId) REFERENCES MOVIES(movieId),
    rating double,
	tiempo varchar(10)
) engine=InnoDB default charset=utf8;

create table if not exists TAGS(
	userId bigint,
    movieId bigint,
    tag varchar(1000),
    tiempo varchar(10)
) engine=InnoDB default charset=utf8;

create table if not exists GENOME_TAGS(
	tagId bigint, 
    tag varchar(1000)
) engine=InnoDB default charset=utf8;

