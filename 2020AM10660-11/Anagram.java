// Program to check for command line arguments
import java.util.*;
import java.io.File;

class StringList{
	public ArrayList<String> list = new ArrayList<String>();
}

class HashTable{
	StringList[] hmap = new StringList[100003];
	HashTable()
	{
		for(int i =0; i < 100003;i++)
		{
			hmap[i] = new StringList();
		}
	}

	private long pow(int x,int n)
	{
		long y = 1;
		for(int j = 0; j < n; j++)
		{
			y *= x;
			y %= 100003;
		}
		return y;
	}
	private String sorting(String value)
	{
		int[] ascii = new int[256];
		for(int i = 0; i<256 ; ++i){
			ascii[i] = 0;
		}
		int n = value.length();
		for(int i = 0; i<n; ++i){
			int val = value.charAt(i);
			ascii[val]++;
		}
		String sortedString = "";
		for (int i  = 0; i<256; ++i){
			int occ = ascii[i];
			for(int j = 0; j<occ; ++j){
				sortedString += (char) i ;
			}
		}
		return sortedString;
	}
	
	private long hashCode (String value)
	{
		long val = 0;
		for(int i = 0; i < value.length();i++)
		{
			int a = value.charAt(i);
			val += a * pow(256,i);
		}
		return val % 100003;
	}
	// This function will create hashMap using quadratic chaining
	public void hashFunction(List<String> vocabulary)
	{
		for(int i = 0; i < vocabulary.size(); i++)
		{
			String word = sorting(vocabulary.get(i));
			long index =  hashCode(word);
			int j = 0;
			while(true)
			{
				long value = (index + (pow(j,2))) % 100003;
				if (hmap[(int) value].list.size() == 0)
				{
					hmap[(int) value].list.add(vocabulary.get(i));
					break;
				}
				else
				{
					if((sorting(hmap[(int) value].list.get(0))).equals(word))
					{
						hmap[(int) value].list.add(vocabulary.get(i));
						break;
					}
					else
					{
						j++;
					}
				}
			}
		}
	}

	// This will give us the value list corresponding to the key of hashtable
	public ArrayList<String> getList(String sorted)
	{
		long index = hashCode(sorted);
		int j = 0;
		while(true)
		{
			long value = (index + (pow(j,2))) % 100003;
			ArrayList<String> wordList = hmap[(int) value].list;
			if(wordList.size() == 0)
			{
				return wordList;
			}
			else
			{
				if(sorting(wordList.get(0)).equals(sorted))
				{
					return wordList;
				}
				else
				{
					j++;
				}
			}
		}
	}

}
public class Anagram {

	

	// This function will sort the string in alphabetical order
	private String sortString(String value)
	{
		int[] ascii = new int[256];
		for(int i = 0; i<256 ; ++i){
			ascii[i] = 0;
		}
		int n = value.length();
		for(int i = 0; i<n; ++i){
			int val = value.charAt(i);
			ascii[val]++;
		}
		String sortedString = "";
		for (int i  = 0; i<256; ++i){
			int occ = ascii[i];
			for(int j = 0; j<occ; ++j){
				sortedString += (char) i ;
			}
		}
		return sortedString;                                                                                                    
	}

	private void  twoSubSets(ArrayList<Integer> indices, ArrayList<Integer> output, int index,List<List<Integer>> subset)
    {
      // This means that we have reached the end of array for possible choices
	  int len = indices.size();
        if (index == len) 
		{
            subset.add(output);
        }

		else
		{
			ArrayList<Integer> case1 = new ArrayList<Integer>(output);
			// Not Including Value which is at Index
			twoSubSets(indices, case1, index + 1, subset);
			// Including Value which is at Index
			int value = indices.get(index);
			output.add(value);
			ArrayList<Integer> case2 = new ArrayList<Integer>(output);
			twoSubSets(indices, case2, index + 1, subset);
		}
    }

	
	// This function will identify the anagrams of 1 word
	private List<String> oneWord(String input, HashTable hash)
	{
		String sort = sortString(input);
		return hash.getList(sort);
	}

	private String stringValue(List<Integer> listValue)
	{
		String result = "";
		for(int i =0; i < listValue.size(); i++ )
		{
			int val = listValue.get(i);
			 result += (char) val;
		}
		result = sortString(result);
		return result;
	}

	private String counterPart(String total, String subset)
	{
		int traverseMain = 0;
		int traverseSub = 0;
		String result = "";
		while( traverseMain < total.length())
		{
			char first = total.charAt(traverseMain);
			if( traverseSub < subset.length())
			{
				char second = subset.charAt(traverseSub);
				if (first != second )
				{
					result += first;
					traverseMain++;
				}
				else
				{
					traverseMain++;
					traverseSub++;
				}
			}
			else
			{
				result += first;
				traverseMain++;
			}
		}
		return result;
	}

