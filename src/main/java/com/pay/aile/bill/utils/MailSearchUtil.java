package com.pay.aile.bill.utils;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.search.OrTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;

import com.pay.aile.bill.exception.MailBillException;

/***
 * MailSearchUtil.java
 *
 * @author shinelon
 *
 * @date 2017年10月30日
 *
 */
public class MailSearchUtil {

    public static Message[] search(String querykey, Folder folder) throws MailBillException {
        SearchTerm subjectTerm = null;
        SearchTerm orTerm1 = null;
        List<SearchTerm> searchTermList = new ArrayList<SearchTerm>();

        try {
            if (-1 != querykey.indexOf("，")) {
                String[] split = querykey.split("，");
                int length = split.length;
                for (int i = 0; i < length; i++) {
                    subjectTerm = new SubjectTerm(split[i]);
                    searchTermList.add(subjectTerm);
                }
                int size = searchTermList.size();
                for (int j = 0; j < size; j++) {
                    if (size == 1) {
                        return folder.search(searchTermList.get(j));
                    }
                    if (size <= 2) {
                        orTerm1 = new OrTerm(searchTermList.get(j), searchTermList.get(j + 1));
                        return folder.search(orTerm1);
                    }
                    if (j < 1) {
                        orTerm1 = new OrTerm(searchTermList.get(j), searchTermList.get(j + 1));
                        continue;
                    }
                    orTerm1 = new OrTerm(searchTermList.get(j), orTerm1);
                }
                return folder.search(orTerm1);
            } else if (-1 != querykey.indexOf(",")) {
                String[] split = querykey.split(",");
                int length = split.length;
                for (int i = 0; i < length; i++) {
                    if (i == 0) {
                        subjectTerm = new SubjectTerm(split[i]);
                        continue;
                    }
                    orTerm1 = new OrTerm(subjectTerm, new SubjectTerm(split[i]));
                }
                return folder.search(orTerm1);
            } else {
                subjectTerm = new SubjectTerm(querykey);
                return folder.search(subjectTerm);
            }
        } catch (MessagingException e) {
            throw new MailBillException("");
        }
    }
}
