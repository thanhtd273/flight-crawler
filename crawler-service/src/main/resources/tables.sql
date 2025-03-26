create keyspace flight_data if not exists with replication = {'class': 'SimpleStrategy', 'replication_factor': 3};
CREATE TABLE flight_data.cities (
    city_id uuid PRIMARY KEY,
    name text,
    code text,
    country_name text,
    created_at timestamp,
    updated_at timestamp,
    status text
);

CREATE TABLE flight_data.airlines (
    airline_id uuid PRIMARY KEY,
    name text,
    code text,
    country_name text,
    created_at timestamp,
    updated_at timestamp,
    status text
);

CREATE TABLE flight_data.airports (
    airport_id uuid PRIMARY KEY,
    name text,
    code text,
    city_code text,
    country_name text,
    created_at timestamp,
    updated_at timestamp,
    status text
);

CREATE TYPE flight_data.airport_location (
    airport_name text,
    airport_code text,
    city text,
    country text,
    scheduled timestamp
);

CREATE TABLE flight_data.flights (
    flight_id uuid PRIMARY KEY,
    flight_code text,
    departure frozen<airport_location>,
    arrival frozen<airport_location>,
    duration int,
    price int,
    currency text,
    airline text,
    seats_available int,
    airplane_type text,
    flight_date text,
    created_at timestamp,
    updated_at timestamp,
    status text
);