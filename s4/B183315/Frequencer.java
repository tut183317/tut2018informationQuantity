package s4.B183315;
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
    int k, cnt = 0, tmp = j;
    if(mySpace[i] > mySpace[j])
        return 1;
    if(mySpace[i] < mySpace[j])
        return -1;
    if(mySpace[i] == mySpace[j]){
        if(i > j)
            tmp = i;
        for(k = 0; k < (mySpace.length - tmp); k++){
            cnt ++;
            if(mySpace[i + k] > mySpace[j + k]){
                return 0;
            }
            if(mySpace[i + k] < mySpace[j + k]){
                return -1;
            } 
        }
        if(cnt == mySpace.length - tmp && i < j)
            return 0;
    }
    return -1;
    }

    public void setSpace(byte []space) { 
	mySpace = space; if(mySpace.length > 0) spaceReady = true; 
	suffixArray = new int[space.length];
	// put all suffixes in suffixArray. Each suffix is expressed by one integer.
	for(int i = 0; i< space.length; i++) {
	    suffixArray[i] = i;
	}
	// Sorting is not implmented yet.
	//
	//
	// ****  Please write code here... ***
	
    
    	mergeSort(suffixArray);
    	
    	
    /*
	int left = 0;
	int rigth = space.length-1;
	quick_sort(left, rigth);
    */
    	/*
    	for(int i = space.length - 1; i > 0; i--){
       	
        	for(int j = 0; j < i; j++){
            	if(suffixCompare(suffixArray[j], suffixArray[j + 1]) == 1 || suffixCompare(suffixArray[j], suffixArray[j + 1]) == 0){
              	 
                	int box = suffixArray[j];
                	suffixArray[j] = suffixArray[j + 1];
                	suffixArray[j + 1] = box;
            	}else{
              
            	}
        	}
    	}
    	*/
    }
	
	private void merge(int[] a1, int[] a2, int[] a){
		int i=0,j=0;
		while(i<a1.length || j<a2.length){
			if(j>=a2.length || (i<a1.length && -1 == suffixCompare(a1[i], a2[j]))){
				a[i+j] = a1[i];
				i++;
			}else{
				a[i+j] = a2[j];
				j++;
			}
		}
	}

	private void mergeSort(int[] a){
		
		if(a.length>1){
			int m=a.length/2;
			int n=a.length-m;
			int[] a1=new int[m];
			int[] a2=new int[n];
			
			for(int i=0; i<m; i++){
				a1[i] = a[i];
			}
			for(int i=0; i<n; i++){
				a2[i] = a[m+i];
			}
			mergeSort(a1);
			mergeSort(a2);
			merge(a1, a2, a);
		}
	}
	

    /*
    public void quick_sort(int left, int right){
		if(left >= right ){
	    	return;
		}
	    int p = (left + right)/2;
	    int l = left;
	    int r = right;
	    while(l <= r){
		while(suffixCompare(suffixArray[l], suffixArray[p]) == -1 ){
		    l++;
		}
		while(suffixCompare(suffixArray[r], suffixArray[p]) == 1 ){
		    r--;
		}
		if(l <= r){
		    int tmp = suffixArray[l];
		    suffixArray[l] = suffixArray[r];
		    suffixArray[r] = tmp;
		    l++;
		    r--;
		}
	    }
	    quick_sort(left, r);
	    quick_sort(l, right);
    }
	*/
	
    
	/*
    for(int i = space.length - 1; i > 0; i--){
       
        for(int j = 0; j < i; j++){
            if(suffixCompare(suffixArray[j], suffixArray[j + 1]) == 1 || suffixCompare(suffixArray[j], suffixArray[j + 1]) == 0){
               
                int box = suffixArray[j];
                suffixArray[j] = suffixArray[j + 1];
                suffixArray[j + 1] = box;
            }else{
              
            }
        }
    }
	*/


    private int targetCompare(int i, int j, int end) {
	// comparing suffix_i and target_j_end by dictonary order with limitation of length;
	// if the beginning of suffix_i matches target_j, and suffix is longer than target  it returns 0;
	// if suffix_i > target_j it return 1;
	// if suffix_i < target_j it return -1
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
	//
	
	
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
	// It returns the index of the first suffix which is equal or greater than subBytes;
	// not implemented yet;
	// For "Ho", it will return 5  for "Hi Ho Hi Ho".
	// For "Ho ", it will return 6 for "Hi Ho Hi Ho".
	//
	// ****  Please write code here... ***
	//

	/*
      //MY_program
	for(int k=0; k<mySpace.length; k++){
	    if(0 == targetCompare(k, start, end))
		return k;
	}
    */
    
    	int pLeft = 0;
		int pRight = mySpace.length - 1;
    	//int L_flag = 0;
    	//int R_flag = 0;
    	int flag = 0;

		do {
			int center = (pLeft + pRight) / 2;
			
			
			if (0 == targetCompare(center, start, end)) {
				pRight = center - 1;
				flag = 1; 
				//System.out.println("Rigth ="+pRight+"");
			} else if (-1 == targetCompare(center, start, end)){
				pLeft = center + 1; 
				//System.out.println("Left  ="+pLeft+"");
			} else {
				pRight = center - 1;
				//System.out.println("Rigth ="+pRight+"");
			}
		} while (pLeft < pRight);
    	
    	
		
    	//System.out.println("\n");
    	/*
    	System.out.println("\n");
    	System.out.println("L_flag  ="+L_flag+"");
    	System.out.println("R_flag  ="+R_flag+"");
		System.out.println("\n");
    	*/
    	
    	
    	
    	if( flag == 1 ){ 
    		if( 0 == targetCompare(pLeft, start, end) ){
    			return pLeft;
    		} else {
    			return pLeft+1;
    		}
    	}
    	
    	if( flag == 0 ){ 
    		//System.out.println("pLeft ="+pLeft+"");
    		//System.out.println("start ="+start+"");
    		//System.out.println("end   ="+end+"");
    		
    		if( 0 == targetCompare(pLeft, start, end) ){
    			return pLeft;
    		} else if( pLeft >= mySpace.length-1 ) {
    			return suffixArray.length;
    		} else if(0 == targetCompare(pLeft+1, start, end)){
    			return pLeft+1;
    		} else {
    			return suffixArray.length;
    		}
    		
    	}
    	
    	
		return suffixArray.length; // This line should be modified.
    	
    }

    private int subByteEndIndex(int start, int end) {
	// It returns the next index of the first suffix which is greater than subBytes;
	// not implemented yet
	// For "Ho", it will return 7  for "Hi Ho Hi Ho".
	// For "Ho ", it will return 7 for "Hi Ho Hi Ho".
	//
	// ****  Please write code here... ***
	//
    /*
	 //MY_program
	for(int k=0; k<mySpace.length; k++){
	    if(1 == targetCompare(k, start, end))
		return k;
	}
    */
    	int pLeft = 0;
		int pRight = mySpace.length - 1;
    	int flag = 0;

		do {
			int center = (pLeft + pRight) / 2;
			
			if (1 == targetCompare(center, start, end)) { 
				pRight = center - 1;
				//System.out.println("Rigth ="+pRight+"");
			} else if (-1 == targetCompare(center, start, end)){
				pLeft = center + 1;
				//System.out.println("Left ="+pLeft+"");
			} else { 
				pLeft = center + 1;
				flag = 1;
				//System.out.println("Left ="+pLeft+"");
			}
		} while (pLeft < pRight);
    	/*
    	System.out.println("\n");
    	System.out.println("Left  ="+pLeft+"");
    	System.out.println("Rigth ="+pRight+"");
    	System.out.println("FRAG ="+flag+"");
		System.out.println("\n");
    	*/
    	
    	if( flag == 1 ){ 
    		if( 1 == targetCompare(pLeft, start, end) ){
    			return pLeft;
    		} else {
    			return pLeft+1;
    		}
    	}
    	
    	if( flag == 0 ){ 
    		if( 0 == targetCompare(pLeft, start, end) ){
    			return pLeft+1;
    		} else if( pLeft >= 0 ){
    			return suffixArray.length;
    		} else if(0 == targetCompare(pLeft-1, start, end)){
    			return pLeft;
    		} else {
    			return suffixArray.length;
    		}
    	}
    	
		return suffixArray.length; // This line should be modified.
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
	try {
	    frequencerObject = new Frequencer();
	    frequencerObject.setSpace("Hi Ho Hi Ho".getBytes());
	    frequencerObject.printSuffixArray(); // you may use this line for DEBUG
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
	    //
	    // ****  Please write code to check subByteStartIndex, and subByteEndIndex
	    //

	    //System.out.println(frequencerObject.subByteStartIndex(0,frequencerObject.mySpace.length));
	    System.out.println(frequencerObject.subByteStartIndex(0, 1));
	    //System.out.println(frequencerObject.subByteEndIndex(0, 6));
	    //System.out.println(frequencerObject.suffixArray.length);
	    System.out.println("\n");

	    int result = frequencerObject.frequency();
	    System.out.print("Freq = "+ result+" ");
	    if(4 == result) { System.out.println("OK"); } else {System.out.println("WRONG"); }
	}
	catch(Exception e) {
	    System.out.println("STOP");
		e.printStackTrace();
	}
    }
}
