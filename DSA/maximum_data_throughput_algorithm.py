import heapq

def maxThroughput(connections, maxIn, maxOut, origin, targets):
    """
    Description:
        Calculates the maximum throughput in a network flow with limitations on incoming and outgoing data from each data center,
        where data centers are displayed as nodes and connections are displayed as edges

    Approach:
    - The function builds an adjacency matrix representing the network flow graph.
    - It also converts all the the maxIn & maxOut element that corresponds to the index of the node into their own seperate node,
    where the maxIn node is connected to the original node with an edge of value maxIn[i], where i is the index of the node, and
    similarly to the maxOut node aswell.
    - an extra node is also created at the end to connect all target nodes to form a supersink, where the base node of the target node
    is connected to the supersink with infinite capacity
    - It then uses the Edmonds-Karp algorithm with breadth-first search to find augmenting paths and calculate the maximum throughput,
    augmenting the path in the bsf itself.

    Args:
        connections (list): List of tuples representing edges between nodes. Each tuple contains three values: 
                            (a, b, t), where a is the ID of the node from which the edge departs, b is the ID of 
                            the node to which the edge arrives, and t is the capacity of the edge.
        maxIn (list): List of integers specifying the maximum amount of incoming flow that each node can receive.
        maxOut (list): List of integers specifying the maximum amount of outgoing flow that each node can send.
        origin (int): ID of the source node.
        targets (list): List of IDs representing the sink node(s).

    Returns:
        int: The maximum throughput in the network flow.

    Time Complexity:
    - The time complexity of the function is O(V * E^2), where V is the number of vertices and E is the number of edges in the graph.

    Auxiliary Space Complexity:
    - The auxiliary space complexity of the function is O(V^2), where V is the number of vertices in the graph.
    """

    # Calculate the index of the last target node and the total number of vertices in the graph
    target_index = len(maxIn) * 3
    vertex_count = (len(maxIn) * 3) + 1

    # Create the adjacency matrix and the resultant matrix
    matrix = [[0 for _ in range(vertex_count)] for _ in range(vertex_count)]
    resultant = [[0 for _ in range(vertex_count)] for _ in range(vertex_count)]

    # Assign capacities to the matrix based on maxIn and maxOut restrictions
    for index in range(len(maxIn)):
        one = index * 3
        two = one + 1
        three = one + 2
        matrix[one][two] = maxIn[index]
        matrix[two][three] = maxOut[index]

    # Assign capacities to the matrix based on the provided connections
    for connector in connections:
        start = (connector[0] * 3) + 2
        target = connector[1] * 3
        capacity = connector[2]
        matrix[start][target] = capacity

    # Set capacities from nodes representing sinks to target_index as infinity
    for target in targets:
        matrix[(target * 3) + 1][target_index] = float("inf")

    final_capacity = 0

    while True:
        # Perform breadth-first search to find an augmenting path
        path = bfs(matrix, resultant, origin)
        if path is None:
            return final_capacity
        else:
            final_capacity += path[1]

