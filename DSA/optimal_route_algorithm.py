import heapq    

def optimalRoute(start, end, passengers, roads):
    """
    Function description: This function returns a list of integers that contains the most optimal journey from start to end, optimal being the shortest amount of time taken.
    This is performed using a uniquely-modified version of the Dijkstra's algorithm to identify the lowest amount of time taken to go from a certain town to the start, both
    with respect to driving alone & carpooling.

    Approach description: Since the scenario provides us with two different values for each weighted & directed edge "road" that corresponds to whether we are currently alone
    or carpooling, we can split the scenario into two different subsections: a section find the shortest distance where we do not carpool & a section where we find the shortest
    distance carpooling, beginning from the location of all the carpool spots. Firstly the standard Dijkstra's algorithm will be applied to calculate the shortest distance to
    all locations from the start while driving alone. If during the calculation we come across locations where there are passengers that can carpool with you, that location will
    be added into a list that stores all the carpool spots, alongside all the information regarding travelling to said location itself. Once the standard algorithm has concluded,
    if there is carpool spots the Dijkstra's algorithm will run one more time to calculate the shortest distance carpooling, this time with a modified list to serve as distance 
    & precedance. These two situations & their lists will be compared when we construct the route, to determine which route is the shortest amongst them all.

    :Input:
        start: a departure location that is ∈ {0, 1, . . . , |L| − 1}
        end: a destination location that is ∈ {0, 1, . . . , |L| − 1}   
        passangers: a list of integers such that each integer i in it is such that i ∈ {0, 1, . . . , |L| − 1} 
        and indicates that there are potential passengers at location i looking for a ride to your destination.
        roads: a list of tuples (a, b, c, d) where:
            • a ∈ {0, 1, . . . , |L| − 1} is the starting location of the road.
            • b ∈ {0, 1, . . . , |L| − 1} is the ending location of the road.
            • c is a positive integer representing how many minutes you would spend to drive from a to b on that 
            road if you are alone in the car.
            • d is a positive integer representing how many minutes you would spend to drive from a to b on that 
            road if you are there are 2 or more persons in the car.

    :Output: A list of integers containing the locations to travel for the most optimal route from start to end, with the first element being the beginning and the last
    element beng the destination.

    :Time complexity: O(|R|Log(|L|)), where |R| is the number of locations with passengers in the map & |L| is the number of locations in the map. The standard Dijkstra's 
    algorithm will run in O(Log(L)) time, and because get_connected_locations() is called in every loop, their time complexity of O(|R|) will be added into this turning it
    into O(|R|Log(L)). Extensively, the for loop that happens after the first Dijkstra runs at O(|P|), as it has to iterate every single carpool location in the map to complete
    the iteration. The modified Dijkstra's algorithm that happens after that runs in a similar time complexity as the first Dijkstra, as the only change in the algorithm is
    the modified lists, bringing it to O(|R|Log(|L|)) aswell. Finally, when constructing the route list, the loop will run in at most O(2|L|) time, to keep in mind that both
    the locations of driving alone & carpooling could overlap. This brings the total complexity to O(|R|log(|L|) + |P| + |R|log(|L|) + 2|L|), which can be simplified to 
    O(|R|log(|L|)), as that term dominates everything else.

    :Aux space complexity: O(|L| + |R|), where |R| is the number of locations with passengers in the map & |L| is the number of locations in the map. All created lists are all in the length of |R|,
    and the priority can only be at maximum be in the length of |L|.
    """
    # Setting up lists used in the Dijkstra's algorithm
    distance = [float('infinity') for i in range (len(roads)+1)]    
    precedance = [0 for i in range (len(roads)+1)]

    # Setting up carpool-related lists used in the second part of the now slightly modified Dijkstra's algorithm
    distance_carpool = [float('infinity') for i in range (len(roads)+1)]    
    precedance_carpool = [0 for i in range (len(roads)+1)]
    carpool = [False for i in range (len(roads)+1)] 

    # Starting location is defined & inserted into the priority queue upon creation as a tuple, with the format (location index, key)
    distance[start] = 0
    priority_queue = [(start, 0)]

    ########## DIJKSTRA'S ALGORITHM PROCESS ##########

    # Looping the priority queue until theres no more items in the queue
    while priority_queue:
        # Leftmost item is popped
        search, key = heapq.heappop(priority_queue)
        # if statement to check if an entry is outdated by comparing the key with the location's current shortest distance
        if distance[search] == key:
            # All edges that leads to a connected location is iterated through.
            for location in get_connected_locations(roads,search):
                destination_town = location[0]
                # Checker to see if the current shortest distance to the connected location is longer than the shortest time taken to get to the previous location & drive to the
                # connected location alone. If true, relax the edge & update with the new shortest distance.
                if distance[destination_town] > distance[search] + location[1]:
                    distance[destination_town] = distance[search] + location[1]
                    precedance[destination_town] = search
                    # New entry with the new time taken used as a key pushed into the heap priority queue
                    heapq.heappush(priority_queue,(destination_town,distance[destination_town]))
                    # Checker to see if the location happens to have passengers to carpool. If there is, distance & precedance information will be added into their carpool lists
                    if destination_town in passengers:
                        carpool[destination_town] = True
                        distance_carpool[destination_town] = distance[destination_town]
                        precedance_carpool[destination_town] = precedance[destination_town]
    
    # For loop to push all carpool locations into the heap
    for location in passengers:
        heapq.heappush(priority_queue, (location, distance_carpool[location]))

    # If there is carpool locations the heap will continue in a modified way, updating the carpool equivalent of all the lists above.
    while priority_queue:
        # Leftmost item is popped
        search, key = heapq.heappop(priority_queue)
        # if statement to check if an entry is outdated by comparing the key with the location's current shortest distance
        if distance_carpool[search] == key:
            # All edges that leads to a connected location is iterated through.
            for location in get_connected_locations(roads,search):
                destination_town = location[0]
                # Checker to see if the current shortest distance to the connected location is longer than the shortest time taken to get to the previous location & drive to the
                # connected location alone. If true, relax the edge & update with the new shortest distance.
                if distance_carpool[destination_town] > distance_carpool[search] + location[2]:
                    carpool[destination_town] = True
                    distance_carpool[destination_town] = distance_carpool[search] + location[2]
                    precedance_carpool[destination_town] = search
                    heapq.heappush(priority_queue,(destination_town,distance_carpool[destination_town]))

    ########## ROUTE CONSTRUCTING PROCESS ##########

    # Placeholder variable for the list
    final_route = []

    # Temporary variable for the current location, defaulted to end
    destination = end

    # Variable to indicate whether to use the carpool list or normal list
    carpool_phase = False

    # If statement to check whether the shortest distance with carpool is shorter than the shortest distance without carpool
    if distance[destination] > distance_carpool[destination]:
        carpool_phase = True

    # Infinite loop that can only be broken out of if destination == start & theres no carpooling
    while True:
        # the current location is appended into the list, starting with the location of the destination
        final_route.append(destination)

        # Once the very beginning has been appended, the list will be reversed & returned.
        if destination == start and carpool_phase == False:
                final_route.reverse()
                # The constructed product is returned.
                return final_route
        else: 
            # destination variable updated with the location connected to the appended location, based on whether is it carpooling or not
            if carpool_phase:
                # if the current location is a carpool spot, it is safe to assume the rest of the locations will not be done in carpool, so we will return back to using the normal lists
                if destination in passengers:
                    carpool_phase = False
                destination = precedance_carpool[destination]
            else:
                destination = precedance[destination]

        # variable assignment onto carpool element to indicate that that location has been traveled with or without carpool, and the next time you enter the same location will be alone
        carpool[destination] = False

