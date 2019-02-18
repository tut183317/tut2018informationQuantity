package s4.B183324;
import java.lang.*;
import java.lang.String;
import java.util.*;
import java.util.List;
import s4.specification.*;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;

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
    List<Byte> subSpace = new ArrayList<Byte>();
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
                //System.out.println("j="+j);
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
      for(; i < mySpace.length && j < mySpace.length; i++,j++){
        if(mySpace[i] > mySpace[j]){
          return 1;
        }
        else if(mySpace[i] < mySpace[j]){
          return -1;
        }
        else if(mySpace[i] == mySpace[j]){
        }
      }
      if((mySpace.length == i)&&(mySpace.length > j)){
        return -1;
      }
      else if((mySpace.length > i)&&(mySpace.length == j)){
        return 1;
      }
	   return 0; // This line should be modified.
    }
    /*
    * マージ
    * 2つの配列a1[]とa2[]を併合してa[]を作ります。
    */
    private void merge(int[] a1,int[] a2,int[] a){
      int i=0,j=0;
      while(i<a1.length || j<a2.length){
        if(j>=a2.length || (i<a1.length && (suffixCompare(a2[j],a1[i])==1))) {
        	a[i+j]=a1[i];
        	i++;
        }
        else{
        	a[i+j]=a2[j];
        	j++;
        }
      }
    }

    /*
    * マージソート
    * 既にソート済みの2つの配列を併合して新しい配列を
    * 生成することで、データのソートを行います。
    */
    private void mergeSort(int[] a){
      //System.out.println("length of a =" + a.length);
      if(a.length>1){
        int m=a.length/2;
        int n=a.length-m;
        int[] a1=new int[m];
        int[] a2=new int[n];
        for(int i=0;i<m;i++){
          a1[i]=a[i];
          //System.out.print(" a1[" + i + "] =" + a1[i]);
        }//System.out.println(" ");
        for(int i=0;i<n;i++){
          a2[i]=a[m+i];
          //System.out.print(" a2[" + i + "] =" + a2[i]);
        }//System.out.println(" ");
        //System.out.print(Arrays.toString(a1));
        //System.out.print(Arrays.toString(a2));System.out.println(" ");
        mergeSort(a1);
        mergeSort(a2);
        merge(a1,a2,a);
      }
    }

    /*
    * ソート
    */
    private void sort(int[] a){
      mergeSort(a);
    }

    public void setSpace(byte []space) {
    	mySpace = space; if(mySpace.length>0) spaceReady = true;
    	suffixArray = new int[space.length];
    	// put all suffixes  in suffixArray. Each suffix is expressed by one integer.
    	for(int i = 0; i< space.length; i++) {
    	    suffixArray[i] = i;
    	}
    	// Sorting is not implmented yet.
    	// ****  Please write code here... ***

      //bubble sort
      /*for (int i = 0; i < mySpace.length - 1; i++) {
          for (int j = mySpace.length - 1; j > i; j--) {
              if (suffixCompare(suffixArray[j-1], suffixArray[j])==1) {
                  int tmpNum = suffixArray[j - 1];
                  suffixArray[j - 1] = suffixArray[j];
                  suffixArray[j] = tmpNum;
              }
          }
      }*/

      //merge sort
      sort(suffixArray);
    }

    private int targetCompare(int i, int j, int end) {
    	// comparing suffix_i and target_j_end by dictonary order with limitation of length;
    	// if the beginning of
      //suffix_i matches target_i_end, and suffix is longer than target  it returns 0;
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
    	//
        if(mySpace[i] > myTarget[j]){
          return 1;
        }
        else if(mySpace[i] < myTarget[j]){
          return -1;
        }else if(mySpace[i] == myTarget[j]){
          i++;
          for(j++; j < end; j++, i++){
            if(i < suffixArray.length){
              if(mySpace[i] > myTarget[j]){
                return 1;
              }
              else if(mySpace[i] < myTarget[j]){
                return -1;
              }
            }
          }
          if(mySpace.length - i < end - j){
            return -1;
          }
          /*if(mySpace.length < myTarget.length){
            return -1;
          }*/
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
      /*for(int i = 0; i < suffixArray.length; i++){
        if(targetCompare(suffixArray[i], start, end)==0){
          //System.out.println("i="+i);
          return i;
        }
      }*/
      int Left = 0;
      int Right = suffixArray.length -1;
      int middle = (Left + Right) / 2;
      int index = suffixArray.length;
      while(Left <= Right){
        middle = (Left + Right) / 2;
        int result_tcnp = targetCompare(suffixArray[middle], start, end);
        if(result_tcnp == 0){
          index = middle;
          Right = middle - 1;
        }else if(result_tcnp == -1){
          Left = middle + 1;
        }else if(result_tcnp == 1){
          Right = middle - 1;
        }
      }
    	return index; // This line should be modified.
    }

    private int subByteEndIndex(int start, int end) {
    	// It returns the next index of the first suffix which is greater than subBytes;
    	// not implemented yet
    	// For "Ho", it will return 7  for "Hi Ho Hi Ho".
    	// For "Ho ", it will return 7 for "Hi Ho Hi Ho".
    	//
    	// ****  Please write code here... ***
    	//
      /*for(int i = suffixArray.length-1; i > -1; i--){
        if(targetCompare(suffixArray[i], start, end)==0){
          //System.out.println("i="+(i+1));
          return i+1;
        }
      }
    	return suffixArray.length; // This line should be modified. */
      int Left = 0;
      int Right = suffixArray.length -1;
      int middle = (Left + Right) / 2;
      int index = suffixArray.length;
      while(Left <= Right){
        middle = (Left + Right) / 2;
        int result_tcnp = targetCompare(suffixArray[middle], start, end);
        if(result_tcnp == 0){
          index = middle + 1;
          Left = middle + 1;
        }else if(result_tcnp == -1){
          Left = middle + 1;
        }else if(result_tcnp == 1){
          Right = middle - 1;
        }
      }
    	return index; // This line should be modified.
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
      //System.out.println("first = " + first);
    	int last1 = subByteEndIndex(start, end);
      //System.out.println("last1 = " + last1);
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
	    //frequencerObject.setTarget("H".getBytes());
      frequencerObject.setTarget("Hi Ho Hi Ho H".getBytes());
	    //
	    // ****  Please write code to check subByteStartIndex, and subByteEndIndex
	    //
      /*for(int i = 0; i < frequencerObject.suffixArray.length; i++){
        System.out.println("my=" + frequencerObject.targetCompare(frequencerObject.suffixArray[i], 0, frequencerObject.myTarget.length));
      }*/
	    int result = frequencerObject.frequency();
	    System.out.print("Freq = "+ result+" ");
	    if(4 == result) { System.out.println("OK"); } else {System.out.println("WRONG"); }
	}
	catch(Exception e) {
	    System.out.println("STOP");
      System.out.println(e);
    }
  }
}
