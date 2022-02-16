set foreign_key_checks = 0;

truncate table product;
truncate table cart;
truncate table cart_item_list;
truncate table item;
truncate table app_user;

insert into product(id, name, price, quantity)
values(12, 'Ariel',554, 3 ),
       (13, 'Omo',554, 3 ),
       (14, 'Klin',554, 3 ),
       (15, 'Sunlight',554, 3 );

insert into app_user(id, first_name, last_name, email, cart_id)
values(5005, 'John', 'Badmus', 'johnbadmus@gmail.com', 345),
      (5010, 'Chris', 'Toye', 'chrisye@gmail.com', 355),
      (5015, 'Johnny', 'Drille', 'ezinwa@gmail.com', 366);

insert into cart(id)
values(345),
      (355),
      (366);



insert into item(id, quantity_added, product_id)
values(510, 14, 12),
       (511, 12, 13),
       (512, 13, 14);

insert into cart_item_list(cart_id, item_list_id)
values(345, 510),
       (345, 511),
       (345, 512);

set foreign_key_checks = 1;