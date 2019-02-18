package s4.B183372; // Please modify to s4.Bnnnnnn, where nnnnnn is your student ID.
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


//public class Frequencer{
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

	  if (mySpace[i] < mySpace[j]) {
		    return -1;
	  } else if (mySpace[i] > mySpace[j]) {
		    return 1;
	  } else {
		    if (i+1 == mySpace.length) {
			      if (j+1 == mySpace.length) {
				        return 0;
			      } else {
				        return -1;
			      }
		    } else if (j + 1 == mySpace.length) {
			      return 1;
		    }
		    return suffixCompare(i + 1, j + 1);
    }
    }

    public void setSpace(byte []space) {
	     mySpace = space; if(mySpace.length>0) spaceReady = true;
	     suffixArray = new int[space.length];
	// put all suffixes  in suffixArray. Each suffix is expressed by one integer.
	     for(int i = 0; i< space.length; i++) {
		       suffixArray[i] = i;
	     }
	// Sorting is not implmented yet.
	//
	//
	// ****  Please write code here... ***
  /*
		for (int i = 0; i < mySpace.length - 1; i++) {
			for (int j = mySpace.length-1; j > i; j--) {
				int comp = suffixCompare(suffixArray[i], suffixArray[j]);
				if (comp == 1) {
					int temp = suffixArray[j];
					suffixArray[j] = suffixArray[i];
					suffixArray[i] = temp;
				}
		}
	}

*/
        merge_sort(suffixArray);
    }

    private void quick_sort(int[] d, int left, int right) {
        if (left>=right) {
            return;
        }
        int p = d[(left+right)/2];
        int l = left, r = right, tmp;
        while(l<=r) {
            while(suffixCompare(d[l],p) == -1) { l++; }
            while(suffixCompare(d[r],p) == 1) { r--; }
            if (l<=r) {
                tmp = d[l];
                d[l] = d[r];
                d[r] = tmp;
                l++; r--;
            }
        }
        quick_sort(d, left, r);  // ピボットより左側をクイックソート
        quick_sort(d, l, right); // ピボットより右側をクイックソート
    }
    
    /*
     * マージ
     * 2つの配列a1[]とa2[]を併合してa[]を作ります。
     */
    private void merge(int[] a1,int[] a2,int[] a){
        int i=0,j=0;
        while(i<a1.length || j<a2.length){
            if(j>=a2.length || (i<a1.length && (suffixCompare(a1[i],a2[j]) == -1))){
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
    private void merge_sort(int[] a){
        if(a.length>1){
            int m=a.length/2;
            int n=a.length-m;
            int[] a1=new int[m];
            int[] a2=new int[n];
            for(int i=0;i<m;i++) a1[i]=a[i];
            for(int i=0;i<n;i++) a2[i]=a[m+i];
            merge_sort(a1);
            merge_sort(a2);
            merge(a1,a2,a);
        }
    }

    private int targetCompare(int i, int j, int end) {
	// comparing suffix_i and target_j_end by dictonary order with limitation of length;
	// if the beginning of suffix_i matches target_i_end, and suffix is longer than target  it returns 0;
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


	if (mySpace[i] > myTarget[j]) {
		return 1;
	} else if (mySpace[i] < myTarget[j]) {
		return -1;
	} else {
		if (i + 1 == mySpace.length) {
			if (j == end - 1) {
				return 0;
			} else if (j < end - 1) {
				return -1;
			}
		} else if (j == end - 1){
			return 0;
        }

        return targetCompare(i + 1, j + 1, end);
    }

    /*



         for(int k=j; k<end; k++){
           if(myTarget[k]!=mySpace[i+k-j]){
                if(myTarget[k]<mySpace[i+k-j]) return 1;
                else return -1;
           }
         }
        return 0;
      */

}




    private int subByteStartIndex(int start, int end) {
	// It returns the index of the first suffix which is equal or greater than subBytes;
	// not implemented yet;
	// For "Ho", it will return 5  for "Hi Ho Hi Ho".
	// For "Ho ", it will return 6 for "Hi Ho Hi Ho".
	//
	// ****  Please write code here... ***

  /*
	for(int i = 0;i < mySpace.length - 1 ;i++){
		if(targetCompare(suffixArray[i],start,end) == 0){
			return i;
			}
		}

    return suffixArray.length-1;
*/

    return binary_search(start,end,0,suffixArray.length-1,0);
    }

    private int subByteEndIndex(int start, int end) {
      // It returns the next index of the first suffix which is greater than subBytes;
      // not implemented yet
      // For "Ho", it will return 7  for "Hi Ho Hi Ho".
      // For "Ho ", it will return 7 for "Hi Ho Hi Ho".
      //
      // ****  Please write code here... ***

      /*
      for(int i = subByteStartIndex(start,end);i < mySpace.length ;i++){

        if(targetCompare(suffixArray[i],start,end) != 0){
          return i;
        }
      }


      return suffixArray.length;
      
*/
      return binary_search(start,end,subByteStartIndex(start,end),suffixArray.length-1,1);
    }


    private int binary_search(int start_sub,int end_sub,int start_suff,int end_suff,int target){
      //subByteの始点終点
      //suffixArrayの始点終点
      //探す数値（この数値が始まるところを返す）

      int mid = (end_suff + start_suff) / 2; //中点
      int comp = targetCompare(suffixArray[mid],start_sub,end_sub);



      //再帰が先頭まで行ったとき
      if(mid==0){
        if(comp == target){
          return mid;
        }else if(targetCompare(suffixArray[mid+1],start_sub,end_sub) == target){
          return mid +1;
        }else{
          return suffixArray.length-1;
        }
      }

      //ひとつ前の結果
      int comp_r = targetCompare(suffixArray[mid-1],start_sub,end_sub);

      //再帰が最後まで行ったとき
      if(mid == suffixArray.length-1){
       if(comp == target){
         return mid;
       }else if(comp_r == target){
         return mid-1;
       }else if(mid >= target){
           return suffixArray.length;
       }else{
        return suffixArray.length-1;
      }
    }
    //System.out.println(start_suff+" "+end_suff+" "+mid+" "+comp+" "+comp_r);

    if(comp > target && comp_r < target){
      return suffixArray.length-1;
    }

      if(comp == target && comp_r != target){ //見ているところがtargetで、見ている1つ前がtargetじゃない時
          return mid;
      }else if(comp < target){ //compがターゲットより小さい場合
        return binary_search(start_sub,end_sub,mid+1,end_suff,target); //midより後ろを探索
      }else{ //compがターゲットより大きい場合
        return binary_search(start_sub,end_sub,start_suff,mid-1,target); //midより前を探索
      }
    }


    public int subByteFrequency(int start, int end) {
	/* This method be work as follows, but */
	int spaceLength = mySpace.length;
	int count = 0;
	for(int offset = 0; offset< spaceLength - (end - start); offset++) {
	    boolean abort = false;
	    for(int i = 0; i< (end - start); i++) {
		if(myTarget[start+i] != mySpace[offset+i]) { abort = true; break; }
	    }
	    if(abort == false) { count++; }
	}

    if(mySpace.length<(end-start))
        return 0;

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

	public int getTargetlength(){
		return myTarget.length;
	}

    public static void main(String[] args) {
	Frequencer frequencerObject;
	try {
	    frequencerObject = new Frequencer();
	    	frequencerObject.setSpace("AAA".getBytes());
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
	    frequencerObject.setTarget("AA".getBytes());
	    //
	    // ****  Please write code to check subByteStartIndex, and subByteEndIndex
		//

		int Start = frequencerObject.subByteStartIndex(0, frequencerObject.getTargetlength());
        int End = frequencerObject.subByteEndIndex(0, frequencerObject.getTargetlength());
    System.out.print("Start = " + Start + " ");
    if(3 == Start) { System.out.println("OK"); } else {System.out.println("WRONG"); }
		System.out.print("End = " + End + " ");
    if(7 == End) { System.out.println("OK"); } else {System.out.println("WRONG"); }



	    int result = frequencerObject.frequency();
	    System.out.print("Freq = "+ result+" ");
	    if(4 == result) { System.out.println("OK"); } else {System.out.println("WRONG"); }
	}
	catch(Exception e) {
	    System.out.println("STOP");
	}
    }
}