def bfs(graph, resultant, starting):
    """
    Performs breadth-first search to find an augmenting path in the graph, taking considerations of whether the edge is a resultant
    or not.

    Args:
        graph (list): Adjacency matrix representing the original edges and the capacities.
        resultant (list): Resultant matrix keeping track of residual capacities.
        starting (int): index of the starting node.

    Returns:
        (itinerary, path_capacity) or None: a tuple containing the augmented path and all the nodes it took and 
        its capacity, or None if no path is found.

    Time Complexity:
    - The time complexity of the breadth-first search algorithm is O(V * E), where V is the number of vertices 
      and E is the number of edges in the graph.

    Auxiliary Space Complexity:
    - The auxiliary space complexity of the breadth-first search algorithm is O(V), where V is the number of 
      vertices in the graph.
    """

    # initializing specific nodes
    initial_node = (starting * 3) + 1
    destination = len(graph) - 1

    # intiailizing lists to store information about traversed nodes
    visited = [False for _ in range(destination + 1)]
    precedence = [None for _ in range(destination + 1)]

    # setting up the first node to be inserted into the heapq
    visited[initial_node] = True
    priority_queue = [(initial_node, float("inf"))]

    # initializing the capacity of the search
    path_capacity = 0

    while priority_queue:
        # item is popped and used for search
        initial, preceding_capacity = heapq.heappop(priority_queue)
        targets = search_adjacent_nodes(graph, resultant, initial)
        # all nodes adjacent to the original node is iterated.
        for target in targets:
            # if not visited, add it into the queue and store information regarding it.
            if not visited[target]:
                visited[target] = True
                precedence[target] = initial
                # if the target is the destination node, the search is completed.
                if target == destination:
                    path_capacity = min(preceding_capacity, graph[initial][target])
                    break
                # a checker to retrieve the minimum capacity between the previous capacity and the capacity of the edge.
                if resultant[initial][target] == 0:
                    heapq.heappush(priority_queue, (target, min(preceding_capacity, graph[initial][target])))
                else:
                    heapq.heappush(priority_queue, (target, min(preceding_capacity, resultant[initial][target])))

    # if capacity is 0, no path has been found.
    if path_capacity == 0:
        return None

    itinerary = []
    current_node = destination

    # looper to loop through all the nodes & update their capacities
    while current_node != initial_node:
        preceding_node = precedence[current_node]

        # checker to prevent any edge cases
        if precedence[preceding_node] == current_node:
            return None

        itinerary.append(current_node)

        # updating all the edge capacities according to whether it is a resultant edge or a normal edge.
        if resultant[preceding_node][current_node] == 0:
            graph[preceding_node][current_node] -= path_capacity
            resultant[current_node][preceding_node] += path_capacity
        else:
            graph[current_node][preceding_node] += path_capacity
            resultant[preceding_node][current_node] -= path_capacity

        # after everything is done, reupdate the current node.
        current_node = preceding_node

    itinerary.append(initial_node)
    itinerary.reverse()

    # everything is returned.
    return (itinerary, path_capacity)

def search_adjacent_nodes(graph, resultant, initial_node):
    """
    Searches for adjacent nodes of the initial node in the graph.

    Input:
        graph (list): Adjacency matrix representing the graph.
        resultant (list): Resultant matrix keeping track of residual capacities.
        initial_node (int): ID of the initial node.

    Returns:
        list: List of IDs representing the adjacent nodes.

    Time Complexity:
    - The time complexity of this function is O(V), where V is the number of vertices in the graph.

    Auxiliary Space Complexity:
    - The auxiliary space complexity of this function is O(1).
    """

    # Runs through all nodes adjacent to the initial node, and if theres a capacity higher than 0 there an edge there.
    target_nodes = []
    for index in range(len(graph)):
        if graph[initial_node][index] > 0:
            target_nodes.append(index)
        if resultant[initial_node][index] > 0:
            target_nodes.append(index)
    return target_nodes

