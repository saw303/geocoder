CREATE TABLE geo_location (
  id           BIGINT       NOT NULL AUTO_INCREMENT,
  external_key VARCHAR(36)  NOT NULL,
  version      INTEGER      NOT NULL,
  city         VARCHAR(50)  NOT NULL,
  country      VARCHAR(2)   NOT NULL,
  housenumber  VARCHAR(10)  NOT NULL,
  latitude     DOUBLE PRECISION,
  longitude    DOUBLE PRECISION,
  street       VARCHAR(150) NOT NULL,
  zip          VARCHAR(10)  NOT NULL,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB;

ALTER TABLE geo_location
  ADD CONSTRAINT UK8ruvwd12gqmxdnrt7tv4u41d1 UNIQUE (street, housenumber, zip, city, country);

ALTER TABLE geo_location
  ADD CONSTRAINT UK_4c36ieehbgtqc4vud1r1ui2t2 UNIQUE (external_key);
