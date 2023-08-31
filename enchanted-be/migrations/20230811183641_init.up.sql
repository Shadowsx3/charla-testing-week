create extension if not exists "uuid-ossp";

create table
	if not exists users (
		id char(36) not null unique default (uuid_generate_v4()),
		email varchar(60) not null unique,
		name varchar(60) not null,
		is_premium boolean not null default false,
		password varchar(100) not null,
		role varchar(50) not null default 'User',
		energy smallint not null default 100,
		coins int not null default 3000,
		wins int not null default 0,
		losses int not null default 0,
		next_claim_energy_time timestamptz not null
);

create index users_email_idx on users (email);

create table
    if not exists plants (
        id serial primary key not null unique,
        name varchar(60) not null unique,
        description varchar(255) not null,
        coins_to_collect smallint not null default 100,
        file_path varchar(255) not null
);

create table
	if not exists achievements (
		id serial primary key not null unique,
		name varchar(60) not null unique,
		coins int not null default 0,
		code varchar(36) not null unique,
		description varchar(255) not null,
		file_path varchar(255) not null,
		plants_id int not null,
        constraint g_plant_id foreign key (plants_id) references plants (id)
);

create table
	if not exists users_achievements (
		id serial primary key not null unique,
		achievements_id int not null,
		user_id char(36) not null,
		constraint g_achievements_id foreign key (achievements_id) references achievements (id),
		constraint g_user_id foreign key (user_id) references users (id),
		CONSTRAINT unique_user_achievements_combination UNIQUE (user_id, achievements_id)
);


create table
	if not exists users_plants (
		id serial primary key not null unique,
		user_id char(36) not null,
		plants_id int not null,
		next_collect_time timestamptz not null,
		constraint g_user_id foreign key (user_id) references users (id),
		constraint g_plants_id foreign key (plants_id) references plants (id),
		CONSTRAINT unique_user_plants_combination UNIQUE (user_id, plants_id)
);

create table
    if not exists events (
        id serial primary key not null unique,
        start_date timestamptz not null,
        end_date timestamptz not null,
        name varchar(60) not null
);

create table
    if not exists store_items (
        id serial primary key not null unique,
        cost int not null,
        name varchar(60) not null,
        description varchar(255) not null,
        event_id int not null,
        plant_id int not null,
        constraint g_event_id foreign key (event_id) references events (id),
        constraint g_plant_id foreign key (plant_id) references plants (id)
);

create table
    if not exists user_store_items (
        id serial primary key not null unique,
        store_items_id int not null,
        user_id char(36) not null,
        constraint g_store_items_id foreign key (store_items_id) references store_items (id),
        constraint g_user_id foreign key (user_id) references users (id),
		CONSTRAINT unique_user_item_combination UNIQUE (user_id, store_items_id)
);
