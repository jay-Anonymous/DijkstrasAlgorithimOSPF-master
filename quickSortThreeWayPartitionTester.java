public class quickSortThreeWayPartitionTester 
{

	public static void main(String[] args) 
	{
		Integer arr [] = new Integer [] {1, 5, 2, 6, 3, 2, 3, 6, 7, 9, 34, 2, 3, 4};
		
		quickSortThreeWayPartition x = new quickSortThreeWayPartition(arr);
		
		System.out.println(x.toString());
	}
}
