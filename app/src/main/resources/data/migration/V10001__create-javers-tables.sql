CREATE TABLE IF NOT EXISTS scheduling.jv_global_id (
    global_id_pk BIGINT NOT NULL,
    local_id     VARCHAR(191),
    fragment     VARCHAR(200),
    type_name    VARCHAR(200),
    owner_id_fk  BIGINT,
    CONSTRAINT jv_global_id_pk PRIMARY KEY (global_id_pk),
    CONSTRAINT jv_global_id_owner_id_fk FOREIGN KEY (owner_id_fk) REFERENCES scheduling.jv_global_id (global_id_pk)
);
CREATE INDEX IF NOT EXISTS jv_global_id_local_id_idx ON scheduling.jv_global_id (local_id);
CREATE INDEX IF NOT EXISTS jv_global_id_owner_id_fk_idx ON scheduling.jv_global_id (owner_id_fk);
CREATE SEQUENCE IF NOT EXISTS scheduling.jv_global_id_pk_seq;
GRANT SELECT, INSERT, UPDATE ON scheduling.jv_global_id TO ${application-user};
GRANT ALL ON scheduling.jv_global_id_pk_seq TO ${application-user};

CREATE TABLE IF NOT EXISTS scheduling.jv_commit (
    commit_pk           BIGINT NOT NULL,
    author              VARCHAR(200),
    commit_date         TIMESTAMP,
    commit_date_instant VARCHAR(30),
    commit_id           NUMERIC(22, 2),
    CONSTRAINT jv_commit_pk PRIMARY KEY (commit_pk)
);
CREATE INDEX IF NOT EXISTS jv_commit_commit_id_idx ON scheduling.jv_commit (commit_id);
CREATE SEQUENCE IF NOT EXISTS scheduling.jv_commit_pk_seq;
GRANT SELECT, INSERT, UPDATE ON scheduling.jv_commit TO ${application-user};
GRANT ALL ON scheduling.jv_commit_pk_seq TO ${application-user};

CREATE TABLE IF NOT EXISTS scheduling.jv_commit_property (
    property_name  VARCHAR(191) NOT NULL,
    property_value VARCHAR(600),
    commit_fk      BIGINT,
    CONSTRAINT jv_commit_property_pk PRIMARY KEY (commit_fk, property_name),
    CONSTRAINT jv_commit_property_commit_fk FOREIGN KEY (commit_fk) REFERENCES scheduling.jv_commit (commit_pk)
);
CREATE INDEX IF NOT EXISTS jv_commit_property_commit_fk_idx ON scheduling.jv_commit_property (commit_fk);
CREATE INDEX IF NOT EXISTS jv_commit_property_property_name_property_value_idx ON scheduling.jv_commit_property (property_name, property_value);
GRANT SELECT, INSERT, UPDATE ON scheduling.jv_commit_property TO ${application-user};

CREATE TABLE IF NOT EXISTS scheduling.jv_snapshot (
    snapshot_pk        BIGINT NOT NULL,
    type               VARCHAR(200),
    version            BIGINT,
    state              TEXT,
    changed_properties TEXT,
    managed_type       VARCHAR(200),
    global_id_fk       BIGINT,
    commit_fk          BIGINT,
    CONSTRAINT jv_snapshot_pk PRIMARY KEY (snapshot_pk),
    CONSTRAINT jv_snapshot_global_id_fk FOREIGN KEY (global_id_fk) REFERENCES scheduling.jv_global_id (global_id_pk),
    CONSTRAINT jv_snapshot_commit_fk FOREIGN KEY (commit_fk) REFERENCES scheduling.jv_commit (commit_pk)
);
CREATE INDEX IF NOT EXISTS jv_snapshot_global_id_fk_idx ON scheduling.jv_snapshot(global_id_fk);
CREATE INDEX IF NOT EXISTS jv_snapshot_commit_fk_idx ON scheduling.jv_snapshot(commit_fk);
CREATE SEQUENCE IF NOT EXISTS scheduling.jv_snapshot_pk_seq;
GRANT SELECT, INSERT, UPDATE ON scheduling.jv_snapshot TO ${application-user};
GRANT ALL ON scheduling.jv_snapshot_pk_seq TO ${application-user};
