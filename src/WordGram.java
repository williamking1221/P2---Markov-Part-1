import java.util.*;
/**
 * A WordGram represents a sequence of strings
 * just as a String represents a sequence of characters
 * 
 * @author William Joshua King
 *
 */
public class WordGram {
	
	private String[] myWords;   
	private String myToString;  // cached string
	private int myHash;         // cached hash value

	/**
	 * Create WordGram by creating instance variable myWords and copying
	 * size strings from source starting at index start
	 * @param source is array of strings from which copying occurs
	 * @param start starting index in source for strings to be copied
	 * @param size the number of strings copied
	 */
	public WordGram(String[] source, int start, int size) {
		myWords = new String[size];
		myToString = null;
		myHash = 0;

		for (int k = 0; k < size; k ++) {
			myWords[k] = source[k+start];
		}
	}

	/**
	 * Return string at specific index in this WordGram
	 * @param index in range [0..length() ) for string 
	 * @return string at index
	 */
	public String wordAt(int index) {
		if (index < 0 || index >= myWords.length) {
			throw new IndexOutOfBoundsException("bad index in wordAt "+index);
		}
		return myWords[index];
	}

	/**
	 * Returns the length of the WordGram
	 * @return
	 */
	public int length(){
		return myWords.length;
	}


	/**
	 * Checks whether two WordGrams are equal to each other
	 * @param other
	 * @return
	 */
	@Override
	public boolean equals(Object other) {
		if (! (other instanceof WordGram) || other == null) {
			return false;
		}
		WordGram wg = (WordGram) other;
		if (! Arrays.equals(this.myWords, wg.myWords)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode(){
		if (myHash == 0) {
			myHash = this.toString().hashCode();
		}
		return myHash;
	}
	

	/**
	 * Moves the index of the words by -1, taking out the first word, and adds another specified word to the end
	 * @param last is last String of returned WordGram
	 * @return
	 */
	public WordGram shiftAdd(String last) {
		WordGram wg = new WordGram(myWords, 0, myWords.length);
		for (int k = 1; k < myWords.length; k++) {
			wg.myWords[k-1] = this.myWords[k];
		}
		wg.myWords[wg.length() - 1] = last;
		return wg;
	}

	@Override
	public String toString(){
		if (myToString == null) {
			myToString = String.join(" ", myWords);
		}
		return myToString;
	}
}
