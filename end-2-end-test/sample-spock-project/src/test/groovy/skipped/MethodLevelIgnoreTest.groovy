package skipped

import spock.lang.Ignore
import spock.lang.Specification

class MethodLevelIgnoreTest extends Specification {

    @Ignore
    def "Spock and his friends"() {
        given: "Spock has 3 friends"
        def spockFriends = ["Kirk", "Sulu","McCoy"]

        when: "He meets another friend"
        spockFriends.add("Scotty")

        then: "Spock now has 4 friends"
        spockFriends.size() == 4
    }
}
