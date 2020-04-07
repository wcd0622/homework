package com.lagou.edu.service.impl;

import com.lagou.edu.common.annotation.Autowired;
import com.lagou.edu.common.annotation.Service;
import com.lagou.edu.common.annotation.Transactional;
import com.lagou.edu.dao.AccountDao;
import com.lagou.edu.pojo.Account;
import com.lagou.edu.service.TransferService;

/**
 * @author 应癫
 */
@Service("transferService")
@Transactional
public class TransferServiceImpl implements TransferService {


    // 最佳状态
    @Autowired
    private AccountDao accountDao;

    // 构造函数传值/set方法传值

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }



    @Override
    public void transfer(String fromCardNo, String toCardNo, int money) throws Exception {



            Account from = accountDao.queryAccountByCardNo(fromCardNo);
            Account to = accountDao.queryAccountByCardNo(toCardNo);

            from.setMoney(from.getMoney()-money);
            to.setMoney(to.getMoney()+money);

            accountDao.updateAccountByCardNo(to);
            accountDao.updateAccountByCardNo(from);



    }
}
