package com.dambra.paul.moduluschecker;

import com.dambra.paul.moduluschecker.chain.*;
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
     * SecondModulusCheckRouter
     */
    static ModulusChainLink create(ModulusWeightRows weightRows, SortCodeSubstitution sortCodeSubstitution) {
        final SecondModulusCheckRouter secondModulusCheckRouter = new SecondModulusCheckRouter(sortCodeSubstitution);
        final ExceptionSevenAccountTransformer secondExceptionSevenAccountTransformer
                = new ExceptionSevenAccountTransformer(secondModulusCheckRouter, p -> p.secondWeightRow.get());
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
        final ExceptionEightAccountTransformer exceptionEightAccountTransformer
                = new ExceptionEightAccountTransformer(exceptionTenAccountTransformer);
        final ExceptionSevenAccountTransformer firstExceptionSevenAccountTransformer
                = new ExceptionSevenAccountTransformer(exceptionEightAccountTransformer, p -> p.firstWeightRow.get());
        final ExceptionSixGate exceptionSixGate = new ExceptionSixGate(firstExceptionSevenAccountTransformer);
        return new AtLeastOneWeightRowGate(weightRows, exceptionSixGate);
    }
}
