package com.dambra.paul.moduluschecker.chain;

import com.dambra.paul.moduluschecker.Account.BankAccount;
import com.dambra.paul.moduluschecker.ModulusCheckParams;
import com.dambra.paul.moduluschecker.valacdosFile.WeightRow;

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

        if (Optional.ofNullable(
                WeightRow.copy(params.firstWeightRow.orElse(null))).isPresent() && Optional.ofNullable(
                WeightRow.copy(params.firstWeightRow.orElse(null))).get().isException(6)) {
            int a = params.account.getNumberAt(BankAccount.A);
            int g = params.account.getNumberAt(BankAccount.G);
            int h = params.account.getNumberAt(BankAccount.H);

            if (Arrays.asList(4, 5, 6, 7, 8).contains(a)
                    && g == h) {
                return new ModulusResult(Optional.of(true), Optional.empty());
            }
        }

        return next.check(params);
    }
}
