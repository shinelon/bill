﻿INSERT INTO `credit_template` (`due_date`, `current_amount`, `credits`, `cash`, `last_amount`, `repayment`, `consumption`, `transaction_date`, `billing_date`, `transaction description`, `transaction_amount`, `accountable_amount`, `prepaid_cash_amount`, `details`, `cardtype_id`) VALUES ('到期还款日：\\\\d{4}年\\\\d{2}月\\\\d{2}日', '到期还款日：\\\\d{4}年\\\\d{2}月\\\\d{2}日 RMB \\\\d+.?\\\\d+', '到期还款日：\\\\d{4}年\\\\d{2}月\\\\d{2}日 RMB \\\\d+.?\\\\d+ USD \\\\d+.?\\\\d+ RMB \\\\d+.?\\\\d+ USD \\\\d+.?\\\\d+ \\\\d*\\\\D* RMB \\\\d+.?\\\\d+', '取现额度 RMB \\\\d+.?\\\\d+ RMB \\\\d+.?\\\\d+', null, null, null, null, null, null, null, null, '预借现金额度 RMB \\\\d+.?\\\\d+ RMB \\\\d+.?\\\\d+', '\\\\d{8} \\\\d{8} \\\\d{0,4} \\\\d*\\\\D* [A-Za-z]+ -?\\\\d+.?\\\\d+ RMB -?\\\\d+.?\\\\d+', '1');
INSERT INTO `credit_template` (`due_date`, `current_amount`, `credits`, `cash`, `last_amount`, `repayment`, `consumption`, `transaction_date`, `billing_date`, `transaction description`, `transaction_amount`, `accountable_amount`, `prepaid_cash_amount`, `details`, `cardtype_id`) VALUES ('\\\\d{2}/\\\\d{2}', '\\\\d{2}/\\\\d{2} \\\\d+.?\\\\d*', '\\\\d+.?\\\\d* \\\\d+.?\\\\d* 卡号后四位', null, null, null, null, null, null, null, null, null, null, '\\\\d{4} \\\\d{8} \\\\d{2}:\\\\d{2}:\\\\d{2} \\\\S+ \\\\S+ \\\\d+.?\\\\d*', '2');


/*credit_bank*/
insert into `credit_bank` (`name`, `for_short`, `ext_keyword`, `online`, `email`, `status`, `update_date`, `create_date`) values('交通银行','BCM',NULL,'1','1','1',NULL,NULL);
insert into `credit_bank` (`name`, `for_short`, `ext_keyword`, `online`, `email`, `status`, `update_date`, `create_date`) values('北京银行','BOB',NULL,'1','1','1',NULL,NULL);
insert into `credit_bank` (`name`, `for_short`, `ext_keyword`, `online`, `email`, `status`, `update_date`, `create_date`) values('中国银行','BOC',NULL,'1','1','1',NULL,NULL);
insert into `credit_bank` (`name`, `for_short`, `ext_keyword`, `online`, `email`, `status`, `update_date`, `create_date`) values('中国建设银行','CCB',NULL,'1','1','1',NULL,NULL);
insert into `credit_bank` (`name`, `for_short`, `ext_keyword`, `online`, `email`, `status`, `update_date`, `create_date`) values('光大银行','CEB',NULL,'1','1','1',NULL,NULL);
insert into `credit_bank` (`name`, `for_short`, `ext_keyword`, `online`, `email`, `status`, `update_date`, `create_date`) values('兴业银行','CIB',NULL,'1','1','1',NULL,NULL);
insert into `credit_bank` (`name`, `for_short`, `ext_keyword`, `online`, `email`, `status`, `update_date`, `create_date`) values('中信银行','CITIC',NULL,'1','1','1',NULL,NULL);
insert into `credit_bank` (`name`, `for_short`, `ext_keyword`, `online`, `email`, `status`, `update_date`, `create_date`) values('招商银行','CMB',NULL,'1','1','1',NULL,NULL);
insert into `credit_bank` (`name`, `for_short`, `ext_keyword`, `online`, `email`, `status`, `update_date`, `create_date`) values('中国民生银行','CMBC',NULL,'1','1','1',NULL,NULL);
insert into `credit_bank` (`name`, `for_short`, `ext_keyword`, `online`, `email`, `status`, `update_date`, `create_date`) values('广东发展银行','GDB',NULL,'1','1','1',NULL,NULL);
insert into `credit_bank` (`name`, `for_short`, `ext_keyword`, `online`, `email`, `status`, `update_date`, `create_date`) values('中国工商银行','ICBC',NULL,'1','1','1',NULL,NULL);
insert into `credit_bank` (`name`, `for_short`, `ext_keyword`, `online`, `email`, `status`, `update_date`, `create_date`) values('深圳发展银行','SDB',NULL,'1','1','1',NULL,NULL);
insert into `credit_bank` (`name`, `for_short`, `ext_keyword`, `online`, `email`, `status`, `update_date`, `create_date`) values('上海浦东发展银行','SPDB',NULL,'1','1','1',NULL,NULL);
insert into `credit_bank` (`name`, `for_short`, `ext_keyword`, `online`, `email`, `status`, `update_date`, `create_date`) values('邮储银行','PSBC',NULL,'1','1','1',NULL,NULL);

