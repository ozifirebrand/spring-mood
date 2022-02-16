set foreign_key_checks = 0;

truncate table product;

insert into product(id, name, price, quantity)
values(12, 'Ariel',554, 3 ),
(13, 'Omo',554, 3 ),
(14, 'Klin',554, 3 ),
(15, 'Sunlight',554, 3 );


insert into item(id, product_id, quantity_added_to_cart)
values(102, 14, 2),
       (122, 12, 4),
       (133, 13, 1);

insert into cart(id)
values(345)

insert into cart_item_list(cart_id, item_list_id)
values(345, 102),
  (345, 122),
  (345, 133);
set foreign_key_checks = 1;