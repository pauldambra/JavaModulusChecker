import com.dambra.paul.moduluschecker.chain.ModulusChainCheck;
import com.dambra.paul.moduluschecker.ModulusCheckParams;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ChainOfResponsibilityTests {

    private boolean firstWasCalled = false;
    private boolean secondWasCalled = false;
    private boolean thirdWasCalled = false;

    private class First implements ModulusChainCheck {

        private final ModulusChainCheck next;

        public First(ModulusChainCheck next) {

            this.next = next;
        }

        public Boolean check(ModulusCheckParams params) {
            firstWasCalled = true;
            return next.check(params);
        }
    }

    private class SecondIsAlwaysResponsible implements ModulusChainCheck {

        private final ModulusChainCheck next;

        public SecondIsAlwaysResponsible(ModulusChainCheck next) {

            this.next = next;
        }

        public Boolean check(ModulusCheckParams params) {
            secondWasCalled = true;
            return false;
        }
    }

    private class Third implements ModulusChainCheck {

        public Boolean check(ModulusCheckParams params) {
            thirdWasCalled = true;
            return true;
        }
    }

    @Test
    public void CanChainTogether() {
        ModulusChainCheck chain = new First(new SecondIsAlwaysResponsible(new Third()));

        boolean result = chain.check(new ModulusCheckParams(null, Optional.empty(), Optional.empty()));

        assertThat(result, is(equalTo(false)));
        assertThat(firstWasCalled, is(equalTo(true)));
        assertThat(secondWasCalled, is(equalTo(true)));
        assertThat(thirdWasCalled, is(equalTo(false)));
    }
}
