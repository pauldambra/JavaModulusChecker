import com.dambra.paul.moduluschecker.chain.ModulusChainCheck;
import com.dambra.paul.moduluschecker.ModulusCheckParams;
import com.dambra.paul.moduluschecker.chain.ModulusResult;
import org.junit.Test;

import javax.swing.text.html.Option;
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

        public ModulusResult check(ModulusCheckParams params) {
            firstWasCalled = true;
            return next.check(params);
        }
    }

    private class SecondIsAlwaysResponsible implements ModulusChainCheck {

        private final ModulusChainCheck next;

        public SecondIsAlwaysResponsible(ModulusChainCheck next) {

            this.next = next;
        }

        public ModulusResult check(ModulusCheckParams params) {
            secondWasCalled = true;
            return new ModulusResult(Optional.of(false), Optional.empty());
        }
    }

    private class Third implements ModulusChainCheck {

        public ModulusResult check(ModulusCheckParams params) {
            thirdWasCalled = true;
            return new ModulusResult(Optional.of(true), Optional.empty());
        }
    }

    @Test
    public void CanChainTogether() {
        ModulusChainCheck chain = new First(new SecondIsAlwaysResponsible(new Third()));

        ModulusResult result = chain.check(new ModulusCheckParams(null, Optional.empty(), Optional.empty()));

        assertThat(result.firstCheck.get(), is(equalTo(false)));
        assertThat(firstWasCalled, is(equalTo(true)));
        assertThat(secondWasCalled, is(equalTo(true)));
        assertThat(thirdWasCalled, is(equalTo(false)));
    }
}
