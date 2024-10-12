class CatsTrie:
    """
    The CatsTrie class represents a trie data structure for auto-completing cat sentences.

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

    
if __name__ == "__main__":

    # sentences = ["abc", "abczacy", "dbcef", "xzz", "gdbc", "abczacy", "xyz","abczacy", "dbcef", "xyz", "xxx", "xzz"]
    # cat_trie = CatsTrie(sentences)

    # prompt = "abc"
    # completed_sentence = cat_trie.autoComplete(prompt)
    # print(completed_sentence)  # Output: "abazacy"

    sentences = ['vurkn', 'kauf', 'xrxmliujs', 'xzgkwkdo', 'fufhu', 'igezlkfb', 'xzgkwkdo', 'pcmmurz', 'guainfxm', 'inlvhjjg', 'r', 'rt', 'xzgkwkdo', 'rtynech', 'pcmmurz', 'svtc', 'ejdom', 't', 'xzgkwkdo', 'vurkn', 'rhmqnk', 'c', 'rtynech', 'igezlkfb', 'yrflw', 'jhtxqvil', 'yrflw', 'vw', 'xcm', 'inlvhjjg', 'pcmmurz', 'inlvhjjg', 'euuufb', 'aeyku', 'vccupky', 'rtynech', 'uhkqaktsni', 'jhtxqvil', 'guainfxm', 'xcm', 'rhmqnk', 'euuufb', 'guainfxm', 'lfzczcd', 'jhtxqvil', 'shdzfsbatv', 'iv', 'ftijb', 't', 'yrflw', 'rt', 'sfxxhbhus', 'jybsjbx', 'guainfxm', 'ftijb', 'lxxl', 'stosjssv', 'ftijb', 'fufhu', 'ajcahyb', 'xrxmliujs', 'ejdom', 'zscbcoccdc', 'sckkvxgbwt', 'ejdom', 'k', 'svtc', 'ftijb', 'xpayhoz', 'yrflw', 'lbywobax', 'wrkl', 'zuzjqqmyg', 'ajcahyb', 'tfrxrscbpd', 'kauf', 'ajcahyb', 'fxuvi', 'swpaxzi', 'shdzfsbatv', 'wrkl', 'stosjssv', 'tnh', 'lfzczcd', 'fufhu', 'jybsjbx', 'ajcahyb', 'fxuvi', 'c', 'espdlmpnkm', 'hlj', 'pcmmurz', 'yrflw', 'pvkglnj', 'txzhfncm', 'jybsjbx', 'stosjssv', 'c', 'pcmmurz', 'lfzczcd']
    trie = CatsTrie(sentences)
    print(trie.autoComplete("a"))
    # ajcahyb