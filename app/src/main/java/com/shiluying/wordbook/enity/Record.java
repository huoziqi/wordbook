package com.shiluying.wordbook.enity;

public class Record {
    private String word;
    private String wordMeaning;
    private String wordSample;
    public  Record(){

    }
    public Record(String id,String word,String wordMeaning,String wordSample){
        this.word = word;
        this.wordMeaning = wordMeaning;
        this.wordSample = wordSample;
    }


    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWordMeaning() {
        return wordMeaning;
    }

    public void setWordMeaning(String wordMeaning) {
        this.wordMeaning = wordMeaning;
    }

    public String getWordSample() {
        return wordSample;
    }

    public void setWordSample(String wordSample) {
        this.wordSample = wordSample;
    }
    @Override
    public String toString() {
        return "Record{" +
                ", word='" + word + '\'' +
                ", wordmeaning='" + wordMeaning + '\'' +
                ", wordsample='" + wordSample + '\'' +
                '}';
    }
}