	// This function will return the list of anagrams after combining 2 lists of  values of hashtable
	private List<String> combine(List<String> list1,List<String> list2)
	{
		List<String> result = new ArrayList<String>();
		for(int i = 0; i < list1.size(); i++)
		{
			for(int j = 0; j < list2.size(); j++)
			{
				String val  = list1.get(i) + " " + list2.get(j);
				result.add(val);
			}
		}
		return result;
	}
	private List<String> twoWord(String input,HashTable hash)
	{
		String sort = sortString(input);
		ArrayList<Integer> indices = new ArrayList<Integer>();
		for(int i =0; i < sort.length(); i++)
		{
			int val = sort.charAt(i);
			indices.add(val);
		}
		ArrayList<Integer> output = new ArrayList<Integer>();
		int index = 0;
		List<List<Integer>> subset = new  ArrayList<List<Integer>>();
		twoSubSets(indices, output, index, subset);
		List<String> result = new ArrayList<>();
		for(int i =0; i < subset.size(); i++)
		{
			List<Integer> sub = subset.get(i);
			String substr = stringValue(sub);
			String substr2 = counterPart(sort, substr);
			List<String> list1 = hash.getList(substr);
			List<String> list2 = hash.getList(substr2);
			if (list1.size() > 0 && list2.size() > 0)
			{
				List<String> combinedAnagram = combine(list1, list2);
				result.addAll(combinedAnagram);
			}
		}
		return result;

	}

	// This function will give the anagrams containing 3 words
	private List<String> threeWord(String input, HashTable hash)
	{
		String sort = sortString(input);
		ArrayList<Integer> indices = new ArrayList<Integer>();
		for(int i =0; i < sort.length(); i++)
		{
			int val = sort.charAt(i);
			indices.add(val);
		}
		ArrayList<Integer> output = new ArrayList<Integer>();
		int index = 0;
		List<List<Integer>> subset = new  ArrayList<List<Integer>>();
		twoSubSets(indices, output, index, subset);
		List<String> result = new ArrayList<String>();
		for(int i =0; i < subset.size(); i++)
		{
			List<Integer> sub = subset.get(i);
			String substr = stringValue(sub);
			String substr2 = counterPart(sort, substr);
			List<String> list1 = hash.getList(substr);
			List<String> list2 = twoWord(substr2, hash);
			if (list1.size() > 0 && list2.size() > 0)
			{
				List<String> combinedAnagram = combine(list1, list2);
				result.addAll(combinedAnagram);
			}
		}
		return result;
	}

	// This function will compare the strings which will be used further in sorting
	private int compareString(String string1, String string2)
	{
		// if string1 is less then string2 we return -1
		int traverse1 = 0;
		int traverse2 = 0;
		while( traverse1 < string1.length() && traverse2 < string2.length())
		{
			char c1 = string1.charAt(traverse1);
			char c2 = string2.charAt(traverse2);
			if( c1 < c2)
			{
				return -1;
			}
			else if( c1 > c2)
			{
				return 1;
			}
			else 
			{
				traverse1++;
				traverse2++;
			}
		}

		if(traverse1 < string1.length() && traverse2 >= string2.length())
		{
			return 1;
		}
		else if(traverse1 >= string1.length() && traverse2 < string2.length())
		{
			return -1;
		}
		else 
		{
			return 0;
		}
	}

	// This function will sort the anagrams in lexographical order and remove the duplicates
	private List<String> sortAnagrams(List<String> anagrams)
	{
		for(int i =0; i < anagrams.size(); i++)
		{
			int minimum = i;
			for(int j = i; j < anagrams.size(); j++)
			{
				if( compareString(anagrams.get(minimum), anagrams.get(j)) == 1)
				{
					minimum = j;
				}
			}
			String val = anagrams.get(i);
			anagrams.set(i,anagrams.get(minimum));
			anagrams.set(minimum,val);
		}
		// Sorting done now removing duplicates
		List<String> result = new ArrayList<String>();
		for(int i = 0; i < anagrams.size(); i++)
		{
			if( result.size() == 0)
			{
				result.add(anagrams.get(i));
			}
			else
			{
				if( compareString(result.get(result.size() - 1), anagrams.get(i)) != 0)
				{
					result.add(anagrams.get(i));
				}
			}
		}

		return  result;
	}

	private void print(String value, HashTable hash)
	{
		List<String> one = oneWord(value, hash);
		List<String> two = twoWord(value, hash);
		List<String> three = threeWord(value, hash);
		List<String> result = new ArrayList<>();
		result.addAll(one);
		result.addAll(two);
		result.addAll(three);
		result = sortAnagrams(result);
		for(int i = 0; i < result.size(); i++)
		{
			System.out.println(result.get(i));
		}
		System.out.println(-1);
	}
	public static void main(String[] args) throws Exception
	{
		// check if length of args array is
		// greater than 0
		long start = System.currentTimeMillis();
		if (args.length > 0) {
			String vocab = args[0];
			String input = args[1];
			// took vocabulary file name using terminal
			List<String> vocabulary = new ArrayList<String>();
			File vocabFile =  new File(vocab);
			Scanner readVocab = new Scanner(vocabFile);
			String vocabSize = readVocab.nextLine();

			// vocabulary list will contain the list of all the valid words in vocabulary
			while( readVocab.hasNextLine())
			{
				vocabulary.add(readVocab.nextLine());
			}
			
			File inputFile = new File(input);
			Scanner readInput = new Scanner(inputFile);
			String inputSize = readInput.nextLine();
			List<String> inputValue = new ArrayList<String>();
			while( readInput.hasNextLine())
			{
				inputValue.add(readInput.nextLine());
			}
			// Now we will create hashmap where key is the sorted string from vocabulary and value is a list of words
			HashTable hash = new HashTable();
			hash.hashFunction(vocabulary);

			Anagram obj = new Anagram();

			for(int i =0 ; i < inputValue.size();  i++)
			{
				obj.print(inputValue.get(i),hash);
			}

			long end = System.currentTimeMillis();
			// System.out.println(end - start); 

			// System.out.println(obj.threeWord("bnoclearcrokr", hash).size());


		}
		else
			System.out.println("No command line "
							+ "arguments found.");

	}
}

