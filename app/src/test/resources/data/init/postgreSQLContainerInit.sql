-- When running the Application locally using the PostgresContainer from TestContainers, we need to define a user
-- different from the PostgresContainer default so that it will serve as a mock "application user" in the migration
-- scripts. The application user has fewer privileges defined in the migration scripts, but we need the default user to
-- have full privileges so that it can execute the scripts.

CREATE USER scheduling WITH PASSWORD 'scheduling';
