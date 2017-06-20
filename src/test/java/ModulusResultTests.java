import com.dambra.paul.moduluschecker.chain.ModulusResult;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ModulusResultTests {
    @Test
    public void CanCopyFirstResult() {
        ModulusResult original = new ModulusResult(Optional.of(true), Optional.empty());

        ModulusResult modulusResult = ModulusResult.copy(original);

        assertThat(modulusResult.isExceptionFive, is(false));
        assertThat(modulusResult.isExceptionTen, is(false));
        assertThat(modulusResult.firstCheck.orElse(false), is(true));
        assertThat(modulusResult.secondCheck.isPresent(), is(false));
    }

    @Test
    public void CanCopySecondResult() {
        ModulusResult original = new ModulusResult(Optional.of(true), Optional.of(false));

        ModulusResult modulusResult = ModulusResult.copy(original);

        assertThat(modulusResult.isExceptionFive, is(false));
        assertThat(modulusResult.isExceptionTen, is(false));
        assertThat(modulusResult.firstCheck.orElse(false), is(true));
        assertThat(modulusResult.secondCheck.orElse(true), is(false));
    }

    @Test
    public void CanCopyExceptionFive() {
        ModulusResult original = ModulusResult.WasProcessedAsExceptionFive(
                new ModulusResult(Optional.of(true), Optional.of(false)));

        ModulusResult modulusResult = ModulusResult.copy(original);

        assertThat(modulusResult.isExceptionFive, is(true));
        assertThat(modulusResult.isExceptionTen, is(false));
        assertThat(modulusResult.firstCheck.orElse(false), is(true));
        assertThat(modulusResult.secondCheck.orElse(true), is(false));
    }

    @Test
    public void CanCopyExceptionTen() {
        ModulusResult original = ModulusResult.WasProcessedAsExceptionTen(
                new ModulusResult(Optional.of(true), Optional.of(false)));

        ModulusResult modulusResult = ModulusResult.copy(original);

        assertThat(modulusResult.isExceptionFive, is(false));
        assertThat(modulusResult.isExceptionTen, is(true));
        assertThat(modulusResult.firstCheck.orElse(false), is(true));
        assertThat(modulusResult.secondCheck.orElse(true), is(false));
    }

    @Test
    public void CanCopyWithSecondResult() {
        ModulusResult original = new ModulusResult(Optional.of(true), Optional.empty());

        ModulusResult modulusResult = ModulusResult.WithSecondResult(Optional.ofNullable(original), true);

        assertThat(modulusResult.isExceptionFive, is(false));
        assertThat(modulusResult.isExceptionTen, is(false));
        assertThat(modulusResult.firstCheck.orElse(false), is(true));
        assertThat(modulusResult.secondCheck.orElse(false), is(true));
    }

    @Test
    public void CanCopyExceptionFiveWithSecondResult() {
        ModulusResult original = ModulusResult.WasProcessedAsExceptionFive(
                new ModulusResult(Optional.of(true), Optional.empty()));

        ModulusResult modulusResult = ModulusResult.WithSecondResult(Optional.ofNullable(original), true);

        assertThat(modulusResult.isExceptionFive, is(true));
        assertThat(modulusResult.isExceptionTen, is(false));
        assertThat(modulusResult.firstCheck.orElse(false), is(true));
        assertThat(modulusResult.secondCheck.orElse(false), is(true));
    }

    @Test
    public void CanCopyExceptionTenWithSecondResult() {
        ModulusResult original = ModulusResult.WasProcessedAsExceptionTen(
                new ModulusResult(Optional.of(true), Optional.empty()));

        ModulusResult modulusResult = ModulusResult.WithSecondResult(Optional.ofNullable(original), true);

        assertThat(modulusResult.isExceptionFive, is(false));
        assertThat(modulusResult.isExceptionTen, is(true));
        assertThat(modulusResult.firstCheck.orElse(false), is(true));
        assertThat(modulusResult.secondCheck.orElse(false), is(true));
    }
}
