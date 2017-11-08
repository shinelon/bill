package com.pay.aile.bill.service.mail.analyze.banktemplate.icbc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pay.aile.bill.entity.CreditBill;
import com.pay.aile.bill.entity.CreditBillDetail;
import com.pay.aile.bill.entity.CreditTemplate;
import com.pay.aile.bill.service.mail.analyze.enums.CardTypeEnum;
import com.pay.aile.bill.service.mail.analyze.model.AnalyzeParamsModel;
import com.pay.aile.bill.service.mail.analyze.util.DateUtil;

/**
 * @author ji
 * @description 中国工商银行-牡丹贷记卡
 */
@Service
public class ICBCMDCreditTemplate extends AbstractICBCTemplate {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 预借现金
     */
    @Override
    protected void analyzeCash(CreditBill bill, String content, AnalyzeParamsModel apm) {
        // 暂时无此项
        super.analyzeCash(bill, content, apm);
    }

    /**
     * 信用额度
     */
    @Override
    protected void analyzeCredits(CreditBill bill, String content, AnalyzeParamsModel apm) {
        super.analyzeCredits(bill, content, apm);
    }

    /**
     * 应还款额
     */
    @Override
    protected void analyzeCurrentAmount(CreditBill bill, String content, AnalyzeParamsModel apm) {
        super.analyzeCurrentAmount(bill, content, apm);
    }

    /**
     * 交易明细
     */
    @Override
    protected void analyzeDetail(List<CreditBillDetail> detailList, String content, AnalyzeParamsModel apm) {
        super.analyzeDetail(detailList, content, apm);
    }

    /**
     * 解析账单明细
     * 卡号后四位 交易日 记账日 交易类型 商户名称/城市 交易金额 币种 记账金额 币种
     */
    @Override
    protected CreditBillDetail setCreditBillDetail(String details) {
        CreditBillDetail cbd = new CreditBillDetail();
        // 将/RMB 币种与金额的/替换成空格
        details = details.replaceAll("/", " ");
        String[] list = details.split(" ");
        if (list.length != 9) {
            logger.error("ICBC 牡丹贷记卡,账单明细解析数组长度异常...", details);
            return null;
        }
        cbd.setTransactionDate(DateUtil.parseDateFromString(list[1], null));
        cbd.setBillingDate(DateUtil.parseDateFromString(list[2], null));
        cbd.setTransactionDescription(list[4]);
        cbd.setTransactionAmount(list[5]);
        cbd.setTransactionCurrency(list[6]);
        cbd.setAccountableAmount(list[7]);
        cbd.setAccountType(list[8]);
        cbd.setStatus(1);

        return cbd;
    }

    /**
     * 还款日期
     */
    @Override
    protected void analyzeDueDate(CreditBill bill, String content, AnalyzeParamsModel apm) {
        super.analyzeDueDate(bill, content, apm);
    }

    @Override
    public void initRules() {
        super.initRules();
        if (rules == null) {
            rules = new CreditTemplate();
            rules.setDueDate("贷记卡到期还款日 \\d{4}年\\d{1,2}月\\d{1,2}日");
            rules.setCurrentAmount("合计 [\u4e00-\u9fa5]{2,} (-?\\d+,?){0,9}.?\\d{0,2}/[A-Z]{2,}");
            rules.setCredits("贷记卡\\) [\u4e00-\u9fa5]{2,} (-?\\d+,?){0,9}.?\\d{0,2}/[A-Z]{2,} (-?\\d+,?){0,9}.?\\d{0,2}/[A-Z]{2,} (\\d+,?){0,9}.?\\d{0,2}/[A-Z]{2,}");
            rules.setDetails(
                    "\\d{4} \\d{4}-\\d{2}-\\d{2} \\d{4}-\\d{2}-\\d{2} \\S+ \\S+ (\\d+,?){0,9}.?\\d{0,2}/[A-Z]{2,} (\\d+,?){0,9}.?\\d{0,2}/[A-Z]{2,}");
        }
    }

    /**
     * 设置信用卡类型
     */
    @Override
    protected void setCardType() {
        cardType = CardTypeEnum.ICBC_MDC;
    }
}
