package com.dambra.paul.moduluschecker;

import com.dambra.paul.moduluschecker.chain.*;
import com.dambra.paul.moduluschecker.chain.gates.*;
import com.dambra.paul.moduluschecker.chain.transformers.ExceptionEightAccountTransformer;
import com.dambra.paul.moduluschecker.chain.transformers.ExceptionSevenAccountTransformer;
import com.dambra.paul.moduluschecker.chain.transformers.ExceptionTenAccountTransformer;
import com.dambra.paul.moduluschecker.valacdosFile.ModulusWeightRows;

final class ModulusCheckingChain {
    /**
     * AtLeastOneWeightRowGate
     *         |
     *         V
     * ExceptionSixGate
     *         |
     *         V
     * ExceptionSevenAccountTransformer
     *         |
     *         V
     * ExceptionEightAccountTransformer
     *         |
     *         V
     * ExceptionTenAccountTransformer
     *         |
     *         V
     * FirstModulusCheckRouter
     *         |
     *         V
     * SecondCheckRequiredGate
     *         |
     *         V
     * ExceptionTwoGate
     *         |
     *         V
     * ExceptionFourteenGate
     *         |
     *         V
     * ExceptionTwoAndNineGate
     *         |
     *         V
     * ExceptionSevenAccountTransformer
     *         |
     *         V
     * ExceptionEightAccountTransformer
     *         |
     *         V
     * ExceptionThreeGate
     *         |
     *         V
     * SecondModulusCheckRouter
     */
    static ModulusChainLink create(ModulusWeightRows weightRows, SortCodeSubstitution sortCodeSubstitution) {
        final SecondModulusCheckRouter secondModulusCheckRouter = new SecondModulusCheckRouter(sortCodeSubstitution);
        final ExceptionThreeGate exceptionThreeGate = new ExceptionThreeGate(secondModulusCheckRouter);
        final ExceptionEightAccountTransformer secondExceptionEightAccountTransformer
                = new ExceptionEightAccountTransformer(exceptionThreeGate, p -> p.secondWeightRow.get());
        final ExceptionSevenAccountTransformer secondExceptionSevenAccountTransformer
                = new ExceptionSevenAccountTransformer(secondExceptionEightAccountTransformer, p -> p.secondWeightRow.get());
        final ExceptionTwoAndNineGate exceptionTwoAndNineGate = new ExceptionTwoAndNineGate(secondExceptionSevenAccountTransformer);
        final ExceptionFourteenGate exceptionFourteenGate = new ExceptionFourteenGate(exceptionTwoAndNineGate);
        final ExceptionTwoGate exceptionTwoGate = new ExceptionTwoGate(exceptionFourteenGate);
        final SecondCheckRequiredGate secondCheckRequiredGate = new SecondCheckRequiredGate(exceptionTwoGate);

        final FirstModulusCheckRouter firstModulusCheckRouter = new FirstModulusCheckRouter(
                sortCodeSubstitution,
                secondCheckRequiredGate
        );
        final ExceptionTenAccountTransformer exceptionTenAccountTransformer
                = new ExceptionTenAccountTransformer(firstModulusCheckRouter);
        final ExceptionEightAccountTransformer firstExceptionEightAccountTransformer
                = new ExceptionEightAccountTransformer(exceptionTenAccountTransformer, p -> p.firstWeightRow.get());
        final ExceptionSevenAccountTransformer firstExceptionSevenAccountTransformer
                = new ExceptionSevenAccountTransformer(firstExceptionEightAccountTransformer, p -> p.firstWeightRow.get());
        final ExceptionSixGate exceptionSixGate = new ExceptionSixGate(firstExceptionSevenAccountTransformer);
        return new AtLeastOneWeightRowGate(weightRows, exceptionSixGate);
    }
}