class CatsTrie:
    """
    The CatsTrie class represents a trie data structure for auto-completing all the cat sentences.

    Attributes:
        root (Node): The root node of the trie.

    Methods:
        __init__(self, sentences): Initializes the CatsTrie object and builds the trie based on the given sentences.
        build_trie(self, sentences): Builds the trie by inserting each sentence into the trie.
        get_all_sentences(self, sentences, frequency_list, node, starting_phrase): Recursively collects all sentences in the trie.
        autoComplete(self, prompt): Performs auto-completion of the given prompt based on the most commonly used sentences.
    """

    def __init__(self, sentences):
        """
        Initializes the CatsTrie object and builds the trie based on the given sentences.

        Args:
            sentences (list): A list of cat sentences represented as strings.

        Time complexity: O(N * M), where N is the number of sentences and M is the average length of a sentence.
        Aux space complexity: O(N * M), where N is the number of sentences and M is the average length of a sentence.
        """
        # The root of the class is initialized to store all the first combinations of letters.
        self.root = Node()
        # Function is called to build the trie.
        self.build_trie(sentences)

    def build_trie(self, sentences):
        """
        Builds the trie by inserting each sentence into the trie.

        Args:
            sentences (list): A list of cat sentences represented as strings.

        Time complexity: O(N * M), where N is the number of sentences and M is the average length of a sentence.
        Aux space complexity: O(N * M), where N is the number of sentences and M is the average length of a sentence.
        """
        # Sentences are iterated through from the sentences list
        for sentence in sentences:
            # current node is initialized to reflect the node to lookup during the loop. In the beginning of every loop, the node begins with the root.
            current_node = self.root
            # every letter will be iterated through from the sentence
            for letter in sentence:
                # index variable created to indicate the position of the letter in the alphabet. For example, "a" is 0 & "z" is 26.
                index = ord(str(letter)) - 97
                # the node's child_nodes attribute is checked to see if the letter is stored in the node. If not, create a new object in the attribute's index.
                if current_node.child_nodes[index] is None:
                    current_node.child_nodes[index] = Node()
                    current_node.letters.append(index) # the index of the letter is appended into an additional attribute for future checking
                current_node = current_node.child_nodes[index]
            # the last letter of the sentence has been reached. the frequency will be increased for this particular node
            current_node.frequency += 1

    def get_all_sentences(self, sentences, frequency_list, node, starting_phrase):
        """
        Recursively collects all sentences in the trie.

        Args:
            sentences (list): A list to store the collected sentences.
            frequency_list (list): A list to store the frequencies of the collected sentences.
            node (Node): The current node being processed.
            starting_phrase (str): The current prefix of the sentence being built.

        Time complexity: O(N), where N is the number of nodes in the trie.
        Aux space complexity: O(N * M), where N is the number of nodes in the trie and M is the average length of a sentence.
        """
        # initialized variables
        phrase = starting_phrase
        skip = False

        if node.frequency > 0:
            sentences.append((phrase, node.frequency))
            frequency_list.append(node.frequency)
            if node.letters is None:
                skip = True

        if not skip:
            for letter_index in node.letters:
                new_letter = chr(97 + letter_index)
                self.get_all_sentences(sentences, frequency_list, node.child_nodes[letter_index], phrase + new_letter)

    def autoComplete(self, prompt):
        """
        Performs auto-completion of the given prompt based on the most commonly used sentences.

        Args:
            prompt (str): The incomplete sentence to be completed.

        Returns:
            str or None: The completed sentence with the highest frequency, or None if no completion is found.

        Time complexity: O(K), where K is the length of the prompt.
        Aux space complexity: O(N * M), where N is the number of sentences and M is the average length of a sentence.
        """
        list_of_sentences = []
        list_of_frequency = []
        initial_product = ""
        current_node = self.root
        highest_frequency = 0

        for letter in prompt:
            index = ord(str(letter)) - 97
            if current_node.child_nodes[index] is None:
                return None
            else:
                initial_product += letter
            current_node = current_node.child_nodes[index]

        self.get_all_sentences(list_of_sentences, list_of_frequency, current_node, initial_product)
        highest_frequency = max(list_of_frequency)
        completed_sentence = None

        for sentence in list_of_sentences:
            if completed_sentence is None and sentence[1] == highest_frequency:
                completed_sentence = sentence[0]
            elif sentence[1] == highest_frequency:
                completed_sentence = min(completed_sentence, sentence[0])

        return completed_sentence


class Node:
    """
    The Node class represents a node in the CatsTrie.

    Attributes:
        child_nodes (list): A list of child nodes.
        letters (list): A list of letters that are present in the node's children.
        frequency (int): The frequency count of sentences that end at this node.

    Methods:
        __init__(self): Initializes a new instance of the Node class.
    """

    def __init__(self):
        """
        Initializes a new instance of the Node class.

        Time complexity: O(1)
        Aux space complexity: O(1)
        """
        self.child_nodes = [None for _ in range(26)]
        self.letters = []
        self.frequency = 0