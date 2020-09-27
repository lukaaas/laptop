
public class main {

	public static void main (String[]args)
	{
		System.out.println(sum(7));
	}
	
	public static int sum(int n)
	{
		int result = 0;
		for(int i = 0; i<= n; i++)
		{
			result = result + i;
		}
		
		return result;
	}
}
//0+1 = 1 +2 = 3 +3 = 6+4 = 10+5 = 15 + 6 = 21