package parma.edu.money_transfer.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import parma.edu.money_transfer.dto.BankAccountDetailsDto;
import parma.edu.money_transfer.dto.BankAccountDto;
import parma.edu.money_transfer.exception.BankExceptionResponse;
import parma.edu.money_transfer.service.ActualDetailsService;
import parma.edu.money_transfer.service.BankAccountService;

import java.util.List;

@Api(value = "Методы по работе с банковским счетом (кошельком пользователя)", tags = {"Wallet controller"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wallet")
public class WalletResource {
    private final BankAccountService bankAccountService;
    private final ActualDetailsService actualDetailsService;

    @ApiOperation(value = "Получить информацию о кошельке по идентификатору")
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = BankAccountDto.class, message = "Получен кошелек"),
            @ApiResponse(code = 404, response = BankExceptionResponse.class, message = "По данному идентификатору нет кошелька")
    })
    @GetMapping("/bank-account/{id}")
    @ResponseBody
    public ResponseEntity<BankAccountDto> bankAccount(
            @ApiParam(name = "id", value = "Идентификатор кошелька", example = "1", required = true)
            @PathVariable("id") Integer id) {
        BankAccountDto account = bankAccountService.getAccountByIdWithDto(id);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @ApiOperation(value = "Получить перечень кошельков по массиву идентификаторов")
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = BankAccountDto.class, responseContainer = "List", message = "Получен перечень кошельков")
    })
    @GetMapping("/bank-accounts/{ids}")
    @ResponseBody
    public ResponseEntity<List<BankAccountDto>> bankAccounts(
            @ApiParam(name = "ids", value = "Идентификаторы кошельков", example = "1", required = true)
            @PathVariable("ids") List<Integer> ids) {
        List<BankAccountDto> accounts = bankAccountService.getAccountByIds(ids);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @ApiOperation(value = "Получить перечень кошельков конкретного пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = BankAccountDto.class, responseContainer = "List", message = "Получен перечень кошельков")
    })
    @GetMapping("/user-id/{user_id}/bank-accounts")
    @ResponseBody
    public ResponseEntity<List<BankAccountDto>> userBankAccounts(
            @ApiParam(name = "user_id", value = "Идентификатор пользователя", example = "1", required = true)
            @PathVariable("user_id") Integer userId) {
        List<BankAccountDto> userAccounts = bankAccountService.getAccountsByUserId(userId);
        return new ResponseEntity<>(userAccounts, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @ApiOperation(value = "Создать кошелек пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 201, response = BankAccountDto.class, message = "Создан кошелек пользователя"),
            @ApiResponse(code = 403, response = BankExceptionResponse.class, message = "Кошелек не будет создан")
    })
    @PostMapping("/create/bank-account")
    @ResponseBody
    public ResponseEntity<BankAccountDto> newBankAccount(
            @ApiParam(name = "userId", value = "Идентификатор пользователя", example = "1", required = true)
            @RequestBody
            Integer userId) {
        BankAccountDto account = bankAccountService.createBankAccount(userId);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @ApiOperation(value = "Изменить активность кошелька")
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = String.class, message = "Изменена активность кошелька пользователя"),
            @ApiResponse(code = 404, response = BankExceptionResponse.class, message = "По данному идентификатору нет кошелька")
    })
    @PatchMapping("/change/bank-account/{id}")
    @ResponseBody
    public ResponseEntity<String> setBankAccountProperties(
            @ApiParam(name = "id", value = "Идентификатор счёта", example = "1", required = true)
            @PathVariable("id") Integer accountId,
            @ApiParam(name = "enabled", value = "Присваиваемое состояние счёта", example = "true", required = true)
            @RequestBody
            boolean enabled) {
        bankAccountService.setAccountState(accountId, enabled);
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }

    @ApiOperation(value = "Получить историю изменения баланса кошелька пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = BankAccountDetailsDto.class, responseContainer = "List", message = "Получена история изменения баланса счёта кошелька"),
            @ApiResponse(code = 404, response = BankExceptionResponse.class, message = "По данному идентификатору нет кошелька")
    })
    @GetMapping("/balance_history/bank-account/{id}")
    @ResponseBody
    public ResponseEntity<BankAccountDetailsDto> balanceHistoryAccount(
            @ApiParam(name = "id", value = "Идентификатор счёта", example = "1", required = true)
            @PathVariable("id") Integer accountId) {
        BankAccountDetailsDto itemsHistory = actualDetailsService.getDetailsByAccountId(accountId);
        return new ResponseEntity<>(itemsHistory, HttpStatus.OK);
    }
}