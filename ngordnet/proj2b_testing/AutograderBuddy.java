package ngordnet.proj2b_testing;

import edu.princeton.cs.algs4.Transaction;
import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.main.HyponymsHandler;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {

        return new HyponymsHandler(synsetFile, hyponymFile, wordFile, countFile);
    }
}
