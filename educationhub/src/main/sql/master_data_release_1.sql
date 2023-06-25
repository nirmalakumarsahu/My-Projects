INSERT INTO `user` VALUES (1,_binary '','nirmalakumarsahu7@gmail.com','Nirmala','Sahu',NULL,'$2a$10$n/ACAbYelLw4Mt3Id2LrZ.QzyaXsb3Ywt0Q9NrnvFajKFAGnZKvQO',NULL,NULL,'active','b77d9d79-a16c-4582-9b51-500ba8378205'),(2,_binary '','support.educationhub@gmail.com','Support','Education Hub',NULL,'$2a$10$N1LVoZFgZER54eprPMnN2.ayMiYnHxn.c8x8522t7OYyO9dvJdPqS',NULL,NULL,'active','94d81b2b-20f6-4561-9249-06f03acf7fc4');

INSERT INTO `role` VALUES (1,'2023-06-25 22:39:48',1,'2023-06-25 22:39:48',1,_binary '','GLOBAL ADMIN'),(2,'2023-06-25 22:39:48',2,'2023-06-25 22:39:48',2,_binary '','Trainer'),(3,'2023-06-25 22:39:48',2,'2023-06-25 22:39:48',2,_binary '','Student');

INSERT INTO `role_user` VALUES (1,1);

INSERT INTO `permission` VALUES (1,'2023-05-13 02:57:48',1,'2023-05-13 02:57:48',1,_binary '',NULL,'GLOBAL_ADMINISTRATION');

INSERT INTO `role_permission` VALUES (1,1);