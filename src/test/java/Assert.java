import java.util.Iterator;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class Assert {
    public static void thatStreamEquals(Stream<?> s1, Stream<?> s2)
    {
        @SuppressWarnings("SpellCheckingInspection")
        Iterator<?> lefterator = s1.iterator(), righterator = s2.iterator();

        while(lefterator.hasNext() && righterator.hasNext())
            assertEquals(lefterator.next(), righterator.next());

        assert !lefterator.hasNext() && !righterator.hasNext();
    }
}
