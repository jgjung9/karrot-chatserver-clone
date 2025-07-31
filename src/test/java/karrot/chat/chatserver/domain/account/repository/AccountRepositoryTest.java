package karrot.chat.chatserver.domain.account.repository;

import karrot.chat.chatserver.domain.account.entity.Account;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void save() {
        Account account = new Account();
        account.setUsername("testid");
        account.setNickname("test");
        account.setPassword("1234");

        Long savedId = accountRepository.save(account);
        assertThat(savedId).isEqualTo(account.getId());
    }

    @Test
    void findById() {
        Account account = new Account();
        account.setUsername("testid");
        account.setNickname("test");
        account.setPassword("1234");

        Long savedId = accountRepository.save(account);
        Account found = accountRepository.findById(savedId);

        assertThat(found).isEqualTo(account);
    }

    @Test
    void findByUsername() {
        Account account = new Account();
        account.setUsername("testid");
        account.setNickname("test");
        account.setPassword("1234");

        Long savedId = accountRepository.save(account);
        Account found = accountRepository.findByUsername(account.getUsername());

        assertThat(found).isEqualTo(account);
    }
}