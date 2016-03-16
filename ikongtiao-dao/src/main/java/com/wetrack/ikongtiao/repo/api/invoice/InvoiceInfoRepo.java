package com.wetrack.ikongtiao.repo.api.invoice;

import com.wetrack.ikongtiao.domain.invoice.InvoiceInfo;

/**
 * Created by zhanghong on 16/3/14.
 */
public interface InvoiceInfoRepo {
    InvoiceInfo create(InvoiceInfo invoiceInfo);
}
