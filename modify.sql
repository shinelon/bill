alter table `bill`.`credit_file` 
   add column `subject` varchar(128) NULL COMMENT '主题' after `file_name`, 
   add column `mail_type` varchar(10) NULL COMMENT '邮件类型' after `receive_date`,
   change `receive_date` `receive_date` datetime NULL  comment '邮件收到时间'
   
   
   
alter table `bill`.`credit_file` 
   change `receive_date` `sent_date` datetime NULL  comment '邮件发送时间'