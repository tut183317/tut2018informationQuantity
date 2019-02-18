package s4.B183318;
import java.lang.*;
import s4.specification.*;


/*package s4.specification;

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



public class Frequencer implements FrequencerInterface{
    // Code to start with: This code is not working, but good start point to work.
    byte [] myTarget;
    byte [] mySpace;
    boolean targetReady = false;
    boolean spaceReady = false;

    int []  suffixArray;

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
	// "i"      <  "o"        : compare by code
	// "Hi"     <  "Ho"       ; if head is same, compare the next element
	// "Ho"     <  "Ho "      ; if the prefix is identical, longer string is big
	//
	// ****  Please write code here... ***
	//
	for(;i < mySpace.length && j < mySpace.length;i++,j++){
    	if(mySpace[i] > mySpace[j]){
        	return 1;
		}
		else if(mySpace[i] < mySpace[j]){
			return -1;
		}
    }
    
    if(i<j) return 1;
    else if(i>j) return -1;
	return 0;
  }

    public void setSpace(byte []space) {
		mySpace = space; if(mySpace.length>0) spaceReady = true;
		suffixArray = new int[space.length];
		// put all suffixes  in suffixArray. Each suffix is expressed by one integer.
		for(int i = 0; i< space.length; i++) {
	    	suffixArray[i] = i;
		}

	// Bubble Sort 
	// for(int j = 0; j < suffixArray.length - 1; j++){
	// 	for(int k = suffixArray.length - 1; k > j; k--){
	// 		if(suffixCompare(suffixArray[k-1],suffixArray[k]) == 1){
	// 			int tmp = suffixArray[k];
	// 			suffixArray[k] = suffixArray[k-1];
	// 			suffixArray[k-1] = tmp;
 //      		}
 //    	}
 //  	}  

 	//marge sort
  	mergeSort(suffixArray,suffixArray.length);

    }

	//mergeSort
	public void mergeSort(int[] a, int n) {
    	//If the size of the array lowers to "2", finish this function.
    	if (n < 2) {
        	return;
    	}

    	int mid = n/2;
		//left    	
    	int[] l = new int[mid];
    	//right
    	int[] r = new int[n-mid];

		//separation process 
		for (int i=0; i<mid; i++) {
        	l[i] = a[i];
		}
		
		for (int i=mid; i<n; i++) {
        	r[i-mid] = a[i];
		}
		
		//Call repeatedly until the array size become 1.
		mergeSort(l, mid);
		mergeSort(r, n-mid);
 
 		//merge left and right
		merge(a, l, r, mid, n-mid);

}

	//merge 2 arrays
	public void merge(int[] a, int[] l, int[] r, int left, int right) {
 
    	int i=0, j=0, k=0;
    	//start mergin 2 arrays.
		while (i<left && j<right){
			if (suffixCompare(l[i],r[j]) == -1){
				a[k++] = l[i++];
			}	
			else{
	            a[k++] = r[j++];
			}
		}

		while (i < left) {
			a[k++] = l[i++];
		}
		while (j < right) {
			a[k++] = r[j++];
		}

	}

    private int targetCompare(int i, int j, int end) {
	// comparing suffix_i and target_j_end by dictonary order with limitation of length;
	// if the beginning of suffix_i matches target_i_end, and suffix is longer than target  it returns 0;
	// if suffix_i > target_j_end it return 1;
	// if suffix_i < target_j_end it return -1
	// It is not implemented yet.
	// It should be used to search the apropriate index of some suffix.
	// Example of search
	// suffix          target
        // "o"       >     "i"
        // "o"       <     "z"
	// "o"       =     "o"
        // "o"       <     "oo"
	// "Ho"      >     "Hi"
	// "Ho"      <     "Hz"
	// "Ho"      =     "Ho"
        // "Ho"      <     "Ho "   : "Ho " is not in the head of suffix "Ho"
	// "Ho"      =     "H"     : "H" is in the head of suffix "Ho"
	//
	// ****  Please write code here... ***


  int k = suffixArray[i]; // beginning letter's index

  for(;j<end;j++,k++){
    if(mySpace[k] > myTarget[j]){
      return 1;
    }else if(mySpace[k] < myTarget[j] || (mySpace.length-k == 1 && end - j != 1)){
      return -1;
      }
    }

    return 0;

  }

    private int subByteStartIndex(int start, int end) {
	// It returns the index of the first suffix which is equal or greater than subBytes;
	// not implemented yet;
	// For "Ho", it will return 5  for "Hi Ho Hi Ho".
	// For "Ho ", it will return 6 for "Hi Ho Hi Ho".
	//
	// ****  Please write code here... ***
	//
    	//this flag becomes true when -1 appears.
    	boolean flag_n1 = false;
    	//this flag becomes true when 0 appears.
    	boolean flag_0 = false;

	    int i=suffixArray.length/2;
	    //save the minimum index that indicate 0.
	    int min0 = suffixArray.length;
	 	//the size of transition of index number.
	 	int shiftsize = suffixArray.length/4;

	 	/*if the first is 1 or the end is -1, 
	 	it means that 0 doesn't exist. */

	 	if(mySpace.length==0||targetCompare(0,start,end)==1||targetCompare(suffixArray.length-1,start,end)==-1){
	 		return min0;
	 	}

	 	while(true){
	 		switch(targetCompare(i,start,end)){
	 			case -1:
	 				i+=shiftsize;
	 				flag_n1=true;
	 				break;
	 			case 1:
	 				i-=shiftsize;
	 				break;
	 			case 0:
	 				flag_0=true;
	 				if(min0>i)
	 					min0 = i;
	 				i-=shiftsize;
	 				break;
	 		}
	 		if(shiftsize==0){
	 			if(i!=suffixArray.length-1){
	 				if(targetCompare(i,start,end)==-1 && targetCompare(i+1,start,end)==0){
	 					return i+1;
	 				}
	 			/*
	 			Sometimes, shiftsize becomes 0 
	 			/even though it has not found the correct value.
	 			then, this program implements linear search
	 			*/			
	 			while(i>0&&(!flag_n1||!flag_0)){
	 				i--;
	 				if(targetCompare(i,start,end)==0&&min0>i){
	 					flag_0=true;
	 					min0=i;
	 				}
	 				else{
	 					flag_n1=true;
	 					}
	 				}
	 			//when -1 has already appearded and 0 has not.
	 			while(flag_n1&&!flag_0&&i<suffixArray.length-1){
	 				i++;
	 				if(targetCompare(i,start,end)==0)
	 					return i;
	 			}

	 			}
	 			return min0;
	 		}

	 		shiftsize = shiftsize >> 1;
	 	}

	 //  for(int i=0;i<suffixArray.length;i++){
	 //    if(targetCompare(i,start,end)==0)
	 //      return i;
	 //  }

		// return suffixArray.length; // This line should be modified.

    }

    private int subByteEndIndex(int start, int end) {
	// It returns the next index of the first suffix which is greater than subBytes;
	// not implemented yet
	// For "Ho", it will return 7  for "Hi Ho Hi Ho".
	// For "Ho ", it will return 7 for "Hi Ho Hi Ho".
	//
	// ****  Please write code here... ***
	//

    	//this flag becomes true when 0 appears.
    	boolean flag_0 = false;
    	//this flag becomes true when 1 appears.
    	boolean flag_1 = false;

	    int i=suffixArray.length/2;
	    //save the minimum index that indicate 1.
	    int min1 = suffixArray.length;
	 	//the size of transition of index number.
	 	int shiftsize = suffixArray.length/4;

	 	/*if the first is 1 or the end is -1, 
	 	it means that suffix doesn't exist. */

	 	if(mySpace.length==0||targetCompare(0,start,end)==1||targetCompare(suffixArray.length-1,start,end)==-1){
	 		return min1;
	 	}

	 	while(true){
	 		switch(targetCompare(i,start,end)){
	 			case -1:
	 				i+=shiftsize;
	 				break;
	 			case 1:
	 				flag_1=true;
	 				if(i>0){
		 				if(targetCompare(i-1,start,end)==0&&min1>i){
		 					return i;
		 				}
		 			}
	 				i-=shiftsize;
	 				break;
	 			case 0:
	 				flag_0=true;
	 				i+=shiftsize;
	 				break;
	 		}
	 		if(shiftsize==0){
	 			if(i!=suffixArray.length-1){
	 				if(targetCompare(i,start,end)==0 && targetCompare(i+1,start,end)==1){
	 					return i+1;
	 				}
	 			/*
	 			Sometimes, shiftsize becomes 0 
	 			/even though it has not found the correct value.
	 			then, this program implements linear search
	 			*/			
	 			while(i<suffixArray.length-1&&!flag_1){
	 				i++;
	 				if(targetCompare(i-1,start,end)==0&&targetCompare(i,start,end)==1&&min1>i){
	 					min1=i;
	 				}
	 				//if there are sequel 1s, return suffixArraylength. 
	 				if(targetCompare(i-1,start,end)==1&&targetCompare(i,start,end)==1){
	 					return suffixArray.length;
	 				}

	 				}

	 			//when 1 has already appearded and 0 has not.
	 			while(flag_1&&!flag_0&&i>0){
	 				i--;
	 				if(targetCompare(i,start,end)==0)
	 					return i+1;
	 			}

	 			}
	 			return min1;
	 		}

	 		shiftsize = shiftsize >> 1;
	 	}

 //  for(int i=subByteStartIndex(start,end);i<suffixArray.length;i++){
 //    if(targetCompare(i,start,end)==1)
 //      return i;
 //  }
	// return suffixArray.length; // This line should be modified.
   
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
	//System.out.println("first"+first);
	//System.out.println("last1"+last1);
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
	try {
	    frequencerObject = new Frequencer();


    frequencerObject.setSpace("Hi Ho Hi Ho".getBytes());

    //

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

      //Test of targetCompare
      int end = frequencerObject.myTarget.length;
      System.out.println("----- test of targetCompare(i,j,end) ----");
  	  for(int i=0;i<frequencerObject.mySpace.length;i++){
   	      System.out.println(frequencerObject.targetCompare(i,0,end));
  	  }
 
	    //
	    // ****  Please write code to check subByteStartIndex, and subByteEndIndex
	    //
      System.out.println("----- test of subByteStartIndex(start,end) ----");
      System.out.println(frequencerObject.subByteStartIndex(0,end));

      System.out.println("----- test of subByteEndIndex(start,end) ----");
      System.out.println(frequencerObject.subByteEndIndex(0,end));


      System.out.println("----- test of frequency() ----");
	    int result = frequencerObject.frequency();
	    System.out.print("Freq = "+ result+" ");
	    if(4 == result) { System.out.println("OK"); } else {System.out.println("WRONG"); }
	}
	catch(Exception e) {
	    System.out.println("STOP");
	}
    }
}
