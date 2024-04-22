
--This file is optional and made for convenience.
--Delete it to fill the tables through the API.

insert ignore into meter (id, load_amount, min_bill_amount) values
(1,1,500 ),
(2,2,700 ),
(3,3,1000 ),
(4,3,1250 );

insert ignore into price_per_unit (id, unit_range_lower, unit_range_upper,price) values
(1,0,100 ,3 ),
(2,101,200 ,5 ),
(3,201,300 ,6 ),
(4,301,400 ,7 ),
(5,401,500,7.5 ),
(6,501,999 ,8 );
