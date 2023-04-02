package parma.edu.money_transfer;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import parma.edu.money_transfer.dto.BankAccountDto;
import parma.edu.money_transfer.service.BankAccountService;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import static parma.edu.money_transfer.exception.BankingException.NotFoundException;

@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, value = "classpath:/db/scripts/delete-all-bank-accounts.sql")
@Tag("bank-accounts")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BankAccountUnitTest extends AbstractTest {
    @Autowired
    private BankAccountService bankAccountService;

    @Test
    @Order(1)
    public void givenBankAccountService_whenSave_thenOk() {
        final int userId = 1;
        this.initUserServiceMockObject(userId);
        BankAccountDto bankAccount = bankAccountService.createBankAccount(userId);

        Assertions.assertNotNull(bankAccount);
        Assertions.assertEquals(userId, bankAccount.getUserId());

        await()
                .atMost(AWAIT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .pollInterval(POLL_INTERVAL_MS, TimeUnit.MILLISECONDS)
                .until(() -> !bankAccountService.getAccountsByUserId(userId).isEmpty());
    }

    @Test
    @Order(2)
    public void givenBankAccountService_whenSaveAndRetrieveByNotId_thenNotFound() {
        final int userId = 2;
        this.initUserServiceMockObject(userId);
        BankAccountDto bankAccount = bankAccountService.createBankAccount(userId);

        Assertions.assertNotNull(bankAccount);
        Assertions.assertEquals(userId, bankAccount.getUserId());

        await()
                .atMost(AWAIT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .pollInterval(POLL_INTERVAL_MS, TimeUnit.MILLISECONDS)
                .until(() -> !bankAccountService.getAccountsByUserId(userId).isEmpty());

        List<BankAccountDto> accounts = bankAccountService.getAccountsByUserId(userId);
        Assertions.assertNotNull(accounts);
        Assertions.assertEquals(1, accounts.size());

        Integer curAccountId = accounts.get(0).getId();
        Integer bankIdForSearch = curAccountId + 1;

        Assertions.assertThrows(NotFoundException.class,
                () -> bankAccountService.getAccountByIdWithDto(bankIdForSearch));
    }

    @Test
    @Order(3)
    public void givenBankAccountService_whenSaveAndRetrieve_thenOk() {
        final int userId = 3;
        this.initUserServiceMockObject(userId);
        BankAccountDto bankAccount = bankAccountService.createBankAccount(userId);

        await()
                .atMost(AWAIT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .pollInterval(POLL_INTERVAL_MS, TimeUnit.MILLISECONDS)
                .until(() -> !bankAccountService.getAccountsByUserId(userId).isEmpty());

        List<BankAccountDto> accounts = bankAccountService.getAccountsByUserId(userId);
        Assertions.assertNotNull(accounts);
        Assertions.assertEquals(1, accounts.size());

        Integer curAccountId = accounts.get(0).getId();

        Assertions.assertNotNull(bankAccount);
        Assertions.assertEquals(curAccountId, bankAccountService.getAccountByIdWithDto(curAccountId).getId());
    }

    @Test
    @Order(4)
    public void givenBankAccountService_whenSaveAndRetrieveByUserId_thenOk() {
        final int userId = 4;
        final int anotherUserId = 5;
        final int accountsNumber = 10;
        final int anotherNumber = 0;

        this.initUserServiceMockObject(userId);
        for (int i = 0; i < accountsNumber; i++) {
            bankAccountService.createBankAccount(userId);
        }

        await()
                .atMost(AWAIT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .pollInterval(POLL_INTERVAL_MS, TimeUnit.MILLISECONDS)
                .until(() -> bankAccountService.getAccountsByUserId(userId).size() == accountsNumber);

        List<BankAccountDto> accounts = bankAccountService.getAccountsByUserId(userId);
        Assertions.assertNotNull(accounts);
        Assertions.assertEquals(accountsNumber, accounts.size());

        List<BankAccountDto> anotherAccounts = bankAccountService.getAccountsByUserId(anotherUserId);
        Assertions.assertNotNull(anotherAccounts);
        Assertions.assertEquals(anotherNumber, anotherAccounts.size());
    }

    @Test
    @Order(5)
    public void givenBankAccountService_whenSaveAndChangeState_thenOk() {
        final int userId = 5;
        final boolean initialState = true;
        final boolean newState = false;

        this.initUserServiceMockObject(userId);
        bankAccountService.createBankAccount(userId);

        await()
                .atMost(AWAIT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .pollInterval(POLL_INTERVAL_MS, TimeUnit.MILLISECONDS)
                .until(() -> !bankAccountService.getAccountsByUserId(userId).isEmpty());

        List<BankAccountDto> accounts = bankAccountService.getAccountsByUserId(userId);
        Assertions.assertNotNull(accounts);
        Assertions.assertEquals(1, accounts.size());
        Assertions.assertEquals(initialState, accounts.get(0).getIsEnabled());

        bankAccountService.setAccountState(accounts.get(0).getId(), newState);

        await()
                .atMost(AWAIT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .pollInterval(POLL_INTERVAL_MS, TimeUnit.MILLISECONDS)
                .until(() -> !bankAccountService.getAccountsByUserId(userId).get(0).getIsEnabled());

        BankAccountDto accountDto = bankAccountService.getAccountByIdWithDto(accounts.get(0).getId());
        Assertions.assertNotNull(accountDto);
        Assertions.assertEquals(newState, accountDto.getIsEnabled());
    }
}