package com.dambra.paul.moduluschecker.chain;

import com.dambra.paul.moduluschecker.Account.BankAccount;
import com.dambra.paul.moduluschecker.ModulusCheckParams;

import java.util.Arrays;
import java.util.Optional;

/**
 * Exception 6
 * Indicates that these sorting codes may contain foreign currency accounts which cannot be checked.
 * Perform the first and second checks, except:
 * • If a = 4, 5, 6, 7 or 8, and g and h are the same, the accounts are for a foreign currency and the
 * checks cannot be used.
 */
public class ExceptionSixGate implements ModulusChainCheck {

    private final FirstModulusCheckRouter next;

    public ExceptionSixGate(FirstModulusCheckRouter next) {
        this.next = next;
    }

    @Override
    public ModulusResult check(ModulusCheckParams params) {
        int a = params.getAccount().getNumberAt(BankAccount.A);
        int g = params.getAccount().getNumberAt(BankAccount.G);
        int h = params.getAccount().getNumberAt(BankAccount.H);

        if (Arrays.asList(4, 5, 6, 7, 8).contains(a)
                && g == h) {
            return new ModulusResult(Optional.of(true), Optional.empty());
        }

        return next.check(params);
    }
}
