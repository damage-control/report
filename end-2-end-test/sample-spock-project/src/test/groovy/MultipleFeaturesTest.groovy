import spock.lang.Ignore
import spock.lang.Specification

class MultipleFeaturesTest extends Specification {

    def "length of Spocks and his friends names"() {
        expect: "name length is correct"
        name.size() == length

        where:
        name     | length
        "Spock"  | 5
        "Kirk"   | 4
        "Scotty" | 6
    }

    def "length of Spocks and his friends names FAILED"() {
        expect: "name length is correct"
        name.size() == length

        where:
        name     | length
        "Spock"  | 6
        "Kirk"   | 4
        "Scotty" | 6
    }

    def "feature with several steps FAILED"() {
        given: "given description"
        // some code
        when: "when description"
        // some code
        and:
        // some code
        then: 'then description'
        1 == 2
        and: 'and description'
        // some code
    }

    @Ignore
    def "length of Spocks and his friends names SKIPPED"() {
        expect: "name length is correct"
        name.size() == length

        where:
        name     | length
        "Spock"  | 6
        "Kirk"   | 4
        "Scotty" | 6
    }

}
