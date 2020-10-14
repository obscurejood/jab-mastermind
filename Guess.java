/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.util.Random;

/**
 *
 * @author jab607
 */
public class Guess {
    private int size;
    private int maxVal;
    private int[] guessNums;
    
    public Guess(int[] g) {
        size = g.length;
        maxVal = 5;
        guessNums = new int[size];
        guessNums = arrayCopy(g);
    }
    
    public Guess(int[] g, int mv) {
        size = g.length;
        maxVal = mv;
        guessNums = new int[size];
        guessNums = arrayCopy(g);
    }
    
    public Guess(int s) {
        size = s;
        maxVal = 5;
        guessNums = new int[s];
        for (int i=0; i<s; i++) {
            guessNums[i]=0;
        }
    }
    
    public Guess(int s, int mv) {
        size = s;
        maxVal = mv;
        guessNums = new int[s];
        
    }
    
    public int[] getGuess() {
        int[] ret = new int[size];
        for (int i=0; i<size; i++) {
            ret[i] = guessNums[i];
        }
        return ret;
    }
    
    public int getSize() {
        return size;
    }
    
    public void setGuess(Guess g) {
        size = g.getSize();
        int[] nums = g.getGuess();
        setGuess(nums);
    }
    
    public void setGuess(int[] nums) {
        size = nums.length;
        guessNums = arrayCopy(nums);
    }
    
    public boolean isExactMatch(Guess g) {
        boolean ret = true;
        if (size!=g.getSize()) {
            ret = false;
        } else {
            int[] nums = g.getGuess();
            for (int i=0; i<size; i++) {
                if (nums[i] != guessNums[i]) {
                    ret = false;
                    i=size;
                }
            }
        }
        return ret;
    }
    
    public boolean isExactMatch(int[] g) {
        boolean ret = true;
        if (size!=g.length) {
            ret = false;
        } else {
            for (int i=0; i<size; i++) {
                if (g[i] != guessNums[i]) {
                    ret = false;
                }
            }
        }
        return ret;
    }
    public int exactMatches(Guess g) {
        int ret = 0;
        if (size == g.getSize()) {
            int[] nums = g.getGuess();
            for (int i=0; i<size; i++) {
                if (nums[i] == guessNums[i]) { ret++; }
            }
        }
        return ret;
    }
    
    public int exactMatches(int[] nums) {
        int ret = 0;
        if (size == nums.length) {
            for (int i=0; i<size; i++) {
                if (nums[i] == guessNums[i]) { ret++; }
            }
        }
        return ret;
    }
    
    public int numberMatches(Guess g) {
        int ret = 0;
        if (size == g.getSize()) {
            int[] nums1 = arrayCopy(guessNums);
            int[] nums2 = g.getGuess();
            for (int i1=0; i1<size; i1++) {
                for (int i2=0; i2 < size; i2++) {
                    if (nums1[i1] == nums2[i2]) {
                        ret++;
                        nums2[i2] = -1;
			i2 = size;
                    }
                }
            }
        }
        return ret;
    }
    
    private int[] arrayCopy(int[] nums) {
        int[] ret = new int[nums.length];
        System.arraycopy(nums, 0, ret, 0, nums.length);
        return ret;
    }
    
    public void generateRandom() {
        Random r = new Random();
        for (int i=0; i<size; i++) {
            guessNums[i] = r.nextInt(maxVal) + 1;
        }
    }
	
	public String toString() {
		String s = "";
		for (int i=0; i<size; i++) {
			s = s + Integer.toString(guessNums[i]) + " ";
		}
		return s;
	}
}
