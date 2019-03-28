
drop table if exists USER_PRODUCT_OPINION
drop table if exists USER_JOB_OPINIONTYPE


create table USER_JOB_OPINIONTYPE (JOB_ID bigint not null, USER_ID bigint not null, JOB_STATUS integer not null, OPINION_TYPE integer not null, ASSIGNED_TIMESTAMP datetime, version bigint,created datetime,updated datetime, primary key (USER_ID, JOB_ID)) ENGINE=InnoDB
create table USER_PRODUCT_OPINION (JOB_ID bigint not null, PRODUCT_ID bigint not null, USER_ID bigint not null, includeForecast bit not null, includeOpinion tinyint(1) default 1, OPINION float not null, OPINIONTYPE integer not null, version bigint,created datetime,updated datetime, primary key (JOB_ID, USER_ID, PRODUCT_ID)) ENGINE=InnoDB

alter table USER_JOB_OPINIONTYPE add constraint USER_JOB_OPINIONTYPE_JOB_ID foreign key (JOB_ID) references Job (jobId) on delete cascade
alter table USER_JOB_OPINIONTYPE add constraint USER_JOB_OPINIONTYPE_USER_ID foreign key (USER_ID) references User (userId) on delete cascade

alter table USER_PRODUCT_OPINION add constraint USER_PRODUCT_OPINION_JOB_ID foreign key (JOB_ID) references Job (jobId) on delete cascade
alter table USER_PRODUCT_OPINION add constraint USER_PRODUCT_OPINION_USER_ID foreign key (USER_ID) references User (userId) on delete cascade
alter table USER_PRODUCT_OPINION add constraint USER_PRODUCT_OPINION_PRODUCT_ID foreign key (PRODUCT_ID) references Product (productId) on delete cascade
alter table USER_PRODUCT_OPINION add constraint USER_PRODUCT_OPINION_JOB_ID_PRODUCT_ID foreign key (JOB_ID, PRODUCT_ID) references JOB_PRODUCT (JOB_ID, PRODUCT_ID) on delete cascade
alter table USER_PRODUCT_OPINION add constraint USER_PRODUCT_OPINION_JOB_ID_USER_ID foreign key (JOB_ID, USER_ID) references USER_JOB_OPINIONTYPE (JOB_ID, USER_ID) on delete cascade
