package s4.B183301; // Please modify to s4.Bnnnnnn, where nnnnnn is your student ID.

import s4.specification.*;
import java.util.Random;

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

/**
 * Simple Frequency Counter Class
 */
public class Frequencer implements FrequencerInterface {
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
                final int s = suffixArray[i];
                if(s > -1){
                for(int j=s;j<mySpace.length;j++) {
                        System.out.write(mySpace[j]);
                }}
                else
                    System.out.write(-1);
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
        if(i == j)return 0;
        final var order =  i < j;//比較しやすいようにiとjを並び替える
        if(!order){
            final var temp = j ;
            j = i;
            i = temp;
        }
        while (j < mySpace.length){
            var x = mySpace[i];
            var y = mySpace[j];
            if(x == y){
                i++;
                j++;
                continue;}
            return order ^ (x < y) ? -1 : 1;
        }
        return order ? -1 : 1;
    }

    public void setSpace(final byte []space) {
        mySpace = space; if(mySpace.length>0) spaceReady = true;
        int end = space.length;
        suffixArray = new int[end];
        // put all suffixes  in suffixArray. Each suffix is expressed by one integer.
        //for(int i = 0; i< end; i++) { //no need for putting suffixes for induced_sort
            //suffixArray[i] = i;
            //suffixArray[i] = -1;
        //}
        final var start = 0;
        //mpSort(suffixArray, start, end);
        //insertionSort(suffixArray, start, end);
        //binaryInsertionSort(suffixArray,start,end);
        if(end != 0)
            twoStageSort(suffixArray,start,end);
    }

    /*
    The MIT License (MIT)

    Copyright (c) 2015 masakazu matsubara

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
    */
    private void mpSort(final int[] suffixArray ,final int from, final int to, final int[] pivots, final int fromPivots, final int toPivots)
    {
        final int pivotIdx = fromPivots + (toPivots - fromPivots) / 2;      //  using index from pivots (center position) / pivots配列の中で、今回使うべき要素の添え字
        final int pivot = pivots[pivotIdx];                                   //  pivot value / ピボット値

        int curFrom = from;     //  min index / 現在処理中位置の小さい方の位置
        int curTo = to - 1;     //  max index / 現在処理中位置の大きい方の位置

        while (true) {
            //while (comparator.compare(array[curFrom], pivot) < 0)
            while (suffixCompare(suffixArray[curFrom],pivot) > 0){
                curFrom++;}
            //while (comparator.compare(pivot, array[curTo]) < 0)
            while (suffixCompare(pivot, suffixArray[curTo]) > 0){
                curTo--;}
            if (curTo < curFrom)
                break;
            final int work = suffixArray[curFrom];
            suffixArray[curFrom] = suffixArray[curTo];
            suffixArray[curTo] = work;
            curFrom++;
            curTo--;
        }

        if (from < curTo) {
            if (fromPivots >= pivotIdx - 3) //  pivotsの残りが３つを切ったらpivotsを作り直す。（最後まで使い切らないのは、最後の１個は範囲内の中間値に近いとは言えないので）
                mpSort(suffixArray, from, curTo + 1);
            else
                mpSort(suffixArray, from, curTo + 1, pivots, fromPivots, pivotIdx);
        }

        if (curFrom < to - 1) {
            if (pivotIdx + 1 >= toPivots - 3)   //  pivotsの残りが３つを切ったらpivotsを作り直す。（最後まで使い切らないのは、最後の１個は範囲内の中間値に近いとは言えないので）
                mpSort(suffixArray, curFrom, to);
            else
                mpSort(suffixArray, curFrom, to, pivots, pivotIdx + 1, toPivots);
        }
    }

    //static int SWITCH_SIZE = 64;
    static final int SWITCH_SIZE = 1024;
    static final int PIVOTS_SIZE = 128;

    private void mpSort(final int[] suffixArray, final int from, final int to) {
        final int range = to - from;        //  sort range / ソート範囲サイズ

        //  ソート対象配列サイズがSWITCH_SIZE以下のときは
        //  insertion に変更
        if (range < SWITCH_SIZE) {
            //insertionSort(suffixArray,from,to);
            binaryInsertionSort(suffixArray,from,to);
            return;
        }

        final int pivotsSize = PIVOTS_SIZE > (range >> 3) ? (range >> 3) + 3: PIVOTS_SIZE;
        final int[] pivots = new int[pivotsSize];     //  pivot candidates / ピボット候補の配列

        // Selection of the pivot values
        // ピボット（複数）の選出
        final int i1 = range / 2 / pivots.length;
        for (int i = 0; i < pivots.length; i++) {
            pivots[i] = suffixArray[(int)(from + (long)range * i / pivots.length + i1)];
        }
        // sort of pivot candidates / ピボット値のみをソート
        //insertionSort(pivots,0,pivots.length);
        binaryInsertionSort(pivots,0,pivots.length);
        // sort of array / ソート対象本体のソート
        mpSort(suffixArray, from, to, pivots, 0, pivots.length);
    }

    private void insertionSort(final int[] array,final int start,final int end) {
        for (int i = start + 1; i < end; i++) {
            final var temp = array[i];
            if(suffixCompare(array[i-1],temp) < 0){
                var j = i;
                do{
                    array[j] = array[j-1];
                    j--;
                }while (j > 0 && suffixCompare(array[j-1],temp) < 0);
                array[j] = temp;
            }
        }
    }

    private void binaryInsertionSort(final int[] array,final int start,final int end){
        for (int i = start + 1; i < end; i++) {
            final var temp = array[i];
            if(suffixCompare(array[i-1],temp) < 0){
                var min = start - 1;
                var max= i;
                while (max - min > 1){
                    var mid = min + ((max - min) >> 1);
                    if(suffixCompare(array[mid],temp) > 0){
                        min = mid;
                    }else{
                        max = mid;
                    }
                }
                System.arraycopy(array, max, array,max + 1,i - max);
                array[max] = temp;
            }
        }
    }

    private void sA_IS(final int[] array,final int start,final int end){
        var prevsl = false;
        //create lms buckets
        final var lms = new int[256][];
        for (int i = 0; i < 256;i++){
            lms[i] = new int[1];
        }
        //s-type is true
        final var ls = new boolean[array.length];
        final var lmscounts = new int[256];
        final var bytecounter = new int[256];
        //figure out the value is either l-type or s-type or left-most-s-(lms-)type
        //it doesn't set the sentinel so we check the last value here
        var prevvalue = ((int)mySpace[end - 1])+128;//we add +128 so as to treat as unsigned byte because java's byte is signed
        bytecounter[prevvalue]++;
        for (int i = end - 2; i >= start;i--){
            final var value = ((int)mySpace[i]) + 128;
            bytecounter[value]++;
            if(value < prevvalue){//s-type
                ls[i] = true;
                prevsl = true;
            }else if(prevsl) {
                if(mySpace[i] == mySpace[i+1]) {
                    ls[i] = true;
                } else {
                    //set the  prev value as lms-type
                    final int lmscount = lmscounts[prevvalue]++;
                    if(lmscount == lms[prevvalue].length){
                        final var newarray = new int[lmscount << 1];
                        System.arraycopy(lms[prevvalue],0,newarray,0,lmscount);
                        lms[prevvalue] = newarray;
                    }
                    lms[prevvalue][lmscount] = i + 1;
                    prevsl = false;
                }
            }
            prevvalue = value;
        }
        final var bbucket = new int[256];
        final var abucket = new int[256];
        var sum = 0;
        //create the bucket for induced_sort
        for (int i = 0;i < 256; i ++){
            bbucket[i] = sum;
            sum += bytecounter[i];
            abucket[i] = sum;
            final int lmscount = lmscounts[i];
            if(lmscount == 0) continue;
            var sumx = sum;
            final int[] lmsi = lms[i];
            //mpSort(lmsi,0, lmscount);//sort lms_value
            for (int j = lmscount - 1; j >= 0; j--){
                array[--sumx] = lmsi[j];
            }
        }

        //induced sort
        {
            final var value = ((int)mySpace[end-1] + 128);
            array[(bbucket[value]++)] = end - 1;
        }
        for (int i = 0; i < end ; i++){
            final int i2 = array[i] - 1;
            if(i2 < 0) continue;
            if (ls[i2]) continue;
            final var value = ((int)mySpace[i2]) + 128;
            array[(bbucket[value]++)] = i2;
        }
        for (int i = end - 1;i >= 0;i--){
            final int i2 = array[i] - 1;
            if(i2 < 0) continue;
            if (!ls[i2]) continue;
            final var value = ((int)mySpace[i2]) + 128;
            array[(--abucket[value])] = i2;
        }



    }

    private void twoStageSort(final int[] array,final int start,final int end){
        var prevsl = false;
        //create lms buckets
        final var lms = new int[256][];
        for (int i = 0; i < 256;i++){
            lms[i] = new int[1];
        }
        //s-type is true
        final var ls = new boolean[array.length];
        final var lmscounts = new int[256];
        final var bytecounter = new int[256];
        //figure out the value is either l-type or s-type or left-most-s-(lms-)type
        //it doesn't set the sentinel so we check the last value here
        var prevvalue = ((int)mySpace[end - 1])+128;//we add +128 so as to treat as unsigned byte because java's byte is signed
        bytecounter[prevvalue]++;
        for (int i = end - 2; i >= start;i--){
            final var value = ((int)mySpace[i]) + 128;
            bytecounter[value]++;
            if(value < prevvalue){//s-type
                ls[i] = true;
                prevsl = true;
            }else if(prevsl) {
                if(mySpace[i] == mySpace[i+1]) {
                    ls[i] = true;
                } else {
                    //set the  prev value as lms-type
                    final int lmscount = lmscounts[prevvalue]++;
                    if(lmscount == lms[prevvalue].length){
                        final var newarray = new int[lmscount << 1];
                        System.arraycopy(lms[prevvalue],0,newarray,0,lmscount);
                        lms[prevvalue] = newarray;
                    }
                    lms[prevvalue][lmscount] = i + 1;
                    prevsl = false;
                }
            }
            prevvalue = value;
        }
        final var bbucket = new int[256];
        final var abucket = new int[256];
        var sum = 0;
        //create the bucket for induced_sort
        for (int i = 0;i < 256; i ++){
            bbucket[i] = sum;
            sum += bytecounter[i];
            abucket[i] = sum;
            final int lmscount = lmscounts[i];
            if(lmscount == 0) continue;
            var sumx = sum;
            final int[] lmsi = lms[i];
            mpSort(lmsi,0, lmscount);//sort lms_value
            for (int j = lmscount - 1; j >= 0; j--){
                array[--sumx] = lmsi[j];
            }
        }

        //induced sort
        {
            final var value = ((int)mySpace[end-1] + 128);
            array[(bbucket[value]++)] = end - 1;
        }
        for (int i = 0; i < end ; i++){
            final int i2 = array[i] - 1;
            if(i2 < 0) continue;
            if (ls[i2]) continue;
            final var value = ((int)mySpace[i2]) + 128;
            array[(bbucket[value]++)] = i2;
        }
        for (int i = end - 1;i >= 0;i--){
            final int i2 = array[i] - 1;
            if(i2 < 0) continue;
            if (!ls[i2]) continue;
            final var value = ((int)mySpace[i2]) + 128;
            array[(--abucket[value])] = i2;
        }
    }


    private int targetCompare(final int i, int j,final int end) {
        // comparing suffix_i and target_j_end by dictonary order with limitation of length;
        // if the beginning of suffix_i matches target_i_end, and suffix is longer than target  it returns 0;
        // if suffix_i > target_i_end it return 1;
        // if suffix_i < target_i_end it return -1
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
        var n = suffixArray[i];
        while (j < end) {
            if(mySpace.length <= n){
                return -1;
            }
            var x = mySpace[n];
            var y = myTarget[j];
            if(x == y){
                n++;
                j++;
                continue;
            }
            return x > y ? 1 : -1;
        }
        return 0;
    }

    private int subByteStartIndex(final int start,final int end,int min,int max) {
        // It returns the index of the first suffix which is equal or greater than subBytes;
        // For "Ho", it will return 5  for "Hi Ho Hi Ho".
        // For "Ho ", it will return 6 for "Hi Ho Hi Ho".
        //int min = -1;
        //int max = mySpace.length;

        while(max - min > 1) {
            int mid = (max - min) / 2 + min;
            int compare = targetCompare(mid,start,end);
            if(compare >= 0) max = mid;
            else min = mid;
        }

        return max; // This line should be modified.
    }

    private int subByteEndIndex(final int start,final int end,int min,int max) {
        // It returns the next index of the first suffix which is greater than subBytes;
        // For "Ho", it will return 7  for "Hi Ho Hi Ho".
        // For "Ho ", it will return 7 for "Hi Ho Hi Ho".
        //int min = -1;
        //int max = mySpace.length;

        while(max - min > 1) {
            int mid = (max - min) / 2 + min;
            int compare = targetCompare(mid,start,end);
            if(compare <= 0) min = mid;
            else max = mid;
        }
        return min; // This line should be modified.
    }

    public int subByteFrequency(final int start,final int end) {
        int min = 0;
        int max = mySpace.length - 1;
        int mid;
        firstMatch:
        {
            while (max >= min) {
                mid = (max - min) / 2 + min;
                var comp = targetCompare(mid, start, end);
                if (comp > 0) {
                    max = mid - 1;
                } else if (comp == 0) {
                    break firstMatch;
                } else {
                    min = mid + 1;
                }
            }
            return 0;
        }
        final int first = subByteStartIndex(start, end, min - 1, mid + 1);
        final int last = subByteEndIndex(start, end, mid - 1, max + 1);
        return last - first + 1;
    }

    public void setTarget(final byte [] target) {
        myTarget = target; if(myTarget.length>0) targetReady = true;
    }

    public int frequency() {
        if(targetReady == false) return -1;
        if(spaceReady == false) return 0;
        return subByteFrequency(0, myTarget.length);
    }

    public static void main(String[] args) {
        hi_HoTest();
        //speedCheck();
        //swtichCheck();
    }

    private static void hi_HoTest() {
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

            FrequenceChecker(frequencerObject,"H",4);
            FrequenceChecker(frequencerObject,"Hi",2);
            FrequenceChecker(frequencerObject,"o",2);
            FrequenceChecker(frequencerObject,"Hi ",2);
            FrequenceChecker(frequencerObject,"Hi Ho Hi Ho",1);
            FrequenceChecker(frequencerObject,"Hio",0);

        }
        catch(Exception e) {
            //System.out.println("STOP");
            e.printStackTrace();
        }
    }

    private static void FrequenceChecker(Frequencer frequencerObject,String str,int expected) {
        int result;
        frequencerObject.setTarget(str.getBytes());
        result = frequencerObject.frequency();
        System.out.print("Freq = "+ result+" ");
        if(expected == result) { System.out.println("OK"); } else {System.out.println("WRONG"); }
    }

    //make sure to comment out sort in setspace before running these tests.
    private static void speedCheck(){
        var ran = new Random();
        var fr = new Frequencer();
        for (int i = 0; i < 30; i++) {
            timeInner(ran, fr, i * i);
        }
        for (int i = 100; i < 100000; i *= 10){
            //timeInner(ran, fr, i);
        }
    }
    private static void swtichCheck(){
        var ran = new Random();
        var fr = new Frequencer();
        for (int j = 16; j < 10000; j <<= 1){
            System.out.println(j);
            System.out.println("------------------------------------");
            //Frequencer.SWITCH_SIZE = j;// dont forget to comment out this for release
            for (int i = 0; i < 30; i++) {
                //switchtimeInner(ran, fr, i * i);
            }
            //for (int i = 1; i < 50000; i <<= 2){
            {var i = 100000;
                switchtimeInner(ran, fr, i);
            }
            System.out.println("");
        }
    }

    private static void timeInner(Random ran, Frequencer fr, int i) {
        var bytes = new byte[i];
        System.out.println(bytes.length + " : ");
        ran.nextBytes(bytes);
        fr.setSpace(bytes);
        var start1 = System.currentTimeMillis();
        for (int j = 0;j < 1000;j++){
            fr.setSpace(bytes);
            //fr.insertionSort(fr.suffixArray,0,bytes.length);
        }
        var len1 = System.currentTimeMillis();
        System.out.println("ins | " + (len1-start1));
        var start3 = System.currentTimeMillis();
        for (int j = 0;j < 1000;j++){
            fr.setSpace(bytes);
            fr.binaryInsertionSort(fr.suffixArray,0,bytes.length);
        }
        var len3 = System.currentTimeMillis();
        System.out.println("bin | " + (len3-start3));
        var start2 = System.currentTimeMillis();
        for (int j = 0;j < 1000;j++){
            fr.setSpace(bytes);
            fr.mpSort(fr.suffixArray,0,bytes.length);
        }
        var len2 = System.currentTimeMillis();
        System.out.println("mpq | " + (len2-start2));
    }
    private static void switchtimeInner(Random ran, Frequencer fr, int i) {
        var bytes = new byte[i];
        System.out.print(bytes.length + " : ");
        ran.nextBytes(bytes);
        var start2 = System.currentTimeMillis();
        for (int j = 0;j < 100;j++){
            fr.setSpace(bytes);
            fr.mpSort(fr.suffixArray,0,bytes.length);
        }
        var len2 = System.currentTimeMillis();
        System.out.println("mpq | " + (len2-start2));
    }
}

	    
