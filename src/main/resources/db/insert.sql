set foreign_key_checks = 0;

truncate table product;
truncate table cart;
truncate table cart_item_list;
truncate table item;

insert into product(id, name, price, quantity)
values(12, 'Ariel',554, 3 ),
(13, 'Omo',554, 3 ),
(14, 'Klin',554, 3 ),
(15, 'Sunlight',554, 3 );

insert into item(id, quantity_added, product_id)
values(510, 14, 12),
       (511, 12, 13),
       (512, 13, 14);

insert into cart(id)
values(345);

insert into cart_item_list(cart_id, item_list_id)
values(345, 510),
  (345, 511),
  (345, 512);
set foreign_key_checks = 1;