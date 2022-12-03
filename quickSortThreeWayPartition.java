public class quickSortThreeWayPartition <AnyType extends Comparable<? super AnyType>>
{
	 AnyType [] a;
	//constructor for quicksort, calls inner quicksort method
	quickSortThreeWayPartition( AnyType [] a )
	{
		this.a = a;
		//calls the private method
		quickSortThreeWayPartition( a, 0, a.length - 1 );
	}

	//algorithm

	//sort all the way to the end and swap if index you're at

	/**
	 * Internal quicksort method that makes recursive calls.
	 * Uses median-of-three partitioning and a cutoff of 10.
	 * @param a an array of Comparable items.
	 * @param left the left-most index of the subarray.
	 * @param right the right-most index of the subarray.
	 */
	void quickSortThreeWayPartition( AnyType [] a, int left, int right )
	{
		//first find best pivot of set using median of 3
		AnyType pivot = median3(a, left, right);

		//BUILDING LESS-THAN SIDE
		//then iterate from left to right and swap if smaller
		//keep an index to know where you are
		int lessPlacement = 0;
		for(int i = 0; i < a.length; i++)
		{
			//if the current index is less than the pivot
			if(a[i].compareTo(pivot) < 0)
			{
				//if the lessPlacement is equal to the index then place it
				if(lessPlacement == i)
				{
					//place it and move on by incrementing lessPlacement
					lessPlacement++;   				
				}
				else //else it means lessPlacement < index
				{
					//lessPlacement would be on a smaller index on a value
					//that is either equal or greater than pivot 
					//so swap  and place
					swapReferences(a, lessPlacement, i);

					//increment lessPlacement to move forwards
					lessPlacement++;

				}

			}
		}

		//BUILDING GREATER-THAN SIDE
		//then iterate from right to left and swap if bigger
		//keep an index to know where you are: starts from the last index
		int greaterPlacement = a.length-1;
		for(int j = a.length-1; j >= 0; j--)
		{
			//if the current value is greater than the pivot
			if(a[j].compareTo(pivot) > 0)
			{
				//if the greaterPlacement is equal to the index then place it aka move on
				if(greaterPlacement == j)
				{
					//place it and move on by incrementing lessPlacement
					greaterPlacement--;   				
				}
				else //else it means greaterPlacement > index
				{
					//greaterPlacement would be on a larger index on a value
					//that is either equal or greater than pivot 
					//so swap  and place
					swapReferences(a, greaterPlacement, j);

					//increment lessPlacement to move forwards
					greaterPlacement--;

				}

			}

		}

	}

	
	/**
	 * Return median of left, center, and right.
	 * Order these and hide the pivot.
	 */
	private static <AnyType extends Comparable<? super AnyType>>
	AnyType median3( AnyType [] a, int left, int right )
	{
		//set center as current middle of array
		int center = ( left + right ) / 2;
		//if the center value is less than left, swap with left to be very left/1st index 
		if( a[center].compareTo( a[left] ) < 0 )
			swapReferences( a, left, center );

		//if left is greater than the right, swap 
		if( a[right].compareTo( a[ left ] ) < 0 )
			swapReferences( a, left, right );

		//if the center value is greater than right, swap with right to be very right/last index 
		if( a[right].compareTo( a[center] ) < 0 )
			swapReferences( a, center, right );

		//now the center one should be at center

		//code from other methods I'm going to remove this 
		//because it just moves the pivot to the far right

		// Place pivot at position right - 1
		//swapReferences( a, center, right - 1 );

		//the median of 3 is now located at the center
		return a[center];
	}

	//swaps the indexes of the elements
	public static <AnyType> void swapReferences( AnyType [ ] a, int index1, int index2 )
	{
		AnyType tmp = a[index1];
		a[index1] = a[ index2 ];
		a[index2] = tmp;
	}
	
	public String toString()
	{
		String repr = "";
		for(int i = 0; i < a.length; i++)
		{
			repr = repr + " " + a[i];
		}
		
		return repr;
		
	}
}
