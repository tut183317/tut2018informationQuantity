package s4.B183337;
import java.lang.*;
import s4.specification.*;

/*package s4.specification;
public interface FrequencerInterface {	   // This interface provides the design for frequency counter.
	void setTarget(byte	 target[]); // set the data to search.
	void setSpace(byte	space[]);  // set the data to be searched target from.
	int frequency(); //It return -1, when TARGET is not set or TARGET's length is zero
					//Otherwise, it return 0, when SPACE is not set or SPACE's length is zero
					//Otherwise, get the frequency of TAGET in SPACE
	int subByteFrequency(int start, int end);
	// get the frequency of subByte of taget, i.e target[start], taget[start+1], ... , target[end-1].
	// For the incorrect value of START or END, the behavior is undefined.
}
*/

public class Frequencer implements FrequencerInterface{
	// Code to start with: This code is not working, but good start point to work.
	byte [] myTarget;
	byte [] mySpace;
	boolean targetReady = false;
	boolean spaceReady = false;
	int []	suffixArray;

	// The variable, "suffixArray" is the sorted array of all suffixes of mySpace.
	// Each suffix is expressed by a integer, which is the starting position in mySpace.
	// The following is the code to print the variable
	private void printSuffixArray() {
	if(spaceReady) {
		for(int i=0; i< mySpace.length; i++) {
			int s = suffixArray[i];
			for(int j=s;j<mySpace.length;j++) {
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
	// "i"		<  "o"		  : compare by code
	// "Hi"		<  "Ho"		  ; if head is same, compare the next element
	// "Ho"		<  "Ho "	  ; if the prefix is identical, longer string is big

	int max_ij = Math.max(i, j);
	for(int k=0; k<mySpace.length-max_ij; k++){
		if(mySpace[i] > mySpace[j])         return 1;
		else if(mySpace[i] < mySpace[j])    return -1;
		else if(mySpace[i] == mySpace[j]){  i++; j++; continue; }
	}
	//  文字列の長いほうが後ろになるようにi,jを比較する
	if(i > j)		return -1;
	else if(i < j)	return  1;
	else if(i == j)	return  0;
	else ;
	return 0;
	}

	public void setSpace(byte []space) { 
	mySpace = space; if(mySpace.length>0) spaceReady = true; 
	suffixArray = new int[space.length];
	// put all suffixes	in suffixArray. Each suffix is expressed by one integer.
	for(int i=0; i<space.length; i++) {
		suffixArray[i]=i;
	}
	/* * * * * * * * * * * * * * * * * * * * 
	*  Like Bubble Sort
	* * * * * * * * * * * * * * * * * * * * */
	/*
	for (int i = 0; i < suffixArray.length - 1; i++) {
	for (int j = suffixArray.length - 1; j > i; j--) {
		int tmpNum = suffixArray[j-1];
		int sufComp = suffixCompare(suffixArray[j-1], suffixArray[j]);
		if(sufComp == 1){
			tmpNum = suffixArray[j-1];
			suffixArray[j-1] = suffixArray[j];
			suffixArray[j] = tmpNum;
		}
	}
	}
	*/

	/* * * * * * * * * * * * * * * * * * * * 
	*  Like merge sort
	* * * * * * * * * * * * * * * * * * * * */
	mergeSort(suffixArray);
	}

	private void merge(int []spa1, int []spa2, int []array){
	// Merging for mergeSort()
	// Merge two arrays;
		int i=0, j=0;
		while(i<spa1.length || j<spa2.length){
			if(j>=spa2.length || (i<spa1.length && suffixCompare(spa1[i], spa2[j])==-1)){
				array[i+j] = spa1[i]; i++;
			}else{
				array[i+j] = spa2[j]; j++;
			}
		}
	}
	private void mergeSort(int []array){
	// mergeSort for suffixArray.
	// Sorting suffixArray in the correct order. 
		if(array.length>1){
			int m=array.length/2, n=array.length-m;	// ２つに分割
			int [] spa1=new int[m]; // First splited array.
			int [] spa2=new int[n]; // Second splited array.
			for(int i=0; i<m; i++) spa1[i]=array[i];
			for(int i=0; i<n; i++) spa2[i]=array[i+m];
			mergeSort(spa1); mergeSort(spa2);
			merge(spa1, spa2, array);
		}
	}

	private int targetCompare(int i, int j, int end) {
	// comparing suffix_i and target_j_end by dictonary order with limitation of length;
	// if the beginning of suffix_i matches target_j, and suffix is longer than target	it returns 0;
	// if suffix_i > target_j_end it return 1;
	// if suffix_i < target_j_end it return -1
	// It is not implemented yet.
	// It should be used to search the apropriate index of some suffix.
	// Example of search
	// suffix		   target
	// "o"		 >	   "i"
	// "o"		 <	   "z"
	// "o"		 =	   "o"
	// "o"		 <	   "oo"
	// "Ho"		 >	   "Hi"
	// "Ho"		 <	   "Hz"
	// "Ho"		 =	   "Ho"
	// "Ho"		 <	   "Ho "   : "Ho " is not in the head of suffix "Ho"
	// "Ho"		 =	   "H"	   : "H" is in the head of suffix "Ho"

	if(mySpace[i] > myTarget[j])        return  1;
	else if(mySpace[i] < myTarget[j])   return -1;
	else if(mySpace[i] == myTarget[j]){
		if(mySpace.length-i >= end-j){
			for(j++, i++; j<end; j++, i++){
				if(mySpace[i] > myTarget[j])      return  1;
				else if(mySpace[i] < myTarget[j]) return -1;
			}
			return 0;
		}else{
			for(j++, i++; i<mySpace.length; j++, i++){
				if(mySpace[i] > myTarget[j])      return  1;
				else if(mySpace[i] < myTarget[j]) return -1;
			}
			return -1;

		}
	}
	return -1;
	}

		private int subByteStartIndex(int start, int end) {
	// It returns the index of the first suffix which is equal or greater than subBytes;
	// not implemented yet;
	// For "Ho", it will return 5  for "Hi Ho Hi Ho".
	// For "Ho ", it will return 6 for "Hi Ho Hi Ho".

	/* * * * * * * * * * * * 
	* Linear Search
	* * * * * * * * * * * * *
	
	for(int i=0; i<suffixArray.length; i++){
		if(targetCompare(suffixArray[i], start, end) == 0) return i;
	}
	return suffixArray.length; 
	

	/* * * * * * * * * * * * 
	* Binary Search
	* * * * * * * * * * * * */
	int index=suffixArray.length;
	int left=0, right=suffixArray.length-1;
	int mid=suffixArray.length/2;
	while(left<=right){
		mid = (left+right)/2;
		int tComp = targetCompare(suffixArray[mid], start, end);
		if(tComp == -1) left=mid+1;
		else if(tComp == 1) right=mid-1;
		else{ index=mid; right=mid-1; }
	}
	return index;
	}

	private int subByteEndIndex(int start, int end) {
	// It returns the next index of the first suffix which is greater than subBytes;
	// not implemented yet
	// For "Ho", it will return 7  for "Hi Ho Hi Ho".
	// For "Ho ", it will return 7 for "Hi Ho Hi Ho".
	
	/* * * * * * * * * * * * 
	* Linear Search
	* * * * * * * * * * * * */
	/*
	for(int i=suffixArray.length-1; i>=0; i--){
		if(targetCompare(suffixArray[i], start, end) == 0)  return i+1;
	}
	return suffixArray.length;
	*/

	/* * * * * * * * * * * * 
	* Binary Search
	* * * * * * * * * * * * */
	int index=suffixArray.length;
	int left=0, right=suffixArray.length-1;
	int mid=suffixArray.length/2;
	while(left<=right){
		mid = (left+right)/2;
		int tComp = targetCompare(suffixArray[mid], start, end);
		if(tComp == -1) left=mid+1;
		else if(tComp == 1) right=mid-1;
		else{ index=mid+1; left=mid+1; }
	}
	return index;
	}
	
	public int subByteFrequency(int start, int end) {
	/* This method be work as follows, but
	int spaceLength = mySpace.length;
	int count = 0;
	for(int offset = 0; offset< spaceLength - (end - start); offset++) {
		boolean abort = false;
		for(int i = 0; i< (end - start); i++) {
		if(myTarget[start+i] != mySpace[offset+i]) { abort = true; break; }
		}
		if(abort == false) { count++; }
	}
	*/
	int first = subByteStartIndex(start, end);
	int last1 = subByteEndIndex(start, end);
	return last1 - first;
	}

	public void setTarget(byte [] target) { 
	myTarget = target; if(myTarget.length>0) targetReady = true; 
	}

	public int frequency() {
	if(targetReady == false) return -1;
	if(spaceReady == false) return 0;
	return subByteFrequency(0, myTarget.length);
	}

	public static void main(String[] args) {
	Frequencer frequencerObject;
	if(args.length!=0 && args[0].equals("test")){
	try {
		frequencerObject = new Frequencer();
		frequencerObject.setSpace("Hi Ho Hi Ho".getBytes());
		//frequencerObject.printSuffixArray(); // you may use this line for DEBUG
		/* Example from "Hi Ho Hi Ho"
		   0: Hi Ho
		   1: Ho
		   2: Ho Hi Ho
		   3:Hi Ho
		   4:Hi Ho Hi Ho
		   5:Ho
		   6:Ho Hi Ho
		   7:i Ho
		   8:i Ho Hi Ho
		   9:o
		   A:o Hi Ho
		*/
		frequencerObject.setTarget("H".getBytes());
		/************************************
		 * Debbuging TestCase
		 ************************************/
		Frequencer testObj = new Frequencer();
		testObj.setSpace("Hi Ho Hi Ho".getBytes());
		testObj.setTarget("H".getBytes());
		System.out.println("TestCase 1: ");
		System.out.println("Space  : \"Hi Ho Hi Ho\"");
		System.out.println("Target : \"H\"");
		System.out.println("\n-- printSuffixArray()");
		testObj.printSuffixArray();
		System.out.println("\n-- targetCompare()");
		for(int i=0; i<testObj.suffixArray.length; i++){
			System.out.println("\ttargetCompare[suffix[" + i + "]] : " + testObj.targetCompare(testObj.suffixArray[i], 0, testObj.myTarget.length));
		}
		System.out.println("\n-- subByteStartIndex()");
		System.out.println("\tsubByteStartIndex : "+testObj.subByteStartIndex(0, testObj.myTarget.length));
		System.out.println("\n-- subByteEndIndex()");
		System.out.println("\tsubByteEndIndex : "+testObj.subByteEndIndex(0, testObj.myTarget.length));
		int result = frequencerObject.frequency();
		System.out.println("-- Result");
		System.out.print("\tFreq = "+ result+" ");
		if(4 == result) { System.out.println("OK"); } else {System.out.println("WRONG"); }

		}
	catch(Exception e) {
		System.out.println("STOP");
	}
	}
	}
}
