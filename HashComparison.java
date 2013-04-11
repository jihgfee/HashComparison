import java.lang.Math;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.*;

public class HashComparison{
	
	private Species[] species;
	private int k;
	private int d;
	
	public HashComparison(Species[] species, int k, int d)
	{
		StdOut.println(testGeometry());
	
		this.species = species;
		this.k = k;
		this.d = d;
		
		for(int i=0; i<species.length; i++)													//We iterate through every single species
		{
			String[] kGrams = species[i].getKGrams(k);										//We get all possible kGrams for the current species
			
			HashMap<Integer, Integer> kGramCount = new HashMap<Integer, Integer>();			//We initiate a HashMap to hold the amount of instances of each kGram
			
			//We iterate through the kGrams
			for(int j=0; j<kGrams.length; j++)
			{
				int currentHash = h(kGrams[j]);												//We get the current HashValue
			
				if(currentHash >= 0 && currentHash < d)										//We check whether or not the HashValue is inside our qualified partition
					if(kGramCount.get(currentHash) == null)									//If the current key is null we initiate it
						kGramCount.put(currentHash, 1);
					else																	//else we add 1 more to the value
						kGramCount.put(currentHash, (kGramCount.get(currentHash)+1));
			}
			
			int[] p = new int[d];															//We initiate the profile array
			
			Iterator it = kGramCount.keySet().iterator();									//We iterate through our HashMap
			while(it.hasNext())
			{
				int key = (Integer)it.next();
			
				p[key] = kGramCount.get(key);												//Everytime there is a hashvalue that would fit in p, we add the amount of instances
				
				it.remove();
			}
			
			species[i].setProfile(p);														//We apply the profile to the species
		}
		
		
		for(int i=0; i<species.length; i++)													//We iterate through all the species to compare them
		{
			for(int j=(i+1); j<species.length; j++)
			{
				StdOut.println("Comparing " + species[i].getName() + " and " + species[j].getName() + ": " + compare(species[i],species[j]));
			}
		}
	}
	
	private int h(String T)
	{	
		return (T.hashCode() & 0x7fffffff) % d;												//We compute the hashcode. We use 0x7fffffff to avoid errors
	}
	
	private double getCompareValue(int[] profile1, int[] profile2)
	{
		double dotProduct = 0;
		double length1 = 0;
		double length2 = 0;
	
		for(int i=0; i<profile1.length; i++)												//we iterate through both profiles
		{
			dotProduct += (profile1[i]*profile2[i]);										//We compute the dot product
			
			length1 += Math.pow(profile1[i],2);												//We compute the length of profile 1
			length2 += Math.pow(profile2[i],2);												//We compute the length of profile 2
		}
		
		length1 = Math.sqrt(length1);														//We finalize the computation of the lengths
		length2 = Math.sqrt(length2);
		
		return dotProduct/(length1*length2);												//We compute the final value and return it
	}
	
	private double compare(Species species1, Species species2)
	{
		return getCompareValue(species1.getProfile(), species2.getProfile());				//Linking method so we can easily parse a species instead of a profile
	}

	public static void main(String[] args)
	{
		String[] strings = new In(args[0]).readAll().split(">");							//We accumulate a string array containing each seperate line in the file.
		
		int k = 20;
		int d = 10000;
		
		Stopwatch timer = new Stopwatch();													//We begin our stopwatch
		
		Species[] species = new Species[strings.length-1];									//We initate an array for our species
		
		for(int i=1; i<strings.length; i++)
		{
			String[] split = strings[i].split("\n");
			
			species[i-1] = new Species(split[0].split(" ")[0], split[1]+split[2]+split[3]);	//we initiate the species, [Note: that the first string in the strings array is empty].
		}
		
		new HashComparison(species, k, d);													//We initialize our class in order to save our values
		
		StdOut.println("elapsed time = " + timer.elapsedTime());							//We check how long our computation took
	}
	
	
	//Test Methods
	private boolean testGeometry()
	{
		if(getCompareValue(new int[]{2,0}, new int[]{0,3}) != 0)
			return false;
		
		if(getCompareValue(new int[]{2,2}, new int[]{2,-2}) != 0)
			return false;
		
		if(getCompareValue(new int[]{1,0}, new int[]{6,0}) != 1)
			return false;
		
		if(lengthMethod(new int[]{0,1}) != 1)
			return false;
		
		if(lengthMethod(new int[]{3,4}) != 5)
			return false;
		
		return true;
	}
	
	private double lengthMethod(int[] profile)
	{
		double length = 0;
	
		for(int i=0; i<profile.length; i++)												//we iterate through our profiles
		{
			length += Math.pow(profile[i],2);											//We compute the length
		}
		
		length = Math.sqrt(length);	
		
		return length;																	//We compute the final value and return it
	}
}