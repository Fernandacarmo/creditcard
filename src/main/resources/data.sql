insert into user (id, username, password) values ('1', 'admin', '$2a$04$2pwvjarOyHVGqxfPBiphaOMHwZwKYqsyp2pKCd3EHwk7/X/AXl47S');
insert into user (id, username, password) values ('2', 'fernanda', '$2a$04$2pwvjarOyHVGqxfPBiphaOMHwZwKYqsyp2pKCd3EHwk7/X/AXl47S');

insert into role (id, name) values ('1', 'SYSADMIN');
insert into role (id, name) values ('2', 'USER');

insert into user_role (user_id, role_id) values ('1', '1');
insert into user_role (user_id, role_id) values ('2', '2');

insert into credit_card (id, expiry_date, name, number) values ('1', current_date(), 'user admin', '4222222222222');
insert into credit_card (id, expiry_date, name, number) values ('2', current_date(), 'user fernanda', '5105105105105100');
insert into credit_card (id, expiry_date, name, number) values ('3', current_date(), 'user fernanda', '4111111111111111');

insert into credit_user (user_id, credit_id) values ('1', '1');
insert into credit_user (user_id, credit_id) values ('2', '2');
insert into credit_user (user_id, credit_id) values ('2', '3');

commit;