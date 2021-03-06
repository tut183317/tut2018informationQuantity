package s4.B183317;

import java.lang.*;
import s4.specification.*;
/*
package s4.specification;

public interface FrequencerInterface {     // This interface provides the design for frequency counter.
    void setTarget(byte  target[]); // set the data to search.
    void setSpace(byte  space[]);  // set the data to be searched target from.
    int frequency(); //It return -1, when TARGET is not set or TARGET's length is zero
                    //Otherwise, it return 0, when SPACE is not set or SPACE's length is zero
                    //Otherwise, get the frequency of TAGET in SPACE
    int subByteFrequency(int start, int end);
    // get the frequency of subByte of taget, i.e target[start], taget[start+1], ... , target[end-1].
    // For the incorrect value of START or END, the behavior is undefined.
}
*/

public class Frequencer implements FrequencerInterface {
    // Code to start with: This code is not working, but good start point to work.
    byte[] myTarget;
    byte[] mySpace;
    boolean targetReady = false;
    boolean spaceReady = false;
    
    int[] suffixArray;
    
    // The variable, "suffixArray" is the sorted array of all suffixes of mySpace.
    // Each suffix is expressed by a integer, which is the starting position in
    // mySpace.
    // The following is the code to print the variable
    private void printSuffixArray() {
	if (spaceReady) {
	    for (int i = 0; i < mySpace.length; i++) {
		int s = suffixArray[i];
		for (int j = s; j < mySpace.length; j++) {
		    System.out.write(mySpace[j]);
		}
		System.out.write('\n');
	    }
	}
    }
    
    private int suffixCompare(int i, int j) {
	// comparing two suffixes by dictionary order.
	// i and j denoetes suffix_i, and suffix_j
	// if suffix_i > suffix_j, it returns 1
	// if suffix_i < suffix_j, it returns -1
	// if suffix_i = suffix_j, it returns 0;
	// It is not implemented yet,
	// It should be used to create suffix array.
	// Example of dictionary order
	// "i" < "o" : compare by code
	// "Hi" < "Ho" ; if head is same, compare the next element
	// "Ho" < "Ho " ; if the prefix is identical, longer string is big
	if (mySpace[i] > mySpace[j]) {
	    return 1;
	} else if (mySpace[i] < mySpace[j]) {
	    return -1;
	} else if (mySpace[i] == mySpace[j]) {
	    int x = i;
	    int y = j;
	    while (true) {
		if (x == mySpace.length-1 || y == mySpace.length-1) {
		    x = mySpace.length - x;
		    y = mySpace.length - y;
		    if (x > y) {
			return 1;
		    } else if (y > x) {
			return -1;
		    } else {
			return 0;
		    }
		}
		x++;
		y++;
		
		if (mySpace[x] > mySpace[y]) {
		    return 1;
		} else if (mySpace[x] < mySpace[y]) {
		    return -1;
		}
		
	    }
	}
	return 0;
    }
    
    public void setSpace(byte[] space) {
	mySpace = space;
	if (mySpace.length > 0)
	    spaceReady = true;
	suffixArray = new int[space.length];
	// put all suffixes in suffixArray. Each suffix is expressed by one integer.
	for (int i = 0; i < space.length; i++) {
	    suffixArray[i] = i;
	}
	
	for (int i = 0; i < space.length; i++) {
	    for (int j = i+1; j < space.length; j++) {
		if (this.suffixCompare(suffixArray[i], suffixArray[j])==1) {
		    int tmp=suffixArray[i];
		    suffixArray[i]=suffixArray[j];
		    suffixArray[j]=tmp;
		}
	    }
	}
    }
    
    private int targetCompare(int i, int j, int end) {
	// comparing suffix_i and target_j_end by dictonary order with limitation of
	// length;
	// if the beginning of suffix_i matches target_j, and suffix is longer than
	// target it returns 0;
	// suffix_i --> mySpace[i], mySpace[i+1], .... ,
	// mySpace[mySpace.length-1],mySpace[mySpace.length -1]
	// target_j_end -> myTarget[j], myTarget[j+1], .... ,
	// myTarget[end-2],myTarget[end-1]
	// if suffix_i > target_j_end it return 1;
	// if suffix_i < target_j_end it return -1
	// It is not implemented yet.
	// It should be used to search the apropriate index of some suffix.
	// Example of search
	// suffix target
	// "o" > "i"
	// "o" < "z"
	// "o" = "o"
	// "o" < "oo"
	// "Ho" > "Hi"
	// "Ho" < "Hz"
	// "Ho" = "Ho"
	// "Ho" < "Ho " : "Ho " is not in the head of suffix "Ho"
	// "Ho" = "H" : "H" is in the head of suffix "Ho"
	/*for (int k = j; k < end; k++) {
	    if (myTarget[k] != mySpace[suffixArray[i] + k - j]) {
		if (myTarget[k] < mySpace[suffixArray[i] + k - j]) {
		    return 1;
		}
		return -1;
	    }
	}
	return 0; // This line should be modified.*/
	
	int i_length = mySpace.length - suffixArray[i];
	//System.out.println("i_length  ="+i_length+"");
	int j_length = end - j;
	int tmp = end-j;
	int k;
	if(i_length < j_length){
	    tmp = i_length;
	}
	
	for(k=0; k<tmp; k++){
	    if(mySpace[suffixArray[i] + k] > myTarget[j + k]){
		return 1;
	    }
	    if(mySpace[suffixArray[i] + k] < myTarget[j + k]){
		return -1;
	    }
	}
	
	if( i_length == j_length || i_length > j_length){
	    return 0;
	}else{
	    return -1;
	}
    }
    
