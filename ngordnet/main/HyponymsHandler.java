package ngordnet.main;

import edu.princeton.cs.algs4.In;
import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;

import java.util.*;


public class HyponymsHandler extends NgordnetQueryHandler {
    ourGraph graph;
    Map<String, List<Integer>> wordMap; // word to all its nodes' ids
    Map<Integer, String> idToWords;

    NGramMap nMap;
    private static final int NEGATIVE_INFINITY = Integer.MIN_VALUE;

    public HyponymsHandler(String synsetFile, String hyponymFile, String wordsFile, String countsFile) {
        wordMap = new HashMap<>();
        idToWords = new HashMap<>();
        nMap = new NGramMap(wordsFile, countsFile);
        In in = new In(synsetFile);
        int id;
        int counter = 0;
        while (in.hasNextLine()) {
            if (in.isEmpty()) {
                break;
            }

            String word = in.readLine();
            String[] temp = word.split(",");
            String[] individualWords = temp[1].split(" ");
            id = Integer.parseInt(temp[0]);
            idToWords.put(id, temp[1]);
            counter++;
            for (String tempWord : individualWords) {
                if (wordMap.containsKey(tempWord)) {
                    wordMap.get(tempWord).add(id);
                } else {
                    List<Integer> x = new ArrayList<>();
                    x.add(id);
                    wordMap.put(tempWord, x);
                }
            }
        }
        graph = new ourGraph(counter, hyponymFile);
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int k = q.k();
        if (k == 0) {
            if (words.size() == 1) {
                String word = words.get(0);
                return Arrays.toString(helperTraverse(word));
            } else if (words.size() > 1) {
                String[] a = helperTraverseMultipleWords(words);
                if (a == null) {
                    return "[]";
                } else {
                    return Arrays.toString(a);
                }
            }
        } else { // FOR WHEN K != 0
            if (words.size() == 1) {
                String word = words.get(0);
                String[] hyponymArray = helperTraverse(word);
                String[] finalHyponyms = handleHelper(hyponymArray, k, startYear, endYear);
                if (finalHyponyms == null) {
                    return "[]";
                } else {
                    return Arrays.toString(finalHyponyms);
                }

            } else {
                String[] initialHyponyms = helperTraverseMultipleWords(words);
                if (initialHyponyms == null) {
                    return "[]";
                }
                String[] finalHyponyms = handleHelper(initialHyponyms, k, startYear, endYear);
                if (finalHyponyms == null) {
                    return "[]";
                } else {
                    return Arrays.toString(finalHyponyms);
                }
            }
        }
        return null;
    }

    private String[] handleHelper(String[] hyponymArray, int k, int startYear, int endYear) {
        double[] countArray = new double[hyponymArray.length];
        TimeSeries ts;
        int pos = 0;
        for (String s : hyponymArray) {
            ts = nMap.countHistory(s, startYear, endYear);
            countArray[pos] = listSum(ts.data());
            pos++;
        }
        Set<String> finalHyponyms = new TreeSet<>();
        for (int i = 0; i < k; i++) {
            int maxIndex = maxHelper(countArray);
            if (countArray[maxIndex] > 0) {
                finalHyponyms.add(hyponymArray[maxIndex]);
                countArray[maxIndex] = NEGATIVE_INFINITY;
            }
        }
        if (finalHyponyms.size() == 0) {
            return null;
        } else {
            String[] array = new String[finalHyponyms.size()];
            System.arraycopy(finalHyponyms.toArray(), 0, array, 0, finalHyponyms.size());
            return array;
        }
    }
    private int maxHelper(double[] array) {
        int maxIndex = 0;
        double max = array[0];
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }
    private double listSum(List<Double> data) {
        double sum = 0;
        for (double i : data) {
            sum += i;
        }
        return sum;
    }

    private String[] helperTraverse(String word) {
        List<Integer> ids = wordMap.get(word);
        Set<String> hyponyms = new TreeSet<>();
        traverse(hyponyms, ids);
        String[] array = new String[hyponyms.size()];
        System.arraycopy(hyponyms.toArray(), 0, array, 0, hyponyms.size());
        return array;
    }

    private String[] helperTraverseMultipleWords(List<String> words) {
        List<Integer> ids;
        Set<String> hyponyms = new TreeSet<>();
        Set<String> hyponymsIntersect = new TreeSet<>();
        boolean flag = true;
        for (String s: words)  {
            ids = wordMap.get(s);
            if (ids == null) {
                return null;
            }
            traverse(hyponyms, ids);
            if (flag) {
                hyponymsIntersect.addAll(hyponyms);
                flag = false;
            }
            hyponymsIntersect.retainAll(hyponyms);
            hyponyms.clear();
        }

        String[] array = new String[hyponymsIntersect.size()];
        int tempSize = hyponymsIntersect.size();
        System.arraycopy(hyponymsIntersect.toArray(), 0, array, 0, tempSize);
        if (tempSize == 0) {
            return null;
        }
        return array;
    }

    public Set<String> traverse(Set<String> hyponyms, List<Integer> ids) {
        if (ids.size() == 0) {
            return hyponyms;
        }
        List<Integer> ids2 = new ArrayList<>();
        for (int i: ids) {
            String[] words = idToWords.get(i).split(" ");
            for (String w: words) {
                hyponyms.add(w);
            }
            Set<Integer> children = graph.childSet(i);
            for (Integer child: children) {
                ids2.add(child);
            }

        }
        return traverse(hyponyms, ids2);

    }


}
