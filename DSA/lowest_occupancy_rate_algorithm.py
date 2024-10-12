def select_sections(occupancy_probability):
    """
    Function description: This function returns a list containing two elements, the total occupancy probability & the list of all the sections removed, which identifies the best option
    to remove an office's section in a way that the total occupancy probability is minimized, adhearing to all the 3 conditions mentioned in the document. This is performed using
    bottom-up Dynamic Programming, iterating through a nested loop to store all Dynamic Programming values.

    Approach description: Since there will be n*m amount of items in the list, n being the total number of rows & m being the total number of columns, we can calculate the most optimal
    occupancy probability by solving iteratively, using dynamic programming. As part of dynamic programming, we have undergo some degree of memoisisation to store previously computed
    solutions so that future iterations can utilize those solutions to continue solving iteratively. A 2D array of size n*m should be set up in tangent to that, and since we are going
    to be iterating through the rows on the OCP list, we will be using a bottom-up method to solve this, starting from 0 to n. The base case of our recurrance is of the following:

    MinP[n][m] = occupancy_probability[n][m], where n = 0

    This is because there is no previous row for the first row to utitlize solutions, so our array will begin in that fashion. After that the relationship will follow through in consistent
    manner, using the following recurrance:

    MinP[n][m] = min(Min[n-1][k]) + occupancy_probability[n][m], where n > 0 & k ranges between m-1 & m+1

    k can be either 2 or 3 items, depending on which column the current iteration is at. If it is at the border of the row it will only have 2 items, otherwise it will always have 3 items.
    This iteration will continue untill all of MinP has been filled up, until the last row of the last column. After that, the output will be constructed in a similar method, where we will
    now backtrack the MinP array to see amongst all the items in the row which has the smallest OCP, and reference its index to the occupancy_probability list add its position into the output,
    while adding its OCP into the total aswell. This will continue until the loop has finally backtracked to the very first row, and the total OCP used has been finalized & all n positions in
    the office space has been identified.

    :Input:
        occupancy_probability: a list of list that contains n amount of interior lists that each has m items in it. All those lists will contain an integer from 0 to 100 inclusive, which
        represents the occupancy probability of that specific section n index n row & m collumn

    :Output: A list that contains two items: minimum_total_occupancy & sections_location. minimum_total_occupancy is an integer that totals all the selected section's occupancy probability,
    and sections_location is the location of said section in the table, represented in the form of a tuple of 2 elements, first element representing the row index & the second element
    representing the column index.

    :Time complexity: O(nm), where n is the number of rows, & m is the number of columns. Throughout this formula, two similar nested loops were made that iterates m amount of times in an exterior iteration
    that lasts n times. This results in a total time complexity of O(2nm), which can be simplified into O(nm)

    :Aux space complexity: O(nm), where n is the number of rows, & m is the number of columns. The array used for Dynamic programming is created in n*m space, so the space complexity is O(nm)
    """

    # Variables indicating the length of the rows & columns are made.
    rows = len(occupancy_probability)
    columns = len(occupancy_probability[0])

    # Array used for Dynamic programming has been initialized. The array will be in rows*columns size
    MinP = [[0]*columns for i in range (rows)]

    ##### BOTTOM-UP DYNAMIC PROGRAMMING PROCESS #####

    # Nested for loop used to go through every single item in MinP.
    for row in range (rows):
        for aisle in range (columns):
            # Variables used to indicate the range of the recursive case.
            aisle_start = aisle-1
            aisle_end = aisle+2
            # Extra if statements when the item is at the boundary of the row & a maximum of 2 items can only be compared.
            if aisle_start < 0:
                aisle_start = 0
            if aisle_end > columns:
                aisle_end = columns
            if row == 0:
                # First row of MinP will be initialized with the OCP of the item with the same position index on the OCP table. This is our base case when row = 0
                MinP[row][aisle] = occupancy_probability[row][aisle]
            else:
                # Subsequent rows will use the previous row's data to retrieve the lowest OCP & combine it with its OCP to create the item in MinP.
                MinP[row][aisle] = min(MinP[row-1][aisle_start:aisle_end]) + occupancy_probability[row][aisle]

    ##### OUTPUT-CONSTRUCTING PROCESS #####

    # Output variables are defined
    minimum_total_occupancy = 0
    sections_location = []

    # Temporary variable used for constructing the first output defined
    temporary_index = (None, float("infinity"))

    # For loop to identify the lowest OCP in the top row
    for aisle in range (columns):
        if MinP[row][aisle] < temporary_index[1]:
            temporary_index = (aisle, MinP[row][aisle])

    # Once identified, the information will be added into both sections_location & minimum_total_occupancy
    sections_location += [(rows-1,temporary_index[0])]
    minimum_total_occupancy += occupancy_probability[rows-1][temporary_index[0]]

    # Variable used for constructing the output defined, defaulting to the temporary_index used for the top row as the starting point
    previous_index = temporary_index[0]

    # For loop that will backwards, starting from the second top.
    for row in reversed(range(rows-1)):
        # Initial variable declared as a tuple, which contains the information for what index the item is in, and the OCP it has.
        aisle_index = (None, float("infinity"))

        # Variables used to indicate the range of the recursive case.
        aisle_start = previous_index - 1
        aisle_end = previous_index + 2

        # Extra if statements when the item is at the boundary of the row & a maximum of 2 items can only be compared.
        if aisle_start < 0:
            aisle_start = 0
        if aisle_end > columns:
            aisle_end = columns

        # Nested for loop to iterate through the items in the rows to identify the item with the lowest OCP, adhering to the conditions.
        for aisle in range (aisle_start,aisle_end):
            if MinP[row][aisle] < aisle_index[1]:
                aisle_index = (aisle, MinP[row][aisle])

        # Once identified, the information will be added into both sections_location & minimum_total_occupancy
        sections_location += [(row,aisle_index[0])]
        minimum_total_occupancy += occupancy_probability[row][aisle_index[0]]

        # Variable updated for reference in the next iteration
        previous_index = aisle_index[0]
    
    # Once all rows have been iterated through, the output is completed and will be returned. sections_locaton is reversed as the order of the elements was in the opposite order.
    return [minimum_total_occupancy, list(reversed(sections_location))]