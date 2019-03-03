CREATE database bbs;

use bbs;

CREATE TABLE artical
(
  id INT PRIMARY KEY auto_increment,
  pid INT,
  rootid INT,
  title VARCHAR(255),
  cont text,
  pdate datetime,
  isleaf INT
);

INSERT INTO artical VALUES (NULL ,0,1,'蚂蚁大战大象','蚂蚁大战大象',now(),1);
INSERT INTO artical VALUES (NULL ,0,1,'大象被打趴下了','大象被打趴下了',now(),1);
INSERT INTO artical VALUES (NULL ,0,1,'蚂蚁也不好过','蚂蚁也不好过',now(),0);
INSERT INTO artical VALUES (NULL ,0,1,'瞎说','瞎说',now(),1);
INSERT INTO artical VALUES (NULL ,0,1,'没有瞎说','没有瞎说',now(),0);
INSERT INTO artical VALUES (NULL ,0,1,'怎么可能','怎么可能',now(),1);
INSERT INTO artical VALUES (NULL ,0,1,'怎么没有可能','怎么没有可能',now(),0);
INSERT INTO artical VALUES (NULL ,0,1,'可能性是很大的','可能性是很大的',now(),0);
INSERT INTO artical VALUES (NULL ,0,1,'大象进医院了','大象进医院了',now(),1);
INSERT INTO artical VALUES (NULL ,0,1,'护士是蚂蚁','护士是蚂蚁',now(),0);
