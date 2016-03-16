package com.wetrack.ikongtiao.repo.impl.invoice;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.ikongtiao.domain.invoice.InvoiceInfo;
import com.wetrack.ikongtiao.repo.api.invoice.InvoiceInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhanghong on 16/3/14.
 */
public class InvoiceInfoRepoImpl implements InvoiceInfoRepo {

    @Autowired
    CommonDao commonDao;

    @Override
    public InvoiceInfo create(InvoiceInfo invoiceInfo) {
        commonDao.mapper(InvoiceInfo.class).sql("insert").session().insert(invoiceInfo);
        return null;
    }
}
