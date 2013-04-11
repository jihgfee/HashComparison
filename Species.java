import java.util.ArrayList;

public class Species{

	private String name;
	private String code;
	private int[] profile;
	
	public Species(String name, String code)
	{
		this.name = name;
		this.code = code;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getCode()
	{
		return code;
	}
	
	public void setProfile(int[] profile)
	{
		this.profile = profile;
	}
	
	public int[] getProfile()
	{
		return profile;
	}
	
	public String[] getKGrams(int k)
	{
		int indexPosition = 0;
	
		ArrayList<String> aList = new ArrayList<String>();
	
		while(indexPosition+k <= getCode().length())							//As long as we can fit a string of k size into our array from start indexPosition..
		{
			aList.add(getCode().substring(indexPosition, indexPosition+k));		//.., we add the substring from indexPosition to indexPosition+k..
			
			indexPosition++;													//.. and increment the indexPosition
		}
		
		String[] array = new String[aList.size()];
		
		array = aList.toArray(array);											//We convert the arrayList to an array
		
		return array;
	}
}