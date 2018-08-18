import org.junit.Assert.assertEquals
import java.util.stream.Stream

object Assert {
    fun thatStreamEquals(s1: Stream<*>, s2: Stream<*>) {
        val lefterator = s1.iterator()
        val righterator = s2.iterator()

        while (lefterator.hasNext() && righterator.hasNext())
            assertEquals(lefterator.next(), righterator.next())

        assert(!lefterator.hasNext() && !righterator.hasNext())
    }
}
