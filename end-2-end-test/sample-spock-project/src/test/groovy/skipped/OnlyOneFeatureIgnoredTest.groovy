package skipped

import spock.lang.Ignore
import spock.lang.Specification

class OnlyOneFeatureIgnoredTest extends Specification {

    def "Spock and his friends"() {
        given: "Spock has 3 friends"
        def spockFriends = ["Kirk", "Sulu","McCoy"]

        when: "He meets another friend"
        spockFriends.add("Scotty")

        then: "Spock now has 4 friends"
        spockFriends.size() == 4
    }

    @Ignore
    def "length of Spock's and his friends names"() {
        expect: "name length is correct"
        name.size() == length

        where:
        name     | length
        "Spock"  | 5
        "Kirk"   | 4
        "Scotty" | 6
    }
}