    private int subByteStartIndex(int start, int end) {
	// It returns the index of the first suffix which is equal or greater than
	// subBytes;
	// not implemented yet;
	// If myTaget is "Hi Ho", start=0, end=2 means "Hi".
	// For "Ho", it will return 5 for "Hi Ho Hi Ho".
	// For "Ho ", it will return 6 for "Hi Ho Hi Ho".
	for (int i = 0; i < mySpace.length; i++) {
	    if (targetCompare(i, start, end) == 0) {
		return i;
	    }
	}
	return suffixArray.length; // This line should be modified.
    }
    
    private int subByteEndIndex(int start, int end) {
	// It returns the next index of the first suffix which is greater than subBytes;
	// not implemented yet
	// If myTaget is "Hi Ho", start=0, end=2 means "Hi".
	// For "Ho", it will return 7 for "Hi Ho Hi Ho".
	// For "Ho ", it will return 7 for "Hi Ho Hi Ho".
	int beginning = this.subByteStartIndex(start, end);
	for (int i = beginning; i < mySpace.length; i++) {
	    if (targetCompare(i, start, end) != 0) {
		return i;
	    }
	}
	return suffixArray.length; // This line should be modified.
    }
    
    public int subByteFrequency(int start, int end) {
	/*
	 * This method be work as follows, but int spaceLength = mySpace.length; int
	 * count = 0; for(int offset = 0; offset< spaceLength - (end - start); offset++)
	 * { boolean abort = false; for(int i = 0; i< (end - start); i++) {
	 * if(myTarget[start+i] != mySpace[offset+i]) { abort = true; break; } }
	 * if(abort == false) { count++; } }
	 */
	int first = subByteStartIndex(start, end);
	int last1 = subByteEndIndex(start, end);
	System.out.println(last1 +","+first);
	return last1 - first;
    }
    
    public void setTarget(byte[] target) {
	myTarget = target;
	if (myTarget.length > 0)
	    targetReady = true;
    }
    
    public int frequency() {
	if (targetReady == false)
	    return -1;
	if (spaceReady == false)
	    return 0;
	return subByteFrequency(0, myTarget.length);
    }
    
    public static void main(String[] args) {
	Frequencer frequencerObject;
	try {
	    frequencerObject = new Frequencer();
	    //frequencerObject.setSpace("Hi Ho Hi Ho".getBytes());
	    frequencerObject.setSpace("AAA".getBytes());
	    frequencerObject.printSuffixArray(); // you may use this line for DEBUG
	    /*
	     * Example from "Hi Ho Hi Ho" 
	     * 0: Hi Ho 
	     * 1: Ho 
	     * 2: Ho Hi Ho 
	     * 3:Hi Ho 
	     * 4:Hi Ho Hi Ho
	     * 5:Ho 
	     * 6:Ho Hi Ho 
	     * 7:i Ho 
	     * 8:i Ho Hi Ho 
	     * 9:o 
	     * A:o Hi Ho
	     */
	    
	    //frequencerObject.setTarget("H".getBytes());
	    frequencerObject.setTarget("AA".getBytes());
	    if (frequencerObject.subByteStartIndex(0, 1)==1) {
		System.out.println("OK");
	    }else {
		System.out.println("WRONG");
	    }
	    
	    if (frequencerObject.subByteEndIndex(0, 1)==3) {
		System.out.println("OK");
	    }else {
		System.out.println("WRONG");
	    }
	    System.out.println(frequencerObject.subByteStartIndex(0, 1) + " " + frequencerObject.subByteEndIndex(0, 1));
	    int result = frequencerObject.frequency();
	    System.out.print("Freq = " + result + " ");
	    if (4 == result) {
		System.out.println("OK");
	    } else {
		System.out.println("WRONG");
	    }
	} catch (Exception e) {
	    System.out.println("STOP");
	    e.printStackTrace();
	}
    }
}
