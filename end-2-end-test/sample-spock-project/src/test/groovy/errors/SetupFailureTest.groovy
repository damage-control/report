package errors

import spock.lang.Specification

class SetupFailureTest extends Specification {

    static class FakeFile {
        def open() { throw new Exception() }
        def read() { 'file contents' }
        def write(contents) { true }
    }

    def file = new FakeFile()

    def setup() {
        file.open()
    }

    def "reads from file"() {
        expect: "file contents to be read"
        file.read() == 'file contents'
    }

    def "writes to file"() {
        expect: "file contents to be written"
        file.write('contents')
    }
}
