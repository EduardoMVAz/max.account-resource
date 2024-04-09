package max.account;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import max.account.exceptions.AccountAlreadyExistsdException;
import max.account.exceptions.AccountNotFoundException;
import max.account.exceptions.InternalHashingError;
import max.account.exceptions.LoginFailedException;

@Service
public class AccountService {
    
    @Autowired
    private AccountRepository accountRepository;

    @CachePut(value = "accounts", key = "#result.id()")
    public Account create(Account in) {
        AccountModel account = accountRepository.findByEmail(in.email()).orElse(null);

        if (account != null) {
            throw new AccountAlreadyExistsdException(in.email());
        }

        in.hash(calculateHash(in.password()));
        in.password(null);
        return accountRepository.save(new AccountModel(in)).to();
    }

    @Cacheable(value = "accounts", key = "#id", unless = "#result == null")
    public Account read(String id) {
        Account account = accountRepository.findById(id).map(AccountModel::to).orElse(null);

        if (account == null) {
            throw new AccountNotFoundException(id);
        }

        return account;
    }

    public Account login(String email, String password){
        String hash = calculateHash(password);
        Account account = accountRepository.findByEmailAndHash(email, hash).map(AccountModel::to).orElse(null);
        
        if (account == null) {
            throw new LoginFailedException(email);
        }

        return account;
    }

    public String calculateHash(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            byte[] encoded = Base64.getEncoder().encode(hash);
            return new String(encoded);
        } catch (NoSuchAlgorithmException e) {
            throw new InternalHashingError("Could not calculate hash.");
        }
    }
}
