
CREATE TABLE IF NOT EXISTS scheduling.location (
    id SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(32) NOT NULL,
    state VARCHAR(2) NOT NULL,
    time_zone VARCHAR(64) NOT NULL,
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    radius INT NOT NULL,
    status_code VARCHAR(16) NOT NULL,
    version INT NOT NULL,
    date_created TIMESTAMP WITH TIME ZONE NOT NULL,
    date_last_modified TIMESTAMP WITH TIME ZONE NOT NULL
);
GRANT SELECT, INSERT, UPDATE ON scheduling.location TO ${application-user};
GRANT ALL ON scheduling.location_id_seq TO ${application-user};
