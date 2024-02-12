INSERT INTO `user` VALUES 
	(1, true, now(), now(), null, 'nirmalakumarsahu7@gmail.com','Nirmala','Sahu',NULL,'$2a$10$n/ACAbYelLw4Mt3Id2LrZ.QzyaXsb3Ywt0Q9NrnvFajKFAGnZKvQO',NULL,NULL, NULL, 'ACTIVE', 'nirmalakumarsahu7@gmail.com','b77d9d79-a16c-4582-9b51-500ba8378205', null, null),
	(2,true, now(), now(), null, 'digitalmarketinghub.info@gmail.com','Support','Digital Marketing Hub',NULL,'$2a$10$N1LVoZFgZER54eprPMnN2.ayMiYnHxn.c8x8522t7OYyO9dvJdPqS',NULL,NULL, NULL, 'ACTIVE', 'digitalmarketinghub.info@gmail.com', '94d81b2b-20f6-4561-9249-06f03acf7fc4', 1, 1);

INSERT INTO `role` VALUES 
	(1, true, now(),now(),'GLOBAL ADMIN','GLOBAL_ADMIN', 1, 1),
    (2, true, now(),now(),'USER', 'USER', 1,1);
    
INSERT INTO `user_role` VALUES (1,1);

INSERT INTO `permission` VALUES 
	(1,true, now(),now(), 'GLOBAL ADMINISTRATION','GLOBAL_ADMINISTRATION',1,1);;

INSERT INTO `role_permission` VALUES (1,1);