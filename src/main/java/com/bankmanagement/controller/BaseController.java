package com.bankmanagement.controller;

import com.bankmanagement.service.AccountService;
import com.mysql.cj.log.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {
    protected static final long accountId = 1L;
    protected final Log logger = (Log) LogFactory.getLog(getClass());
    @Autowired
    private AccountService accountService;


}
