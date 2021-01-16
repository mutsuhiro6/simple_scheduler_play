-- schedules schema

-- !Ups

CREATE TABLE schedule (
                      id bigint NOT NULL AUTO_INCREMENT,
                      name varchar(30) NOT NULL,
                      begin_at date NOT NULL,
                      end_at date NOT NULL,
                      PRIMARY KEY (id)
);

-- !Downs

DROP TABLE schedule;