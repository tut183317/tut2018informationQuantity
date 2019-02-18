package s4.B183310; // Please modify to s4.Bnnnnnn, where nnnnnn is your student ID.
import java.lang.*;
import s4.specification.*;

/*
interface FrequencerInterface {     // This interface provides the design for frequency counter.
    void setTarget(byte[]  target); // set the data to search.
    void setSpace(byte[]  space);  // set the data to be searched target from.
    int frequency(); //It return -1, when TARGET is not set or TARGET's length is zero
                    //Otherwise, it return 0, when SPACE is not set or Space's length is zero
                    //Otherwise, get the frequency of TAGET in SPACE
    int subByteFrequency(int start, int end);
    // get the frequency of subByte of taget, i.e target[start], taget[start+1], ... , target[end-1].
    // For the incorrect value of START or END, the behavior is undefined.
*/


public class Frequencer implements FrequencerInterface{
	// Code to Test, *warning: This code  contains intentional problem*
	byte [] myTarget;
	byte [] mySpace;
	boolean targetReady = false;
	boolean spaceReady = false;

	int [] suffixArray;

	private void printSuffixArray(){
		if(spaceReady){
			for(int i=0;i<mySpace.length;i++){
				int s = suffixArray[i];
				for(int j=s;j<mySpace.length;j++) {
					System.out.write(mySpace[j]);
				}
				System.out.println("");
			}
		}
	}

	private int suffixCompare(int i, int j){
		//	It	is	not	implemented	yet,
		//	It	should	be	used	to	create	suffix	array.
		//	Example	of	dictionary	order
		//	"i"						<		"o"								:	compare	by	code
		//	"Hi"					<		"Ho"							;	if	head	is	same,	compare	the	next	element
		//	"Ho"					<		"Ho	"						;	if	the	prefix	is	identical,	longer	string	is	big
		//
		//	****		Please	write	code	here...	***
		//
		if (mySpace[i] > mySpace[j]) {
			return 1;
		} else if(mySpace[i] < mySpace[j]) {
			return -1;
		} else {
			if(i+1 >= mySpace.length && j+1 >= mySpace.length){
				return 0;
			}else if(i+1 >= mySpace.length){
				return -1;
			}else if(j+1 >= mySpace.length){
				return 1;
			}
			return suffixCompare(i+1,j+1);    //	This	line	should	be	modified.
		}
	}

	public void	setSpace(byte []space){
		mySpace	= space;	if(mySpace.length>0)	spaceReady = true;
		suffixArray	= new int[space.length];
		byte[] foo = new byte[space.length];
		//	put	all	suffixes in	suffixArray.Each suffix	is expressed by	one	integer.
		for(int	i=0; i<mySpace.length; i++){
			suffixArray[i] = i;
		}

//		quickSort(suffixArray,0,suffixArray.length-1);
		mergeSort(suffixArray,0,suffixArray.length-1);
//		//	Sorting
//		for(int i=0;i<mySpace.length-1;i++) {
//			for (int j = 0; j < space.length - i - 1; j++) {
//				int r = suffixCompare(suffixArray[j], suffixArray[j + 1]);
//				//suffix compare
//				if (r == 1) { // suffix_j > suffix_j+1
//					int tmp = suffixArray[j];
//					suffixArray[j] = suffixArray[j + 1];
//					suffixArray[j + 1] = tmp;
//				}
//			}
//		}
	}

	private void merge(int arr[], int l, int m, int r)	{
		int i;
		int j;
		int k;
		int n1 = m - l + 1;
		int n2 = r - m;

		int [] L = new int[n1];
		int [] R = new int[n2];

		for (i = 0; i < n1; i++)
			L[i] = arr[l + i];
		for (j = 0; j < n2; j++)
			R[j] = arr[m + 1+ j];

		i = 0; // Initial index of first subarray
		j = 0; // Initial index of second subarray
		k = l; // Initial index of merged subarray
		while (i < n1 && j < n2){
			if (this.suffixCompare(L[i], R[j]) == -1){
				arr[k] = L[i];

				i++;
			}else{
				arr[k] = R[j];
				j++;
			}
			k++;
		}
		while (i < n1){
			arr[k] = L[i];
			i++;
			k++;
		}
		while (j < n2){
			arr[k] = R[j];
			j++;
			k++;
		}
	}

	private void mergeSort(int arr[], int l, int r)
	{
		if (l < r){
			int m = l+(r-l)/2;
			mergeSort(arr, l, m);
			mergeSort(arr, m+1, r);
			merge(arr, l, m, r);
		}
	}

	private int targetCompare(int i, int j, int end) {
		int s=suffixArray[i];
		for (int k=0;j<end;k++,j++){
			if(mySpace[s+k] < myTarget[j] || end-j > mySpace.length-s){
				return -1;
			} else if (mySpace[s+k] > myTarget[j]){
				return 1;
			}
		}
		return 0;
	}

	private int subByteStartIndex(int start, int end) {
		for (int i=0; i<=mySpace.length-1;i++) {
			if (targetCompare(i,start,end) == 0) {
				return i;
			}
		}
		return 0;
	}

	private int subByteEndIndex(int start, int end) {
		for (int i=mySpace.length-1; i>=0;i--) {
			if (targetCompare(i,start,end) == 0) {
				return i+1;
			}
		}
		return 0;
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
		return count;
	*/
		int first = subByteStartIndex(start, end);
//		System.out.println(first);
		int last1 = subByteEndIndex(start, end);
//		System.out.println(last1);
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
		Frequencer myObject;
		int freq;
		try {
			System.out.println("checking my Frequencer");
			myObject = new Frequencer();
			myObject.setSpace("Hi Ho Hi Ho".getBytes());
			myObject.printSuffixArray(); // you may use this line for DEBUG
			/*	Example	from "Hi Ho Hi Ho"
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

			myObject.setTarget("H".getBytes());

			freq = myObject.frequency();
			System.out.print("\"H\" in \"Hi Ho Hi Ho\" appears "+freq+" times. ");
			if(4 == freq) { System.out.println("OK"); } else {System.out.println("WRONG"); }

			myObject = new Frequencer();
			myObject.setSpace("AAA".getBytes());
			myObject.setTarget("A".getBytes());
			freq = myObject.frequency();
			if(3 != freq) { System.out.println("frequency() for AAA should return 3, when target is A. But it returns  "+freq); }

			myObject = new Frequencer();
			myObject.setSpace("AAA".getBytes());
			myObject.setTarget("AA".getBytes());
			freq = myObject.frequency();
			if(2 != freq) { System.out.println("frequency() for AAA, should return 2, when target is AA. But it returns "+freq); }

			myObject = new Frequencer();
			myObject.setSpace("AAB".getBytes());
			myObject.setTarget("B".getBytes());
			freq = myObject.frequency();
			if(1 != freq) { System.out.println("frequency() for AAB, should return 1, when target is B. But it returns "+freq); }

			myObject = new Frequencer();
			myObject.setSpace("AAA".getBytes());
			myObject.setTarget("AAA".getBytes());
			freq = myObject.frequency();
			if(1 != freq) { System.out.println("frequency() for AAA, should return 1, when target is AAA. But it returns "+freq); }

			myObject = new Frequencer();
			myObject.setSpace("AAAB".getBytes());
			myObject.setTarget("AAA".getBytes());
			freq = myObject.frequency();
			if(1 != freq) { System.out.println("frequency() for AAA, should return 1, when target is AAA. But it returns "+freq); }

		}
		catch(Exception e) {
			System.out.println("Exception occurred: STOP");
		}
	}
}