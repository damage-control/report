import spock.lang.Specification

class AllGreenFeaturesTest extends Specification {

    def "length of Spocks and his friends names"() {
        expect: "name length is correct"
        name.size() == length

        where:
        name     | length
        "Spock"  | 5
        "Kirk"   | 4
        "Scotty" | 6
    }

    def "Spock and his friends"() {
        given: "Spock has 3 friends"
        def spockFriends = ["Kirk", "Sulu","McCoy"]

        when: "He meets another friend"
        spockFriends.add("Scotty")

        and: "He loses one friend"
        spockFriends.remove("Sulu")

        then: "Spock now has 3 friends"
        spockFriends.size() == 3
    }
}
