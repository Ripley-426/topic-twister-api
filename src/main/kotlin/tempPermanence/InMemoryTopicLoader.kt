package com.example.tempPermanence

import com.example.interfaces.ITopicLoader
import com.example.model.Topic
import com.example.model.TopicList

class InMemoryTopicLoader : ITopicLoader {
    private var topicList:TopicList = TopicList()

    init {
        AddTopics()
        AddAlphabetToTopicList()
    }

    fun AddTopics() {
        var animalTopic: Topic = Topic("ANIMALS", mutableListOf(
            "Aardvark", "Alligator", "Alpaca", "Anaconda", "Ant", "Antelope", "Ape", "Aphid", "Armadillo", "Asp", "Ass",
            "Baboon", "Badger", "Bald Eagle", "Barracuda", "Bass", "Basset Hound", "Bat", "Bear", "Beaver", "Bedbug",
            "Bee", "Beetle", "Bird", "Bison", "Black panther", "Black Widow Spider", "Blue Jay", "Blue Whale", "Bobcat",
            "Buffalo", "Butterfly", "Buzzard", "Camel", "Caribou", "Carp", "Cat", "Caterpillar", "Catfish", "Cheetah",
            "Chicken", "Chihuahua", "Chipmunk", "Cicada Clam Clownfish Coati", "Cobra", "Cockatoo Cockroach", "Cod",
            "Condor", "Copperhead", "Cougar", "Cow", "Coyote", "Crab", "Crane", "Cricket", "Crocodile", "Crow",
            "Cuckoo", "Deer", "Dinosaur", "Dog", "Dolphin", "Donkey", "Dove", "Dragonfly", "Duck", "Eagle", "Eel",
            "Elephant", "Emu", "Falcon", "Ferret", "Finch", "Fish", "Flamingo", "Flea", "Fly", "Fox", "Frog", "Goat",
            "Goose", "Gopher", "Gorilla", "Grasshopper", "Hamster", "Hare", "Hawk", "Hippopotamus", "Horse",
            "Hummingbird", "Humpback Whale", "Husky", "Iguana", "Impala", "Kangaroo", "Lemur", "Leopard", "Lion",
            "Lizard", "Llama", "Lobster", "Marmot", "Monitor lizard", "Monkey", "Moose", "Mosquito", "Moth",
            "Mountain goat", "Mouse", "Mule", "Octopus", "Orca", "Ostrich", "Otter", "Owl", "Ox", "Oyster", "Panda",
            "Parrot", "Peacock", "Pelican", "Penguin", "Perch", "Pheasant", "Pig", "Pigeon", "Polar bear", "Porcupine",
            "Quoll", "Rabbit", "Raccoon", "Rat", "Rattlesnake", "Red Panda", "Rooster", "Sea lion", "Sheep", "Shrew",
            "Sloth", "Snail", "Snake", "Spider", "Tiger", "Turkey", "Wolf", "Wombat", "Zebra"
        ))
        topicList.AddTopic(animalTopic)

        var namesTopic: Topic = Topic("NAMES", mutableListOf(
            "Abigail", "Addison", "Aiden", "Alexander", "Amelia", "Anthony", "Aria", "Asher", "Aurora", "Ava", "Avery",
            "Benjamin", "Camila", "Carter", "Charlotte", "Chloe", "Daniel", "David", "Eleanor", "Elena", "Eli",
            "Eliana", "Elias", "Elijah", "Elizabeth", "Ella", "Ellie", "Emilia", "Emily", "Emma", "Ethan", "Evelyn",
            "Everly", "Ezra", "Gabriel", "Gianna", "Grace", "Grayson", "Harper", "Hazel", "Henry", "Hudson", "Isabella",
            "Isaiah", "Isla", "Ivy", "Jack", "Jackson", "Jacob", "James", "Jaxon", "Jayden", "John", "Joseph", "Josiah",
            "Julian", "Kai", "Layla", "Leo", "Levi", "Liam", "Lily", "Lincoln", "Logan", "Luca", "Lucas", "Luke",
            "Luna", "Madison", "Mason", "Mateo", "Matthew", "Maverick", "Maya", "Mia", "Michael", "Mila", "Muhammad",
            "Noah", "Nora", "Nova", "Oliver", "Olivia", "Owen", "Paisley", "Penelope", "Riley", "Rylee", "Ryleigh",
            "Samuel", "Scarlett", "Sebastian", "Sofia", "Sophia", "Stella", "Violet", "William", "Willow", "Wyatt",
            "Zoey"
        ))
        topicList.AddTopic(namesTopic)

        var countriesTopic: Topic = Topic("COUNTRIES", mutableListOf(
            "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Argentina", "Armenia", "Australia", "Austria",
            "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin",
            "Bhutan", "Bolivia", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burma", "Burundi", "Cambodia", "Cameroon",
            "Canada", "Chad", "Chile", "China", "Comoros", "Congo", "Croatia", "Cuba", "Cyprus", "Denmark", "Djibouti",
            "Dominica", "Ecuador", "Egypt", "Eritrea", "Estonia", "Eswatini", "Ethiopia", "Fiji", "Finland", "France",
            "Gabon", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guyana", "Haiti",
            "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy",
            "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea", "Kyrgyzstan", "Laos", "Latvia",
            "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macedonia",
            "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Mauritania", "Mauritius", "Mexico",
            "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Namibia", "Nauru",
            "Nepal", "Netherlands", "Nicaragua", "Niger", "Nigeria", "Norway", "Oman", "Pakistan", "Palau", "Panama",
            "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia", "Rwanda", "Samoa",
            "Senegal", "Serbia", "Seychelles", "Singapore", "Slovakia", "Slovenia", "Somalia", "Spain", "Sudan",
            "Suriname", "Sweden", "Switzerland", "Syria", "Tajikistan", "Tanzania", "Thailand", "Togo", "Tonga",
            "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates",
            "United Kingdom", "United States of America", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City",
            "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe"
        ))
        topicList.AddTopic(countriesTopic)

        var plantsTopic: Topic = Topic("PLANTS", mutableListOf(
            "acorn", "agriculture", "alfalfa", "angiosperm", "annual", "antherapical meristem", "autotroph", "axil",
            "axilary bud", "bamboo", "bark", "beanberry", "biennial", "blade", "blossom", "botany", "bract", "branch",
            "bromeliad", "brush", "bud", "bulb", "bulbel", "bush", "cactus", "calyx", "canopy", "carpel", "cleft leaf",
            "clover", "composite flower", "compound leaf", "cone", "cork", "corn", "corolla", "crenate leaf",
            "deciduous", "dentate leaf", "dicot", "embryo", "emergents", "endosperm", "entire", "epicotyl", "evergreen",
            "fern", "fertilizer", "filament", "flora", "flower", "foliage", "forest", "frond", "fruit", "garden",
            "germinate", "ginkgo", "grain", "grass", "grove", "grow", "guard cellgum", "hardy", "hastate", "herb",
            "horsetail", "horticulture", "hybrid", "imperfect flower", "incomplete flower", "inflorescence",
            "internodeivy", "jungle", "juniper", "kapok tree", "kelp", "kudzu", "laminalanceolate leaf", "lateral bud",
            "lead scar", "leaf", "leaflet", "legume", "lily", "lobed", "margin", "meristem", "midrib", "monocot",
            "moss", "nectar", "needle", "netted", "node", "nut",  "opposite leaves", "ovary", "palm", "palmate",
            "parted leaf", "peduncle", "perennial", "perfect flower", "petal", "petiole", "petrified wood", "phloem",
            "photosynthesis", "pinnate", "pistil", "pith", "plum", "poison ivy", "pollen", "pollinate", "prickle",
            "pulse", "rachis", "rain forest", "reniform", "resin", "reticulate", "rings", "root", "root cap",
            "root hairs", "root tip", "rootstock", "sage brush", "sap", "sapling", "sea weed", "seed", "seed pod",
            "seedling", "sepal", "serrated leaf", "shamrock", "shoot", "shrub", "simple leaf", "soil", "spine", "spore",
            "sprout", "stalk", "stamen", "stand", "stem", "stigma", "stipule", "stomastyle", "succulent", "sunlight",
            "tap root", "terminal bud", "thorn", "toothed", "tree", "tree fern", "trunk", "tuber", "tumbleweed", "twig",
            "understory", "undulate leaf margin", "vascular plant", "vegetable", "vegetation", "vein", "venation",
            "vine", "weed", "whorled", "wood", "woody", "xerophyte", "xylem", "yucca"
        ))
        topicList.AddTopic(plantsTopic)

        var jobsTopic: Topic = Topic("JOBS", mutableListOf(
            "actor", "baker", "butcher", "carpenter", "cook", "doctor", "engineer", "farmer", "fireman", "fireworker",
            "fisherman", "gardener", "goldsmith", "hairdresser", "journalist", "judge", "lawyer", "mason", "mechanic",
            "nurse", "painter", "pilot", "plumber", "policeman", "postman", "secretary", "singer", "soldier", "tailor",
            "taxi driver", "teacher", "waiter"
        ))
        topicList.AddTopic(jobsTopic)
    }

    fun AddAlphabetToTopicList(){
        for (topic in topicList.GetTopics()){
            AddAlphabet(topic)
        }
    }

    fun AddAlphabet(topic: Topic) {
        for (letter in ('A'..'Z')) {
            topic.AddWord(letter.toString())
        }
    }

    override fun LoadTopics(): MutableList<Topic> {
        return topicList.GetTopics()
    }
}