import com.dambra.paul.moduluschecker.chain.ModulusResult;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ModulusResultTests {
    @Test
    public void CanCopyFirstResult() {
        ModulusResult original = new ModulusResult(Optional.of(true), Optional.empty());

        ModulusResult copy = ModulusResult.copy(original);

        assertThat(copy.firstException.isPresent(), is(false));
        assertThat(copy.secondException.isPresent(), is(false));

        assertThat(copy.firstCheckResult.orElse(false), is(true));
        assertThat(copy.secondCheckResult.isPresent(), is(false));
    }

    @Test
    public void CanCopySecondResult() {
        ModulusResult original = new ModulusResult(Optional.of(true), Optional.of(false));

        ModulusResult copy = ModulusResult.copy(original);

        assertThat(copy.firstException.isPresent(), is(false));
        assertThat(copy.secondException.isPresent(), is(false));

        assertThat(copy.firstCheckResult.orElse(false), is(true));
        assertThat(copy.secondCheckResult.orElse(true), is(false));
    }

    @Test
    public void CanCopyFirstException() {
        ModulusResult original = new ModulusResult(Optional.of(true), Optional.of(false));
        final ModulusResult modulusResult = original.withFirstException(Optional.of(5));
        ModulusResult copy = ModulusResult.copy(modulusResult);

        assertThat(copy.firstException.get(), is(5));
        assertThat(copy.secondException.isPresent(), is(false));

        assertThat(copy.firstCheckResult.orElse(false), is(true));
        assertThat(copy.secondCheckResult.orElse(true), is(false));
    }

    @Test
    public void CanCopySecondException() {
        ModulusResult original = new ModulusResult(Optional.of(true), Optional.of(false));
        final ModulusResult modulusResult = original.withSecondException(Optional.of(10));
        ModulusResult copy = ModulusResult.copy(modulusResult);

        assertThat(copy.firstException.isPresent(), is(false));
        assertThat(copy.secondException.get(), is(10));

        assertThat(copy.firstCheckResult.orElse(false), is(true));
        assertThat(copy.secondCheckResult.orElse(true), is(false));
    }

    @Test
    public void CanCopyBothExceptions() {
        ModulusResult original = new ModulusResult(Optional.of(true), Optional.of(false));
        final ModulusResult x = original.withFirstException(Optional.of(3));
        final ModulusResult y = x.withSecondException(Optional.of(4));
        ModulusResult copy = ModulusResult.copy(y);

        assertThat(copy.firstException.get(), is(3));
        assertThat(copy.secondException.get(), is(4));

        assertThat(copy.firstCheckResult.orElse(false), is(true));
        assertThat(copy.secondCheckResult.orElse(true), is(false));
    }

    @Test
    public void CanCopyWithSecondResult() {
        ModulusResult original = new ModulusResult(Optional.of(true), Optional.empty());
        final ModulusResult x = original.withFirstException(Optional.of(6));
        final ModulusResult y = x.withSecondException(Optional.of(7));
        ModulusResult copy = ModulusResult.withSecondResult(Optional.ofNullable(y), true);

        assertThat(copy.firstException.get(), is(6));
        assertThat(copy.secondException.get(), is(7));

        assertThat(copy.firstCheckResult.orElse(false), is(true));
        assertThat(copy.secondCheckResult.orElse(false), is(true));
    }
}
