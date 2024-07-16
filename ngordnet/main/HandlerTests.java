package ngordnet.main;

public class HandlerTests {
    public static void main(String args[]) {
        HyponymsHandler h = new HyponymsHandler("/home/yuvan/cs61b/sp23-proj2b-g74/proj2b/data/wordnet/synsets.txt", "/home/yuvan/cs61b/sp23-proj2b-g74/proj2b/data/wordnet/hyponyms.txt", "/home/yuvan/cs61b/sp23-proj2b-g74/proj2b/data/ngrams/top_14377_words.csv", "/home/yuvan/cs61b/sp23-proj2b-g74/proj2b/data/ngrams/total_counts.csv");
    }
}
