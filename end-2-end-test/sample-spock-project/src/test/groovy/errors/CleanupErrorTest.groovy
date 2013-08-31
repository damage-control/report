package errors

import spock.lang.Specification

class CleanupErrorTest extends Specification {

    static class FakeFile {
        def close() { throw new Exception() }
        def read() { 'file contents' }
        def write(contents) { true }
    }

    def file = new FakeFile()

    def cleanup() {
        file.close()
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
