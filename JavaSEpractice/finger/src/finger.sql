CREATE database finger;

use finger;

CREATE table fingerinfo (
  id int primary key not null auto_increment,
  height VARCHAR(255),
  width VARCHAR(255),
  infotime VARCHAR(255),
  infomation VARCHAR(255)
  );

