package ThisIsAPackage

import spock.lang.Specification

class InsideOfPackageTest extends Specification {

    def "length of Spocks and his friends names"() {
        expect: "name length is correct"
        name.size() == length

        where:
        name     | length
        "Spock"  | 5
        "Kirk"   | 4
        "Scotty" | 6
    }
}
