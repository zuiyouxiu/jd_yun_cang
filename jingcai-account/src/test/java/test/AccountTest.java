package test;

import com.lqh.dev.AccountApplication;
import com.lqh.dev.domain.Account;
import com.lqh.dev.service.IAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountApplication.class)
public class AccountTest {

    @Resource
    private IAccountService accountService;

    @Test
    public void findTest() {
        Account jack = accountService.selectOneByName("jack");
        System.out.println(jack);
    }

    @Test
    public void saveTest() {
        Account account = new Account();
        account.setName("rose");
        account.setBalance(new BigDecimal(500));
        account.setCreateTime(new Date());
        account.setUserId(UUID.randomUUID().toString());
        Long id = accountService.save(account);
        System.out.println(id);
    }

    @Test
    public void transfer() {
        Account account = accountService.selectOneByName("jack");
        BigDecimal subtract = account.getBalance().subtract(new BigDecimal(100));
        account.setBalance(subtract);
        accountService.transfer(account);
    }
}
