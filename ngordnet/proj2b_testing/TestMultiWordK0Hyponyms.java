package ngordnet.proj2b_testing;

import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/** Tests the case where the list of words is length greater than 1, but k is still zero. */
public class TestMultiWordK0Hyponyms {
    // this case doesn't use the NGrams dataset at all, so the choice of files is irrelevant
    public static final String WORDS_FILE = "/home/yuvan/cs61b/sp23-proj2b-g74/proj2b/data/ngrams/top_49887_words.csv";
    public static final String TOTAL_COUNTS_FILE = "data/ngrams/total_counts.csv";
    public static final String SMALL_SYNSET_FILE = "/home/yuvan/cs61b/sp23-proj2b-g74/proj2b/data/wordnet/synsets16.txt";
    public static final String SMALL_HYPONYM_FILE = "/home/yuvan/cs61b/sp23-proj2b-g74/proj2b/data/wordnet/hyponyms16.txt";
    public static final String LARGE_SYNSET_FILE = "/home/yuvan/cs61b/sp23-proj2b-g74/proj2b/data/wordnet/synsets.txt";
    public static final String LARGE_HYPONYM_FILE = "/home/yuvan/cs61b/sp23-proj2b-g74/proj2b/data/wordnet/hyponyms.txt";

    public static final String MY_HYPONYM_FILE = "/home/yuvan/cs61b/sp23-proj2b-g74/proj2b/data/wordnet/testHype.txt";
    public static final String MY_SYNSET_FILE = "/home/yuvan/cs61b/sp23-proj2b-g74/proj2b/data/wordnet/testSyne.txt";


    /** This is an example from the spec.*/
    @Test
    public void testOccurrenceAndChangeK0() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, MY_SYNSET_FILE, MY_HYPONYM_FILE);
        List<String> words = List.of("occurrence", "change");

        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0);
        String actual = studentHandler.handle(nq);
        String expected = "[alteration, change, increase, jump, leap, modification, saltation, transition]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testDebug() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("quantity");
        NgordnetQuery nq = new NgordnetQuery(words, 1470, 2019, 2);
        String actual = studentHandler.handle(nq);
    }
    // TODO: Add more unit tests (including edge case tests) here.

    // TODO: Create similar unit test files for the k != 0 cases.
}
