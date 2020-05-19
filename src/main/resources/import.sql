
INSERT INTO customer (id,Name,Email,addr) VALUES (1,'Deva', 'devakumar4u@gmail.com','Bangalore');
INSERT INTO customer (id,Name,Email,addr) VALUES (2,'Kumar', 'dvoleti@gmail.com','Delhi');
INSERT INTO customer (id,Name,Email,addr) VALUES (5,'shyam', 'shyam@gmail.com','Hyderabad');

INSERT INTO account (id,cust_Id,act_type,act_no,balance) VALUES (1,1, 'Checking','007100333',200);
INSERT INTO account (id,cust_Id,act_type,act_no,balance) VALUES (2,2, 'Checking','007100111',100);
INSERT INTO account (id,cust_Id,act_type,act_no,balance) VALUES (6,4, 'Checking','007100777',100);

INSERT INTO transaction (id,txn_type,act_no,txn_amount,txn_date) VALUES (1,'D','007100333',2,TO_DATE('05/19/2020','MM/DD/YYYY'));


