package s4.B183329;
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
      /////////////////////////////////////////////////////////////////////////////////////
        //
        //
        //max...残り文字列の短い方
        int max = Math.max(i, j);
        
        //文字列の短い方回だけ比較を繰り返す
        for(int k=0;k<suffixArray.length-max;k++){
            if(mySpace[i+k] > mySpace[j+k]){
                return 1;
            }else if(mySpace[i+k] < mySpace[j+k]){
                return -1;
            }else if(mySpace[i+k] == mySpace[j+k]){
                continue;
            }
        }
        
        //最後の文字まで同じ場合文字列が長い方が後に出現
        if(i < j){
            return 1;
        }else if(i > j){
            return -1;
        }
        //
        //
      //////////////////////////////////////////////////////////////////////////////////////
      return 0;
    }

    public void setSpace(byte []space) {
    	mySpace = space;
      if(mySpace.length>0) spaceReady = true;
    	suffixArray = new int[space.length];
    	// put all suffixes  in suffixArray. Each suffix is expressed by one integer.
    	for(int i = 0; i< space.length; i++) {
    	    suffixArray[i] = i;
    	}
    	// Sorting is not implmented yet.
    	//
    	//
    	// ****  Please write code here... ***
    //////////////////////////////////////////////////////////////////////////////////////
        //
        //
        //マージソート
        mergeSort(suffixArray);
        //
        //
    //////////////////////////////////////////////////////////////////////////////////////
    }
   
    private void mergeSort(int[] a){
        if(a.length > 1){
            int m = a.length / 2;
            int n = a.length - m;
            int[] a1 = new int[m];
            int[] a2 = new int[n];
            for(int i=0; i < m;i++) a1[i] = a[i];
            for(int i=0; i < n;i++) a2[i] = a[m + i];
            mergeSort(a1);
            mergeSort(a2);
            merge(a1, a2, a);
        }
    }
    private void merge(int[] a1, int[] a2, int[] a){
        int i=0, j=0;
        while(i <a1.length || j < a2.length){
            if(j >= a2.length || (i < a1.length && suffixCompare(a1[i],a2[j]) == -1)){
                a[i+j] = a1[i];
                i++;
            }else{
                a[i+j] = a2[j];
                j++;
            }
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
	// "o"       =     "o
    // "o"       <     "oo"
	// "Ho"      >     "Hi"
	// "Ho"      <     "Hz"
	// "Ho"      =     "Ho"
    // "Ho"      <     "Ho "   : "Ho " is not in the head of suffix "Ho"
	// "Ho"      =     "H"     : "H" is in the head of suffix "Ho"
	//
	// ****  Please write code here... ***
    //////////////////////////////////////////////////////////////////////////////////////
        //
        //
        for(int k=0;k<end-j;k++){
            //Targetの方がSpaceよりも長いときは必ず-1を返す
            if(mySpace.length <= suffixArray[i]+k){
                return -1;
            }
            if(mySpace[suffixArray[i]+k]>myTarget[j+k]){
                return 1;
            }else if(mySpace[suffixArray[i]+k]<myTarget[j+k]){
                return -1;
            }else{
                //Targetを見つけられた場合それ以降の文字列に関わらず0を返す
                if(k+1 == end-j){
                    return 0;
                }
            }
        }
        return 1; // This line should be modified.
        //
        //
    //////////////////////////////////////////////////////////////////////////////////////
    }

  private int subByteStartIndex(int start, int end) {
  	// It returns the index of the first suffix which is equal or greater than subBytes;
  	// not implemented yet;
  	// For "Ho", it will return 5  for "Hi Ho Hi Ho".
  	// For "Ho ", it will return 6 for "Hi Ho Hi Ho".
  	//
    ////////////////////////////////////////////////////////////////////////////////////
      //
      //
      //二分探索
      int L = 0; //left
      int R = suffixArray.length - 1; // right
      int prev_M = suffixArray.length; //直前のMiddleを保存
      while(R - L >= 0){
          int M = (R - L) / 2 + L; //M...middle
          int result = targetCompare(M,start,end);
          if(result == 0){
              R = M - 1;
              prev_M = M;
          }else if(result == 1){
              R = M - 1;
          }else if(result == -1){
              L = M + 1;
          }
      }
      return prev_M;
      //
      //
    /////////////////////////////////////////////////////////////////////////////////////
  }

  private int subByteEndIndex(int start, int end) {
	// It returns the next index of the first suffix which is greater than subBytes;
	// not implemented yet
	// For "Ho", it will return 7  for "Hi Ho Hi Ho".
	// For "Ho ", it will return 7 for "Hi Ho Hi Ho".
	//
  ////////////////////////////////////////////////////////////////////////////////////////
      //
      //
      //二分探索
      int L = 0; //left
      int R = suffixArray.length - 1; // right
      int prev_M = suffixArray.length;
      while(R - L >= 0){
          int M = (R - L) / 2 + L; //M...middle
          int result = targetCompare(M,start,end);
          if(result == 0){
              L = M + 1;
              prev_M = M + 1;
          }else if(result == 1){
              R = M - 1;
          }else if(result == -1){
              L = M + 1;
          }
      }
      return prev_M;
      //
      //
  ////////////////////////////////////////////////////////////////////////////////////////
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
  	}*/
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
    	    frequencerObject.setSpace("Hi Ho Hi Ho".getBytes()); //入力
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
          ///////////////////////////////////////////////////////////////////////////////
            //
            //
          // ****  Please write code to check subByteStartIndex, and subByteEndIndex
          int end = frequencerObject.myTarget.length;

          //デバッグ用
//           System.out.println("0:"+frequencerObject.targetCompare(0,0,end));
//           System.out.println("1:"+frequencerObject.targetCompare(1,0,end));
//           System.out.println("2:"+frequencerObject.targetCompare(2,0,end));
//           System.out.println("3:"+frequencerObject.targetCompare(3,0,end));
//           System.out.println("4:"+frequencerObject.targetCompare(4,0,end));
//           System.out.println("5:"+frequencerObject.targetCompare(5,0,end));
//           System.out.println("6:"+frequencerObject.targetCompare(6,0,end));
//           System.out.println("7:"+frequencerObject.targetCompare(7,0,end));
//           System.out.println("8:"+frequencerObject.targetCompare(8,0,end));
//           System.out.println("9:"+frequencerObject.targetCompare(9,0,end));
//           System.out.println("10:"+frequencerObject.targetCompare(10,0,end));
            //
            //
    	  ///////////////////////////////////////////////////////////////////////////////
    	    int result = frequencerObject.frequency();
    	    System.out.print("Freq = "+ result+" ");
    	    //if(2 == result) { System.out.println("OK"); } else {System.out.println("WRONG"); }
            
    	}
    	catch(Exception e) {
    	    System.out.println("STOP");
    	}
    }
}