def get_connected_locations(roads, current_location):
    """
    Function description: A function that retrieves every single location that is currently connected to the current location it is in, utilizing the road map & the location
    itself. This is done by iterating through the entirety of roads in a for loop to check if the start location of the road tuples lines up with the current town.

    :Input:
        roads: a list of tuples (a, b, c, d) where:
            • a ∈ {0, 1, . . . , |L| − 1} is the starting location of the road.
            • b ∈ {0, 1, . . . , |L| − 1} is the ending location of the road.
            • c is a positive integer representing how many minutes you would spend to drive from a to b on that 
            road if you are alone in the car.
            • d is a positive integer representing how many minutes you would spend to drive from a to b on that 
            road if you are there are 2 or more persons in the car.
        current_location: an integer that represents the current location you are in

    :Output: A list of tuples, where each tuples contains the location of the connected location, the time taken to drive to said location alone & the time taken to drive while
    on carpool.

    :Time complexity: O(|R|), where |R| is the number of roads in the map. The whole roads list needs to be iterated through to check if there are matching elements with
    current_location, so it will always iterate |R| times, hence resulting in O(|R|)

    :Aux space complexity: O(k), where k is the number of connected locations that start from the current location.
    
    """
    # Placeholder variable for the list
    connected_locations = []
    # for loop that iterates through the whole roads list
    for road in roads:
        # If the first element of the road tuple, being the starting location lines up with the current location, then the tuple will be appended into the list, excluding the
        # beginning destination.
        if road[0] == current_location:
            connected_locations.append((road[1],road[2],road[3]))

    # Once fully iterated, the list is returned.
    return connected_locations