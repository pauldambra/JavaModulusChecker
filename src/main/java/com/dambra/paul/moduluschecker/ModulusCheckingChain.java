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
     * SecondModulusCheckRouter
     */
    static ModulusChainCheck create(ModulusWeightRows weightRows, SortCodeSubstitution sortCodeSubstitution) {
        final SecondModulusCheckRouter secondModulusCheckRouter = new SecondModulusCheckRouter(sortCodeSubstitution);
        final ExceptionFourteenGate exceptionFourteenGate = new ExceptionFourteenGate(secondModulusCheckRouter);
        final ExceptionTwoGate exceptionTwoGate = new ExceptionTwoGate(exceptionFourteenGate);
        final SecondCheckRequiredGate secondCheckRequiredGate = new SecondCheckRequiredGate(exceptionTwoGate);
        final FirstModulusCheckRouter firstModulusCheckRouter = new FirstModulusCheckRouter(
                sortCodeSubstitution,
                secondCheckRequiredGate
        );
        final ExceptionSevenAccountTransformer exceptionSevenAccountTransformer = new ExceptionSevenAccountTransformer(firstModulusCheckRouter);
        final ExceptionSixGate exceptionSixGate = new ExceptionSixGate(exceptionSevenAccountTransformer);
        return new AtLeastOneWeightRowGate(weightRows, exceptionSixGate);
    }
}
