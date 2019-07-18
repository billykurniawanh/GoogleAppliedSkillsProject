/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 3;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private static ArrayList<String> wordList = new ArrayList<>();
    private static HashSet<String> wordSet = new HashSet<>();
    private static HashMap<String,ArrayList<String>> lettersToWord = new HashMap<>();
    private static HashMap<Integer,ArrayList<String>> sizeToWord = new HashMap<>();
    private static int wordLength = DEFAULT_WORD_LENGTH;

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            wordSet.add(word);

            if (lettersToWord.containsKey(sortLetter(word))){
                lettersToWord.get(sortLetter(word)).add(word);
            }
            else {
                ArrayList<String> temp1 = new ArrayList<String>();
                temp1.add(word);
                lettersToWord.put(sortLetter(word), temp1);
            }

            if (sizeToWord.containsKey(word.length())){
                sizeToWord.get(word.length()).add(word);
            }
            else {
                ArrayList<String> temp2 = new ArrayList<String>();
                temp2.add(word);
                sizeToWord.put(word.length(), temp2);
            }

        }

    }

    public boolean isGoodWord(String word, String base) {
        if (wordSet.contains(word) && !word.contains(base))
            return true;
        else
            return false;
    }

    public String sortLetter(String word) {
        ArrayList<Character> temp = new ArrayList<Character>();
        for (int i = 0; i < word.length(); i++){
            temp.add(word.charAt(i));
        }
        Collections.sort(temp);
        String result = "";
        for (char i: temp){
            result += i;
        }
        return result;
    }

    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        for (String i: wordList){
            if (sortLetter(targetWord).equals(sortLetter(i)))
                result.add(i);
        }
        return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();

        Set<String> keys = lettersToWord.keySet();
        String words = sortLetter(word);
        for (String i: keys){
            if (i.length() == words.length() + 1){
                int count = 0;
                for (int j = 0; j < words.length(); j++){
                    if (i.charAt(j+count) != words.charAt(j)){
                        count++;
                        j--;
                        if (count == 2)
                            break;
                    }
                }
                if (count == 1)
                    result.addAll(lettersToWord.get(i));
            }
        }
        for (int k = 0; k < result.size(); k++) {
            if (!isGoodWord(result.get(k), word)) {
                result.remove(k);
                k--;
            }
        }
        return result;
    }

    public String pickGoodStarterWord() {
        ArrayList<String> var = sizeToWord.get(wordLength);
        int n = random.nextInt(var.size());
        wordLength = wordLength + 1;
        for (int i = n; i < var.size(); i++) {
            if (getAnagramsWithOneMoreLetter(var.get(i)).size() >= MIN_NUM_ANAGRAMS)
                return var.get(i);
        }
        return "REAP";
    }
}
